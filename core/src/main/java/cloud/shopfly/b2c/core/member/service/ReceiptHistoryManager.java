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
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.ReceiptHistory;
import cloud.shopfly.b2c.core.member.model.vo.ReceiptHistoryVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Invoice history business layer
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-20 20:48:09
 */
public interface ReceiptHistoryManager {

    /**
     * Query the invoice history list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Add invoice history
     *
     * @param receiptHistory Invoice history
     * @return ReceiptHistory Invoice history
     */
    ReceiptHistory add(ReceiptHistory receiptHistory);

    /**
     * According to the ordersnExample Query historical invoice information
     *
     * @param orderSn The ordersn
     * @return Historical invoice Information
     */
    ReceiptHistory getReceiptHistory(String orderSn);


    /**
     * Get invoice history details, including the shipping address for the goodsskuThe relevant information
     *
     * @param historyId Invoice historicalid
     * @return Detailed invoiceVO
     */
    ReceiptHistoryVO getReceiptDetail(Integer historyId);

}
