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
package cloud.shopfly.b2c.core.client.trade;

import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;

/**
 * @author zh
 * @version v2.0
 * @Description: 优惠券单对外接口
 * @date 2018/7/26 11:21
 * @since v7.0.0
 */
public interface CouponClient {
    /**
     * 获取优惠券
     *
     * @param id 优惠券主键
     * @return Coupon  优惠券
     */
    CouponDO getModel(Integer id);


    /**
     * 增加被领取数量
     *
     * @param couponId
     */
    void addReceivedNum(Integer couponId);


}
