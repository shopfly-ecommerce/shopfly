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
package cloud.shopfly.b2c.core.client.trade.impl;

import cloud.shopfly.b2c.core.client.trade.CouponClient;
import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.coupon.service.CouponManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 优惠券client实现
 *
 * @author zh
 * @version v7.0
 * @date 18/12/6 下午4:28
 * @since v7.0
 */
@Service
public class CouponClientImpl implements CouponClient {


    @Autowired
    private CouponManager couponManager;

    @Override
    public CouponDO getModel(Integer id) {
        return couponManager.getModel(id);
    }

    @Override
    public void addReceivedNum(Integer couponId) {
        couponManager.addReceivedNum(couponId);
    }
}
