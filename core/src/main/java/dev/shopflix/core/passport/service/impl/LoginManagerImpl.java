/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.passport.service.impl;

import cn.hutool.core.lang.UUID;
import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.base.message.MemberRegisterMsg;
import dev.shopflix.core.base.rabbitmq.AmqpExchange;
import dev.shopflix.core.member.MemberErrorCode;
import dev.shopflix.core.member.model.dos.ConnectDO;
import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.core.member.model.dto.LoginUserDTO;
import dev.shopflix.core.member.model.enums.ConnectTypeEnum;
import dev.shopflix.core.member.model.vo.Auth2Token;
import dev.shopflix.core.member.model.vo.MemberLoginMsg;
import dev.shopflix.core.member.model.vo.MemberVO;
import dev.shopflix.core.member.service.MemberManager;
import dev.shopflix.core.passport.service.LoginManager;
import dev.shopflix.framework.JavashopConfig;
import dev.shopflix.framework.auth.Token;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.context.ThreadContextHolder;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.logs.Logger;
import dev.shopflix.framework.logs.LoggerFactory;
import dev.shopflix.framework.rabbitmq.MessageSender;
import dev.shopflix.framework.rabbitmq.MqMessage;
import dev.shopflix.framework.security.TokenManager;
import dev.shopflix.framework.security.model.Buyer;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.EmojiCharacterUtil;
import dev.shopflix.framework.util.StringUtil;
import dev.shopflix.framework.util.TokenKeyGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 三方登陆服务
 * @author cs
 * 2020/11/02
 */
@Service
public class LoginManagerImpl implements LoginManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("memberDaoSupport")
    private DaoSupport memberDaoSupport;

    @Autowired
    private JavashopConfig javashopConfig;

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private Cache cache;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private TokenManager tokenManager;


    /**
     * 根据UnionId登陆
     * @param loginUserDTO
     * @return
     */
    @Override
    public Map loginByUnionId(LoginUserDTO loginUserDTO){
        Map res = new HashMap(16);
        //通过unionid查找会员(es_connect表)
        ConnectDO connectDO= findMemberByUnionId(loginUserDTO.getUnionid(),loginUserDTO.getUnionType());
        Member member = null;
        if(connectDO==null){
            //没找到注册一个
            member = register(loginUserDTO);
        }else{
            member=findMemberById(connectDO.getMemberId());
            //查看当前登陆终端该账户openid是否已记录，如果未记录则新增记录
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
        //存储uuid和unionId的关系
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
        logger.debug("三方登录openId为：" + loginUserDTO.getOpenid()+";unionid为"+loginUserDTO.getUnionid());
        cache.put(CachePrefix.CONNECT_LOGIN.getPrefix() + loginUserDTO.getUuid(), auth2Token);
        MemberVO memberVO = this.connectWeChatLoginHandle(member, loginUserDTO.getUuid(),loginUserDTO.getTokenOutTime(),loginUserDTO.getRefreshTokenOutTime());
        res.put("access_token", memberVO.getAccessToken());
        res.put("refresh_token", memberVO.getRefreshToken());
        res.put("uid", memberVO.getUid());
        return res;
    }

    /**
     * 根据unionId查询connect
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
     * 根据unionId查询connect
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
     * 注册
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
        member.setProvince(loginUserDTO.getProvince());
        member.setCity(loginUserDTO.getCity());
        //设置会员等级积分为0
        member.setGradePoint(0);
        //设置会员消费积分为0
        member.setConsumPoint(0);
        //设置会员是否完善了个人信息 0：否，1：是
        member.setInfoFull(0);
        memberDaoSupport.insert("es_member",member);
        member.setMemberId( memberDaoSupport.getLastId("es_member"));
        addConnect(loginUserDTO, member);
        //组织数据结构发送会员注册消息
        MemberRegisterMsg memberRegisterMsg = new MemberRegisterMsg();
        memberRegisterMsg.setMember(member);
        memberRegisterMsg.setUuid(ThreadContextHolder.getHttpRequest().getHeader("uuid"));
        this.messageSender.send(new MqMessage(AmqpExchange.MEMEBER_REGISTER, AmqpExchange.MEMEBER_REGISTER + "_ROUTING", memberRegisterMsg));
        return member;
    }

    private void addConnect(LoginUserDTO loginUserDTO, Member member) {

        //写入UnionId
        ConnectDO connect = new ConnectDO();
        connect.setMemberId(member.getMemberId());
        connect.setUnionType(loginUserDTO.getUnionType().value());
        connect.setUnionId(loginUserDTO.getUnionid());
        memberDaoSupport.insert("es_connect",connect);
        if (!StringUtil.isEmpty(loginUserDTO.getOpenid())){

            //写入openId
            connect = new ConnectDO();
            connect.setMemberId(member.getMemberId());
            connect.setUnionType(loginUserDTO.getOpenType().value());
            connect.setUnionId(loginUserDTO.getOpenid());
            memberDaoSupport.insert("es_connect",connect);
        }
    }

    /**
     * 根据用户id查询用户信息
     * @param memberId
     * @return
     */
    private Member findMemberById(Integer memberId) {
        Member member = memberManager.getModel(memberId);
        return member;
    }

    /**
     * 生成member的token
     *
     * @param member
     * @param uuid
     * @return
     */
    private MemberVO convertWechatMember(Member member, String uuid,Integer tokenOutTime,Integer refreshTokenOutTime) {
        //校验当前账号是否被禁用
        if (!member.getDisabled().equals(0)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "当前账号已经禁用，请联系管理员");
        }

        if (null == tokenOutTime){
            tokenOutTime = javashopConfig.getAccessTokenTimeout();
        }
        if (null == refreshTokenOutTime){
            refreshTokenOutTime = javashopConfig.getRefreshTokenTimeout();
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
        cache.put(TokenKeyGenerate.generateBuyerAccessToken(uuid, member.getMemberId()), accessToken, tokenOutTime + 60);
        cache.put(TokenKeyGenerate.generateBuyerRefreshToken(uuid, member.getMemberId()), refreshTokenOutTime + 60);
        return memberVO;
    }


    public MemberVO connectWeChatLoginHandle(Member member, String uuid,Integer tokenOutTime,Integer refreshTokenOutTime) {
        //初始化会员信息
        MemberVO memberVO = this.convertWechatMember(member, uuid,tokenOutTime,refreshTokenOutTime);
        //发送登录消息
        this.sendMessage(member);
        return memberVO;
    }

    /**
     * 发送登录消息
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
