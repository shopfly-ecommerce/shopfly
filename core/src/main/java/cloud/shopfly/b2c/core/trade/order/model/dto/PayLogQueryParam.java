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
package cloud.shopfly.b2c.core.trade.order.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Payment order query parameters
 *
 * @author Snow create in 2018/7/18
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PayLogQueryParam {

    @ApiModelProperty(name = "page_no", value = "What page")
    private Integer pageNo;

    @ApiModelProperty(name = "page_size", value = "Number each page")
    private Integer pageSize;

    @ApiModelProperty(name = "pay_way", value = "Method of payment")
    private String payWay;

    @ApiModelProperty(name = "start_time", value = "The start time")
    private Long startTime;

    @ApiModelProperty(name = "end_time", value = "By the time")
    private Long endTime;

    @ApiModelProperty(name = "order_sn", value = "Order no.")
    private String orderSn;

    @ApiModelProperty(name = "member_name", value = "Paying Member name")
    private String memberName;

    @ApiModelProperty(name = "payment_type", value = "Payment method", allowableValues = "ONLINE,COD")
    private String paymentType;

    @ApiModelProperty(name = "pay_status", value = "Payment status", allowableValues = "PAY_YES,PAY_NO")
    private String payStatus;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PayLogQueryParam that = (PayLogQueryParam) o;

        return new EqualsBuilder()
                .append(pageNo, that.pageNo)
                .append(pageSize, that.pageSize)
                .append(payWay, that.payWay)
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .append(orderSn, that.orderSn)
                .append(memberName, that.memberName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(pageNo)
                .append(pageSize)
                .append(payWay)
                .append(startTime)
                .append(endTime)
                .append(orderSn)
                .append(memberName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "PayLogQueryParam{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", payWay='" + payWay + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", orderSn='" + orderSn + '\'' +
                ", memberName='" + memberName + '\'' +
                '}';
    }
}
