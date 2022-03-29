/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 订单取消
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */

@ApiModel(description = "订单取消")
public class CancelVO {


    @ApiModelProperty(value = "订单编号")
    private String orderSn;


    @ApiModelProperty(value = "取消原因")
    private String reason;


    @ApiModelProperty(hidden = true, value = "操作人")
    private String operator;


    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "CancelVO{" +
                "orderSn='" + orderSn + '\'' +
                ", reason='" + reason + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CancelVO cancelVO = (CancelVO) o;

        return new EqualsBuilder()
                .append(orderSn, cancelVO.orderSn)
                .append(reason, cancelVO.reason)
                .append(operator, cancelVO.operator)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(orderSn)
                .append(reason)
                .append(operator)
                .toHashCode();
    }
}
