/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.core.receiver;

import cloud.shopfly.b2c.consumer.core.event.GoodsCommentEvent;
import cloud.shopfly.b2c.core.base.message.GoodsCommentMsg;
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
 * 商品评论
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月23日 上午10:30:02
 */
@Component
public class GoodsCommentReceiver {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	private List<GoodsCommentEvent> events;

	/**
	 * 商品评论
	 * 
	 * @param goodsCommentMsg
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = AmqpExchange.GOODS_COMMENT_COMPLETE + "_QUEUE"),
			exchange = @Exchange(value = AmqpExchange.GOODS_COMMENT_COMPLETE, type = ExchangeTypes.FANOUT)
	))
	public void commentComplete(GoodsCommentMsg goodsCommentMsg) {

		if (events != null) {
			for (GoodsCommentEvent event : events) {
				try {
					event.goodsComment(goodsCommentMsg);
				} catch (Exception e) {
					logger.error("处理商品评论出错",e);
				}
			}
		}

	}
}
