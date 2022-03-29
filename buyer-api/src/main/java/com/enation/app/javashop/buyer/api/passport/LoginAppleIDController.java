/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.buyer.api.passport;

import com.enation.app.javashop.core.member.model.dto.AppleIDUserDTO;
import com.enation.app.javashop.core.passport.service.LoginAppleIDManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * IOS-APP端 AppleID登录
 *
 * @author snow
 * @version v1.0
 * @since v7.2.2
 * 2020-12-16
 */
@Api(description = "IOS-APP端 AppleID登录")
@RestController
@RequestMapping("/apple")
@Validated
public class LoginAppleIDController {

    @Autowired
    private LoginAppleIDManager loginAppleIDManager;

    @ApiOperation(value = "APP登陆")
    @PostMapping("/app/login/{uuid}")
    public Map appLogin(@PathVariable String uuid, AppleIDUserDTO appleIDUserDTO){
        System.out.println(uuid);
        System.out.println(appleIDUserDTO.toString());
        return this.loginAppleIDManager.appleIDLogin(uuid,appleIDUserDTO);
    }

}
