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
package cloud.shopfly.b2c.api.buyer.passport;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.client.system.CaptchaClient;
import cloud.shopfly.b2c.core.client.system.SmsClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.member.service.MemberSecurityManager;
import cloud.shopfly.b2c.core.passport.service.PassportManager;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.JsonUtil;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Member Password Recoveryapi
 *
 * @author zh
 * @version v7.0
 * @date 18/5/16 In the afternoon4:07
 * @since v7.0
 */
@RestController
@RequestMapping("/passport")
@Api(description = "Member Password Recoveryapi")
@Validated
public class PassportFindPasswordBuyerController {

    @Autowired
    private CaptchaClient captchaClient;
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private Cache cache;
    @Autowired
    private PassportManager passportManager;
    @Autowired
    private MemberSecurityManager memberSecurityManager;
    @Autowired
    private SmsClient smsClient;
    @Autowired
    private ShopflyConfig shopflyConfig;


    @ApiOperation(value = "Get account information")
    @GetMapping("find-pwd")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuidUnique identifier of the client",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "Image verification code",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "account", value = "The name of the account",
                    required = true, dataType = "String", paramType = "query"),
    })
    public String getMemberInfo(@NotEmpty(message = "uuidCant be empty") String uuid,
                                @NotEmpty(message = "The image verification code cannot be empty") String captcha,
                                @NotEmpty(message = "The account name cannot be empty") String account) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.FIND_PASSWORD.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect");
        }
        // Verify member status
        Member member = memberManager.getMemberByAccount(account);
        if (!member.getDisabled().equals(0)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The current account is disabled. Contact the administrator");
        }
        // Process the obtained member information
        String mobile = member.getMobile();
        mobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        // Processing of user names
        String name = member.getUname();
        // Organize the data and return it in JSON format
        uuid = StringUtil.getUUId();
        Map map = new HashMap(16);
        map.put("mobile", mobile);
        map.put("uname", name.substring(0, 1) + "***" + name.substring(name.length() - 1, name.length()));
        map.put("uuid", uuid);
        cache.put(uuid, member, shopflyConfig.getSmscodeTimout());
        return JsonUtil.objectToJson(map);

    }

    @PostMapping(value = "/find-pwd/send")
    @ApiOperation(value = "Send verification code")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuidUnique identifier of the client",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "Image verification code",
                    required = true, dataType = "String", paramType = "query")
    })
    public String sendSmsCode(@NotEmpty(message = "uuidCant be empty") String uuid,
                              @NotEmpty(message = "The image verification code cannot be empty") String captcha) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.FIND_PASSWORD.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect");
        }
        Member member = (Member) cache.get(uuid);
        if (member != null) {
            passportManager.sendFindPasswordCode(member.getMobile());
            return shopflyConfig.getSmscodeTimout() / 60 + "";
        }
        throw new ServiceException(MemberErrorCode.E119.code(), "Verify the identity of the current user");
    }


    @PutMapping(value = "/find-pwd/update-password")
    @ApiOperation(value = "Change the password")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuidUnique identifier of the client",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "Password",
                    required = true, dataType = "String", paramType = "query")
    })
    public String updatePassword(@NotEmpty(message = "uuidCant be empty") String uuid, String password) {
        Object o = cache.get(CachePrefix.SMS_VERIFY.getPrefix() + uuid);
        if (o != null) {
            Member member = (Member) cache.get(uuid);
            if (member != null) {
                memberSecurityManager.updatePassword(member.getMemberId(), password);
                return null;
            }
            cache.remove(CachePrefix.SMS_VERIFY.getPrefix() + uuid);
            cache.remove(uuid);
        }
        throw new ServiceException(MemberErrorCode.E119.code(), "Verify the identity of the current user");
    }


    @GetMapping(value = "/find-pwd/valid")
    @ApiOperation(value = "Authentication Retrieve password Verification code")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuidUnique identifier of the client",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sms_code", value = "captcha",
                    required = true, dataType = "String", paramType = "query")
    })
    public String updateCodeCheck(@Valid @ApiIgnore @NotEmpty(message = "The verification code cannot be empty") String smsCode,
                                  @NotEmpty(message = "uuidCant be empty") String uuid) {
        Member member = (Member) cache.get(uuid);
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E119.code(), "Verify the identity of the current user");
        }
        if (StringUtil.isEmpty(member.getMobile())) {
            cache.remove(uuid);
            throw new ServiceException(MemberErrorCode.E119.code(), "Before performing this operation, bind the mobile phone number to the account");
        }
        boolean isPass = smsClient.valid(SceneType.VALIDATE_MOBILE.name(), member.getMobile(), smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The SMS verification code is incorrect");
        } else {
            // Authenticated requests are held for one minute.
            cache.put(CachePrefix.SMS_VERIFY.getPrefix() + uuid, " ", 1 * 60);
        }
        return null;
    }


}
