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
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderItemsDO;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderQueryParam;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderDetailVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderFlowNode;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderStatusNumVO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Order related
 *
 * @author Snow create in 2018/5/14
 * @version v2.0
 * @since v7.0.0
 */
public interface OrderQueryManager {
    /**
     * Read the order number of the order status
     *
     * @param memberId membersid
     * @return
     */
    OrderStatusNumVO getOrderStatusNum(Integer memberId);

    /**
     * Read all order numbers of members
     *
     * @param memberId membersid
     * @return
     */
    Integer getOrderNumByMemberId(Integer memberId);

    /**
     * Read the member(Review status)The order number
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
     * @param buyerId The user id
     * @return
     */
    OrderDetailVO getModel(String orderSn, Integer buyerId);

    /**
     * Query the details of an order
     *
     * @param orderSn
     * @return
     */
    OrderDetailDTO getModel(String orderSn);

    /**
     * Gets the order item for an order
     *
     * @param orderSn Order no.
     * @return
     */
    List<OrderItemsDO> orderItems(String orderSn);

    /**
     * Get the total refundable amount of the order
     * @param orderSn Order no.
     * @return
     */
    double getOrderRefundPrice(String orderSn);


    /**
     * Query the order table list
     *
     * @param paramDTO Parameter object
     * @return Page
     */
    Page list(OrderQueryParam paramDTO);

    /**
     * Read the order list according to the transaction number
     *
     * @param tradeSn
     * @param memberId
     * @return
     */
    List<OrderDetailVO> getOrderByTradeSn(String tradeSn, Integer memberId);

    /**
     * Read the order list according to the transaction number——System internal useOrderClient
     * @param tradeSn
     * @return
     */
    List<OrderDetailDTO> getOrderByTradeSn(String tradeSn);

    /**
     * According to the ordersnThe process of reading the order
     *
     * @param orderSn Order no.
     * @return
     */
    List<OrderFlowNode> getOrderFlow(String orderSn);

    /**
     * Querying an orderDO
     * @param orderSn
     * @return
     */
    OrderDO getDoByOrderSn(String orderSn);


}
