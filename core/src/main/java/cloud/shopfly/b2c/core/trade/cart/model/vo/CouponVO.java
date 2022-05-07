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
package cloud.shopfly.b2c.core.trade.cart.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * coupons
 *
 * @author kingapex
 * @version v2.0
 * @since v7.0.0
 * 2017years3month22On the afternoon1:03:52
 */
@ApiModel(description = "coupons")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CouponVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5236361119763282449L;

    /**
     * Record the membership coupons used when placing the orderID
     */
    @ApiModelProperty(value = "Membership couponid")
    private Integer memberCouponId;

    /**
     * Keep track of coupons given when you place an orderID
     */
    @ApiModelProperty(value = "couponsid")
    private Integer couponId;

    @ApiModelProperty(value = "The amount of")
    private Double amount;

    @ApiModelProperty(value = "The period of validity")
    private Long endTime;

    @ApiModelProperty(value = "Conditions of use")
    private String useTerm;


    @ApiModelProperty(value = "Coupon threshold amount")
    private Double couponThresholdPrice;

    @ApiModelProperty(value = "Whether it is available,1As the available,0For is not available")
    private int enable;

    @ApiModelProperty(value = "Whether or not they are selected,1For the selected,0Is not selected")
    private int selected;


    @ApiModelProperty(name = "error_msg", value = "Error message used on settlement page")
    private String errorMsg;

    public Integer getMemberCouponId() {
        return memberCouponId;
    }

    public void setMemberCouponId(Integer memberCouponId) {
        this.memberCouponId = memberCouponId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getUseTerm() {
        return useTerm;
    }

    public void setUseTerm(String useTerm) {
        this.useTerm = useTerm;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }


    public Double getCouponThresholdPrice() {
        return couponThresholdPrice;
    }

    public void setCouponThresholdPrice(Double couponThresholdPrice) {
        this.couponThresholdPrice = couponThresholdPrice;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "CouponVO{" +
                "memberCouponId=" + memberCouponId +
                ", couponId=" + couponId +
                ", amount=" + amount +
                ", endTime=" + endTime +
                ", useTerm='" + useTerm + '\'' +
                ", couponThresholdPrice=" + couponThresholdPrice +
                ", enable=" + enable +
                ", selected=" + selected +
                ", errorMsg='" + errorMsg + '\'' +
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
        CouponVO couponVO = (CouponVO) o;

        return new EqualsBuilder()
                .append(enable, couponVO.enable)
                .append(selected, couponVO.selected)
                .append(memberCouponId, couponVO.memberCouponId)
                .append(couponId, couponVO.couponId)
                .append(amount, couponVO.amount)
                .append(endTime, couponVO.endTime)
                .append(useTerm, couponVO.useTerm)
                .append(couponThresholdPrice, couponVO.couponThresholdPrice)
                .append(errorMsg, couponVO.errorMsg)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(memberCouponId)
                .append(couponId)
                .append(amount)
                .append(endTime)
                .append(useTerm)
                .append(couponThresholdPrice)
                .append(enable)
                .append(selected)
                .append(errorMsg)
                .toHashCode();
    }

}
