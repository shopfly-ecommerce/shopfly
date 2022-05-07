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

import cloud.shopfly.b2c.consumer.core.event.OrderStatusChangeEvent;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
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
 * Order status changes to the consumer
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018years3month23The morning of10:31:42
 */
@Component
public class OrderStatusChangeReceiver {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	private List<OrderStatusChangeEvent> events;

	/**
	 * Order status change
	 * @param orderMessage
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = AmqpExchange.ORDER_STATUS_CHANGE + "_QUEUE"),
			exchange = @Exchange(value = AmqpExchange.ORDER_STATUS_CHANGE, type = ExchangeTypes.FANOUT)
	))
	public void orderChange(OrderStatusChangeMsg orderMessage) {
		if (events != null) {
			for (OrderStatusChangeEvent event : events) {
				try {
					event.orderChange(orderMessage);
				} catch (Exception e) {
					logger.error("The order status change message failed", e);
				}
			}
		}

	}

}
