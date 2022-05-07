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

import cloud.shopfly.b2c.core.member.model.dto.AppleIDUserDTO;
import cloud.shopfly.b2c.core.passport.service.LoginAppleIDManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * IOS-APPendAppleIDSign in
 *
 * @author snow
 * @version v1.0
 * @since v7.2.2
 * 2020-12-16
 */
@Api(description = "IOS-APPendAppleIDSign in")
@RestController
@RequestMapping("/apple")
@Validated
public class LoginAppleIDController {

    @Autowired
    private LoginAppleIDManager loginAppleIDManager;

    @ApiOperation(value = "APPlanding")
    @PostMapping("/app/login/{uuid}")
    public Map appLogin(@PathVariable String uuid, AppleIDUserDTO appleIDUserDTO){
        System.out.println(uuid);
        System.out.println(appleIDUserDTO.toString());
        return this.loginAppleIDManager.appleIDLogin(uuid,appleIDUserDTO);
    }

}
