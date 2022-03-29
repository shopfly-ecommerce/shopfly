/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.model.vo;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 确认订单操作
 *
 * @author Snow create in 2018/5/18
 * @version v2.0
 * @since v7.0.0
 */
public class ConfirmVO {

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "是否同意确认此订单")
    private Boolean agree;

    @ApiModelProperty(value = "操作者")
    private String operator;


    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Boolean getAgree() {
        return agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "ConfirmVO{" +
                "orderSn='" + orderSn + '\'' +
                ", agree=" + agree +
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

        ConfirmVO confirmVO = (ConfirmVO) o;

        return new EqualsBuilder()
                .append(orderSn, confirmVO.orderSn)
                .append(agree, confirmVO.agree)
                .append(operator, confirmVO.operator)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(orderSn)
                .append(agree)
                .append(operator)
                .toHashCode();
    }
}
