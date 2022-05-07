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
package cloud.shopfly.b2c.core.goods.model.dto;

import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsParamsDO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * productvo
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month21The morning of11:25:10
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsDTO implements Serializable {

    private static final long serialVersionUID = 3922135264669953741L;
    @ApiModelProperty(hidden = true)
    private Integer goodsId;

    @ApiModelProperty(name = "category_id", value = "Categoriesid", required = true)
    @NotNull(message = "Mall classification cannot be empty")
    @Min(value = 0, message = "The mall classification value is incorrect")
    private Integer categoryId;

    /**
     * Used to display
     */
    @ApiModelProperty(name = "category_name", value = "name", required = true)
    private String categoryName;

    @ApiModelProperty(name = "brand_id", value = "brandid", required = false)
    @Min(value = 0, message = "The brand value is incorrect")
    private Integer brandId;

    @ApiModelProperty(name = "goods_name", value = "Name", required = true)
    @NotEmpty(message = "The commodity name cannot be empty")
    private String goodsName;

    @ApiModelProperty(name = "sn", value = "SN", required = true)
    @Length(max = 30, message = "Item number is too long and cannot be exceeded30A character")
    private String sn;

    @ApiModelProperty(name = "price", value = "Price", required = true)
    @NotNull(message = "Commodity prices cannot be empty")
    @Min(value = 0, message = "Commodity prices cannot be negative")
    @Max(value = 99999999, message = "Commodity prices cannot exceed99999999")
    private Double price;

    @ApiModelProperty(name = "cost", value = "Cost price", required = true)
    @NotNull(message = "The cost price cannot be empty")
    @Min(value = 0, message = "The cost price cannot be negative")
    @Max(value = 99999999, message = "The cost price cannot exceed99999999")
    private Double cost;

    @ApiModelProperty(name = "mktprice", value = "Marking price", required = true)
    @NotNull(message = "The market price cannot be empty")
    @Min(value = 0, message = "The market price cannot be negative")
    @Max(value = 99999999, message = "The market price cannot exceed99999999")
    private Double mktprice;

    @ApiModelProperty(name = "weight", value = "Weight", required = true)
    @NotNull(message = "The weight of goods cannot be empty")
    @Min(value = 0, message = "The weight cannot be negative")
    @Max(value = 99999999, message = "Weight shall not exceed99999999")
    private Double weight;

    @ApiModelProperty(name = "goods_transfee_charge", value = "Who bears the freight0：The buyer bears,1：The seller bear", required = true)
    @NotNull(message = "Bear freight cannot be empty")
    @Min(value = 0, message = "The borne freight value is incorrect")
    @Max(value = 1, message = "The borne freight value is incorrect")
    private Integer goodsTransfeeCharge;

    @ApiModelProperty(name = "intro", value = "details", required = false)
    private String intro;

    @ApiModelProperty(name = "have_spec", value = "Are there specifications?0There is no1There are", hidden = true)
    private Integer haveSpec;

    @ApiModelProperty(name = "quantity", value = "Inventory", required = false)
    @Max(value = 99999999, message = "Stock cannot exceed99999999")
    private Integer quantity;

    @ApiModelProperty(name = "market_enable", value = "Whether its on the shelf,1save0off", required = true)
    @Min(value = 0, message = "Whether the mounting value is incorrect")
    @Max(value = 1, message = "Whether the mounting value is incorrect")
    private Integer marketEnable;

    @ApiModelProperty(name = "sku_list", value = "skuThe list of", required = false)
    @Valid
    private List<GoodsSkuVO> skuList;

    @ApiModelProperty(name = "goods_params_list", value = "Product parameters", required = false)
    @Valid
    private List<GoodsParamsDO> goodsParamsList;

    @ApiModelProperty(name = "goods_gallery_list", value = "Photo album", required = true)
    @NotNull(message = "Product album picture cannot be empty")
    @Size(min = 1, message = "Product album picture cannot be empty")
    private List<GoodsGalleryDO> goodsGalleryList;

    @ApiModelProperty(name = "exchange", value = "Points exchange object, not points exchange goods can not pass", required = false)
    private ExchangeVO exchange;

    @ApiModelProperty(name = "has_changed", value = "skuData change or combination change judgment0:No change,1：change", required = true)
    private Integer hasChanged;

    @ApiModelProperty(name = "page_title", value = "seo title", required = false)
    private String pageTitle;

    @ApiModelProperty(name = "meta_keywords", value = "seokeyword", required = false)
    private String metaKeywords;

    @ApiModelProperty(name = "meta_description", value = "seo describe", required = false)
    private String metaDescription;

    @ApiModelProperty(value = "Number of reviews for items", hidden = true)
    private Integer commentNum;

    @ApiModelProperty(name = "template_id", value = "The freight templateid,不需要The freight template时值是0", required = true)
    @NotNull(message = "Freight template cannot be empty. If there is no freight template, pass the value0")
    @Min(value = 0, message = "The freight template value is incorrect")
    private Integer templateId;

    @ApiModelProperty(value = "Available", hidden = true)
    private Integer enableQuantity;

    public GoodsDTO() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public List<GoodsSkuVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<GoodsSkuVO> skuList) {
        this.skuList = skuList;
    }

    public List<GoodsParamsDO> getGoodsParamsList() {
        return goodsParamsList;
    }

    public void setGoodsParamsList(List<GoodsParamsDO> goodsParamsList) {
        this.goodsParamsList = goodsParamsList;
    }

    public List<GoodsGalleryDO> getGoodsGalleryList() {
        return goodsGalleryList;
    }

    public void setGoodsGalleryList(List<GoodsGalleryDO> goodsGalleryList) {
        this.goodsGalleryList = goodsGalleryList;
    }

    public Integer getHasChanged() {
        return hasChanged;
    }

    public void setHasChanged(Integer hasChanged) {
        this.hasChanged = hasChanged;
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

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ExchangeVO getExchange() {
        return exchange;
    }

    public void setExchange(ExchangeVO exchange) {
        this.exchange = exchange;
    }

    @Override
    public String toString() {
        return "GoodsDTO{" +
                "goodsId=" + goodsId +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
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
                ", skuList=" + skuList +
                ", goodsParamsList=" + goodsParamsList +
                ", goodsGalleryList=" + goodsGalleryList +
                ", hasChanged=" + hasChanged +
                ", pageTitle='" + pageTitle + '\'' +
                ", metaKeywords='" + metaKeywords + '\'' +
                ", metaDescription='" + metaDescription + '\'' +
                ", commentNum=" + commentNum +
                ", templateId=" + templateId +
                ", enableQuantity=" + enableQuantity +
                '}';
    }
}
