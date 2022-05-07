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
package cloud.shopfly.b2c.core.passport.service.impl;

import cloud.shopfly.b2c.core.client.system.SmsClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.passport.service.PassportManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.auth.Token;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.TokenManager;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.Validator;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Member account management implementation
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
    private ShopflyConfig shopflyConfig;
    @Autowired
    private TokenManager tokenManager;

    @Override
    public void sendRegisterSmsCode(String mobile) {
        if (!Validator.isMobile(mobile)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The mobile phone number format is incorrect！");
        }
        // Verify membership exists
        Member member = memberManager.getMemberByMobile(mobile);
        if (member != null) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The phone number has been occupied！");
        }
        // Send verification code SMS messages
        smsClient.sendSmsMessage("Register", mobile, SceneType.REGISTER);
    }

    @Override
    public void sendLoginSmsCode(String mobile) {
        if (!Validator.isMobile(mobile)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The mobile phone number format is incorrect！");
        }
        // Verify whether the v member exists
        Member member = memberManager.getMemberByMobile(mobile);
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The phone number is unregistered！");
        }
        // Send verification code SMS messages
        smsClient.sendSmsMessage("Sign in", mobile, SceneType.LOGIN);
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
        throw new ResourceNotFoundException("Current member does not exist");
    }

    @Override
    public void sendFindPasswordCode(String mobile) {
        // Verify membership exists
        Member member = memberManager.getMemberByMobile(mobile);
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The phone number is unregistered");
        }
        smsClient.sendSmsMessage("Retrieve password", mobile, SceneType.VALIDATE_MOBILE);
    }

    @Override
    public void clearSign(String mobile, String scene) {
        cache.remove(CachePrefix.MOBILE_VALIDATE.getPrefix() + scene + "_" + mobile);
    }

    /**
     * checkrefresh tokenThe validity of
     * @param buyer
     */
    private void validRefreshToken(Buyer buyer) {
        // Obtain the user according to the UID, and obtain whether the current member is buyer or Seller
        Member member = this.memberManager.getModel(buyer.getUid());

        if (member == null) {
            throw new ServiceException(MemberErrorCode.E109.code(), "The currenttokenHave failed[Membership does not exist]");
        }
        // If the member token is invalid when the member token is refreshed, no new token is issued
        if (member.getDisabled() == -1) {
            throw new ServiceException(MemberErrorCode.E109.code(), "The currenttokenHave failed");
        }
    }

    /**
     * Set up therefresh token  Return the dataMap
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

