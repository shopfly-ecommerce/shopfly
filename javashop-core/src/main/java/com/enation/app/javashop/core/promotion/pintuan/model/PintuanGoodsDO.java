/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.pintuan.model;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.validation.constraints.Min;


/**
 * 拼团商品实体
 *
 * @author admin
 * @version vv1.0.0
 * @since vv7.1.0
 * 2019-01-22 11:20:56
 */
@Table(name = "es_pintuan_goods")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PintuanGoodsDO {

    /**
     * id
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * sku_id
     */
    @Column(name = "sku_id")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "sku_id", value = "sku_id")
    private Integer skuId;

    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "goods_id")
    private Integer goodsId;


    @Column(name = "seller_id")
    @ApiModelProperty(name = "seller_id", value = "卖家id")
    private Integer sellerId;

    /**
     * 卖家名称
     */
    @Column(name = "seller_name")
    @ApiModelProperty(name = "seller_name", value = "卖家名称", hidden = true)
    private String sellerName;


    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "商品名称", required = true)
    private String goodsName;
    /**
     * 原价
     */
    @Column(name = "origin_price")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "origin_price", value = "原价")
    private Double originPrice;
    /**
     * 活动价
     */
    @Column(name = "sales_price")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "sales_price", value = "活动价")
    private Double salesPrice;
    /**
     * sn
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "sn", required = true)
    private String sn;
    /**
     * 已售数量
     */
    @Column(name = "sold_quantity")
    @ApiModelProperty(name = "sold_quantity", value = "已售数量")
    private Integer soldQuantity;
    /**
     * 待发货数量
     */
    @Column(name = "locked_quantity")
    @ApiModelProperty(name = "locked_quantity", value = "待发货数量")
    private Integer lockedQuantity;
    /**
     * 拼团活动id
     */
    @Column(name = "pintuan_id")
    @ApiModelProperty(name = "pintuan_id", value = "拼团活动id")
    private Integer pintuanId;


    @Column(name = "specs")
    @ApiModelProperty(name = "specs", value = "规格信息json")
    @JsonRawValue
    private String specs;

    @Column(name = "thumbnail")
    @ApiModelProperty(name = "thumbnail", value = "商品图片")
    private String thumbnail;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getPrice() {
        return originPrice;
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public Integer getLockedQuantity() {
        return lockedQuantity;
    }

    public void setLockedQuantity(Integer lockedQuantity) {
        this.lockedQuantity = lockedQuantity;
    }

    public Integer getPintuanId() {
        return pintuanId;
    }

    public void setPintuanId(Integer pintuanId) {
        this.pintuanId = pintuanId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PintuanGoodsDO that = (PintuanGoodsDO) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(skuId, that.skuId)
                .append(goodsId, that.goodsId)
                .append(goodsName, that.goodsName)
                .append(originPrice, that.originPrice)
                .append(salesPrice, that.salesPrice)
                .append(sn, that.sn)
                .append(soldQuantity, that.soldQuantity)
                .append(lockedQuantity, that.lockedQuantity)
                .append(pintuanId, that.pintuanId)
                .append(sellerId, that.sellerId)
                .append(sellerName, that.sellerName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(skuId)
                .append(goodsId)
                .append(goodsName)
                .append(originPrice)
                .append(salesPrice)
                .append(sn)
                .append(soldQuantity)
                .append(lockedQuantity)
                .append(pintuanId)
                .append(sellerId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "PintuanGoodsDO{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", originPrice=" + originPrice +
                ", salesPrice=" + salesPrice +
                ", sn='" + sn + '\'' +
                ", soldQuantity=" + soldQuantity +
                ", lockedQuantity=" + lockedQuantity +
                ", pintuanId=" + pintuanId +
                ", sellerId=" + sellerId +
                ", sellerName=" + sellerName +
                '}';
    }


}