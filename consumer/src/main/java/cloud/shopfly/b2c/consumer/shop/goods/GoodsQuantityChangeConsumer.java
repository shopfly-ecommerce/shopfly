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
package cloud.shopfly.b2c.consumer.shop.goods;

import cloud.shopfly.b2c.consumer.core.event.OrderStatusChangeEvent;
import cloud.shopfly.b2c.consumer.core.event.RefundStatusChangeEvent;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundGoodsDO;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefuseTypeEnum;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.base.message.RefundChangeMsg;
import cloud.shopfly.b2c.core.client.goods.GoodsQuantityClient;
import cloud.shopfly.b2c.core.client.trade.AfterSaleClient;
import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.goods.model.enums.QuantityType;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PayStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.ShipStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Commodity inventory increases/deductions
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018years6month22The morning of10:18:20
 */
@Service
public class GoodsQuantityChangeConsumer implements OrderStatusChangeEvent, RefundStatusChangeEvent {

    @Autowired
    private GoodsQuantityClient goodsQuantityClient;

    @Autowired
    private AfterSaleClient afterSaleClient;

    @Autowired
    private OrderClient orderClient;

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());


    /**
     * Order change processing
     *
     * @param orderMessage
     */
    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {
        // The delivery
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.SHIPPED.name())) {
            // Get order information
            OrderDO order = orderMessage.getOrderDO();
            String itemsJson = order.getItemsJson();
            // The collection of SKUs in the order
            List<OrderSkuVO> list = JsonUtil.jsonToList(itemsJson, OrderSkuVO.class);
            List<GoodsQuantityVO> quantityVOList = new ArrayList<>();
            for (OrderSkuVO sku : list) {
                GoodsQuantityVO goodsQuantity = new GoodsQuantityVO();

                goodsQuantity.setGoodsId(sku.getGoodsId());

                // Set to inventory to be cut
                goodsQuantity.setQuantity(0 - sku.getNum());
                // Shipping to reduce the actual inventory
                goodsQuantity.setQuantityType(QuantityType.actual);

                goodsQuantity.setSkuId(sku.getSkuId());

                quantityVOList.add(goodsQuantity);
            }
            // Deducting the inventory
            goodsQuantityClient.updateSkuQuantity(quantityVOList);

        }

        // Order cancelled before payment
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.CANCELLED.name()) && orderMessage.getOrderDO().getPayStatus().equals(PayStatusEnum.PAY_NO.name())) {

            List<GoodsQuantityVO> quantityVOList = new ArrayList<>();

            OrderDO order = orderMessage.getOrderDO();
            String itemsJson = order.getItemsJson();
            List<OrderSkuVO> list = JsonUtil.jsonToList(itemsJson, OrderSkuVO.class);

            for (OrderSkuVO sku : list) {

                GoodsQuantityVO goodsQuantity = new GoodsQuantityVO();
                goodsQuantity.setQuantity(sku.getNum());
                goodsQuantity.setGoodsId(sku.getGoodsId());

                // Cancellation Of an order The available stock to be occupied in order to resume an order
                goodsQuantity.setQuantity(sku.getNum());
                goodsQuantity.setQuantityType(QuantityType.enable);
                goodsQuantity.setSkuId(sku.getSkuId());
                quantityVOList.add(goodsQuantity);

            }

            goodsQuantityClient.updateSkuQuantity(quantityVOList);

        }

    }

    @Override
    public void refund(RefundChangeMsg refundChangeMsg) {
        RefundDO refund = refundChangeMsg.getRefund();
        // Get current order information
        OrderDetailDTO orderDetailDTO = orderClient.getModel(refundChangeMsg.getRefund().getOrderSn());
        // Refund increases available inventory when merchant review has been approved and not shipped
        boolean bool = refund.getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.name()) && orderDetailDTO.getShipStatus().equals(ShipStatusEnum.SHIP_NO.name()) && refundChangeMsg.getRefundStatusEnum().name().equals(RefundStatusEnum.PASS.name());
        List<RefundGoodsDO> goodsList = afterSaleClient.getRefundGoods(refund.getSn());
        if (bool) {

            List<GoodsQuantityVO> quantityVOList = new ArrayList<>();

            for (RefundGoodsDO goods : goodsList) {
                // Commodity warehousing
                GoodsQuantityVO goodsQuantity = new GoodsQuantityVO();
                goodsQuantity.setSkuId(goods.getSkuId());
                goodsQuantity.setGoodsId(goods.getGoodsId());
                goodsQuantity.setQuantity(goods.getReturnNum());
                goodsQuantity.setQuantityType(QuantityType.enable);
                quantityVOList.add(goodsQuantity);
            }

            goodsQuantityClient.updateSkuQuantity(quantityVOList);

        }

        // Return goods and order into storage, increase inventory
        bool = refund.getRefuseType().equals(RefuseTypeEnum.RETURN_GOODS.name()) && refundChangeMsg.getRefundStatusEnum().equals(RefundStatusEnum.STOCK_IN);
        if (bool) {

            List<GoodsQuantityVO> quantityVOList = new ArrayList<>();

            for (RefundGoodsDO goods : goodsList) {
                // Commodity warehousing
                GoodsQuantityVO goodsQuantity = new GoodsQuantityVO();
                goodsQuantity.setSkuId(goods.getSkuId());
                goodsQuantity.setGoodsId(goods.getGoodsId());
                goodsQuantity.setQuantity(goods.getReturnNum());

                // Increase the actual inventory first
                goodsQuantity.setQuantityType(QuantityType.actual);
                quantityVOList.add(goodsQuantity);
            }

            // Increase the actual inventory first
            goodsQuantityClient.updateSkuQuantity(quantityVOList);

            quantityVOList.forEach(goodsQuantityVO -> {
                goodsQuantityVO.setQuantityType(QuantityType.enable);
            });
            // Increase the available inventory
            goodsQuantityClient.updateSkuQuantity(quantityVOList);
        }


    }
}
