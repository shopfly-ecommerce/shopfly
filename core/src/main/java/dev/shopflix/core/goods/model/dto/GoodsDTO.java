/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.model.dto;

import dev.shopflix.core.goods.model.dos.GoodsGalleryDO;
import dev.shopflix.core.goods.model.dos.GoodsParamsDO;
import dev.shopflix.core.goods.model.vo.GoodsSkuVO;
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
 * 商品vo
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月21日 上午11:25:10
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsDTO implements Serializable {

    private static final long serialVersionUID = 3922135264669953741L;
    @ApiModelProperty(hidden = true)
    private Integer goodsId;

    @ApiModelProperty(name = "category_id", value = "分类id", required = true)
    @NotNull(message = "商城分类不能为空")
    @Min(value = 0, message = "商城分类值不正确")
    private Integer categoryId;

    /**
     * 用作显示
     */
    @ApiModelProperty(name = "category_name", value = "分类名称", required = true)
    private String categoryName;

    @ApiModelProperty(name = "brand_id", value = "品牌id", required = false)
    @Min(value = 0, message = "品牌值不正确")
    private Integer brandId;

    @ApiModelProperty(name = "goods_name", value = "商品名称", required = true)
    @NotEmpty(message = "商品名称不能为空")
    private String goodsName;

    @ApiModelProperty(name = "sn", value = "商品编号", required = true)
    @Length(max = 30, message = "商品编号太长，不能超过30个字符")
    private String sn;

    @ApiModelProperty(name = "price", value = "商品价格", required = true)
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0, message = "商品价格不能为负数")
    @Max(value = 99999999, message = "商品价格不能超过99999999")
    private Double price;

    @ApiModelProperty(name = "cost", value = "成本价格", required = true)
    @NotNull(message = "成本价格不能为空")
    @Min(value = 0, message = "成本价格不能为负数")
    @Max(value = 99999999, message = "成本价格不能超过99999999")
    private Double cost;

    @ApiModelProperty(name = "mktprice", value = "市场价格", required = true)
    @NotNull(message = "市场价格不能为空")
    @Min(value = 0, message = "市场价格不能为负数")
    @Max(value = 99999999, message = "市场价格不能超过99999999")
    private Double mktprice;

    @ApiModelProperty(name = "weight", value = "重量", required = true)
    @NotNull(message = "商品重量不能为空")
    @Min(value = 0, message = "重量不能为负数")
    @Max(value = 99999999, message = "重量不能超过99999999")
    private Double weight;

    @ApiModelProperty(name = "goods_transfee_charge", value = "谁承担运费0：买家承担，1：卖家承担", required = true)
    @NotNull(message = "承担运费不能为空")
    @Min(value = 0, message = "承担运费值不正确")
    @Max(value = 1, message = "承担运费值不正确")
    private Integer goodsTransfeeCharge;

    @ApiModelProperty(name = "intro", value = "详情", required = false)
    private String intro;

    @ApiModelProperty(name = "have_spec", value = "是否有规格0没有1有", hidden = true)
    private Integer haveSpec;

    @ApiModelProperty(name = "quantity", value = "库存", required = false)
    @Max(value = 99999999, message = "库存不能超过99999999")
    private Integer quantity;

    @ApiModelProperty(name = "market_enable", value = "是否上架，1上架 0下架", required = true)
    @Min(value = 0, message = "是否上架值不正确")
    @Max(value = 1, message = "是否上架值不正确")
    private Integer marketEnable;

    @ApiModelProperty(name = "sku_list", value = "sku列表", required = false)
    @Valid
    private List<GoodsSkuVO> skuList;

    @ApiModelProperty(name = "goods_params_list", value = "商品参数", required = false)
    @Valid
    private List<GoodsParamsDO> goodsParamsList;

    @ApiModelProperty(name = "goods_gallery_list", value = "商品相册", required = true)
    @NotNull(message = "商品相册图片不能为空")
    @Size(min = 1, message = "商品相册图片不能为空")
    private List<GoodsGalleryDO> goodsGalleryList;

    @ApiModelProperty(name = "exchange", value = "积分兑换对象，不是积分兑换商品可以不传", required = false)
    private ExchangeVO exchange;

    @ApiModelProperty(name = "has_changed", value = "sku数据变化或者组合变化判断 0:没变化，1：变化", required = true)
    private Integer hasChanged;

    @ApiModelProperty(name = "page_title", value = "seo标题", required = false)
    private String pageTitle;

    @ApiModelProperty(name = "meta_keywords", value = "seo关键字", required = false)
    private String metaKeywords;

    @ApiModelProperty(name = "meta_description", value = "seo描述", required = false)
    private String metaDescription;

    @ApiModelProperty(value = "商品的评论数量", hidden = true)
    private Integer commentNum;

    @ApiModelProperty(name = "template_id", value = "运费模板id,不需要运费模板时值是0", required = true)
    @NotNull(message = "运费模板不能为空，没有运费模板时，传值0")
    @Min(value = 0, message = "运费模板值不正确")
    private Integer templateId;

    @ApiModelProperty(value = "可用库存", hidden = true)
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
