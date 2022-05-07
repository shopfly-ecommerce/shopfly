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
package cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos;

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

import javax.validation.constraints.*;
import java.io.Serializable;


/**
 * Full preferential activity entities<br/>
 * default1For the selected,0For not choosing
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 10:42:06
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
@Table(name = "es_full_discount")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FullDiscountDO implements Serializable {

    private static final long serialVersionUID = 5679579627685903L;

    /**
     * activityid
     */
    @Id(name = "fd_id")
    @ApiModelProperty(hidden = true)
    private Integer fdId;

    /**
     * Preferential threshold amount
     */
    @Column(name = "full_money")
    @NotNull(message = "Please fill in the discount threshold")
    @DecimalMax(value = "99999999.00", message = "The coupon threshold exceeds the limit")
    @ApiModelProperty(name = "full_money", value = "Preferential threshold amount", required = true)
    private Double fullMoney;

    /**
     * Whether the activity is less cash
     */
    @Column(name = "is_full_minus")
    @Min(value = 0, message = "The cash reduction parameter is wrong")
    @Max(value = 1, message = "The cash reduction parameter is wrong")
    @ApiModelProperty(name = "is_full_minus", value = "Whether the activity is less cash", required = false, example = "0For no,1As a")
    private Integer isFullMinus;

    /**
     * Decrease in cash
     */
    @Column(name = "minus_value")
    @ApiModelProperty(name = "minus_value", value = "Decrease in cash", required = false)
    private Double minusValue;

    /**
     * Whether the discount
     */
    @Column(name = "is_discount")
    @Min(value = 0, message = "Wrong discount parameter")
    @Max(value = 1, message = "Wrong discount parameter")
    @ApiModelProperty(name = "is_discount", value = "Whether the discount", required = false, example = "0For no,1As a")
    private Integer isDiscount;

    /**
     * How much discount
     */
    @Column(name = "discount_value")
    @ApiModelProperty(name = "discount_value", value = "How much discount", required = false)
    private Double discountValue;


    /**
     * Whether the present
     */
    @Column(name = "is_send_point")
    @Min(value = 0, message = "Incorrect bonus points")
    @Max(value = 1, message = "Incorrect bonus points")
    @ApiModelProperty(name = "is_send_point", value = "Whether to give bonus points", required = false, example = "0For no,1As a")
    private Integer isSendPoint;

    /**
     * Integral data
     */
    @Column(name = "point_value")
    @ApiModelProperty(name = "point_value", value = "How many bonus points", required = false)
    private Integer pointValue;

    /**
     * Whether free mail
     */
    @Column(name = "is_free_ship")
    @Min(value = 0, message = "Incorrect postage free parameters")
    @Max(value = 1, message = "Incorrect postage free parameters")
    @ApiModelProperty(name = "is_free_ship", value = "Whether the postage", required = false, example = "0For no,1As a")
    private Integer isFreeShip;

    /**
     * Are there any freebies
     */
    @Column(name = "is_send_gift")
    @Min(value = 0, message = "Gift parameters are wrong")
    @Max(value = 1, message = "Gift parameters are wrong")
    @ApiModelProperty(name = "is_send_gift", value = "Are there any freebies", required = false, example = "0For no,1As a")
    private Integer isSendGift;

    /**
     * The giftsid
     */
    @Column(name = "gift_id")
    @ApiModelProperty(name = "gift_id", value = "The giftsid", required = false)
    private Integer giftId;


    /**
     * Do you give coupons?
     */
    @Column(name = "is_send_bonus")
    @Min(value = 0, message = "Incorrect parameters for sending coupons")
    @Max(value = 1, message = "Incorrect parameters for sending coupons")
    @ApiModelProperty(name = "is_send_bonus", value = "Whether to add coupons", required = false, example = "0For no,1As a")
    private Integer isSendBonus;

    /**
     * couponsid
     */
    @Column(name = "bonus_id")
    @ApiModelProperty(name = "bonus_id", value = "couponsid", required = false)
    private Integer bonusId;

    /**
     * Activity start time
     */
    @Column(name = "start_time")
    @NotNull(message = "Please fill in the start time of the activity")
    @ApiModelProperty(name = "start_time", value = "Activity start time", required = true)
    private Long startTime;

    /**
     * End time
     */
    @Column(name = "end_time")
    @NotNull(message = "Please fill in the deadline")
    @ApiModelProperty(name = "end_time", value = "Activity deadline", required = true)
    private Long endTime;

    /**
     * Activity title
     */
    @Column(name = "title")
    @NotEmpty(message = "Please fill in the activity title")
    @ApiModelProperty(name = "title", value = "Activity title", required = true)
    private String title;

    /**
     * Mode of Commodity participation
     */
    @Column(name = "range_type")
    @NotNull(message = "Please select all commodities to participate in the way：1, some commodities：2")
    @Min(value = 1, message = "Item participation mode value is incorrect")
    @Max(value = 2, message = "Item participation mode value is incorrect")
    @ApiModelProperty(name = "range_type", value = "Mode of Commodity participation", required = true)
    private Integer rangeType;

    /**
     * Whether discontinuation
     */
    @Column(name = "disabled")
    @ApiModelProperty(name = "disabled", value = "Whether discontinuation", required = false)
    private Integer disabled;

    /**
     * Activities that
     */
    @Column(name = "description")
    @ApiModelProperty(name = "description", value = "Activities that", required = false)
    private String description;

    @PrimaryKeyField
    public Integer getFdId() {
        return fdId;
    }

    public void setFdId(Integer fdId) {
        this.fdId = fdId;
    }

    public Double getFullMoney() {
        return fullMoney;
    }

    public void setFullMoney(Double fullMoney) {
        this.fullMoney = fullMoney;
    }

    public Double getMinusValue() {
        return minusValue;
    }

    public void setMinusValue(Double minusValue) {
        this.minusValue = minusValue;
    }

    public Integer getIsFullMinus() {
        return isFullMinus;
    }

    public void setIsFullMinus(Integer isFullMinus) {
        this.isFullMinus = isFullMinus;
    }

    public Integer getIsFreeShip() {
        return isFreeShip;
    }

    public void setIsFreeShip(Integer isFreeShip) {
        this.isFreeShip = isFreeShip;
    }

    public Integer getIsSendGift() {
        return isSendGift;
    }

    public void setIsSendGift(Integer isSendGift) {
        this.isSendGift = isSendGift;
    }

    public Integer getIsSendBonus() {
        return isSendBonus;
    }

    public void setIsSendBonus(Integer isSendBonus) {
        this.isSendBonus = isSendBonus;
    }

    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public Integer getBonusId() {
        return bonusId;
    }

    public void setBonusId(Integer bonusId) {
        this.bonusId = bonusId;
    }

    public Integer getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(Integer isDiscount) {
        this.isDiscount = isDiscount;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRangeType() {
        return rangeType;
    }

    public void setRangeType(Integer rangeType) {
        this.rangeType = rangeType;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getIsSendPoint() {
        return isSendPoint;
    }

    public void setIsSendPoint(Integer isSendPoint) {
        this.isSendPoint = isSendPoint;
    }

    public Integer getPointValue() {
        return pointValue;
    }

    public void setPointValue(Integer pointValue) {
        this.pointValue = pointValue;
    }

    @Override
    public String toString() {
        return "FullDiscountDO{" +
                "fdId=" + fdId +
                ", fullMoney=" + fullMoney +
                ", isFullMinus=" + isFullMinus +
                ", minusValue=" + minusValue +
                ", isDiscount=" + isDiscount +
                ", discountValue=" + discountValue +
                ", isSendPoint=" + isSendPoint +
                ", pointValue=" + pointValue +
                ", isFreeShip=" + isFreeShip +
                ", isSendGift=" + isSendGift +
                ", giftId=" + giftId +
                ", isSendBonus=" + isSendBonus +
                ", bonusId=" + bonusId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", title='" + title + '\'' +
                ", rangeType=" + rangeType +
                ", disabled=" + disabled +
                ", description='" + description + '\'' +
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

        FullDiscountDO that = (FullDiscountDO) o;

        return new EqualsBuilder()
                .append(fdId, that.fdId)
                .append(fullMoney, that.fullMoney)
                .append(isFullMinus, that.isFullMinus)
                .append(minusValue, that.minusValue)
                .append(isDiscount, that.isDiscount)
                .append(discountValue, that.discountValue)
                .append(isFreeShip, that.isFreeShip)
                .append(isSendGift, that.isSendGift)
                .append(giftId, that.giftId)
                .append(isSendBonus, that.isSendBonus)
                .append(bonusId, that.bonusId)
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .append(title, that.title)
                .append(rangeType, that.rangeType)
                .append(disabled, that.disabled)
                .append(description, that.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(fdId)
                .append(fullMoney)
                .append(isFullMinus)
                .append(minusValue)
                .append(isDiscount)
                .append(discountValue)
                .append(isFreeShip)
                .append(isSendGift)
                .append(giftId)
                .append(isSendBonus)
                .append(bonusId)
                .append(startTime)
                .append(endTime)
                .append(title)
                .append(rangeType)
                .append(disabled)
                .append(description)
                .toHashCode();
    }
}
