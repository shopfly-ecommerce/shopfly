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

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Administrator audit retreat(paragraph)cargoVO
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 In the morning11:21 2018/5/2
 */
public class AdminRefundApprovalVO implements Serializable {

    @ApiModelProperty(value = "The refund number", name = "sn", required = true)
    @NotBlank(message = "Refund receipt number Mandatory")
    private String sn;

    @ApiModelProperty(value = "Do you agree to refund?:agree1，不agree0", allowableValues = "1,0", required = true)
    @NotNull(message = "This parameter is mandatory")
    private Integer agree;

    @ApiModelProperty(value = "The refund amount", name = "refund_price", required = true)
    @NotNull(message = "Refund amount mandatory")
    @Min(value = 0, message = "The refund amount cannot be negative")
    private Double refundPrice;

    @ApiModelProperty(value = "The refund note", name = "remark", required = false)
    private String remark;
    @ApiModelProperty(value = "Return the integral", name = "refund_point", required = false)
    private Integer refundPoint;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getAgree() {
        return agree;
    }

    public void setAgree(Integer agree) {
        this.agree = agree;
    }

    public Double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRefundPoint() {
        return refundPoint;
    }

    public void setRefundPoint(Integer refundPoint) {
        this.refundPoint = refundPoint;
    }

    @Override
    public String toString() {
        return "AdminRefundApprovalVO{" +
                "sn='" + sn + '\'' +
                ", agree=" + agree +
                ", refundPrice=" + refundPrice +
                ", remark='" + remark + '\'' +
                ", refundPoint=" + refundPoint +
                '}';
    }
}
