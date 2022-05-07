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

import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.service.PaymentCallbackDevice;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Order payment callback
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-16
 */

@Service
public class OrderCallbackDevice implements PaymentCallbackDevice {


    @Autowired
    private OrderClient orderClient;

    /**
     * Call to orderclientComplete the modification of order payment status
     *
     * @param outTradeNo
     * @param returnTradeNo
     * @param payPrice
     */
    @Override
    public void paySuccess(String outTradeNo, String returnTradeNo, double payPrice) {
        orderClient.payOrder(outTradeNo, payPrice, returnTradeNo, OrderPermission.client.name());
    }

    /**
     * Defining transaction types
     *
     * @return
     */
    @Override
    public TradeType tradeType() {

        return TradeType.order;
    }

}
