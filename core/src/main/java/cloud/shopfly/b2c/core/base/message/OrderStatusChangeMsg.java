/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.base.message;

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;

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
