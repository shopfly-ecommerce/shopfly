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
package cloud.shopfly.b2c.core.trade.order.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;


/**
 * Receipt entity
 *
 * @author xlp
 * @version v2.0
 * @since v7.0.0
 * 2018-07-18 10:39:51
 */
@Table(name = "es_pay_log")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PayLog implements Serializable {

    private static final long serialVersionUID = 7244927488352294L;

    /**
     * voucherID
     */
    @Id(name = "pay_log_id")
    @ApiModelProperty(hidden = true)
    private Integer payLogId;

    /**
     * Receipt serial number
     */
    @Column(name = "pay_log_sn")
    @ApiModelProperty(name = "order_sn", value = "Receipt serial number", required = false)
    private String payLogSn;

    /**
     * Order no.
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "Order no.", required = false)
    private String orderSn;

    /**
     * Payment typePaymentTypeEnum
     */
    @Column(name = "pay_type")
    @ApiModelProperty(name = "pay_type", value = "Payment type", required = false)
    private String payType;

    /**
     * Method of paymentalipay,wechat
     */
    @Column(name = "pay_way")
    @ApiModelProperty(name = "pay_way", value = "Method of payment", required = false)
    private String payWay;

    /**
     * Time of payment
     */
    @Column(name = "pay_time")
    @ApiModelProperty(name = "pay_time", value = "Time of payment", required = false)
    private Long payTime;

    /**
     * The payment amount
     */
    @Column(name = "pay_money")
    @ApiModelProperty(name = "pay_money", value = "The payment amount", required = false)
    private Double payMoney;

    /**
     * Paying Member name
     */
    @Column(name = "pay_member_name")
    @ApiModelProperty(name = "pay_member_name", value = "Paying Member name", required = false)
    private String payMemberName;

    /**
     * Payment status
     */
    @Column(name = "pay_status")
    @ApiModelProperty(name = "pay_status", value = "Payment status", required = false)
    private String payStatus;


    /**
     * The serial number returned by payment method
     */
    @Column(name = "pay_order_no")
    @ApiModelProperty(name = "pay_order_no", value = "Payment method returns the transaction number", required = false)
    private String payOrderNo;

    @PrimaryKeyField
    public Integer getPayLogId() {
        return payLogId;
    }

    public void setPayLogId(Integer payLogId) {
        this.payLogId = payLogId;
    }

    public String getPayLogSn() {
        return payLogSn;
    }

    public void setPayLogSn(String payLogSn) {
        this.payLogSn = payLogSn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayMemberName() {
        return payMemberName;
    }

    public void setPayMemberName(String payMemberName) {
        this.payMemberName = payMemberName;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PayLog payLog = (PayLog) o;

        return new EqualsBuilder()
                .append(payLogId, payLog.payLogId)
                .append(payLogSn, payLog.payLogSn)
                .append(orderSn, payLog.orderSn)
                .append(payType, payLog.payType)
                .append(payWay, payLog.payWay)
                .append(payTime, payLog.payTime)
                .append(payMoney, payLog.payMoney)
                .append(payMemberName, payLog.payMemberName)
                .append(payStatus, payLog.payStatus)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(payLogId)
                .append(payLogSn)
                .append(orderSn)
                .append(payType)
                .append(payWay)
                .append(payTime)
                .append(payMoney)
                .append(payMemberName)
                .append(payStatus)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "PayLog{" +
                "payLogId=" + payLogId +
                ", payLogSn='" + payLogSn + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", payType='" + payType + '\'' +
                ", payWay='" + payWay + '\'' +
                ", payTime=" + payTime +
                ", payMoney=" + payMoney +
                ", payMemberName='" + payMemberName + '\'' +
                ", payStatus='" + payStatus + '\'' +
                '}';
    }
}
