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
 * 交易记录表业务层
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-25 15:37:56
 */
public interface TransactionRecordManager {

    /**
     * 查询交易记录表列表
     *
     * @param orderSn 页码
     * @return List
     */
    List<TransactionRecord> listAll(String orderSn);

    /**
     * 添加交易记录表
     *
     * @param transactionRecord 交易记录表
     * @return TransactionRecord 交易记录表
     */
    TransactionRecord add(TransactionRecord transactionRecord);

    /**
     * 修改交易记录表
     *
     * @param transactionRecord 交易记录表
     * @param id                交易记录表主键
     * @return TransactionRecord 交易记录表
     */
    TransactionRecord edit(TransactionRecord transactionRecord, Integer id);

    /**
     * 删除交易记录表
     *
     * @param id 交易记录表主键
     */
    void delete(Integer id);

    /**
     * 获取交易记录表
     *
     * @param id 交易记录表主键
     * @return TransactionRecord  交易记录表
     */
    TransactionRecord getModel(Integer id);

}
