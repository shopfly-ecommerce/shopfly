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
package cloud.shopfly.b2c.core.payment.model.vo;

import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;

import java.io.Serializable;

/**
 * 支付账单，
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-11 16:06:57
 */
public class PayBill implements Serializable {


    /**
     * 编号（交易或订单编号，或其它要扩展的其它类型编号）
     */
    private String sn;

    /**
     * 账单编号,要传递给第三方平台的，不要管，系统自动生成
     */
    private String billSn;

    /**
     * 要支付的金额
     */
    private Double orderPrice;

    /**
     * normal:正常的网页跳转
     * qr:二维码扫描
     */
    private String payMode;

    /**
     * 交易类型
     */
    private TradeType tradeType;


    /**
     * 客户端类型
     */
    private ClientType clientType;

    /**
     * 支付插件
     */
    private String pluginId;

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public String getBillSn() {
        return billSn;
    }

    public void setBillSn(String billSn) {
        this.billSn = billSn;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getSn() {
        return sn;
    }

    @Override
    public String toString() {
        return "PayBill{" +
                "sn='" + sn + '\'' +
                ", billSn='" + billSn + '\'' +
                ", orderPrice=" + orderPrice +
                ", payMode='" + payMode + '\'' +
                ", tradeType=" + tradeType +
                ", clientType=" + clientType +
                ", pluginId='" + pluginId + '\'' +
                '}';
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

}
