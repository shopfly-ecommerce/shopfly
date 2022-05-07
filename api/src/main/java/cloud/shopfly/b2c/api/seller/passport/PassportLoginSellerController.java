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
package cloud.shopfly.b2c.api.seller.passport;

import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.client.system.CaptchaClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.system.model.vo.AdminLoginVO;
import cloud.shopfly.b2c.core.system.service.AdminUserManager;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;


/**
 * Merchant loginAPI
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018years3month23The morning of10:12:12
 */
@RestController
@RequestMapping("/seller")
@Api(description = "Merchant loginAPI")
@Validated
public class PassportLoginSellerController {

    @Autowired
    private CaptchaClient captchaClient;

    @Autowired
    private AdminUserManager adminUserManager;



    @GetMapping("/login")
    @ApiOperation(value = "Username（Mobile phone no.）/Password to loginAPI")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "Username", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "Password", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "captcha", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "Unique identifier of the client", required = true, dataType = "String", paramType = "query"),
    })
    public AdminLoginVO login(@NotEmpty(message = "The user name cannot be empty") String username, @NotEmpty(message = "The password cannot be empty") String password, @NotEmpty(message = "The image verification code cannot be empty") String captcha, @NotEmpty(message = "uuidCant be empty") String uuid) {
        // Verify that the image verification code is correct
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.LOGIN.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect");
        }
        return adminUserManager.login(username, password);

    }

    @ApiOperation(value = "The refreshtoken")
    @PostMapping("/check/token")
    @ApiImplicitParam(name = "refresh_token", value = "The refreshtoken", required = true, dataType = "String", paramType = "query")
    public String refreshToken(@ApiIgnore @NotEmpty(message = "The refreshtokenCant be empty") String refreshToken) {
        try {
            return adminUserManager.exchangeToken(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new ServiceException(MemberErrorCode.E109.code(), "The currenttokenHave failed");
        }
    }
}
