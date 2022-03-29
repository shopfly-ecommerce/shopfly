/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.core.receiver;

import com.enation.app.javashop.consumer.core.event.TradeIntoDbEvent;
import com.enation.app.javashop.core.base.rabbitmq.AmqpExchange;
import com.enation.app.javashop.core.trade.order.model.vo.TradeVO;
import com.enation.app.javashop.framework.cache.Cache;
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
 * 订单入库消息处理
 *
 * @author Snow create in 2018/5/10
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class OrderIntoDbReceiver {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired(required = false)
    private List<TradeIntoDbEvent> events;

    @Autowired
    private Cache cache;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AmqpExchange.ORDER_CREATE + "_QUEUE"),
            exchange = @Exchange(value = AmqpExchange.ORDER_CREATE, type = ExchangeTypes.FANOUT)
    ))
    public void tradeIntoDb(String tradeVOKey) {

        TradeVO tradeVO = (TradeVO) this.cache.get(tradeVOKey);

        if (events != null) {
            for (TradeIntoDbEvent event : events) {
                try {
                    event.onTradeIntoDb(tradeVO);
                } catch (Exception e) {
                    logger.error("交易入库消息出错", e);
                } finally {
                    cache.remove(tradeVOKey);
                }
            }
        }

    }

}
