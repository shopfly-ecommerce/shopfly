/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.model.dos;

import cloud.shopfly.b2c.core.member.model.enums.ReceiptTypeEnum;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 发票历史实体
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-20 20:48:09
 */
@Table(name = "es_receipt_history")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReceiptHistory implements Serializable {

    private static final long serialVersionUID = 7024661438767317L;

    /**
     * 发票历史idreceipt_amount
     */
    @Id(name = "history_id")
    @ApiModelProperty(hidden = true)
    private Integer historyId;
    /**
     * 订单编号
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "订单编号", required = false)
    private String orderSn;
    /**
     * 会员id
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "会员id", required = false)
    private Integer memberId;
    /**
     * 发票类型
     */
    @Column(name = "receipt_type")
    @ApiModelProperty(name = "receipt_type", value = "发票类型", required = false)
    private String receiptType;
    /**
     * 发票抬头
     */
    @Column(name = "receipt_title")
    @ApiModelProperty(name = "receipt_title", value = "发票抬头", required = false)
    private String receiptTitle;
    /**
     * 发票金额
     */
    @Column(name = "receipt_amount")
    @ApiModelProperty(name = "receipt_amount", value = "发票金额", required = false)
    private Double receiptAmount;
    /**
     * 发票内容
     */
    @Column(name = "receipt_content")
    @ApiModelProperty(name = "receipt_content", value = "发票内容", required = false)
    private String receiptContent;
    /**
     * 税号
     */
    @Column(name = "tax_no")
    @ApiModelProperty(name = "tax_no", value = "税号", required = false)
    private String taxNo;
    /**
     * 注册地址
     */
    @Column(name = "reg_addr")
    @ApiModelProperty(name = "reg_addr", value = "注册地址", required = false)
    private String regAddr;
    /**
     * 注册电话
     */
    @Column(name = "reg_tel")
    @ApiModelProperty(name = "reg_tel", value = "注册电话", required = false)
    private String regTel;
    /**
     * 开户银行
     */
    @Column(name = "bank_name")
    @ApiModelProperty(name = "bank_name", value = "开户银行", required = false)
    private String bankName;
    /**
     * 银行账户
     */
    @Column(name = "bank_account")
    @ApiModelProperty(name = "bank_account", value = "银行账户", required = false)
    private String bankAccount;
    /**
     * 开票时间
     */
    @Column(name = "add_time")
    @ApiModelProperty(name = "add_time", value = "开票时间", required = false)
    private Long addTime;
    /**
     * 会员名称
     */
    @Column(name = "member_name")
    @ApiModelProperty(name = "member_name", value = "会员名称", required = false)
    private String memberName;

    @PrimaryKeyField
    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getReceiptType() {
        if (!StringUtil.isEmpty(receiptType)) {
            if (receiptType.equals(ReceiptTypeEnum.VATORDINARY.name())) {
                return "增值税普通发票";
            }
            if (receiptType.equals(ReceiptTypeEnum.VATOSPECIAL.name())) {
                return "增值税专用发票";
            }
            if (receiptType.equals(ReceiptTypeEnum.ELECTRO.name())) {
                return "电子发票";
            }
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

    public Double getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Double receiptAmount) {
        this.receiptAmount = receiptAmount;
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

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptHistory that = (ReceiptHistory) o;
        if (historyId != null ? !historyId.equals(that.historyId) : that.historyId != null) {
            return false;
        }
        if (orderSn != null ? !orderSn.equals(that.orderSn) : that.orderSn != null) {
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
        if (receiptAmount != null ? !receiptAmount.equals(that.receiptAmount) : that.receiptAmount != null) {
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
        if (addTime != null ? !addTime.equals(that.addTime) : that.addTime != null) {
            return false;
        }
        return memberName != null ? memberName.equals(that.memberName) : that.memberName == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (historyId != null ? historyId.hashCode() : 0);
        result = 31 * result + (orderSn != null ? orderSn.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (receiptType != null ? receiptType.hashCode() : 0);
        result = 31 * result + (receiptTitle != null ? receiptTitle.hashCode() : 0);
        result = 31 * result + (receiptAmount != null ? receiptAmount.hashCode() : 0);
        result = 31 * result + (receiptContent != null ? receiptContent.hashCode() : 0);
        result = 31 * result + (taxNo != null ? taxNo.hashCode() : 0);
        result = 31 * result + (regAddr != null ? regAddr.hashCode() : 0);
        result = 31 * result + (regTel != null ? regTel.hashCode() : 0);
        result = 31 * result + (bankName != null ? bankName.hashCode() : 0);
        result = 31 * result + (bankAccount != null ? bankAccount.hashCode() : 0);
        result = 31 * result + (addTime != null ? addTime.hashCode() : 0);
        result = 31 * result + (memberName != null ? memberName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReceiptHistory{" +
                "historyId=" + historyId +
                ", orderSn='" + orderSn + '\'' +
                ", memberId=" + memberId +
                ", receiptType='" + receiptType + '\'' +
                ", receiptTitle='" + receiptTitle + '\'' +
                ", receiptAmount=" + receiptAmount +
                ", receiptContent='" + receiptContent + '\'' +
                ", taxNo='" + taxNo + '\'' +
                ", regAddr='" + regAddr + '\'' +
                ", regTel='" + regTel + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", addTime=" + addTime +
                ", memberName='" + memberName + '\'' +
                '}';
    }

}