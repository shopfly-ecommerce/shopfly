/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.receiver;

import dev.shopflix.consumer.core.event.IndexChangeEvent;
import dev.shopflix.core.base.message.CmsManageMsg;
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
 * 首页生成
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月23日 上午10:30:51
 */
@Component
public class IndexChangeReceiver {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	private List<IndexChangeEvent> events;

	/**
	 * 生成首页
	 * @param operation
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = AmqpExchange.PC_INDEX_CHANGE + "_QUEUE"),
			exchange = @Exchange(value = AmqpExchange.PC_INDEX_CHANGE, type = ExchangeTypes.FANOUT)
	))
	public void createIndexPage(CmsManageMsg operation) {
		System.out.println("create index page");
		if (events != null) {
			for (IndexChangeEvent event : events) {
				try {
					event.createIndexPage(operation);
				} catch (Exception e) {
					logger.error("首页生成", e);
				}
			}
		}

	}

}
