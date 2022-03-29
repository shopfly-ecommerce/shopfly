/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goods.model.dos;

import com.enation.app.javashop.core.goods.model.dto.GoodsDTO;
import com.enation.app.javashop.core.goods.model.enums.GoodsType;
import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.enation.app.javashop.framework.util.JsonUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jodd.util.StringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 草稿商品实体
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
     * 草稿商品id
     */
    @Id(name = "draft_goods_id")
    @ApiModelProperty(hidden = true)
    private Integer draftGoodsId;
    /**
     * 商品编号
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "商品编号", required = false)
    private String sn;
    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "商品名称", required = false)
    private String goodsName;
    /**
     * 商品品牌ID
     */
    @Column(name = "brand_id")
    @ApiModelProperty(name = "brand_id", value = "商品品牌ID", required = false)
    private Integer brandId;
    /**
     * 商品分类ID
     */
    @Column(name = "category_id")
    @ApiModelProperty(name = "category_id", value = "商品分类ID", required = false)
    private Integer categoryId;
    /**
     * 商品重量
     */
    @Column(name = "weight")
    @ApiModelProperty(name = "weight", value = "商品重量", required = false)
    private Double weight;
    /**
     * 商品详情
     */
    @Column(name = "intro")
    @ApiModelProperty(name = "intro", value = "商品详情", required = false)
    private String intro;
    /**
     * 商品价格
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "商品价格", required = false)
    private Double price;
    /**
     * 商品成本价
     */
    @Column(name = "cost")
    @ApiModelProperty(name = "cost", value = "商品成本价", required = false)
    private Double cost;
    /**
     * 商品市场价
     */
    @Column(name = "mktprice")
    @ApiModelProperty(name = "mktprice", value = "商品市场价", required = false)
    private Double mktprice;
    /**
     * 商品类型
     */
    @Column(name = "goods_type")
    @ApiModelProperty(name = "goods_type", value = "商品类型", required = false)
    private String goodsType;
    /**
     * 是否开启规格
     */
    @Column(name = "have_spec")
    @ApiModelProperty(name = "have_spec", value = "是否开启规格", required = false)
    private Integer haveSpec;
    /**
     * 商品添加时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "商品添加时间", required = false)
    private Long createTime;
    /**
     * 商品总库存
     */
    @Column(name = "quantity")
    @ApiModelProperty(name = "quantity", value = "商品总库存", required = false)
    private Integer quantity;
    /**
     * 商品原始图片
     */
    @Column(name = "original")
    @ApiModelProperty(name = "original", value = "商品原始图片", required = false)
    private String original;

    /**
     * 商品运费模板ID
     */
    @Column(name = "template_id")
    @ApiModelProperty(name = "template_id", value = "商品运费模板ID", required = false)
    private Integer templateId;
    /**
     * 是否为买家承担运费
     */
    @Column(name = "goods_transfee_charge")
    @ApiModelProperty(name = "goods_transfee_charge", value = "是否为买家承担运费", required = false)
    private Integer goodsTransfeeCharge;

    /**
     * seo 标题
     */
    @Column(name = "page_title")
    @ApiModelProperty(name = "page_title", value = "seo 标题", required = false)
    private String pageTitle;
    /**
     * seo关键字
     */
    @Column(name = "meta_keywords")
    @ApiModelProperty(name = "meta_keywords", value = "seo关键字", required = false)
    private String metaKeywords;
    /**
     * seo描述
     */
    @Column(name = "meta_description")
    @ApiModelProperty(name = "meta_description", value = "seo描述", required = false)
    private String metaDescription;
    /**
     * 积分商品需要的金额
     */
    @Column(name = "exchange_money")
    @ApiModelProperty(name = "exchange_money", value = "积分商品需要的金额", required = false)
    private Double exchangeMoney;
    /**
     * 积分商品需要的积分
     */
    @Column(name = "exchange_point")
    @ApiModelProperty(name = "exchange_point", value = "积分商品需要的积分", required = false)
    private Integer exchangePoint;
    /**
     * 积分商品的分类id
     */
    @Column(name = "exchange_category_id")
    @ApiModelProperty(name = "exchange_category_id", value = "积分商品的分类id", required = false)
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