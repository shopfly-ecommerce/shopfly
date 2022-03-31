/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.member.impl;

import dev.shopflix.core.client.member.MemberClient;
import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.core.member.model.dos.MemberCoupon;
import dev.shopflix.core.member.model.dos.MemberPointHistory;
import dev.shopflix.core.member.model.vo.BackendMemberVO;
import dev.shopflix.core.member.service.MemberCouponManager;
import dev.shopflix.core.member.service.MemberManager;
import dev.shopflix.core.member.service.MemberPointManager;
import dev.shopflix.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员业务默认实现
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 上午11:52
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value = "shopflix.product", havingValue = "stand")
public class MemberClientDefaultImpl implements MemberClient {

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private MemberPointManager memberPointManager;

    @Autowired

    private DaoSupport memberDaoSupport;

    @Autowired
    private MemberCouponManager memberCouponManager;

    @Override
    public Member getModel(Integer memberId) {
        return memberManager.getModel(memberId);
    }

    @Override
    public void loginNumToZero() {
        memberManager.loginNumToZero();
    }

    @Override
    public Member edit(Member member, Integer id) {

        return memberManager.edit(member, id);
    }

    /**
     * 更新登录次数
     *
     * @param memberId
     * @param now
     * @return
     */
    @Override
    public void updateLoginNum(Integer memberId, Long now) {
        memberManager.updateLoginNum(memberId, now);
    }

    @Override
    public void pointOperation(MemberPointHistory memberPointHistory) {

        memberPointManager.pointOperation(memberPointHistory);
    }

    @Override
    public String queryAllMemberIds() {

        String sql = "select group_concat(member_id) as member_ids from es_member ";
        String memberIds = this.memberDaoSupport.queryForString(sql);

        return memberIds;
    }

    @Override
    public MemberCoupon getModel(Integer memberId, Integer mcId) {
        return memberCouponManager.getModel(memberId, mcId);
    }

    @Override
    public void usedCoupon(Integer mcId) {
        memberCouponManager.usedCoupon(mcId);
    }

    @Override
    public void receiveBonus(Integer memberId, Integer couponId) {
        memberCouponManager.receiveBonus(memberId, couponId);

    }

    @Override
    public List<BackendMemberVO> newMember(Integer length) {
        return memberManager.newMember(length);
    }
}
