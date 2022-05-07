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
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fk
 * @version v1.0
 * @Description: Change in the quantity of goods purchased
 * @date 2018/6/2510:13
 * @since v7.0.0
 */
@Service
public class GoodsBuyCountConsumer implements OrderStatusChangeEvent {

    @Autowired
    private GoodsClient goodsClient;


    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        // Update the purchase quantity after receiving the goods
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.ROG.name())) {
            OrderDO order = orderMessage.getOrderDO();
            String itemsJson = order.getItemsJson();

            List<OrderSkuVO> list = JsonUtil.jsonToList(itemsJson, OrderSkuVO.class);
            this.goodsClient.updateBuyCount(list);
        }
    }
}
