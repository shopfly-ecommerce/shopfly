/*
 * Yi family of hui（Beijing）All Rights Reserved.
 * You may not use this file without permission.
 * The official address：www.javamall.com.cn
 */
package cloud.shopfly.b2c.core.goods.model.dos;

import cloud.shopfly.b2c.core.goods.model.dto.GoodsDTO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Commodity entity
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:23:10
 */
@Table(name = "es_goods")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsDO implements Serializable {

    private static final long serialVersionUID = 9115135430405642L;

    /**
     * A primary key
     */
    @Id(name = "goods_id")
    @ApiModelProperty(hidden = true)
    private Integer goodsId;
    /**
     * Name
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "Name", required = false)
    private String goodsName;
    /**
     * SN
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "SN", required = false)
    private String sn;
    /**
     * brandid
     */
    @Column(name = "brand_id")
    @ApiModelProperty(name = "brand_id", value = "brandid", required = false)
    private Integer brandId;
    /**
     * Categoriesid
     */
    @Column(name = "category_id")
    @ApiModelProperty(name = "category_id", value = "Categoriesid", required = false)
    private Integer categoryId;
    /**
     * Typenormalordinarypointpoint
     */
    @Column(name = "goods_type")
    @ApiModelProperty(name = "goods_type", value = "TypeNORMALordinaryPOINTpoint", required = false)
    private String goodsType;
    /**
     * Weight
     */
    @Column(name = "weight")
    @ApiModelProperty(name = "weight", value = "Weight", required = false)
    private Double weight;
    /**
     * On state1save0off
     */
    @Column(name = "market_enable")
    @ApiModelProperty(name = "market_enable", value = "On state1save0off", required = false)
    private Integer marketEnable;
    /**
     * details
     */
    @Column(name = "intro")
    @ApiModelProperty(name = "intro", value = "details", required = false)
    private String intro;
    /**
     * Price
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "Price", required = false)
    private Double price;
    /**
     * Cost price
     */
    @Column(name = "cost")
    @ApiModelProperty(name = "cost", value = "Cost price", required = false)
    private Double cost;
    /**
     * Marking price
     */
    @Column(name = "mktprice")
    @ApiModelProperty(name = "mktprice", value = "Marking price", required = false)
    private Double mktprice;
    /**
     * Are there specifications?0There is no1There are
     */
    @Column(name = "have_spec")
    @ApiModelProperty(name = "have_spec", value = "Are there specifications?0There is no1There are", required = false)
    private Integer haveSpec;
    /**
     * Last update
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "Last update", required = false)
    private Long createTime;
    /**
     * Last Modified time
     */
    @Column(name = "last_modify")
    @ApiModelProperty(name = "last_modify", value = "Last Modified time", required = false)
    private Long lastModify;
    /**
     * Browse the number
     */
    @Column(name = "view_count")
    @ApiModelProperty(name = "view_count", value = "Browse the number", required = false)
    private Integer viewCount;
    /**
     * Purchase quantity
     */
    @Column(name = "buy_count")
    @ApiModelProperty(name = "buy_count", value = "Purchase quantity", required = false)
    private Integer buyCount;
    /**
     * Deleted or not0 delete1未delete
     */
    @Column(name = "disabled")
    @ApiModelProperty(name = "disabled", value = "Deleted or not0 delete1未delete", required = false)
    private Integer disabled;
    /**
     * Inventory
     */
    @Column(name = "quantity")
    @ApiModelProperty(name = "quantity", value = "Inventory", required = false)
    private Integer quantity;
    /**
     * Available
     */
    @Column(name = "enable_quantity")
    @ApiModelProperty(name = "enable_quantity", value = "Available", required = false)
    private Integer enableQuantity;
    /**
     * If its a points product, you need to use points
     */
    @Column(name = "point")
    @ApiModelProperty(name = "point", value = "If its a points product, you need to use points", required = false)
    private Integer point;
    /**
     * seo title
     */
    @Column(name = "page_title")
    @ApiModelProperty(name = "page_title", value = "seo title", required = false)
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
     * Product praise rate
     */
    @Column(name = "grade")
    @ApiModelProperty(name = "grade", value = "Product praise rate", required = false)
    private Double grade;
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
    /**
     * Comment number
     */
    @Column(name = "comment_num")
    @ApiModelProperty(name = "comment_num", value = "Comment number", required = false)
    private Integer commentNum;
    /**
     * The freight templateid
     */
    @Column(name = "template_id")
    @ApiModelProperty(name = "template_id", value = "The freight templateid", required = false)
    private Integer templateId;
    /**
     * Who bears the freight0：The buyer bears,1：The seller bear
     */
    @Column(name = "goods_transfee_charge")
    @ApiModelProperty(name = "goods_transfee_charge", value = "Who bears the freight0：The buyer bears,1：The seller bear", required = false)
    private Integer goodsTransfeeCharge;

    /**
     * From the shelves because
     */
    @Column(name = "under_message")
    @ApiModelProperty(name = "under_message", value = "From the shelves because", required = false)
    private String underMessage;


    public GoodsDO() {
    }


    public GoodsDO(GoodsDTO goodsVO) {

        this.goodsId = goodsVO.getGoodsId();
        this.categoryId = goodsVO.getCategoryId();
        this.brandId = goodsVO.getBrandId() == null ? 0 : goodsVO.getBrandId();
        this.goodsName = goodsVO.getGoodsName();
        this.sn = goodsVO.getSn();
        this.price = goodsVO.getPrice();
        this.cost = goodsVO.getCost();
        this.mktprice = goodsVO.getMktprice();
        this.weight = goodsVO.getWeight();
        this.goodsTransfeeCharge = goodsVO.getGoodsTransfeeCharge();
        this.intro = goodsVO.getIntro();
        this.haveSpec = goodsVO.getHaveSpec();
        this.templateId = goodsVO.getTemplateId();
        this.pageTitle = goodsVO.getPageTitle();
        this.metaKeywords = goodsVO.getMetaKeywords();
        this.metaDescription = goodsVO.getMetaDescription();
        this.marketEnable = goodsVO.getMarketEnable() != 1 ? 0 : 1;

        if (StringUtil.isEmpty(this.pageTitle)) {
            this.pageTitle = goodsVO.getGoodsName();
        }
        if (StringUtil.isEmpty(this.metaKeywords)) {
            this.metaKeywords = goodsVO.getGoodsName();
        }
        if (StringUtil.isEmpty(this.metaDescription)) {
            this.metaDescription = goodsVO.getGoodsName();
        }
    }


    @PrimaryKeyField
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

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getMarketEnable() {
        return marketEnable;
    }

    public void setMarketEnable(Integer marketEnable) {
        this.marketEnable = marketEnable;
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

    public Long getLastModify() {
        return lastModify;
    }

    public void setLastModify(Long lastModify) {
        this.lastModify = lastModify;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
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

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
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

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
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

    public Integer getGoodsTransfeeCharge() {
        return goodsTransfeeCharge;
    }

    public void setGoodsTransfeeCharge(Integer goodsTransfeeCharge) {
        this.goodsTransfeeCharge = goodsTransfeeCharge;
    }

    public String getUnderMessage() {
        return underMessage;
    }

    public void setUnderMessage(String underMessage) {
        this.underMessage = underMessage;
    }

    @Override
    public String toString() {
        return "GoodsDO [goodsId=" + goodsId + ", goodsName=" + goodsName + ", sn=" + sn + ", brandId=" + brandId
                + ", categoryId=" + categoryId + ", goodsType=" + goodsType + ", weight=" + weight + ", marketEnable="
                + marketEnable + ", intro=" + intro + ", price=" + price + ", cost=" + cost + ", mktprice=" + mktprice
                + ", haveSpec=" + haveSpec + ", createTime=" + createTime + ", lastModify=" + lastModify
                + ", viewCount=" + viewCount + ", buyCount=" + buyCount + ", disabled=" + disabled + ", quantity="
                + quantity + ", enableQuantity=" + enableQuantity + ", point=" + point + ", pageTitle=" + pageTitle
                + ", metaKeywords=" + metaKeywords + ", metaDescription=" + metaDescription + ", grade=" + grade
                + ", thumbnail=" + thumbnail + ", big=" + big + ", small=" + small + ", original=" + original
                + ", commentNum=" + commentNum + ", templateId="
                + templateId + ", goodsTransfeeCharge=" + goodsTransfeeCharge;
    }


}
