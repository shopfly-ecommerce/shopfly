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
 * Refund applicationVO
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 In the morning10:33 2018/5/2
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BuyerCancelOrderVO implements Serializable {

    private static final long serialVersionUID = 758087208773569549L;
    @ApiModelProperty(name = "order_sn", value = "Order no.", required = true)
    @NotBlank(message = "Order number Is mandatory")
    private String orderSn;

    @ApiModelProperty(name = "refund_reason", value = "A refund reason", required = true)
    @NotBlank(message = "Refund reason must be filled in")
    private String refundReason;

    @ApiModelProperty(name = "account_type", value = "Account type Alipay:ALIPAY, WeChat:WEIXINPAY, Bank transfer:BANKTRANSFER", allowableValues = "ALIPAY,WEIXINPAY,BANKTRANSFER")
    private String accountType;

    @ApiModelProperty(name = "return_account", value = "Refund account", required = false)
    private String returnAccount;

    @ApiModelProperty(name = "customer_remark", value = "Customer remarks", required = false)
    private String customerRemark;

    @ApiModelProperty(name = "bank_name", value = "Bank name", required = false)
    private String bankName;

    @ApiModelProperty(name = "bank_account_number", value = "The bank account", required = false)
    private String bankAccountNumber;

    @ApiModelProperty(name = "bank_account_name", value = "Bank account name", required = false)
    private String bankAccountName;

    @ApiModelProperty(name = "bank_deposit_name", value = "Bank opening bank", required = false)
    private String bankDepositName;

    @ApiModelProperty(name = "refund_sn", value = "The refund number", required = false, hidden = true)
    private String refundSn;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
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
                ", accountType='" + accountType + '\'' +
                ", returnAccount='" + returnAccount + '\'' +
                ", customerRemark='" + customerRemark + '\'' +
                ", refundReason='" + refundReason + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", bankAccountName='" + bankAccountName + '\'' +
                ", bankDepositName='" + bankDepositName + '\'' +
                ", refundSn='" + refundSn + '\'' +
                '}';
    }
}
