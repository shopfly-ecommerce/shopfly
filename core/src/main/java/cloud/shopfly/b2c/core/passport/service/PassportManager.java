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
package cloud.shopfly.b2c.core.passport.service;


/**
 * 账号管理
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-04-8 11:33:56
 */
public interface PassportManager {
    /**
     * 发送注册短信验证码
     *
     * @param mobile 手机号码
     */
    void sendRegisterSmsCode(String mobile);

    /**
     * 发送找回密码短信验证码
     *
     * @param mobile
     */
    void sendFindPasswordCode(String mobile);

    /**
     * 发送登录短信验证码
     *
     * @param mobile
     */
    void sendLoginSmsCode(String mobile);


    /**
     * 通过refreshToken 获取 accessToken
     *
     * @param refreshToken
     * @returna ccessToken
     */
    String exchangeToken(String refreshToken);

    /**
     * 清除标记缓存
     *
     * @param mobile 手机号码
     * @param scene  业务场景
     */
    void clearSign(String mobile, String scene);


}