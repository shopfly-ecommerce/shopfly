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
package cloud.shopfly.b2c.core.trade.order.service;

/**
 * Order task
 * @author Snow create in 2018/7/13
 * @version v2.0
 * @since v7.0.0
 */
public interface OrderTaskManager {

    /**
     * Payment to delivery, new order not paid, automatic change：Automatically cancelled
     */
    void cancelTask();

    /**
     * Automatic change after delivery：Confirm the goods
     */
    void rogTask();

    /**
     * Automatic change after confirming receipt of goods：complete
     */
    void completeTask();

    /**
     * Cash on delivery orders automatically change：Payment has been
     */
    void payTask();

    /**
     * After the order is completed, there is no application for after-sales, automatic change：After a timeout
     */
    void serviceTask();

    /**
     * Comments automatically change after how many days after the order is completed：Good reviews.
     */
    void commentTask();

}
