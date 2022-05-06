/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.buyer.passport;


import cloud.shopfly.b2c.core.member.model.dto.LoginAppDTO;
import cloud.shopfly.b2c.core.passport.service.LoginWeiboManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * 微博统一登陆
 *
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-10-30
 */
@Api(description = "微博统一登陆")
@RestController
@RequestMapping("/weibo")
@Validated
public class LoginWeiboController {


    @Autowired
    private LoginWeiboManager loginWeiboManager;

    @ApiOperation(value = "获取授权页地址")
    @ApiImplicitParam(name	= "redirectUri",	value =	"授权成功跳转地址(需要urlEncode整体加密)",	required = true, dataType = "string",	paramType =	"query")
    @GetMapping("/wap/getLoginUrl")
    public String getLoginUrl(@RequestParam("redirectUri") String  redirectUri){
        return  loginWeiboManager.getLoginUrl(redirectUri);
    }

    @ApiOperation(value = "网页登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "code",	value =	"授权登陆返回的code",	required = true, dataType = "string",	paramType =	"query"),
            @ApiImplicitParam(name	= "uuid",	value =	"此次登陆的随机数",	required = true, dataType = "string",	paramType =	"query"),
            @ApiImplicitParam(name	= "redirect_uri",	value =	"授权成功跳转地址(需要urlEncode整体加密)",	required = true, dataType = "string",	paramType =	"query")
    })
    @PostMapping("/wap/login")
    public Map h5Login(@NotEmpty(message = "code不能为空")String code,@NotEmpty(message = "uuid不能为空") String uuid,@NotEmpty(message = "redirect_uri不能为空")String redirect_uri){
        return loginWeiboManager.wapLogin(code,uuid,redirect_uri);
    }

    @ApiOperation(value = "app登陆")
    @PostMapping("/app/login")
    public Map appLogin(LoginAppDTO loginAppDTO){
        return loginWeiboManager.appLogin(loginAppDTO);
    }

}
