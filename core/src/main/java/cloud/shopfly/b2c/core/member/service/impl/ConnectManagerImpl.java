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
package cloud.shopfly.b2c.core.member.service.impl;

import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.ConnectDO;
import cloud.shopfly.b2c.core.member.model.dos.ConnectSettingDO;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dto.ConnectSettingDTO;
import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.model.vo.*;
import cloud.shopfly.b2c.core.member.plugin.alipay.AlipayAbstractConnectLoginPlugin;
import cloud.shopfly.b2c.core.member.plugin.wechat.WechatAbstractConnectLoginPlugin;
import cloud.shopfly.b2c.core.member.plugin.weibo.WeiboAbstractConnectLoginPlugin;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.framework.util.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.DomainHelper;
import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.model.dto.FileDTO;
import cloud.shopfly.b2c.core.base.model.vo.FileVO;
import cloud.shopfly.b2c.core.base.service.FileManager;
import cloud.shopfly.b2c.core.base.service.SmsManager;
import cloud.shopfly.b2c.core.distribution.util.ShortUrlGenerator;
import cloud.shopfly.b2c.core.member.model.vo.*;
import cloud.shopfly.b2c.core.system.model.vo.SiteSetting;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.xfire.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.*;


/**
 * @author zjp
 * @version v7.0
 * @Description Trusted login business class
 * @ClassName ConnectManagerImpl
 * @since v7.0 In the afternoon8:55 2018/6/6
 */
@Service
public class ConnectManagerImpl implements ConnectManager {

    @Autowired

    private DaoSupport memberDaoSupport;

    @Autowired
    private FileManager fileManager;

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private Cache cache;

    @Autowired
    private DomainHelper domainHelper;

    @Autowired
    private ShopflyConfig shopflyConfig;

    @Autowired
    private SettingClient settingClient;

    @Autowired
    private SmsManager smsManager;

    @Autowired
    private WechatAbstractConnectLoginPlugin wechatAbstractConnectLoginPlugin;

    @Autowired
    private cloud.shopfly.b2c.core.member.plugin.qq.QQConnectLoginPlugin QQConnectLoginPlugin;

    @Autowired
    private WeiboAbstractConnectLoginPlugin weiboAbstractConnectLoginPlugin;

    @Autowired
    private AlipayAbstractConnectLoginPlugin alipayAbstractConnectLoginPlugin;

    @Autowired
    private Debugger debugger;

    private static Long time = 2592000L;


    /**
     * logging
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void wechatAuth() {
        try {
            ConnectTypeEnum connectTypeEnum = ConnectTypeEnum.valueOf(ConnectTypeEnum.WECHAT.value());
            AbstractConnectLoginPlugin connectionLogin = this.getConnectionLogin(connectTypeEnum);
            ThreadContextHolder.getHttpResponse().sendRedirect(connectionLogin.getLoginUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map bind(String uuid, Integer uid) {
        // Get the current user information
        Member member = memberManager.getModel(uid);
        // Verify the status of members. Disabled members are not allowed to bind
        if (!member.getDisabled().equals(0)) {
            throw new ServiceException(MemberErrorCode.E124.code(), "Current membership is disabled");
        }
        Map map = new HashMap(4);
        Auth2Token auth2Token = (Auth2Token) cache.get(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid);
        if (auth2Token == null) {
            throw new ServiceException(MemberErrorCode.E133.name(), "redisThe authorization information does not exist");
        }
        String sql = "select * from es_connect where union_id = ? and union_type = ?";
        ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, auth2Token.getUnionid(), auth2Token.getType());
        // If the member authorized login information union_id is not empty, the corresponding prompt will be given to ask whether to change
        if (connectDO != null && !StringUtil.isEmpty(connectDO.getUnionId())) {
            map.put("result", "existed");
        } else {
            // If the member authorization information exists, it will be updated, and if it does not exist, it will be added
            sql = "select * from es_connect where member_id = ? and union_type = ?";
            connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, member.getMemberId(), auth2Token.getType());
            if (connectDO == null) {
                connectDO = new ConnectDO();
                connectDO.setMemberId(member.getMemberId());
                connectDO.setUnionType(auth2Token.getType());
                connectDO.setUnionId(auth2Token.getUnionid());
                connectDO.setUnboundTime(DateUtil.getDateline());
                this.memberDaoSupport.insert(connectDO);
            } else {
                sql = "update es_connect set union_id = ? where id = ?";
                this.memberDaoSupport.execute(sql, auth2Token.getUnionid(), connectDO.getId());
            }
            map.put("result", "bind_success");
        }
        return map;
    }

    @Override
    public void wechatAuthCallBack() {
        try {
            // Cookie validity period: 30 days
            final int maxAge = 30 * 24 * 60 * 60;
            // Uuid generated
            String uuid = UUID.randomUUID().toString();
            // Get the corresponding plug-in by type

            AbstractConnectLoginPlugin connectionLogin = this.getConnectionLogin(ConnectTypeEnum.WECHAT);
            // Obtain wechat authorization information
            Auth2Token auth2Token = (Auth2Token) cache.get(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid);

            if (auth2Token == null) {
                auth2Token = connectionLogin.loginCallback();
                // When you need to test, open this comment and comment out the previous sentence
                //auth2Token = new Auth2Token();
                //auth2Token.setUnionid("unionId123 ");
                //auth2Token.setOpneId("openid123");
                //auth2Token.setAccessToken("accessToken123");
                // auth2Token.setType(ConnectTypeEnum.WECHAT.value());
            }
            // The authorization information is put into the cache, and the cache expires for 30 days
            cache.put(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid, auth2Token, maxAge);
            if (logger.isDebugEnabled()) {
                this.logger.debug(new Date() + " " + uuid + " The authorization information is stored in the cache. The expiration time is30day");
            }
            // Store the tag in a cookie
            Cookie cookie = new Cookie("uuid_connect", uuid);
            cookie.setDomain(domainHelper.getTopDomain());
            cookie.setPath("/");
            cookie.setMaxAge(maxAge);


            Cookie wechatCookie = new Cookie("is_wechat_auth", "1");
            wechatCookie.setDomain(domainHelper.getTopDomain());
            wechatCookie.setPath("/");
            wechatCookie.setMaxAge(maxAge);


            Cookie uuidCookie = new Cookie("uuid", uuid);
            uuidCookie.setDomain(domainHelper.getTopDomain());
            uuidCookie.setPath("/");
            // 30 days
            uuidCookie.setMaxAge(maxAge);

            ThreadContextHolder.getHttpResponse().addCookie(wechatCookie);
            ThreadContextHolder.getHttpResponse().addCookie(cookie);
            ThreadContextHolder.getHttpResponse().addCookie(uuidCookie);
            // Jump to home page
            ThreadContextHolder.getHttpResponse().sendRedirect(domainHelper.getMobileDomain());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Wechat trust login callback error", e);
            }
        }
    }


    @Override
    public Map bindLogin(String uuid) {
        Map map = new HashMap(3);
        Auth2Token auth2Token = (Auth2Token) cache.get(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid);
        if (auth2Token == null) {
            if (logger.isDebugEnabled()) {
                this.logger.debug(new Date() + " " + uuid + " Authorization Information invalid");
            }


            cleanCookie();
            throw new ServiceException(MemberErrorCode.E133.name(), "The authorization has timed out. Please reauthorize it");
        }
        String sql = "select * from es_connect where union_type = ? and  union_id = ? ";
        ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, ConnectTypeEnum.WECHAT.value(), auth2Token.getUnionid());

        // Check whether the third party login information exists, if so, obtain the member information according to memberId
        if (connectDO != null) {
            Member member = memberManager.getModel(connectDO.getMemberId());

            // Check whether the member exists, if so, perform login operation
            if (member != null) {
                MemberVO memberVO = memberManager.connectLoginHandle(member, uuid);
                map.put("access_token", memberVO.getAccessToken());
                map.put("refresh_token", memberVO.getRefreshToken());
                map.put("uid", memberVO.getUid());
            }
        }
        return map;
    }

    @Override
    public void initiate(String type, String port, String member) {
        try {
            ConnectTypeEnum connectTypeEnum = ConnectTypeEnum.valueOf(type);

            AbstractConnectLoginPlugin connectionLogin = this.getConnectionLogin(connectTypeEnum);
            if (connectionLogin == null) {
                throw new ServiceException(MemberErrorCode.E130.name(), "The login mode is not supported");
            }
            debugger.log("According to the type[" + type + "]Call up the login plug-in：[" + connectionLogin + "]");
            String loginUrl = connectionLogin.getLoginUrl();
            debugger.log("jumpurlfor：");
            debugger.log(loginUrl);
            ThreadContextHolder.getHttpResponse().sendRedirect(loginUrl);

        } catch (IOException e) {
            this.logger.error(e.getMessage(), e);
            throw new ServiceException(MemberErrorCode.E131.name(), "Joint login failure");
        }
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberVO callBack(String type, String mem, String uuid) {
        // Get the corresponding plug-in based on the callback, and get the corresponding OpenID
        ConnectTypeEnum connectTypeEnum = ConnectTypeEnum.valueOf(type);
        AbstractConnectLoginPlugin connectionLogin = this.getConnectionLogin(connectTypeEnum);

        debugger.log("Tuning up the plug-in:" + connectionLogin);
        Auth2Token auth2Token = connectionLogin.loginCallback();
        // Check whether any member has been bound according to openID
        String sql = "select * from es_connect where union_type = ? and  union_id = ? ";
        ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, type, auth2Token.getUnionid());

        debugger.log("Getting binding information:" + connectDO);

        Member member = null;
        if (connectDO != null) {
            member = memberManager.getModel(connectDO.getMemberId());
            debugger.log("Get bound membership:" + member);

        }
        // Store information about trusted logins in Redis
        auth2Token.setType(type);
        cache.put(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid, auth2Token, shopflyConfig.getCaptchaTimout());

        debugger.log("willtokenThe information is written to the cache and the timeout period is：" + shopflyConfig.getCaptchaTimout());

        if (logger.isDebugEnabled()) {
            this.logger.debug(new Date() + " " + uuid + " Login authorization. The authorization time is" + shopflyConfig.getCaptchaTimout());
        }
        // If the account is bound in the member center, there is no need to return the new member information
        if ("member".equals(mem)) {
            return null;
        }
        MemberVO memberVO = null;
        if (member != null) {
            debugger.log("Member login operation");
            memberVO = memberManager.connectLoginHandle(member, uuid);
            debugger.log("generatevo:");
            debugger.log(memberVO.toString());

        }
        return memberVO;

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void registerBind(String uuid) {
        Buyer buyer = UserContext.getBuyer();
        Member member = memberManager.getModel(buyer.getUid());

        // Get the data stored in Redis to fill in the membership information and associate the corresponding login id
        Auth2Token auth2Token = (Auth2Token) cache.get(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid);
        if (auth2Token == null) {
            if (logger.isDebugEnabled()) {
                this.logger.debug(new Date() + " " + uuid + " Automatic login authorization could not be found");
            }
            cleanCookie();
            throw new ServiceException(MemberErrorCode.E133.name(), "The authorization has timed out. Please reauthorize it");
        }
        ConnectTypeEnum connectTypeEnum = ConnectTypeEnum.valueOf(auth2Token.getType());
        AbstractConnectLoginPlugin connectionLogin = this.getConnectionLogin(connectTypeEnum);
        // Update member information
        member = connectionLogin.fillInformation(auth2Token, member);
        memberManager.edit(member, buyer.getUid());
        // Organize data to store new person login information
        ConnectDO connectDO = new ConnectDO();
        connectDO.setMemberId(member.getMemberId());
        connectDO.setUnionType(auth2Token.getType());
        connectDO.setUnionId(auth2Token.getUnionid());
        this.memberDaoSupport.insert(connectDO);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void unbind(String type) {
        Integer memberId = UserContext.getBuyer().getUid();

        String sql = "select * from es_connect where member_id = ? and union_type = ?";
        ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, memberId, type);

        if (connectDO == null) {
            throw new ServiceException(MemberErrorCode.E134.name(), "Members are not bound to relevant accounts");
        }
        // Do not unbind again within 30 days
        /*if (DateUtil.getDateline() - (connectDO.getUnboundTime() == null ? 0 : connectDO.getUnboundTime()) < time) {
            throw new ServiceException(MemberErrorCode.E135.name(), "30Do not repeatedly unbind within a day");
        }*/

        sql = "update es_connect set unbound_time = ? , union_id = ? where id = ?";
        this.memberDaoSupport.execute(sql, DateUtil.getDateline(), "", connectDO.getId());
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map openidBind(String uuid) {
        Map map = new HashMap(4);
        Auth2Token auth2Token = (Auth2Token) cache.get(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid);
        if (auth2Token != null) {
            // Check whether this wechat has been bound to other users
            String sql = "select * from es_connect where union_id = ? and union_type = ?";
            ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, auth2Token.getUnionid(), auth2Token.getType());
            if (connectDO != null) {
                throw new ServiceException(MemberErrorCode.E143.code(), "You have bound another user,Please unbind it and try again");
            }
            Member model = memberManager.getModel(UserContext.getBuyer().getUid());
            MemberVO memberVO = memberManager.connectLoginHandle(model, uuid);
            map.put("result", "bind_success");
            map.put("access_token", memberVO.getAccessToken());
            map.put("refresh_token", memberVO.getRefreshToken());
            // Update member openID if there is an update of this data, if there is no add one
            sql = "select * from es_connect where member_id = ? and union_type = ?";
            connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, UserContext.getBuyer().getUid(), auth2Token.getType());
            if (connectDO == null) {
                connectDO = new ConnectDO();
                connectDO.setMemberId(UserContext.getBuyer().getUid());
                connectDO.setUnionId(auth2Token.getUnionid());
                connectDO.setUnionType(auth2Token.getType());
                this.memberDaoSupport.insert(connectDO);
            } else {
                sql = "update es_connect set union_id = ? where id = ?";
                this.memberDaoSupport.execute(sql, auth2Token.getUnionid(), connectDO.getId());
            }
        } else {
            cleanCookie();
            throw new ServiceException(MemberErrorCode.E133.name(), "The authorization has timed out. Please reauthorize it");
        }
        return map;
    }


    @Override
    public String getParam(String type) {
        try {

            Map map = new HashMap(16);
            List<ConnectSettingVO> list = this.list();

            for (ConnectSettingVO connectSettingVO : list) {
                if (connectSettingVO.getType().equals(type)) {
                    List<ConnectSettingParametersVO> configList = connectSettingVO.getClientList();
                    for (ConnectSettingParametersVO connectSettingParametersVO : configList) {
                        List<ConnectSettingConfigItem> lists = connectSettingParametersVO.getConfigList();
                        for (ConnectSettingConfigItem connectSettingConfigItem : lists) {
                            map.put(connectSettingConfigItem.getKey(), connectSettingConfigItem.getValue());
                        }
                    }
                }
            }
            String globalAuthKey = JsonUtil.jsonToObject(settingClient.get(SettingGroup.SITE), SiteSetting.class).getGlobalAuthKey();
            return AESUtil.encrypt(map.toString(), globalAuthKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Map checkOpenid(String type, String openid) {

        String sql = "select * from es_connect where union_type = ? and  union_id = ? ";
        ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, type, openid);
        Member member = null;
        if (connectDO != null) {
            member = memberManager.getModel(connectDO.getMemberId());
        }

        Map map = new HashMap(4);
        if (member != null) {
            // Member login
            MemberVO memberVO = memberManager.loginHandle(member);
            map.put("is_bind", true);
            map.put("access_token", memberVO.getAccessToken());
            map.put("refresh_token", memberVO.getRefreshToken());
            map.put("uid", memberVO.getUid());
        } else {
            map.put("is_bind", false);
        }
        return map;
    }

    @Override
    public void sendCheckMobileSmsCode(String mobile) {
        if (!Validator.isMobile(mobile)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The mobile phone number format is incorrect");
        }
        // Verify membership exists
        Member member = memberManager.getMemberByMobile(mobile);
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E123.code(), "Current member does not exist");
        }
        // Send verification code SMS messages
        smsManager.sendSmsMessage("Verify mobile phone operation", mobile, SceneType.VALIDATE_MOBILE);

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map mobileBind(String mobile, String uuid) {
        // Verify membership exists
        Member member = memberManager.getMemberByMobile(mobile);
        // Check whether the current member exists
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E123.code(), "Current member does not exist");
        }
        return this.binding(member, uuid, uuid);

    }

    @Override
    public List<ConnectVO> get() {
        Buyer buyer = UserContext.getBuyer();
        String sql = "select * from es_connect where member_id = ? ";
        List<ConnectDO> connectDOS = this.memberDaoSupport.queryForList(sql, ConnectDO.class, buyer.getUid());
        List<ConnectVO> list = new ArrayList<>();

        for (ConnectTypeEnum connectTypeEnum : ConnectTypeEnum.values()) {
            ConnectVO connectVO = new ConnectVO();
            connectVO.setUnionType(connectTypeEnum.name());
            connectVO.setIsBind(false);
            for (ConnectDO connectDO : connectDOS) {
                if (connectTypeEnum.name().equals(connectDO.getUnionType())) {
                    connectVO.setUnionType(connectDO.getUnionType());
                    if (!StringUtil.isEmpty(connectDO.getUnionId())) {
                        connectVO.setIsBind(true);
                    }

                }
            }
            list.add(connectVO);
        }
        return list;
    }

    @Override
    public List<ConnectSettingVO> list() {
        String sql = "select * from es_connect_setting ";
        List<ConnectSettingVO> connectSetting = this.memberDaoSupport.queryForList(sql, ConnectSettingVO.class);
        // Gets an existing authorization type
        List<String> list = new ArrayList<>();
        if (connectSetting.size() > 0) {
            for (ConnectSettingVO connectSettingDO : connectSetting) {
                list.add(connectSettingDO.getType());
            }
        } else {
            connectSetting = new ArrayList<>();
        }
        // If no, generate another data
        for (ConnectTypeEnum connectTypeEnum : ConnectTypeEnum.values()) {
            if (!list.contains(connectTypeEnum.value())) {
                AbstractConnectLoginPlugin connectionLogin = this.getConnectionLogin(connectTypeEnum);
                if (null == connectionLogin) {
                    continue;
                }
                ConnectSettingVO connectSettingVO = connectionLogin.assembleConfig();
                connectSetting.add(connectSettingVO);
            }
        }
        return connectSetting;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ConnectSettingDTO save(ConnectSettingDTO connectSettingDTO) {

        ConnectSettingDO connectSettingDO = new ConnectSettingDO();
        connectSettingDO.setType(connectSettingDTO.getType());
        connectSettingDO.setName(connectSettingDTO.getName());
        connectSettingDO.setConfig(JsonUtil.objectToJson(connectSettingDTO.getClientList()));
        String sql = "select * from es_connect_setting where type = ? ";
        ConnectSettingDO connectSetting = this.memberDaoSupport.queryForObject(sql, ConnectSettingDO.class, connectSettingDTO.getType());

        if (connectSetting != null) {
            connectSettingDO.setId(connectSettingDTO.getId());
            this.memberDaoSupport.update(connectSettingDO, connectSettingDO.getId());
        } else {
            this.memberDaoSupport.insert(connectSettingDO);
        }
        return connectSettingDTO;
    }

    @Override
    public ConnectSettingDO get(String type) {
        String sql = "select * from es_connect_setting where type = ? ";
        return this.memberDaoSupport.queryForObject(sql, ConnectSettingDO.class, type);
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map bind(String username, String password, String uuidConnect, String uuid) {
        // Verify the validity of member account password
        Member member = memberManager.validation(username, password);
        return this.binding(member, uuidConnect, uuid);
    }


    /**
     * According to thetypeGets the corresponding plug-in class
     *
     * @param type
     * @return
     */
    @Override
    public AbstractConnectLoginPlugin getConnectionLogin(ConnectTypeEnum type) {

        switch (type) {
            case QQ:
                return QQConnectLoginPlugin;
            case WECHAT:
                return wechatAbstractConnectLoginPlugin;
            case WEIBO:
                return weiboAbstractConnectLoginPlugin;
            case ALIPAY:
                return alipayAbstractConnectLoginPlugin;
            default:
        }
        return null;
    }


    /**
     * Get the corresponding membership according to the login typeopenid Check whether the binding returns the corresponding information
     *
     * @param member
     * @param uuidConnect
     * @param uuid
     * @return
     */
    private Map binding(Member member, String uuidConnect, String uuid) {
        Map map = this.bind(uuidConnect, member.getMemberId());
        if (!"existed".equals(map.get("result"))) {
            // Verify the status of members. Disabled members are not allowed to bind
            MemberVO memberVO = memberManager.connectLoginHandle(member, uuid);
            map.put("access_token", memberVO.getAccessToken());
            map.put("refresh_token", memberVO.getRefreshToken());
            map.put("uid", memberVO.getUid());
        }
        return map;
    }

    @Override
    public void wechatOut() {
        // Remove the binding
        this.unbind(ConnectTypeEnum.WECHAT.value());
        // The cancellation of membership
        memberManager.logout(UserContext.getBuyer().getUid());
    }


    @Override
    public String getAliInfo() {
        // Put parameter values into map
        Map<String, String> alipayMap = new HashMap<>();
        ConnectSettingDO pay = this.get(ConnectTypeEnum.ALIPAY.value());
        ConnectSettingVO alipay = new ConnectSettingVO();
        BeanUtil.copyProperties(pay, alipay);
        List<ConnectSettingParametersVO> clientList = alipay.getClientList();
        for (ConnectSettingParametersVO connectSettingParametersVO : clientList) {
            List<ConnectSettingConfigItem> lists = connectSettingParametersVO.getConfigList();
            for (ConnectSettingConfigItem connectSettingConfigItem : lists) {
                alipayMap.put(connectSettingConfigItem.getKey(), connectSettingConfigItem.getValue());
            }
        }

        // Stitching parameters
        String appId = alipayMap.get("alipay_app_app_id");
        String pid = alipayMap.get("alipay_app_pid");
        String targetId = DateUtil.getDateline() + "" + DateUtil.getDateline() + "" + DateUtil.getDateline() + "00";
        Map<String, String> map = new HashMap<>();
        map.put("apiname", "com.alipay.account.auth");
        map.put("method", "alipay.open.auth.sdk.code.get");
        map.put("app_id", appId);
        map.put("app_name", "mc");
        map.put("biz_type", "openservice");
        map.put("pid", pid);
        map.put("product_id", "APP_FAST_LOGIN");
        map.put("scope", "kuaijie");
        map.put("target_id", targetId);
        map.put("auth_type", "AUTHACCOUNT");
        map.put("sign_type", "RSA2");
        // Gets the url of the parameter that generated sign
        String content = getSignContent(map);
        String sign = "";
        try {
            sign = AlipaySignature.rsaSign(content, alipayMap.get("alipay_app_private_key"), "UTF-8");
            map.put("sign", sign);
            sign = getSignContent(map);
        } catch (Exception e) {
            logger.error("Error generating alipay signature===" + e.getMessage());
        }
        return sign;
    }

    @Override
    public Map appBind(Member member, String openid, String type, String uuid) {
        // Verify the status of members. Disabled members are not allowed to bind
        if (!member.getDisabled().equals(0)) {
            throw new ServiceException(MemberErrorCode.E124.code(), "Current membership is disabled");
        }
        Map map = new HashMap(4);

        String sql = "select * from es_connect where member_id = ? and union_type = ?";
        ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, member.getMemberId(), type);
        MemberVO memberVO = memberManager.connectLoginHandle(member, uuid);
        // The current member is already bound to another account
        if (connectDO != null && !StringUtil.isEmpty(connectDO.getUnionId())) {
            throw new ServiceException(MemberErrorCode.E124.code(), "This account has been bound. Please unbind it before continuing to bind");
        } else {
            // If the member authorization information exists, update it; if it does not exist, add it
            if (connectDO == null) {
                connectDO = new ConnectDO();
                connectDO.setMemberId(member.getMemberId());
                connectDO.setUnionType(type);
                connectDO.setUnionId(openid);
                this.memberDaoSupport.insert(connectDO);
            } else {
                sql = "update es_connect set  union_id = ? where id = ?";
                this.memberDaoSupport.execute(sql, openid, connectDO.getId());
            }
            map.put("access_token", memberVO.getAccessToken());
            map.put("refresh_token", memberVO.getRefreshToken());
            map.put("uid", memberVO.getUid());
        }
        return map;
    }

    /**
     * Initialize configuration parameters
     *
     * @return
     */
    @Override
    public Map initConnectSetting() {
        Map map = new HashMap();
        List<ConnectSettingVO> list = this.list();
        for (ConnectSettingVO connectSettingVO : list) {
            List<ConnectSettingParametersVO> configList = connectSettingVO.getClientList();
            for (ConnectSettingParametersVO connectSettingParametersVO : configList) {
                List<ConnectSettingConfigItem> lists = connectSettingParametersVO.getConfigList();
                for (ConnectSettingConfigItem connectSettingConfigItem : lists) {
                    map.put(connectSettingConfigItem.getKey(), connectSettingConfigItem.getValue());
                }
            }
        }
        debugger.log("To obtain parameters：", map.toString());
        return map;

    }

    @Override
    public Map miniProgramLogin(String content, String uuid) {
        Map res = new HashMap(16);
        JSONObject json = JSONObject.fromObject(content);
        // Failed to obtain the UnionID
        if (json.get("unionid") == null) {
            res.put("autologin", "fail");
            res.put("reson", "unionid_not_found");
            // Storage sessionkey
            String sessionKey = json.getString("session_key");
            cache.put(CachePrefix.SESSION_KEY.getPrefix() + uuid, sessionKey);
            return res;
        }
        String unionId = json.getString("unionid");
        if (content != null) {
            // Stores the relationship between uUID and unionId
            Auth2Token auth2Token = new Auth2Token();
            auth2Token.setType(ConnectTypeEnum.WECHAT.value());
            auth2Token.setUnionid(unionId);
            String openid = json.getString("openid");
            // Openid is used to obtain wechat information when registering binding
            auth2Token.setOpneId(openid);
            if (logger.isDebugEnabled()) {
                logger.debug("Wechat mini program loginopenIdfor：" + openid);
            }
            cache.put(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid, auth2Token);
        }
        // Use unionID to read database binding data
        String sql = "select * from es_connect where union_id = ? and union_type = ?";
        ConnectDO connect = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, unionId, ConnectTypeEnum.WECHAT.value());
        // Unionid read, but no account found
        if (connect == null) {
            res.put("autologin", "fail");
            res.put("reson", "account_not_found");
            return res;
        }
        // If the authentication succeeds, you can log in normally
        Integer memberId = connect.getMemberId();
        Member member = memberManager.getModel(memberId);
        MemberVO memberVO = memberManager.connectLoginHandle(member, uuid);

        res.put("access_token", memberVO.getAccessToken());
        res.put("refresh_token", memberVO.getRefreshToken());
        res.put("uid", memberVO.getUid());
        return res;
    }

    @Override
    public Map decrypt(String code, String encryptedData, String uuid, String iv) {
        Map res = new HashMap(16);

        String sessionKey = (String) cache.get(CachePrefix.SESSION_KEY.getPrefix() + uuid);

        JSONObject userInfo = this.getUserInfo(encryptedData, sessionKey, iv);
        if (userInfo != null) {
            String unionId = (String) userInfo.get("unionId");
            String sql = "select * from es_connect where union_id = ? and union_type = ?";
            ConnectDO connect = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, unionId, ConnectTypeEnum.WECHAT.value());
            // Unionid read, but no account found
            if (connect == null) {
                res.put("autologin", "fail");
                res.put("reson", "account_not_found");

                // Stores the relationship between uUID and unionId
                Auth2Token auth2Token = new Auth2Token();
                auth2Token.setType(ConnectTypeEnum.WECHAT.value());
                auth2Token.setUnionid(unionId);
                String openId = (String) userInfo.get("openId");
                // Openid is used to obtain wechat login information during registration binding
                auth2Token.setOpneId(openId);
                cache.put(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid, auth2Token);
                return res;
            }
            // If the authentication succeeds, you can log in normally
            Integer memberId = connect.getMemberId();
            Member member = memberManager.getModel(memberId);
            MemberVO memberVO = memberManager.connectLoginHandle(member, uuid);

            res.put("access_token", memberVO.getAccessToken());
            res.put("refresh_token", memberVO.getRefreshToken());
            res.put("uid", memberVO.getUid());
        }
        return res;
    }

    @Override
    public String getWXACodeUnlimit(String accessToken, int goodsId) {
        try {
            String imei = "867186032552993";
            Map<String, Object> params = new HashMap<>();
            Buyer buyer = UserContext.getBuyer();
            int memberId = buyer.getUid();

            // Member short link cache key
            String memberSuKey = CachePrefix.MEMBER_SU.getPrefix() + memberId;
            // Retrieves the member short link from the cache
            Object memberSu = cache.get(memberSuKey);
            if (memberSu == null) {
                // Regenerate the member short link
                memberSu = this.getSuCode(memberId+"");
                logger.debug("==============According to the membershipIDThe resulting short connection is:" + memberSu);
                // Put the member short connection in the cache
                cache.put(CachePrefix.MEMBER_SU.getPrefix() + memberId, memberSu);
            }
            // Put the member ID in the cache with the member short link as the key
            String memberIdCacheKey = CachePrefix.MEMBER_SU.getPrefix() + memberSu;
            cache.put(memberIdCacheKey, memberId);

            // Put the short link into the QR code
            params.put("scene", goodsId + "&" + memberSu);
            params.put("page", "goods-module/goods/goods");
            logger.debug(params.toString());
            params.put("width", 430);
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            String body = JSONObject.fromObject(params).toString();
            StringEntity entity = new StringEntity(body);
            entity.setContentType("image/png");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            InputStream inputStream = response.getEntity().getContent();
            String name = imei + ".png";
            FileDTO fileDTO = new FileDTO();
            fileDTO.setStream(inputStream);
            fileDTO.setName(name);
            fileDTO.setExt("png");
            FileVO goods = fileManager.upload(fileDTO, "goods");
            logger.debug("++++++++++++++++++The address of the small program qr code is：++++++++++++" + goods.getUrl());
            return goods.getUrl();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the federated login object
     *
     * @param memberId  membersid
     * @param unionType type
     * @return ConnectDO
     */
    @Override
    public ConnectDO getConnect(Integer memberId, String unionType) {
        String sql = "select * from es_connect where member_id = ? and union_type = ?";
        List<ConnectDO> connectDOList = memberDaoSupport.queryForList(sql, ConnectDO.class, memberId, unionType);
        if (null == connectDOList || connectDOList.size() == 0) {
            return null;
        }
        return connectDOList.get(0);
    }


    /**
     * Decryption, access to information
     *
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     */
    @Override
    public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // Encrypted data
        byte[] dataByte = Base64.decode(encryptedData);
        // Add the secret key
        byte[] keyByte = Base64.decode(sessionKey);
        // The offset
        byte[] ivByte = Base64.decode(iv);
        try {
            // If the key is less than 16 bits, it is replenished
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // Initialize the
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // Initialize the
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Get formattedsignThe parameters of the
     *
     * @param sortedParams generatesignThe parameters of themap
     * @return Organized parametersurl
     */
    public static String getSignContent(Map<String, String> sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;

        for (int i = 0; i < keys.size(); ++i) {
            String key = keys.get(i);
            String value = sortedParams.get(key);
            if (StringUtils.areNotEmpty(key, value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                ++index;
            }
        }

        return content.toString();
    }


    /**
     * clearcookie
     */
    private void cleanCookie() {
        String main = domainHelper.getTopDomain();
        Cookie user = new Cookie("user", "");
        user.setDomain(main);
        user.setPath("/");
        user.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(user);


        Cookie uuid = new Cookie("uuid", "");
        uuid.setDomain(main);
        uuid.setPath("/");
        uuid.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(uuid);

        Cookie uuidConnect = new Cookie("uuid_connect", "");
        uuidConnect.setDomain(main);
        uuidConnect.setPath("/");
        uuidConnect.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(uuidConnect);

        Cookie accessToken = new Cookie("access_token", "");
        accessToken.setDomain(main);
        accessToken.setPath("/");
        accessToken.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(accessToken);

        Cookie refreshToken = new Cookie("refresh_token", "");
        refreshToken.setDomain(main);
        refreshToken.setPath("/");
        refreshToken.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(refreshToken);

        Cookie forward = new Cookie("forward", "");
        forward.setDomain(main);
        forward.setPath("/");
        forward.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(forward);

        Cookie isWechatAuth = new Cookie("is_wechat_auth", "");
        isWechatAuth.setDomain(main);
        isWechatAuth.setPath("/");
        isWechatAuth.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(isWechatAuth);


    }

    /**
     * Gets a unique short connection value
     *
     * @param param parameter
     * @return
     */
    private String getSuCode(String param) {
        String[] suArr = ShortUrlGenerator.getShortUrl(param);
        boolean flag = false;
        String result = "";
        for (String su : suArr) {
            if (cache.get(su) == null) {
                flag = true;
                result = su;
            }
        }

        if (!flag) {
            getSuCode(param);
        }

        return result;
    }

}
