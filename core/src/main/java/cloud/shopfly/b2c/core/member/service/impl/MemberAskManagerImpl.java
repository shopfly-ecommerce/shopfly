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

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dos.MemberAsk;
import cloud.shopfly.b2c.core.member.model.dto.CommentQueryParam;
import cloud.shopfly.b2c.core.member.model.enums.AuditEnum;
import cloud.shopfly.b2c.core.member.service.MemberAskManager;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.framework.context.AdminUserContext;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Admin;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * Consulting business
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
        // If the client shows only approved, if the platform management shows all queries
        if (UserContext.getBuyer() != null) {
            sqlBuffer.append(" and c.auth_status = ?");
            term.add(AuditEnum.PASS_AUDIT.name());
        }
        sqlBuffer.append(" order by c.create_time desc ");
        Page webPage = this.daoSupport.queryForPage(sqlBuffer.toString(), param.getPageNo(), param.getPageSize(), MemberAsk.class, term.toArray());

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {

        // Change the state to the deleted state
        String sql = "update es_member_ask set status = 0 where ask_id = ?";

        this.daoSupport.execute(sql, id);
    }

    @Override
    public MemberAsk getModel(Integer id) {
        return this.daoSupport.queryForObject(MemberAsk.class, id);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberAsk reply(@NotEmpty(message = "Please enter your reply") String replyContent, Integer askId) {
        MemberAsk ask = this.getModel(askId);
        if (ask.getReplyStatus() == 1) {
            throw new ServiceException(MemberErrorCode.E202.code(), "Non-repeatable reply");
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
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void auth(Integer askId, String authStatus) {

        // Verify whether the administrator has permission
        Admin admin = AdminUserContext.getAdmin();

        if (admin == null) {
            throw new NoPermissionException("No permission to audit member consulting information!");
        }

        MemberAsk memberAsk = this.getModel(askId);

        if (memberAsk == null) {
            throw new ResourceNotFoundException("Member consultation does not exist!");
        }

        this.daoSupport.execute("update es_member_ask set auth_status = ? where ask_id = ? ", authStatus, askId);
    }
}
