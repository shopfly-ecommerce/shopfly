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
package cloud.shopfly.b2c.consumer.shop.trade.consumer;

import cloud.shopfly.b2c.consumer.core.event.OrderStatusChangeEvent;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.goods.model.enums.QuantityType;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.service.FullDiscountGiftManager;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderMetaKeyEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.service.OrderMetaManager;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单取消时增加订单赠品的可用库存
 *
 * @author Snow create in 2018/5/21
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class OrderGiftStoreConsumer implements OrderStatusChangeEvent {

    @Autowired
    private FullDiscountGiftManager fullDiscountGiftManager;

    @Autowired
    private OrderMetaManager orderMetaManager;

    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        //已确认状态减少赠品可用库存
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.CONFIRM.name())) {

            List<FullDiscountGiftDO> giftList = this.getList(orderMessage);
            this.fullDiscountGiftManager.reduceGiftQuantity(giftList, QuantityType.enable);
        }

        // 发货减少赠品真实库存
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.SHIPPED.name())) {

            List<FullDiscountGiftDO> giftList = this.getList(orderMessage);
            this.fullDiscountGiftManager.reduceGiftQuantity(giftList, QuantityType.actual);
        }

        //取消状态增加赠品可用库存
        if(orderMessage.getNewStatus().name().equals(OrderStatusEnum.CANCELLED.name())){

            List<FullDiscountGiftDO> giftList = this.getList(orderMessage);
            this.fullDiscountGiftManager.addGiftEnableQuantity(giftList);
        }

    }

    /**
     * 查询赠品信息
     *
     * @param orderMessage
     * @return
     */
    private List<FullDiscountGiftDO> getList(OrderStatusChangeMsg orderMessage) {

        String fullDiscountGiftJson = this.orderMetaManager.getMetaValue(orderMessage.getOrderDO().getSn(), OrderMetaKeyEnum.GIFT);
        List<FullDiscountGiftDO> giftList = JsonUtil.jsonToList(fullDiscountGiftJson, FullDiscountGiftDO.class);

        return giftList;
    }
}
