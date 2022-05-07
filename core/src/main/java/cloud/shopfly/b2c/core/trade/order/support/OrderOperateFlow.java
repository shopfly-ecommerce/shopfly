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
package cloud.shopfly.b2c.core.trade.order.support;

import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOperateEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingapex on 2019-01-28.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-28
 */
public class OrderOperateFlow {

    /**
     * Cash on delivery process
     */
    public static final Map<OrderStatusEnum, OrderStep> COD_FLOW = new HashMap<OrderStatusEnum, OrderStep>();

    /**
     * Payment delivery process
     */
    public static final Map<OrderStatusEnum, OrderStep> PAY_FIRST_FLOW = new HashMap<OrderStatusEnum, OrderStep>();

    /**
     * Group order process
     */
    public static final Map<OrderStatusEnum, OrderStep> PINTUAN_FLOW = new HashMap<OrderStatusEnum, OrderStep>();


    // Define the process
    static {

        // Define the cash on delivery process
        initCodFlow();

        // Define payment to delivery process
        initPayFirstFlow();

        // Define the group process
        initPinTuanFlow();
    }


    public static Map<OrderStatusEnum, OrderStep> getFlow(PaymentTypeEnum paymentType, OrderTypeEnum orderType) {

        // Group orders are returned to the group process directly
        if (orderType.equals(OrderTypeEnum.pintuan)) {
            return PINTUAN_FLOW;
        }


        // Online payment is the payment to delivery process
        if (PaymentTypeEnum.ONLINE.equals(paymentType)) {
            return PAY_FIRST_FLOW;
        }

        // Cash on delivery process
        if (PaymentTypeEnum.COD.equals(paymentType)) {
            return COD_FLOW;
        }

        return null;
    }


    private static void initPinTuanFlow() {

        // Clone a copy of the delivery process to the group process
        PAY_FIRST_FLOW.forEach((k, v) -> PINTUAN_FLOW.put(k, (OrderStep) v.clone()));

        // Group payment status cannot be shipped, you need to join a "group already formed".
        OrderStep payStep = PINTUAN_FLOW.get(OrderStatusEnum.PAID_OFF);
        List<OrderOperateEnum> operate = payStep.getOperate();
        operate.remove(OrderOperateEnum.SHIP);

        // Delivery can only be made when the package is assembled
        OrderStep formedStep = new OrderStep(OrderStatusEnum.FORMED, OrderOperateEnum.SHIP);
        PINTUAN_FLOW.put(OrderStatusEnum.FORMED, formedStep);
    }


    /**
     * Define payment to delivery process
     */
    private static void initPayFirstFlow() {

        // New orders can be confirmed or cancelled
        OrderStep newStep = new OrderStep(OrderStatusEnum.NEW, OrderOperateEnum.CONFIRM, OrderOperateEnum.CANCEL);
        PAY_FIRST_FLOW.put(OrderStatusEnum.NEW, newStep);

        // Confirmed orders can be paid or cancelled
        OrderStep confirmStep = new OrderStep(OrderStatusEnum.CONFIRM, OrderOperateEnum.PAY, OrderOperateEnum.CANCEL);
        PAY_FIRST_FLOW.put(OrderStatusEnum.CONFIRM, confirmStep);

        // It has been paid and can be shipped
        OrderStep payStep = new OrderStep(OrderStatusEnum.PAID_OFF, OrderOperateEnum.SHIP);
        PAY_FIRST_FLOW.put(OrderStatusEnum.PAID_OFF, payStep);

        // Delivery orders can confirm receipt of goods
        OrderStep shipStep = new OrderStep(OrderStatusEnum.SHIPPED, OrderOperateEnum.ROG);
        PAY_FIRST_FLOW.put(OrderStatusEnum.SHIPPED, shipStep);

        // Receiving orders can be completed
        OrderStep rogStep = new OrderStep(OrderStatusEnum.ROG, OrderOperateEnum.COMPLETE);
        PAY_FIRST_FLOW.put(OrderStatusEnum.ROG, rogStep);

        // After sales orders can be completed
        OrderStep refundStep = new OrderStep(OrderStatusEnum.AFTER_SERVICE, OrderOperateEnum.COMPLETE);
        PAY_FIRST_FLOW.put(OrderStatusEnum.AFTER_SERVICE, refundStep);


        // No action can be taken on cancelled orders
        OrderStep cancelStep = new OrderStep(OrderStatusEnum.CANCELLED);
        PAY_FIRST_FLOW.put(OrderStatusEnum.CANCELLED, cancelStep);

        // No action can be taken on abnormal orders
        OrderStep errorStep = new OrderStep(OrderStatusEnum.INTODB_ERROR);
        PAY_FIRST_FLOW.put(OrderStatusEnum.INTODB_ERROR, errorStep);

        // No action can be taken on completed orders
        OrderStep completeStep = new OrderStep(OrderStatusEnum.COMPLETE);
        PAY_FIRST_FLOW.put(OrderStatusEnum.COMPLETE, completeStep);
    }

    /**
     * Define the cash on delivery process
     */
    private static void initCodFlow() {
        // New orders can be confirmed or cancelled
        OrderStep newStep = new OrderStep(OrderStatusEnum.NEW, OrderOperateEnum.CONFIRM, OrderOperateEnum.CANCEL);
        COD_FLOW.put(OrderStatusEnum.NEW, newStep);

        // Confirmed orders can be shipped or cancelled
        OrderStep confirmStep = new OrderStep(OrderStatusEnum.CONFIRM, OrderOperateEnum.SHIP, OrderOperateEnum.CANCEL);
        COD_FLOW.put(OrderStatusEnum.CONFIRM, confirmStep);

        // Delivery orders can confirm receipt of goods
        OrderStep shipStep = new OrderStep(OrderStatusEnum.SHIPPED, OrderOperateEnum.ROG);
        COD_FLOW.put(OrderStatusEnum.SHIPPED, shipStep);

        // Order to receive goods
        OrderStep rogStep = new OrderStep(OrderStatusEnum.ROG, OrderOperateEnum.PAY);
        COD_FLOW.put(OrderStatusEnum.ROG, rogStep);

        // Payment has been made and can be completed
        OrderStep payStep = new OrderStep(OrderStatusEnum.PAID_OFF, OrderOperateEnum.COMPLETE);
        COD_FLOW.put(OrderStatusEnum.PAID_OFF, payStep);

        // After sales orders can be completed
        OrderStep refundStep = new OrderStep(OrderStatusEnum.AFTER_SERVICE, OrderOperateEnum.COMPLETE);
        COD_FLOW.put(OrderStatusEnum.AFTER_SERVICE, refundStep);

        // No action can be taken on cancelled orders
        OrderStep cancelStep = new OrderStep(OrderStatusEnum.CANCELLED);
        COD_FLOW.put(OrderStatusEnum.CANCELLED, cancelStep);

        // No action can be taken on completed orders
        OrderStep completeStep = new OrderStep(OrderStatusEnum.COMPLETE);
        COD_FLOW.put(OrderStatusEnum.COMPLETE, completeStep);

        // No action can be taken on abnormal orders
        OrderStep errorStep = new OrderStep(OrderStatusEnum.INTODB_ERROR);
        COD_FLOW.put(OrderStatusEnum.INTODB_ERROR, errorStep);
    }

}
