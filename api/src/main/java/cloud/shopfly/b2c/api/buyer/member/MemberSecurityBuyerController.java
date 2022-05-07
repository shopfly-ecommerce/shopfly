/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.api.buyer.member;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.client.system.CaptchaClient;
import cloud.shopfly.b2c.core.client.system.SmsClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.member.service.MemberSecurityManager;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Member safety controller
 *
 * @author zh
 * @version v7.0
 * @date 18/4/23 In the afternoon3:30
 * @since v7.0
 */
@RestController
@RequestMapping("/members")
@Validated
@Api(description = "Member of the safetyAPI")
public class MemberSecurityBuyerController {

    @Autowired
    private MemberSecurityManager memberSecurityManager;
    @Autowired
    private CaptchaClient captchaClient;
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private SmsClient smsClient;
    @Autowired
    private Cache cache;
    @Autowired
    private ShopflyConfig shopflyConfig;

    @PostMapping(value = "/security/send")
    @ApiOperation(value = "Send the mobile phone verification code")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuidUnique identifier of the client", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "Image verification code", required = true, dataType = "String", paramType = "query")
    })
    public String sendValSmsCode(@NotEmpty(message = "uuidCant be empty") String uuid, @NotEmpty(message = "图片验证码Cant be empty") String captcha) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.VALIDATE_MOBILE.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect");
        }
        Buyer buyer = UserContext.getBuyer();
        Member member = memberManager.getModel(buyer.getUid());
        if (member == null || StringUtil.isEmpty(member.getMobile())) {
            throw new ServiceException(MemberErrorCode.E114.code(), "Current member does not bind mobile phone number");
        }
        // Clear image verification code information
        captchaClient.deleteCode(uuid, captcha, SceneType.VALIDATE_MOBILE.name());
        memberSecurityManager.sendValidateSmsCode(member.getMobile());
        // Returns the validity time of the verification code for front-end warning
        return shopflyConfig.getSmscodeTimout() / 60 + "";
    }


    @PostMapping(value = "/security/bind/send/{mobile}")
    @ApiOperation(value = "Send the verification code of the bound mobile phone")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuidUnique identifier of the client", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "Image verification code", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "Mobile phone number", required = true, dataType = "String", paramType = "path")
    })
    public String sendBindSmsCode(@NotEmpty(message = "uuidCant be empty") String uuid, @NotEmpty(message = "图片验证码Cant be empty") String captcha, @PathVariable("mobile") String mobile) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.BIND_MOBILE.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect");
        }
        // Clear the image verification code information
        captchaClient.deleteCode(uuid, captcha, SceneType.BIND_MOBILE.name());
        // The bound mobile phone number is sent
        memberSecurityManager.sendBindSmsCode(mobile);
        return null;
    }

    @PutMapping("/security/bind/{mobile}")
    @ApiOperation(value = "Binding mobile Phone NumbersAPI")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "Mobile phone no.", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sms_code", value = "Mobile verification code", required = true, dataType = "String", paramType = "query"),
    })
    public String bindMobile(@PathVariable String mobile, @ApiIgnore @NotEmpty(message = "The SMS verification code cannot be empty") String smsCode) {
        boolean isPass = smsClient.valid(SceneType.BIND_MOBILE.name(), mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The SMS verification code is incorrect");
        }
        memberSecurityManager.bindMobile(mobile);
        return null;
    }

    @GetMapping(value = "/security/exchange-bind")
    @ApiOperation(value = "Authentication Indicates the binding verification code")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sms_code", value = "captcha", required = true, dataType = "String", paramType = "query")
    })
    public String checkExchangeBindCode(@Valid @ApiIgnore @NotEmpty(message = "The verification code cannot be empty") String smsCode) {
        return this.valSmsCode(smsCode);

    }

    /**
     * Verify the mobile phone verification code
     *
     * @param code captcha
     * @return
     */
    private String valSmsCode(String code) {
        Buyer buyer = UserContext.getBuyer();
        Member member = memberManager.getModel(buyer.getUid());
        if (member == null || StringUtil.isEmpty(member.getMobile())) {
            throw new ServiceException(MemberErrorCode.E114.code(), "Current member does not bind mobile phone number");
        }
        boolean isPass = smsClient.valid(SceneType.VALIDATE_MOBILE.name(), member.getMobile(), code);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The SMS verification code is incorrect");
        }
        return null;
    }


    @PutMapping("/security/exchange-bind/{mobile}")
    @ApiOperation(value = "Change your cell phone numberAPI")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "Mobile phone no.", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sms_code", value = "Mobile verification code", required = true, dataType = "String", paramType = "query"),
    })
    public String exchangeBindMobile(@PathVariable String mobile, @ApiIgnore @NotEmpty(message = "The SMS verification code cannot be empty") String smsCode) {
        boolean isPass = smsClient.valid(SceneType.BIND_MOBILE.name(), mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The SMS verification code is incorrect");
        }
        memberSecurityManager.changeBindMobile(mobile);
        return null;
    }

    @GetMapping(value = "/security/password")
    @ApiOperation(value = "Verification Modifies the password verification code")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sms_code", value = "captcha", required = true, dataType = "String", paramType = "query")
    })
    public String checkUpdatePwdCode(@Valid @ApiIgnore @NotEmpty(message = "The verification code cannot be empty") String smsCode) {
        return this.valSmsCode(smsCode);

    }


    @PutMapping(value = "/security/password")
    @ApiOperation(value = "Change the password")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuidUnique identifier of the client", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "Image verification code", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "Password", required = true, dataType = "String", paramType = "query")
    })
    public String updatePassword(@NotEmpty(message = "uuidCant be empty") String uuid, @NotEmpty(message = "图片验证码Cant be empty") String captcha, @NotEmpty(message = "密码Cant be empty") String password) {
        // Verification code
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.MODIFY_PASSWORD.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect");
        }
        Buyer buyer = UserContext.getBuyer();
        Member member = memberManager.getModel(buyer.getUid());
        if(!StringUtil.isEmpty(member.getMobile())){
            String str = StringUtil.toString(cache.get(CachePrefix.MOBILE_VALIDATE.getPrefix() + SceneType.VALIDATE_MOBILE.name() + "_" + member.getMobile()));
            if (StringUtil.isEmpty(str)) {
                throw new ServiceException(MemberErrorCode.E115.code(), "Verify the identity of the current user");
            }
        }
        memberSecurityManager.updatePassword(buyer.getUid(), password);
        // Clear the image verification code
        captchaClient.deleteCode(uuid, captcha, SceneType.MODIFY_PASSWORD.name());
        return null;
    }


}
