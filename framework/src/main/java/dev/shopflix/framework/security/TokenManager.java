/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.security;

import dev.shopflix.framework.auth.AuthUser;
import dev.shopflix.framework.auth.Token;
import dev.shopflix.framework.auth.TokenParseException;

/**
 * token业务管理接口
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019/12/25
 */
public interface TokenManager {


    /**
     * 创建token
     * @param user
     * @return
     */
    Token create(AuthUser user);

    /**
     * 解析token
     * @param token
     * @return 用户对象
     */
    <T>  T parse(Class<T> clz, String token) throws TokenParseException;


    /**
     * 创建token
     * @param user
     * @param tokenOutTime token超时时间
     * @param refreshTokenOutTime  refreshToken超时时间
     * @return
     */
    Token create(AuthUser user, Integer tokenOutTime, Integer refreshTokenOutTime);

}
