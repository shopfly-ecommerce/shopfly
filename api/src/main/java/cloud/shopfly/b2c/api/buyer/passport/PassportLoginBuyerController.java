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
 * Member login and registrationAPI
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018years3month23The morning of10:12:12
 */
@RestController
@RequestMapping("/passport")
@Api(description = "Member loginAPI")
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
    @ApiOperation(value = "Send verification code")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuidUnique identifier of the client", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "Image verification code", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "Mobile phone number", required = true, dataType = "String", paramType = "path"),
    })
    public String sendSmsCode(@NotEmpty(message = "uuidCant be empty") String uuid, @NotEmpty(message = "图片验证码Cant be empty") String captcha, @PathVariable("mobile") String mobile) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect！");
        }
        passportManager.sendLoginSmsCode(mobile);
        // Clear image verification code information
        captchaClient.deleteCode(uuid, captcha, SceneType.LOGIN.name());
        return shopflyConfig.getSmscodeTimout() / 60 + "";
    }

    @GetMapping("/login")
    @ApiOperation(value = "Username（Mobile phone no.）/Password to loginAPI")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "Username", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "Password", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "captcha", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "Unique identifier of the client", required = true, dataType = "String", paramType = "query"),
    })
    public MemberVO login(@NotEmpty(message = "The user name cannot be empty") String username, @NotEmpty(message = "The password cannot be empty") String password, @NotEmpty(message = "The image verification code cannot be empty") String captcha, @NotEmpty(message = "uuidCant be empty") String uuid) {
        // Verify that the image verification code is correct
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect！");
        }
        // Verify that account information is correct
        return memberManager.login(username, password);
    }


    @GetMapping("/login/{mobile}")
    @ApiOperation(value = "Login by Mobile NumberAPI")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "Mobile phone no.", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sms_code", value = "Mobile verification code", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "Unique identifier of the client", required = true, dataType = "String", paramType = "query")
    })
    public MemberVO mobileLogin(@PathVariable String mobile, @ApiIgnore @NotEmpty(message = "The SMS verification code cannot be empty") String smsCode) {
        boolean isPass = smsClient.valid(SceneType.LOGIN.name(), mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The SMS verification code is incorrect！");
        }
        return memberManager.login(mobile);
    }

    @GetMapping("/app/login/{mobile}")
    @ApiOperation(value = "APPLogin by Mobile NumberAPI")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "Mobile phone no.", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sms_code", value = "Mobile verification code", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "Unique identifier of the client", required = true, dataType = "String", paramType = "query")
    })
    public MemberVO appMobileLogin(@PathVariable String mobile, @ApiIgnore @NotEmpty(message = "The SMS verification code cannot be empty") String smsCode) {
        boolean isPass = smsClient.valid(SceneType.LOGIN.name(), mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The SMS verification code is incorrect！");
        }
        return memberManager.appLogin(mobile);
    }
}
