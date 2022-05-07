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
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.MemberCoupon;
import cloud.shopfly.b2c.core.member.model.dto.MemberCouponQueryParam;
import cloud.shopfly.b2c.core.member.model.vo.MemberCouponNumVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Membership coupon
 *
 * @author Snow create in 2018/5/24
 * @version v2.0
 * @since v7.0.0
 */
public interface MemberCouponManager {

    /**
     * Get a coupon
     *
     * @param memberId membersid
     * @param couponId couponsid
     */
    void receiveBonus(Integer memberId, Integer couponId);

    /**
     * Check all my coupons
     *
     * @param param
     * @return
     */
    Page list(MemberCouponQueryParam param);


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
     * Check the quantity received
     *
     * @param couponId
     */
    void checkLimitNum(Integer couponId);

    /**
     * The settlement page—Check membership coupons
     *
     * @param memberId  membersid
     * @return
     */
    List<MemberCoupon> listByCheckout(Integer memberId);

    /**
     * Number of coupons in each state
     *
     * @return
     */
    MemberCouponNumVO statusNum();

}
