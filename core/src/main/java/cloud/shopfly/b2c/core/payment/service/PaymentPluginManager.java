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
 * 支付插件
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-11 16:06:57
 */
public interface PaymentPluginManager {


    /**
     * 获取支付插件id
     *
     * @return
     */
    String getPluginId();

    /**
     * 支付名称
     *
     * @return
     */
    String getPluginName();

    /**
     * 自定义客户端配置文件
     *
     * @return
     */
    List<ClientConfig> definitionClientConfig();

    /**
     * 支付
     *
     * @param bill
     * @return
     */
    Map pay(PayBill bill);

    /**
     * 同步回调
     *
     * @param tradeType
     */
    void onReturn(TradeType tradeType);

    /**
     * 异步回调
     *
     * @param tradeType
     * @param clientType
     * @return
     */
    String onCallback(TradeType tradeType, ClientType clientType);

    /**
     * 主动查询支付结果
     *
     * @param bill
     * @return
     */
    String onQuery(PayBill bill);


    /**
     * 退款，原路退回
     *
     * @param bill
     * @return
     */
    boolean onTradeRefund(RefundBill bill);

    /**
     * 查询退款状态
     *
     * @param bill
     * @return
     */
    String queryRefundStatus(RefundBill bill);

    /**
     * 是否支持原路退回   0 不支持  1支持
     *
     * @return
     */
    Integer getIsRetrace();

}
