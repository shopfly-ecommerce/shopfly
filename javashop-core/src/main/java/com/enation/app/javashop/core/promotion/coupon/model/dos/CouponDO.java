/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.coupon.model.dos;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;


/**
 * 优惠券实体
 * @author Snow
 * @version v2.0
 * @since v7.0.0
 * 2018-04-17 23:19:39
 */
@Table(name="es_coupon")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CouponDO implements Serializable {

    private static final long serialVersionUID = 8587456467004980L;

    /**主键*/
    @Id(name = "coupon_id")
    @ApiModelProperty(hidden=true)
    private Integer couponId;

    /**优惠券名称*/
    @Column(name = "title")
    @ApiModelProperty(name="title",value="优惠券名称",required=true)
    private String title;

    /**优惠券面额*/
    @Column(name = "coupon_price")
    @ApiModelProperty(name="coupon_price",value="优惠券面额",required=false)
    private Double couponPrice;

    /**优惠券门槛价格*/
    @Column(name = "coupon_threshold_price")
    @ApiModelProperty(name="coupon_threshold_price",value="优惠券门槛价格",required=false)
    private Double couponThresholdPrice;

    /**使用起始时间*/
    @Column(name = "start_time")
    @ApiModelProperty(name="start_time",value="使用起始时间",required=false)
    private Long startTime;

    /**使用截止时间*/
    @Column(name = "end_time")
    @ApiModelProperty(name="end_time",value="使用截止时间",required=false)
    private Long endTime;

    /**发行量*/
    @Column(name = "create_num")
    @ApiModelProperty(name="create_num",value="发行量",required=false)
    private Integer createNum;

    /**每人限领数量*/
    @Column(name = "limit_num")
    @ApiModelProperty(name="limit_num",value="每人限领数量",required=false)
    private Integer limitNum;

    /**已被使用的数量*/
    @Column(name = "used_num")
    @ApiModelProperty(name="used_num",value="已被使用的数量",required=false)
    private Integer usedNum;

    /**已被领取的数量*/
    @Column(name = "received_num")
    @ApiModelProperty(name="received_num",value="已被领取的数量",required=false)
    private Integer receivedNum;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @PrimaryKeyField
    public Integer getCouponId() {
        return couponId;
    }
    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Double getCouponPrice() {
        return couponPrice;
    }
    public void setCouponPrice(Double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Double getCouponThresholdPrice() {
        return couponThresholdPrice;
    }
    public void setCouponThresholdPrice(Double couponThresholdPrice) {
        this.couponThresholdPrice = couponThresholdPrice;
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

    public Integer getCreateNum() {
        return createNum;
    }
    public void setCreateNum(Integer createNum) {
        this.createNum = createNum;
    }

    public Integer getLimitNum() {
        return limitNum;
    }
    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public Integer getUsedNum() {
        return usedNum;
    }
    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public Integer getReceivedNum() {
        if(receivedNum == null){
            receivedNum = 0;
        }
        return receivedNum;
    }

    public void setReceivedNum(Integer receivedNum) {
        this.receivedNum = receivedNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        CouponDO couponDO = (CouponDO) o;

        return new EqualsBuilder()
                .append(couponId, couponDO.couponId)
                .append(title, couponDO.title)
                .append(couponPrice, couponDO.couponPrice)
                .append(couponThresholdPrice, couponDO.couponThresholdPrice)
                .append(startTime, couponDO.startTime)
                .append(endTime, couponDO.endTime)
                .append(createNum, couponDO.createNum)
                .append(limitNum, couponDO.limitNum)
                .append(usedNum, couponDO.usedNum)
                .append(receivedNum, couponDO.receivedNum)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(couponId)
                .append(title)
                .append(couponPrice)
                .append(couponThresholdPrice)
                .append(startTime)
                .append(endTime)
                .append(createNum)
                .append(limitNum)
                .append(usedNum)
                .append(receivedNum)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "CouponDO{" +
                "couponId=" + couponId +
                ", title='" + title + '\'' +
                ", couponPrice=" + couponPrice +
                ", couponThresholdPrice=" + couponThresholdPrice +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createNum=" + createNum +
                ", limitNum=" + limitNum +
                ", usedNum=" + usedNum +
                ", receivedNum=" + receivedNum +
                '}';
    }
}
