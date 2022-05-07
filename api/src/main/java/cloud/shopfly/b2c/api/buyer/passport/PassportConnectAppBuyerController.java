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
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: appTrusted login correlationAPI
 * @date 2018/11/6 10:28
 * @since v7.0.0
 */
@Api(description = "appTrusted login correlationAPI")
@RestController
@RequestMapping("/passport")
@Validated
public class PassportConnectAppBuyerController {

    @Autowired
    private ConnectManager connectManager;
    @Autowired
    private SmsClient smsClient;
    @Autowired
    private CaptchaClient captchaClient;
    @Autowired
    private MemberManager memberManager;

    @ApiOperation(value = "To obtainappParameters required for federated login")
    @GetMapping("/connect/app/{type}/param")
    @ApiImplicitParam(name = "type", value = "Login type", required = true, dataType = "String", paramType = "path", allowableValues = "QQ,WEIBO,WECHAT,ALIPAY")
    public String getParam(@PathVariable("type") @ApiIgnore String type) {
        return connectManager.getParam(type);
    }

    @ApiOperation(value = "detectionopenidWhether the binding")
    @GetMapping("/connect/app/{type}/openid")
    @ApiImplicitParam(name = "openid", value = "openid", required = true, dataType = "String", paramType = "query")
    public Map checkOpenid(@PathVariable("type") @ApiIgnore String type, @ApiIgnore String openid) {
        return connectManager.checkOpenid(type, openid);
    }

    @ApiOperation(value = "APPObtain alipay login authorizationSDK")
    @GetMapping("/login-binder/ali/info")
    public String getAppInfoStr() {
        return connectManager.getAliInfo();
    }


    @ApiOperation(value = "appSMS login binding")
    @PostMapping("/sms-binder/app")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "Third party flatopenid", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "The login mode is optional：qq、weixin、weibo、alipay", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "Mobile phone number", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sms_code", value = "SMS code", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "A unique identifier", required = true, dataType = "String", paramType = "query"),
    })
    public Map smsBinder(String openid, String type, String mobile, String smsCode, String uuid) {

        // Verify the mobile phone verification code
        boolean isPass = smsClient.valid(SceneType.VALIDATE_MOBILE.name(), mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The SMS verification code is incorrect");
        }

        // Verify membership exists
        Member member = memberManager.getMemberByMobile(mobile);
        // Check whether the current member exists
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E123.code(), "Current member does not exist");
        }

        return connectManager.appBind(member, openid, type, uuid);
    }

    @ApiOperation(value = "appUser name and password login binding")
    @PostMapping("/login-binder/app")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "Third party flatopenid", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "The login mode is optional：qq、weixin、weibo、alipay", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "Username", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "Password", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "Image verification code", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "A unique identifier", required = true, dataType = "String", paramType = "query"),
    })
    public Map loginBinder(String openid, String type, String username, String password, String captcha, String uuid) {

        // Verify the image verification code
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect！");
        }

        // Verify membership exists
        Member member = memberManager.validation(username, password);

        return connectManager.appBind(member, openid, type, uuid);
    }

    @ApiOperation(value = "appRegistered bindingapi")
    @PostMapping("/register-binder/app")
    public Map registerBinder(String openid, String type, String mobile, String captcha,String password, String uuid) {


        return null;
    }

}
