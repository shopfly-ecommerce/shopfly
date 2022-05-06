/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.support;

import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOperateEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingapex on 2019-01-28.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-28
 */
public class OrderOperateFlow {

    /**
     * 货到付款流程
     */
    public static final Map<OrderStatusEnum, OrderStep> COD_FLOW = new HashMap<OrderStatusEnum, OrderStep>();

    /**
     * 款到发化流程
     */
    public static final Map<OrderStatusEnum, OrderStep> PAY_FIRST_FLOW = new HashMap<OrderStatusEnum, OrderStep>();

    /**
     * 拼团订单流程
     */
    public static final Map<OrderStatusEnum, OrderStep> PINTUAN_FLOW = new HashMap<OrderStatusEnum, OrderStep>();


    //定义流程
    static {

        //定义货到付款流程
        initCodFlow();

        //定义款到发货流程
        initPayFirstFlow();

        //定义拼团流程
        initPinTuanFlow();
    }


    public static Map<OrderStatusEnum, OrderStep> getFlow(PaymentTypeEnum paymentType, OrderTypeEnum orderType) {

        //拼团类型的订单直接返回拼团流程
        if (orderType.equals(OrderTypeEnum.pintuan)) {
            return PINTUAN_FLOW;
        }


        //在线支付的是 款到发货流程
        if (PaymentTypeEnum.ONLINE.equals(paymentType)) {
            return PAY_FIRST_FLOW;
        }

        //货到付款流程
        if (PaymentTypeEnum.COD.equals(paymentType)) {
            return COD_FLOW;
        }

        return null;
    }


    private static void initPinTuanFlow() {

        //将款到发货的流程clone一份给拼团的流程
        PAY_FIRST_FLOW.forEach((k, v) -> PINTUAN_FLOW.put(k, (OrderStep) v.clone()));

        //拼团的已经支付状态是不能发货的，要加入一个“已经成团”
        OrderStep payStep = PINTUAN_FLOW.get(OrderStatusEnum.PAID_OFF);
        List<OrderOperateEnum> operate = payStep.getOperate();
        operate.remove(OrderOperateEnum.SHIP);

        //已成团才能发货
        OrderStep formedStep = new OrderStep(OrderStatusEnum.FORMED, OrderOperateEnum.SHIP);
        PINTUAN_FLOW.put(OrderStatusEnum.FORMED, formedStep);
    }


    /**
     * 定义款到发货流程
     */
    private static void initPayFirstFlow() {

        //新订单，可以确认，可以取消
        OrderStep newStep = new OrderStep(OrderStatusEnum.NEW, OrderOperateEnum.CONFIRM, OrderOperateEnum.CANCEL);
        PAY_FIRST_FLOW.put(OrderStatusEnum.NEW, newStep);

        //确认的订单，可以支付，可以取消
        OrderStep confirmStep = new OrderStep(OrderStatusEnum.CONFIRM, OrderOperateEnum.PAY, OrderOperateEnum.CANCEL);
        PAY_FIRST_FLOW.put(OrderStatusEnum.CONFIRM, confirmStep);

        //已经支付，可以发货
        OrderStep payStep = new OrderStep(OrderStatusEnum.PAID_OFF, OrderOperateEnum.SHIP);
        PAY_FIRST_FLOW.put(OrderStatusEnum.PAID_OFF, payStep);

        //发货的订单，可以确认收货
        OrderStep shipStep = new OrderStep(OrderStatusEnum.SHIPPED, OrderOperateEnum.ROG);
        PAY_FIRST_FLOW.put(OrderStatusEnum.SHIPPED, shipStep);

        //收货的订单，可以完成
        OrderStep rogStep = new OrderStep(OrderStatusEnum.ROG, OrderOperateEnum.COMPLETE);
        PAY_FIRST_FLOW.put(OrderStatusEnum.ROG, rogStep);

        //售后的订单可以完成
        OrderStep refundStep = new OrderStep(OrderStatusEnum.AFTER_SERVICE, OrderOperateEnum.COMPLETE);
        PAY_FIRST_FLOW.put(OrderStatusEnum.AFTER_SERVICE, refundStep);


        //取消的的订单不能有任何操作
        OrderStep cancelStep = new OrderStep(OrderStatusEnum.CANCELLED);
        PAY_FIRST_FLOW.put(OrderStatusEnum.CANCELLED, cancelStep);

        //异常的订单不能有任何操作
        OrderStep errorStep = new OrderStep(OrderStatusEnum.INTODB_ERROR);
        PAY_FIRST_FLOW.put(OrderStatusEnum.INTODB_ERROR, errorStep);

        //完成的订单不能有任何操作
        OrderStep completeStep = new OrderStep(OrderStatusEnum.COMPLETE);
        PAY_FIRST_FLOW.put(OrderStatusEnum.COMPLETE, completeStep);
    }

    /**
     * 定义货到付款流程
     */
    private static void initCodFlow() {
        //新订单，可以确认，可以取消
        OrderStep newStep = new OrderStep(OrderStatusEnum.NEW, OrderOperateEnum.CONFIRM, OrderOperateEnum.CANCEL);
        COD_FLOW.put(OrderStatusEnum.NEW, newStep);

        //确认的订单，可以发货,可取消
        OrderStep confirmStep = new OrderStep(OrderStatusEnum.CONFIRM, OrderOperateEnum.SHIP, OrderOperateEnum.CANCEL);
        COD_FLOW.put(OrderStatusEnum.CONFIRM, confirmStep);

        //发货的订单，可以确认收货
        OrderStep shipStep = new OrderStep(OrderStatusEnum.SHIPPED, OrderOperateEnum.ROG);
        COD_FLOW.put(OrderStatusEnum.SHIPPED, shipStep);

        //收货的订单
        OrderStep rogStep = new OrderStep(OrderStatusEnum.ROG, OrderOperateEnum.PAY);
        COD_FLOW.put(OrderStatusEnum.ROG, rogStep);

        //已经支付，可以完成
        OrderStep payStep = new OrderStep(OrderStatusEnum.PAID_OFF, OrderOperateEnum.COMPLETE);
        COD_FLOW.put(OrderStatusEnum.PAID_OFF, payStep);

        //售后的订单可以完成
        OrderStep refundStep = new OrderStep(OrderStatusEnum.AFTER_SERVICE, OrderOperateEnum.COMPLETE);
        COD_FLOW.put(OrderStatusEnum.AFTER_SERVICE, refundStep);

        //取消的的订单不能有任何操作
        OrderStep cancelStep = new OrderStep(OrderStatusEnum.CANCELLED);
        COD_FLOW.put(OrderStatusEnum.CANCELLED, cancelStep);

        //完成的订单不能有任何操作
        OrderStep completeStep = new OrderStep(OrderStatusEnum.COMPLETE);
        COD_FLOW.put(OrderStatusEnum.COMPLETE, completeStep);

        //异常的订单不能有任何操作
        OrderStep errorStep = new OrderStep(OrderStatusEnum.INTODB_ERROR);
        COD_FLOW.put(OrderStatusEnum.INTODB_ERROR, errorStep);
    }

}
