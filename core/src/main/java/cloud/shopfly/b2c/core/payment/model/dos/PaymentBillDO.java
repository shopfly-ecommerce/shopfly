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
package cloud.shopfly.b2c.core.payment.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 支付帐单实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-16 17:28:07
 */
@Table(name = "es_payment_bill")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentBillDO implements Serializable {

    private static final long serialVersionUID = 4389253275250473L;

    /**
     * 主键id
     */
    @Id(name = "bill_id")
    @ApiModelProperty(hidden = true)
    private Integer billId;
    /**
     * 交易单号
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "单号", required = false)
    private String sn;
    /**
     * 支付账单编号（提交给第三方平台单号）
     */
    @Column(name = "out_trade_no")
    @ApiModelProperty(name = "out_trade_no", value = "提交给第三方平台单号", required = false)
    private String outTradeNo;
    /**
     * 第三方平台返回交易号
     */
    @Column(name = "return_trade_no")
    @ApiModelProperty(name = "return_trade_no", value = "第三方平台返回交易号", required = false)
    private String returnTradeNo;
    /**
     * 是否已支付
     */
    @Column(name = "is_pay")
    @ApiModelProperty(name = "is_pay", value = "是否已支付", required = false)
    private Integer isPay;
    /**
     * 交易类型
     */
    @Column(name = "trade_type")
    @ApiModelProperty(name = "trade_type", value = "交易类型", required = false)
    private String tradeType;
    /**
     * 支付方式名称
     */
    @Column(name = "payment_name")
    @ApiModelProperty(name = "payment_name", value = "支付方式名称", required = false)
    private String paymentName;
    /**
     * 支付参数
     */
    @Column(name = "pay_config")
    @ApiModelProperty(name = "pay_config", value = "支付参数", required = false)
    private String payConfig;
    /**
     * 交易金额
     */
    @Column(name = "trade_price")
    private Double tradePrice;

    /**
     * 支付插件id
     */
    @Column(name = "payment_plugin_id")
    private String paymentPluginId;

    public PaymentBillDO() {
    }

    public PaymentBillDO(String sn, String outTradeNo, String returnTradeNo, Integer isPay, String tradeType, String paymentName, Double tradePrice, String paymentPluginId) {
        this.sn = sn;
        this.outTradeNo = outTradeNo;
        this.returnTradeNo = returnTradeNo;
        this.isPay = isPay;
        this.tradeType = tradeType;
        this.paymentName = paymentName;
        this.tradePrice = tradePrice;
        this.paymentPluginId = paymentPluginId;
    }

    @PrimaryKeyField
    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getReturnTradeNo() {
        return returnTradeNo;
    }

    public void setReturnTradeNo(String returnTradeNo) {
        this.returnTradeNo = returnTradeNo;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getPayConfig() {
        return payConfig;
    }

    public void setPayConfig(String payConfig) {
        this.payConfig = payConfig;
    }

    public Double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(Double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getPaymentPluginId() {
        return paymentPluginId;
    }

    public void setPaymentPluginId(String paymentPluginId) {
        this.paymentPluginId = paymentPluginId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentBillDO that = (PaymentBillDO) o;

        if (billId != null ? !billId.equals(that.billId) : that.billId != null) {
            return false;
        }
        if (sn != null ? !sn.equals(that.sn) : that.sn != null) {
            return false;
        }
        if (outTradeNo != null ? !outTradeNo.equals(that.outTradeNo) : that.outTradeNo != null) {
            return false;
        }
        if (returnTradeNo != null ? !returnTradeNo.equals(that.returnTradeNo) : that.returnTradeNo != null) {
            return false;
        }
        if (isPay != null ? !isPay.equals(that.isPay) : that.isPay != null) {
            return false;
        }
        if (tradeType != null ? !tradeType.equals(that.tradeType) : that.tradeType != null) {
            return false;
        }
        return paymentName != null ? paymentName.equals(that.paymentName) : that.paymentName == null;
    }

    @Override
    public int hashCode() {
        int result = billId != null ? billId.hashCode() : 0;
        result = 31 * result + (sn != null ? sn.hashCode() : 0);
        result = 31 * result + (outTradeNo != null ? outTradeNo.hashCode() : 0);
        result = 31 * result + (returnTradeNo != null ? returnTradeNo.hashCode() : 0);
        result = 31 * result + (isPay != null ? isPay.hashCode() : 0);
        result = 31 * result + (tradeType != null ? tradeType.hashCode() : 0);
        result = 31 * result + (paymentName != null ? paymentName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PaymentBillDO{" +
                "billId=" + billId +
                ", sn='" + sn + "'" +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", returnTradeNo='" + returnTradeNo + '\'' +
                ", isPay=" + isPay +
                ", tradeType='" + tradeType + '\'' +
                ", paymentName='" + paymentName + '\'' +
                '}';
    }


}