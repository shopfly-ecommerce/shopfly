package cloud.shopfly.b2c.api.buyer.passport;

import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.client.system.CaptchaClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.vo.MemberVO;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * register by email controller
 * @author kingapex
 * @version 1.0
 * @data 2022/4/18 15:00
 **/

@RestController
@RequestMapping("/passport")
@Api(description = "register by email api")
@Validated
public class EmailRegisterController {

    @Autowired
    private CaptchaClient captchaClient;

    @Autowired
    private MemberManager memberManager;


    @PostMapping("/register/email")
    @ApiOperation(value = "email register")
    public MemberVO registerByEmail(String nickname,String email,String password,String captcha,String uuid) {

        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.REGISTER.name());

        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "Incorrect captcha!");
        }

        captchaClient.deleteCode(uuid, captcha, SceneType.REGISTER.name());

        Member member = new Member();
        member.setNickname(nickname);
        member.setPassword(password);
        member.setUname(email);
        member.setSex(1);
        member.setRegisterIp(ThreadContextHolder.getHttpRequest().getRemoteAddr());

        //注册
        memberManager.register(member);
        //登录
        return memberManager.login(member.getUname(),password);
    }
}
