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

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * Activities of goodsDTO
 *
 * @author Snow create in 2018/3/30
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PromotionGoodsDTO implements Serializable {

    private static final long serialVersionUID = 698984607262185052L;

    @ApiModelProperty(name = "goods_id", value = "productid")
    private Integer goodsId;

    @ApiModelProperty(name = "goods_name", value = "Name")
    private String goodsName;

    @ApiModelProperty(value = "Product thumbnail")
    private String thumbnail;

    @ApiModelProperty(value = "SN")
    private String sn;

    @ApiModelProperty(value = "Price")
    private Double price;

    @ApiModelProperty(value = "Inventory")
    private Integer quantity;

    @ApiModelProperty(value = "Available")
    private Integer enableQuantity;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PromotionGoodsDTO that = (PromotionGoodsDTO) o;
        return Objects.equals(goodsId, that.goodsId) &&
                Objects.equals(goodsName, that.goodsName) &&
                Objects.equals(thumbnail, that.thumbnail) &&
                Objects.equals(sn, that.sn) &&
                Objects.equals(price, that.price) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(enableQuantity, that.enableQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodsId, goodsName, thumbnail, sn, price, quantity, enableQuantity);
    }

    @Override
    public String toString() {
        return "PromotionGoodsDTO{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", sn='" + sn + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", enableQuantity=" + enableQuantity +
                '}';
    }
}
