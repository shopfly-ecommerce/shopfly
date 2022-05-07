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
 * Converted goodsvo
 *
 * @author Snow create in 2018/3/20
 * @version v2.0
 * @since v7.0.0
 */
public class TradeConvertGoodsSkuVO implements Serializable {


    @ApiModelProperty(value = "Available")
    private Integer enableQuantity;

    @ApiModelProperty(value = "SN")
    private Integer skuId;

    @ApiModelProperty(value = "productid")
    private Integer goodsId;

    @ApiModelProperty(value = "Name")
    private String goodsName;

    @ApiModelProperty(value = "SN")
    private String sn;

    @ApiModelProperty(value = "Inventory")
    private Integer quantity;

    @ApiModelProperty(value = "Price")
    private Double price;

    @ApiModelProperty(value = "Specification informationjson")
    private String specs;

    @ApiModelProperty(value = "Cost price")
    private Double cost;

    @ApiModelProperty(value = "Weight")
    private Double weight;

    @ApiModelProperty(value = "Categoriesid")
    private Integer categoryId;

    @ApiModelProperty(value = "The thumbnail")
    private String thumbnail;

    @ApiModelProperty(value = "The seller name")
    private String sellerName;

    @ApiModelProperty(value = "The sellerid")
    private Integer sellerId;

    @ApiModelProperty(value = "Price")
    private String goodsPrice;

    @ApiModelProperty(value = "Specification list")
    private List<SpecValueVO> specList;

    @ApiModelProperty(value = "Who bears the freight0：The buyer bears,1：The seller bear")
    private Integer goodsTransfeeCharge;

    @ApiModelProperty(value = "On state1save0off")
    private Integer marketEnable;

    @ApiModelProperty(value = "Deleted or not0 delete1未delete")
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
