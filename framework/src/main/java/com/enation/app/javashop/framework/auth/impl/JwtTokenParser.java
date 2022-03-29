/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.auth.impl;

import com.enation.app.javashop.framework.auth.BeanUtil;
import com.enation.app.javashop.framework.auth.TokenParseException;
import com.enation.app.javashop.framework.auth.TokenParser;
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
