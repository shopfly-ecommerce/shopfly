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
 * Bills of lading
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel(description = "Bills of lading")
public class DeliveryVO {

    @ApiModelProperty(value = "Order no.")
    private String orderSn;

    @ApiModelProperty(value = "Bills of lading no.")
    private String deliveryNo;

    @ApiModelProperty(value = "Logistics company")
    private Integer logiId;

    @ApiModelProperty(value = "Name of logistics Company")
    private String logiName;

    @ApiModelProperty(hidden = true)
    private String operator;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public Integer getLogiId() {
        return logiId;
    }

    public void setLogiId(Integer logiId) {
        this.logiId = logiId;
    }

    public String getLogiName() {
        return logiName;
    }

    public void setLogiName(String logiName) {
        this.logiName = logiName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "DeliveryVO{" +
                "orderSn='" + orderSn + '\'' +
                ", deliveryNo='" + deliveryNo + '\'' +
                ", logiId=" + logiId +
                ", logiName='" + logiName + '\'' +
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

        DeliveryVO that = (DeliveryVO) o;

        return new EqualsBuilder()
                .append(orderSn, that.orderSn)
                .append(deliveryNo, that.deliveryNo)
                .append(logiId, that.logiId)
                .append(logiName, that.logiName)
                .append(operator, that.operator)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(orderSn)
                .append(deliveryNo)
                .append(logiId)
                .append(logiName)
                .append(operator)
                .toHashCode();
    }
}
