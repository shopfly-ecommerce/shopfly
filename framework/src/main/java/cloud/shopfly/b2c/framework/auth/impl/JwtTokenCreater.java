/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * Jwt token 创建实现
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

        //默认session失效时间为1小时：60秒 x 60 (=1分钟) * 60 (=1小时)
        refreshTokenExp = 60 * 60 * 60;
    }

    @Override
    public Token create(AuthUser user) {

        ObjectMapper oMapper = new ObjectMapper();

        Map buyerMap = oMapper.convertValue(user, HashMap.class);

        //使用Calendar计算时间的加减，以免出现精度问题
        Calendar cal = Calendar.getInstance();

        //设置为当期日期
        cal.setTime(new Date());

        //由当前日期加上访问令牌的有效时长即为访问令牌失效时间
        cal.add(Calendar.SECOND,accessTokenExp);
        String accessToken = Jwts.builder()
                .setClaims(buyerMap)
                .setSubject("user")
                .setExpiration( cal.getTime() )
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();

        //由当前日期加上刷新令牌的有效时长即为刷新令牌失效时间
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
