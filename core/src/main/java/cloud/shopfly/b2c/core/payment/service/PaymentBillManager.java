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
package cloud.shopfly.b2c.core.payment.service;

import cloud.shopfly.b2c.core.payment.model.dos.PaymentBillDO;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Pay bills business layer
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-16 17:28:07
 */
public interface PaymentBillManager {

    /**
     * Check the list of paid bills
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Add payment bill
     *
     * @param paymentBill Pay your bills
     * @return PaymentBill Pay your bills
     */
    PaymentBillDO add(PaymentBillDO paymentBill);

    /**
     * Payment successfully called
     *
     * @param billSn        Pay bill number
     * @param returnTradeNo The third party platform returns the flyer number（The payment slip number of the third-party platform）
     * @param tradeType     Transaction type
     * @param payPrice      Pay the amount
     */
    void paySuccess(String billSn, String returnTradeNo, TradeType tradeType, double payPrice);


    /**
     * Query the corresponding payment flow using the order number and transaction type, the last one
     *
     * @param sn
     * @param tradeType
     * @return
     */
    PaymentBillDO getBillBySnAndTradeType(String sn, String tradeType);

    /**
     * Use the third party tracking number to query the flow
     *
     * @param returnTradeNo
     * @return
     */
    PaymentBillDO getBillByReturnTradeNo(String returnTradeNo);


    /**
     * According to thebillSnModify the third party transaction serial number
     * @param billSn
     * @param returnTradeNo
     */
    void updateTradeNoByBillSn(String billSn,String returnTradeNo);

    /**
     * According to thebillSnRead the data
     * @param billSn
     * @return
     */
    PaymentBillDO getBillByBillSn(String billSn);


    List<PaymentBillDO> getWaitPay();

}
