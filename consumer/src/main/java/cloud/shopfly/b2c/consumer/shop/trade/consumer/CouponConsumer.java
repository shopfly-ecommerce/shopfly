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
package cloud.shopfly.b2c.consumer.shop.trade.consumer;

import cloud.shopfly.b2c.consumer.core.event.OrderStatusChangeEvent;
import cloud.shopfly.b2c.consumer.core.event.TradeIntoDbEvent;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.member.model.dos.MemberCoupon;
import cloud.shopfly.b2c.core.promotion.coupon.service.CouponManager;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CouponVO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderMetaKeyEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.core.trade.order.service.OrderMetaManager;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Confirm receivables issue promotional activities complimentary coupons
 *
 * @author Snow create in 2018/5/22
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class CouponConsumer implements OrderStatusChangeEvent, TradeIntoDbEvent {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private OrderMetaManager orderMetaManager;

    @Autowired
    private MemberClient memberClient;

    @Autowired
    private CouponManager couponManager;


    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        if ((orderMessage.getNewStatus().name()).equals(OrderStatusEnum.PAID_OFF.name())) {

            // Read the issued coupon JSON
            String itemJson = this.orderMetaManager.getMetaValue(orderMessage.getOrderDO().getSn(), OrderMetaKeyEnum.COUPON);
            List<CouponVO> couponList = JsonUtil.jsonToList(itemJson, CouponVO.class);
            if (couponList != null && couponList.size() > 0) {

                // A coupon that circulates
                for (CouponVO couponVO : couponList) {
                    this.memberClient.receiveBonus(orderMessage.getOrderDO().getMemberId(), couponVO.getCouponId());
                }
            }
        }
    }


    @Override
    public void onTradeIntoDb(TradeVO tradeVO) {
        try {

            // Coupon status changes in advance
            // Turn used coupons into used coupons
            List<CouponVO> useCoupons = tradeVO.getCouponList();
            if (useCoupons != null) {
                for (CouponVO couponVO : useCoupons) {
                    this.memberClient.usedCoupon(couponVO.getMemberCouponId());
                    MemberCoupon memberCoupon = this.memberClient.getModel(tradeVO.getMemberId(), couponVO.getMemberCouponId());
                    // Modify the number of coupons the store has used
                    this.couponManager.addUsedNum(memberCoupon.getCouponId());
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Changing the status of the coupon is complete");
            }

        } catch (Exception e) {
            logger.error("Error changing coupon status", e);
        }
    }
}
