/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.receiver;

import dev.shopflix.consumer.core.event.OrderStatusChangeEvent;
import dev.shopflix.core.base.message.OrderStatusChangeMsg;
import dev.shopflix.core.base.rabbitmq.AmqpExchange;
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
 * 订单状态改变消费者
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月23日 上午10:31:42
 */
@Component
public class OrderStatusChangeReceiver {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	private List<OrderStatusChangeEvent> events;

	/**
	 * 订单状态改变
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
					logger.error("订单状态改变消息出错", e);
				}
			}
		}

	}

}
