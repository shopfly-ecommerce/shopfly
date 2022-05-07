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


import cloud.shopfly.b2c.core.member.model.dto.WeChatMiniLoginDTO;
import cloud.shopfly.b2c.core.member.model.dto.WeChatUserDTO;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Wechat unified login service
 * @author cs
 * @since v1.0
 * @version 7.2.2
 * 2020/09/24
 */
public interface LoginWeChatManager {


    /**
     * Obtained authorized loginurl
     *
     * @return
     */
    String getLoginUrl(String redirectUri, String uuid);


    /**
     * To obtainaccesstoken
     *
     * @param code
     * @return
     */
    JSONObject getAccessToken(String code);


    /**
     * To obtainunionid
     * @param code WeChath5Authorized to returncode
     * @return
     */
    Map wxWapLogin(String code, String uuid, String oldUuid);

    /**
     * unipp applanding
     * @param weChatUserDTO
     * @return
     */
    Map wxAppLogin(String uuid, WeChatUserDTO weChatUserDTO);

    /**
     * Applets login
     * @param weChatMiniLoginDTO
     * @return
     */
    Map miniLogin(WeChatMiniLoginDTO weChatMiniLoginDTO);

    /**
     * To get the appletscode
     * @param code
     * @return
     */
    String getMiniOpenid(String code);

    /**
     * Wechat mini program binding mobile phone number
     * @param
     * @return
     */
    Map miniBindPhone(String encryptedData, String iv);

}
