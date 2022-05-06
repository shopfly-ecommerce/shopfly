/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * token管理基于twt的实现
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
