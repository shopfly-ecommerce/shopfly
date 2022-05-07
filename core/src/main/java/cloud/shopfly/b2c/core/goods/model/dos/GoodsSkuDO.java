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
package cloud.shopfly.b2c.core.goods.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.Objects;


/**
 * productskuentity
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-21 11:48:40
 */
@Table(name = "es_goods_sku")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsSkuDO implements Serializable {

    private static final long serialVersionUID = 5102510694003249L;

    /**
     * A primary key
     */
    @Id(name = "sku_id")
    @ApiModelProperty(hidden = true)
    private Integer skuId;
    /**
     * productid
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "productid", hidden = true)
    private Integer goodsId;
    /**
     * Name
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "Name", hidden = true)
    private String goodsName;
    /**
     * SN
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "SN", required = false)
    @Length(max = 30, message = "The specification number is too long and cannot be exceeded30A character")
    private String sn;
    /**
     * Inventory
     */
    @Column(name = "quantity")
    @ApiModelProperty(name = "quantity", value = "Inventory", required = false)
    @Max(value = 99999999, message = "Stock cannot exceed99999999")
    private Integer quantity;
    /**
     * Available
     */
    @Column(name = "enable_quantity")
    @ApiModelProperty(name = "enable_quantity", value = "Available")
    private Integer enableQuantity;
    /**
     * Price
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "Price", required = false)
    @Max(value = 99999999, message = "The price cannot exceed99999999")
    private Double price;
    /**
     * Specification informationjson
     */
    @Column(name = "specs")
    @ApiModelProperty(name = "specs", value = "Specification informationjson", hidden = true)
    @JsonIgnore
    private String specs;
    /**
     * Cost price
     */
    @Column(name = "cost")
    @ApiModelProperty(name = "cost", value = "Cost price", required = true)
    @Max(value = 99999999, message = "The cost price cannot exceed99999999")
    private Double cost;
    /**
     * Weight
     */
    @Column(name = "weight")
    @ApiModelProperty(name = "weight", value = "Weight", required = true)
    @Max(value = 99999999, message = "Weight shall not exceed99999999")
    private Double weight;
    /**
     * The sellerid
     */
    @ApiModelProperty(name = "seller_id", value = "The sellerid", hidden = true)
    private Integer sellerId;
    /**
     * The seller name
     */
    @ApiModelProperty(name = "seller_name", value = "The seller name", hidden = true)
    private String sellerName;
    /**
     * Categoriesid
     */
    @Column(name = "category_id")
    @ApiModelProperty(name = "category_id", value = "Categoriesid", hidden = true)
    private Integer categoryId;
    /**
     * The thumbnail
     */
    @Column(name = "thumbnail")
    @ApiModelProperty(name = "thumbnail", value = "The thumbnail", hidden = true)
    private String thumbnail;


    @Column(name = "hash_code")
    @ApiModelProperty(name = "hash_code", value = "hash_code", hidden = true)
    private Integer hashCode;


    private Integer templateId;

    public GoodsSkuDO() {
    }

    @PrimaryKeyField
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

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;
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

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getHashCode() {
        return hashCode;
    }

    public void setHashCode(Integer hashCode) {
        this.hashCode = hashCode;
    }

    public Integer getSellerId() {
        return 1;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return "Platform proprietary";
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @Override
    public String toString() {
        return "GoodsSkuDO{" +
                "skuId=" + skuId +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", sn='" + sn + '\'' +
                ", quantity=" + quantity +
                ", enableQuantity=" + enableQuantity +
                ", price=" + price +
                ", specs='" + specs + '\'' +
                ", cost=" + cost +
                ", weight=" + weight +
                ", categoryId=" + categoryId +
                ", thumbnail='" + thumbnail + '\'' +
                ", hashCode=" + hashCode +
                ", templateId=" + templateId +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        GoodsSkuDO that = (GoodsSkuDO) o;
        return Objects.equals(skuId, that.skuId) &&
                Objects.equals(goodsId, that.goodsId) &&
                Objects.equals(goodsName, that.goodsName) &&
                Objects.equals(sn, that.sn) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(enableQuantity, that.enableQuantity) &&
                Objects.equals(price, that.price) &&
                Objects.equals(specs, that.specs) &&
                Objects.equals(cost, that.cost) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(thumbnail, that.thumbnail) &&
                Objects.equals(hashCode, that.hashCode) &&
                Objects.equals(templateId, that.templateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skuId, goodsId, goodsName, sn, quantity, enableQuantity, price, specs, cost, weight, categoryId, thumbnail, hashCode, templateId);
    }
}
