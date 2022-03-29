/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.buyer.api.passport;

import com.enation.app.javashop.core.base.SceneType;
import com.enation.app.javashop.core.client.system.CaptchaClient;
import com.enation.app.javashop.core.client.system.SmsClient;
import com.enation.app.javashop.core.member.MemberErrorCode;
import com.enation.app.javashop.core.member.model.dos.Member;
import com.enation.app.javashop.core.member.service.ConnectManager;
import com.enation.app.javashop.core.member.service.MemberManager;
import com.enation.app.javashop.framework.exception.ServiceException;
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
 * @Description: app信任登录相关API
 * @date 2018/11/6 10:28
 * @since v7.0.0
 */
@Api(description = "app信任登录相关API")
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

    @ApiOperation(value = "获取app联合登录所需参数")
    @GetMapping("/connect/app/{type}/param")
    @ApiImplicitParam(name = "type", value = "登录类型", required = true, dataType = "String", paramType = "path", allowableValues = "QQ,WEIBO,WECHAT,ALIPAY")
    public String getParam(@PathVariable("type") @ApiIgnore String type) {
        return connectManager.getParam(type);
    }

    @ApiOperation(value = "检测openid是否绑定")
    @GetMapping("/connect/app/{type}/openid")
    @ApiImplicitParam(name = "openid", value = "openid", required = true, dataType = "String", paramType = "query")
    public Map checkOpenid(@PathVariable("type") @ApiIgnore String type, @ApiIgnore String openid) {
        return connectManager.checkOpenid(type, openid);
    }

    @ApiOperation(value = "APP获取支付宝登录授权SDK")
    @GetMapping("/login-binder/ali/info")
    public String getAppInfoStr() {
        return connectManager.getAliInfo();
    }


    @ApiOperation(value = "app手机短信登录绑定")
    @PostMapping("/sms-binder/app")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "第三方平的openid", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "登录方式，可选有：qq、weixin、weibo、alipay", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sms_code", value = "短信码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "唯一标识", required = true, dataType = "String", paramType = "query"),
    })
    public Map smsBinder(String openid, String type, String mobile, String smsCode, String uuid) {

        //验证手机验证码
        boolean isPass = smsClient.valid(SceneType.VALIDATE_MOBILE.name(), mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "短信验证码错误");
        }

        //校验会员是否存在
        Member member = memberManager.getMemberByMobile(mobile);
        //校验当前会员是否存在
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E123.code(), "当前会员不存在");
        }

        return connectManager.appBind(member, openid, type, uuid);
    }

    @ApiOperation(value = "app用户名密码登录绑定")
    @PostMapping("/login-binder/app")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "第三方平的openid", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "登录方式，可选有：qq、weixin、weibo、alipay", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "图片验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "唯一标识", required = true, dataType = "String", paramType = "query"),
    })
    public Map loginBinder(String openid, String type, String username, String password, String captcha, String uuid) {

        //验证图片验证码
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "图片验证码错误！");
        }

        //校验会员是否存在
        Member member = memberManager.validation(username, password);

        return connectManager.appBind(member, openid, type, uuid);
    }

    @ApiOperation(value = "app注册绑定api")
    @PostMapping("/register-binder/app")
    public Map registerBinder(String openid, String type, String mobile, String captcha,String password, String uuid) {


        return null;
    }

}
