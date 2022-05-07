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
package cloud.shopfly.b2c.core.member.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * Member invoice entityVO
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
     * Invoice type
     */
    @ApiModelProperty(name = "receipt_type", value = "Enumeration,ELECTRO:Electronic general invoice,VATORDINARY：VAT general invoice,VATOSPECIAL：VAT special invoice", required = false, hidden = true)
    private String receiptType;
    /**
     * The invoice looked up
     */
    @ApiModelProperty(name = "receipt_title", value = "The invoice looked up", required = false)
    private String receiptTitle;
    /**
     * The invoice content
     */
    @ApiModelProperty(name = "receipt_content", value = "The invoice content", required = false)
    private String receiptContent;
    /**
     * The invoice id number
     */
    @ApiModelProperty(name = "tax_no", value = "The invoice id number", required = false)
    private String taxNo;

    /**
     * VAT ordinary invoice value assignment
     *
     * @param ordinaryReceiptVO VAT general invoice
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
