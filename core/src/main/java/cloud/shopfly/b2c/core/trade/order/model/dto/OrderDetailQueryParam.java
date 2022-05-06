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
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订单详细查询参数
 *
 * @author Snow create in 2018/7/18
 * @version v2.0
 * @since v7.0.0
 */

@ApiIgnore
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDetailQueryParam {

    @ApiModelProperty(value = "会员ID")
    private Integer buyerId;

    @ApiModelProperty(value = "商家ID")
    private Integer sellerId;


    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderDetailQueryParam that = (OrderDetailQueryParam) o;

        return new EqualsBuilder()
                .append(buyerId, that.buyerId)
                .append(sellerId, that.sellerId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(buyerId)
                .append(sellerId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderDetailQueryParam{" +
                ", buyerId=" + buyerId +
                ", sellerId=" + sellerId +
                '}';
    }
}
