/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.fulldiscount.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;


/**
 * 满优惠活动实体<br/>
 * 默认1为选中，0为未选
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
     * 活动id
     */
    @Id(name = "fd_id")
    @ApiModelProperty(hidden = true)
    private Integer fdId;

    /**
     * 优惠门槛金额
     */
    @Column(name = "full_money")
    @NotNull(message = "请填写优惠门槛")
    @DecimalMax(value = "99999999.00", message = "优惠券门槛金额超出限制")
    @ApiModelProperty(name = "full_money", value = "优惠门槛金额", required = true)
    private Double fullMoney;

    /**
     * 活动是否减现金
     */
    @Column(name = "is_full_minus")
    @Min(value = 0, message = "减现金参数有误")
    @Max(value = 1, message = "减现金参数有误")
    @ApiModelProperty(name = "is_full_minus", value = "活动是否减现金", required = false, example = "0为否,1为是")
    private Integer isFullMinus;

    /**
     * 减现金
     */
    @Column(name = "minus_value")
    @ApiModelProperty(name = "minus_value", value = "减现金", required = false)
    private Double minusValue;

    /**
     * 是否打折
     */
    @Column(name = "is_discount")
    @Min(value = 0, message = "打折参数有误")
    @Max(value = 1, message = "打折参数有误")
    @ApiModelProperty(name = "is_discount", value = "是否打折", required = false, example = "0为否,1为是")
    private Integer isDiscount;

    /**
     * 打多少折
     */
    @Column(name = "discount_value")
    @ApiModelProperty(name = "discount_value", value = "打多少折", required = false)
    private Double discountValue;


    /**
     * 是否赠送
     */
    @Column(name = "is_send_point")
    @Min(value = 0, message = "赠送积分有误")
    @Max(value = 1, message = "赠送积分有误")
    @ApiModelProperty(name = "is_send_point", value = "是否赠送积分", required = false, example = "0为否,1为是")
    private Integer isSendPoint;

    /**
     * 积分数据
     */
    @Column(name = "point_value")
    @ApiModelProperty(name = "point_value", value = "赠送多少积分", required = false)
    private Integer pointValue;

    /**
     * 是否免邮
     */
    @Column(name = "is_free_ship")
    @Min(value = 0, message = "免邮费参数有误")
    @Max(value = 1, message = "免邮费参数有误")
    @ApiModelProperty(name = "is_free_ship", value = "是否邮费", required = false, example = "0为否,1为是")
    private Integer isFreeShip;

    /**
     * 是否有赠品
     */
    @Column(name = "is_send_gift")
    @Min(value = 0, message = "送赠品参数有误")
    @Max(value = 1, message = "送赠品参数有误")
    @ApiModelProperty(name = "is_send_gift", value = "是否有赠品", required = false, example = "0为否,1为是")
    private Integer isSendGift;

    /**
     * 赠品id
     */
    @Column(name = "gift_id")
    @ApiModelProperty(name = "gift_id", value = "赠品id", required = false)
    private Integer giftId;


    /**
     * 是否赠优惠券
     */
    @Column(name = "is_send_bonus")
    @Min(value = 0, message = "送优惠券参数有误")
    @Max(value = 1, message = "送优惠券参数有误")
    @ApiModelProperty(name = "is_send_bonus", value = "是否增优惠券", required = false, example = "0为否,1为是")
    private Integer isSendBonus;

    /**
     * 优惠券id
     */
    @Column(name = "bonus_id")
    @ApiModelProperty(name = "bonus_id", value = "优惠券id", required = false)
    private Integer bonusId;

    /**
     * 活动开始时间
     */
    @Column(name = "start_time")
    @NotNull(message = "请填写活动起始时间")
    @ApiModelProperty(name = "start_time", value = "活动起始时间", required = true)
    private Long startTime;

    /**
     * 活动结束时间
     */
    @Column(name = "end_time")
    @NotNull(message = "请填写活动截止时间")
    @ApiModelProperty(name = "end_time", value = "活动截止时间", required = true)
    private Long endTime;

    /**
     * 活动标题
     */
    @Column(name = "title")
    @NotEmpty(message = "请填写活动标题")
    @ApiModelProperty(name = "title", value = "活动标题", required = true)
    private String title;

    /**
     * 商品参与方式
     */
    @Column(name = "range_type")
    @NotNull(message = "请选择商品参与方式，全部商品：1，部分商品：2")
    @Min(value = 1, message = "商品参与方式值不正确")
    @Max(value = 2, message = "商品参与方式值不正确")
    @ApiModelProperty(name = "range_type", value = "商品参与方式", required = true)
    private Integer rangeType;

    /**
     * 是否停用
     */
    @Column(name = "disabled")
    @ApiModelProperty(name = "disabled", value = "是否停用", required = false)
    private Integer disabled;

    /**
     * 活动说明
     */
    @Column(name = "description")
    @ApiModelProperty(name = "description", value = "活动说明", required = false)
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
