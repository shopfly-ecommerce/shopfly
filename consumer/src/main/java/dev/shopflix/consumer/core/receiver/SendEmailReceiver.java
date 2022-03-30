/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.receiver;

import dev.shopflix.consumer.core.event.SendEmailEvent;
import dev.shopflix.core.base.model.vo.EmailVO;
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
 * 发送邮件
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月23日 上午10:31:58
 */
@Component
public class SendEmailReceiver {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	private List<SendEmailEvent> events;

	/**
	 * 发送邮件
	 * 
	 * @param emailVO
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = AmqpExchange.EMAIL_SEND_MESSAGE + "_QUEUE"),
			exchange = @Exchange(value = AmqpExchange.EMAIL_SEND_MESSAGE, type = ExchangeTypes.FANOUT)
	))
	public void sendEmail(EmailVO emailVO) {
		if (events != null) {
			for (SendEmailEvent event : events) {
				try {
					event.sendEmail(emailVO);
				} catch (Exception e) {
					logger.error("发送邮件出错", e);
				}
			}
		}

	}
}
