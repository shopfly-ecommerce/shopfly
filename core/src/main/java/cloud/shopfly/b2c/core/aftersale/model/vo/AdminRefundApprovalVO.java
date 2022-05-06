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
 * 管理员审核退(款)货VO
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 上午11:21 2018/5/2
 */
public class AdminRefundApprovalVO implements Serializable {

    @ApiModelProperty(value = "退款单号", name = "sn", required = true)
    @NotBlank(message = "退款单号必填")
    private String sn;

    @ApiModelProperty(value = "是否同意退款:同意 1，不同意 0", allowableValues = "1,0", required = true)
    @NotNull(message = "是否同意必填")
    private Integer agree;

    @ApiModelProperty(value = "退款金额", name = "refund_price", required = true)
    @NotNull(message = "退款金额必填")
    @Min(value = 0, message = "退款金额不能为负数")
    private Double refundPrice;

    @ApiModelProperty(value = "退款备注", name = "remark", required = false)
    private String remark;
    @ApiModelProperty(value = "退还积分", name = "refund_point", required = false)
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
