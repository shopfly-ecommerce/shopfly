/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.support;

import dev.shopflix.core.trade.order.model.enums.OrderOperateEnum;
import dev.shopflix.core.trade.order.model.enums.OrderStatusEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单流程
 *
 * @author Snow create in 2018/5/16
 * @version v2.0
 * @since v7.0.0
 */
public class OrderStep implements Cloneable {


    /**
     * 订单状态
     */
    private OrderStatusEnum orderStatus;


    /**
     * 允许的操作
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
