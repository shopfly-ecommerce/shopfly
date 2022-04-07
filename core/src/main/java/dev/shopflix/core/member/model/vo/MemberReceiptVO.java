/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 会员发票实体VO
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-20 20:48:13
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberReceiptVO implements Serializable {

    private static final long serialVersionUID = 8050258384276672902L;
    /**
     * 发票类型
     */
    @ApiModelProperty(name = "receipt_type", value = "枚举，ELECTRO:电子普通发票，VATORDINARY：增值税普通发票，VATOSPECIAL：增值税专用发票", required = false, hidden = true)
    private String receiptType;
    /**
     * 发票抬头
     */
    @ApiModelProperty(name = "receipt_title", value = "发票抬头", required = false)
    private String receiptTitle;
    /**
     * 发票内容
     */
    @ApiModelProperty(name = "receipt_content", value = "发票内容", required = false)
    private String receiptContent;
    /**
     * 发票税号
     */
    @ApiModelProperty(name = "tax_no", value = "发票税号", required = false)
    private String taxNo;

    /**
     * 增值税普通发票赋值
     *
     * @param ordinaryReceiptVO 增值税普通发票
     */
    public MemberReceiptVO(OrdinaryReceiptVO ordinaryReceiptVO) {
        this.receiptType = ordinaryReceiptVO.getReceiptType();
        this.receiptContent = ordinaryReceiptVO.getReceiptContent();
        this.receiptTitle = ordinaryReceiptVO.getReceiptTitle();
        this.taxNo = ordinaryReceiptVO.getTaxNo();
    }

    public MemberReceiptVO() {

    }


    public String getReceiptType() {
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


    @Override
    public String toString() {
        return "MemberReceiptVO{" +
                "receiptType='" + receiptType + '\'' +
                ", receiptTitle='" + receiptTitle + '\'' +
                ", receiptContent='" + receiptContent + '\'' +
                ", taxNo='" + taxNo + '\'' +
                '}';
    }
}