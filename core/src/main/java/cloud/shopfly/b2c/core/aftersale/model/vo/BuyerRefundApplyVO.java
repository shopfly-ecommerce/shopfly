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
package cloud.shopfly.b2c.core.aftersale.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 退款申请VO
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 上午10:33 2018/5/2
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BuyerRefundApplyVO implements Serializable {

    private static final long serialVersionUID = 758087208773569549L;
    @ApiModelProperty(name = "order_sn", value = "订单编号", required = true)
    @NotBlank(message = "订单编号必填")
    private String orderSn;

    @ApiModelProperty(name = "sku_id", value = "货品id", required = false)
    private Integer skuId;

    @ApiModelProperty(name = "return_num", value = "退货数量", required = false)
    private Integer returnNum;

    @ApiModelProperty(name = "refund_reason", value = "退款原因", required = true)
    @NotBlank(message = "退款原因必填")
    private String refundReason;

    @ApiModelProperty(name = "account_type", value = "账号类型 支付宝:ALIPAY, 微信:WEIXINPAY, 银行转账:BANKTRANSFER", allowableValues = "ALIPAY,WEIXINPAY,BANKTRANSFER", required = false)
    private String accountType;

    @ApiModelProperty(name = "return_account", value = "退款账号", required = false)
    private String returnAccount;

    @ApiModelProperty(name = "customer_remark", value = "客户备注", required = false)
    private String customerRemark;

    @ApiModelProperty(name = "bank_name", value = "银行名称", required = false)
    private String bankName;

    @ApiModelProperty(name = "bank_account_number", value = "银行账号", required = false)
    private String bankAccountNumber;

    @ApiModelProperty(name = "bank_account_name", value = "银行开户名", required = false)
    private String bankAccountName;

    @ApiModelProperty(name = "bank_deposit_name", value = "银行开户行", required = false)
    private String bankDepositName;

    @ApiModelProperty(name = "refuse_type", value = "退款/退货", required = false, hidden = true)
    private String refuseType;

    @ApiModelProperty(name = "refund_sn", value = "退款单号", required = false, hidden = true)
    private String refundSn;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getReturnAccount() {
        return returnAccount;
    }

    public void setReturnAccount(String returnAccount) {
        this.returnAccount = returnAccount;
    }

    public String getCustomerRemark() {
        return customerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankDepositName() {
        return bankDepositName;
    }

    public void setBankDepositName(String bankDepositName) {
        this.bankDepositName = bankDepositName;
    }

    public String getRefuseType() {
        return refuseType;
    }

    public void setRefuseType(String refuseType) {
        this.refuseType = refuseType;
    }

    public String getRefundSn() {
        return refundSn;
    }

    public void setRefundSn(String refundSn) {
        this.refundSn = refundSn;
    }

    @Override
    public String toString() {
        return "BuyerRefundApplyVO{" +
                "orderSn='" + orderSn + '\'' +
                ", skuId=" + skuId +
                ", returnNum=" + returnNum +
                ", accountType='" + accountType + '\'' +
                ", returnAccount='" + returnAccount + '\'' +
                ", customerRemark='" + customerRemark + '\'' +
                ", refundReason='" + refundReason + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", bankAccountName='" + bankAccountName + '\'' +
                ", bankDepositName='" + bankDepositName + '\'' +
                ", refuseType='" + refuseType + '\'' +
                ", refundSn='" + refundSn + '\'' +
                '}';
    }
}
