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

import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dto.MemberQueryParam;
import cloud.shopfly.b2c.core.member.model.dto.MemberStatisticsDTO;
import cloud.shopfly.b2c.core.member.model.vo.BackendMemberVO;
import cloud.shopfly.b2c.core.member.model.vo.MemberLoginMsg;
import cloud.shopfly.b2c.core.member.model.vo.MemberPointVO;
import cloud.shopfly.b2c.core.member.model.vo.MemberVO;
import cloud.shopfly.b2c.core.member.service.MemberCollectionGoodsManager;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.trade.order.model.enums.CommentStatusEnum;
import cloud.shopfly.b2c.core.base.message.MemberRegisterMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.framework.auth.Token;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import cloud.shopfly.b2c.framework.security.TokenManager;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.util.TokenKeyGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Member Business
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-03-16 11:33:56
 */
@Service
public class MemberManagerImpl implements MemberManager {

    @Autowired
    
    private DaoSupport memberDaoSupport;
    @Autowired
    private MessageSender messageSender;

    @Autowired
    private MemberCollectionGoodsManager memberCollectionGoodsManager;
    @Autowired
    private Cache cache;
    @Autowired
    private OrderClient orderClient;

    @Override
    public Member getModel(Integer id) {
        return this.memberDaoSupport.queryForObject(Member.class, id);
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Member edit(Member member, Integer id) {
        // Verify whether the mailbox already exists
        if (!StringUtil.isEmpty(member.getEmail())) {
            Member mb = this.getMemberByEmail(member.getEmail());
            if (mb != null && !mb.getMemberId().equals(id)) {
                throw new ServiceException(MemberErrorCode.E117.code(), "The mailbox is occupied");
            }
        }
        // Verify that the user name already exists
        if (!StringUtil.isEmpty(member.getUname())) {
            Member mb = this.getMemberByName(member.getUname());
            if (mb != null && !mb.getMemberId().equals(id)) {
                throw new ServiceException(MemberErrorCode.E108.code(), "The current user name is already in use");
            }
        }

        // Check whether the mobile phone number is duplicated
        if (!StringUtil.isEmpty(member.getMobile())) {
            Member mb = this.getMemberByMobile(member.getMobile());
            if (mb != null && !mb.getMemberId().equals(id)) {
                throw new ServiceException(MemberErrorCode.E118.code(), "The current mobile phone number has been used");
            }
        }
        this.memberDaoSupport.update(member, id);
        return member;
    }


    @Override
    public Member getMemberByName(String uname) {
        String sql = "select * from es_member where binary uname = ?";
        return this.memberDaoSupport.queryForObject(sql, Member.class, uname);
    }

    @Override
    public Member getMemberByMobile(String mobile) {
        String sql = "select * from es_member where mobile = ?";
        return this.memberDaoSupport.queryForObject(sql, Member.class, mobile);
    }

    @Override
    public Member getMemberByEmail(String email) {
        String sql = "select * from es_member where email = ?";
        return this.memberDaoSupport.queryForObject(sql, Member.class, email);
    }

    @Override
    public void logout(Integer memberId) {
        cache.remove(TokenKeyGenerate.generateBuyerAccessToken(ThreadContextHolder.getHttpRequest().getHeader("uuid"), memberId));
        cache.remove(TokenKeyGenerate.generateBuyerRefreshToken(ThreadContextHolder.getHttpRequest().getHeader("uuid"), memberId));
    }

    @Override
    public MemberStatisticsDTO getMemberStatistics() {
        MemberStatisticsDTO memberStatisticsDTO = new MemberStatisticsDTO();
        // Number of items collected by members
        memberStatisticsDTO.setGoodsCollectCount(memberCollectionGoodsManager.getMemberCollectCount());
        // Membership order number
        memberStatisticsDTO.setOrderCount(orderClient.getOrderNumByMemberId(UserContext.getBuyer().getUid()));
        // For comments
        memberStatisticsDTO.setPendingCommentCount(orderClient.getOrderCommentNumByMemberId(UserContext.getBuyer().getUid(), CommentStatusEnum.UNFINISHED.name()));
        return memberStatisticsDTO;
    }

    @Override
    public void loginNumToZero() {
        this.memberDaoSupport.execute("update es_member set login_count = 0");
    }

    @Override
    public void updateLoginNum(Integer memberId, Long now) {
        this.memberDaoSupport.execute("update es_member set login_count = login_count+1,last_login = ? where member_id = ?", now, memberId);
    }

    @Override
    public List<BackendMemberVO> newMember(Integer length) {
        return this.memberDaoSupport.queryForList("select * from es_member order by create_time desc limit 0,?", BackendMemberVO.class, length);
    }

    @Override
    public MemberPointVO getMemberPoint() {
        Buyer buyer = UserContext.getBuyer();
        Member member = this.getModel(buyer.getUid());
        if (member != null) {
            MemberPointVO memberPointVO = new MemberPointVO();
            if (member.getConsumPoint() != null) {
                memberPointVO.setConsumPoint(member.getConsumPoint());
            } else {
                memberPointVO.setConsumPoint(0);
            }

            if (member.getGradePoint() != null) {
                memberPointVO.setGradePoint(member.getGradePoint());
            } else {
                memberPointVO.setGradePoint(0);
            }
            return memberPointVO;
        }
        throw new ResourceNotFoundException("This member does not exist！");

    }

    @Override
    public MemberVO connectLoginHandle(Member member, String uuid) {
        // Initialize member information
        MemberVO memberVO = this.convertMember(member, uuid);
        // Sending a Login message
        this.sendMessage(member);
        return memberVO;
    }

    @Override
    public MemberVO login(String username, String password) {
        Member member = this.validation(username, password);
        return this.loginHandle(member);
    }

    @Override
    public Member validation(String username, String password) {
        String pwdmd5 = "";
        // User name login processing
        Member member = this.getMemberByName(username);
        // Determine whether to register an account for UNIapp. If yes and the password is empty, prompt uniapp to change the password and log in, add chushuai by 2020/10/09
        if (member!=null && StringUtil.isEmpty(member.getPassword()) && username.startsWith("m_")){
            throw new ServiceException(MemberErrorCode.E107.code(), "This account is wechat/For third-party authorized accounts of mobile terminals such as Alipay, please log in on the mobile terminal and change the password before logging in on the computer");
        }
        if (member != null) {
            if (!StringUtil.equals(member.getUname(), username)) {
                throw new ServiceException(MemberErrorCode.E107.code(), "Incorrect account password！");
            }
            pwdmd5 = StringUtil.md5(password + member.getUname().toLowerCase());
            if (member.getPassword().equals(pwdmd5)) {
                return member;
            }
        }
        // Mobile phone number login processing
        member = this.getMemberByMobile(username);
        if (member != null) {
            pwdmd5 = StringUtil.md5(password + member.getUname().toLowerCase());
            if (member.getPassword().equals(pwdmd5)) {
                return member;
            }
        }
        // Mailbox Login Processing
        member = this.getMemberByEmail(username);
        if (member != null) {
            pwdmd5 = StringUtil.md5(password + member.getUname().toLowerCase());
            if (member.getPassword().equals(pwdmd5)) {
                return member;
            }
        }
        throw new ServiceException(MemberErrorCode.E107.code(), "Incorrect account password！");
    }

    /**
     * Processing after login member
     *
     * @param member The member information
     */
    @Override
    public MemberVO loginHandle(Member member) {
        // Get the users UUID from the request header
        String uuid = ThreadContextHolder.getHttpRequest().getHeader("uuid");
        // Initialize member information
        MemberVO memberVO = this.convertMember(member, uuid);
        // Sending a Login message
        this.sendMessage(member);

        return memberVO;
    }

    @Override
    public String[] generateMemberUname(String uname) {
        // If the number of users entered by the user is greater than 15, a random number of 5 digits is intercepted and the total length cannot be greater than 20
        if (uname.length() > 15) {
            uname = uname.substring(0, 15);

        }
        String[] strs = new String[2];
        int i = 0;
        while (true) {
            if (i > 1) {
                break;
            }
            String unameRandom = "" + (int) (Math.random() * (99999 - 10000 + 1));
            // Determine whether the user exists according to the spliced user
            Member member = this.getMemberByName(uname + unameRandom);
            if (member == null) {
                strs[i] = uname + unameRandom;
                i++;
            }
        }
        return strs;
    }

    @Override
    public MemberVO login(String mobile) {
        // Obtaining User information
        Member member = this.mobileLoginValidation(mobile);
        // Follow-up to login
        return this.loginHandle(member);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberVO appLogin(String mobile) {
        // Obtaining User information
        Member member = this.mobileLoginValidation(mobile);

        // Get the users UUID from the request header
        String uuid = ThreadContextHolder.getHttpRequest().getHeader("uuid");
        // Initialize member information
        MemberVO memberVO = this.convertMember(member, uuid);
        // Sending a Login message
        this.sendMessage(member);

        return memberVO;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Member register(Member member) {
        // Mobile phone number verification
        Member m = this.getMemberByMobile(member.getMobile());
        if (m != null) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The phone number has been occupied");
        }
        // User name verification
        m = this.getMemberByName(member.getUname());
        if (m != null) {
            throw new ServiceException(MemberErrorCode.E107.code(), "Current membership is registered");
        }
        // Mail check
        if (!StringUtil.isEmpty(member.getEmail())) {
            m = this.getMemberByEmail(member.getEmail());
            if (m != null) {
                throw new ServiceException(MemberErrorCode.E117.code(), "The mailbox is occupied");
            }
        }
        String password = member.getPassword();
        member.setPassword(StringUtil.md5(password + member.getUname().toLowerCase()));
        member.setCreateTime(DateUtil.getDateline());
        member.setGradePoint(0);
        member.setConsumPoint(0);
        member.setLoginCount(0);
        member.setDisabled(0);
        member.setInfoFull(0);
        this.memberDaoSupport.insert(member);
        int memberId = this.memberDaoSupport.getLastId("es_member");
        member.setMemberId(memberId);
        // The organization data structure sends membership registration messages
        MemberRegisterMsg memberRegisterMsg = new MemberRegisterMsg();
        memberRegisterMsg.setMember(member);
        memberRegisterMsg.setUuid(ThreadContextHolder.getHttpRequest().getHeader("uuid"));
        this.messageSender.send(new MqMessage(AmqpExchange.MEMEBER_REGISTER, AmqpExchange.MEMEBER_REGISTER + "_ROUTING", memberRegisterMsg));
        return member;
    }

    @Override
    public Member getMemberByAccount(String account) {
        // You can query account information by mobile phone number
        Member member = this.getMemberByMobile(account);
        if (member != null) {
            return member;
        }
        // You can query account information by user name
        member = this.getMemberByName(account);
        if (member != null) {
            return member;
        }
        member = this.getMemberByEmail(account);
        if (member != null) {
            return member;
        }
        throw new ResourceNotFoundException("This member does not exist");
    }

    @Override
    public Page list(MemberQueryParam memberQueryParam) {
        List<String> term = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from es_member");
        // Member status query processing
        if (memberQueryParam.getDisabled() != null) {
            if (memberQueryParam.getDisabled() != -1 && memberQueryParam.getDisabled() != 0) {
                sql.append(" where disabled =  0");
            } else {
                sql.append(" where disabled =  ?");
                term.add(memberQueryParam.getDisabled() + "");
            }
        } else {
            sql.append(" where disabled =  0");
        }
        // Keyword query
        if (!StringUtil.isEmpty(memberQueryParam.getKeyword())) {
            sql.append(" and (uname like ? or mobile like ? or nickname like ? ) ");
            term.add("%" + memberQueryParam.getKeyword() + "%");
            term.add("%" + memberQueryParam.getKeyword() + "%");
            term.add("%" + memberQueryParam.getKeyword() + "%");
        }
        // Inquire and deal with members mobile phone number
        if (memberQueryParam.getMobile() != null) {
            sql.append(" and mobile like ?");
            term.add("%" + memberQueryParam.getMobile() + "%");
        }
        // User name Query
        if (memberQueryParam.getUname() != null) {
            sql.append(" and uname like ?");
            term.add("%" + memberQueryParam.getUname() + "%");
        }
        // Member email enquiry processing
        if (memberQueryParam.getEmail() != null) {
            sql.append(" and email = ?");
            term.add(memberQueryParam.getEmail());
        }
        // Member gender query processing, if input other values to query all genders
        if (memberQueryParam.getSex() != null) {
            if (memberQueryParam.getSex() == 1 || memberQueryParam.getSex() == 0) {
                sql.append(" and sex = ?");
                term.add(memberQueryParam.getSex() + "");
            }
        }
        // Handling of member registration time
        if (memberQueryParam.getStartTime() != null && !StringUtil.isEmpty(memberQueryParam.getStartTime())) {
            sql.append(" and create_time > ?");
            term.add(memberQueryParam.getStartTime());
        }

        if (memberQueryParam.getEndTime() != null && !StringUtil.isEmpty(memberQueryParam.getEndTime())) {
            sql.append(" and create_time < ?");
            term.add(memberQueryParam.getEndTime());
        }
        if (memberQueryParam.getRegion() != null) {
            sql.append(" and province_id = ? and city_id = ? and county_id = ?");
            term.add(memberQueryParam.getRegion().getProvinceId() + "");
            term.add(memberQueryParam.getRegion().getCityId() + "");
            term.add(memberQueryParam.getRegion().getCountyId() + "");
            if (!memberQueryParam.getRegion().getTownId().equals(0)) {
                sql.append(" and town_id = ?");
                term.add(memberQueryParam.getRegion().getTownId() + "");
            }
        }
        sql.append(" order by create_time desc");
        Page webPage = this.memberDaoSupport.queryForPage(sql.toString(), memberQueryParam.getPageNo(), memberQueryParam.getPageSize(), Member.class, term.toArray());

        return webPage;
    }

    @Override
    public void memberLoginout(Integer memberId) {
        this.cache.vagueDel(TokenKeyGenerate.generateVagueBuyerAccessToken(memberId));
        this.cache.vagueDel(TokenKeyGenerate.generateVagueBuyerRefreshToken(memberId));
    }

    @Override
    public List<Member> getMemberByIds(Integer[] memberIds) {
        List<Object> term = new ArrayList<>();
        String str = SqlUtil.getInSql(memberIds, term);
        String sql = "select * from es_member where member_id in (" + str + ")";
        return this.memberDaoSupport.queryForList(sql, Member.class, term.toArray());
    }

    /**
     * Mobile phone number login authentication
     * @param mobile Mobile phone number
     * @return
     */
    private Member mobileLoginValidation(String mobile) {
        // Obtaining User information
        Member member = this.getMemberByMobile(mobile);
        // Verify that the account exists
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E107.code(), "Incorrect account password！");
        }
        // Verify that the account is normal
        if (member.getDisabled().equals(-1)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The current account is disabled. Contact the administrator");
        }

        return member;
    }

    @Autowired
    private TokenManager tokenManager;

    /**
     * generatememberthetoken
     *
     * @param member
     * @param uuid
     * @return
     */
    private MemberVO convertMember(Member member, String uuid) {
        // Verify that the current account is disabled
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
        Token token = tokenManager.create(buyer);
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
