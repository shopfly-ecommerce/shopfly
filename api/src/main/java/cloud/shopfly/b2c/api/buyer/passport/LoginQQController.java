/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.buyer.passport;


import cloud.shopfly.b2c.core.member.model.dto.QQUserDTO;
import cloud.shopfly.b2c.core.passport.service.LoginQQManager;
import io.swagger.annotations.Api;
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
@Api(description = "QQ统一登陆")
@RestController
@RequestMapping("/qq")
@Validated
public class LoginQQController {

    @Autowired
    private LoginQQManager loginQQManager;


    @ApiOperation(value = "获取appid")
    @GetMapping("/wap/getAppid")
    public String getLoginUrl(){
        return  loginQQManager.getAppid();
    }


    @ApiOperation(value = "网页登陆")
    @GetMapping("/wap/login")
    public Map h5Login(String access_token,String uuid){
        return loginQQManager.qqWapLogin(access_token,uuid);
    }

    @ApiOperation(value = "app登陆")
    @PostMapping("/app/login/{uuid}")
    public Map appLogin(@PathVariable String uuid, QQUserDTO qqUserDTO){
        return loginQQManager.qqAppLogin(uuid,qqUserDTO);
    }

}
