/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.model.dto;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 会员优惠券查询参数
 *
 * @author Snow create in 2018/6/12
 * @version v2.0
 * @since v7.0.0
 */
public class MemberCouponQueryParam {

    @ApiModelProperty(value = "页码", name = "page_no")
    private Integer pageNo;

    @ApiModelProperty(value = "条数", name = "page_size")
    private Integer pageSize;

    @ApiModelProperty(value = "优惠券状态 0为全部，1为未使用，2为已使用，3为已过期", allowableValues = "0,1,2")
    private Integer status;

    /**
     * 此项为订单结算页，根据订单金额读取会员可用的优惠券
     */
    @ApiModelProperty(value = "订单总金额", name = "order_price")
    private Double orderPrice;


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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MemberCouponQueryParam param = (MemberCouponQueryParam) o;

        return new EqualsBuilder()
                .append(pageNo, param.pageNo)
                .append(pageSize, param.pageSize)
                .append(status, param.status)
                .append(orderPrice, param.orderPrice)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(pageNo)
                .append(pageSize)
                .append(status)
                .append(orderPrice)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "MemberCouponQueryParam{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", status=" + status +
                ", orderPrice=" + orderPrice +
                '}';
    }
}
