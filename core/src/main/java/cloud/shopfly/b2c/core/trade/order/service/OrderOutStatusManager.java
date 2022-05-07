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

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderOutStatus;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOutTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOutStatusEnum;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Order outbound status business layer
 *
 * @author xlp
 * @version v2.0
 * @since v7.0.0
 * 2018-07-10 14:06:38
 */
public interface OrderOutStatusManager {

    /**
     * Query the order outbound status list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Add order outgoing status
     *
     * @param orderOutStatus Order delivery status
     * @return OrderOutStatus Order delivery status
     */
    OrderOutStatus add(OrderOutStatus orderOutStatus);

    /**
     * Modify the order outgoing status
     *
     * @param orderSn    Order no.
     * @param typeEnum   Outbound type
     * @param statusEnum The delivery status
     * @return OrderOutStatus Order delivery status
     */
    void edit(String orderSn, OrderOutTypeEnum typeEnum, OrderOutStatusEnum statusEnum);

    /**
     * Delete order outgoing status
     *
     * @param id Order outbound status primary key
     */
    void delete(Integer id);

    /**
     * Get order outgoing status
     *
     * @param orderSn  Order no.
     * @param typeEnum Outbound type
     * @return OrderOutStatus  Order delivery status
     */
    OrderOutStatus getModel(String orderSn, OrderOutTypeEnum typeEnum);

}
