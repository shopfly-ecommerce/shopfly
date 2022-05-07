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
package cloud.shopfly.b2c.core.client.member;

import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dos.MemberCoupon;
import cloud.shopfly.b2c.core.member.model.dos.MemberPointHistory;
import cloud.shopfly.b2c.core.member.model.vo.BackendMemberVO;

import java.util.List;

/**
 * Member client client
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 In the morning11:48
 * @since v7.0
 */

public interface MemberClient {
    /**
     * According to the membershipidGet membership information
     *
     * @param memberId
     * @return The member information
     */
    Member getModel(Integer memberId);

    /**
     * The login number is cleared to zero
     */
    void loginNumToZero();

    /**
     * Modify the member
     *
     * @param member members
     * @param id     Member of the primary key
     * @return Member members
     */
    Member edit(Member member, Integer id);

    /**
     * Update login times
     *
     * @param memberId
     * @param now
     * @return
     */
    void updateLoginNum(Integer memberId, Long now);

    /**
     * Member points operation, this method can add points and consumption points at the same time
     * 1、Add integral
     * gadePointType for1则for添加等级积分gadePointfor积分值
     * consumPointType for1则for添加消费积分consumPointfor消费积分值
     * <p>
     * 2、consumption score
     * gadePointType for0则for消费等级积分gadePointfor积分值
     * consumPointType for0则for消费消费积分consumPointfor消费积分值
     * <p>
     * If no operation is performedgadePointType for0 gadePointTypefor1
     * If no operation is performedconsumPoint for0 consumPointTypefor1
     *
     * @param memberPointHistory Member of the integral
     */
    void pointOperation(MemberPointHistory memberPointHistory);

    /**
     * Query all membersid
     *
     * @return
     */
    String queryAllMemberIds();

    /**
     * Read my coupons
     *
     * @param memberId
     * @param mcId
     * @return
     */
    MemberCoupon getModel(Integer memberId, Integer mcId);

    /**
     * Turn the coupon into used
     *
     * @param mcId
     */
    void usedCoupon(Integer mcId);

    /**
     * Get a coupon
     *
     * @param memberId membersid
     * @param couponId couponsid
     */
    void receiveBonus(Integer memberId, Integer couponId);

    /**
     * Search for new Members
     *
     * @param length
     * @return
     */
    List<BackendMemberVO> newMember(Integer length);

}
