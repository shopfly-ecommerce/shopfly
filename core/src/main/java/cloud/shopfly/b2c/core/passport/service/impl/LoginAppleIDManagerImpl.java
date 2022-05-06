/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * AppleID登陆相关接口
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
     * 根据UnionId登陆
     * @param
     * @return
     */
    private Map loginByUnionId(String uuid, AppleIDUserDTO userDTO, Integer tokenOutTime, Integer refreshTokenOutTime, ConnectTypeEnum loginType){
        Map res = new HashMap(16);
        //通过unionid查找会员(es_connect表)
        ConnectDO connectDO = findMemberByUnionId(userDTO.getOpenid());
        Member member = null;
        if(connectDO==null){
            //没找到注册一个
            member = registerBy(userDTO,loginType);
        }else{
            member = findMemberById(connectDO.getMemberId(),userDTO.getOpenid(),loginType);
        }
        //存储uuid和unionId的关系
        Auth2Token auth2Token = new Auth2Token();
        auth2Token.setType(loginType.value());
        auth2Token.setUnionid(userDTO.getOpenid());
        //openid用于注册绑定时获取登录的微信的信息
        auth2Token.setOpneId(userDTO.getOpenid());
        if (logger.isDebugEnabled()) {
            logger.debug("QQ登录openId为：" + userDTO.getOpenid());
        }
        cache.put(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid, auth2Token);
        MemberVO memberVO = this.connectWeChatLoginHandle(member, uuid,tokenOutTime,refreshTokenOutTime);
        res.put("access_token", memberVO.getAccessToken());
        res.put("refresh_token", memberVO.getRefreshToken());
        res.put("uid", memberVO.getUid());
        return res;
    }

    /**
     * 根据unionId查询connect
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
     * 注册
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
        //设置会员等级积分为0
        member.setGradePoint(0);
        //设置会员消费积分为0
        member.setConsumPoint(0);
        //设置会员是否完善了个人信息 0：否，1：是
        member.setInfoFull(0);
        memberDaoSupport.insert("es_member",member);
        member.setMemberId( memberDaoSupport.getLastId("es_member"));
        addConnect(userDTO, member,loginType);
        //组织数据结构发送会员注册消息
        MemberRegisterMsg memberRegisterMsg = new MemberRegisterMsg();
        memberRegisterMsg.setMember(member);
        memberRegisterMsg.setUuid(ThreadContextHolder.getHttpRequest().getHeader("uuid"));
        this.messageSender.send(new MqMessage(AmqpExchange.MEMEBER_REGISTER, AmqpExchange.MEMEBER_REGISTER + "_ROUTING", memberRegisterMsg));
        return member;
    }

    private void addConnect(AppleIDUserDTO userDTO, Member member, ConnectTypeEnum loginType) {
        //写入UnionId
        ConnectDO connect = new ConnectDO();
        connect.setMemberId(member.getMemberId());
        connect.setUnionType(ConnectTypeEnum.APPLEID.value());
        connect.setUnionId(userDTO.getOpenid());
        memberDaoSupport.insert("es_connect",connect);
        //写入openId
        connect = new ConnectDO();
        connect.setMemberId(member.getMemberId());
        connect.setUnionType(loginType.value());
        connect.setUnionId(userDTO.getOpenid());
        memberDaoSupport.insert("es_connect",connect);
    }

    /**
     * 根据用户id查询用户信息
     * @param memberId
     * @param openid
     * @param loginType
     * @return
     */
    private Member findMemberById(Integer memberId, String openid, ConnectTypeEnum loginType) {
        Member member = memberManager.getModel(memberId);
        //查看当前登陆终端该账户openid是否已记录，如果未记录则新增记录
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
        //初始化会员信息
        MemberVO memberVO = this.convertWechatMember(member, uuid,tokenOutTime,refreshTokenOutTime);
        //发送登录消息
        this.sendMessage(member);
        return memberVO;
    }

    /**
     * 生成member的token
     *
     * @param member
     * @param uuid
     * @return
     */
    private MemberVO convertWechatMember(Member member, String uuid,Integer tokenOutTime,Integer refreshTokenOutTime) {
        if (!member.getDisabled().equals(0)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "当前账号已经禁用，请联系管理员");
        }
        //新建买家用户角色对象
        Buyer buyer = new Buyer();
        //设置用户ID
        buyer.setUid(member.getMemberId());
        //设置用户名称
        buyer.setUsername(member.getUname());
        //设置uuid
        buyer.setUuid(uuid);
        //创建Token
        Token token = tokenManager.create(buyer,tokenOutTime,refreshTokenOutTime);
        //获取访问Token
        String accessToken = token.getAccessToken();
        //获取刷新Token
        String refreshToken = token.getRefreshToken();
        //组织返回数据
        MemberVO memberVO = new MemberVO(member, accessToken, refreshToken);
        return memberVO;
    }

    /**
     * 发送登录消息
     * @param member
     */
    private void sendMessage(Member member) {
        MemberLoginMsg loginMsg = new MemberLoginMsg();
        loginMsg.setLastLoginTime(member.getLastLogin());
        loginMsg.setMemberId(member.getMemberId());
        this.messageSender.send(new MqMessage(AmqpExchange.MEMEBER_LOGIN, AmqpExchange.MEMEBER_LOGIN + "_ROUTING", loginMsg));
    }
}
