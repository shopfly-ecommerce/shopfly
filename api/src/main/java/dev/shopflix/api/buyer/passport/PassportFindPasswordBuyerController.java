/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.passport;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.base.SceneType;
import dev.shopflix.core.client.system.CaptchaClient;
import dev.shopflix.core.client.system.SmsClient;
import dev.shopflix.core.member.MemberErrorCode;
import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.core.member.service.MemberManager;
import dev.shopflix.core.member.service.MemberSecurityManager;
import dev.shopflix.core.passport.service.PassportManager;
import dev.shopflix.framework.JavashopConfig;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.StringUtil;
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
 * 会员找回密码api
 *
 * @author zh
 * @version v7.0
 * @date 18/5/16 下午4:07
 * @since v7.0
 */
@RestController
@RequestMapping("/passport")
@Api(description = "会员找回密码api")
@Validated
public class PassportFindPasswordBuyerController {

    @Autowired
    private CaptchaClient captchaClient;
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private Cache cache;
    @Autowired
    private PassportManager passportManager;
    @Autowired
    private MemberSecurityManager memberSecurityManager;
    @Autowired
    private SmsClient smsClient;
    @Autowired
    private JavashopConfig javashopConfig;


    @ApiOperation(value = "获取账户信息")
    @GetMapping("find-pwd")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid客户端的唯一标识",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "图片验证码",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "account", value = "账户名称",
                    required = true, dataType = "String", paramType = "query"),
    })
    public String getMemberInfo(@NotEmpty(message = "uuid不能为空") String uuid,
                                @NotEmpty(message = "图片验证码不能为空") String captcha,
                                @NotEmpty(message = "账户名称不能为空") String account) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.FIND_PASSWORD.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "图片验证码不正确");
        }
        //对会员状态进行校验
        Member member = memberManager.getMemberByAccount(account);
        if (!member.getDisabled().equals(0)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "当前账号已经禁用，请联系管理员");
        }
        //对获得的会员信息进行处理
        String mobile = member.getMobile();
        mobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        //对用户名的处理
        String name = member.getUname();
        //将数据组织好json格式返回
        uuid = StringUtil.getUUId();
        Map map = new HashMap(16);
        map.put("mobile", mobile);
        map.put("uname", name.substring(0, 1) + "***" + name.substring(name.length() - 1, name.length()));
        map.put("uuid", uuid);
        cache.put(uuid, member, javashopConfig.getSmscodeTimout());
        return JsonUtil.objectToJson(map);

    }

    @PostMapping(value = "/find-pwd/send")
    @ApiOperation(value = "发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid客户端的唯一标识",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "图片验证码",
                    required = true, dataType = "String", paramType = "query")
    })
    public String sendSmsCode(@NotEmpty(message = "uuid不能为空") String uuid,
                              @NotEmpty(message = "图片验证码不能为空") String captcha) {
        boolean isPass = captchaClient.valid(uuid, captcha, SceneType.FIND_PASSWORD.name());
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "图片验证码不正确");
        }
        Member member = (Member) cache.get(uuid);
        if (member != null) {
            passportManager.sendFindPasswordCode(member.getMobile());
            return javashopConfig.getSmscodeTimout() / 60 + "";
        }
        throw new ServiceException(MemberErrorCode.E119.code(), "请先对当前用户进行身份校验");
    }


    @PutMapping(value = "/find-pwd/update-password")
    @ApiOperation(value = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid客户端的唯一标识",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码",
                    required = true, dataType = "String", paramType = "query")
    })
    public String updatePassword(@NotEmpty(message = "uuid不能为空") String uuid, String password) {
        Object o = cache.get(CachePrefix.SMS_VERIFY.getPrefix() + uuid);
        if (o != null) {
            Member member = (Member) cache.get(uuid);
            if (member != null) {
                memberSecurityManager.updatePassword(member.getMemberId(), password);
                return null;
            }
            cache.remove(CachePrefix.SMS_VERIFY.getPrefix() + uuid);
            cache.remove(uuid);
        }
        throw new ServiceException(MemberErrorCode.E119.code(), "请先对当前用户进行身份校验");
    }


    @GetMapping(value = "/find-pwd/valid")
    @ApiOperation(value = "验证找回密码验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid客户端的唯一标识",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sms_code", value = "验证码",
                    required = true, dataType = "String", paramType = "query")
    })
    public String updateCodeCheck(@Valid @ApiIgnore @NotEmpty(message = "验证码不能为空") String smsCode,
                                  @NotEmpty(message = "uuid不能为空") String uuid) {
        Member member = (Member) cache.get(uuid);
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E119.code(), "请先对当前用户进行身份校验");
        }
        if (StringUtil.isEmpty(member.getMobile())) {
            cache.remove(uuid);
            throw new ServiceException(MemberErrorCode.E119.code(), "请先对账户进行手机号码绑定在进行此操作");
        }
        boolean isPass = smsClient.valid(SceneType.VALIDATE_MOBILE.name(), member.getMobile(), smsCode);
        if (!isPass) {
            throw new ServiceException(MemberErrorCode.E107.code(), "短信验证码不正确");
        } else {
            // 通过验证的请求，会存放一分钟。
            cache.put(CachePrefix.SMS_VERIFY.getPrefix() + uuid, " ", 1 * 60);
        }
        return null;
    }


}
