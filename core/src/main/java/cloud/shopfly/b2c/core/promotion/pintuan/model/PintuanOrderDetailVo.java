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
package cloud.shopfly.b2c.core.promotion.pintuan.model;

import cloud.shopfly.b2c.framework.util.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by kingapex on 2019-02-12.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-12
 */
public class PintuanOrderDetailVo extends PintuanOrder {

    @ApiModelProperty(name = "origin_price", value = "The original price")
    private Double originPrice;

    @ApiModelProperty(name = "sales_price", value = "Spell group price")
    private Double salesPrice;

    @ApiModelProperty(name = "left_time", value = "Number of seconds left in group activity")
    private Long leftTime;


    @Override
    public String toString() {
        return "PintuanOrderDetailVo{" +
                "originPrice=" + originPrice +
                ", salesPrice=" + salesPrice +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PintuanOrderDetailVo that = (PintuanOrderDetailVo) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getOriginPrice(), that.getOriginPrice())
                .append(getSalesPrice(), that.getSalesPrice())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getOriginPrice())
                .append(getSalesPrice())
                .toHashCode();
    }

    public Double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice) {
        this.originPrice = originPrice;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Long getLeftTime() {
        Long now = DateUtil.getDateline();
        Long leftTime = getEndTime() - now;
        if (leftTime < 0) {
            leftTime = 0L;
        }
        return leftTime;
    }

    public void setLeftTime(Long leftTime) {
        this.leftTime = leftTime;
    }
}
