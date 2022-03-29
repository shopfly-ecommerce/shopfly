/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.service;

import dev.shopflix.core.trade.order.model.dos.OrderDO;

/**
 * 订单信息收集manager
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/8 上午8:22
 */

public interface OrderDataManager {


    /**
     * 订单新增
     *
     * @param order
     */
    void put(OrderDO order);

    /**
     * 订单修改
     *
     * @param order
     */
    void change(OrderDO order);

}
