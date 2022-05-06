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
package cloud.shopfly.b2c.core.trade.order.model.vo;

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
