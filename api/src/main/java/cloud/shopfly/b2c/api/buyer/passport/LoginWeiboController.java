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
 * Unified microblog login
 *
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-10-30
 */
@Api(description = "Unified microblog login")
@RestController
@RequestMapping("/weibo")
@Validated
public class LoginWeiboController {


    @Autowired
    private LoginWeiboManager loginWeiboManager;

    @ApiOperation(value = "Gets the authorization page address")
    @ApiImplicitParam(name	= "redirectUri",	value =	"The redirect address is successfully authorized(Need to beurlEncodeThe whole encryption)",	required = true, dataType = "string",	paramType =	"query")
    @GetMapping("/wap/getLoginUrl")
    public String getLoginUrl(@RequestParam("redirectUri") String  redirectUri){
        return  loginWeiboManager.getLoginUrl(redirectUri);
    }

    @ApiOperation(value = "Landing page")
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "code",	value =	"Authorized to log in and returncode",	required = true, dataType = "string",	paramType =	"query"),
            @ApiImplicitParam(name	= "uuid",	value =	"The random number of the login",	required = true, dataType = "string",	paramType =	"query"),
            @ApiImplicitParam(name	= "redirect_uri",	value =	"The redirect address is successfully authorized(Need to beurlEncodeThe whole encryption)",	required = true, dataType = "string",	paramType =	"query")
    })
    @PostMapping("/wap/login")
    public Map h5Login(@NotEmpty(message = "codeCant be empty")String code,@NotEmpty(message = "uuidCant be empty") String uuid,@NotEmpty(message = "redirect_uriCant be empty")String redirect_uri){
        return loginWeiboManager.wapLogin(code,uuid,redirect_uri);
    }

    @ApiOperation(value = "applanding")
    @PostMapping("/app/login")
    public Map appLogin(LoginAppDTO loginAppDTO){
        return loginWeiboManager.appLogin(loginAppDTO);
    }

}
