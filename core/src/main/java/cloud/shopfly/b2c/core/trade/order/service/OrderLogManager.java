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

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderLogDO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Order log table business layer
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-16 12:01:34
 */
public interface OrderLogManager {

    /**
     * Example Query the order log list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);


    /**
     * Example Query the order log list
     *
     * @param orderSn Order no.
     * @return List
     */
    List listAll(String orderSn);


    /**
     * Add order log table
     *
     * @param orderLog Order log table
     * @return OrderLog Order log table
     */
    OrderLogDO add(OrderLogDO orderLog);

    /**
     * Modify the order log table
     *
     * @param orderLog Order log table
     * @param id       Order log table primary key
     * @return OrderLog Order log table
     */
    OrderLogDO edit(OrderLogDO orderLog, Integer id);

    /**
     * Delete the order log table
     *
     * @param id Order log table primary key
     */
    void delete(Integer id);

    /**
     * Get the order log table
     *
     * @param id Order log table primary key
     * @return OrderLog  Order log table
     */
    OrderLogDO getModel(Integer id);

}
