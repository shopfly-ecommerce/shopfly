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
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.ConnectDO;
import cloud.shopfly.b2c.core.member.model.dos.ConnectSettingDO;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dto.ConnectSettingDTO;
import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingVO;
import cloud.shopfly.b2c.core.member.model.vo.ConnectVO;
import cloud.shopfly.b2c.core.member.model.vo.MemberVO;
import cloud.shopfly.b2c.core.member.service.impl.AbstractConnectLoginPlugin;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author zjp
 * @version v7.0
 * @Description Trusted login business class
 * @ClassName ConnectManager
 * @since v7.0 In the afternoon8:54 2018/6/6
 */
public interface ConnectManager {

    /**
     * WeChat authorization
     */
    void wechatAuth();

    /**
     * Wechat authorization callback
     */
    void wechatAuthCallBack();

    /**
     * Wechat binding login
     *
     * @param uuid Unique identifier of the client
     * @return
     */
    Map bindLogin(String uuid);

    /**
     * Binding account
     *
     * @param name        Username
     * @param password    Password
     * @param connectUuid Joint loginuuid
     * @param uuid        uuid
     * @return
     */
    Map bind(String name, String password, String connectUuid, String uuid);

    /**
     * Member center binding account
     *
     * @param uuid The only no.
     * @param uid  The userid
     * @return
     */
    Map bind(String uuid, Integer uid);

    /**
     * Initiating a trusted login
     *
     * @param type
     * @param port
     * @param member
     */
    void initiate(String type, String port, String member);

    /**
     * Trusted login callback
     *
     * @param type
     * @param member
     * @param uuid
     * @return
     */
    MemberVO callBack(String type, String member, String uuid);

    /**
     * Register member and bind
     *
     * @param uuid
     */
    void registerBind(String uuid);

    /**
     * Member unbinding
     *
     * @param type Login type
     */
    void unbind(String type);

    /**
     * Member of the bindingopenid
     *
     * @param uuid
     * @return
     */
    Map openidBind(String uuid);


    /**
     * To obtainappParameters required for federated login
     *
     * @param type
     * @return
     */
    String getParam(String type);

    /**
     * detectionopenidWhether the binding
     *
     * @param type
     * @param openid
     * @return
     */
    Map checkOpenid(String type, String openid);

    /**
     * Send the mobile phone verification code
     *
     * @param mobile
     */
    void sendCheckMobileSmsCode(String mobile);

    /**
     * WAPBinding of mobile phone number
     *
     * @param mobile
     * @param uuid
     * @return
     */
    Map mobileBind(String mobile, String uuid);


    /**
     * Get a list of member bindings
     *
     * @return
     */
    List<ConnectVO> get();

    /**
     * Get background trust login parameters
     *
     * @return
     */
    List<ConnectSettingVO> list();

    /**
     * Save trusted login information
     *
     * @param connectSettingDTO
     * @return
     */
    ConnectSettingDTO save(ConnectSettingDTO connectSettingDTO);

    /**
     * Gets authorized login parameters
     *
     * @param type Authorized Login Type
     * @return
     */
    ConnectSettingDO get(String type);

    /**
     * According to thetypeGets the corresponding plug-in class
     *
     * @param type
     * @return
     */
    AbstractConnectLoginPlugin getConnectionLogin(ConnectTypeEnum type);

    /**
     * Wechat exited the unbinding operation
     */
    void wechatOut();

    /**
     * ios APP Third-party login to obtain authorizationurl
     *
     * @return
     */
    String getAliInfo();


    /**
     * appUsers binding
     *
     * @param member
     * @param openid
     * @param type
     * @param uuid
     * @return
     */
    Map appBind(Member member, String openid, String type, String uuid);

    /**
     * Initialize configuration parameters
     *
     * @return
     */
    Map initConnectSetting();

    /**
     * Applets login
     *
     * @param content
     * @param uuid
     * @return
     */
    Map miniProgramLogin(String content, String uuid);

    /**
     * decryption
     *
     * @param code
     * @param encryptedData
     * @param uuid
     * @param iv
     * @return
     */
    Map decrypt(String code, String encryptedData, String uuid, String iv);

    /**
     * Get wechat small program code
     *
     * @param accessTocken
     * @return
     */
    String getWXACodeUnlimit(String accessTocken, int goodsId);

    /**
     * Gets the federated login object
     *
     * @param memberId  membersid
     * @param unionType type
     * @return ConnectDO
     */
    ConnectDO getConnect(Integer memberId, String unionType);

    /**
     * Decryption, access to information
     *
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     */
    JSONObject getUserInfo(String encryptedData, String sessionKey, String iv);
}
