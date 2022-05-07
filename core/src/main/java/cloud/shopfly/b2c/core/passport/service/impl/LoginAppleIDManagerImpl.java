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
package cloud.shopfly.b2c.core.passport.service.impl;


import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.ConnectDO;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dto.AppleIDUserDTO;
import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.model.vo.Auth2Token;
import cloud.shopfly.b2c.core.member.model.vo.MemberLoginMsg;
import cloud.shopfly.b2c.core.member.model.vo.MemberVO;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.passport.service.LoginAppleIDManager;
import cn.hutool.core.lang.UUID;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.message.MemberRegisterMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.framework.auth.Token;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import cloud.shopfly.b2c.framework.security.TokenManager;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AppleIDLogin related interface
 * @author snow
 * @version v1.0
 * @since v7.2.2
 * 2020-12-17
 */
@Service
public class LoginAppleIDManagerImpl implements LoginAppleIDManager {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private ConnectManager connectManager;

    @Autowired
    
    private DaoSupport memberDaoSupport;

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private Cache cache;


    @Autowired
    private TokenManager tokenManager;

    @Override
    public Map appleIDLogin(String uuid, AppleIDUserDTO appleIDUserDTO) {
        return this.loginByUnionId(uuid,appleIDUserDTO,null,null, ConnectTypeEnum.APPLEID);
    }

    /**
     * According to theUnionIdlanding
     * @param
     * @return
     */
    private Map loginByUnionId(String uuid, AppleIDUserDTO userDTO, Integer tokenOutTime, Integer refreshTokenOutTime, ConnectTypeEnum loginType){
        Map res = new HashMap(16);
        // Find members by unionID (ES_CONNECT table)
        ConnectDO connectDO = findMemberByUnionId(userDTO.getOpenid());
        Member member = null;
        if(connectDO==null){
            // Couldnt find one registered
            member = registerBy(userDTO,loginType);
        }else{
            member = findMemberById(connectDO.getMemberId(),userDTO.getOpenid(),loginType);
        }
        // Stores the relationship between uUID and unionId
        Auth2Token auth2Token = new Auth2Token();
        auth2Token.setType(loginType.value());
        auth2Token.setUnionid(userDTO.getOpenid());
        // Openid is used to obtain wechat information when registering binding
        auth2Token.setOpneId(userDTO.getOpenid());
        if (logger.isDebugEnabled()) {
            logger.debug("QQSign inopenIdfor：" + userDTO.getOpenid());
        }
        cache.put(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid, auth2Token);
        MemberVO memberVO = this.connectWeChatLoginHandle(member, uuid,tokenOutTime,refreshTokenOutTime);
        res.put("access_token", memberVO.getAccessToken());
        res.put("refresh_token", memberVO.getRefreshToken());
        res.put("uid", memberVO.getUid());
        return res;
    }

    /**
     * According to theunionIdThe queryconnect
     * @param unionId
     * @return
     */
    private ConnectDO findMemberByUnionId(String unionId) {
        String sql = "select * from es_connect where union_id = ? and union_type = ?";
        List<ConnectDO> connectDOList = memberDaoSupport.queryForList(sql, ConnectDO.class, unionId, ConnectTypeEnum.APPLEID.value());
        if (null == connectDOList || connectDOList.size()==0){
            return null;
        }
        return connectDOList.get(0);
    }

    /**
     * Register
     * @param userDTO
     * @return
     */
    private Member  registerBy(AppleIDUserDTO userDTO,ConnectTypeEnum loginType) {
        String usernamePrefix = "m_";
        String usernameSuffix = UUID.fastUUID().toString(true).substring(0,9);
        String username = usernamePrefix+usernameSuffix;
        Member memberByName = memberManager.getMemberByName(username);
        while (memberByName!=null){
            usernameSuffix = UUID.fastUUID().toString(true).substring(0,9);
            username = usernamePrefix+usernameSuffix;
            memberByName = memberManager.getMemberByName(username);
        }
        Member member = new Member();
        member.setUname(username);
        long dateline = DateUtil.getDateline();
        member.setCreateTime(dateline);
        member.setRegisterIp(ThreadContextHolder.getHttpRequest().getRemoteAddr());
        member.setLoginCount(1);
        member.setLastLogin(dateline);
        member.setDisabled(0);
        member.setNickname(username);
        member.setSex(1);
        // Set the membership level score to 0
        member.setGradePoint(0);
        // Set member consumption points to 0
        member.setConsumPoint(0);
        // Set whether the member has completed the personal information 0: no, 1: yes
        member.setInfoFull(0);
        memberDaoSupport.insert("es_member",member);
        member.setMemberId( memberDaoSupport.getLastId("es_member"));
        addConnect(userDTO, member,loginType);
        // The organization data structure sends membership registration messages
        MemberRegisterMsg memberRegisterMsg = new MemberRegisterMsg();
        memberRegisterMsg.setMember(member);
        memberRegisterMsg.setUuid(ThreadContextHolder.getHttpRequest().getHeader("uuid"));
        this.messageSender.send(new MqMessage(AmqpExchange.MEMEBER_REGISTER, AmqpExchange.MEMEBER_REGISTER + "_ROUTING", memberRegisterMsg));
        return member;
    }

    private void addConnect(AppleIDUserDTO userDTO, Member member, ConnectTypeEnum loginType) {
        // Write UnionId
        ConnectDO connect = new ConnectDO();
        connect.setMemberId(member.getMemberId());
        connect.setUnionType(ConnectTypeEnum.APPLEID.value());
        connect.setUnionId(userDTO.getOpenid());
        memberDaoSupport.insert("es_connect",connect);
        // Write the openId
        connect = new ConnectDO();
        connect.setMemberId(member.getMemberId());
        connect.setUnionType(loginType.value());
        connect.setUnionId(userDTO.getOpenid());
        memberDaoSupport.insert("es_connect",connect);
    }

    /**
     * According to the useridQuerying User Information
     * @param memberId
     * @param openid
     * @param loginType
     * @return
     */
    private Member findMemberById(Integer memberId, String openid, ConnectTypeEnum loginType) {
        Member member = memberManager.getModel(memberId);
        // Check whether the openID of the current login terminal account is recorded. If not, add a record
        ConnectDO aDo = connectManager.getConnect(memberId,loginType.value());;
        if (aDo==null){
            ConnectDO connect = new ConnectDO();
            connect.setMemberId(member.getMemberId());
            connect.setUnionId(openid);
            connect.setUnionType(loginType.value());
            memberDaoSupport.insert("es_connect",connect);
        }
        return member;
    }

    public MemberVO connectWeChatLoginHandle(Member member, String uuid,Integer tokenOutTime,Integer refreshTokenOutTime) {
        // Initialize member information
        MemberVO memberVO = this.convertWechatMember(member, uuid,tokenOutTime,refreshTokenOutTime);
        // Sending a Login message
        this.sendMessage(member);
        return memberVO;
    }

    /**
     * generatememberthetoken
     *
     * @param member
     * @param uuid
     * @return
     */
    private MemberVO convertWechatMember(Member member, String uuid,Integer tokenOutTime,Integer refreshTokenOutTime) {
        if (!member.getDisabled().equals(0)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The current account is disabled. Contact the administrator");
        }
        // Create a buyer user role object
        Buyer buyer = new Buyer();
        // Setting a User ID
        buyer.setUid(member.getMemberId());
        // Setting a User Name
        buyer.setUsername(member.getUname());
        // Set the uuid
        buyer.setUuid(uuid);
        // Create a Token
        Token token = tokenManager.create(buyer,tokenOutTime,refreshTokenOutTime);
        // Obtaining an Access Token
        String accessToken = token.getAccessToken();
        // Obtaining the Refresh Token
        String refreshToken = token.getRefreshToken();
        // Organization returns data
        MemberVO memberVO = new MemberVO(member, accessToken, refreshToken);
        return memberVO;
    }

    /**
     * Sending a Login message
     * @param member
     */
    private void sendMessage(Member member) {
        MemberLoginMsg loginMsg = new MemberLoginMsg();
        loginMsg.setLastLoginTime(member.getLastLogin());
        loginMsg.setMemberId(member.getMemberId());
        this.messageSender.send(new MqMessage(AmqpExchange.MEMEBER_LOGIN, AmqpExchange.MEMEBER_LOGIN + "_ROUTING", loginMsg));
    }
}
