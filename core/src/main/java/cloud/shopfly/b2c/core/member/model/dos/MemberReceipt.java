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
package cloud.shopfly.b2c.core.member.model.dos;

import cloud.shopfly.b2c.core.member.model.enums.ReceiptTypeEnum;
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
 * Member invoice entity
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-21 14:42:25
 */
@Table(name = "es_member_receipt")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberReceipt implements Serializable {

    private static final long serialVersionUID = 4743090485786622L;

    /**
     * Member of the invoiceid
     */
    @Id(name = "receipt_id")
    @ApiModelProperty(hidden = true)
    private Integer receiptId;
    /**
     * membersid
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "membersid", required = false)
    private Integer memberId;
    /**
     * Invoice type
     */
    @Column(name = "receipt_type")
    @ApiModelProperty(name = "receipt_type", value = "Invoice type", required = false)
    private String receiptType;
    /**
     * The invoice looked up
     */
    @Column(name = "receipt_title")
    @ApiModelProperty(name = "receipt_title", value = "The invoice looked up", required = false)
    private String receiptTitle;
    /**
     * The invoice content
     */
    @Column(name = "receipt_content")
    @ApiModelProperty(name = "receipt_content", value = "The invoice content", required = false)
    private String receiptContent;
    /**
     * The invoice id number
     */
    @Column(name = "tax_no")
    @ApiModelProperty(name = "tax_no", value = "The invoice id number", required = false)
    private String taxNo;
    /**
     * The registered address
     */
    @Column(name = "reg_addr")
    @ApiModelProperty(name = "reg_addr", value = "The registered address", required = false)
    private String regAddr;
    /**
     * Registered telephone
     */
    @Column(name = "reg_tel")
    @ApiModelProperty(name = "reg_tel", value = "Registered telephone", required = false)
    private String regTel;
    /**
     * bank
     */
    @Column(name = "bank_name")
    @ApiModelProperty(name = "bank_name", value = "bank", required = false)
    private String bankName;
    /**
     * The bank account
     */
    @Column(name = "bank_account")
    @ApiModelProperty(name = "bank_account", value = "The bank account", required = false)
    private String bankAccount;
    /**
     * Default or not
     */
    @Column(name = "is_default")
    @ApiModelProperty(name = "is_default", value = "Default or not", required = false)
    private Integer isDefault;

    @PrimaryKeyField
    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getReceiptType() {
        if (ReceiptTypeEnum.ELECTRO.name().equals(receiptType)) {
            return "Electronic invoice";
        }
        if (ReceiptTypeEnum.VATORDINARY.name().equals(receiptType)) {
            return "VAT general invoice";
        }
        if (ReceiptTypeEnum.VATOSPECIAL.name().equals(receiptType)) {
            return "VAT special invoice";
        }
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getReceiptTitle() {
        return receiptTitle;
    }

    public void setReceiptTitle(String receiptTitle) {
        this.receiptTitle = receiptTitle;
    }

    public String getReceiptContent() {
        return receiptContent;
    }

    public void setReceiptContent(String receiptContent) {
        this.receiptContent = receiptContent;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr;
    }

    public String getRegTel() {
        return regTel;
    }

    public void setRegTel(String regTel) {
        this.regTel = regTel;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberReceipt that = (MemberReceipt) o;
        if (receiptId != null ? !receiptId.equals(that.receiptId) : that.receiptId != null) {
            return false;
        }
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) {
            return false;
        }
        if (receiptType != null ? !receiptType.equals(that.receiptType) : that.receiptType != null) {
            return false;
        }
        if (receiptTitle != null ? !receiptTitle.equals(that.receiptTitle) : that.receiptTitle != null) {
            return false;
        }
        if (receiptContent != null ? !receiptContent.equals(that.receiptContent) : that.receiptContent != null) {
            return false;
        }
        if (taxNo != null ? !taxNo.equals(that.taxNo) : that.taxNo != null) {
            return false;
        }
        if (regAddr != null ? !regAddr.equals(that.regAddr) : that.regAddr != null) {
            return false;
        }
        if (regTel != null ? !regTel.equals(that.regTel) : that.regTel != null) {
            return false;
        }
        if (bankName != null ? !bankName.equals(that.bankName) : that.bankName != null) {
            return false;
        }
        if (bankAccount != null ? !bankAccount.equals(that.bankAccount) : that.bankAccount != null) {
            return false;
        }
        return isDefault != null ? isDefault.equals(that.isDefault) : that.isDefault == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (receiptId != null ? receiptId.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (receiptType != null ? receiptType.hashCode() : 0);
        result = 31 * result + (receiptTitle != null ? receiptTitle.hashCode() : 0);
        result = 31 * result + (receiptContent != null ? receiptContent.hashCode() : 0);
        result = 31 * result + (taxNo != null ? taxNo.hashCode() : 0);
        result = 31 * result + (regAddr != null ? regAddr.hashCode() : 0);
        result = 31 * result + (regTel != null ? regTel.hashCode() : 0);
        result = 31 * result + (bankName != null ? bankName.hashCode() : 0);
        result = 31 * result + (bankAccount != null ? bankAccount.hashCode() : 0);
        result = 31 * result + (isDefault != null ? isDefault.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MemberReceipt{" +
                "receiptId=" + receiptId +
                ", memberId=" + memberId +
                ", receiptType='" + receiptType + '\'' +
                ", receiptTitle='" + receiptTitle + '\'' +
                ", receiptContent='" + receiptContent + '\'' +
                ", taxNo='" + taxNo + '\'' +
                ", regAddr='" + regAddr + '\'' +
                ", regTel='" + regTel + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }


}
