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
package cloud.shopfly.b2c.core.aftersale.model.dos;

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
 * refund(cargo)To the entity
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-02 15:43:16
 */
@Table(name = "es_refund")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RefundDO implements Serializable {

    private static final long serialVersionUID = 3043923149260205L;

    /**
     * refund(cargo)A single paragraphid
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * Return of the goods(paragraph)A single number
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "Return of the goods(paragraph)A single number", required = false)
    private String sn;
    /**
     * membersid
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "membersid", required = false)
    private Integer memberId;
    /**
     * Member name
     */
    @Column(name = "member_name")
    @ApiModelProperty(value = "Member name")
    private String memberName;
    /**
     * Order no.
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "Order no.", required = false)
    private String orderSn;
    /**
     * refund(cargo)Kind of state
     */
    @Column(name = "refund_status")
    @ApiModelProperty(name = "refund_status", value = "refund(cargo)Kind of state", required = false)
    private String refundStatus;
    /**
     * Last update
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "Last update", required = false)
    private Long createTime;
    /**
     * The refund amount
     */
    @Column(name = "refund_price")
    @ApiModelProperty(name = "refund_price", value = "The refund amount", required = false)
    private Double refundPrice;
    /**
     * Return the integral
     */
    @Column(name = "refund_point")
    @ApiModelProperty(name = "refund_point", value = "Return the integral", required = false)
    private Integer refundPoint;
    /**
     * The refund way(The original way back, online payment, offline payment)
     */
    @Column(name = "refund_way")
    @ApiModelProperty(name = "refund_way", value = "The refund way(The original way back, offline payment)", required = false)
    private String refundWay;
    /**
     * Type of Refund Account
     */
    @Column(name = "account_type")
    @ApiModelProperty(name = "account_type", value = "Type of Refund Account", required = false)
    private String accountType;
    /**
     * Refund account
     */
    @Column(name = "return_account")
    @ApiModelProperty(name = "return_account", value = "Refund account", required = false)
    private String returnAccount;
    /**
     * Customer remarks
     */
    @Column(name = "customer_remark")
    @ApiModelProperty(name = "customer_remark", value = "Customer remarks", required = false)
    private String customerRemark;
    /**
     * Warehouse note
     */
    @Column(name = "warehouse_remark")
    @ApiModelProperty(name = "warehouse_remark", value = "Warehouse note", required = false)
    private String warehouseRemark;
    /**
     * Financial note
     */
    @Column(name = "finance_remark")
    @ApiModelProperty(name = "finance_remark", value = "Financial note", required = false)
    private String financeRemark;
    /**
     * A refund reason
     */
    @Column(name = "refund_reason")
    @ApiModelProperty(name = "refund_reason", value = "A refund reason", required = false)
    private String refundReason;
    /**
     * Refuse to reason
     */
    @Column(name = "refuse_reason")
    @ApiModelProperty(name = "refuse_reason", value = "Refuse to reason", required = false)
    private String refuseReason;
    /**
     * Bank name
     */
    @Column(name = "bank_name")
    @ApiModelProperty(name = "bank_name", value = "Bank name", required = false)
    private String bankName;
    /**
     * The bank account
     */
    @Column(name = "bank_account_number")
    @ApiModelProperty(name = "bank_account_number", value = "The bank account", required = false)
    private String bankAccountNumber;
    /**
     * Bank account name
     */
    @Column(name = "bank_account_name")
    @ApiModelProperty(name = "bank_account_name", value = "Bank account name", required = false)
    private String bankAccountName;
    /**
     * Bank opening bank
     */
    @Column(name = "bank_deposit_name")
    @ApiModelProperty(name = "bank_deposit_name", value = "Bank opening bank", required = false)
    private String bankDepositName;
    /**
     * Transaction number
     */
    @Column(name = "trade_sn")
    @ApiModelProperty(name = "trade_sn", value = "Transaction number", required = false)
    private String tradeSn;
    /**
     * After type(Cancel the order,Apply for after sales)
     */
    @Column(name = "refund_type")
    @ApiModelProperty(name = "refund_type", value = "After type(Cancel the order,Apply for after sales)", required = false)
    private String refundType;
    /**
     * Order type(Online payment,Cash on delivery)
     */
    @Column(name = "payment_type")
    @ApiModelProperty(name = "payment_type", value = "Order type(Online payment,Cash on delivery)", required = false)
    private String paymentType;
    /**
     * refund(cargo)New type(refundcargo，refund款)
     */
    @Column(name = "refuse_type")
    @ApiModelProperty(name = "refuse_type", value = "refund(cargo)New type(refundcargo，refund款)", required = false, hidden = true)
    private String refuseType;
    /**
     * Payment result transaction number
     */
    @Column(name = "pay_order_no")
    @ApiModelProperty(name = "pay_order_no", value = "Payment result transaction number", required = false)
    private String payOrderNo;

    @Column(name = "refund_fail_reason")
    @ApiModelProperty(name = "refund_fail_reason", value = "Reasons for refund Failure", required = false)
    private String refundFailReason;

    @Column(name = "refund_time")
    @ApiModelProperty(name = "refund_time", value = "A refund of time", hidden = true)
    private Long refundTime;
    /**
     * The note of the service
     */
    @Column(name = "seller_remark")
    @ApiModelProperty(name = "seller_remark", value = "The note of the service", required = false)
    private String sellerRemark;

    /**
     * Merchandise information
     */
    @Column(name = "refund_gift")
    @ApiModelProperty(name = "refund_gift", value = "Merchandise information", required = false)
    private String refundGift;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public Integer getRefundPoint() {
        return refundPoint;
    }

    public void setRefundPoint(Integer refundPoint) {
        this.refundPoint = refundPoint;
    }

    public String getRefundWay() {
        return refundWay;
    }

    public void setRefundWay(String refundWay) {
        this.refundWay = refundWay;
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

    public String getSellerRemark() {
        return sellerRemark;
    }

    public void setSellerRemark(String sellerRemark) {
        this.sellerRemark = sellerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }

    public String getWarehouseRemark() {
        return warehouseRemark;
    }

    public void setWarehouseRemark(String warehouseRemark) {
        this.warehouseRemark = warehouseRemark;
    }

    public String getFinanceRemark() {
        return financeRemark;
    }

    public void setFinanceRemark(String financeRemark) {
        this.financeRemark = financeRemark;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
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

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getRefuseType() {
        return refuseType;
    }

    public void setRefuseType(String refuseType) {
        this.refuseType = refuseType;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }


    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRefundFailReason() {
        return refundFailReason;
    }

    public void setRefundFailReason(String refundFailReason) {
        this.refundFailReason = refundFailReason;
    }

    public Long getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Long refundTime) {
        this.refundTime = refundTime;
    }

    public String getRefundGift() {
        return refundGift;
    }

    public void setRefundGift(String refundGift) {
        this.refundGift = refundGift;
    }

    @Override
    public String toString() {
        return "RefundDO{" +
                "id=" + id +
                ", sn='" + sn + '\'' +
                ", memberId=" + memberId +
                ", orderSn='" + orderSn + '\'' +
                ", refundStatus='" + refundStatus + '\'' +
                ", createTime=" + createTime +
                ", refundPrice=" + refundPrice +
                ", refundPoint=" + refundPoint +
                ", refundWay='" + refundWay + '\'' +
                ", accountType='" + accountType + '\'' +
                ", returnAccount='" + returnAccount + '\'' +
                ", customerRemark='" + customerRemark + '\'' +
                ", warehouseRemark='" + warehouseRemark + '\'' +
                ", financeRemark='" + financeRemark + '\'' +
                ", refundReason='" + refundReason + '\'' +
                ", refuseReason='" + refuseReason + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", bankAccountName='" + bankAccountName + '\'' +
                ", bankDepositName='" + bankDepositName + '\'' +
                ", sellerRemark='" + sellerRemark + '\'' +
                ", tradeSn='" + tradeSn + '\'' +
                ", refundType='" + refundType + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", refuseType='" + refuseType + '\'' +
                ", payOrderNo='" + payOrderNo + '\'' +
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

        RefundDO refundDO = (RefundDO) o;

        if (!memberId.equals(refundDO.memberId)) {
            return false;
        }
        if (!orderSn.equals(refundDO.orderSn)) {
            return false;
        }
        if (!refundStatus.equals(refundDO.refundStatus)) {
            return false;
        }
        if (!refundPrice.equals(refundDO.refundPrice)) {
            return false;
        }
        if (!refundReason.equals(refundDO.refundReason)) {
            return false;
        }
        if (!tradeSn.equals(refundDO.tradeSn)) {
            return false;
        }
        if (!refundType.equals(refundDO.refundType)) {
            return false;
        }
        if (!refuseType.equals(refundDO.refuseType)) {
            return false;
        }
        return payOrderNo.equals(refundDO.payOrderNo);
    }

    @Override
    public int hashCode() {
        int result = memberId.hashCode();
        result = 31 * result + orderSn.hashCode();
        result = 31 * result + refundStatus.hashCode();
        result = 31 * result + refundPrice.hashCode();
        result = 31 * result + refundReason.hashCode();
        result = 31 * result + tradeSn.hashCode();
        result = 31 * result + refundType.hashCode();
        result = 31 * result + refuseType.hashCode();
        result = 31 * result + payOrderNo.hashCode();
        return result;
    }
}
