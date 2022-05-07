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
package cloud.shopfly.b2c.core.member.model.dto;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Membership coupon query parameters
 *
 * @author Snow create in 2018/6/12
 * @version v2.0
 * @since v7.0.0
 */
public class MemberCouponQueryParam {

    @ApiModelProperty(value = "The page number", name = "page_no")
    private Integer pageNo;

    @ApiModelProperty(value = "A number of", name = "page_size")
    private Integer pageSize;

    @ApiModelProperty(value = "Coupon status0For all,1Is not used,2Is used,3Overdue for", allowableValues = "0,1,2")
    private Integer status;

    /**
     * This is the order settlement page, which reads the coupons available to members according to the order amount
     */
    @ApiModelProperty(value = "Total order amount", name = "order_price")
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
