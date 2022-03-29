/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.security.impl;

import com.enation.app.javashop.framework.JavashopConfig;
import com.enation.app.javashop.framework.auth.AuthUser;
import com.enation.app.javashop.framework.auth.Token;
import com.enation.app.javashop.framework.auth.TokenParseException;
import com.enation.app.javashop.framework.auth.impl.JwtTokenCreater;
import com.enation.app.javashop.framework.auth.impl.JwtTokenParser;
import com.enation.app.javashop.framework.security.TokenManager;
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
    private JavashopConfig javashopConfig;

    @Override
    public Token create(AuthUser user) {
        JwtTokenCreater tokenCreater = new JwtTokenCreater(javashopConfig.getTokenSecret());
        tokenCreater.setAccessTokenExp(javashopConfig.getAccessTokenTimeout());
        tokenCreater.setRefreshTokenExp(javashopConfig.getRefreshTokenTimeout());
        return tokenCreater.create(user);

    }

    @Override
    public <T> T parse(Class<T> clz, String token) throws TokenParseException {
        JwtTokenParser tokenParser = new JwtTokenParser(javashopConfig.getTokenSecret());
        return tokenParser.parse(clz, token);
    }

    @Override
    public Token create(AuthUser user, Integer tokenOutTime, Integer refreshTokenOutTime) {
        JwtTokenCreater tokenCreater = new JwtTokenCreater(javashopConfig.getTokenSecret());
        if (null == tokenOutTime){
            tokenCreater.setAccessTokenExp(javashopConfig.getAccessTokenTimeout());
        }else{
            tokenCreater.setAccessTokenExp(tokenOutTime);
        }
        if (null == refreshTokenOutTime){
            tokenCreater.setRefreshTokenExp(javashopConfig.getRefreshTokenTimeout());
        }else{
            tokenCreater.setRefreshTokenExp(refreshTokenOutTime);
        }
        return tokenCreater.create(user);

    }
}
