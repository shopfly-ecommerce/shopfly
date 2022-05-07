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

import cloud.shopfly.b2c.core.trade.order.model.dos.PayLog;
import cloud.shopfly.b2c.core.trade.order.model.dto.PayLogQueryParam;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Receipt business layer
 *
 * @author xlp
 * @version v2.0
 * @since v7.0.0
 * 2018-07-18 10:39:51
 */
public interface PayLogManager {

    /**
     * Query the list of bills
     *
     * @param queryParam Query parameters
     * @return Page
     */
    Page list(PayLogQueryParam queryParam);

    /**
     * Add a receipt
     *
     * @param payLog voucher
     * @return PayLog voucher
     */
    PayLog add(PayLog payLog);

    /**
     * Revise the receipt
     *
     * @param payLog voucher
     * @param id     Receipt primary key
     * @return PayLog voucher
     */
    PayLog edit(PayLog payLog, Integer id);

    /**
     * Delete the receipt
     *
     * @param id Receipt primary key
     */
    void delete(Integer id);

    /**
     * Get the receipt
     *
     * @param id Receipt primary key
     * @return PayLog  voucher
     */
    PayLog getModel(Integer id);

    /**
     * According to the order No.
     *
     * @param orderSn
     * @return
     */
    PayLog getModel(String orderSn);

    /**
     * Returns non-paged data
     *
     * @param queryParam
     * @return
     */
    List<PayLog> exportExcel(PayLogQueryParam queryParam);
}
