/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.support;

import dev.shopflix.core.trade.order.model.enums.OrderOperateEnum;
import dev.shopflix.core.trade.order.model.enums.OrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * 订单操作检验
 *
 * @author Snow create in 2018/5/16
 * @version 3.0
 * 流程外部化， written by kingapex  in  2019/1/28
 * @since v7.0.0
 */
public class OrderOperateChecker {

    private Map<OrderStatusEnum, OrderStep> flow;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 检测程序必须初始化流程
     *
     * @param flow
     */
    public OrderOperateChecker(Map<OrderStatusEnum, OrderStep> flow) {
        this.flow = flow;
    }

    /**
     * 校验操作是否被允许
     *
     * @param status
     * @param operate
     * @return
     */
    public boolean checkAllowable(OrderStatusEnum status, OrderOperateEnum operate) {

        if (flow == null) {
            if (logger.isErrorEnabled()) {
                logger.error("status[" + status + "] and operate[" + operate + "] 没找到flow,flow发生为空，强制返回false");
            }

            return false;
        }

        OrderStep step = flow.get(status);

        if (step == null) {
            if (logger.isErrorEnabled()) {
                logger.error("status[" + status + "] and operate[" + operate + "] 没找到step,step发生为空，强制返回false");
            }

            return false;
        }

        return step.checkAllowable(operate);

    }


}
