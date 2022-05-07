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
package cloud.shopfly.b2c.core.promotion.tool.model.dto;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * activityDTO
 *
 * @author Snow create in 2018/7/9
 * @version v2.0
 * @since v7.0.0
 */
public class PromotionDTO {

    @ApiModelProperty(value="Activities to participate inID")
    private Integer actId;

    @ApiModelProperty(value="commodity")
    private Integer goodsId;

    @ApiModelProperty(value="Quantity purchased")
    private Integer num;

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        PromotionDTO that = (PromotionDTO) o;

        return new EqualsBuilder()
                .append(actId, that.actId)
                .append(goodsId, that.goodsId)
                .append(num, that.num)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(actId)
                .append(goodsId)
                .append(num)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "PromotionDTO{" +
                "actId=" + actId +
                ", goodsId=" + goodsId +
                ", num=" + num +
                '}';
    }
}
