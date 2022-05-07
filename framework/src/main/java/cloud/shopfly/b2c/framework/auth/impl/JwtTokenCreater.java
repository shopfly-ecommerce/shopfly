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
package cloud.shopfly.b2c.framework.auth.impl;

import cloud.shopfly.b2c.framework.auth.AuthUser;
import cloud.shopfly.b2c.framework.auth.Token;
import cloud.shopfly.b2c.framework.auth.TokenCreater;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt token Create the implementation
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-06-21
 */

public class JwtTokenCreater implements TokenCreater {

    private String secret;

    private int accessTokenExp;

    private int refreshTokenExp;

    public JwtTokenCreater(String secret) {

        this.secret = secret;

        accessTokenExp=60*60;

        // The default session expiration time is 1 hour: 60 seconds x 60 (=1 minute) x 60 (=1 hour)
        refreshTokenExp = 60 * 60 * 60;
    }

    @Override
    public Token create(AuthUser user) {

        ObjectMapper oMapper = new ObjectMapper();

        Map buyerMap = oMapper.convertValue(user, HashMap.class);

        // Use Calendar to calculate addition and subtraction to avoid accuracy problems
        Calendar cal = Calendar.getInstance();

        // Set to the current date
        cal.setTime(new Date());

        // The current date plus the access token validity period is the access token expiration time
        cal.add(Calendar.SECOND,accessTokenExp);
        String accessToken = Jwts.builder()
                .setClaims(buyerMap)
                .setSubject("user")
                .setExpiration( cal.getTime() )
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();

        // The refreshing token expiration time is the current date plus the refreshing token expiration time
        cal.add(Calendar.SECOND,refreshTokenExp);
        String refreshToken = Jwts.builder()
                .setClaims(buyerMap)
                .setSubject("user")
                .setExpiration( cal.getTime() )
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();

        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);


        return token;
    }


    public JwtTokenCreater setSecret(String secret) {
        this.secret = secret;
        return  this;
    }

    public JwtTokenCreater setAccessTokenExp(int accessTokenExp) {
        this.accessTokenExp = accessTokenExp;
        return  this;
    }

    public JwtTokenCreater setRefreshTokenExp(int refreshTokenExp) {
        this.refreshTokenExp = refreshTokenExp;
        return  this;
    }
}
