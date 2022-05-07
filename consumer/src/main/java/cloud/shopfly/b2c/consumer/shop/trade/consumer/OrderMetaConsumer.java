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

import cloud.shopfly.b2c.consumer.core.event.TradeIntoDbEvent;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CouponVO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderMetaDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.PayLog;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderDTO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderMetaKeyEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PayStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.ServiceStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.core.trade.order.service.OrderMetaManager;
import cloud.shopfly.b2c.core.trade.order.service.PayLogManager;
import cloud.shopfly.b2c.core.trade.order.service.TradeSnCreator;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Other order information is stored
 *
 * @author Snow create in 2018/6/26
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class OrderMetaConsumer implements TradeIntoDbEvent {


    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private OrderMetaManager orderMetaManager;

    @Autowired
    private PayLogManager payLogManager;

    @Autowired
    private TradeSnCreator tradeSnCreator;


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void onTradeIntoDb(TradeVO tradeVO) {

        List<OrderDTO> orderList = tradeVO.getOrderList();
        for (OrderDTO orderDTO : orderList) {
            addCashBack(orderDTO);
            addUsePoint(orderDTO);
            addGiftPoint(orderDTO);
            addGiftCoupon(orderDTO);
            addCouponPrice(orderDTO);
            addGift(orderDTO);
            addLog(orderDTO);
            addFullMinusPrice(orderDTO);
        }

    }

    /**
     * Record the cash back amount
     *
     * @param orderDTO
     */
    private void addCashBack(OrderDTO orderDTO) {
        try {
            // Record the cash back amount
            double cashBack = orderDTO.getPrice().getCashBack();
            OrderMetaDO cashBackMeta = new OrderMetaDO();
            cashBackMeta.setMetaKey(OrderMetaKeyEnum.CASH_BACK.name());
            cashBackMeta.setMetaValue(cashBack + "");
            cashBackMeta.setOrderSn(orderDTO.getSn());
            this.orderMetaManager.add(cashBackMeta);
            if (logger.isDebugEnabled()) {
                logger.debug("Deposit of cash back amount is completed");
            }
        } catch (Exception e) {
            logger.error("The deposit of cash back amount is wrong", e);
        }

    }


    /**
     * Record credits used
     *
     * @param orderDTO
     */
    private void addUsePoint(OrderDTO orderDTO) {
        try {

            // Integral used
            int consumerPoint = orderDTO.getPrice().getExchangePoint();
            if (consumerPoint > 0) {
                // Use a record of integrals
                OrderMetaDO pointMeta = new OrderMetaDO();
                pointMeta.setMetaKey(OrderMetaKeyEnum.POINT.name());
                pointMeta.setMetaValue(consumerPoint + "");
                pointMeta.setOrderSn(orderDTO.getSn());
                pointMeta.setStatus(ServiceStatusEnum.NOT_APPLY.name());
                this.orderMetaManager.add(pointMeta);
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Record credits used");
            }

        } catch (Exception e) {
            logger.error("Error recording credits used", e);
        }

    }


    /**
     * Record of bonus points
     *
     * @param orderDTO
     */
    private void addGiftPoint(OrderDTO orderDTO) {
        try {
            // Record of bonus points
            OrderMetaDO giftPointMeta = new OrderMetaDO();
            giftPointMeta.setMetaKey(OrderMetaKeyEnum.GIFT_POINT.name());
            giftPointMeta.setMetaValue(orderDTO.getGiftPoint() + "");
            giftPointMeta.setOrderSn(orderDTO.getSn());
            giftPointMeta.setStatus(ServiceStatusEnum.NOT_APPLY.name());
            this.orderMetaManager.add(giftPointMeta);

            if (logger.isDebugEnabled()) {
                logger.debug("Bonus points completed");
            }
        } catch (Exception e) {
            logger.error("Error in recording bonus points", e);
        }

    }


    /**
     * Free coupons for storage
     *
     * @param orderDTO
     */
    private void addGiftCoupon(OrderDTO orderDTO) {
        try {


            // Free coupons for storage
            List<CouponVO> couponList = orderDTO.getGiftCouponList();
            OrderMetaDO couponMeta = new OrderMetaDO();
            couponMeta.setMetaKey(OrderMetaKeyEnum.COUPON.name());
            couponMeta.setMetaValue(JsonUtil.objectToJson(couponList));
            couponMeta.setOrderSn(orderDTO.getSn());
            couponMeta.setStatus(ServiceStatusEnum.NOT_APPLY.name());
            this.orderMetaManager.add(couponMeta);

            if (logger.isDebugEnabled()) {
                logger.debug("Coupon completion");
            }

        } catch (Exception e) {
            logger.error("Giving coupons wrong", e);
        }

    }


    /**
     * Keep track of how much you spend on coupons
     *
     * @param orderDTO
     */
    private void addCouponPrice(OrderDTO orderDTO) {
        try {

            // Keep track of how much you spend on coupons
            OrderMetaDO orderMeta = new OrderMetaDO();
            orderMeta.setMetaKey(OrderMetaKeyEnum.COUPON_PRICE.name());
            orderMeta.setMetaValue("" + orderDTO.getPrice().getCouponPrice());
            orderMeta.setOrderSn(orderDTO.getSn());
            orderMetaManager.add(orderMeta);

            if (logger.isDebugEnabled()) {
                logger.debug("Use the coupon amount to complete");
            }

        } catch (Exception e) {
            logger.error("Incorrect amount of coupon used", e);
        }

    }


    /**
     * Keep track of how much you spend on coupons
     *
     * @param orderDTO
     */
    private void addFullMinusPrice(OrderDTO orderDTO) {
        try {

            // Keep track of how much you spend on coupons
            OrderMetaDO orderMeta = new OrderMetaDO();
            orderMeta.setMetaKey(OrderMetaKeyEnum.FULL_MINUS.name());
            orderMeta.setMetaValue("" + orderDTO.getPrice().getFullMinus());
            orderMeta.setOrderSn(orderDTO.getSn());
            orderMetaManager.add(orderMeta);
            if (logger.isDebugEnabled()) {
                logger.debug("Order full amount of the warehouse completed");
            }
        } catch (Exception e) {
            logger.error("There is an error in warehousing the order full and the amount reduced", e);
        }

    }


    /***
     * The gifts into the Treasury
     * @param orderDTO
     */
    private void addGift(OrderDTO orderDTO) {

        try {
            // The gifts into the Treasury
            List<FullDiscountGiftDO> giftList = orderDTO.getGiftList();
            OrderMetaDO giftMeta = new OrderMetaDO();
            giftMeta.setMetaKey(OrderMetaKeyEnum.GIFT.name());
            giftMeta.setMetaValue(JsonUtil.objectToJson(giftList));
            giftMeta.setOrderSn(orderDTO.getSn());
            giftMeta.setStatus(ServiceStatusEnum.NOT_APPLY.name());
            this.orderMetaManager.add(giftMeta);

            if (logger.isDebugEnabled()) {
                logger.debug("Free gift storage completed");
            }

        } catch (Exception e) {
            logger.error("Gift entry error", e);
        }


    }


    /**
     * log
     *
     * @param orderDTO
     */
    private void addLog(OrderDTO orderDTO) {
        try {
            // Payment order
            PayLog payLog = new PayLog();
            payLog.setPayLogSn(tradeSnCreator.generatePayLogSn());
            payLog.setOrderSn(orderDTO.getSn());
            payLog.setPayMemberName(orderDTO.getMemberName());
            payLog.setPayStatus(PayStatusEnum.PAY_NO.name());
            payLog.setPayWay(orderDTO.getPaymentType());

            this.payLogManager.add(payLog);

            if (logger.isDebugEnabled()) {
                logger.debug("Logs are imported into the database");
            }

        } catch (Exception e) {
            logger.error("The log entry failed. Procedure", e);
        }

    }

}
