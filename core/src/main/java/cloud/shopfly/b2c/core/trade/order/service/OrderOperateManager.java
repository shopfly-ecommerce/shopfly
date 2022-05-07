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
package cloud.shopfly.b2c.core.trade.order.service;

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.CommentStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOperateEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.ServiceStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.*;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import cloud.shopfly.b2c.core.trade.order.model.vo.*;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderSkuDTO;

import java.util.List;

/**
 * Order flow operation
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
public interface OrderOperateManager {

    /**
     * Make sure the order
     *
     * @param confirmVO  Order confirmationvo
     * @param permission The order permission to check
     */
    void confirm(ConfirmVO confirmVO, OrderPermission permission);

    /**
     * Make payment for an order<br/>
     *
     * @param orderSn    The order number
     * @param payPrice   Amount of this payment
     * @param returnTradeNo   Amount of this payment
     * @param permission The order permission to check
     * @param permission permissions{@link OrderPermission}
     * @return
     * @throws IllegalArgumentException This exception is thrown in one of the following cases:
     *                                  <li>order_sn(The orderid)fornull</li>
     * @throws IllegalStateException    If the order payment status is paid
     */
    OrderDO payOrder(String orderSn, Double payPrice, String returnTradeNo, OrderPermission permission);


    /**
     * The delivery
     *
     * @param deliveryVO waybill</br>
     * @param permission The order permission to check
     */
    void ship(DeliveryVO deliveryVO, OrderPermission permission);


    /**
     * Order the goods
     *
     * @param rogVO      The goodsVO
     * @param permission The order permission to check
     */
    void rog(RogVO rogVO, OrderPermission permission);


    /**
     * Order cancellation
     *
     * @param cancelVO   cancelvo
     * @param permission The order permission to check
     */
    void cancel(CancelVO cancelVO, OrderPermission permission);

    /**
     * The order finished
     *
     * @param completeVO The order finishedvo
     * @param permission The order permission to check
     */
    void complete(CompleteVO completeVO, OrderPermission permission);

    /**
     * Update the after-sale status of orders
     *
     * @param orderSn
     * @param serviceStatus
     */
    void updateServiceStatus(String orderSn, ServiceStatusEnum serviceStatus);


    /**
     * Modify consignee information
     *
     * @param orderConsignee
     * @return
     */
    OrderConsigneeVO updateOrderConsignee(OrderConsigneeVO orderConsignee);

    /**
     * Modify order price
     *
     * @param orderSn
     * @param orderPrice
     */
    void updateOrderPrice(String orderSn, Double orderPrice);

    /**
     * Update the comment status of the order
     *
     * @param orderSn
     * @param commentStatus
     */
    void updateCommentStatus(String orderSn, CommentStatusEnum commentStatus);

    /**
     * Update order item snapshotID
     *
     * @param itemsJson
     * @param orderSn
     * @return
     */
    void updateItemJson(String itemsJson, String orderSn);

    /**
     * Update the order status of an order
     *
     * @param orderSn
     * @param orderStatus
     */
    void updateOrderStatus(String orderSn, OrderStatusEnum orderStatus);

    /**
     * Update transaction Status
     *
     * @param sn          tradingsn
     * @param orderStatus Status
     */
    void updateTradeStatus(String sn, OrderStatusEnum orderStatus);

    /**
     * Perform operations
     *
     * @param orderSn      Order no.
     * @param permission   permissions
     * @param orderOperate What operations do you want to perform
     * @param paramVO      Parameter object
     */
    void executeOperate(String orderSn, OrderPermission permission, OrderOperateEnum orderOperate, Object paramVO);

    /**
     * Update order item refundable amount
     *
     * @param order
     */
    void updateItemRefundPrice(OrderDetailVO order);

    /**
     * Change the after-sale status of the order
     *
     * @param orderSn
     * @param statusEnum
     */
    void updateOrderServiceStatus(String orderSn, String statusEnum);

    /**
     * Modify the after-sale status of the goods in the order
     *
     * @param sn
     * @param orderSkuDTOList
     */
    void updateOrderItemServiceStatus(String sn, List<OrderSkuDTO> orderSkuDTOList);

    /**
     * Modify the third-party payment serial number according to the order number
     * @param payOrderNo
     * @param sn
     */
    void updateOrderPayOrderNo(String payOrderNo, String sn);

}
