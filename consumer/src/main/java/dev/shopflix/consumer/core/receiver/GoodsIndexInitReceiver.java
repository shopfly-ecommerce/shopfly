/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.receiver;

import dev.shopflix.consumer.core.event.GoodsIndexInitEvent;
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
 * 商品索引
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月23日 上午10:30:12
 */
@Component
public class GoodsIndexInitReceiver {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	private List<GoodsIndexInitEvent> events;

	/**
	 * 初始化商品索引
	 *
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = AmqpExchange.INDEX_CREATE + "_QUEUE"),
			exchange = @Exchange(value = AmqpExchange.INDEX_CREATE, type = ExchangeTypes.FANOUT)
	))
	public void initGoodsIndex() {

		System.out.println(" rceive index msg");
		if (events != null) {
			for (GoodsIndexInitEvent event : events) {
				try {
					event.createGoodsIndex();
				} catch (Exception e) {
					logger.error("初始化商品索引出错", e);
				}
			}
		}

	}

}
