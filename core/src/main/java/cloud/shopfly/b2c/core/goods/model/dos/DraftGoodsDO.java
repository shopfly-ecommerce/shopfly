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

import cloud.shopfly.b2c.core.goods.model.dto.GoodsDTO;
import cloud.shopfly.b2c.core.goods.model.enums.GoodsType;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jodd.util.StringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Draft commodity entity
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-26 11:01:27
 */
@Table(name = "es_draft_goods")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DraftGoodsDO implements Serializable {

    private static final long serialVersionUID = 7646662730625878L;

    /**
     * The draft of goodsid
     */
    @Id(name = "draft_goods_id")
    @ApiModelProperty(hidden = true)
    private Integer draftGoodsId;
    /**
     * SN
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "SN", required = false)
    private String sn;
    /**
     * Name
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "Name", required = false)
    private String goodsName;
    /**
     * BrandID
     */
    @Column(name = "brand_id")
    @ApiModelProperty(name = "brand_id", value = "BrandID", required = false)
    private Integer brandId;
    /**
     * CategoryID
     */
    @Column(name = "category_id")
    @ApiModelProperty(name = "category_id", value = "CategoryID", required = false)
    private Integer categoryId;
    /**
     * Weight
     */
    @Column(name = "weight")
    @ApiModelProperty(name = "weight", value = "Weight", required = false)
    private Double weight;
    /**
     * Product details
     */
    @Column(name = "intro")
    @ApiModelProperty(name = "intro", value = "Product details", required = false)
    private String intro;
    /**
     * Price
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "Price", required = false)
    private Double price;
    /**
     * The cost of goods
     */
    @Column(name = "cost")
    @ApiModelProperty(name = "cost", value = "The cost of goods", required = false)
    private Double cost;
    /**
     * Commodity market price
     */
    @Column(name = "mktprice")
    @ApiModelProperty(name = "mktprice", value = "Commodity market price", required = false)
    private Double mktprice;
    /**
     * Type
     */
    @Column(name = "goods_type")
    @ApiModelProperty(name = "goods_type", value = "Type", required = false)
    private String goodsType;
    /**
     * Enable specifications
     */
    @Column(name = "have_spec")
    @ApiModelProperty(name = "have_spec", value = "Enable specifications", required = false)
    private Integer haveSpec;
    /**
     * Product addition time
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "Product addition time", required = false)
    private Long createTime;
    /**
     * Gross merchandise inventory
     */
    @Column(name = "quantity")
    @ApiModelProperty(name = "quantity", value = "Gross merchandise inventory", required = false)
    private Integer quantity;
    /**
     * Original picture of commodity
     */
    @Column(name = "original")
    @ApiModelProperty(name = "original", value = "Original picture of commodity", required = false)
    private String original;

    /**
     * Freight templateID
     */
    @Column(name = "template_id")
    @ApiModelProperty(name = "template_id", value = "Freight templateID", required = false)
    private Integer templateId;
    /**
     * Whether to bear the freight for the buyer
     */
    @Column(name = "goods_transfee_charge")
    @ApiModelProperty(name = "goods_transfee_charge", value = "Whether to bear the freight for the buyer", required = false)
    private Integer goodsTransfeeCharge;

    /**
     * seo  title
     */
    @Column(name = "page_title")
    @ApiModelProperty(name = "page_title", value = "seo  title", required = false)
    private String pageTitle;
    /**
     * seokeyword
     */
    @Column(name = "meta_keywords")
    @ApiModelProperty(name = "meta_keywords", value = "seokeyword", required = false)
    private String metaKeywords;
    /**
     * seo describe
     */
    @Column(name = "meta_description")
    @ApiModelProperty(name = "meta_description", value = "seo describe", required = false)
    private String metaDescription;
    /**
     * The amount of money needed to score goods
     */
    @Column(name = "exchange_money")
    @ApiModelProperty(name = "exchange_money", value = "The amount of money needed to score goods", required = false)
    private Double exchangeMoney;
    /**
     * Points Points required for an item
     */
    @Column(name = "exchange_point")
    @ApiModelProperty(name = "exchange_point", value = "Points Points required for an item", required = false)
    private Integer exchangePoint;
    /**
     * Classification of integral goodsid
     */
    @Column(name = "exchange_category_id")
    @ApiModelProperty(name = "exchange_category_id", value = "Classification of integral goodsid", required = false)
    private Integer exchangeCategoryId;
    @ApiModelProperty(hidden = true)
    private List<String> galleryList;

    public DraftGoodsDO() {
    }

    public DraftGoodsDO(GoodsDTO goodsVO) {

        this.draftGoodsId = goodsVO.getGoodsId();
        this.categoryId = goodsVO.getCategoryId();
        this.brandId = goodsVO.getBrandId();
        this.goodsName = goodsVO.getGoodsName();
        this.sn = goodsVO.getSn();
        this.price = goodsVO.getPrice() == null ? 0 : goodsVO.getPrice();
        this.cost = goodsVO.getCost() == null ? 0 : goodsVO.getCost();
        this.mktprice = goodsVO.getMktprice() == null ? 0 : goodsVO.getMktprice();
        this.weight = goodsVO.getWeight() == null ? 0 : goodsVO.getWeight();
        this.goodsTransfeeCharge = goodsVO.getGoodsTransfeeCharge();
        this.intro = goodsVO.getIntro();
        this.haveSpec = goodsVO.getHaveSpec();

        if (goodsVO.getExchange() != null && goodsVO.getExchange().getEnableExchange() == 1) {
            this.goodsType = GoodsType.POINT.name();
            this.exchangeMoney = goodsVO.getExchange().getExchangeMoney();
            this.exchangePoint = goodsVO.getExchange().getExchangePoint();
            this.exchangeCategoryId = goodsVO.getExchange().getCategoryId();
        } else {
            this.goodsType = GoodsType.NORMAL.name();
            this.exchangeMoney = 0.00;
            this.exchangePoint = 0;
            this.exchangeCategoryId = 1;
        }
        this.pageTitle = goodsVO.getPageTitle();
        this.metaKeywords = goodsVO.getMetaKeywords();
        this.metaDescription = goodsVO.getMetaDescription();
        this.templateId = goodsVO.getTemplateId();
    }

    @PrimaryKeyField
    public Integer getDraftGoodsId() {
        return draftGoodsId;
    }

    public void setDraftGoodsId(Integer draftGoodsId) {
        this.draftGoodsId = draftGoodsId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getHaveSpec() {
        return haveSpec;
    }

    public void setHaveSpec(Integer haveSpec) {
        this.haveSpec = haveSpec;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getGoodsTransfeeCharge() {
        return goodsTransfeeCharge;
    }

    public void setGoodsTransfeeCharge(Integer goodsTransfeeCharge) {
        this.goodsTransfeeCharge = goodsTransfeeCharge;
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

    public Double getExchangeMoney() {
        return exchangeMoney;
    }

    public void setExchangeMoney(Double exchangeMoney) {
        this.exchangeMoney = exchangeMoney;
    }

    public Integer getExchangePoint() {
        return exchangePoint;
    }

    public void setExchangePoint(Integer exchangePoint) {
        this.exchangePoint = exchangePoint;
    }

    public Integer getExchangeCategoryId() {
        return exchangeCategoryId;
    }

    public void setExchangeCategoryId(Integer exchangeCategoryId) {
        this.exchangeCategoryId = exchangeCategoryId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getGalleryList() {

        if (!StringUtil.isEmpty(this.getOriginal())) {
            return JsonUtil.jsonToList(this.getOriginal(), String.class);
        }
        return galleryList;
    }

    public void setGalleryList(List<String> galleryList) {
        this.galleryList = galleryList;
    }

    @Override
    public String toString() {
        return "DraftGoodsDO{" +
                "draftGoodsId=" + draftGoodsId +
                ", sn='" + sn + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", brandId=" + brandId +
                ", categoryId=" + categoryId +
                ", weight=" + weight +
                ", intro='" + intro + '\'' +
                ", price=" + price +
                ", cost=" + cost +
                ", mktprice=" + mktprice +
                ", goodsType='" + goodsType + '\'' +
                ", haveSpec=" + haveSpec +
                ", createTime=" + createTime +
                ", quantity=" + quantity +
                ", original='" + original + '\'' +
                ", templateId=" + templateId +
                ", goodsTransfeeCharge=" + goodsTransfeeCharge +
                ", pageTitle='" + pageTitle + '\'' +
                ", metaKeywords='" + metaKeywords + '\'' +
                ", metaDescription='" + metaDescription + '\'' +
                ", exchangeMoney=" + exchangeMoney +
                ", exchangePoint=" + exchangePoint +
                ", exchangeCategoryId=" + exchangeCategoryId +
                ", galleryList=" + galleryList +
                '}';
    }
}
