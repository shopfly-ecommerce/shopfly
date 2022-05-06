/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 付款单查询参数
 *
 * @author Snow create in 2018/7/18
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PayLogQueryParam {

    @ApiModelProperty(name = "page_no", value = "第几页")
    private Integer pageNo;

    @ApiModelProperty(name = "page_size", value = "每页条数")
    private Integer pageSize;

    @ApiModelProperty(name = "pay_way", value = "支付方式")
    private String payWay;

    @ApiModelProperty(name = "start_time", value = "开始时间")
    private Long startTime;

    @ApiModelProperty(name = "end_time", value = "截止时间")
    private Long endTime;

    @ApiModelProperty(name = "order_sn", value = "订单编号")
    private String orderSn;

    @ApiModelProperty(name = "member_name", value = "付款会员名")
    private String memberName;

    @ApiModelProperty(name = "payment_type", value = "付款方式", allowableValues = "ONLINE,COD")
    private String paymentType;

    @ApiModelProperty(name = "pay_status", value = "支付状态", allowableValues = "PAY_YES,PAY_NO")
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
