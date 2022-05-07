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
package cloud.shopfly.b2c.core.promotion.coupon.model.dos;

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

import java.io.Serializable;


/**
 * Coupon entity
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

    /**A primary key*/
    @Id(name = "coupon_id")
    @ApiModelProperty(hidden=true)
    private Integer couponId;

    /**Coupon name*/
    @Column(name = "title")
    @ApiModelProperty(name="title",value="Coupon name",required=true)
    private String title;

    /**Coupon face value*/
    @Column(name = "coupon_price")
    @ApiModelProperty(name="coupon_price",value="Coupon face value",required=false)
    private Double couponPrice;

    /**Coupon threshold price*/
    @Column(name = "coupon_threshold_price")
    @ApiModelProperty(name="coupon_threshold_price",value="Coupon threshold price",required=false)
    private Double couponThresholdPrice;

    /**Start time of use*/
    @Column(name = "start_time")
    @ApiModelProperty(name="start_time",value="Start time of use",required=false)
    private Long startTime;

    /**Use deadline*/
    @Column(name = "end_time")
    @ApiModelProperty(name="end_time",value="Use deadline",required=false)
    private Long endTime;

    /**circulation*/
    @Column(name = "create_num")
    @ApiModelProperty(name="create_num",value="circulation",required=false)
    private Integer createNum;

    /**Limit the amount per person*/
    @Column(name = "limit_num")
    @ApiModelProperty(name="limit_num",value="Limit the amount per person",required=false)
    private Integer limitNum;

    /**The amount that has been used*/
    @Column(name = "used_num")
    @ApiModelProperty(name="used_num",value="The amount that has been used",required=false)
    private Integer usedNum;

    /**The amount that has been claimed*/
    @Column(name = "received_num")
    @ApiModelProperty(name="received_num",value="The amount that has been claimed",required=false)
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
