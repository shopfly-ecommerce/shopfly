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

import cloud.shopfly.b2c.core.payment.model.enums.TradeType;

/**
 * 支付回调器
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-16
 */

public interface PaymentCallbackDevice {


    /**
     * 第三方平台支付成功后回调的方法
     *
     * @param outTradeNo
     * @param returnTradeNo
     * @param payPrice
     */
    void paySuccess(String outTradeNo, String returnTradeNo, double payPrice);


    /**
     * 定义此回调器支持的交易类型
     *
     * @return
     */
    TradeType tradeType();
}
