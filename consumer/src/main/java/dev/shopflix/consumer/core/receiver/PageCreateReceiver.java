/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.receiver;

import dev.shopflix.consumer.core.event.PageCreateEvent;
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
 * 页面生成
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月23日 上午10:31:49
 */
@Component
public class PageCreateReceiver {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	private List<PageCreateEvent> events;

	/**
	 * 页面生成
	 * 
	 * @param choosePages
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = AmqpExchange.PAGE_CREATE + "_QUEUE"),
			exchange = @Exchange(value = AmqpExchange.PAGE_CREATE, type = ExchangeTypes.FANOUT)
	))
	public void createPage(String[] choosePages) {

		System.out.println("收到静态页生成消息："+ choosePages);

		if (events != null) {
			for (PageCreateEvent event : events) {
				try {
					event.createPage(choosePages);
				} catch (Exception e) {
					logger.error("页面生成出错", e);
				}
			}
		}
	}
}
