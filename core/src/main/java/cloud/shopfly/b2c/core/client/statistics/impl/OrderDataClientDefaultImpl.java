/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.statistics.impl;

import cloud.shopfly.b2c.core.client.statistics.OrderDataClient;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.statistics.service.OrderDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * OrderDataClientDefaultImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 下午2:41
 */

@Service
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
public class OrderDataClientDefaultImpl implements OrderDataClient {

    @Autowired
    private OrderDataManager orderDataManager;
    /**
     * 订单新增
     *
     * @param order
     */
    @Override
    public void put(OrderDO order) {
        orderDataManager.put(order);
    }

    /**
     * 订单修改
     *
     * @param order
     */
    @Override
    public void change(OrderDO order) {
        orderDataManager.change(order);
    }
}
