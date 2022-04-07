/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.event;


import dev.shopflix.core.base.message.OrderStatusChangeMsg;

/**
 * 订单状态变化事件
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午9:52:32
 */
public interface OrderStatusChangeEvent {


    /**
     * 订单状态改变
     * @param orderMessage
     */
    void orderChange(OrderStatusChangeMsg orderMessage);

}