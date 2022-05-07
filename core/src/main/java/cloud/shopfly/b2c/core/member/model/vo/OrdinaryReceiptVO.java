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

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * Object of general invoice
 *
 * @author zh
 * @version v7.0
 * @date 18/7/23 In the morning9:52
 * @since v7.0
 */
public class OrdinaryReceiptVO {

    /**
     * Invoice type
     */
    @ApiModelProperty(name = "receipt_type", value = "Enumeration,ELECTRO:Electronic general invoice,VATORDINARY：VAT general invoice,VATOSPECIAL：VAT special invoice", required = false, hidden = true)
    private String receiptType;
    /**
     * The invoice looked up
     */
    @NotEmpty(message = "Invoice title must not be blank")
    @ApiModelProperty(name = "receipt_title", value = "The invoice looked up", required = true)
    private String receiptTitle;
    /**
     * The invoice content
     */
    @NotEmpty(message = "The invoice cannot be blank")
    @ApiModelProperty(name = "receipt_content", value = "The invoice content", required = true)
    private String receiptContent;
    /**
     * The invoice id number
     */
    @ApiModelProperty(name = "tax_no", value = "The invoice id number", required = true)
    private String taxNo;

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
        return "OrdinaryReceiptVO{" +
                "receiptType='" + receiptType + '\'' +
                ", receiptTitle='" + receiptTitle + '\'' +
                ", receiptContent='" + receiptContent + '\'' +
                ", taxNo='" + taxNo + '\'' +
                '}';
    }
}
