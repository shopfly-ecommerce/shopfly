/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.message;

import com.enation.app.javashop.core.trade.order.model.dos.OrderDO;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;

import java.io.Serializable;


/**
 * 订单变化消息
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午9:52:13
 */
public class OrderStatusChangeMsg implements Serializable {

    private static final long serialVersionUID = 8915428082431868648L;

    /**
     * 变化的订单
     */
    private OrderDO orderDO;

    /**
     * 原状态
     */
    private OrderStatusEnum oldStatus;

    /**
     * 新状态
     */
    private OrderStatusEnum newStatus;

    public OrderDO getOrderDO() {
        return orderDO;
    }

    public void setOrderDO(OrderDO orderDO) {
        this.orderDO = orderDO;
    }

    public OrderStatusEnum getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(OrderStatusEnum oldStatus) {
        this.oldStatus = oldStatus;
    }

    public OrderStatusEnum getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(OrderStatusEnum newStatus) {
        this.newStatus = newStatus;
    }

    public OrderStatusChangeMsg() {
    }

    @Override
    public String toString() {
        return "OrderStatusChangeMsg{" +
                "orderDO=" + orderDO +
                ", oldStatus=" + oldStatus +
                ", newStatus=" + newStatus +
                '}';
    }
}
