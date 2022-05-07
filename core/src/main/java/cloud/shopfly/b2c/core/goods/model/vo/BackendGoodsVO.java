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

import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Background Home page commodityvo
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/7/6 In the morning12:07
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BackendGoodsVO implements Serializable {

    private static final long serialVersionUID = 3922135264669953741L;
    @ApiModelProperty(value = "id")
    private Integer goodsId;

    @ApiModelProperty(name = "category_id", value = "Categoriesid")
    private Integer categoryId;

    @ApiModelProperty(value = "name")
    private String categoryName;

    @ApiModelProperty(name = "shop_cat_id", value = "Store classificationid")
    private Integer shopCatId;

    @ApiModelProperty(name = "brand_id", value = "brandid")
    private Integer brandId;

    @ApiModelProperty(name = "goods_name", value = "Name")
    private String goodsName;

    @ApiModelProperty(name = "sn", value = "SN")
    private String sn;

    @ApiModelProperty(name = "price", value = "Price")
    private Double price;

    @ApiModelProperty(name = "cost", value = "Cost price")
    private Double cost;

    @ApiModelProperty(name = "mktprice", value = "Marking price")
    private Double mktprice;

    @ApiModelProperty(name = "weight", value = "Weight")
    private Double weight;

    @ApiModelProperty(name = "goods_transfee_charge", value = "Who bears the freight0：The buyer bears,1：The seller bear")
    private Integer goodsTransfeeCharge;

    @ApiModelProperty(name = "intro", value = "details")
    private String intro;

    @ApiModelProperty(name = "have_spec", value = "Are there specifications?0There is no1There are")
    private Integer haveSpec;

    @ApiModelProperty(name = "quantity", value = "Inventory")
    private Integer quantity;

    @ApiModelProperty(name = "market_enable", value = "Whether its on the shelf,1save0off")
    private Integer marketEnable;

    @ApiModelProperty(name = "goods_gallery_list", value = "Photo album")
    private List<GoodsGalleryDO> goodsGalleryList;

    @ApiModelProperty(name = "page_title", value = "seo title", required = false)
    private String pageTitle;

    @ApiModelProperty(name = "meta_keywords", value = "seokeyword", required = false)
    private String metaKeywords;

    @ApiModelProperty(name = "meta_description", value = "seo describe", required = false)
    private String metaDescription;

    @ApiModelProperty(name = "template_id", value = "The freight templateid,不需要The freight template时值是0")
    private Integer templateId;

    @ApiModelProperty(value = "Available")
    private Integer enableQuantity;

    /**
     * Thumbnail path
     */
    @Column(name = "thumbnail")
    @ApiModelProperty(name = "thumbnail", value = "Thumbnail path", required = false)
    private String thumbnail;
    /**
     * A larger image path
     */
    @Column(name = "big")
    @ApiModelProperty(name = "big", value = "A larger image path", required = false)
    private String big;
    /**
     * Insets path
     */
    @Column(name = "small")
    @ApiModelProperty(name = "small", value = "Insets path", required = false)
    private String small;
    /**
     * The original path
     */
    @Column(name = "original")
    @ApiModelProperty(name = "original", value = "The original path", required = false)
    private String original;

    public BackendGoodsVO() {
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getShopCatId() {
        return shopCatId;
    }

    public void setShopCatId(Integer shopCatId) {
        this.shopCatId = shopCatId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getMktprice() {
        return mktprice;
    }

    public void setMktprice(Double mktprice) {
        this.mktprice = mktprice;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getGoodsTransfeeCharge() {
        return goodsTransfeeCharge;
    }

    public void setGoodsTransfeeCharge(Integer goodsTransfeeCharge) {
        this.goodsTransfeeCharge = goodsTransfeeCharge;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getHaveSpec() {
        return haveSpec;
    }

    public void setHaveSpec(Integer haveSpec) {
        this.haveSpec = haveSpec;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<GoodsGalleryDO> getGoodsGalleryList() {
        return goodsGalleryList;
    }

    public void setGoodsGalleryList(List<GoodsGalleryDO> goodsGalleryList) {
        this.goodsGalleryList = goodsGalleryList;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getMarketEnable() {
        return marketEnable;
    }

    public void setMarketEnable(Integer marketEnable) {
        this.marketEnable = marketEnable;
    }

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;

    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    @Override
    public String toString() {
        return "GoodsVO{" +
                "goodsId=" + goodsId +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", shopCatId=" + shopCatId +
                ", brandId=" + brandId +
                ", goodsName='" + goodsName + '\'' +
                ", sn='" + sn + '\'' +
                ", price=" + price +
                ", cost=" + cost +
                ", mktprice=" + mktprice +
                ", weight=" + weight +
                ", goodsTransfeeCharge=" + goodsTransfeeCharge +
                ", intro='" + intro + '\'' +
                ", haveSpec=" + haveSpec +
                ", quantity=" + quantity +
                ", marketEnable=" + marketEnable +
                ", goodsGalleryList=" + goodsGalleryList +
                ", pageTitle='" + pageTitle + '\'' +
                ", metaKeywords='" + metaKeywords + '\'' +
                ", metaDescription='" + metaDescription + '\'' +
                ", templateId=" + templateId +
                ", enableQuantity=" + enableQuantity +
                '}';
    }
}


