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

import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.ClientConfig;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.model.vo.RefundBill;

import java.util.List;
import java.util.Map;

/**
 * Pay the plug-in
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-11 16:06:57
 */
public interface PaymentPluginManager {


    /**
     * Get payment plug-inid
     *
     * @return
     */
    String getPluginId();

    /**
     * Pay the name
     *
     * @return
     */
    String getPluginName();

    /**
     * Customize the client configuration file
     *
     * @return
     */
    List<ClientConfig> definitionClientConfig();

    /**
     * pay
     *
     * @param bill
     * @return
     */
    Map pay(PayBill bill);

    /**
     * Synchronous callback
     *
     * @param tradeType
     */
    void onReturn(TradeType tradeType);

    /**
     * An asynchronous callback
     *
     * @param tradeType
     * @param clientType
     * @return
     */
    String onCallback(TradeType tradeType, ClientType clientType);

    /**
     * Proactively query payment results
     *
     * @param bill
     * @return
     */
    String onQuery(PayBill bill);


    /**
     * Refund, return the original way
     *
     * @param bill
     * @return
     */
    boolean onTradeRefund(RefundBill bill);

    /**
     * Querying refund Status
     *
     * @param bill
     * @return
     */
    String queryRefundStatus(RefundBill bill);

    /**
     * Whether to support the original way back0 Does not support1support
     *
     * @return
     */
    Integer getIsRetrace();

}
