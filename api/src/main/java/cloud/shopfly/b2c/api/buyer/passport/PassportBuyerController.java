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

import cloud.shopfly.b2c.core.client.system.SmsClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.passport.service.PassportManager;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.Validator;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;


/**
 * Member verification code processing
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018years3month23The morning of10:12:12
 */
@RestController
@RequestMapping("/passport")
@Api(description = "Other Handling of MembersAPI")
@Validated
public class PassportBuyerController {

    @Autowired
    private PassportManager passportManager;
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private SmsClient smsClient;

    @GetMapping(value = "/smscode/{mobile}")
    @ApiOperation(value = "Verify the mobile phone verification code")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scene", value = "Business types", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sms_code", value = "captcha", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "Mobile phone number", required = true, dataType = "String", paramType = "path"),
    })
    public String checkSmsCode(@NotEmpty(message = "Service scenarios cannot be empty") String scene, @PathVariable("mobile") String mobile, @Valid @ApiIgnore @NotEmpty(message = "The verification code cannot be empty") String smsCode) {
        boolean isPass = smsClient.valid(scene, mobile, smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The SMS verification code is incorrect");
        }
        return null;

    }

    @GetMapping("/username/{username}")
    @ApiOperation(value = "The user name is verified repeatedly")
    @ApiImplicitParam(name = "username", value = "Username", required = true, dataType = "String", paramType = "path")
    public String checkUserName(@PathVariable("username") String username) {
        Member member = memberManager.getMemberByName(username);
        Map map = new HashMap(16);
        if (member != null) {
            map.put("exist", true);
            map.put("suggests", memberManager.generateMemberUname(username));
        } else {
            map.put("exist", false);
        }
        return JsonUtil.objectToJson(map);
    }


    @GetMapping("/mobile/{mobile}")
    @ApiOperation(value = "The mobile phone number is checked repeatedly")
    @ApiImplicitParam(name = "mobile", value = "Mobile phone no.", required = true, dataType = "String", paramType = "path")
    public String checkMobile(@PathVariable("mobile") String mobile) {
        boolean isPass = Validator.isMobile(mobile);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The mobile phone number format is incorrect");
        }
        Member member = memberManager.getMemberByMobile(mobile);
        Map map = new HashMap(16);
        if (member != null) {
            map.put("exist", true);
        } else {
            map.put("exist", false);
        }
        return JsonUtil.objectToJson(map);
    }


    @ApiOperation(value = "The refreshtoken")
    @PostMapping("/token")
    @ApiImplicitParam(name = "refresh_token", value = "The refreshtoken", required = true, dataType = "String", paramType = "query")
    public String refreshToken(@ApiIgnore @NotEmpty(message = "The refreshtokenCant be empty") String refreshToken) {
        try {
            return passportManager.exchangeToken(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new ServiceException(MemberErrorCode.E109.code(), "The currenttokenHave failed");
        }
    }
}
