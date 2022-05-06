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
 * 订单相关
 *
 * @author Snow create in 2018/5/14
 * @version v2.0
 * @since v7.0.0
 */
public interface OrderQueryManager {
    /**
     * 读取订单状态的订单数
     *
     * @param memberId 会员id
     * @return
     */
    OrderStatusNumVO getOrderStatusNum(Integer memberId);

    /**
     * 读取会员所有的订单数量
     *
     * @param memberId 会员id
     * @return
     */
    Integer getOrderNumByMemberId(Integer memberId);

    /**
     * 读取会员(评论状态)订单数量
     *
     * @param memberId
     * @param commentStatus 评论状态
     * @return
     */
    Integer getOrderCommentNumByMemberId(Integer memberId, String commentStatus);

    /**
     * 读取一个订单详细<br/>
     *
     * @param orderSn 订单编号 必传
     * @param buyerId 用户编号
     * @return
     */
    OrderDetailVO getModel(String orderSn, Integer buyerId);

    /**
     * 查询一个订单的详细
     *
     * @param orderSn
     * @return
     */
    OrderDetailDTO getModel(String orderSn);

    /**
     * 获取某订单的订单项
     *
     * @param orderSn 订单编号
     * @return
     */
    List<OrderItemsDO> orderItems(String orderSn);

    /**
     * 获取订单可退款总额
     * @param orderSn 订单编号
     * @return
     */
    double getOrderRefundPrice(String orderSn);


    /**
     * 查询订单表列表
     *
     * @param paramDTO 参数对象
     * @return Page
     */
    Page list(OrderQueryParam paramDTO);

    /**
     * 读取订单列表根据交易编号
     *
     * @param tradeSn
     * @param memberId
     * @return
     */
    List<OrderDetailVO> getOrderByTradeSn(String tradeSn, Integer memberId);

    /**
     * 读取订单列表根据交易编号——系统内部使用 OrderClient
     * @param tradeSn
     * @return
     */
    List<OrderDetailDTO> getOrderByTradeSn(String tradeSn);

    /**
     * 根据订单sn读取，订单的流程
     *
     * @param orderSn 订单编号
     * @return
     */
    List<OrderFlowNode> getOrderFlow(String orderSn);

    /**
     * 查询一个订单DO
     * @param orderSn
     * @return
     */
    OrderDO getDoByOrderSn(String orderSn);


}