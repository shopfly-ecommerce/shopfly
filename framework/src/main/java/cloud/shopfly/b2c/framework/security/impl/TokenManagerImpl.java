/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.security.impl;

import cloud.shopfly.b2c.framework.ShopflixConfig;
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
    private ShopflixConfig shopflixConfig;

    @Override
    public Token create(AuthUser user) {
        JwtTokenCreater tokenCreater = new JwtTokenCreater(shopflixConfig.getTokenSecret());
        tokenCreater.setAccessTokenExp(shopflixConfig.getAccessTokenTimeout());
        tokenCreater.setRefreshTokenExp(shopflixConfig.getRefreshTokenTimeout());
        return tokenCreater.create(user);

    }

    @Override
    public <T> T parse(Class<T> clz, String token) throws TokenParseException {
        JwtTokenParser tokenParser = new JwtTokenParser(shopflixConfig.getTokenSecret());
        return tokenParser.parse(clz, token);
    }

    @Override
    public Token create(AuthUser user, Integer tokenOutTime, Integer refreshTokenOutTime) {
        JwtTokenCreater tokenCreater = new JwtTokenCreater(shopflixConfig.getTokenSecret());
        if (null == tokenOutTime){
            tokenCreater.setAccessTokenExp(shopflixConfig.getAccessTokenTimeout());
        }else{
            tokenCreater.setAccessTokenExp(tokenOutTime);
        }
        if (null == refreshTokenOutTime){
            tokenCreater.setRefreshTokenExp(shopflixConfig.getRefreshTokenTimeout());
        }else{
            tokenCreater.setRefreshTokenExp(refreshTokenOutTime);
        }
        return tokenCreater.create(user);

    }
}
