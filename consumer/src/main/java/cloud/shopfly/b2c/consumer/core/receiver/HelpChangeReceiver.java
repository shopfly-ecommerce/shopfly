/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.core.receiver;

import cloud.shopfly.b2c.consumer.core.event.HelpChangeEvent;
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
 * 帮助中心页面生成
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月23日 上午10:30:38
 */
@Component
public class HelpChangeReceiver {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	private List<HelpChangeEvent> events;

	/**
	 * 消费执行者
	 * 
	 * @param articeids
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = AmqpExchange.HELP_CHANGE + "_QUEUE"),
			exchange = @Exchange(value = AmqpExchange.HELP_CHANGE, type = ExchangeTypes.FANOUT)
	))
	public void helpChange(List<Integer> articeids) {

		if (events != null) {
			for (HelpChangeEvent event : events) {
				try {
					event.helpChange(articeids);
				} catch (Exception e) {
					logger.error("生成帮助中心页面出错", e);
				}
			}
		}

	}
}
