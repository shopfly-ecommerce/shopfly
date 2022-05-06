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
 * 订单任务
 * @author Snow create in 2018/7/13
 * @version v2.0
 * @since v7.0.0
 */
public interface OrderTaskManager {

    /**
     * 款到发货，新订单未付款，自动变更：自动取消
     */
    void cancelTask();

    /**
     * 发货之后，自动变更：确认收货
     */
    void rogTask();

    /**
     * 确认收货后，自动变更：完成
     */
    void completeTask();

    /**
     * 货到付款订单，自动变更：已付款
     */
    void payTask();

    /**
     * 订单完成后，没有申请过售后，自动变更：售后超时
     */
    void serviceTask();

    /**
     * 订单完成后，多少天后，评论自动变更：好评。
     */
    void commentTask();

}
