/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.sdk.model;

import dev.shopflix.core.goods.model.vo.SpecValueVO;
import dev.shopflix.core.trade.order.model.vo.GoodsOperateAllowable;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;

/**
 * 订单货品项DTO
 *
 * @author Snow create in 2018/5/28
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel(description = "订单货品项DTO")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderSkuDTO {

    @ApiModelProperty(value = "卖家id")
    private Integer sellerId;

    @ApiModelProperty(value = "卖家姓名")
    private String sellerName;

    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "产品id")
    private Integer skuId;

    @ApiModelProperty(value = "产品sn")
    private String skuSn;

    @ApiModelProperty(value = "商品所属的分类id")
    private Integer catId;

    @ApiModelProperty(value = "购买数量")
    private Integer num;

    @ApiModelProperty(value = "商品重量")
    private Double goodsWeight;

    @ApiModelProperty(value = "商品原价")
    private Double originalPrice;

    @ApiModelProperty(value = "购买时的成交价")
    private Double purchasePrice;

    @ApiModelProperty(value = "小计")
    private Double subtotal;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品图片")
    private String goodsImage;

    /**
     * 商品规格列表
     */
    @ApiModelProperty(value = "规格列表")
    private List<SpecValueVO> specList;

    /**
     * 积分换购活动中，购买这个商品需要消费的积分。
     */
    @ApiModelProperty(value = "使用积分")
    private Integer point;

    @ApiModelProperty(value = "快照ID")
    private Integer snapshotId;

    @ApiModelProperty(value = "售后状态")
    private String serviceStatus;

    @ApiModelProperty(value = "订单操作允许情况")
    private GoodsOperateAllowable goodsOperateAllowableVO;

    @ApiModelProperty(value = "此商品需要提示给顾客的优惠标签")
    private List<String> promotionTags;

    public List<String> getPromotionTags() {
        return promotionTags;
    }

    public void setPromotionTags(List<String> promotionTags) {
        this.promotionTags = promotionTags;
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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getSkuSn() {
        return skuSn;
    }

    public void setSkuSn(String skuSn) {
        this.skuSn = skuSn;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(Double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public List<SpecValueVO> getSpecList() {
        return specList;
    }

    public void setSpecList(List<SpecValueVO> specList) {
        this.specList = specList;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(Integer snapshotId) {
        this.snapshotId = snapshotId;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public GoodsOperateAllowable getGoodsOperateAllowableVO() {
        return goodsOperateAllowableVO;
    }

    public void setGoodsOperateAllowableVO(GoodsOperateAllowable goodsOperateAllowableVO) {
        this.goodsOperateAllowableVO = goodsOperateAllowableVO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderSkuDTO that = (OrderSkuDTO) o;

        return new EqualsBuilder()
                .append(sellerId, that.sellerId)
                .append(sellerName, that.sellerName)
                .append(goodsId, that.goodsId)
                .append(skuId, that.skuId)
                .append(skuSn, that.skuSn)
                .append(catId, that.catId)
                .append(num, that.num)
                .append(goodsWeight, that.goodsWeight)
                .append(originalPrice, that.originalPrice)
                .append(purchasePrice, that.purchasePrice)
                .append(subtotal, that.subtotal)
                .append(name, that.name)
                .append(goodsImage, that.goodsImage)
                .append(specList, that.specList)
                .append(point, that.point)
                .append(snapshotId, that.snapshotId)
                .append(serviceStatus, that.serviceStatus)
                .append(goodsOperateAllowableVO, that.goodsOperateAllowableVO)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(sellerId)
                .append(sellerName)
                .append(goodsId)
                .append(skuId)
                .append(skuSn)
                .append(catId)
                .append(num)
                .append(goodsWeight)
                .append(originalPrice)
                .append(purchasePrice)
                .append(subtotal)
                .append(name)
                .append(goodsImage)
                .append(specList)
                .append(point)
                .append(snapshotId)
                .append(serviceStatus)
                .append(goodsOperateAllowableVO)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderSkuDTO{" +
                "sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", goodsId=" + goodsId +
                ", skuId=" + skuId +
                ", skuSn='" + skuSn + '\'' +
                ", catId=" + catId +
                ", num=" + num +
                ", goodsWeight=" + goodsWeight +
                ", originalPrice=" + originalPrice +
                ", purchasePrice=" + purchasePrice +
                ", subtotal=" + subtotal +
                ", name='" + name + '\'' +
                ", goodsImage='" + goodsImage + '\'' +
                ", specList=" + specList +
                ", point=" + point +
                ", snapshotId=" + snapshotId +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", goodsOperateAllowableVO=" + goodsOperateAllowableVO +
                '}';
    }
}
