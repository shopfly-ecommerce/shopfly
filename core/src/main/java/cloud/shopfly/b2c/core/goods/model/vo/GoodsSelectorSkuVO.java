/*
 * Yi family of hui（Beijing）All Rights Reserved.
 * You may not use this file without permission.
 * The official address：www.javamall.com.cn
 */
package cloud.shopfly.b2c.core.goods.model.vo;

import cloud.shopfly.b2c.core.promotion.tool.support.SkuNameUtil;
import io.swagger.annotations.ApiModelProperty;

/**
 * Product selector,skuunit
 *
 * @author fk
 * @version v2.0
 * @since v7.2.0
 * 2021years06month02day11:12:31
 */
public class GoodsSelectorSkuVO {
    /**
     * A primary key
     */
    @ApiModelProperty(hidden = true)
    private Integer skuId;
    /**
     * productid
     */
    @ApiModelProperty(name = "goods_id", value = "productid", hidden = true)
    private Integer goodsId;
    /**
     * Name
     */
    @ApiModelProperty(name = "goods_name", value = "Name", hidden = true)
    private String goodsName;
    /**
     * SN
     */
    @ApiModelProperty(name = "sn", value = "SN", required = false)
    private String sn;
    /**
     * Inventory
     */
    @ApiModelProperty(name = "quantity", value = "Inventory", required = false)
    private Integer quantity;
    /**
     * Available
     */
    @ApiModelProperty(name = "enable_quantity", value = "Available")
    private Integer enableQuantity;
    /**
     * Price
     */
    @ApiModelProperty(name = "price", value = "Price", required = false)
    private Double price;
    /**
     * Specification information
     */
    @ApiModelProperty(name = "specs_name", value = "Specification information", hidden = true)
    private String specsName;
    /**
     * Specification informationjson
     */
    @ApiModelProperty(name = "specs", value = "Specification informationjson", hidden = true)
    private String specs;
    /**
     * Cost price
     */
    @ApiModelProperty(name = "cost", value = "Cost price", required = true)
    private Double cost;
    /**
     * Weight
     */
    @ApiModelProperty(name = "weight", value = "Weight", required = true)
    private Double weight;
    /**
     * Categoriesid
     */
    @ApiModelProperty(name = "category_id", value = "Categoriesid")
    private Integer categoryId;
    /**
     * The thumbnail
     */
    @ApiModelProperty(name = "thumbnail", value = "The thumbnail", hidden = true)
    private String thumbnail;

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

    public String getSpecsName() {
        return SkuNameUtil.createSkuName(this.getSpecs());
    }

    public void setSpecsName(String specsName) {
        this.specsName = specsName;
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

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }
}
