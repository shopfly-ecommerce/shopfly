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

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderDetailVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderStatusNumVO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;

import java.util.List;

/**
 * Order querySDK
 *
 * @author Snow create in 2018/5/28
 * @version v2.0
 * @since v7.0.0
 */
public interface OrderClient {
    /**
     * Read the order number of the order status
     *
     * @param memberId membersid
     * @return
     */
    OrderStatusNumVO getOrderStatusNum(Integer memberId);

    /**
     * Query order items by transaction number
     *
     * @param tradeSn
     * @return
     */
    List<OrderDetailDTO> getOrderByTradeSn(String tradeSn);

    /**
     * According to the membershipidRead all my order quantities
     *
     * @param memberId membersid
     * @return
     */
    Integer getOrderNumByMemberId(Integer memberId);

    /**
     * According to the membershipidRead my(Review status)The order number
     *
     * @param memberId
     * @param commentStatus Review status
     * @return
     */
    Integer getOrderCommentNumByMemberId(Integer memberId, String commentStatus);

    /**
     * Read an order detail<br/>
     *
     * @param orderSn Order number must be uploaded
     * @return
     */
    OrderDetailDTO getModel(String orderSn);

    /**
     * Update order Status
     *
     * @param sn          The order number
     * @param orderStatus Status
     * @return Check whether the update succeeds.
     */
    boolean updateOrderStatus(String sn, OrderStatusEnum orderStatus);

    /**
     * Update transaction Status
     *
     * @param sn          tradingsn
     * @param orderStatus Transaction status
     * @return Check whether the update succeeds.
     */
    boolean updateTradeStatus(String sn, OrderStatusEnum orderStatus);

    /**
     * Update order item refundable amount
     *
     * @param orderDO
     */
    void addOrderItemRefundPrice(OrderDO orderDO);

    /**
     * Read an order detail<br/>
     *
     * @param orderSn Order number must be uploaded
     * @return
     */
    OrderDetailVO getOrderVO(String orderSn);

    /**
     * Change order comment status
     *
     * @param orderSn
     * @param statusEnum
     */
    void updateOrderCommentStatus(String orderSn, String statusEnum);


    /**
     * Payment for an order
     *
     * @param orderSn       Order no.
     * @param price         Pay the price
     * @param returnTradeNo The payment order number returned by the third-party platform
     * @param permission    permissions{@link OrderPermission}
     */
    void payOrder(String orderSn, Double price, String returnTradeNo, String permission);


    /**
     * Modify the third-party payment serial number according to the order number
     * @param payOrderNo
     * @param sn
     */
    void updateOrderPayOrderNo(String payOrderNo, String sn);


}
