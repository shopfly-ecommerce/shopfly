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

import java.util.Map;

/**
 * 支付宝登陆相关接口
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-10-30
 */
public interface LoginAlipayManager {


    /**
     * 获取wap登陆跳转地址
     * @param redirectUri
     * @return
     */
    String getLoginUrl(String redirectUri);


    /**
     * wap登陆
     * @param code
     * @param uuid
     * @return
     */
    Map wapLogin(String code, String uuid);
}
