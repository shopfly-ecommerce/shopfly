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
 * Order status scan
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-05 In the afternoon2:11
 */
@Component
public class OrderStatusCheckJob implements EveryDayExecute {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private OrderTaskManager orderTaskManager;

    /**
     * Every night,23:30perform
     */
    @Override
    public void everyDay() {
        /** Automatically cancelled*/
        try {
            // The new order will be cancelled automatically if it is not paid within 24 hours
            this.orderTaskManager.cancelTask();
        } catch (Exception e) {
            logger.error("Autocancel error", e);
        }

        /** Automatic confirmation of receipt*/
        try {
            // Automatically confirm receipt of goods 10 days after shipment
            this.orderTaskManager.rogTask();
        } catch (Exception e) {
            logger.error("Automatic confirmation of receipt error", e);
        }

        /** Automatic completion days*/
        try {
            // Mark completed 7 days after confirmation of receipt
           this.orderTaskManager.completeTask();
        } catch (Exception e) {
            logger.error("The order7The days after are marked as completion errors", e);
        }

        /** Automatic payment days*/
        try {
            this.orderTaskManager.payTask();
        } catch (Exception e) {
            logger.error("Order automatic payment completion error", e);
        }

        /** Number of days after sale*/
        try {
            // If there is no after-sale application within one month after completion, it will be marked as after-sale expired
            this.orderTaskManager.serviceTask();
        } catch (Exception e) {
            logger.error("The order was marked as after sale expired error", e);
        }

        try {
            // Can not be evaluated for more than 14 days, and automatically praise
            this.orderTaskManager.commentTask();
        } catch (Exception e) {
            logger.error("Order more than14Days can not be evaluated wrong", e);
        }

    }

}
