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
package cloud.shopfly.b2c.core.trade.cart.model.vo;

import cloud.shopfly.b2c.core.goods.model.vo.SpecValueVO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 转化后的货品vo
 *
 * @author Snow create in 2018/3/20
 * @version v2.0
 * @since v7.0.0
 */
public class TradeConvertGoodsSkuVO implements Serializable {


    @ApiModelProperty(value = "可用库存")
    private Integer enableQuantity;

    @ApiModelProperty(value = "货号")
    private Integer skuId;

    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品编号")
    private String sn;

    @ApiModelProperty(value = "库存")
    private Integer quantity;

    @ApiModelProperty(value = "价格")
    private Double price;

    @ApiModelProperty(value = "规格信息json")
    private String specs;

    @ApiModelProperty(value = "成本价格")
    private Double cost;

    @ApiModelProperty(value = "重量")
    private Double weight;

    @ApiModelProperty(value = "分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "卖家名称")
    private String sellerName;

    @ApiModelProperty(value = "卖家id")
    private Integer sellerId;

    @ApiModelProperty(value = "商品价格")
    private String goodsPrice;

    @ApiModelProperty(value = "规格列表")
    private List<SpecValueVO> specList;

    @ApiModelProperty(value = "谁承担运费0：买家承担，1：卖家承担")
    private Integer goodsTransfeeCharge;

    @ApiModelProperty(value = "上架状态 1上架  0下架")
    private Integer marketEnable;

    @ApiModelProperty(value = "是否被删除0 删除 1未删除")
    private Integer disabled;


    @Override
    public String toString() {
        return "TradeConvertGoodsSkuVO{" +
                "enableQuantity=" + enableQuantity +
                ", skuId=" + skuId +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", sn='" + sn + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", specs='" + specs + '\'' +
                ", cost=" + cost +
                ", weight=" + weight +
                ", categoryId=" + categoryId +
                ", thumbnail='" + thumbnail + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", sellerId=" + sellerId +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", goodsTransfeeCharge=" + goodsTransfeeCharge +
                ", marketEnable=" + marketEnable +
                ", disabled=" + disabled +
                '}';
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsTransfeeCharge() {
        return goodsTransfeeCharge;
    }

    public void setGoodsTransfeeCharge(Integer goodsTransfeeCharge) {
        this.goodsTransfeeCharge = goodsTransfeeCharge;
    }

    public Integer getMarketEnable() {
        return marketEnable;
    }

    public void setMarketEnable(Integer marketEnable) {
        this.marketEnable = marketEnable;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;
    }

    public List<SpecValueVO> getSpecList() {
        return specList;
    }

    public void setSpecList(List<SpecValueVO> specList) {
        this.specList = specList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TradeConvertGoodsSkuVO that = (TradeConvertGoodsSkuVO) o;

        return new EqualsBuilder()
                .append(enableQuantity, that.enableQuantity)
                .append(skuId, that.skuId)
                .append(goodsId, that.goodsId)
                .append(goodsName, that.goodsName)
                .append(sn, that.sn)
                .append(quantity, that.quantity)
                .append(price, that.price)
                .append(specs, that.specs)
                .append(cost, that.cost)
                .append(weight, that.weight)
                .append(categoryId, that.categoryId)
                .append(thumbnail, that.thumbnail)
                .append(sellerName, that.sellerName)
                .append(sellerId, that.sellerId)
                .append(goodsPrice, that.goodsPrice)
                .append(specList, that.specList)
                .append(goodsTransfeeCharge, that.goodsTransfeeCharge)
                .append(marketEnable, that.marketEnable)
                .append(disabled, that.disabled)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(enableQuantity)
                .append(skuId)
                .append(goodsId)
                .append(goodsName)
                .append(sn)
                .append(quantity)
                .append(price)
                .append(specs)
                .append(cost)
                .append(weight)
                .append(categoryId)
                .append(thumbnail)
                .append(sellerName)
                .append(sellerId)
                .append(goodsPrice)
                .append(specList)
                .append(goodsTransfeeCharge)
                .append(marketEnable)
                .append(disabled)
                .toHashCode();
    }
}
