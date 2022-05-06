/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.shop.sss;

import cloud.shopfly.b2c.consumer.core.event.OrderStatusChangeEvent;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.client.statistics.OrderDataClient;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单状态改变消费
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/8 下午6:44
 */
@Component
public class DataOrderConsumer implements OrderStatusChangeEvent {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());


    @Autowired
    private OrderDataClient orderDataClient;

    @Override
    public void orderChange(OrderStatusChangeMsg orderStatusChangeMsg) {
        try {
            if (orderStatusChangeMsg.getNewStatus().equals(OrderStatusEnum.PAID_OFF)) {
                this.orderDataClient.put(orderStatusChangeMsg.getOrderDO());
            } else if (!orderStatusChangeMsg.getNewStatus().equals(OrderStatusEnum.PAID_OFF)) {
                this.orderDataClient.change(orderStatusChangeMsg.getOrderDO());
            }
        } catch (Exception e) {
            logger.error("订单变更消息异常:",e);
            e.printStackTrace();
        }
    }

}
