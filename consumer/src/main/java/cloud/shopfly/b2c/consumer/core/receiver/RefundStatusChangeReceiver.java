/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.core.receiver;

import cloud.shopfly.b2c.consumer.core.event.RefundStatusChangeEvent;
import cloud.shopfly.b2c.core.base.message.RefundChangeMsg;
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
 * 退货退款
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月23日 上午10:23:22
 */
@Component
public class RefundStatusChangeReceiver {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	private List<RefundStatusChangeEvent> events;

	/**
	 * 退货退款
	 * @param refundPartVo
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE"),
			exchange = @Exchange(value = AmqpExchange.REFUND_STATUS_CHANGE, type = ExchangeTypes.FANOUT)
	))
	public void refund(RefundChangeMsg refundPartVo) {

		if (events != null) {
			for (RefundStatusChangeEvent event : events) {
				try {
					event.refund(refundPartVo);
				} catch (Exception e) {
					logger.error("处理退货退款消息出错", e);
				}
			}
		}
	}
}
