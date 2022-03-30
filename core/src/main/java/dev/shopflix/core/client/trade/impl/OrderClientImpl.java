/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.trade.impl;

import dev.shopflix.core.client.trade.OrderClient;
import dev.shopflix.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import dev.shopflix.core.trade.cart.model.dos.OrderPermission;
import dev.shopflix.core.trade.order.model.dos.OrderDO;
import dev.shopflix.core.trade.order.model.enums.CommentStatusEnum;
import dev.shopflix.core.trade.order.model.enums.OrderMetaKeyEnum;
import dev.shopflix.core.trade.order.model.enums.OrderStatusEnum;
import dev.shopflix.core.trade.order.model.vo.OrderDetailVO;
import dev.shopflix.core.trade.order.model.vo.OrderSkuVO;
import dev.shopflix.core.trade.order.model.vo.OrderStatusNumVO;
import dev.shopflix.core.trade.order.service.OrderMetaManager;
import dev.shopflix.core.trade.order.service.OrderOperateManager;
import dev.shopflix.core.trade.order.service.OrderQueryManager;
import dev.shopflix.core.trade.sdk.model.OrderDetailDTO;
import dev.shopflix.core.trade.sdk.model.OrderSkuDTO;
import dev.shopflix.framework.util.BeanUtil;
import dev.shopflix.framework.util.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单相关SDK
 *
 * @author Snow create in 2018/5/28
 * @version v2.0
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value = "shopflix.product", havingValue = "stand")
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
     * 读取一个订单详细<br/>
     *
     * @param orderSn 订单编号 必传
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

}
