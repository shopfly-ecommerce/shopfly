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
import javax.validation.constraints.NotNull;

/**
 * 退款VO
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 下午2:15 2018/5/2
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FinanceRefundApprovalVO {

    @ApiModelProperty(value = "退货(款)单编号", name = "sn", required = true)
    @NotBlank(message = "退款单号必填")
    private String sn;

    @ApiModelProperty(value = "退款金额", name = "refund_price", required = true)
    @NotNull(message = "退款金额必填")
    private Double refundPrice;

    @ApiModelProperty(value = "退款备注", name = "remark", required = false)
    private String remark;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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

    @Override
    public String toString() {
        return "FinanceRefundApprovalVO{" +
                "sn='" + sn + '\'' +
                ", refundPrice=" + refundPrice +
                ", remark='" + remark + '\'' +
                '}';
    }
}
