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
