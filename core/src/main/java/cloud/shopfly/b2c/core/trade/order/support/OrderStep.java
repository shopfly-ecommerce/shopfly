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
package cloud.shopfly.b2c.core.trade.order.support;

import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOperateEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Order process
 *
 * @author Snow create in 2018/5/16
 * @version v2.0
 * @since v7.0.0
 */
public class OrderStep implements Cloneable {


    /**
     * Status
     */
    private OrderStatusEnum orderStatus;


    /**
     * Allowed operations
     */
    private List<OrderOperateEnum> allowableOperate;

    public OrderStep(OrderStatusEnum orderStatus, OrderOperateEnum... operates) {
        this.orderStatus = orderStatus;
        this.allowableOperate = new ArrayList<>();
        for (OrderOperateEnum orderOperate : operates) {
            allowableOperate.add(orderOperate);
        }
    }

    @Override
    public Object clone() {
        OrderStep orderStep = null;
        try {
            orderStep = (OrderStep) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        ArrayList list = (ArrayList) allowableOperate;
        orderStep.allowableOperate = (List) list.clone();
        return orderStep;
    }

    public List<OrderOperateEnum> getOperate() {
        return this.allowableOperate;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        OrderStep step1 = new OrderStep(OrderStatusEnum.NEW, OrderOperateEnum.CONFIRM, OrderOperateEnum.CANCEL);
        OrderStep step2 = (OrderStep) step1.clone();

        step2.getOperate().remove(OrderOperateEnum.CONFIRM);
        step2.getOperate().add(OrderOperateEnum.PAY);

        System.out.println(step1);
        System.out.println(step2);

        step1.getOperate().forEach(orderOperateEnum -> {
            System.out.println(orderOperateEnum);
        });

        System.out.println("--------");
        step2.getOperate().forEach(orderOperateEnum -> {
            System.out.println(orderOperateEnum);
        });

    }


    public boolean checkAllowable(OrderOperateEnum operate) {
        for (OrderOperateEnum orderOperate : allowableOperate) {
            if (operate.compareTo(orderOperate) == 0) {
                return true;
            }
        }
        return false;
    }

}
