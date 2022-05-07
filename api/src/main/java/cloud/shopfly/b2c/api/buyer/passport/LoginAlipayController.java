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


import cloud.shopfly.b2c.core.passport.service.LoginAlipayManager;
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
 * Alipay unified landing
 *
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-10-30
 */
@Api(description = "Alipay unified landing")
@RestController
@RequestMapping("/alipay")
@Validated
public class LoginAlipayController {


    @Autowired
    private LoginAlipayManager loginAlipayManager;

    @ApiOperation(value = "Gets the authorization page address")
    @ApiImplicitParam(name	= "redirectUri",	value =	"The redirect address is successfully authorized(Need to beurlEncodeThe whole encryption)",	required = true, dataType = "string",	paramType =	"query")
    @GetMapping("/wap/getLoginUrl")
    public String getLoginUrl(@RequestParam("redirectUri") String  redirectUri){
        return  loginAlipayManager.getLoginUrl(redirectUri);
    }

    @ApiOperation(value = "Landing page")
    @GetMapping("/wap/login")
    public Map h5Login(String code, String uuid){
        return loginAlipayManager.wapLogin(code,uuid);
    }

}
