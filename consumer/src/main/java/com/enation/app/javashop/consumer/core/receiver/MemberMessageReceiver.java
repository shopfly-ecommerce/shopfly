/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.core.receiver;

import com.enation.app.javashop.consumer.core.event.MemberMessageEvent;
import com.enation.app.javashop.core.base.rabbitmq.AmqpExchange;
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
 * 站内消息
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月23日 上午10:31:13
 */
@Component
public class MemberMessageReceiver {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	private List<MemberMessageEvent> events;

	/**
	 * 站内消息
	 * @param messageid
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = AmqpExchange.MEMBER_MESSAGE + "_QUEUE"),
			exchange = @Exchange(value = AmqpExchange.MEMBER_MESSAGE, type = ExchangeTypes.FANOUT)
	))
	public void memberMessage(int messageid) {

		if (events != null) {
			for (MemberMessageEvent event : events) {
				try {
					event.memberMessage(messageid);
				} catch (Exception e) {
					logger.error("站内消息发送出错", e);
				}
			}
		}
	}
}
