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

import java.io.Serializable;

/**
 * 活动DTO
 *
 * @author zh create in 2018/7/9
 * @version v2.0
 * @since v7.0.0
 */
public class PromotionPriceDTO implements Serializable {


    private static final long serialVersionUID = -975019606881133067L;

    @ApiModelProperty(value = "商品商品")
    private Integer goodsId;

    @ApiModelProperty(value = "活动价格")
    private Double price;

    public PromotionPriceDTO(Integer goodsId, Double price) {
        this.goodsId = goodsId;
        this.price = price;
    }

    public PromotionPriceDTO() {
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PromotionPriceDTO{" +
                "goodsId=" + goodsId +
                ", price=" + price +
                '}';
    }
}
