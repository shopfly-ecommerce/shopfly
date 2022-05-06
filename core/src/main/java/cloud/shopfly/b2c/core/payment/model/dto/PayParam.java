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
package cloud.shopfly.b2c.core.payment.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author fk
 * @version v2.0
 * @Description: 调用支付使用参数
 * @date 2018/4/1616:54
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PayParam {

    @ApiModelProperty(value = "单号，订单号或者交易号", hidden = true)
    private String sn;

    @ApiModelProperty(value = "支付插件id", name = "payment_plugin_id", required = true)
    private String paymentPluginId;

    @ApiModelProperty(value = "支付模式，正常normal， 二维码 ar,枚举类型PaymentPatternEnum", name = "pay_mode", required = true)
    private String payMode;

    @ApiModelProperty(value = "调用客户端PC,WAP,NATIVE,REACT", name = "client_type", required = true, allowableValues = "PC,WAP,NATIVE,REACT")
    private String clientType;

    @ApiModelProperty(value = "交易类型，trade，order", name = "trade_type", hidden = true)
    private String tradeType;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPaymentPluginId() {
        return paymentPluginId;
    }

    public void setPaymentPluginId(String paymentPluginId) {
        this.paymentPluginId = paymentPluginId;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
