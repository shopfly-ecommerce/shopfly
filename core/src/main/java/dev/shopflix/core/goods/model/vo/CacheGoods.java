/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.model.vo;

import dev.shopflix.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 缓存商品对象
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月29日 上午11:50:02
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CacheGoods implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3642358108471082387L;
    @ApiModelProperty(name = "goods_id", value = "商品id")
    @Column(name = "goods_id")
    private Integer goodsId;

    @ApiModelProperty(name = "category_id", value = "分类id")
    private Integer categoryId;

    @ApiModelProperty(name = "goods_name", value = "商品名称")
    @Column(name = "goods_name")
    private String goodsName;

    @ApiModelProperty(name = "sn", value = "商品编号")
    @Column(name = "sn")
    private String sn;

    @ApiModelProperty(name = "price", value = "商品价格")
    @Column(name = "price")
    private Double price;

    @ApiModelProperty(name = "weight", value = "重量")
    @Column(name = "weight")
    private Double weight;

    @ApiModelProperty(name = "intro", value = "详情")
    private String intro;

    @ApiModelProperty(name = "goods_transfee_charge", value = "谁承担运费0：买家承担，1：卖家承担")
    @Column(name = "goods_transfee_charge")
    private Integer goodsTransfeeCharge;

    @ApiModelProperty(name = "template_id", value = "运费模板id,不需要运费模板时值是0")
    @Column(name = "template_id")
    private Integer templateId;

    @ApiModelProperty(name = "market_enable", value = "是否上架，1上架 0下架")
    @Column(name = "market_enable")
    private Integer marketEnable;

    @ApiModelProperty(name = "disabled", value = "是否放入回收站 0 删除 1未删除")
    @Column(name = "disabled")
    private Integer disabled;

    @ApiModelProperty(value = "可用库存")
    @Column(name = "enable_quantity")
    private Integer enableQuantity;

    @ApiModelProperty(name = "quantity", value = "库存")
    private Integer quantity;

    @ApiModelProperty(name = "sku_list", value = "sku列表")
    private List<GoodsSkuVO> skuList;

    @ApiModelProperty(name = "thumbnail", value = "商品缩略图")
    private String thumbnail;

    @ApiModelProperty(name = "last_modify", value = "商品最后修改时间")
    private Long lastModify;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public List<GoodsSkuVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<GoodsSkuVO> skuList) {
        this.skuList = skuList;
    }

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Long getLastModify() {
        return lastModify;
    }

    public void setLastModify(Long lastModify) {
        this.lastModify = lastModify;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CacheGoods that = (CacheGoods) o;

        return new EqualsBuilder()
                .append(goodsId, that.goodsId)
                .append(categoryId, that.categoryId)
                .append(goodsName, that.goodsName)
                .append(sn, that.sn)
                .append(price, that.price)
                .append(weight, that.weight)
                .append(intro, that.intro)
                .append(goodsTransfeeCharge, that.goodsTransfeeCharge)
                .append(templateId, that.templateId)
                .append(marketEnable, that.marketEnable)
                .append(disabled, that.disabled)
                .append(enableQuantity, that.enableQuantity)
                .append(quantity, that.quantity)
//                .append(skuList, that.skuList)
                .append(thumbnail, that.thumbnail)
                .append(lastModify, that.lastModify)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(goodsId)
                .append(categoryId)
                .append(goodsName)
                .append(sn)
                .append(price)
                .append(weight)
                .append(intro)
                .append(goodsTransfeeCharge)
                .append(templateId)
                .append(marketEnable)
                .append(disabled)
                .append(enableQuantity)
                .append(quantity)
                //.append(skuList)
                .append(thumbnail)
                .append(lastModify)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "CacheGoods{" +
                "goodsId=" + goodsId +
                ", categoryId=" + categoryId +
                ", goodsName='" + goodsName + '\'' +
                ", sn='" + sn + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", intro='" + intro + '\'' +
                ", goodsTransfeeCharge=" + goodsTransfeeCharge +
                ", templateId=" + templateId +
                ", marketEnable=" + marketEnable +
                ", disabled=" + disabled +
                ", enableQuantity=" + enableQuantity +
                ", quantity=" + quantity +
                ", skuList=" + skuList +
                ", thumbnail='" + thumbnail + '\'' +
                ", lastModify=" + lastModify +
                '}';
    }
}
