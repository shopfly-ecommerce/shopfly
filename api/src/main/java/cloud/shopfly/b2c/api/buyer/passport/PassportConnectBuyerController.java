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

import cloud.shopfly.b2c.core.base.DomainHelper;
import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.client.system.CaptchaClient;
import cloud.shopfly.b2c.core.client.system.SmsClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.enums.ConnectPortEnum;
import cloud.shopfly.b2c.core.member.model.vo.MemberVO;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

/**
 * @author zjp
 * @version v7.0
 * @Description Trust the loginapi
 * @ClassName ConnectController
 * @since v7.0 In the morning11:13 2018/6/6
 */
@Api(description = "Trust the loginAPI")
@RestController
@RequestMapping("/passport")
@Validated
public class PassportConnectBuyerController {

    @Autowired
    private CaptchaClient captchaClient;

    @Autowired
    private ConnectManager connectManager;

    @Autowired
    private SmsClient smsClient;

    @Autowired
    private DomainHelper domainHelper;

    @Autowired
    private Debugger debugger;


    /**
     * Trust the login binding page
     */
    private static String binder = "/binder";

    /**
     * Trust login to the redirect page
     */

    private static String index = "";

    /**
     * logging
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/connect/wechat/auth")
    @ApiOperation(value = "Wechat initiated authorization")
    public String getWechatAuth() throws IOException {
        String ua = ThreadContextHolder.getHttpRequest().getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") > -1) {
            connectManager.wechatAuth();
        }
        return "";
    }

    @GetMapping("/connect/wechat/auth/back")
    @ApiOperation(value = "Wechat initiates an authorization callback")
    public String wechatAuthCallBack() throws IOException {
        String ua = ThreadContextHolder.getHttpRequest().getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") > -1) {
            connectManager.wechatAuthCallBack();
        }
        return "";
    }

    @GetMapping("/connect/wechat/login")
    @ApiOperation(value = "Automatic loginapi")
    @ApiImplicitParam(name = "uuid", value = "Unique identifier of the client", required = true, dataType = "String", paramType = "path")
    public Map wechatAuthLogin(@NotEmpty(message = "uuidCant be empty") String uuid) throws IOException {
        String ua = ThreadContextHolder.getHttpRequest().getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") > -1) {
            return connectManager.bindLogin(uuid);
        }
        return null;
    }


    @GetMapping("/connect/pc/{type}")
    @ApiOperation(value = "PCInitiating a trusted login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "Log on to way:QQ,weibo,WeChat,Alipay", allowableValues = "QQ,WEIBO,WECHAT,ALIPAY", paramType = "path")
    })
    public void pcInitiate(@PathVariable("type") @ApiIgnore String type) throws IOException {
        String port = ConnectPortEnum.PC.name();

        connectManager.initiate(type, port, null);

    }

    @GetMapping("/connect/wap/{type}")
    @ApiOperation(value = "WAPInitiating a trusted login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "Log on to way:QQ,weibo,WeChat,Alipay", allowableValues = "QQ,WEIBO,WECHAT,ALIPAY", paramType = "path")
    })
    public void wapInitiate(@PathVariable("type") @ApiIgnore String type) throws IOException {
        String port = ConnectPortEnum.WAP.name();
        connectManager.initiate(type, port, null);
    }

    @ApiOperation(value = "Trusted login unified callback address")
    @GetMapping("/connect/{port}/{type}/callback")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "Login type", required = true, dataType = "String", allowableValues = "QQ,WEIBO,WECHAT,ALIPAY", paramType = "path"),
            @ApiImplicitParam(name = "port", value = "Logging In to a Client", required = true, dataType = "String", allowableValues = "PC,WAP", paramType = "path"),
            @ApiImplicitParam(name = "uid", value = "membersid", required = true, dataType = "Integer", paramType = "query")
    })
    public void callBack(@PathVariable("type") String type, @PathVariable("port") String port, @ApiIgnore Integer uid) {
        try {
            uid = getUidForCookies(uid);
            if (uid != null && uid != 0) {
                bindCallBackMethod(type, uid);
            } else {
                String uuid = UUID.randomUUID().toString();

                debugger.log("generateuuid:");
                debugger.log(uuid);


                MemberVO memberVO = connectManager.callBack(type, null, uuid);

                HttpServletResponse httpResponse = ThreadContextHolder.getHttpResponse();
                // The main domain name
                String main = domainHelper.getTopDomain();
                String buyer = domainHelper.getBuyerDomain();
                // If the site is a WAP site, go to the waP binding page or the home page
                if (StringUtil.isWap()) {
                    buyer = domainHelper.getMobileDomain();
                }
                String redirectUri = buyer + binder + "?uuid=" + uuid;
                // If the member exists, go directly to the home page
                if (memberVO != null) {
                    Cookie accessTokenCookie = new Cookie("access_token", memberVO.getAccessToken());
                    Cookie refreshTokenCookie = new Cookie("refresh_token", memberVO.getRefreshToken());
                    Cookie uidCookie = new Cookie("uid", StringUtil.toString(memberVO.getUid()));
                    accessTokenCookie.setDomain(main);
                    accessTokenCookie.setPath("/");
                    accessTokenCookie.setMaxAge(270);

                    refreshTokenCookie.setDomain(main);
                    refreshTokenCookie.setPath("/");
                    refreshTokenCookie.setMaxAge(270);

                    uidCookie.setDomain(main);
                    uidCookie.setPath("/");
                    uidCookie.setMaxAge(270);

                    httpResponse.addCookie(uidCookie);
                    httpResponse.addCookie(accessTokenCookie);
                    httpResponse.addCookie(refreshTokenCookie);
                    redirectUri = buyer + index + "?uuid=" + uuid;
                }
                // If the member exists, log in to the member and store the UUID and token information in the cookie
                Cookie cookie = new Cookie("uuid_connect", uuid);
                cookie.setDomain(main);
                cookie.setPath("/");
                cookie.setMaxAge(270);
                httpResponse.addCookie(cookie);
                // If no member, jump to the binding page
                httpResponse.sendRedirect(redirectUri);
                return;
            }


        } catch (IOException e) {
            this.logger.error(e.getMessage(), e);
            throw new ServiceException(MemberErrorCode.E131.name(), "Joint login failure");
        }
    }


    @ApiOperation(value = "The member center account is bound to the callback address")
    @GetMapping("/account-binder/{type}/callback")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "Login type", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "uid", value = "membersid", required = true, dataType = "Integer", paramType = "query")
    })
    public void bindCallBack(@PathVariable("type") String type, Integer uid) {
        try {
            // Uid if null reads uid from cookie
            uid = getUidForCookies(uid);
            bindCallBackMethod(type, uid);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new ServiceException(MemberErrorCode.E131.name(), "Joint login failure");
        }
    }


    @ApiOperation(value = "pcLog on to the binding")
    @PutMapping("/login-binder/pc/{uuid_connect}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "Username", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "Password", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "captcha", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "Unique identifier of the client", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid_connect", value = "Unique identifier of the client", required = true, dataType = "String", paramType = "path")
    })
    public Map pcBind(@NotEmpty(message = "The user name cannot be empty") String username, @NotEmpty(message = "The password cannot be empty") String
            password,
                      @NotEmpty(message = "The image verification code cannot be empty") String captcha, @PathVariable("uuid_connect") String
                              uuidConnect, String uuid) {
        // Verify that the image verification code is correct
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect！");
        }
        return connectManager.bind(username, password, uuidConnect, uuid);
    }

    @ApiOperation(value = "WAPSend the mobile verification code")
    @PostMapping("/mobile-binder/sms-code/{mobile}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "Mobile phone no.", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "captcha", value = "captcha", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "Unique identifier of the client", required = true, dataType = "String", paramType = "query"),
    })
    public void smsCode(@NotEmpty(message = "uuidCant be empty") String uuid, @NotEmpty(message = "图片验证码Cant be empty") String
            captcha,
                        @PathVariable("mobile") String mobile) {
        // Verify that the image verification code is correct
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect");
        }
        connectManager.sendCheckMobileSmsCode(mobile);
    }

    @ApiOperation(value = "WAPMobile phone binding")
    @PostMapping("/mobile-binder/{uuid}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "Mobile phone no.", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sms_code", value = "Mobile verification code", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "Unique identifier of the client", required = true, dataType = "String", paramType = "path"),
    })
    public Map mobileBind(@NotEmpty(message = "The cell phone number cannot be empty") String
                                  mobile, @ApiIgnore @NotEmpty(message = "The SMS verification code cannot be empty") String smsCode,
                          @PathVariable("uuid") String uuid) {
        boolean isPass = smsClient.valid(SceneType.VALIDATE_MOBILE.name(), mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The SMS verification code is incorrect");
        }
        return connectManager.mobileBind(mobile, uuid);
    }


    @ApiOperation(value = "WAPLog on to the binding")
    @PostMapping("/login-binder/wap/{uuid}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "Username", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "Password", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "captcha", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "Unique identifier of the client", required = true, dataType = "String", paramType = "path"),
    })
    public Map wapBind(@NotEmpty(message = "The user name cannot be empty") String username, @NotEmpty(message = "The password cannot be empty") String
            password,
                       @NotEmpty(message = "The image verification code cannot be empty") String captcha, @PathVariable("uuid") String uuid) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect！");
        }
        return connectManager.bind(username, password, uuid, uuid);
    }

    private Integer getUidForCookies(@ApiIgnore Integer uid) {
        // Uid if null reads uid from cookie
        if (uid == null) {
            Cookie[] cookies = ThreadContextHolder.getHttpRequest().getCookies();
            for (Cookie cookie : cookies) {
                if ("uid".equals(cookie.getName())) {
                    uid = StringUtil.toInt(cookie.getValue(), false);
                }
            }
        }
        return uid;
    }


    private void bindCallBackMethod(@PathVariable("type") String type, Integer uid) throws IOException {

        String uuid = UUID.randomUUID().toString();
        String redirectUri = domainHelper.getBuyerDomain();
        if (StringUtil.isWap()) {
            redirectUri = domainHelper.getMobileDomain();
        }
        MemberVO memberVO = connectManager.callBack(type, "member", uuid);
        HttpServletResponse httpResponse = ThreadContextHolder.getHttpResponse();
        if (memberVO == null) {
            Map map = connectManager.bind(uuid, uid);
            if ("existed".equals(map.get("result"))) {
                httpResponse.sendRedirect(redirectUri + "/binder-error?message=" + URLEncoder.encode("The current account has been bound to other members", "UTF-8"));
                return;
            }
        }
        httpResponse.sendRedirect(redirectUri + "/member/account-binding");
        return;
    }

}
