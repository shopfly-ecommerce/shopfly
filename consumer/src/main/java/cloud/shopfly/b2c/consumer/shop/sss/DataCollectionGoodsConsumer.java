/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.shop.sss;

import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.client.statistics.GoodsDataClient;
import cloud.shopfly.b2c.core.statistics.model.dto.GoodsData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品收藏更新
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-23 下午5:50
 */
@Component
public class DataCollectionGoodsConsumer {


    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private GoodsDataClient goodsDataClient;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AmqpExchange.GOODS_COLLECTION_CHANGE + "_QUEUE"),
            exchange = @Exchange(value = AmqpExchange.GOODS_COLLECTION_CHANGE, type = ExchangeTypes.FANOUT)
    ))
    public void goodsCollectionChange(GoodsData goodsData) {
        try {
            goodsDataClient.updateCollection(goodsData);
        } catch (Exception e) {
            logger.error("商品收藏数量更新失败：", e);
        }
    }

}
