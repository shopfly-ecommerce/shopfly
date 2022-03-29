/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.core.receiver;

import com.enation.app.javashop.consumer.core.event.CategoryChangeEvent;
import com.enation.app.javashop.core.base.message.CategoryChangeMsg;
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
 * 分类 变更
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:29:42
 */
@Component
public class CategoryChangeReceiver {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required=false)
	private List<CategoryChangeEvent> events;



	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = AmqpExchange.GOODS_CATEGORY_CHANGE + "_QUEUE"),
			exchange = @Exchange(value = AmqpExchange.GOODS_CATEGORY_CHANGE, type = ExchangeTypes.FANOUT)
	))
	public void categoryChange(CategoryChangeMsg categoryChangeMsg){
		
		if(events!=null){
			for(CategoryChangeEvent event : events){
				try {
					event.categoryChange(categoryChangeMsg);
				} catch (Exception e) {
					logger.error("处理商品分类变化消息出错",e);
				}
			}
		}
		
	}
}
