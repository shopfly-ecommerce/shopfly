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

import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.client.system.CaptchaClient;
import cloud.shopfly.b2c.core.client.system.SmsClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.vo.MemberVO;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.passport.service.PassportManager;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;


/**
 * 会员登录注册API
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018年3月23日 上午10:12:12
 */
@RestController
@RequestMapping("/passport")
@Api(description = "会员登录API")
@Validated
public class PassportLoginBuyerController {

    @Autowired
    private PassportManager passportManager;
    @Autowired
    private CaptchaClient captchaClient;
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private SmsClient smsClient;
    @Autowired
    private ShopflyConfig shopflyConfig;

    @PostMapping(value = "/login/smscode/{mobile}")
    @ApiOperation(value = "发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid客户端的唯一标识", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "图片验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "String", paramType = "path"),
    })
    public String sendSmsCode(@NotEmpty(message = "uuid不能为空") String uuid, @NotEmpty(message = "图片验证码不能为空") String captcha, @PathVariable("mobile") String mobile) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "图片验证码不正确！");
        }
        passportManager.sendLoginSmsCode(mobile);
        //清清除图片验证码信息
        captchaClient.deleteCode(uuid, captcha, SceneType.LOGIN.name());
        return shopflyConfig.getSmscodeTimout() / 60 + "";
    }

    @GetMapping("/login")
    @ApiOperation(value = "用户名（手机号）/密码登录API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "客户端唯一标识", required = true, dataType = "String", paramType = "query"),
    })
    public MemberVO login(@NotEmpty(message = "用户名不能为空") String username, @NotEmpty(message = "密码不能为空") String password, @NotEmpty(message = "图片验证码不能为空") String captcha, @NotEmpty(message = "uuid不能为空") String uuid) {
        //验证图片验证码是否正确
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "图片验证码错误！");
        }
        //校验账号信息是否正确
        return memberManager.login(username, password);
    }


    @GetMapping("/login/{mobile}")
    @ApiOperation(value = "手机号码登录API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sms_code", value = "手机验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "客户端唯一标识", required = true, dataType = "String", paramType = "query")
    })
    public MemberVO mobileLogin(@PathVariable String mobile, @ApiIgnore @NotEmpty(message = "短信验证码不能为空") String smsCode) {
        boolean isPass = smsClient.valid(SceneType.LOGIN.name(), mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "短信验证码错误！");
        }
        return memberManager.login(mobile);
    }

    @GetMapping("/app/login/{mobile}")
    @ApiOperation(value = "APP手机号码登录API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sms_code", value = "手机验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "客户端唯一标识", required = true, dataType = "String", paramType = "query")
    })
    public MemberVO appMobileLogin(@PathVariable String mobile, @ApiIgnore @NotEmpty(message = "短信验证码不能为空") String smsCode) {
        boolean isPass = smsClient.valid(SceneType.LOGIN.name(), mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "短信验证码错误！");
        }
        return memberManager.appLogin(mobile);
    }
}
