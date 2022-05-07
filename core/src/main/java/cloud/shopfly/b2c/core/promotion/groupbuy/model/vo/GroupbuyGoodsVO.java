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
package cloud.shopfly.b2c.core.promotion.groupbuy.model.vo;

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyGoodsDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A bulk goodsVO
 *
 * @author Snow create in 2018/6/11
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel
public class GroupbuyGoodsVO extends GroupbuyGoodsDO {


    @ApiModelProperty(name = "start_time", value = "Activity start time")
    private Long startTime;

    @ApiModelProperty(name = "end_time", value = "Activity deadline")
    private Long endTime;

    @ApiModelProperty(name = "title", value = "Activity title")
    private String title;

    @ApiModelProperty(name = "enable_quantity", value = "Available")
    private Integer enableQuantity;

    @ApiModelProperty(name = "quantity", value = "Inventory")
    private Integer quantity;

    @ApiModelProperty(name = "isEnable", value = "Whether the activity is in progress")
    private Integer isEnable;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }


    @Override
    public String toString() {
        return "GroupbuyGoodsVO{" +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", title='" + title + '\'' +
                ", enableQuantity=" + enableQuantity +
                ", quantity=" + quantity +
                ", isEnable=" + isEnable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupbuyGoodsVO that = (GroupbuyGoodsVO) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .append(title, that.title)
                .append(enableQuantity, that.enableQuantity)
                .append(quantity, that.quantity)
                .append(isEnable, that.isEnable)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(startTime)
                .append(endTime)
                .append(title)
                .append(enableQuantity)
                .append(quantity)
                .append(isEnable)
                .toHashCode();
    }


}
