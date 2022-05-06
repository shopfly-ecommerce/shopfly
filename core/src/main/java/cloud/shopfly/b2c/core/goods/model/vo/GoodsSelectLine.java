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
package cloud.shopfly.b2c.core.goods.model.vo;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品选择器使用对象
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月28日 下午4:28:32
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsSelectLine {

    @ApiModelProperty(value = "商品id")
    @Column(name = "goods_id")
    private Integer goodsId;
    @ApiModelProperty(value = "商品名称")
    @Column(name = "goods_name")
    private String goodsName;
    @ApiModelProperty(value = "商品编号")
    private String sn;
    @ApiModelProperty(value = "商品缩略图")
    private String thumbnail;
    @ApiModelProperty(value = "商品大图")
    private String big;
    @ApiModelProperty(value = "商品价格")
    private Double price;
    @ApiModelProperty(value = "库存")
    private Integer quantity;
    @ApiModelProperty(value = "可用库存")
    private Integer enableQuantity;
    @ApiModelProperty(value = "购买数量")
    @Column(name = "buy_count")
    private Integer buyCount;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }
}
