/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.service.impl;

import dev.shopflix.core.client.goods.GoodsClient;
import dev.shopflix.core.goods.model.vo.CacheGoods;
import dev.shopflix.core.member.MemberErrorCode;
import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.core.member.model.dos.MemberAsk;
import dev.shopflix.core.member.model.dto.CommentQueryParam;
import dev.shopflix.core.member.model.enums.AuditEnum;
import dev.shopflix.core.member.service.MemberAskManager;
import dev.shopflix.core.member.service.MemberManager;
import dev.shopflix.framework.context.AdminUserContext;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.NoPermissionException;
import dev.shopflix.framework.exception.ResourceNotFoundException;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.security.model.Admin;
import dev.shopflix.framework.security.model.Buyer;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 咨询业务类
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-04 17:41:18
 */
@Service
public class MemberAskManagerImpl implements MemberAskManager {

    @Autowired

    private DaoSupport daoSupport;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private MemberManager memberManager;

    @Override
    public Page list(CommentQueryParam param) {

        StringBuffer sqlBuffer = new StringBuffer("select * from es_member_ask c where c.status = 1 ");
        List<Object> term = new ArrayList<Object>();

        if (param.getGoodsId() != null) {
            sqlBuffer.append(" and  c.goods_id = ?  ");
            term.add(param.getGoodsId());
        }
        if (param.getMemberId() != null) {
            sqlBuffer.append(" and  c.member_id = ? ");
            term.add(param.getMemberId());
        }
        if (param.getReplyStatus() != null) {
            sqlBuffer.append(" and  c.reply_status = ? ");
            term.add(param.getReplyStatus());
        }
        if (!StringUtil.isEmpty(param.getGoodsName())) {
            sqlBuffer.append(" and  c.goods_name like ? ");
            term.add("%" + param.getGoodsName() + "%");
        }
        if (!StringUtil.isEmpty(param.getMemberName())) {
            sqlBuffer.append(" and  c.member_name like ? ");
            term.add("%" + param.getMemberName() + "%");
        }
        if (!StringUtil.isEmpty(param.getContent())) {
            sqlBuffer.append(" and  c.content like ? ");
            term.add("%" + param.getContent() + "%");
        }
        if (!StringUtil.isEmpty(param.getKeyword())) {
            sqlBuffer.append(" and  (c.content like ? or c.goods_name like ?)");
            term.add("%" + param.getKeyword() + "%");
            term.add("%" + param.getKeyword() + "%");
        }
        //如果是用户端只展示审核通过的,如果是平台管理显示所有的咨询
        if (UserContext.getBuyer() != null) {
            sqlBuffer.append(" and c.auth_status = ?");
            term.add(AuditEnum.PASS_AUDIT.name());
        }
        sqlBuffer.append(" order by c.create_time desc ");
        Page webPage = this.daoSupport.queryForPage(sqlBuffer.toString(), param.getPageNo(), param.getPageSize(), MemberAsk.class, term.toArray());

        return webPage;
    }

    @Override
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberAsk add(String askContent, Integer goodsId) {
        CacheGoods goods = goodsClient.getFromCache(goodsId);

        Buyer buyer = UserContext.getBuyer();
        MemberAsk memberAsk = new MemberAsk();
        Member member = memberManager.getModel(buyer.getUid());
        memberAsk.setContent(askContent);
        memberAsk.setMemberId(buyer.getUid());
        memberAsk.setMemberName(buyer.getUsername());
        memberAsk.setGoodsId(goodsId);
        memberAsk.setGoodsName(goods.getGoodsName());
        memberAsk.setCreateTime(DateUtil.getDateline());
        memberAsk.setStatus(1);
        memberAsk.setReplyStatus(0);
        memberAsk.setMemberFace(member.getFace());
        memberAsk.setAuthStatus(AuditEnum.WAIT_AUDIT.name());
        this.daoSupport.insert(memberAsk);
        memberAsk.setAskId(this.daoSupport.getLastId(""));

        return memberAsk;
    }


    @Override
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {

        //将状态变成已删除状态
        String sql = "update es_member_ask set status = 0 where ask_id = ?";

        this.daoSupport.execute(sql, id);
    }

    @Override
    public MemberAsk getModel(Integer id) {
        return this.daoSupport.queryForObject(MemberAsk.class, id);
    }

    @Override
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberAsk reply(@NotEmpty(message = "请输入回复内容") String replyContent, Integer askId) {
        MemberAsk ask = this.getModel(askId);
        if (ask.getReplyStatus() == 1) {
            throw new ServiceException(MemberErrorCode.E202.code(), "不可重复回复");
        }
        ask.setReply(replyContent);
        ask.setReplyStatus(1);
        ask.setReplyTime(DateUtil.getDateline());
        this.daoSupport.update(ask, askId);
        return ask;
    }

    @Override
    public Integer getNoReplyCount() {

        StringBuffer sqlBuffer = new StringBuffer("select count(0) from es_member_ask c where c.status = 1 ");
        sqlBuffer.append(" and  c.reply_status = ?");

        return this.daoSupport.queryForInt(sqlBuffer.toString(), 0);
    }

    @Override
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void auth(Integer askId, String authStatus) {

        // 校验是否有管理端权限
        Admin admin = AdminUserContext.getAdmin();

        if (admin == null) {
            throw new NoPermissionException("没有权限审核会员咨询信息!");
        }

        MemberAsk memberAsk = this.getModel(askId);

        if (memberAsk == null) {
            throw new ResourceNotFoundException("会员咨询不存在!");
        }

        this.daoSupport.execute("update es_member_ask set auth_status = ? where ask_id = ? ", authStatus, askId);
    }
}
