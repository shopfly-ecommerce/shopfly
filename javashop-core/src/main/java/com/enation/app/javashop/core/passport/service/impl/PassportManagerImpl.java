/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.passport.service.impl;

import com.enation.app.javashop.core.base.CachePrefix;
import com.enation.app.javashop.core.base.SceneType;
import com.enation.app.javashop.core.client.system.SmsClient;
import com.enation.app.javashop.core.member.MemberErrorCode;
import com.enation.app.javashop.core.member.model.dos.Member;
import com.enation.app.javashop.core.member.service.MemberManager;
import com.enation.app.javashop.core.passport.service.PassportManager;
import com.enation.app.javashop.framework.JavashopConfig;
import com.enation.app.javashop.framework.auth.Token;
import com.enation.app.javashop.framework.cache.Cache;
import com.enation.app.javashop.framework.exception.ResourceNotFoundException;
import com.enation.app.javashop.framework.exception.ServiceException;
import com.enation.app.javashop.framework.security.TokenManager;
import com.enation.app.javashop.framework.security.model.Buyer;
import com.enation.app.javashop.framework.util.JsonUtil;
import com.enation.app.javashop.framework.util.Validator;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员账号管理实现
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018-04-8 11:33:56
 */
@Service
public class PassportManagerImpl implements PassportManager {

    @Autowired
    private SmsClient smsClient;
    @Autowired
    private Cache cache;
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private PassportManager passportManager;
    @Autowired
    private JavashopConfig javashopConfig;
    @Autowired
    private TokenManager tokenManager;

    @Override
    public void sendRegisterSmsCode(String mobile) {
        if (!Validator.isMobile(mobile)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "手机号码格式不正确！");
        }
        //校验会员是否存在
        Member member = memberManager.getMemberByMobile(mobile);
        if (member != null) {
            throw new ServiceException(MemberErrorCode.E107.code(), "该手机号已经被占用！");
        }
        //发送验证码短信
        smsClient.sendSmsMessage("注册", mobile, SceneType.REGISTER);
    }

    @Override
    public void sendLoginSmsCode(String mobile) {
        if (!Validator.isMobile(mobile)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "手机号码格式不正确！");
        }
        //校验会v员是否存在
        Member member = memberManager.getMemberByMobile(mobile);
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E107.code(), "该手机号未注册！");
        }
        //发送验证码短信
        smsClient.sendSmsMessage("登录", mobile, SceneType.LOGIN);
    }

    @Override
    public String exchangeToken(String refreshToken) throws ExpiredJwtException {
        if (refreshToken != null) {

            Buyer buyer = tokenManager.parse(Buyer.class, refreshToken);
            validRefreshToken(buyer);

            Token token = tokenManager.create(buyer);
            Map map = getRefreshTokenMap(token);
            return JsonUtil.objectToJson(map);

        }
        throw new ResourceNotFoundException("当前会员不存在");
    }

    @Override
    public void sendFindPasswordCode(String mobile) {
        //校验会员是否存在
        Member member = memberManager.getMemberByMobile(mobile);
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E107.code(), "该手机号未注册");
        }
        smsClient.sendSmsMessage("找回密码", mobile, SceneType.VALIDATE_MOBILE);
    }

    @Override
    public void clearSign(String mobile, String scene) {
        cache.remove(CachePrefix.MOBILE_VALIDATE.getPrefix() + scene + "_" + mobile);
    }

    /**
     * 校验refresh token是否有效
     * @param buyer
     */
    private void validRefreshToken(Buyer buyer) {
        //根据uid获取用户,获得当前会员是buyer还是seller
        Member member = this.memberManager.getModel(buyer.getUid());

        if (member == null) {
            throw new ServiceException(MemberErrorCode.E109.code(), "当前token已经失效[会员不存在]");
        }
        //如果会员token刷新时，会员已经失效，则不颁发新的token
        if (member.getDisabled() == -1) {
            throw new ServiceException(MemberErrorCode.E109.code(), "当前token已经失效");
        }
    }

    /**
     * 设置refresh token  返回数据Map
     * @param token
     * @return
     */
    private Map getRefreshTokenMap(Token token) {
        String newAccessToken = token.getAccessToken();
        String newRefreshToken = token.getRefreshToken();

        Map map = new HashMap(16);
        map.put("accessToken", newAccessToken);
        map.put("refreshToken", newRefreshToken);
        return map;
    }
}

