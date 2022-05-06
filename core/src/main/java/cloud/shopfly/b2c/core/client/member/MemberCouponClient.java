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

import cloud.shopfly.b2c.core.member.model.dos.MemberCoupon;

import java.util.List;

/**
 * 会员优惠券client
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 上午11:48
 * @since v7.0
 */

public interface MemberCouponClient {
    /**
     * 查询优惠券列表
     *
     * @param memberId 会员id
     * @return 优惠券列表
     */
    List listByCheckout(Integer memberId);

    /**
     * 领取优惠券
     *
     * @param memberId 会员id
     * @param couponId 优惠券id
     */
    void receiveBonus(Integer memberId, Integer couponId);

    /**
     * 获取会员优惠券信息
     *
     * @param memberId 会员id
     * @param mcId     优惠券id
     * @return 优惠券对象
     */
    MemberCoupon getModel(Integer memberId, Integer mcId);


}
