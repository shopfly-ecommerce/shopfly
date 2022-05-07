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
package cloud.shopfly.b2c.core.payment.service.impl;

import cloud.shopfly.b2c.core.payment.PaymentErrorCode;
import cloud.shopfly.b2c.core.payment.model.dos.PaymentBillDO;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.service.PaymentBillManager;
import cloud.shopfly.b2c.core.payment.service.PaymentCallbackDevice;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.logs.Debugger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Pay bills business class
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-16 17:28:07
 */
@Service
public class PaymentBillManagerImpl implements PaymentBillManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private Debugger debugger;

    @Autowired
    private List<PaymentCallbackDevice> callbackDeviceList;

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_payment_bill  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, PaymentBillDO.class);

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PaymentBillDO add(PaymentBillDO paymentBill) {
        this.daoSupport.insert(paymentBill);

        return paymentBill;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void paySuccess(String billSn, String returnTradeNo, TradeType tradeType, double payPrice) {

        if (callbackDeviceList == null && callbackDeviceList.isEmpty()) {
            logger.error("The payment callback failed because：【There are no callback declarations】");
            throw new ServiceException(PaymentErrorCode.E507.code(), "The payment callback failed because：【There are no callback declarations】");
        } else {

            PaymentCallbackDevice device = findDevice(tradeType);
            if (device == null) {
                logger.error("The payment callback failed because：【" + tradeType.name() + "No callback is adapted】");
                debugger.log("The payment callback failed because：【" + tradeType.name() + "No callback is adapted】");
                throw new ServiceException(PaymentErrorCode.E507.code(), "The payment callback failed because：【" + tradeType.name() + "No callback is adapted】");
            }

            // Find the transaction number based on the payment number
            PaymentBillDO bill = this.getByBillSn(billSn);

            if (bill == null) {
                debugger.log("The payment callback failed because：【" + tradeType.name() + "Type of transaction,Numbers for：" + billSn + "No corresponding bill was found】");
                logger.error("The payment callback failed because：【" + tradeType.name() + "Type of transaction,Numbers for：" + billSn + "No corresponding bill was found】");
                throw new RuntimeException("The payment callback failed because：【" + tradeType.name() + "The transaction number of the type is：" + billSn + "No corresponding bill was found】");
            }

            if(logger.isDebugEnabled()){
                logger.debug("Find the bill：");
            }
            debugger.log("Find the bill：");
            if(logger.isDebugEnabled()){
                logger.debug(bill.toString());
            }
            debugger.log(bill.toString());

            String tradeSn = bill.getSn();

            // Call the callback to complete the change in the transaction state
            device.paySuccess(tradeSn, returnTradeNo, payPrice);
            if(logger.isDebugEnabled()){
                logger.debug("call：" + device + "successful");
            }
            debugger.log("call：" + device + "successful");

            // Modify the status of paying bills
            daoSupport.execute("update es_payment_bill set is_pay=1,return_trade_no=? where bill_id=?", returnTradeNo, bill.getBillId());

            if (logger.isDebugEnabled()){
                logger.debug("Succeeded in changing the paid bill status");
            }
            debugger.log("Succeeded in changing the paid bill status");
        }

    }

    private PaymentBillDO getByBillSn(String billSn) {
        String sql = "select * from es_payment_bill where out_trade_no = ?";
        return this.daoSupport.queryForObject(sql, PaymentBillDO.class, billSn);

    }

    /**
     * Go to the appropriate callbacks in the callbacks list
     *
     * @param tradeType Transaction type
     * @return
     */
    private PaymentCallbackDevice findDevice(TradeType tradeType) {
        for (PaymentCallbackDevice device : callbackDeviceList) {
            if (tradeType.equals(device.tradeType())) {
                return device;
            }
        }
        return null;
    }


    @Override
    public PaymentBillDO getBillBySnAndTradeType(String sn, String tradeType) {

        String sql = "select * from es_payment_bill where sn = ? and trade_type = ? order by bill_id desc limit 0,1";

        return this.daoSupport.queryForObject(sql, PaymentBillDO.class, sn, tradeType);
    }

    @Override
    public PaymentBillDO getBillByReturnTradeNo(String returnTradeNo) {

        String sql = "select * from es_payment_bill where return_trade_no = ? ";

        return this.daoSupport.queryForObject(sql, PaymentBillDO.class, returnTradeNo);
    }

    @Override
    public void updateTradeNoByBillSn(String billSn, String returnTradeNo) {
        System.out.println(returnTradeNo+"======="+billSn);
        String sql = "update es_payment_bill set return_trade_no = ? where sn = ?";
        this.daoSupport.execute(sql, returnTradeNo, billSn);
    }

    @Override
    public PaymentBillDO getBillByBillSn(String billSn) {
        String sql = "select * from es_payment_bill where sn = ? ";
        return this.daoSupport.queryForObject(sql, PaymentBillDO.class, billSn);
    }

    @Override
    public List<PaymentBillDO> getWaitPay() {
        String sql = "SELECT DISTINCT sn,is_pay,trade_price,return_trade_no,trade_type,payment_plugin_id from es_payment_bill p where is_pay = 0 and p.sn not in (SELECT DISTINCT pb.sn from  es_payment_bill pb where pb.is_pay = 1) ORDER BY bill_id desc";
        return this.daoSupport.queryForList(sql,PaymentBillDO.class);
    }
}
