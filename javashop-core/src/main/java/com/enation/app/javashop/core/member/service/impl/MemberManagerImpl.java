/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.service.impl;

import com.enation.app.javashop.core.base.message.MemberRegisterMsg;
import com.enation.app.javashop.core.base.rabbitmq.AmqpExchange;
import com.enation.app.javashop.core.client.trade.OrderClient;
import com.enation.app.javashop.core.member.MemberErrorCode;
import com.enation.app.javashop.core.member.model.dos.Member;
import com.enation.app.javashop.core.member.model.dto.MemberQueryParam;
import com.enation.app.javashop.core.member.model.dto.MemberStatisticsDTO;
import com.enation.app.javashop.core.member.model.vo.BackendMemberVO;
import com.enation.app.javashop.core.member.model.vo.MemberLoginMsg;
import com.enation.app.javashop.core.member.model.vo.MemberPointVO;
import com.enation.app.javashop.core.member.model.vo.MemberVO;
import com.enation.app.javashop.core.member.service.MemberCollectionGoodsManager;
import com.enation.app.javashop.core.member.service.MemberManager;
import com.enation.app.javashop.core.trade.order.model.enums.CommentStatusEnum;
import com.enation.app.javashop.framework.auth.Token;
import com.enation.app.javashop.framework.cache.Cache;
import com.enation.app.javashop.framework.context.ThreadContextHolder;
import com.enation.app.javashop.framework.context.UserContext;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.exception.ResourceNotFoundException;
import com.enation.app.javashop.framework.exception.ServiceException;
import com.enation.app.javashop.framework.rabbitmq.MessageSender;
import com.enation.app.javashop.framework.rabbitmq.MqMessage;
import com.enation.app.javashop.framework.security.TokenManager;
import com.enation.app.javashop.framework.security.model.Buyer;
import com.enation.app.javashop.framework.util.DateUtil;
import com.enation.app.javashop.framework.util.SqlUtil;
import com.enation.app.javashop.framework.util.StringUtil;
import com.enation.app.javashop.framework.util.TokenKeyGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员业务类
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-03-16 11:33:56
 */
@Service
public class MemberManagerImpl implements MemberManager {

    @Autowired
    @Qualifier("memberDaoSupport")
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
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Member edit(Member member, Integer id) {
        //校验邮箱是否已经存在
        if (!StringUtil.isEmpty(member.getEmail())) {
            Member mb = this.getMemberByEmail(member.getEmail());
            if (mb != null && !mb.getMemberId().equals(id)) {
                throw new ServiceException(MemberErrorCode.E117.code(), "邮箱已经被占用");
            }
        }
        //校验用户名是否已经存在
        if (!StringUtil.isEmpty(member.getUname())) {
            Member mb = this.getMemberByName(member.getUname());
            if (mb != null && !mb.getMemberId().equals(id)) {
                throw new ServiceException(MemberErrorCode.E108.code(), "当前用户名已经被使用");
            }
        }

        //校验手机号码是否重复
        if (!StringUtil.isEmpty(member.getMobile())) {
            Member mb = this.getMemberByMobile(member.getMobile());
            if (mb != null && !mb.getMemberId().equals(id)) {
                throw new ServiceException(MemberErrorCode.E118.code(), "当前手机号已经被使用");
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
        //会员收藏商品数
        memberStatisticsDTO.setGoodsCollectCount(memberCollectionGoodsManager.getMemberCollectCount());
        //会员订单数
        memberStatisticsDTO.setOrderCount(orderClient.getOrderNumByMemberId(UserContext.getBuyer().getUid()));
        //待评论数
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
        throw new ResourceNotFoundException("此会员不存在！");

    }

    @Override
    public MemberVO connectLoginHandle(Member member, String uuid) {
        //初始化会员信息
        MemberVO memberVO = this.convertMember(member, uuid);
        //发送登录消息
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
        //用户名登录处理
        Member member = this.getMemberByName(username);
        //判断是否为uniapp注册账号，如果是且密码为空则提示去uniapp修改密码后登陆，add chushuai by 2020/10/09
        if (member!=null && StringUtil.isEmpty(member.getPassword()) && username.startsWith("m_")){
            throw new ServiceException(MemberErrorCode.E107.code(), "此账号为微信/支付宝等移动端三方授权账号，请在移动端登录并修改密码后在电脑端登录");
        }
        if (member != null) {
            if (!StringUtil.equals(member.getUname(), username)) {
                throw new ServiceException(MemberErrorCode.E107.code(), "账号密码错误！");
            }
            pwdmd5 = StringUtil.md5(password + member.getUname().toLowerCase());
            if (member.getPassword().equals(pwdmd5)) {
                return member;
            }
        }
        //手机号码登录处理
        member = this.getMemberByMobile(username);
        if (member != null) {
            pwdmd5 = StringUtil.md5(password + member.getUname().toLowerCase());
            if (member.getPassword().equals(pwdmd5)) {
                return member;
            }
        }
        //邮箱登录处理
        member = this.getMemberByEmail(username);
        if (member != null) {
            pwdmd5 = StringUtil.md5(password + member.getUname().toLowerCase());
            if (member.getPassword().equals(pwdmd5)) {
                return member;
            }
        }
        throw new ServiceException(MemberErrorCode.E107.code(), "账号密码错误！");
    }

    /**
     * 登录会员后的处理
     *
     * @param member 会员信息
     */
    @Override
    public MemberVO loginHandle(Member member) {
        //从请求header中获取用户的uuid
        String uuid = ThreadContextHolder.getHttpRequest().getHeader("uuid");
        //初始化会员信息
        MemberVO memberVO = this.convertMember(member, uuid);
        //发送登录消息
        this.sendMessage(member);

        return memberVO;
    }

    @Override
    public String[] generateMemberUname(String uname) {
        //如果用户输入的用户大于15位 则截取 拼接随机数5位，总长度不能大于二十
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
            //根据拼接好的用户判断是否存在
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
        //获取用户信息
        Member member = this.mobileLoginValidation(mobile);
        //对登录的后续处理
        return this.loginHandle(member);
    }

    @Override
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberVO appLogin(String mobile) {
        //获取用户信息
        Member member = this.mobileLoginValidation(mobile);

        //从请求header中获取用户的uuid
        String uuid = ThreadContextHolder.getHttpRequest().getHeader("uuid");
        //初始化会员信息
        MemberVO memberVO = this.convertMember(member, uuid);
        //发送登录消息
        this.sendMessage(member);

        return memberVO;
    }

    @Override
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Member register(Member member) {
        //手机号码校验
        Member m = this.getMemberByMobile(member.getMobile());
        if (m != null) {
            throw new ServiceException(MemberErrorCode.E107.code(), "该手机号已经被占用");
        }
        //用户名校验
        m = this.getMemberByName(member.getUname());
        if (m != null) {
            throw new ServiceException(MemberErrorCode.E107.code(), "当前会员已经注册");
        }
        //邮箱校验
        if (!StringUtil.isEmpty(member.getEmail())) {
            m = this.getMemberByEmail(member.getEmail());
            if (m != null) {
                throw new ServiceException(MemberErrorCode.E117.code(), "邮箱已经被占用");
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
        //组织数据结构发送会员注册消息
        MemberRegisterMsg memberRegisterMsg = new MemberRegisterMsg();
        memberRegisterMsg.setMember(member);
        memberRegisterMsg.setUuid(ThreadContextHolder.getHttpRequest().getHeader("uuid"));
        this.messageSender.send(new MqMessage(AmqpExchange.MEMEBER_REGISTER, AmqpExchange.MEMEBER_REGISTER + "_ROUTING", memberRegisterMsg));
        return member;
    }

    @Override
    public Member getMemberByAccount(String account) {
        //通过手机号进行查询账户信息
        Member member = this.getMemberByMobile(account);
        if (member != null) {
            return member;
        }
        //通过用户名进行查询账户信息
        member = this.getMemberByName(account);
        if (member != null) {
            return member;
        }
        member = this.getMemberByEmail(account);
        if (member != null) {
            return member;
        }
        throw new ResourceNotFoundException("此会员不存在");
    }

    @Override
    public Page list(MemberQueryParam memberQueryParam) {
        List<String> term = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from es_member");
        //对会员状态的查询处理
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
        //关键字查询
        if (!StringUtil.isEmpty(memberQueryParam.getKeyword())) {
            sql.append(" and (uname like ? or mobile like ? or nickname like ? ) ");
            term.add("%" + memberQueryParam.getKeyword() + "%");
            term.add("%" + memberQueryParam.getKeyword() + "%");
            term.add("%" + memberQueryParam.getKeyword() + "%");
        }
        //对会员手机号码的查询处理
        if (memberQueryParam.getMobile() != null) {
            sql.append(" and mobile like ?");
            term.add("%" + memberQueryParam.getMobile() + "%");
        }
        //用户名查询
        if (memberQueryParam.getUname() != null) {
            sql.append(" and uname like ?");
            term.add("%" + memberQueryParam.getUname() + "%");
        }
        //对会员邮箱的查询处理
        if (memberQueryParam.getEmail() != null) {
            sql.append(" and email = ?");
            term.add(memberQueryParam.getEmail());
        }
        //对会员性别的查询处理,如果输入其他数值则查询所有性别
        if (memberQueryParam.getSex() != null) {
            if (memberQueryParam.getSex() == 1 || memberQueryParam.getSex() == 0) {
                sql.append(" and sex = ?");
                term.add(memberQueryParam.getSex() + "");
            }
        }
        //对会员注册时间的处理
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
     * 手机号码登录验证
     * @param mobile 手机号码
     * @return
     */
    private Member mobileLoginValidation(String mobile) {
        //获取用户信息
        Member member = this.getMemberByMobile(mobile);
        //校验账号是否存在
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E107.code(), "账号密码错误！");
        }
        //校验账号是否正常
        if (member.getDisabled().equals(-1)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "当前账号已经禁用，请联系管理员");
        }

        return member;
    }

    @Autowired
    private TokenManager tokenManager;

    /**
     * 生成member的token
     *
     * @param member
     * @param uuid
     * @return
     */
    private MemberVO convertMember(Member member, String uuid) {
        //校验当前账号是否被禁用
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
        Token token = tokenManager.create(buyer);
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
