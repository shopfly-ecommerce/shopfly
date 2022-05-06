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
 * 会员优惠券
 *
 * @author Snow create in 2018/5/24
 * @version v2.0
 * @since v7.0.0
 */
public interface MemberCouponManager {

    /**
     * 领取优惠券
     *
     * @param memberId 会员id
     * @param couponId 优惠券id
     */
    void receiveBonus(Integer memberId, Integer couponId);

    /**
     * 查询我的所有优惠券
     *
     * @param param
     * @return
     */
    Page list(MemberCouponQueryParam param);


    /**
     * 读取我的优惠券
     *
     * @param memberId
     * @param mcId
     * @return
     */
    MemberCoupon getModel(Integer memberId, Integer mcId);

    /**
     * 将优惠券变为已使用
     *
     * @param mcId
     */
    void usedCoupon(Integer mcId);


    /**
     * 检测已领取数量
     *
     * @param couponId
     */
    void checkLimitNum(Integer couponId);

    /**
     * 结算页—查询会员优惠券
     *
     * @param memberId  会员id
     * @return
     */
    List<MemberCoupon> listByCheckout(Integer memberId);

    /**
     * 优惠券各个状态数量
     *
     * @return
     */
    MemberCouponNumVO statusNum();

}
