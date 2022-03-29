/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.passport;


import dev.shopflix.core.passport.service.LoginAlipayManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 支付宝统一登陆
 *
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-10-30
 */
@Api(description = "支付宝统一登陆")
@RestController
@RequestMapping("/alipay")
@Validated
public class LoginAlipayController {


    @Autowired
    private LoginAlipayManager loginAlipayManager;

    @ApiOperation(value = "获取授权页地址")
    @ApiImplicitParam(name	= "redirectUri",	value =	"授权成功跳转地址(需要urlEncode整体加密)",	required = true, dataType = "string",	paramType =	"query")
    @GetMapping("/wap/getLoginUrl")
    public String getLoginUrl(@RequestParam("redirectUri") String  redirectUri){
        return  loginAlipayManager.getLoginUrl(redirectUri);
    }

    @ApiOperation(value = "网页登陆")
    @GetMapping("/wap/login")
    public Map h5Login(String code, String uuid){
        return loginAlipayManager.wapLogin(code,uuid);
    }

}
