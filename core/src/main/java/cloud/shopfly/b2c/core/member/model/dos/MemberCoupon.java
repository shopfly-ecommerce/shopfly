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
package cloud.shopfly.b2c.core.member.model.dos;

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
 * Member coupon entity
 *
 * @author Snow
 * @version vv7.0.0
 * @since v7.0.0
 * 2018-06-12 21:48:46
 */
@Table(name = "es_member_coupon")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberCoupon implements Serializable {

    private static final long serialVersionUID = 5545788652245350L;

    /**
     * A primary key
     */
    @Id(name = "mc_id")
    @ApiModelProperty(hidden = true)
    private Integer mcId;

    /**
     * Coupon table primary key
     */
    @Column(name = "coupon_id")
    @ApiModelProperty(name = "coupon_id", value = "Coupon table primary key", required = false)
    private Integer couponId;

    /**
     * Member of the primary keyid
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "Member of the primary keyid", required = false)
    private Integer memberId;

    /**
     * Use your time
     */
    @Column(name = "used_time")
    @ApiModelProperty(name = "used_time", value = "Use your time", required = false)
    private Long usedTime;

    /**
     * Get the time
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "Get the time", required = false)
    private Long createTime;

    /**
     * Order is the primary key
     */
    @Column(name = "order_id")
    @ApiModelProperty(name = "order_id", value = "Order is the primary key", required = false)
    private Integer orderId;

    /**
     * Order no.
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "Order no.", required = false)
    private String orderSn;

    /**
     * Members nickname
     */
    @Column(name = "member_name")
    @ApiModelProperty(name = "member_name", value = "Members nickname", required = false)
    private String memberName;

    /**
     * Coupon name
     */
    @Column(name = "title")
    @ApiModelProperty(name = "title", value = "Coupon name", required = false)
    private String title;

    /**
     * Coupon face value
     */
    @Column(name = "coupon_price")
    @ApiModelProperty(name = "coupon_price", value = "Coupon face value", required = false)
    private Double couponPrice;

    /**
     * Coupon threshold amount
     */
    @Column(name = "coupon_threshold_price")
    @ApiModelProperty(name = "coupon_threshold_price", value = "Coupon threshold amount", required = false)
    private Double couponThresholdPrice;

    /**
     * Start time of use
     */
    @Column(name = "start_time")
    @ApiModelProperty(name = "start_time", value = "Start time of use", required = false)
    private Long startTime;

    /**
     * Use deadline
     */
    @Column(name = "end_time")
    @ApiModelProperty(name = "end_time", value = "Use deadline", required = false)
    private Long endTime;

    @Column(name = "used_status")
    @ApiModelProperty(name = "used_status", value = "Using a state", example = "0Is not used,1Has been used,2Is expired")
    private Integer usedStatus;

    @ApiModelProperty(value = "Use status text")
    private String usedStatusText;


    @PrimaryKeyField
    public Integer getMcId() {
        return mcId;
    }

    public void setMcId(Integer mcId) {
        this.mcId = mcId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Long getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Long usedTime) {
        this.usedTime = usedTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(Integer usedStatus) {
        this.usedStatus = usedStatus;
    }

    public String getUsedStatusText() {
        if (usedStatus == 0) {
            usedStatusText = "Dont use";
        } else if(usedStatus == 2){
            usedStatusText = "expired";
        }else{
            usedStatusText = "Has been used";
        }

        return usedStatusText;
    }

    public void setUsedStatusText(String usedStatusText) {
        this.usedStatusText = usedStatusText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MemberCoupon that = (MemberCoupon) o;

        return new EqualsBuilder()
                .append(mcId, that.mcId)
                .append(couponId, that.couponId)
                .append(memberId, that.memberId)
                .append(usedTime, that.usedTime)
                .append(createTime, that.createTime)
                .append(orderId, that.orderId)
                .append(orderSn, that.orderSn)
                .append(memberName, that.memberName)
                .append(title, that.title)
                .append(couponPrice, that.couponPrice)
                .append(couponThresholdPrice, that.couponThresholdPrice)
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .append(usedStatus, that.usedStatus)
                .append(usedStatusText, that.usedStatusText)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(mcId)
                .append(couponId)
                .append(memberId)
                .append(usedTime)
                .append(createTime)
                .append(orderId)
                .append(orderSn)
                .append(memberName)
                .append(title)
                .append(couponPrice)
                .append(couponThresholdPrice)
                .append(startTime)
                .append(endTime)
                .append(usedStatus)
                .append(usedStatusText)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "MemberCoupon{" +
                "mcId=" + mcId +
                ", couponId=" + couponId +
                ", memberId=" + memberId +
                ", usedTime=" + usedTime +
                ", createTime=" + createTime +
                ", orderId=" + orderId +
                ", orderSn='" + orderSn + '\'' +
                ", memberName='" + memberName + '\'' +
                ", title='" + title + '\'' +
                ", couponPrice=" + couponPrice +
                ", couponThresholdPrice=" + couponThresholdPrice +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", usedStatus=" + usedStatus +
                ", usedStatusText='" + usedStatusText + '\'' +
                '}';
    }
}
