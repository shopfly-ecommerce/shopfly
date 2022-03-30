/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.receiver;

import dev.shopflix.core.base.rabbitmq.AmqpExchange;
import dev.shopflix.core.client.goods.GoodsClient;
import dev.shopflix.core.statistics.model.dos.GoodsPageView;
import dev.shopflix.core.statistics.service.DisplayTimesManager;
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
 * 流量统计amqp 消费
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-07 下午4:34
 */
@Component
public class DisplayTimesReceiver {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private DisplayTimesManager displayTimesManager;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AmqpExchange.GOODS_VIEW_COUNT + "_QUEUE"),
            exchange = @Exchange(value = AmqpExchange.GOODS_VIEW_COUNT, type = ExchangeTypes.FANOUT)
    ))
    public void viewGoods(List<GoodsPageView> goodsPageViews) {
        try {
            displayTimesManager.countGoods(goodsPageViews);
        } catch (Exception e) {
            logger.error("流量整理：商品 异常", e);
        }
    }


}
