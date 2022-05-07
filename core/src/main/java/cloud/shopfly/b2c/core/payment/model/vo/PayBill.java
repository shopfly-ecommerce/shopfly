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
 * Pay the bills,
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-11 16:06:57
 */
public class PayBill implements Serializable {


    /**
     * Serial number（交易或订单Serial number，或其它要扩展的其它类型Serial number）
     */
    private String sn;

    /**
     * The bill number,To pass to the third party platform, do not care, the system automatically generated
     */
    private String billSn;

    /**
     * The amount to be paid
     */
    private Double orderPrice;

    /**
     * normal:Normal web page redirect
     * qr:Qr code scanning
     */
    private String payMode;

    /**
     * Transaction type
     */
    private TradeType tradeType;


    /**
     * Client type
     */
    private ClientType clientType;

    /**
     * Pay the plug-in
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
