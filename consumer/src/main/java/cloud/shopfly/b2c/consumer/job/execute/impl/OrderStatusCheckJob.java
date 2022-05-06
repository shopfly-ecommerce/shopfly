/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.job.execute.impl;

import cloud.shopfly.b2c.consumer.job.execute.EveryDayExecute;
import cloud.shopfly.b2c.core.trade.order.service.OrderTaskManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单状态扫描
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-05 下午2:11
 */
@Component
public class OrderStatusCheckJob implements EveryDayExecute {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private OrderTaskManager orderTaskManager;

    /**
     * 每晚23:30执行
     */
    @Override
    public void everyDay() {
        /** 自动取消 */
        try {
            // 款到发货，新订单24小时未付款要自动取消
            this.orderTaskManager.cancelTask();
        } catch (Exception e) {
            logger.error("自动取消出错", e);
        }

        /** 自动确认收货 */
        try {
            // 发货之后10天要自动确认收货
            this.orderTaskManager.rogTask();
        } catch (Exception e) {
            logger.error("自动确认收货出错", e);
        }

        /** 自动完成天数 */
        try {
            // 确认收货7天后标记为完成
           this.orderTaskManager.completeTask();
        } catch (Exception e) {
            logger.error("订单7天后标记为完成出错", e);
        }

        /** 自动支付天数 */
        try {
            this.orderTaskManager.payTask();
        } catch (Exception e) {
            logger.error("订单自动支付完成出错", e);
        }

        /** 售后失效天数 */
        try {
            // 完成后一个月没有申请售后，标记为售后过期
            this.orderTaskManager.serviceTask();
        } catch (Exception e) {
            logger.error("订单标记为售后过期出错", e);
        }

        try {
            // 超过14天不能评价，并自动好评
            this.orderTaskManager.commentTask();
        } catch (Exception e) {
            logger.error("订单超过14天不能评价出错", e);
        }

    }

}
