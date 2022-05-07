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
package cloud.shopfly.b2c.core.client.member.impl;

import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dos.MemberCoupon;
import cloud.shopfly.b2c.core.member.model.dos.MemberPointHistory;
import cloud.shopfly.b2c.core.member.model.vo.BackendMemberVO;
import cloud.shopfly.b2c.core.member.service.MemberCouponManager;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.member.service.MemberPointManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Member services are implemented by default
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 In the morning11:52
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
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
     * Update login times
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
