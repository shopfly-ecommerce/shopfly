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
package cloud.shopfly.b2c.framework.security;

import cloud.shopfly.b2c.framework.auth.AuthUser;
import cloud.shopfly.b2c.framework.auth.Token;
import cloud.shopfly.b2c.framework.auth.TokenParseException;

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
