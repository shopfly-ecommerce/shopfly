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

import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.ReceiptVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.CheckoutParamVO;

/**
 * Settlement parameters business layer interface
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
public interface CheckoutParamManager {

    /**
     * Gets the creation parameters for the order<br>
     * If no parameter has been set, the default is used
     *
     * @return Settlement parameter
     */
    CheckoutParamVO getParam();


    /**
     * Set the shipping addressid
     *
     * @param addressId Shipping addressid
     */
    void setAddressId(Integer addressId);


    /**
     * Set payment mode
     *
     * @param paymentTypeEnum Method of payment{@link PaymentTypeEnum}
     */
    void setPaymentType(PaymentTypeEnum paymentTypeEnum);


    /**
     * Set the invoice
     *
     * @param receipt invoicevo {@link  ReceiptVO }
     */
    void setReceipt(ReceiptVO receipt);

    /**
     * Cancel the invoice
     */
    void deleteReceipt();


    /**
     * Set delivery time
     *
     * @param receiveTime Delivery time
     */
    void setReceiveTime(String receiveTime);


    /**
     * Set order remarks
     *
     * @param remark The order note
     */
    void setRemark(String remark);

    /**
     * Set order Source
     *
     * @param clientType Client source
     */
    void setClientType(String clientType);


    /**
     * Set all parameters in batches
     *
     * @param param Settlement parameter{@link CheckoutParamVO}
     */
    void setAll(CheckoutParamVO param);

    /**
     * Check whether cash on delivery is supported
     * @param paymentTypeEnum
     */
    void checkCod(PaymentTypeEnum paymentTypeEnum);

}
