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

import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.client.system.CaptchaClient;
import cloud.shopfly.b2c.core.client.system.SmsClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dto.MemberDTO;
import cloud.shopfly.b2c.core.member.model.vo.MemberVO;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.passport.service.PassportManager;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.validation.annotation.Mobile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


/**
 * Member login and registrationAPI
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018years3month23The morning of10:12:12
 */
@RestController
@RequestMapping("/passport")
@Api(description = "Registered membersAPI")
@Validated
public class PassportRegisterBuyerController {

    @Autowired
    private PassportManager passportManager;
    @Autowired
    private CaptchaClient captchaClient;
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private SmsClient smsClient;
    @Autowired
    private ShopflyConfig shopflyConfig;

    @PostMapping(value = "/register/smscode/{mobile}")
    @ApiOperation(value = "Send verification code")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuidUnique identifier of the client", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "Image verification code", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "Mobile phone number", required = true, dataType = "String", paramType = "path"),
    })
    public String smsCode(@NotEmpty(message = "uuidCant be empty") String uuid, @NotEmpty(message = "图片验证码Cant be empty") String captcha, @PathVariable("mobile") String mobile) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.REGISTER.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The image verification code is incorrect！");
        }
        passportManager.sendRegisterSmsCode(mobile);
        // Clear the image verification code information from the cache
        captchaClient.deleteCode(uuid, captcha, SceneType.REGISTER.name());
        return shopflyConfig.getSmscodeTimout() / 60 + "";
    }

    @PostMapping("/register/pc")
    @ApiOperation(value = "PCRegister")
    public MemberVO registerForPC(@Valid MemberDTO memberDTO) {
        boolean bool = smsClient.valid(SceneType.REGISTER.name(), memberDTO.getMobile(), memberDTO.getSmsCode());
        if (!bool) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The SMS verification code is incorrect");
        }
        // Verification of user names
        String username = memberDTO.getUsername();
        if (username.contains("@")) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The value cannot be a user name@And other special characters！");
        }
        Member member = new Member();
        member.setUname(memberDTO.getUsername());
        member.setPassword(memberDTO.getPassword());
        member.setNickname(memberDTO.getUsername());
        member.setMobile(memberDTO.getMobile());
        member.setSex(1);
        member.setRegisterIp(ThreadContextHolder.getHttpRequest().getRemoteAddr());
        // Register
        memberManager.register(member);
        // Sign in
        return memberManager.login(member.getUname(), memberDTO.getPassword());

    }


    @PostMapping("/register/wap")
    @ApiOperation(value = "wapRegister")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "Mobile phone number", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "Password", required = true, dataType = "String", paramType = "query")
    })
    public MemberVO registerForWap(@Mobile String mobile, @Pattern(regexp = "[a-fA-F0-9]{32}", message = "The password format is incorrect") String password) {
        Member member = new Member();
        member.setMobile(mobile);
        member.setSex(1);
        member.setPassword(password);
        member.setUname("m_" + mobile);
        member.setNickname("m_" + mobile);
        member.setRegisterIp(ThreadContextHolder.getHttpRequest().getRemoteAddr());
        // Register
        memberManager.register(member);
        // Access token
        return memberManager.login(member.getUname(), password);
    }

}
