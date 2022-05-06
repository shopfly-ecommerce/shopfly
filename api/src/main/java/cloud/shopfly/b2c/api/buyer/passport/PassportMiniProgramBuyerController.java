/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.buyer.passport;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.plugin.wechat.WechatAbstractConnectLoginPlugin;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.framework.cache.Cache;

import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.validation.annotation.Mobile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Pattern;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 小程序登录接口
 * @date 2018/11/20 14:56
 * @since v7.0.0
 */
@RestController
@RequestMapping("/passport/mini-program")
@Api(description = "小程序登录api")
@Validated
public class PassportMiniProgramBuyerController {

    @Autowired
    public WechatAbstractConnectLoginPlugin wechatAbstractConnectLoginPlugin;

    @Autowired
    public ConnectManager connectManager;

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private Cache cache;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());



    @GetMapping("/auto-login")
    @ApiOperation(value = "小程序自动登录")
    public Map autoLogin(String code, String uuid) {

        //获取sessionkey和openid或者unionid
        String content = wechatAbstractConnectLoginPlugin.miniProgramAutoLogin(code);

        return this.connectManager.miniProgramLogin(content, uuid);
    }

    @GetMapping("/decrypt")
    @ApiOperation(value = "加密数据解密验证")
    public Map decrypt(String code, String encryptedData, String uuid, String iv) {

        return connectManager.decrypt(code, encryptedData, uuid, iv);
    }


    @GetMapping("/code-unlimit")
    @ApiOperation(value = "获取微信小程序码")
    @ApiImplicitParam(name = "goods_id", value = "商品id", required = true, dataType = "int", paramType = "query")
    public String getWXACodeUnlimit(@ApiIgnore int goodsId) {

        String accessTocken = wechatAbstractConnectLoginPlugin.getWXAccessTocken();

        return connectManager.getWXACodeUnlimit(accessTocken, goodsId);
    }

    @PostMapping("/distribution")
    @ApiOperation(value = "存储小程序端分销的上级id")
    @ApiImplicitParam(name = "from", value = "上级会员id加密格式", required = true, dataType = "String",dataTypeClass = String.class, paramType = "query")
    public String distribution(String from, @RequestHeader(required = false) String uuid) {
        if (logger.isDebugEnabled()) {
            logger.debug("==============前台传过来的缓存key:" + from);
            logger.debug("==============前台传过来的uuid:" + uuid);
        }

        if (StringUtil.notEmpty(uuid) && StringUtil.notEmpty(from)) {
            try {
                //从缓存中获取分销合伙人的会员ID
                Object memberId = cache.get(CachePrefix.MEMBER_SU.getPrefix() + from);
                if (logger.isDebugEnabled()) {
                    logger.debug("==============从缓存中获取的会员ID为:" + memberId);
                }
                //如果会员ID不为空
                if (memberId != null) {
                    //将uuid作为key值，再次将会员ID存放至缓存中
                    cache.put(CachePrefix.DISTRIBUTION_UP.getPrefix() + uuid, memberId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        return "";
    }

    @ApiOperation(value = "小程序注册绑定")
    @PostMapping("/register-bind/{uuid}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "唯一标识", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "nick_name", value = "昵称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "face", value = "头像", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),

    })
    public Map binder(@PathVariable("uuid") String uuid, @Length(max = 20, message = "昵称超过长度限制") @ApiIgnore String nickName, String face, Integer sex, @Mobile String mobile, @Pattern(regexp = "[a-fA-F0-9]{32}", message = "密码格式不正确") String password) {
        //执行注册
        Member member = new Member();
        member.setUname("m_" + mobile);
        member.setMobile(mobile);
        member.setPassword(password);
        member.setNickname(nickName);
        member.setFace(face);
        member.setSex(sex);
        memberManager.register(member);
        //执行绑定账号
        Map map = connectManager.mobileBind(mobile, uuid);
        return map;
    }


}
