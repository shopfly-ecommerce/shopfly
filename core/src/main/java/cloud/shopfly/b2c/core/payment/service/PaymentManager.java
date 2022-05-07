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

import cloud.shopfly.b2c.core.payment.model.dto.PayParam;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;

import java.util.Map;

/**
 * Global unified Payment interface
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-10
 */
public interface PaymentManager {

    /**
     * Create pay bills and pay them
     *
     * @param payBill
     * @return
     */
    Map pay(PayBill payBill);


    /**
     * Synchronous callback method
     *
     * @param tradeType
     * @param paymentPluginId
     */
    void payReturn(TradeType tradeType, String paymentPluginId);


    /**
     * Pay for asynchronous callbacks
     *
     * @param tradeType
     * @param paymentPluginId
     * @param clientType
     * @return
     */
    String payCallback(TradeType tradeType, String paymentPluginId, ClientType clientType);

    /**
     * Query payment results and update order status
     *
     * @param param
     * @return
     */
    String queryResult(PayParam param);


}
