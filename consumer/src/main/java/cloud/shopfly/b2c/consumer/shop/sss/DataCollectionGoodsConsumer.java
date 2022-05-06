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
package cloud.shopfly.b2c.consumer.shop.sss;

import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.client.statistics.GoodsDataClient;
import cloud.shopfly.b2c.core.statistics.model.dto.GoodsData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品收藏更新
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-23 下午5:50
 */
@Component
public class DataCollectionGoodsConsumer {


    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private GoodsDataClient goodsDataClient;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AmqpExchange.GOODS_COLLECTION_CHANGE + "_QUEUE"),
            exchange = @Exchange(value = AmqpExchange.GOODS_COLLECTION_CHANGE, type = ExchangeTypes.FANOUT)
    ))
    public void goodsCollectionChange(GoodsData goodsData) {
        try {
            goodsDataClient.updateCollection(goodsData);
        } catch (Exception e) {
            logger.error("商品收藏数量更新失败：", e);
        }
    }

}
