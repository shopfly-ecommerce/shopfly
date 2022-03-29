/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.model.vo;

import dev.shopflix.core.trade.order.model.enums.PaymentTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 结算参数VO
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel(description = "结算参数")
public class CheckoutParamVO {

    @ApiModelProperty(name = "address_id", value = "收货地址id")
    private Integer addressId;

    @ApiModelProperty(name = "payment_type", value = "支付方式")
    private PaymentTypeEnum paymentType;

    @ApiModelProperty(value = "发票信息")
    private ReceiptVO receipt;

    @ApiModelProperty(name = "receive_time", value = "收货时间")
    private String receiveTime;

    @ApiModelProperty(value = "订单备注")
    private String remark;

    @ApiModelProperty(name = "client_type", value = "客户端类型")
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
