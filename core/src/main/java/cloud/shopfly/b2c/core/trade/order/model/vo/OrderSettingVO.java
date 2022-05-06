/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 订单设置VO
 *
 * @author Snow create in 2018/7/13
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderSettingVO implements Serializable {

    @ApiModelProperty(name = "cancel_order_day", value = "自动取消订单天数", required = true)
    public Integer cancelOrderDay;

    @ApiModelProperty(name = "rog_order_day", value = "自动确认收货天数", required = true)
    public Integer rogOrderDay;

    @ApiModelProperty(name = "comment_order_day", value = "评价超时天数", required = true)
    public Integer commentOrderDay;

    @ApiModelProperty(name = "service_expired_day", value = "售后失效天数", required = true)
    public Integer serviceExpiredDay;

    @ApiModelProperty(name = "complete_order_day", value = "订单完成天数", required = true)
    public Integer completeOrderDay;

    @ApiModelProperty(name = "complete_order_pay", value = "自动支付天数,仅对货到付款的订单有效", required = true)
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
