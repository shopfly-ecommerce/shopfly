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

import cloud.shopfly.b2c.consumer.core.event.TradeIntoDbEvent;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.framework.cache.Cache;
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
 * 订单入库消息处理
 *
 * @author Snow create in 2018/5/10
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class OrderIntoDbReceiver {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired(required = false)
    private List<TradeIntoDbEvent> events;

    @Autowired
    private Cache cache;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AmqpExchange.ORDER_CREATE + "_QUEUE"),
            exchange = @Exchange(value = AmqpExchange.ORDER_CREATE, type = ExchangeTypes.FANOUT)
    ))
    public void tradeIntoDb(String tradeVOKey) {

        TradeVO tradeVO = (TradeVO) this.cache.get(tradeVOKey);

        if (events != null) {
            for (TradeIntoDbEvent event : events) {
                try {
                    event.onTradeIntoDb(tradeVO);
                } catch (Exception e) {
                    logger.error("交易入库消息出错", e);
                } finally {
                    cache.remove(tradeVOKey);
                }
            }
        }

    }

}
