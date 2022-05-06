/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
