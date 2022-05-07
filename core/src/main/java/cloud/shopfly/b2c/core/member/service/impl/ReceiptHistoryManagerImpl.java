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
package cloud.shopfly.b2c.core.member.service.impl;

import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.member.model.dos.ReceiptHistory;
import cloud.shopfly.b2c.core.member.model.vo.ReceiptHistoryVO;
import cloud.shopfly.b2c.core.member.service.ReceiptHistoryManager;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Invoice history business class
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-20 20:48:09
 */
@Service
public class ReceiptHistoryManagerImpl implements ReceiptHistoryManager {

    @Autowired

    private DaoSupport memberDaoSupport;

    @Autowired
    private OrderClient orderClient;

    @Override
    public Page list(int page, int pageSize) {
        // Invoices with a value of 0 will not be displayed
        StringBuffer sqlBuffer = new StringBuffer("select * from es_receipt_history where receipt_amount != 0 ");
        sqlBuffer.append(" order by add_time desc");
        Page webPage = this.memberDaoSupport.queryForPage(sqlBuffer.toString(), page, pageSize, ReceiptHistory.class);
        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ReceiptHistory add(ReceiptHistory receiptHistory) {
        this.memberDaoSupport.insert(receiptHistory);
        receiptHistory.setHistoryId(memberDaoSupport.getLastId("es_history_receipt"));
        return receiptHistory;
    }

    @Override
    public ReceiptHistory getReceiptHistory(String orderSn) {
        ReceiptHistory receiptHistory = this.memberDaoSupport.queryForObject("select * from es_receipt_history where order_sn = ?", ReceiptHistory.class, orderSn);
        if (receiptHistory != null) {
            return receiptHistory;
        }
        return new ReceiptHistory();
    }

    @Override
    public ReceiptHistoryVO getReceiptDetail(Integer historyId) {
        // Get invoice details
        StringBuffer sqlBuffer = new StringBuffer("select * from es_receipt_history where history_id = ?");
        ReceiptHistory receiptHistory = this.memberDaoSupport.queryForObject(sqlBuffer.toString(), ReceiptHistory.class, historyId);
        if (receiptHistory == null) {
            return new ReceiptHistoryVO();
        }
        // Query order information
        OrderDetailDTO orderDetailDTO = orderClient.getModel(receiptHistory.getOrderSn());
        ReceiptHistoryVO receiptHistoryVO = new ReceiptHistoryVO(receiptHistory, orderDetailDTO);

        return receiptHistoryVO;
    }
}
