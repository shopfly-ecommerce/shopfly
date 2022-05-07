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
package cloud.shopfly.b2c.consumer.core.receiver;

import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.statistics.model.dos.GoodsPageView;
import cloud.shopfly.b2c.core.statistics.service.DisplayTimesManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Traffic statisticsamqp consumption
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-07 In the afternoon4:34
 */
@Component
public class DisplayTimesReceiver {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private DisplayTimesManager displayTimesManager;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AmqpExchange.GOODS_VIEW_COUNT + "_QUEUE"),
            exchange = @Exchange(value = AmqpExchange.GOODS_VIEW_COUNT, type = ExchangeTypes.FANOUT)
    ))
    public void viewGoods(List<GoodsPageView> goodsPageViews) {
        try {
            displayTimesManager.countGoods(goodsPageViews);
        } catch (Exception e) {
            logger.error("Traffic arrangement：Abnormal goods", e);
        }
    }


}
