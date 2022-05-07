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
package cloud.shopfly.b2c.core.trade.order.service.impl;

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderLogDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.TradeDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import cloud.shopfly.b2c.core.trade.order.model.vo.*;
import cloud.shopfly.b2c.core.trade.order.service.*;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.trade.TradeErrorCode;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;

import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import cloud.shopfly.b2c.core.trade.order.model.vo.*;
import cloud.shopfly.b2c.core.trade.order.service.*;
import cloud.shopfly.b2c.core.trade.order.support.OrderOperateChecker;
import cloud.shopfly.b2c.core.trade.order.support.OrderOperateFlow;
import cloud.shopfly.b2c.core.trade.order.support.OrderStep;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderSkuDTO;
import cloud.shopfly.b2c.framework.context.AdminUserContext;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Order flow operation
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class OrderOperateManagerImpl implements OrderOperateManager {


    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private OrderQueryManager orderQueryManager;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private OrderLogManager orderLogManager;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private TradeQueryManager tradeQueryManager;

    @Autowired
    private TradePriceManager tradePriceManager;

    @Autowired
    private OrderMetaManager orderMetaManager;


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void confirm(ConfirmVO confirmVO, OrderPermission permission) {
        executeOperate(confirmVO.getOrderSn(), permission, OrderOperateEnum.CONFIRM, confirmVO);
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public OrderDO payOrder(String orderSn, Double payPrice, String returnTradeNo, OrderPermission permission) {

        PayParam payParam = new PayParam();
        payParam.setPayPrice(payPrice);
        payParam.setReturnTradeNo(returnTradeNo);

        executeOperate(orderSn, permission, OrderOperateEnum.PAY, payParam);
        return null;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void ship(DeliveryVO deliveryVO, OrderPermission permission) {

        String orderSn = deliveryVO.getOrderSn();
        executeOperate(orderSn, permission, OrderOperateEnum.SHIP, deliveryVO);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void rog(RogVO rogVO, OrderPermission permission) {

        String orderSn = rogVO.getOrderSn();
        executeOperate(orderSn, permission, OrderOperateEnum.ROG, rogVO);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void cancel(CancelVO cancelVO, OrderPermission permission) {

        String orderSn = cancelVO.getOrderSn();
        executeOperate(orderSn, permission, OrderOperateEnum.CANCEL, cancelVO);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void complete(CompleteVO completeVO, OrderPermission permission) {
        String orderSn = completeVO.getOrderSn();
        executeOperate(orderSn, permission, OrderOperateEnum.COMPLETE, completeVO);
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateServiceStatus(String orderSn, ServiceStatusEnum serviceStatus) {
        String sql = "update es_order set service_status = ?  where sn = ? ";
        this.daoSupport.execute(sql, serviceStatus.value(), orderSn);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public OrderConsigneeVO updateOrderConsignee(OrderConsigneeVO orderConsignee) {

        OrderDO orderDO = this.orderQueryManager.getDoByOrderSn(orderConsignee.getOrderSn());

        orderDO.setShipCountry(orderConsignee.getShipCountry());
        orderDO.setShipCountryCode(orderConsignee.getShipCountryCode());
        orderDO.setShipState(orderConsignee.getShipState());
        orderDO.setShipStateCode(orderConsignee.getShipStateCode());
        orderDO.setShipCity(orderConsignee.getShipCity());
        orderDO.setShipAddr(orderConsignee.getShipAddr());
        orderDO.setShipMobile(orderConsignee.getShipMobile());
        orderDO.setShipZip(orderConsignee.getShipZip());
        orderDO.setReceiveTime(orderConsignee.getReceiveTime());
        orderDO.setShipName(orderConsignee.getShipName());
        orderDO.setRemark(orderConsignee.getRemark());

        this.orderManager.update(orderDO);
        return orderConsignee;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateOrderPrice(String orderSn, Double orderPrice) {

        // The revised order price cannot be less than or equal to 0
        if (orderPrice <= 0) {
            throw new ServiceException(TradeErrorCode.E471.code(), "The order amount must be greater than0");
        }

        // Get order details
        OrderDetailVO orderDetailVO = this.orderQueryManager.getModel(orderSn, null);

        // Judgment of order authority
        this.checkPermission(OrderPermission.seller, orderDetailVO);

        // Obtain transaction order information
        TradeDO tradeDO = tradeQueryManager.getModel(orderDetailVO.getTradeSn());

        // Obtain the original order amount
        Double oldPrice = orderDetailVO.getOrderPrice();
        // Calculate the difference between the original order amount and the revised order amount
        Double differencePrice = CurrencyUtil.sub(oldPrice, orderPrice);
        // Total transaction price = original transaction price - balance
        Double tradePrice = CurrencyUtil.sub(tradeDO.getTotalPrice(), differencePrice);
        // Total discount = original discount amount - balance
        Double discountPrice = CurrencyUtil.add(tradeDO.getDiscountPrice(), differencePrice);

        // If the total number of offers is less than 0, set the total number of offers to 0
        if (discountPrice < 0) {
            discountPrice = 0.0;
        }

        // Modify transaction price
        this.tradePriceManager.updatePrice(tradeDO.getTradeSn(), tradePrice, discountPrice);

        // Discount amount after the order price modification = original discount amount of the order - the difference between the price modification
        Double orderDiscoutPrice = CurrencyUtil.add(orderDetailVO.getDiscountPrice(), differencePrice);
        // If the discount amount after the revised price of the order is less than 0, the discount amount after the revised price of the order is set to 0 to prevent the cashback amount and the total price of the product from being negative when the total price of the revised order is higher than the original price
        if (orderDiscoutPrice < 0) {
            orderDiscoutPrice = 0.0;
        }

        // Modify the order metadata information, which is what you did to correctly calculate the amount of the refund
        Double fullMinus = Double.valueOf(this.orderMetaManager.getMetaValue(orderSn, OrderMetaKeyEnum.FULL_MINUS));
        Double cashBack = Double.valueOf(this.orderMetaManager.getMetaValue(orderSn, OrderMetaKeyEnum.CASH_BACK));


        // Modify order price
        String sql = "update es_order set order_price = ? ,need_pay_money = ? ,discount_price = ?  where sn = ?";
        this.daoSupport.execute(sql, orderPrice, orderPrice, orderDiscoutPrice, orderSn);

        this.orderMetaManager.updateMetaValue(orderSn, OrderMetaKeyEnum.FULL_MINUS, CurrencyUtil.add(fullMinus, differencePrice).toString());
        this.orderMetaManager.updateMetaValue(orderSn, OrderMetaKeyEnum.CASH_BACK, CurrencyUtil.add(cashBack, differencePrice).toString());

        // Recording Operation Logs
        OrderLogDO orderLogDO = new OrderLogDO();
        orderLogDO.setMessage("The merchant modifies the order price");
        orderLogDO.setOrderSn(orderSn);
        orderLogDO.setOpName("The administrator");
        orderLogDO.setOpTime(DateUtil.getDateline());
        this.orderLogManager.add(orderLogDO);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateCommentStatus(String orderSn, CommentStatusEnum commentStatus) {
        String sql = "update es_order set comment_status = ? where sn = ? ";
        this.daoSupport.execute(sql, commentStatus.name(), orderSn);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateItemJson(String itemsJson, String orderSn) {
        String sql = "update es_order set items_json = ? where  sn = ? ";
        this.daoSupport.execute(sql, itemsJson, orderSn);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateOrderStatus(String orderSn, OrderStatusEnum orderStatus) {

        StringBuffer sqlBuffer = new StringBuffer("update es_order set order_status = ? ");

        if (OrderStatusEnum.PAID_OFF.equals(orderStatus)) {
            sqlBuffer.append(",pay_status = '" + PayStatusEnum.PAY_YES.value() + "'");
        }

        sqlBuffer.append(" where sn = ? ");

        this.daoSupport.execute(sqlBuffer.toString(), orderStatus.value(), orderSn);
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void executeOperate(String orderSn, OrderPermission permission, OrderOperateEnum orderOperate, Object paramVO) {
        // Get this order
        OrderDetailVO orderDetailVO = orderQueryManager.getModel(orderSn, null);

        // 1. Verify the operators permission
        this.checkPermission(permission, orderDetailVO);

        // 2. Verify the operations that can be performed on this order
        this.checkAllowable(permission, orderDetailVO, orderOperate);

        long nowTime = DateUtil.getDateline();

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderDetailVO, orderDO);

        // Order status to be changed
        OrderStatusEnum newStatus = null;

        // Log information
        String logMessage = "Operational information";

        String operator = "The system default";

        switch (orderOperate) {
            case CONFIRM:

                ConfirmVO confirmVO = (ConfirmVO) paramVO;
                logMessage = "Make sure the order";
                newStatus = OrderStatusEnum.CONFIRM;
                this.daoSupport.execute("update es_order set order_status=?  where sn=? ", OrderStatusEnum.CONFIRM.value(),
                        confirmVO.getOrderSn());
                orderDO.setOrderStatus(OrderStatusEnum.CONFIRM.name());
                break;

            case PAY:

                logMessage = "Payment order";
                newStatus = OrderStatusEnum.PAID_OFF;
                PayParam payParam = (PayParam) paramVO;
                Double payPrice = payParam.getPayPrice();
                String returnTradeNo = payParam.getReturnTradeNo();

                switch (permission) {
                    case buyer:
                        operator = orderDetailVO.getMemberName();
                        break;

                    case client:
                        break;

                    case seller:
                        operator = orderDetailVO.getSellerName();
                        break;

                    case admin:
                        operator = AdminUserContext.getAdmin().getUsername();
                        // Click on the background to confirm payment and clear the payment method
                        this.daoSupport.execute("update es_order set payment_plugin_id = null,payment_method_name=null where sn=?", orderDO.getSn());
                        break;
                    default:
                        break;
                }

                // The seller cannot confirm the payment of the delivery order
                if (permission.equals(OrderPermission.seller) && orderDO.getPaymentType().equals(PaymentTypeEnum.ONLINE.name())) {
                    throw new NoPermissionException("You are not authorized to execute this order");
                }
                // The payment amount is not equal to the order amount
                if (payPrice.compareTo(orderDO.getNeedPayMoney()) != 0) {
                    throw new ServiceException(TradeErrorCode.E454.code(), "The amount paid is not the same as the amount due");
                }

                // Judge payment method
                if (orderDO.getPaymentType().equals(PaymentTypeEnum.COD.value())) {
                    this.daoSupport.execute("update es_order set order_status=? ,pay_status=?,ship_status = ? ,pay_money=? ,payment_time = ? where sn=? ",
                            OrderStatusEnum.PAID_OFF.value(), PayStatusEnum.PAY_YES.value(), ShipStatusEnum.SHIP_ROG.value(), payPrice, nowTime, orderDO.getSn());
                    orderDO.setShipStatus(ShipStatusEnum.SHIP_ROG.value());
                } else {
                    this.daoSupport.execute("update es_order set order_status=? ,pay_status=?,service_status=? ,pay_money=? ,payment_time = ? ,pay_order_no=? where sn=? ",
                            OrderStatusEnum.PAID_OFF.value(), PayStatusEnum.PAY_YES.value(), ServiceStatusEnum.EXPIRED.value(), payPrice, nowTime, returnTradeNo, orderDO.getSn());
                    orderDO.setServiceStatus(ServiceStatusEnum.EXPIRED.value());
                }
                this.daoSupport.execute("update es_trade set trade_status=? where trade_sn=?", TradeStatusEnum.PAID_OFF.value(), orderDO.getTradeSn());

                orderDO.setOrderStatus(OrderStatusEnum.PAID_OFF.name());
                orderDO.setPayStatus(PayStatusEnum.PAY_YES.value());
                orderDO.setPayMoney(payPrice);
                orderDO.setPaymentTime(nowTime);

                break;
            case SHIP:

                // Check whether the order has been applied for after-sale
                if (ServiceStatusEnum.APPLY.name().equals(orderDO.getServiceStatus())
                        || ServiceStatusEnum.PASS.name().equals(orderDO.getServiceStatus())) {
                    throw new ServiceException(TradeErrorCode.E455.code(), "The order has been refunded and cannot be shipped");
                }

                DeliveryVO deliveryVO = (DeliveryVO) paramVO;
                logMessage = "Orders for shipment";
                newStatus = OrderStatusEnum.SHIPPED;
                operator = deliveryVO.getOperator();

                this.daoSupport.execute("update es_order set order_status=? ,ship_status=?,service_status=?,ship_no=? ,ship_time = ?,logi_id=?,logi_name=? where sn=? ",
                        OrderStatusEnum.SHIPPED.value(), ShipStatusEnum.SHIP_YES.value(), ServiceStatusEnum.NOT_APPLY.value(), deliveryVO.getDeliveryNo(), nowTime,
                        deliveryVO.getLogiId(), deliveryVO.getLogiName(), orderDetailVO.getSn());

                orderDO.setOrderStatus(OrderStatusEnum.SHIPPED.name());
                orderDO.setShipStatus(ShipStatusEnum.SHIP_YES.value());
                orderDO.setServiceStatus(ServiceStatusEnum.NOT_APPLY.value());
                orderDO.setShipNo(deliveryVO.getDeliveryNo());
                orderDO.setShipTime(nowTime);
                orderDO.setLogiId(deliveryVO.getLogiId());
                orderDO.setLogiName(deliveryVO.getLogiName());

                break;
            case ROG:

                RogVO rogVO = (RogVO) paramVO;
                logMessage = "Confirm the goods";
                newStatus = OrderStatusEnum.ROG;
                operator = rogVO.getOperator();

                // After-sale status of orders
                String orderServiceStatus = ServiceStatusEnum.EXPIRED.value();

                this.daoSupport.execute("update es_order set order_status=? ,ship_status=?,service_status=?,signing_time = ?  where sn=? ",
                        OrderStatusEnum.ROG.value(), ShipStatusEnum.SHIP_ROG.value(), orderServiceStatus, nowTime, orderDO.getSn());

                orderDO.setOrderStatus(OrderStatusEnum.ROG.name());
                orderDO.setShipStatus(ShipStatusEnum.SHIP_ROG.value());
                orderDO.setServiceStatus(orderServiceStatus);
                orderDO.setSigningTime(nowTime);

                break;

            case CANCEL:

                CancelVO cancelVO = (CancelVO) paramVO;
                logMessage = "Cancel the order";
                newStatus = OrderStatusEnum.CANCELLED;
                operator = cancelVO.getOperator();

                this.daoSupport.execute("update es_order set order_status=? , cancel_reason=? where sn=? ",
                        OrderStatusEnum.CANCELLED.value(), cancelVO.getReason(), orderDO.getSn());
                orderDO.setOrderStatus(OrderStatusEnum.CANCELLED.name());
                orderDO.setCancelReason(cancelVO.getReason());
                break;

            case COMPLETE:

                CompleteVO completeVO = (CompleteVO) paramVO;
                logMessage = "The order has been completed";
                newStatus = OrderStatusEnum.COMPLETE;
                operator = completeVO.getOperator();

                this.daoSupport.execute("update es_order set order_status=?,complete_time=?  where sn=? ", OrderStatusEnum.COMPLETE.value(),
                        nowTime, orderSn);
                orderDO.setOrderStatus(OrderStatusEnum.COMPLETE.name());
                orderDO.setCompleteTime(nowTime);

                break;

            default:
                break;
        }

        OrderStatusChangeMsg message = new OrderStatusChangeMsg();
        message.setOrderDO(orderDO);
        message.setOldStatus(OrderStatusEnum.valueOf(orderDO.getOrderStatus()));
        message.setNewStatus(newStatus);
        // Send the order status change message
        this.messageSender.send(new MqMessage(AmqpExchange.ORDER_STATUS_CHANGE, "order-change-queue", message));

        // log
        OrderLogDO orderLogDO = new OrderLogDO();
        orderLogDO.setMessage(logMessage);
        orderLogDO.setOrderSn(orderSn);
        orderLogDO.setOpName(operator);
        orderLogDO.setOpTime(DateUtil.getDateline());
        this.orderLogManager.add(orderLogDO);

    }

    @Override
    public void updateTradeStatus(String sn, OrderStatusEnum orderStatus) {
        String sql = "update es_trade set trade_status = ? where trade_sn = ?";
        this.daoSupport.execute(sql, orderStatus.value(), sn);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateItemRefundPrice(OrderDetailVO order) {
        // Get the full discount amount of the order
        double fullMinus = order.getFullMinus();
        // Get the total amount of order coupon offers
        double couponPrice = order.getCouponPrice();
        // Gets the collection of ordered items
        List<OrderSkuVO> skuVOList = order.getOrderSkuList();

        // The total amount of goods participating in the full reduction promotion activities (the total amount of the full reduction promotion has not been deducted)
        double fmTotal = 0.00;
        // The total amount of all items in the order (before deducting the total amount of full discounts and coupon offers)
        double allTotal = 0.00;
        // Order a collection of items that participate in a full reduction promotion
        List<OrderSkuVO> fmList = new ArrayList<>();
        // A collection of items for which the order did not participate in the full reduction promotion
        List<OrderSkuVO> noFmList = new ArrayList<>();

        for (OrderSkuVO orderSkuVO : skuVOList) {
            if (orderSkuVO.getGroupList() != null && orderSkuVO.getGroupList().size() != 0) {
                fmTotal = CurrencyUtil.add(fmTotal, orderSkuVO.getSubtotal());
                fmList.add(orderSkuVO);
            } else {
                noFmList.add(orderSkuVO);
            }

            allTotal = CurrencyUtil.add(allTotal, orderSkuVO.getSubtotal());
        }

        // The order did not participate in the full discount program and did not use coupons
        if (fmList.size() == 0 && couponPrice == 0 && noFmList.size() != 0) {
            updateItemRefundPrice(order.getSn(), skuVOList);

            // All the ordered goods participated in the full reduction activity without using coupons
        } else if (fmList.size() != 0 && couponPrice == 0 && noFmList.size() == 0) {
            updateFmItemsRefundPrice(order.getSn(), fmList, fullMinus, fmTotal);

            // Part of the order products participate in the full reduction activities, and part of the order products do not participate in the full reduction activities and do not use coupons
        } else if (fmList.size() != 0 && couponPrice == 0 && noFmList.size() != 0) {
            updateFmItemsRefundPrice(order.getSn(), fmList, fullMinus, fmTotal);

            updateItemRefundPrice(order.getSn(), noFmList);

            // All the ordered goods participated in the full reduction activity and used the coupon
        } else if (fmList.size() != 0 && couponPrice != 0 && noFmList.size() == 0) {
            updateFmItemsRefundPrice(order.getSn(), fmList, CurrencyUtil.add(fullMinus, couponPrice), fmTotal);

            // Part of the order participated in the full reduction activity, and part of the order did not participate in the full reduction activity and used coupons
        } else if (fmList.size() != 0 && couponPrice != 0 && noFmList.size() != 0) {
            updateFmCouponItemsRefundPrice(order.getSn(), fmList, noFmList, couponPrice, allTotal, fullMinus, fmTotal);

            // All the goods in the order did not participate in the full reduction activity and used the coupon
        } else if (fmList.size() == 0 && couponPrice != 0 && noFmList.size() != 0) {
            // Obtain the value -1 of the total number of goods not participating in the full reduction activity (the problem that the sum of refund amount of multiple goods is inconsistent with the refund amount due to the fact that the proportion of refund amount is not divisible for compatibility)
            int noFmNum = noFmList.size() - 1;

            // Total amount of coupons for remaining orders
            double surplusCouponPrice = couponPrice;

            updateCouponItemRefundPrice(order.getSn(), noFmList, couponPrice, allTotal, noFmNum, surplusCouponPrice);
        }
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateOrderServiceStatus(String orderSn, String statusEnum) {

        ServiceStatusEnum serviceStatusEnum = ServiceStatusEnum.valueOf(statusEnum);
        this.updateServiceStatus(orderSn, serviceStatusEnum);

        OrderDetailVO orderDetailVO = this.orderQueryManager.getModel(orderSn, null);
        List<OrderSkuVO> orderSkuVOList = orderDetailVO.getOrderSkuList();
        for (OrderSkuVO orderSkuVO : orderSkuVOList) {
            orderSkuVO.setServiceStatus(serviceStatusEnum.name());
        }
        this.updateItemJson(JsonUtil.objectToJson(orderSkuVOList), orderSn);

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateOrderItemServiceStatus(String sn, List<OrderSkuDTO> orderSkuDTOList) {
        OrderDetailVO orderDetailVO = this.orderQueryManager.getModel(sn, null);
        List<OrderSkuVO> orderSkuVOList = orderDetailVO.getOrderSkuList();
        for (OrderSkuVO orderSkuVO : orderSkuVOList) {
            for (OrderSkuDTO orderSkuDTO : orderSkuDTOList) {
                if (orderSkuVO.getSkuId().equals(orderSkuDTO.getSkuId())) {
                    orderSkuVO.setServiceStatus(orderSkuDTO.getServiceStatus());
                }
            }
        }
        this.updateItemJson(JsonUtil.objectToJson(orderSkuVOList), sn);
    }

    @Override
    public void updateOrderPayOrderNo(String payOrderNo, String sn) {
        String sql = "update es_order set pay_order_no = ? where sn = ?";
        this.daoSupport.execute(sql, payOrderNo, sn);
    }


    /**
     * Modify the refund amount of the order item
     * Call that：1.For all the goods in the order did not participate in the full reduction activities and did not use coupons
     * 2.For some commodities in the order, they did not participate in the full reduction activities and did not use coupons
     *
     * @param orderSn   Order no.
     * @param skuVOList Order data
     */
    private void updateItemRefundPrice(String orderSn, List<OrderSkuVO> skuVOList) {
        for (OrderSkuVO orderSkuVO : skuVOList) {
            double refundPrice = orderSkuVO.getSubtotal();
            updateRefundPrice(orderSn, orderSkuVO, refundPrice);
        }
    }

    /**
     * Modify the refund amount of the order item
     * Call that：1.For all products in the order to participate in the full reduction promotion activities and did not use coupons
     * 2.For all products in the order to participate in the full reduction promotion and use of coupons
     * 3.Part of the goods in the order participated in the full reduction promotion and did not use the coupon
     *
     * @param orderSn   Order no.
     * @param fmList    A collection of ordered items that participate in the full reduction promotion
     * @param fullMinus The total amount that has been reduced
     * @param fmTotal   Total amount of goods participating in the full reduction promotion（The total amount of the full discount has not been deducted）
     */
    private void updateFmItemsRefundPrice(String orderSn, List<OrderSkuVO> fmList, double fullMinus, double fmTotal) {
        // Obtain the value -1 of the total number of commodities participating in the full reduction activity (the problem that the sum of refund amount of several commodities is inconsistent with the refund amount due to the fact that the proportion of refund amount is not divisible for compatibility)
        int num = fmList.size() - 1;
        // The total amount of remaining orders is reduced
        double surplusFmPrice = fullMinus;

        for (int i = 0; i < fmList.size(); i++) {
            OrderSkuVO orderSkuVO = fmList.get(i);
            // Amount refundable for current merchandise
            double refundPrice = 0.00;

            if (i != num) {
                // Gets the percentage of the current full reduction of the item
                double fmRatioPrice = CurrencyUtil.mul(CurrencyUtil.div(orderSkuVO.getSubtotal(), fmTotal, 4), fullMinus);
                // Amount of current goods to be refunded = Total amount - proportion of the amount fully reduced
                refundPrice = CurrencyUtil.sub(orderSkuVO.getSubtotal(), fmRatioPrice);
                // Calculate the remaining total full reduction
                surplusFmPrice = CurrencyUtil.sub(surplusFmPrice, fmRatioPrice);
            } else {
                // Amount of current goods due for refund = Total amount - total amount of remaining full reduction
                refundPrice = CurrencyUtil.sub(orderSkuVO.getSubtotal(), surplusFmPrice);
            }

            updateRefundPrice(orderSn, orderSkuVO, refundPrice);
        }
    }

    /**
     * Modify the refund amount of the order item
     * Call that：Part of the goods in the order participated in the maximum reduction promotion activities, and part of the goods did not participate in the maximum reduction promotion activities and also used coupons
     *
     * @param orderSn     Order no.
     * @param fmList      A collection of ordered items that participate in the full reduction promotion
     * @param noFmList    Collection of order items not participating in the full reduction promotion
     * @param couponPrice The amount of coupon used for the order
     * @param allTotal    Total amount of all goods（The total amount of full discounts and coupon offers has not been subtracted）
     * @param fullMinus   The total amount that has been reduced
     * @param fmTotal     Total amount of goods participating in the full reduction promotion（The total amount of the full discount has not been deducted）
     */
    private void updateFmCouponItemsRefundPrice(String orderSn, List<OrderSkuVO> fmList, List<OrderSkuVO> noFmList, double couponPrice, double allTotal, double fullMinus, double fmTotal) {
        // Obtain the value -1 of the total number of commodities participating in the full reduction activity (the problem that the sum of refund amount of several commodities is inconsistent with the refund amount due to the fact that the proportion of refund amount is not divisible for compatibility)
        int fmMum = fmList.size() - 1;
        // Obtain the value -1 of the total number of goods not participating in the full reduction activity (the problem that the sum of refund amount of multiple goods is inconsistent with the refund amount due to the fact that the proportion of refund amount is not divisible for compatibility)
        int noFmNum = noFmList.size() - 1;

        // The total amount of remaining orders is reduced
        double surplusFmPrice = fullMinus;
        // Total amount of coupons for remaining orders
        double surplusCouponPrice = couponPrice;

        for (int i = 0; i < fmList.size(); i++) {
            OrderSkuVO orderSkuVO = fmList.get(i);
            double refundPrice = 0.00;

            if (i != fmMum) {
                // Gets the percentage of the current full reduction of the item
                double fmRatioPrice = CurrencyUtil.mul(CurrencyUtil.div(orderSkuVO.getSubtotal(), fmTotal, 4), fullMinus);
                // The percentage of current merchandise coupon offers received
                double couponRatioPrice = CurrencyUtil.mul(CurrencyUtil.div(orderSkuVO.getSubtotal(), allTotal, 4), couponPrice);
                // Current amount of goods to be refunded = Total amount -(proportion of the full amount + proportion of the coupon)
                refundPrice = CurrencyUtil.sub(orderSkuVO.getSubtotal(), CurrencyUtil.add(fmRatioPrice, couponRatioPrice));
                // Calculate the remaining total full reduction
                surplusFmPrice = CurrencyUtil.sub(surplusFmPrice, fmRatioPrice);
                // Calculate the total amount of coupon offers remaining
                surplusCouponPrice = CurrencyUtil.sub(surplusCouponPrice, couponRatioPrice);
            } else {
                // The percentage of current merchandise coupon offers received
                double couponRatioPrice = CurrencyUtil.mul(CurrencyUtil.div(orderSkuVO.getSubtotal(), allTotal, 4), couponPrice);
                // Current amount of goods to be refunded = Total amount -(percentage of discount coupon + total amount of remaining full reduction)
                refundPrice = CurrencyUtil.sub(orderSkuVO.getSubtotal(), CurrencyUtil.add(couponRatioPrice, surplusFmPrice));
                // Calculate the total amount of coupon offers remaining
                surplusCouponPrice = CurrencyUtil.sub(surplusCouponPrice, couponRatioPrice);
            }

            updateRefundPrice(orderSn, orderSkuVO, refundPrice);
        }

        updateCouponItemRefundPrice(orderSn, noFmList, couponPrice, allTotal, noFmNum, surplusCouponPrice);
    }


    /**
     * Modify the refund amount of the order item
     * Call that：1.Part of the goods in the order participated in the maximum reduction promotion activities, and part of the goods did not participate in the maximum reduction promotion activities and also used coupons
     * 2.In view of the situation that all the goods in the order did not participate in the full reduction activity and used the coupon
     *
     * @param orderSn            Order no.
     * @param noFmList           A collection of ordered items that did not participate in the full reduction promotion
     * @param couponPrice        The amount of coupon used for the order
     * @param allTotal           Total amount of all goods（The total amount of full discounts and coupon offers has not been subtracted）
     * @param noFmNum            Get the total number of goods not participating in the full reduction campaign-1The numerical（The problem that the sum of the refund amount of multiple goods is inconsistent with the refund amount because the proportion of the refund amount is not divisible）
     * @param surplusCouponPrice Total amount of coupons for remaining orders
     */
    private void updateCouponItemRefundPrice(String orderSn, List<OrderSkuVO> noFmList, double couponPrice, double allTotal, int noFmNum, double surplusCouponPrice) {
        for (int i = 0; i < noFmList.size(); i++) {
            OrderSkuVO orderSkuVO = noFmList.get(i);
            double refundPrice = 0.00;

            if (i != noFmNum) {
                // The percentage of current merchandise coupon offers received
                double couponRatioPrice = CurrencyUtil.mul(CurrencyUtil.div(orderSkuVO.getSubtotal(), allTotal, 4), couponPrice);
                // Current product refundable amount = Total amount - percentage of coupon offers
                refundPrice = CurrencyUtil.sub(orderSkuVO.getSubtotal(), couponRatioPrice);
                // Calculate the total amount of coupon offers remaining
                surplusCouponPrice = CurrencyUtil.sub(surplusCouponPrice, couponRatioPrice);
            } else {
                // Current item refundable amount = Total amount - Calculate the total amount of remaining coupon offers
                refundPrice = CurrencyUtil.sub(orderSkuVO.getSubtotal(), surplusCouponPrice);
            }

            updateRefundPrice(orderSn, orderSkuVO, refundPrice);
        }
    }

    /**
     * A public method for modifying the refundable amount of an order item
     *
     * @param orderSn     Order no.
     * @param orderSkuVO  Order Details
     * @param refundPrice Refundable amount
     */
    private void updateRefundPrice(String orderSn, OrderSkuVO orderSkuVO, double refundPrice) {
        String sql = "update es_order_items set refund_price = ? where order_sn = ? and goods_id = ? and product_id = ?";
        this.daoSupport.execute(sql, refundPrice, orderSn, orderSkuVO.getGoodsId(), orderSkuVO.getSkuId());
    }

    /**
     * Perform permission checks on orders to be acted on
     *
     * @param permission Required permissions
     * @param order      Corresponding order
     */
    private void checkPermission(OrderPermission permission, OrderDetailVO order) {

        if (permission != null) {
            if (order == null) {
                throw new NoPermissionException("You are not authorized to execute this order");
            }

            // Verify buyer authority
            if (permission.equals(OrderPermission.buyer)) {
                Buyer buyer = UserContext.getBuyer();
                if (buyer == null || buyer.getUid() == null
                        || buyer.getUid().intValue() != order.getMemberId().intValue()) {
                    throw new NoPermissionException("You are not authorized to execute this order");
                }
            }

            // Verify management rights
            if (permission.equals(OrderPermission.admin)) {

            }

            // Currently, the client does not have any permissions to calibrate tethers
            if (permission.equals(OrderPermission.client)) {

            }

        }
    }


    /**
     * Perform operational verification
     * See if this operation is allowed in this state
     *
     * @param order
     * @param orderOperate
     */
    private void checkAllowable(OrderPermission permission, OrderDetailVO order, OrderOperateEnum orderOperate) {
        // If the client permission is used, the next step is not verified
        if (OrderPermission.client.equals(permission)) {
            return;
        }

        OrderStatusEnum status = OrderStatusEnum.valueOf(order.getOrderStatus());

        PaymentTypeEnum paymentType = PaymentTypeEnum.valueOf(order.getPaymentType());

        Map<OrderStatusEnum, OrderStep> flow = OrderOperateFlow.getFlow(paymentType, OrderTypeEnum.valueOf(order.getOrderType()));
        OrderOperateChecker orderOperateChecker = new OrderOperateChecker(flow);

        boolean isAllowble = orderOperateChecker.checkAllowable(status, orderOperate);

        if (!isAllowble) {
            throw new ServiceException(TradeErrorCode.E460.code(), "The order" + status.description() + "State cannot proceed" + orderOperate.description() + "Operation");
        }

    }


    /**
     * Inner class, used for passing parameters
     */
    private class PayParam {
        private Double payPrice;
        private String returnTradeNo;

        public Double getPayPrice() {
            return payPrice;
        }

        public String getReturnTradeNo() {
            return returnTradeNo;
        }

        public void setPayPrice(Double payPrice) {
            this.payPrice = payPrice;
        }

        public void setReturnTradeNo(String returnTradeNo) {
            this.returnTradeNo = returnTradeNo;
        }
    }


}
