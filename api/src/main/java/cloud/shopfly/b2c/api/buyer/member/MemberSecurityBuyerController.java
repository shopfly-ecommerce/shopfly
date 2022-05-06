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
 * 会员安全控制器
 *
 * @author zh
 * @version v7.0
 * @date 18/4/23 下午3:30
 * @since v7.0
 */
@RestController
@RequestMapping("/members")
@Validated
@Api(description = "会员安全API")
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
    @ApiOperation(value = "发送手机验证验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid客户端的唯一标识", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "图片验证码", required = true, dataType = "String", paramType = "query")
    })
    public String sendValSmsCode(@NotEmpty(message = "uuid不能为空") String uuid, @NotEmpty(message = "图片验证码不能为空") String captcha) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.VALIDATE_MOBILE.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "图片验证码不正确");
        }
        Buyer buyer = UserContext.getBuyer();
        Member member = memberManager.getModel(buyer.getUid());
        if (member == null || StringUtil.isEmpty(member.getMobile())) {
            throw new ServiceException(MemberErrorCode.E114.code(), "当前会员未绑定手机号");
        }
        //清清除图片验证码信息
        captchaClient.deleteCode(uuid, captcha, SceneType.VALIDATE_MOBILE.name());
        memberSecurityManager.sendValidateSmsCode(member.getMobile());
        //将验证码失效时间返回，用于前端提示
        return shopflyConfig.getSmscodeTimout() / 60 + "";
    }


    @PostMapping(value = "/security/bind/send/{mobile}")
    @ApiOperation(value = "发送绑定手机验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid客户端的唯一标识", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "图片验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "String", paramType = "path")
    })
    public String sendBindSmsCode(@NotEmpty(message = "uuid不能为空") String uuid, @NotEmpty(message = "图片验证码不能为空") String captcha, @PathVariable("mobile") String mobile) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.BIND_MOBILE.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "图片验证码不正确");
        }
        //清除除图片验证码信息
        captchaClient.deleteCode(uuid, captcha, SceneType.BIND_MOBILE.name());
        //发送绑定手机号码
        memberSecurityManager.sendBindSmsCode(mobile);
        return null;
    }

    @PutMapping("/security/bind/{mobile}")
    @ApiOperation(value = "手机号码绑定API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sms_code", value = "手机验证码", required = true, dataType = "String", paramType = "query"),
    })
    public String bindMobile(@PathVariable String mobile, @ApiIgnore @NotEmpty(message = "短信验证码不能为空") String smsCode) {
        boolean isPass = smsClient.valid(SceneType.BIND_MOBILE.name(), mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "短信验证码错误");
        }
        memberSecurityManager.bindMobile(mobile);
        return null;
    }

    @GetMapping(value = "/security/exchange-bind")
    @ApiOperation(value = "验证换绑验证验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sms_code", value = "验证码", required = true, dataType = "String", paramType = "query")
    })
    public String checkExchangeBindCode(@Valid @ApiIgnore @NotEmpty(message = "验证码不能为空") String smsCode) {
        return this.valSmsCode(smsCode);

    }

    /**
     * 验证手机验证码
     *
     * @param code 验证码
     * @return
     */
    private String valSmsCode(String code) {
        Buyer buyer = UserContext.getBuyer();
        Member member = memberManager.getModel(buyer.getUid());
        if (member == null || StringUtil.isEmpty(member.getMobile())) {
            throw new ServiceException(MemberErrorCode.E114.code(), "当前会员未绑定手机号");
        }
        boolean isPass = smsClient.valid(SceneType.VALIDATE_MOBILE.name(), member.getMobile(), code);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "短信验证码不正确");
        }
        return null;
    }


    @PutMapping("/security/exchange-bind/{mobile}")
    @ApiOperation(value = "手机号码换绑API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sms_code", value = "手机验证码", required = true, dataType = "String", paramType = "query"),
    })
    public String exchangeBindMobile(@PathVariable String mobile, @ApiIgnore @NotEmpty(message = "短信验证码不能为空") String smsCode) {
        boolean isPass = smsClient.valid(SceneType.BIND_MOBILE.name(), mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "短信验证码错误");
        }
        memberSecurityManager.changeBindMobile(mobile);
        return null;
    }

    @GetMapping(value = "/security/password")
    @ApiOperation(value = "验证修改密码验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sms_code", value = "验证码", required = true, dataType = "String", paramType = "query")
    })
    public String checkUpdatePwdCode(@Valid @ApiIgnore @NotEmpty(message = "验证码不能为空") String smsCode) {
        return this.valSmsCode(smsCode);

    }


    @PutMapping(value = "/security/password")
    @ApiOperation(value = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid客户端的唯一标识", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "图片验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query")
    })
    public String updatePassword(@NotEmpty(message = "uuid不能为空") String uuid, @NotEmpty(message = "图片验证码不能为空") String captcha, @NotEmpty(message = "密码不能为空") String password) {
        //校验验证码
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.MODIFY_PASSWORD.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "图片验证码不正确");
        }
        Buyer buyer = UserContext.getBuyer();
        Member member = memberManager.getModel(buyer.getUid());
        if(!StringUtil.isEmpty(member.getMobile())){
            String str = StringUtil.toString(cache.get(CachePrefix.MOBILE_VALIDATE.getPrefix() + SceneType.VALIDATE_MOBILE.name() + "_" + member.getMobile()));
            if (StringUtil.isEmpty(str)) {
                throw new ServiceException(MemberErrorCode.E115.code(), "请先对当前用户进行身份校验");
            }
        }
        memberSecurityManager.updatePassword(buyer.getUid(), password);
        //清除图片验证码
        captchaClient.deleteCode(uuid, captcha, SceneType.MODIFY_PASSWORD.name());
        return null;
    }


}
