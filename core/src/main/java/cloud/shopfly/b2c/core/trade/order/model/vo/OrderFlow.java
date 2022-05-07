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
package cloud.shopfly.b2c.core.trade.order.model.vo;

import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;

import java.util.List;

/**
 * An order flow object that defines an order flow chart
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-03-11
 */

public class OrderFlow {

    /**
     * Payment delivery process
     */
    private static List<OrderFlowNode> PAY_FIRST_FLOW;

    /**
     * Cash on delivery process
     */
    private static List<OrderFlowNode> COD_FLOW;

    /**
     * Spell the group process
     */
    private static List<OrderFlowNode> PINTUAN_FLOW;

    /**
     * Cancel the process
     */
    private static List<OrderFlowNode> CANCEL_FLOW;

    /**
     * Outbound failure process
     */
    private static List<OrderFlowNode> INTODB_ERROR_FLOW;

    static {
        initFlow();
    }

    /**
     * Initialize the
     */
    private static void initFlow() {

        // The process of delivery upon payment
        PAY_FIRST_FLOW = new MyList()
                .add(OrderStatusEnum.NEW)
                .add(OrderStatusEnum.CONFIRM)
                .add(OrderStatusEnum.PAID_OFF)
                .add(OrderStatusEnum.SHIPPED)
                .add(OrderStatusEnum.ROG)
                .add(OrderStatusEnum.COMPLETE)
                .getList();

        // Cash on delivery process
        COD_FLOW = new MyList()
                .add(OrderStatusEnum.NEW)
                .add(OrderStatusEnum.CONFIRM)
                .add(OrderStatusEnum.SHIPPED)
                .add(OrderStatusEnum.ROG)
                .add(OrderStatusEnum.PAID_OFF)
                .add(OrderStatusEnum.COMPLETE)
                .getList();


        // Spell the group process
        PINTUAN_FLOW = new MyList()
                .add(OrderStatusEnum.NEW)
                .add(OrderStatusEnum.CONFIRM)
                .add(OrderStatusEnum.PAID_OFF)
                .add(OrderStatusEnum.FORMED)
                .add(OrderStatusEnum.SHIPPED)
                .add(OrderStatusEnum.ROG)
                .add(OrderStatusEnum.COMPLETE)
                .getList();


        // The process of delivery upon payment
        CANCEL_FLOW = new MyList()
                .add(OrderStatusEnum.NEW)
                .add(OrderStatusEnum.CONFIRM)
                .add(OrderStatusEnum.CANCELLED)
                .getList();

        // Outbound failure process
        INTODB_ERROR_FLOW = new MyList()
                .add(OrderStatusEnum.NEW)
                .add(OrderStatusEnum.INTODB_ERROR)
                .getList();

    }


    /**
     * Get the corresponding order process
     *
     * @param orderType   Order type
     * @param paymentType Type of payment
     * @return
     */
    public static List<OrderFlowNode> getFlow(String orderType, String paymentType) {
        // Group orders are returned to the group process directly
        if (orderType.equals(OrderTypeEnum.pintuan.name())) {
            return PINTUAN_FLOW;
        }


        // Online payment is the payment to delivery process
        if (PaymentTypeEnum.ONLINE.name().equals(paymentType)) {
            return PAY_FIRST_FLOW;
        }

        // Cash on delivery process
        if (PaymentTypeEnum.COD.name().equals(paymentType)) {
            return COD_FLOW;
        }

        return null;
    }

    /**
     * Get the cancellation process
     *
     * @return
     */
    public static List<OrderFlowNode> getCancelFlow() {
        return CANCEL_FLOW;
    }

    /**
     * Get the outbound failure process
     *
     * @return
     */
    public static List<OrderFlowNode> getIntodbErrorFlow() {
        return INTODB_ERROR_FLOW;
    }

}
