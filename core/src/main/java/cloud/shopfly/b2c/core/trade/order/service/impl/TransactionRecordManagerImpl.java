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
package cloud.shopfly.b2c.core.trade.order.service.impl;

import cloud.shopfly.b2c.core.trade.order.model.dos.TransactionRecord;
import cloud.shopfly.b2c.core.trade.order.service.TransactionRecordManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 交易记录表业务类
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-25 15:37:56
 */
@Service
public class TransactionRecordManagerImpl implements TransactionRecordManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public List listAll(String orderSn) {

        String sql = "select * from es_transaction_record  where order_sn = ?";
        List list = this.daoSupport.queryForList(sql, TransactionRecord.class, orderSn);

        return list;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TransactionRecord add(TransactionRecord transactionRecord) {
        this.daoSupport.insert(transactionRecord);
        return transactionRecord;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TransactionRecord edit(TransactionRecord transactionRecord, Integer id) {
        this.daoSupport.update(transactionRecord, id);
        return transactionRecord;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(TransactionRecord.class, id);
    }

    @Override
    public TransactionRecord getModel(Integer id) {
        return this.daoSupport.queryForObject(TransactionRecord.class, id);
    }
}
