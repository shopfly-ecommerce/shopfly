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

import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.CommentStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderMetaKeyEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import cloud.shopfly.b2c.core.trade.order.service.OrderMetaManager;
import cloud.shopfly.b2c.core.trade.order.service.OrderOperateManager;
import cloud.shopfly.b2c.core.trade.order.service.OrderQueryManager;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderDetailVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderStatusNumVO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderSkuDTO;
import cloud.shopfly.b2c.framework.util.BeanUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Order relatedSDK
 *
 * @author Snow create in 2018/5/28
 * @version v2.0
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class OrderClientImpl implements OrderClient {

    @Autowired
    private OrderQueryManager orderQueryManager;

    @Autowired
    private OrderOperateManager orderOperateManager;

    @Autowired
    private OrderMetaManager orderMetaManager;

    @Override
    public OrderStatusNumVO getOrderStatusNum(Integer memberId) {

        return this.orderQueryManager.getOrderStatusNum(memberId);
    }

    @Override
    public Integer getOrderNumByMemberId(Integer memberId) {
        Integer num = this.orderQueryManager.getOrderNumByMemberId(memberId);
        return num;
    }

    @Override
    public List<OrderDetailDTO> getOrderByTradeSn(String tradeSn) {
        List<OrderDetailDTO> orderDetailDTOList = this.orderQueryManager.getOrderByTradeSn(tradeSn);
        return orderDetailDTOList;
    }


    @Override
    public Integer getOrderCommentNumByMemberId(Integer memberId, String commentStatus) {
        return this.orderQueryManager.getOrderCommentNumByMemberId(memberId, commentStatus);
    }

    @Override
    public OrderDetailDTO getModel(String orderSn) {
        OrderDetailVO orderDetailVO = this.orderQueryManager.getModel(orderSn, null);
        OrderDetailDTO detailDTO = new OrderDetailDTO();
        BeanUtils.copyProperties(orderDetailVO, detailDTO);
        detailDTO.setOrderSkuList(new ArrayList<>());

        for (OrderSkuVO skuVO : orderDetailVO.getOrderSkuList()) {
            OrderSkuDTO skuDTO = new OrderSkuDTO();
            BeanUtil.copyProperties(skuVO, skuDTO);
            detailDTO.getOrderSkuList().add(skuDTO);
        }

        String json = this.orderMetaManager.getMetaValue(detailDTO.getSn(), OrderMetaKeyEnum.GIFT);
        List<FullDiscountGiftDO> giftList = JsonUtil.jsonToList(json, FullDiscountGiftDO.class);
        detailDTO.setGiftList(giftList);

        return detailDTO;
    }

    @Override
    public boolean updateOrderStatus(String sn, OrderStatusEnum orderStatus) {
        orderOperateManager.updateOrderStatus(sn, orderStatus);
        return true;
    }

    @Override
    public boolean updateTradeStatus(String sn, OrderStatusEnum orderStatus) {
        orderOperateManager.updateTradeStatus(sn, orderStatus);
        return true;
    }

    @Override
    public void addOrderItemRefundPrice(OrderDO orderDO) {
        orderOperateManager.updateItemRefundPrice(this.getOrderVO(orderDO.getSn()));
    }

    /**
     * Read an order detail<br/>
     *
     * @param orderSn Order number must be uploaded
     * @return
     */
    @Override
    public OrderDetailVO getOrderVO(String orderSn) {
        return this.orderQueryManager.getModel(orderSn, null);
    }

    @Override
    public void updateOrderCommentStatus(String orderSn, String statusEnum) {
        CommentStatusEnum commentStatusEnum = CommentStatusEnum.valueOf(statusEnum);
        this.orderOperateManager.updateCommentStatus(orderSn, commentStatusEnum);
    }

    @Override
    public void payOrder(String sn, Double price, String returnTradeNo, String permission) {
        OrderPermission orderPermission = OrderPermission.valueOf(permission);
        this.orderOperateManager.payOrder(sn, price, returnTradeNo, orderPermission);
    }


    @Override
    public void updateOrderPayOrderNo(String payOrderNo, String sn) {
        this.orderOperateManager.updateOrderPayOrderNo(payOrderNo, sn);
    }

}
