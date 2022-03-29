/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.pintuan.model;

import com.enation.app.javashop.framework.elasticsearch.EsSettings;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by kingapex on 2019-01-21.
 * 拼团商品在索引中的文档
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2019-01-21
 */
@Document(indexName = "#{esConfig.indexName}_" + EsSettings.PINTUAN_INDEX_NAME, type = EsSettings.PINTUAN_TYPE_NAME)
public class PtGoodsDoc {

    @Id
    private Integer skuId;

    @Field(type = FieldType.Integer)
    private Integer goodsId;

    @Field(type = FieldType.Integer)
    private Integer pinTuanId;

    @Field(type = FieldType.text, analyzer = "ik_max_word")
    private String goodsName;

    @Field(type = FieldType.text)
    private String thumbnail;

    @Field(type = FieldType.Integer)
    private Integer buyCount;

    @Field(type = FieldType.Double)
    private Double originPrice;

    @Field(type = FieldType.Double)
    private Double salesPrice;

    @Field(type = FieldType.Integer)
    private Integer categoryId;

    @Field(type = FieldType.text)
    private String categoryPath;

    @Field(type = FieldType.Boolean)
    private Boolean isEnableSale;


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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice) {
        this.originPrice = originPrice;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getPinTuanId() {
        return pinTuanId;
    }

    public void setPinTuanId(Integer pinTuanId) {
        this.pinTuanId = pinTuanId;
    }

    public Boolean getIsEnableSale() {
        return isEnableSale;
    }

    public void setIsEnableSale(Boolean enableSale) {
        isEnableSale = enableSale;
    }

    @Override
    public String toString() {
        return "PtGoodsDoc{" +
                "skuId=" + skuId +
                ", goodsId=" + goodsId +
                ", pinTuanId=" + pinTuanId +
                ", goodsName='" + goodsName + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", buyCount=" + buyCount +
                ", originPrice=" + originPrice +
                ", salesPrice=" + salesPrice +
                ", categoryId=" + categoryId +
                ", categoryPath='" + categoryPath + '\'' +
                ", isEnableSale=" + isEnableSale +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PtGoodsDoc that = (PtGoodsDoc) o;

        return new EqualsBuilder()
                .append(getSkuId(), that.getSkuId())
                .append(getGoodsId(), that.getGoodsId())
                .append(getPinTuanId(), that.getPinTuanId())
                .append(getGoodsName(), that.getGoodsName())
                .append(getThumbnail(), that.getThumbnail())
                .append(getBuyCount(), that.getBuyCount())
                .append(getOriginPrice(), that.getOriginPrice())
                .append(getSalesPrice(), that.getSalesPrice())
                .append(getCategoryId(), that.getCategoryId())
                .append(getIsEnableSale(), that.getIsEnableSale())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSkuId())
                .append(getGoodsId())
                .append(getPinTuanId())
                .append(getGoodsName())
                .append(getThumbnail())
                .append(getBuyCount())
                .append(getOriginPrice())
                .append(getSalesPrice())
                .append(getCategoryId())
                .append(getIsEnableSale())
                .toHashCode();
    }
}
