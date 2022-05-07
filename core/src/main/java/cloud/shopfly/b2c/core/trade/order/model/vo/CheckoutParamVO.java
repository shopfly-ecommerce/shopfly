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
package cloud.shopfly.b2c.core.trade.order.model.vo;

import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Settlement parameterVO
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel(description = "Settlement parameter")
public class CheckoutParamVO {

    @ApiModelProperty(name = "address_id", value = "Shipping addressid")
    private Integer addressId;

    @ApiModelProperty(name = "payment_type", value = "Method of payment")
    private PaymentTypeEnum paymentType;

    @ApiModelProperty(value = "The invoice information")
    private ReceiptVO receipt;

    @ApiModelProperty(name = "receive_time", value = "The goods time")
    private String receiveTime;

    @ApiModelProperty(value = "The order note")
    private String remark;

    @ApiModelProperty(name = "client_type", value = "Client type")
    private String clientType;


    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public PaymentTypeEnum getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeEnum paymentType) {
        this.paymentType = paymentType;
    }

    public ReceiptVO getReceipt() {
        return receipt;
    }

    public void setReceipt(ReceiptVO receipt) {
        this.receipt = receipt;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    @Override
    public String toString() {
        return "CheckoutParamVO{" +
                "addressId=" + addressId +
                ", paymentType=" + paymentType +
                ", receipt=" + receipt +
                ", receiveTime='" + receiveTime + '\'' +
                ", remark='" + remark + '\'' +
                ", clientType='" + clientType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CheckoutParamVO that = (CheckoutParamVO) o;

        return new EqualsBuilder()
                .append(addressId, that.addressId)
                .append(paymentType, that.paymentType)
                .append(receipt, that.receipt)
                .append(receiveTime, that.receiveTime)
                .append(remark, that.remark)
                .append(clientType, that.clientType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(addressId)
                .append(paymentType)
                .append(receipt)
                .append(receiveTime)
                .append(remark)
                .append(clientType)
                .toHashCode();
    }
}
