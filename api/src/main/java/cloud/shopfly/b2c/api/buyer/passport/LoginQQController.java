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
