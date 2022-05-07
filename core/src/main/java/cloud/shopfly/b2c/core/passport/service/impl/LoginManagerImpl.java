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
import cloud.shopfly.b2c.core.member.model.dto.LoginUserDTO;
import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.model.vo.Auth2Token;
import cloud.shopfly.b2c.core.member.model.vo.MemberLoginMsg;
import cloud.shopfly.b2c.core.member.model.vo.MemberVO;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.passport.service.LoginManager;
import cn.hutool.core.lang.UUID;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.message.MemberRegisterMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.framework.ShopflyConfig;
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
import cloud.shopfly.b2c.framework.util.EmojiCharacterUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.util.TokenKeyGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Three-party login service
 * @author cs
 * 2020/11/02
 */
@Service
public class LoginManagerImpl implements LoginManager {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    
    private DaoSupport memberDaoSupport;

    @Autowired
    private ShopflyConfig shopflyConfig;

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private Cache cache;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private TokenManager tokenManager;


    /**
     * According to theUnionIdlanding
     * @param loginUserDTO
     * @return
     */
    @Override
    public Map loginByUnionId(LoginUserDTO loginUserDTO){
        Map res = new HashMap(16);
        // Find members by unionID (ES_CONNECT table)
        ConnectDO connectDO= findMemberByUnionId(loginUserDTO.getUnionid(),loginUserDTO.getUnionType());
        Member member = null;
        if(connectDO==null){
            // Couldnt find one registered
            member = register(loginUserDTO);
        }else{
            member=findMemberById(connectDO.getMemberId());
            // Check whether the openID of the current login terminal account is recorded. If not, add a record
            if (loginUserDTO.getOpenType()!=null){
                ConnectDO aDo = getConnect(member.getMemberId(),loginUserDTO.getOpenType());
                if (aDo==null){
                    ConnectDO connect = new ConnectDO();
                    connect.setMemberId(member.getMemberId());
                    connect.setUnionId(loginUserDTO.getOpenid());
                    connect.setUnionType(loginUserDTO.getOpenType().value());
                    memberDaoSupport.insert("es_connect",connect);
                }else if(StringUtil.isEmpty(aDo.getUnionId())){
                    aDo.setUnionId(loginUserDTO.getOpenid());
                    aDo.setUnboundTime(null);
                    memberDaoSupport.update(aDo,aDo.getId());
                }
            }
        }
        // Stores the relationship between uUID and unionId
        Auth2Token auth2Token = new Auth2Token();
        if (null == loginUserDTO.getOpenType()){
            auth2Token.setType(loginUserDTO.getUnionType().value());
        }else{
            auth2Token.setType(loginUserDTO.getOpenType().value());
        }
        auth2Token.setUnionid(loginUserDTO.getUnionid());
        if (!StringUtil.isEmpty(loginUserDTO.getOpenid())){
            auth2Token.setOpneId(loginUserDTO.getOpenid());
        }
        if(logger.isDebugEnabled()){
            logger.debug("The three parties loginopenIdfor：" + loginUserDTO.getOpenid()+";unionidfor"+loginUserDTO.getUnionid());
        }
        cache.put(CachePrefix.CONNECT_LOGIN.getPrefix() + loginUserDTO.getUuid(), auth2Token);
        MemberVO memberVO = this.connectWeChatLoginHandle(member, loginUserDTO.getUuid(),loginUserDTO.getTokenOutTime(),loginUserDTO.getRefreshTokenOutTime());
        res.put("access_token", memberVO.getAccessToken());
        res.put("refresh_token", memberVO.getRefreshToken());
        res.put("uid", memberVO.getUid());
        return res;
    }

    /**
     * According to theunionIdThe queryconnect
     * @param memberId
     * @param unionType
     * @return
     */
    private ConnectDO getConnect(Integer memberId, ConnectTypeEnum unionType) {
        String sql = "select * from es_connect where member_id = ? and union_type = ?";
        List<ConnectDO> connectDOList = memberDaoSupport.queryForList(sql, ConnectDO.class, memberId, unionType.value());
        if (null == connectDOList || connectDOList.size()==0){
            return null;
        }
        return connectDOList.get(0);
    }

    /**
     * According to theunionIdThe queryconnect
     * @param unionId
     * @param unionType
     * @return
     */
    private ConnectDO findMemberByUnionId(String unionId, ConnectTypeEnum unionType) {
        String sql = "select * from es_connect where union_id = ? and union_type = ?";
        List<ConnectDO> connectDOList = memberDaoSupport.queryForList(sql, ConnectDO.class, unionId, unionType.value());
        if (null == connectDOList || connectDOList.size()==0){
            return null;
        }
        return connectDOList.get(0);
    }

    /**
     * Register
     * @param loginUserDTO
     * @return
     */
    private Member  register(LoginUserDTO loginUserDTO) {
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
        member.setSex(loginUserDTO.getSex());
        long dateline = DateUtil.getDateline();
        member.setCreateTime(dateline);
        member.setFace(loginUserDTO.getHeadimgurl());
        member.setRegisterIp(ThreadContextHolder.getHttpRequest().getRemoteAddr());
        member.setLoginCount(1);
        member.setLastLogin(dateline);
        member.setDisabled(0);
        String  nickname = loginUserDTO.getNickName();
        nickname = StringUtil.isEmpty(nickname)?username: EmojiCharacterUtil.encode(nickname);
        member.setNickname(nickname);
        member.setCountry(loginUserDTO.getCountry());
        member.setCity(loginUserDTO.getCity());
        // Set the membership level score to 0
        member.setGradePoint(0);
        // Set member consumption points to 0
        member.setConsumPoint(0);
        // Set whether the member has completed the personal information 0: no, 1: yes
        member.setInfoFull(0);
        memberDaoSupport.insert("es_member",member);
        member.setMemberId( memberDaoSupport.getLastId("es_member"));
        addConnect(loginUserDTO, member);
        // The organization data structure sends membership registration messages
        MemberRegisterMsg memberRegisterMsg = new MemberRegisterMsg();
        memberRegisterMsg.setMember(member);
        memberRegisterMsg.setUuid(ThreadContextHolder.getHttpRequest().getHeader("uuid"));
        this.messageSender.send(new MqMessage(AmqpExchange.MEMEBER_REGISTER, AmqpExchange.MEMEBER_REGISTER + "_ROUTING", memberRegisterMsg));
        return member;
    }

    private void addConnect(LoginUserDTO loginUserDTO, Member member) {

        // Write UnionId
        ConnectDO connect = new ConnectDO();
        connect.setMemberId(member.getMemberId());
        connect.setUnionType(loginUserDTO.getUnionType().value());
        connect.setUnionId(loginUserDTO.getUnionid());
        memberDaoSupport.insert("es_connect",connect);
        if (!StringUtil.isEmpty(loginUserDTO.getOpenid())){

            // Write the openId
            connect = new ConnectDO();
            connect.setMemberId(member.getMemberId());
            connect.setUnionType(loginUserDTO.getOpenType().value());
            connect.setUnionId(loginUserDTO.getOpenid());
            memberDaoSupport.insert("es_connect",connect);
        }
    }

    /**
     * According to the useridQuerying User Information
     * @param memberId
     * @return
     */
    private Member findMemberById(Integer memberId) {
        Member member = memberManager.getModel(memberId);
        return member;
    }

    /**
     * generatememberthetoken
     *
     * @param member
     * @param uuid
     * @return
     */
    private MemberVO convertWechatMember(Member member, String uuid,Integer tokenOutTime,Integer refreshTokenOutTime) {
        // Verify that the current account is disabled
        if (!member.getDisabled().equals(0)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The current account is disabled. Contact the administrator");
        }

        if (null == tokenOutTime){
            tokenOutTime = shopflyConfig.getAccessTokenTimeout();
        }
        if (null == refreshTokenOutTime){
            refreshTokenOutTime = shopflyConfig.getRefreshTokenTimeout();
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
        cache.put(TokenKeyGenerate.generateBuyerAccessToken(uuid, member.getMemberId()), accessToken, tokenOutTime + 60);
        cache.put(TokenKeyGenerate.generateBuyerRefreshToken(uuid, member.getMemberId()), refreshTokenOutTime + 60);
        return memberVO;
    }


    public MemberVO connectWeChatLoginHandle(Member member, String uuid,Integer tokenOutTime,Integer refreshTokenOutTime) {
        // Initialize member information
        MemberVO memberVO = this.convertWechatMember(member, uuid,tokenOutTime,refreshTokenOutTime);
        // Sending a Login message
        this.sendMessage(member);
        return memberVO;
    }

    /**
     * Sending a Login message
     *
     * @param member
     */
    private void sendMessage(Member member) {
        MemberLoginMsg loginMsg = new MemberLoginMsg();
        loginMsg.setLastLoginTime(member.getLastLogin());
        loginMsg.setMemberId(member.getMemberId());
        this.messageSender.send(new MqMessage(AmqpExchange.MEMEBER_LOGIN, AmqpExchange.MEMEBER_LOGIN + "_ROUTING", loginMsg));
    }

}
