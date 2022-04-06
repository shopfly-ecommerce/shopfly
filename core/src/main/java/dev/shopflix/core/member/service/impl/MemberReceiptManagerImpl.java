/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.service.impl;

import dev.shopflix.core.member.MemberErrorCode;
import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.core.member.model.dos.MemberReceipt;
import dev.shopflix.core.member.model.enums.ReceiptTypeEnum;
import dev.shopflix.core.member.model.vo.MemberReceiptVO;
import dev.shopflix.core.member.service.MemberManager;
import dev.shopflix.core.member.service.MemberReceiptManager;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.NoPermissionException;
import dev.shopflix.framework.exception.ResourceNotFoundException;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员发票业务类
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
        //校验当前会员是否存在
        Integer memberId = UserContext.getBuyer().getUid();
        Member member = this.memberManager.getModel(memberId);
        boolean bool = (member == null) || (member != null && !member.getDisabled().equals(0));
        if (bool) {
            throw new ResourceNotFoundException("当前会员不存在");
        }

        if (this.checkTitle(memberReceiptVO.getReceiptTitle(), null, memberId)) {
            throw new ServiceException(MemberErrorCode.E121.code(), "发票抬头不能重复");
        }

        //抬头为非个人时，校验税号不能为空
        if(!"个人".equals(memberReceiptVO.getReceiptTitle())){
            if(memberReceiptVO.getTaxNo()==null){
                throw new ServiceException(MemberErrorCode.E121.code(), "发票税号不能为空");
            }
        }

        List<MemberReceipt> list = this.list(memberReceiptVO.getReceiptType());
        //如果是增值税普通发票和电子发票需要校验发票个数不能不能超过十个
        if (memberReceiptVO.getReceiptType().equals(ReceiptTypeEnum.VATORDINARY.name()) || memberReceiptVO.getReceiptType().equals(ReceiptTypeEnum.ELECTRO.name())) {
            if (list.size() >= 10) {
                throw new ServiceException(MemberErrorCode.E121.code(), "发票数已达上限10个");
            }
        } else {
            if (list.size() > 1) {
                throw new ServiceException(MemberErrorCode.E121.code(), "发票数已达上限1个");
            }
        }
        MemberReceipt memberReceipt = new MemberReceipt();
        BeanUtil.copyProperties(memberReceiptVO, memberReceipt);
        memberReceipt.setMemberId(memberId);
        memberReceipt.setIsDefault(1);
        memberDaoSupport.insert(memberReceipt);
        Integer receiptId = memberDaoSupport.getLastId("es_member_receipt");
        memberReceipt.setReceiptId(receiptId);
        //将此发票设置为默认
        this.setDefaultReceipt(memberReceiptVO.getReceiptType(), receiptId);
        return memberReceipt;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberReceipt edit(MemberReceiptVO memberReceiptVO, Integer id) {
        MemberReceipt memberReceipt = this.getModel(id);
        if (memberReceipt == null || !memberReceipt.getMemberId().equals(UserContext.getBuyer().getUid())) {
            throw new NoPermissionException("无权操作");
        }

        if (this.checkTitle(memberReceiptVO.getReceiptTitle(), id, memberReceipt.getMemberId())) {
            throw new ServiceException(MemberErrorCode.E121.code(), "发票抬头不能重复");
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
            throw new NoPermissionException("无权操作");
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
        //如果发票id为0，则说明默认发票为个人，需要将其他发票是否为默认修改为否
        this.memberDaoSupport.execute("update es_member_receipt set is_default = 0 where member_id = ? and receipt_type = ?", UserContext.getBuyer().getUid(), receiptType);
        if (id != 0) {
            MemberReceipt memberReceipt = this.getModel(id);
            if (memberReceipt == null || !memberReceipt.getMemberId().equals(UserContext.getBuyer().getUid())) {
                throw new NoPermissionException("无权操作");
            }
            this.memberDaoSupport.execute("update es_member_receipt set is_default = 1 where member_id = ? and receipt_type = ? and receipt_id = ?", UserContext.getBuyer().getUid(), receiptType, id);
        }
    }

    /**
     * 检测发票抬头是否重复
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
