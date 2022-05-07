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
package cloud.shopfly.b2c.core.promotion.pintuan.model;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * Group sub order entity
 *
 * @author admin
 * @version vv1.0.0
 * @since vv7.1.0
 * 2019-01-25 15:13:30
 */
@Table(name = "es_pintuan_child_order")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PintuanChildOrder implements Serializable {

    private static final long serialVersionUID = 2898134410030384L;

    /**
     * The child ordersid
     */
    @Id(name = "child_order_id")
    @ApiModelProperty(hidden = true)
    private Integer childOrderId;
    /**
     * Order no.
     */
    @Column(name = "order_sn")
    @NotEmpty(message = "The order number cannot be blank")
    @ApiModelProperty(name = "order_sn", value = "Order no.", required = true)
    private String orderSn;
    /**
     * membersid
     */
    @Column(name = "member_id")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "member_id", value = "membersid", required = false)
    private Integer memberId;
    /**
     * membersid
     */
    @Column(name = "sku_id")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "sku_id", value = "membersid", required = false)
    private Integer skuId;
    /**
     * Spell group activitiesid
     */
    @Column(name = "pintuan_id")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "pintuan_id", value = "Spell group activitiesid", required = false)
    private Integer pintuanId;
    /**
     * Status
     * wait New orders are waiting for payment
     * pay_off Payment has been made waiting for the group
     * formed clouds
     */
    @Column(name = "order_status")
    @ApiModelProperty(name = "order_status", value = "Status", required = false)
    private String orderStatus;
    /**
     * The main orderid
     */
    @Column(name = "order_id")
    @ApiModelProperty(name = "order_id", value = "The main orderid", required = false)
    private Integer orderId;
    /**
     * Name of the buyer
     */
    @Column(name = "member_name")
    @ApiModelProperty(name = "member_name", value = "Name of the buyer", required = false)
    private String memberName;

    @Column(name = "origin_price")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "origin_price", value = "The original price", required = false)
    private Double originPrice;
    /**
     * Spell group price
     */
    @Column(name = "sales_price")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "sales_price", value = "Spell group price", required = false)
    private Double salesPrice;


    @PrimaryKeyField
    public Integer getChildOrderId() {
        return childOrderId;
    }

    public void setChildOrderId(Integer childOrderId) {
        this.childOrderId = childOrderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getPintuanId() {
        return pintuanId;
    }

    public void setPintuanId(Integer pintuanId) {
        this.pintuanId = pintuanId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice) {
        this.originPrice = originPrice;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PintuanChildOrder that = (PintuanChildOrder) o;

        return new EqualsBuilder()
                .append(getChildOrderId(), that.getChildOrderId())
                .append(getOrderSn(), that.getOrderSn())
                .append(getMemberId(), that.getMemberId())
                .append(getSkuId(), that.getSkuId())
                .append(getPintuanId(), that.getPintuanId())
                .append(getOrderStatus(), that.getOrderStatus())
                .append(getOrderId(), that.getOrderId())
                .append(getMemberName(), that.getMemberName())
                .append(getOriginPrice(), that.getOriginPrice())
                .append(getSalesPrice(), that.getSalesPrice())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getChildOrderId())
                .append(getOrderSn())
                .append(getMemberId())
                .append(getSkuId())
                .append(getPintuanId())
                .append(getOrderStatus())
                .append(getOrderId())
                .append(getMemberName())
                .append(getOriginPrice())
                .append(getSalesPrice())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "PintuanChildOrder{" +
                "childOrderId=" + childOrderId +
                ", orderSn='" + orderSn + '\'' +
                ", memberId=" + memberId +
                ", skuId=" + skuId +
                ", pintuanId=" + pintuanId +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderId=" + orderId +
                ", memberName='" + memberName + '\'' +
                ", originPrice=" + originPrice +
                ", salesPrice=" + salesPrice +
                '}';
    }


}
