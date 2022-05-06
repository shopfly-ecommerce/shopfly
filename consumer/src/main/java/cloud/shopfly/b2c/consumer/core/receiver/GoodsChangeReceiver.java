/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.core.receiver;

import cloud.shopfly.b2c.consumer.core.event.GoodsChangeEvent;
import cloud.shopfly.b2c.core.base.message.GoodsChangeMsg;
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
 * 商品变化消费者
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月23日 上午10:29:54
 */
@Component
public class GoodsChangeReceiver {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	private List<GoodsChangeEvent> events;

	/**
	 * 商品变化
	 * 
	 * @param goodsChangeMsg
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = AmqpExchange.GOODS_CHANGE + "_QUEUE"),
			exchange = @Exchange(value = AmqpExchange.GOODS_CHANGE, type = ExchangeTypes.FANOUT)
	))
	public void goodsChange(GoodsChangeMsg goodsChangeMsg) {

		if (events != null) {
			for (GoodsChangeEvent event : events) {
				try {
					event.goodsChange(goodsChangeMsg);
				} catch (Exception e) {
					logger.error("处理商品变化消息出错", e);
				}
			}
		}

	}
}
