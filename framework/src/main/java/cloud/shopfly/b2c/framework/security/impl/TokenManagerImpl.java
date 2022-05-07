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
package cloud.shopfly.b2c.framework.security.impl;

import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.auth.AuthUser;
import cloud.shopfly.b2c.framework.auth.Token;
import cloud.shopfly.b2c.framework.auth.TokenParseException;
import cloud.shopfly.b2c.framework.auth.impl.JwtTokenCreater;
import cloud.shopfly.b2c.framework.auth.impl.JwtTokenParser;
import cloud.shopfly.b2c.framework.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * tokenManagement based ontwtThe implementation of the
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019/12/25
 */

@Service
public class TokenManagerImpl implements TokenManager {

    @Autowired
    private ShopflyConfig shopflyConfig;

    @Override
    public Token create(AuthUser user) {
        JwtTokenCreater tokenCreater = new JwtTokenCreater(shopflyConfig.getTokenSecret());
        tokenCreater.setAccessTokenExp(shopflyConfig.getAccessTokenTimeout());
        tokenCreater.setRefreshTokenExp(shopflyConfig.getRefreshTokenTimeout());
        return tokenCreater.create(user);

    }

    @Override
    public <T> T parse(Class<T> clz, String token) throws TokenParseException {
        JwtTokenParser tokenParser = new JwtTokenParser(shopflyConfig.getTokenSecret());
        return tokenParser.parse(clz, token);
    }

    @Override
    public Token create(AuthUser user, Integer tokenOutTime, Integer refreshTokenOutTime) {
        JwtTokenCreater tokenCreater = new JwtTokenCreater(shopflyConfig.getTokenSecret());
        if (null == tokenOutTime){
            tokenCreater.setAccessTokenExp(shopflyConfig.getAccessTokenTimeout());
        }else{
            tokenCreater.setAccessTokenExp(tokenOutTime);
        }
        if (null == refreshTokenOutTime){
            tokenCreater.setRefreshTokenExp(shopflyConfig.getRefreshTokenTimeout());
        }else{
            tokenCreater.setRefreshTokenExp(refreshTokenOutTime);
        }
        return tokenCreater.create(user);

    }
}
