/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.model.vo;

import dev.shopflix.core.goods.model.dos.GoodsGalleryDO;
import dev.shopflix.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 后台首页商品vo
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/7/6 上午12:07
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BackendGoodsVO implements Serializable {

    private static final long serialVersionUID = 3922135264669953741L;
    @ApiModelProperty(value = "id")
    private Integer goodsId;

    @ApiModelProperty(name = "category_id", value = "分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(name = "shop_cat_id", value = "店铺分类id")
    private Integer shopCatId;

    @ApiModelProperty(name = "brand_id", value = "品牌id")
    private Integer brandId;

    @ApiModelProperty(name = "goods_name", value = "商品名称")
    private String goodsName;

    @ApiModelProperty(name = "sn", value = "商品编号")
    private String sn;

    @ApiModelProperty(name = "price", value = "商品价格")
    private Double price;

    @ApiModelProperty(name = "cost", value = "成本价格")
    private Double cost;

    @ApiModelProperty(name = "mktprice", value = "市场价格")
    private Double mktprice;

    @ApiModelProperty(name = "weight", value = "重量")
    private Double weight;

    @ApiModelProperty(name = "goods_transfee_charge", value = "谁承担运费0：买家承担，1：卖家承担")
    private Integer goodsTransfeeCharge;

    @ApiModelProperty(name = "intro", value = "详情")
    private String intro;

    @ApiModelProperty(name = "have_spec", value = "是否有规格0没有1有")
    private Integer haveSpec;

    @ApiModelProperty(name = "quantity", value = "库存")
    private Integer quantity;

    @ApiModelProperty(name = "market_enable", value = "是否上架，1上架 0下架")
    private Integer marketEnable;

    @ApiModelProperty(name = "goods_gallery_list", value = "商品相册")
    private List<GoodsGalleryDO> goodsGalleryList;

    @ApiModelProperty(name = "page_title", value = "seo标题", required = false)
    private String pageTitle;

    @ApiModelProperty(name = "meta_keywords", value = "seo关键字", required = false)
    private String metaKeywords;

    @ApiModelProperty(name = "meta_description", value = "seo描述", required = false)
    private String metaDescription;

    @ApiModelProperty(name = "template_id", value = "运费模板id,不需要运费模板时值是0")
    private Integer templateId;

    @ApiModelProperty(value = "可用库存")
    private Integer enableQuantity;

    /**
     * 缩略图路径
     */
    @Column(name = "thumbnail")
    @ApiModelProperty(name = "thumbnail", value = "缩略图路径", required = false)
    private String thumbnail;
    /**
     * 大图路径
     */
    @Column(name = "big")
    @ApiModelProperty(name = "big", value = "大图路径", required = false)
    private String big;
    /**
     * 小图路径
     */
    @Column(name = "small")
    @ApiModelProperty(name = "small", value = "小图路径", required = false)
    private String small;
    /**
     * 原图路径
     */
    @Column(name = "original")
    @ApiModelProperty(name = "original", value = "原图路径", required = false)
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


