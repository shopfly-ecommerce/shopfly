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

import cloud.shopfly.b2c.framework.auth.BeanUtil;
import cloud.shopfly.b2c.framework.auth.TokenParseException;
import cloud.shopfly.b2c.framework.auth.TokenParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * jwt token解析器
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-06-24
 */

public class JwtTokenParser implements TokenParser {

    private String secret;

    private Claims claims;

    public JwtTokenParser(String secret) {
        this.secret = secret;
    }


    @Override
    public <T> T parse(Class<T> clz, String token) throws TokenParseException {

        try {
            claims
                    = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token).getBody();

            Object obj = claims.get("uid");
            claims.put("uid", Integer.valueOf(obj.toString()));
            T t = BeanUtil.mapToBean(clz, claims);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenParseException(e);
        }

    }

    /**
     * 获取过期时间
     *
     * @return
     */
    public long getExpiration() {
        long tokenDate = getFormatDate(claims.getExpiration().toString());
        return tokenDate;
    }


    private long getFormatDate(String dateFormat) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

            return sdf1.parse(dateFormat).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
