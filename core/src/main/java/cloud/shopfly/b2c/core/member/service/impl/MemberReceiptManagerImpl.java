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

import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dos.MemberReceipt;
import cloud.shopfly.b2c.core.member.model.enums.ReceiptTypeEnum;
import cloud.shopfly.b2c.core.member.model.vo.MemberReceiptVO;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.member.service.MemberReceiptManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Member invoice business category
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-20 20:48:13
 */
@Service
public class MemberReceiptManagerImpl implements MemberReceiptManager {

    @Autowired

    private DaoSupport memberDaoSupport;
    @Autowired
    private MemberManager memberManager;

    @Override
    public List<MemberReceipt> list(String receiptType) {
        String sql = "select * from es_member_receipt where member_id = ? and receipt_type = ? ORDER BY receipt_id desc ";
        return this.memberDaoSupport.queryForList(sql, MemberReceipt.class, UserContext.getBuyer().getUid(), receiptType);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberReceipt add(MemberReceiptVO memberReceiptVO) {
        // Check whether the current member exists
        Integer memberId = UserContext.getBuyer().getUid();
        Member member = this.memberManager.getModel(memberId);
        boolean bool = (member == null) || (member != null && !member.getDisabled().equals(0));
        if (bool) {
            throw new ResourceNotFoundException("Current member does not exist");
        }

        if (this.checkTitle(memberReceiptVO.getReceiptTitle(), null, memberId)) {
            throw new ServiceException(MemberErrorCode.E121.code(), "Invoice title must not be repeated");
        }

        // If the header is not personal, the verification tax number cannot be empty
        if(!"personal".equals(memberReceiptVO.getReceiptTitle())){
            if(memberReceiptVO.getTaxNo()==null){
                throw new ServiceException(MemberErrorCode.E121.code(), "Invoice tax number cannot be blank");
            }
        }

        List<MemberReceipt> list = this.list(memberReceiptVO.getReceiptType());
        // If it is VAT ordinary invoice and electronic invoice, the number of verification invoices should not exceed 10
        if (memberReceiptVO.getReceiptType().equals(ReceiptTypeEnum.VATORDINARY.name()) || memberReceiptVO.getReceiptType().equals(ReceiptTypeEnum.ELECTRO.name())) {
            if (list.size() >= 10) {
                throw new ServiceException(MemberErrorCode.E121.code(), "The invoice amount has reached the upper limit10a");
            }
        } else {
            if (list.size() > 1) {
                throw new ServiceException(MemberErrorCode.E121.code(), "The invoice amount has reached the upper limit1a");
            }
        }
        MemberReceipt memberReceipt = new MemberReceipt();
        BeanUtil.copyProperties(memberReceiptVO, memberReceipt);
        memberReceipt.setMemberId(memberId);
        memberReceipt.setIsDefault(1);
        memberDaoSupport.insert(memberReceipt);
        Integer receiptId = memberDaoSupport.getLastId("es_member_receipt");
        memberReceipt.setReceiptId(receiptId);
        // Set this invoice as the default
        this.setDefaultReceipt(memberReceiptVO.getReceiptType(), receiptId);
        return memberReceipt;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberReceipt edit(MemberReceiptVO memberReceiptVO, Integer id) {
        MemberReceipt memberReceipt = this.getModel(id);
        if (memberReceipt == null || !memberReceipt.getMemberId().equals(UserContext.getBuyer().getUid())) {
            throw new NoPermissionException("Have the right to operate");
        }

        if (this.checkTitle(memberReceiptVO.getReceiptTitle(), id, memberReceipt.getMemberId())) {
            throw new ServiceException(MemberErrorCode.E121.code(), "Invoice title must not be repeated");
        }

        BeanUtil.copyProperties(memberReceiptVO, memberReceipt);
        this.memberDaoSupport.update(memberReceipt, id);
        return memberReceipt;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        MemberReceipt memberReceipt = this.getModel(id);
        if (memberReceipt == null || !memberReceipt.getMemberId().equals(UserContext.getBuyer().getUid())) {
            throw new NoPermissionException("Have the right to operate");
        }
        this.memberDaoSupport.delete(MemberReceipt.class, id);
    }

    @Override
    public MemberReceipt getModel(Integer id) {
        String sql = "select * from es_member_receipt where receipt_id = ?";
        return this.memberDaoSupport.queryForObject(sql, MemberReceipt.class, id);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void setDefaultReceipt(String receiptType, Integer id) {
        // If the invoice ID is 0, it indicates that the default invoice is personal. You need to change the default value of other invoices to no
        this.memberDaoSupport.execute("update es_member_receipt set is_default = 0 where member_id = ? and receipt_type = ?", UserContext.getBuyer().getUid(), receiptType);
        if (id != 0) {
            MemberReceipt memberReceipt = this.getModel(id);
            if (memberReceipt == null || !memberReceipt.getMemberId().equals(UserContext.getBuyer().getUid())) {
                throw new NoPermissionException("Have the right to operate");
            }
            this.memberDaoSupport.execute("update es_member_receipt set is_default = 1 where member_id = ? and receipt_type = ? and receipt_id = ?", UserContext.getBuyer().getUid(), receiptType, id);
        }
    }

    /**
     * Check invoice title for duplicate
     * @param title
     * @param id
     * @param memberId
     * @return
     */
    protected boolean checkTitle(String title, Integer id, Integer memberId) {
        String sql = "select count(0) from es_member_receipt where member_id = ? and receipt_title = ?";

        List<Object> params = new ArrayList<>();
        params.add(memberId);
        params.add(title);

        if (id != null) {
            sql += " and receipt_id != ?";
            params.add(id);
        }

        int count = this.memberDaoSupport.queryForInt(sql, params.toArray());
        boolean flag = count != 0 ? true : false;
        return flag;
    }
}
