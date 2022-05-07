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

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * OrderVO
 *
 * @author Snow create in 2018/7/13
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderSettingVO implements Serializable {

    @ApiModelProperty(name = "cancel_order_day", value = "Days of automatic cancellation", required = true)
    public Integer cancelOrderDay;

    @ApiModelProperty(name = "rog_order_day", value = "Automatic confirmation of receiving days", required = true)
    public Integer rogOrderDay;

    @ApiModelProperty(name = "comment_order_day", value = "Evaluate timeout days", required = true)
    public Integer commentOrderDay;

    @ApiModelProperty(name = "service_expired_day", value = "Number of days after sale", required = true)
    public Integer serviceExpiredDay;

    @ApiModelProperty(name = "complete_order_day", value = "Order completion days", required = true)
    public Integer completeOrderDay;

    @ApiModelProperty(name = "complete_order_pay", value = "Automatic payment days,Only valid for cash on delivery orders", required = true)
    public Integer completeOrderPay;

    public Integer getCancelOrderDay() {
        return cancelOrderDay;
    }

    public void setCancelOrderDay(Integer cancelOrderDay) {
        this.cancelOrderDay = cancelOrderDay;
    }

    public Integer getRogOrderDay() {
        return rogOrderDay;
    }

    public void setRogOrderDay(Integer rogOrderDay) {
        this.rogOrderDay = rogOrderDay;
    }

    public Integer getCommentOrderDay() {
        return commentOrderDay;
    }

    public void setCommentOrderDay(Integer commentOrderDay) {
        this.commentOrderDay = commentOrderDay;
    }

    public Integer getServiceExpiredDay() {
        return serviceExpiredDay;
    }

    public void setServiceExpiredDay(Integer serviceExpiredDay) {
        this.serviceExpiredDay = serviceExpiredDay;
    }

    public Integer getCompleteOrderDay() {
        return completeOrderDay;
    }

    public void setCompleteOrderDay(Integer completeOrderDay) {
        this.completeOrderDay = completeOrderDay;
    }

    public Integer getCompleteOrderPay() {
        return completeOrderPay;
    }

    public void setCompleteOrderPay(Integer completeOrderPay) {
        this.completeOrderPay = completeOrderPay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderSettingVO that = (OrderSettingVO) o;

        return new EqualsBuilder()
                .append(cancelOrderDay, that.cancelOrderDay)
                .append(rogOrderDay, that.rogOrderDay)
                .append(commentOrderDay, that.commentOrderDay)
                .append(serviceExpiredDay, that.serviceExpiredDay)
                .append(completeOrderDay, that.completeOrderDay)
                .append(completeOrderPay, that.completeOrderPay)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(cancelOrderDay)
                .append(rogOrderDay)
                .append(commentOrderDay)
                .append(serviceExpiredDay)
                .append(completeOrderDay)
                .append(completeOrderPay)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderSettingVO{" +
                "cancelOrderDay=" + cancelOrderDay +
                ", rogOrderDay=" + rogOrderDay +
                ", commentOrderDay=" + commentOrderDay +
                ", serviceExpiredDay=" + serviceExpiredDay +
                ", completeOrderDay=" + completeOrderDay +
                ", completeOrderPay=" + completeOrderPay +
                '}';
    }
}
