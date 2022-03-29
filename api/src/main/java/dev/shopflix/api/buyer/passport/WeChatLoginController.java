/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.passport;



import dev.shopflix.core.member.model.dto.WeChatMiniLoginDTO;
import dev.shopflix.core.member.model.dto.WeChatUserDTO;
import dev.shopflix.core.passport.service.LoginWeChatManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 微信统一登陆
 *
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2019-09-22
 */
@Api(description = "微信统一登陆")
@RestController
@RequestMapping("/wechat")
@Validated
public class WeChatLoginController {

    @Autowired
    private LoginWeChatManager loginWeChatManager;

    @ApiOperation(value = "获取授权页地址")
    @ApiImplicitParam(name	= "redirectUri",	value =	"授权成功跳转地址(需要urlEncode整体加密)",	required = true, dataType = "string",	paramType =	"query")
    @GetMapping("/wap/getLoginUrl")
    public String getLoginUrl(@RequestParam("redirectUri") String  redirectUri, @RequestHeader("uuid") String uuid){
        return  loginWeChatManager.getLoginUrl(redirectUri, uuid);
    }

    @ApiOperation(value = "网页登陆")
    @GetMapping("/wap/login")
    public Map h5Login(String code,String uuid,@RequestParam("oldUuid") String oldUuid){
        return loginWeChatManager.wxWapLogin(code,uuid,oldUuid);
    }

    @ApiOperation(value = "app登陆")
    @PostMapping("/app/login/{uuid}")
    public Map appLogin(@PathVariable String uuid, WeChatUserDTO weChatUserDTO){
        return loginWeChatManager.wxAppLogin(uuid,weChatUserDTO);
    }

    @ApiOperation(value = "小程序登陆")
    @PostMapping("/mini/login")
    public Map miniLogin(WeChatMiniLoginDTO weChatMiniLoginDTO){
        return loginWeChatManager.miniLogin(weChatMiniLoginDTO);
    }

    @ApiOperation(value = "小程序绑定手机号")
    @PostMapping("/mini/bind/phone")
    public Map miniBindPhone( String encrypted,String iv){
        return loginWeChatManager.miniBindPhone(encrypted,iv);
    }

}
