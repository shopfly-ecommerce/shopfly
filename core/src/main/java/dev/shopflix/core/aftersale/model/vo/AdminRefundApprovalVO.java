/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.aftersale.model.vo;

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
