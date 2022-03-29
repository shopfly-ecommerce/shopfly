/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.aftersale.model.vo;

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
