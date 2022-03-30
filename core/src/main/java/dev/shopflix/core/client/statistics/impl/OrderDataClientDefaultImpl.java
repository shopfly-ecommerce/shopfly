/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.statistics.impl;

import dev.shopflix.core.client.statistics.OrderDataClient;
import dev.shopflix.core.statistics.service.OrderDataManager;
import dev.shopflix.core.trade.order.model.dos.OrderDO;
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
@ConditionalOnProperty(value="shopflix.product", havingValue="stand")
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
