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
 * 普通发票对象
 *
 * @author zh
 * @version v7.0
 * @date 18/7/23 上午9:52
 * @since v7.0
 */
public class OrdinaryReceiptVO {

    /**
     * 发票类型
     */
    @ApiModelProperty(name = "receipt_type", value = "枚举，ELECTRO:电子普通发票，VATORDINARY：增值税普通发票，VATOSPECIAL：增值税专用发票", required = false, hidden = true)
    private String receiptType;
    /**
     * 发票抬头
     */
    @NotEmpty(message = "发票抬头不能为空")
    @ApiModelProperty(name = "receipt_title", value = "发票抬头", required = true)
    private String receiptTitle;
    /**
     * 发票内容
     */
    @NotEmpty(message = "发票内容不能为空")
    @ApiModelProperty(name = "receipt_content", value = "发票内容", required = true)
    private String receiptContent;
    /**
     * 发票税号
     */
    @ApiModelProperty(name = "tax_no", value = "发票税号", required = true)
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
