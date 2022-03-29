/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
 */
package dev.shopflix.core.goods.model.dos;

import dev.shopflix.core.goods.model.dto.GoodsDTO;
import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import dev.shopflix.framework.util.StringUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 商品实体
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
     * 主键
     */
    @Id(name = "goods_id")
    @ApiModelProperty(hidden = true)
    private Integer goodsId;
    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "商品名称", required = false)
    private String goodsName;
    /**
     * 商品编号
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "商品编号", required = false)
    private String sn;
    /**
     * 品牌id
     */
    @Column(name = "brand_id")
    @ApiModelProperty(name = "brand_id", value = "品牌id", required = false)
    private Integer brandId;
    /**
     * 分类id
     */
    @Column(name = "category_id")
    @ApiModelProperty(name = "category_id", value = "分类id", required = false)
    private Integer categoryId;
    /**
     * 商品类型normal普通point积分
     */
    @Column(name = "goods_type")
    @ApiModelProperty(name = "goods_type", value = "商品类型NORMAL普通POINT积分", required = false)
    private String goodsType;
    /**
     * 重量
     */
    @Column(name = "weight")
    @ApiModelProperty(name = "weight", value = "重量", required = false)
    private Double weight;
    /**
     * 上架状态 1上架 0下架
     */
    @Column(name = "market_enable")
    @ApiModelProperty(name = "market_enable", value = "上架状态 1上架  0下架", required = false)
    private Integer marketEnable;
    /**
     * 详情
     */
    @Column(name = "intro")
    @ApiModelProperty(name = "intro", value = "详情", required = false)
    private String intro;
    /**
     * 商品价格
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "商品价格", required = false)
    private Double price;
    /**
     * 成本价格
     */
    @Column(name = "cost")
    @ApiModelProperty(name = "cost", value = "成本价格", required = false)
    private Double cost;
    /**
     * 市场价格
     */
    @Column(name = "mktprice")
    @ApiModelProperty(name = "mktprice", value = "市场价格", required = false)
    private Double mktprice;
    /**
     * 是否有规格0没有 1有
     */
    @Column(name = "have_spec")
    @ApiModelProperty(name = "have_spec", value = "是否有规格0没有 1有", required = false)
    private Integer haveSpec;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "创建时间", required = false)
    private Long createTime;
    /**
     * 最后修改时间
     */
    @Column(name = "last_modify")
    @ApiModelProperty(name = "last_modify", value = "最后修改时间", required = false)
    private Long lastModify;
    /**
     * 浏览数量
     */
    @Column(name = "view_count")
    @ApiModelProperty(name = "view_count", value = "浏览数量", required = false)
    private Integer viewCount;
    /**
     * 购买数量
     */
    @Column(name = "buy_count")
    @ApiModelProperty(name = "buy_count", value = "购买数量", required = false)
    private Integer buyCount;
    /**
     * 是否被删除0 删除 1未删除
     */
    @Column(name = "disabled")
    @ApiModelProperty(name = "disabled", value = "是否被删除0 删除 1未删除", required = false)
    private Integer disabled;
    /**
     * 库存
     */
    @Column(name = "quantity")
    @ApiModelProperty(name = "quantity", value = "库存", required = false)
    private Integer quantity;
    /**
     * 可用库存
     */
    @Column(name = "enable_quantity")
    @ApiModelProperty(name = "enable_quantity", value = "可用库存", required = false)
    private Integer enableQuantity;
    /**
     * 如果是积分商品需要使用的积分
     */
    @Column(name = "point")
    @ApiModelProperty(name = "point", value = "如果是积分商品需要使用的积分", required = false)
    private Integer point;
    /**
     * seo标题
     */
    @Column(name = "page_title")
    @ApiModelProperty(name = "page_title", value = "seo标题", required = false)
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
     * 商品好评率
     */
    @Column(name = "grade")
    @ApiModelProperty(name = "grade", value = "商品好评率", required = false)
    private Double grade;
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
    /**
     * 评论数量
     */
    @Column(name = "comment_num")
    @ApiModelProperty(name = "comment_num", value = "评论数量", required = false)
    private Integer commentNum;
    /**
     * 运费模板id
     */
    @Column(name = "template_id")
    @ApiModelProperty(name = "template_id", value = "运费模板id", required = false)
    private Integer templateId;
    /**
     * 谁承担运费0：买家承担，1：卖家承担
     */
    @Column(name = "goods_transfee_charge")
    @ApiModelProperty(name = "goods_transfee_charge", value = "谁承担运费0：买家承担，1：卖家承担", required = false)
    private Integer goodsTransfeeCharge;

    /**
     * 下架原因
     */
    @Column(name = "under_message")
    @ApiModelProperty(name = "under_message", value = "下架原因", required = false)
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