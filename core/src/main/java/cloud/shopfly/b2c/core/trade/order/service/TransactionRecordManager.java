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

import cloud.shopfly.b2c.core.trade.order.model.dos.TransactionRecord;

import java.util.List;

/**
 * Transaction record sheet business layer
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-25 15:37:56
 */
public interface TransactionRecordManager {

    /**
     * Query the list of trading records
     *
     * @param orderSn The page number
     * @return List
     */
    List<TransactionRecord> listAll(String orderSn);

    /**
     * Add transaction record table
     *
     * @param transactionRecord Transaction record
     * @return TransactionRecord Transaction record
     */
    TransactionRecord add(TransactionRecord transactionRecord);

    /**
     * Revise the transaction record sheet
     *
     * @param transactionRecord Transaction record
     * @param id                Primary key of transaction record table
     * @return TransactionRecord Transaction record
     */
    TransactionRecord edit(TransactionRecord transactionRecord, Integer id);

    /**
     * Delete the transaction record table
     *
     * @param id Primary key of transaction record table
     */
    void delete(Integer id);

    /**
     * Obtain trading records
     *
     * @param id Primary key of transaction record table
     * @return TransactionRecord  Transaction record
     */
    TransactionRecord getModel(Integer id);

}
