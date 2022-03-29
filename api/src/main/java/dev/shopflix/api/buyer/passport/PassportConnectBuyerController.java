/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.passport;

import dev.shopflix.core.base.DomainHelper;
import dev.shopflix.core.base.SceneType;
import dev.shopflix.core.client.system.CaptchaClient;
import dev.shopflix.core.client.system.SmsClient;
import dev.shopflix.core.member.MemberErrorCode;
import dev.shopflix.core.member.model.enums.ConnectPortEnum;
import dev.shopflix.core.member.model.vo.MemberVO;
import dev.shopflix.core.member.service.ConnectManager;
import dev.shopflix.framework.JavashopConfig;
import dev.shopflix.framework.context.ThreadContextHolder;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.logs.Debugger;
import dev.shopflix.framework.util.StringUtil;
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
 * @Description 信任登录api
 * @ClassName ConnectController
 * @since v7.0 上午11:13 2018/6/6
 */
@Api(description = "信任登录API")
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
    private JavashopConfig javashopConfig;


    @Autowired
    private Debugger debugger;


    /**
     * 信任登录绑定页面
     */
    private static String binder = "/binder";

    /**
     * 信任登录跳转页面
     */

    private static String index = "";

    /**
     * 日志记录
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/connect/wechat/auth")
    @ApiOperation(value = "微信发起授权")
    public String getWechatAuth() throws IOException {
        String ua = ThreadContextHolder.getHttpRequest().getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") > -1) {
            connectManager.wechatAuth();
        }
        return "";
    }

    @GetMapping("/connect/wechat/auth/back")
    @ApiOperation(value = "微信发起授权回调")
    public String wechatAuthCallBack() throws IOException {
        String ua = ThreadContextHolder.getHttpRequest().getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") > -1) {
            connectManager.wechatAuthCallBack();
        }
        return "";
    }

    @GetMapping("/connect/wechat/login")
    @ApiOperation(value = "自动登录api")
    @ApiImplicitParam(name = "uuid", value = "客户端唯一标识", required = true, dataType = "String", paramType = "path")
    public Map wechatAuthLogin(@NotEmpty(message = "uuid不能为空") String uuid) throws IOException {
        String ua = ThreadContextHolder.getHttpRequest().getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") > -1) {
            return connectManager.bindLogin(uuid);
        }
        return null;
    }


    @GetMapping("/connect/pc/{type}")
    @ApiOperation(value = "PC发起信任登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "登录方式:QQ,微博,微信,支付宝", allowableValues = "QQ,WEIBO,WECHAT,ALIPAY", paramType = "path")
    })
    public void pcInitiate(@PathVariable("type") @ApiIgnore String type) throws IOException {
        String port = ConnectPortEnum.PC.name();

        connectManager.initiate(type, port, null);

    }

    @GetMapping("/connect/wap/{type}")
    @ApiOperation(value = "WAP发起信任登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "登录方式:QQ,微博,微信,支付宝", allowableValues = "QQ,WEIBO,WECHAT,ALIPAY", paramType = "path")
    })
    public void wapInitiate(@PathVariable("type") @ApiIgnore String type) throws IOException {
        String port = ConnectPortEnum.WAP.name();
        connectManager.initiate(type, port, null);
    }

    @ApiOperation(value = "信任登录统一回调地址")
    @GetMapping("/connect/{port}/{type}/callback")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "登录类型", required = true, dataType = "String", allowableValues = "QQ,WEIBO,WECHAT,ALIPAY", paramType = "path"),
            @ApiImplicitParam(name = "port", value = "登录客户端", required = true, dataType = "String", allowableValues = "PC,WAP", paramType = "path"),
            @ApiImplicitParam(name = "uid", value = "会员id", required = true, dataType = "Integer", paramType = "query")
    })
    public void callBack(@PathVariable("type") String type, @PathVariable("port") String port, @ApiIgnore Integer uid) {
        try {
            uid = getUidForCookies(uid);
            if (uid != null && uid != 0) {
                bindCallBackMethod(type, uid);
            } else {
                String uuid = UUID.randomUUID().toString();

                debugger.log("生成uuid:");
                debugger.log(uuid);


                MemberVO memberVO = connectManager.callBack(type, null, uuid);

                HttpServletResponse httpResponse = ThreadContextHolder.getHttpResponse();
                //主域名
                String main = domainHelper.getTopDomain();
                String buyer = domainHelper.getBuyerDomain();
                //如果是wap站点，需要跳转到wap对应的绑定页面或者是首页
                if (StringUtil.isWap()) {
                    buyer = domainHelper.getMobileDomain();
                }
                String redirectUri = buyer + binder + "?uuid=" + uuid;
                //如果会员存在则直接跳转到首页
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
                //如果会员存在则登录此会员并将uuid及token信息存入cookie
                Cookie cookie = new Cookie("uuid_connect", uuid);
                cookie.setDomain(main);
                cookie.setPath("/");
                cookie.setMaxAge(270);
                httpResponse.addCookie(cookie);
                //无会员则跳转至绑定页
                httpResponse.sendRedirect(redirectUri);
                return;
            }


        } catch (IOException e) {
            this.logger.error(e.getMessage(), e);
            throw new ServiceException(MemberErrorCode.E131.name(), "联合登录失败");
        }
    }


    @ApiOperation(value = "会员中心账号绑定回调地址")
    @GetMapping("/account-binder/{type}/callback")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "登录类型", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "uid", value = "会员id", required = true, dataType = "Integer", paramType = "query")
    })
    public void bindCallBack(@PathVariable("type") String type, Integer uid) {
        try {
            //uid如果为null从cookie中读取uid
            uid = getUidForCookies(uid);
            bindCallBackMethod(type, uid);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new ServiceException(MemberErrorCode.E131.name(), "联合登录失败");
        }
    }


    @ApiOperation(value = "pc登录绑定")
    @PutMapping("/login-binder/pc/{uuid_connect}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "客户端唯一标识", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid_connect", value = "客户端唯一标识", required = true, dataType = "String", paramType = "path")
    })
    public Map pcBind(@NotEmpty(message = "用户名不能为空") String username, @NotEmpty(message = "密码不能为空") String
            password,
                      @NotEmpty(message = "图片验证码不能为空") String captcha, @PathVariable("uuid_connect") String
                              uuidConnect, String uuid) {
        //验证图片验证码是否正确
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "图片验证码错误！");
        }
        return connectManager.bind(username, password, uuidConnect, uuid);
    }

    @ApiOperation(value = "WAP发送手机验证码")
    @PostMapping("/mobile-binder/sms-code/{mobile}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "客户端唯一标识", required = true, dataType = "String", paramType = "query"),
    })
    public void smsCode(@NotEmpty(message = "uuid不能为空") String uuid, @NotEmpty(message = "图片验证码不能为空") String
            captcha,
                        @PathVariable("mobile") String mobile) {
        //验证图片验证码是否正确
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "图片验证码错误");
        }
        connectManager.sendCheckMobileSmsCode(mobile);
    }

    @ApiOperation(value = "WAP手机绑定")
    @PostMapping("/mobile-binder/{uuid}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sms_code", value = "手机验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "客户端唯一标识", required = true, dataType = "String", paramType = "path"),
    })
    public Map mobileBind(@NotEmpty(message = "手机号不能为空") String
                                  mobile, @ApiIgnore @NotEmpty(message = "短信验证码不能为空") String smsCode,
                          @PathVariable("uuid") String uuid) {
        boolean isPass = smsClient.valid(SceneType.VALIDATE_MOBILE.name(), mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "短信验证码错误");
        }
        return connectManager.mobileBind(mobile, uuid);
    }


    @ApiOperation(value = "WAP登录绑定")
    @PostMapping("/login-binder/wap/{uuid}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "客户端唯一标识", required = true, dataType = "String", paramType = "path"),
    })
    public Map wapBind(@NotEmpty(message = "用户名不能为空") String username, @NotEmpty(message = "密码不能为空") String
            password,
                       @NotEmpty(message = "图片验证码不能为空") String captcha, @PathVariable("uuid") String uuid) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "图片验证码错误！");
        }
        return connectManager.bind(username, password, uuid, uuid);
    }

    private Integer getUidForCookies(@ApiIgnore Integer uid) {
        //uid如果为null从cookie中读取uid
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
                httpResponse.sendRedirect(redirectUri + "/binder-error?message=" + URLEncoder.encode("当前账号已经绑定其他会员", "UTF-8"));
                return;
            }
        }
        httpResponse.sendRedirect(redirectUri + "/member/account-binding");
        return;
    }

}
