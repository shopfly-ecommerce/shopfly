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
package cloud.shopfly.b2c.core.trade.order.model.vo;

import cloud.shopfly.b2c.core.goods.model.vo.SpecValueVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartPromotionVo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;

/**
 * Order items
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel(description = "Order items")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderSkuVO {


    @ApiModelProperty(value = "The sellerid")
    private Integer sellerId;

    @ApiModelProperty(value = "The sellers name")
    private String sellerName;

    @ApiModelProperty(value = "productid")
    private Integer goodsId;

    @ApiModelProperty(value = "productid")
    private Integer skuId;

    @ApiModelProperty(value = "productsn")
    private String skuSn;

    @ApiModelProperty(value = "The category to which the goods belongid")
    private Integer catId;

    @ApiModelProperty(value = "Purchase quantity")
    private Integer num;

    @ApiModelProperty(value = "Weight")
    private Double goodsWeight;

    @ApiModelProperty(value = "Product  price")
    private Double originalPrice;

    @ApiModelProperty(value = "The purchase price at the time of purchase")
    private Double purchasePrice;

    @ApiModelProperty(value = "subtotal")
    private Double subtotal;

    @ApiModelProperty(value = "Name")
    private String name;

    @ApiModelProperty(value = "Commodity images")
    private String goodsImage;

    /**
     * List of specifications
     */
    @ApiModelProperty(value = "Specification list")
    private List<SpecValueVO> specList;

    /**
     * In the point exchange activity, the amount of points needed to purchase the product
     */
    @ApiModelProperty(value = "Using the integral")
    private Integer point;

    @ApiModelProperty(value = "The snapshotID")
    private Integer snapshotId;

    @ApiModelProperty(value = "After state")
    private String serviceStatus;

    @ApiModelProperty(value = "List of single product activity tools that have been participated")
    private List<CartPromotionVo> singleList;

    @ApiModelProperty(value = "List of participating composite activity tools")
    private List<CartPromotionVo> groupList;

    @ApiModelProperty(value = "Merchandise operation permit")
    private GoodsOperateAllowable goodsOperateAllowableVO;

    @ApiModelProperty(value = "This product needs to be prompted to the customers discount label")
    private List<String> promotionTags;

    @ApiModelProperty(value = "Preferential quantity")
    private Integer purchaseNum;

    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
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

    public List<CartPromotionVo> getSingleList() {
        return singleList;
    }

    public void setSingleList(List<CartPromotionVo> singleList) {
        this.singleList = singleList;
    }

    public List<CartPromotionVo> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<CartPromotionVo> groupList) {
        this.groupList = groupList;
    }

    public GoodsOperateAllowable getGoodsOperateAllowableVO() {
        return goodsOperateAllowableVO;
    }

    public List<String> getPromotionTags() {
        return promotionTags;
    }

    public void setPromotionTags(List<String> promotionTags) {
        this.promotionTags = promotionTags;
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

        OrderSkuVO skuVO = (OrderSkuVO) o;

        return new EqualsBuilder()
                .append(sellerId, skuVO.sellerId)
                .append(sellerName, skuVO.sellerName)
                .append(goodsId, skuVO.goodsId)
                .append(skuId, skuVO.skuId)
                .append(skuSn, skuVO.skuSn)
                .append(catId, skuVO.catId)
                .append(num, skuVO.num)
                .append(goodsWeight, skuVO.goodsWeight)
                .append(originalPrice, skuVO.originalPrice)
                .append(purchasePrice, skuVO.purchasePrice)
                .append(subtotal, skuVO.subtotal)
                .append(name, skuVO.name)
                .append(goodsImage, skuVO.goodsImage)
                .append(specList, skuVO.specList)
                .append(point, skuVO.point)
                .append(snapshotId, skuVO.snapshotId)
                .append(serviceStatus, skuVO.serviceStatus)
                .append(singleList, skuVO.singleList)
                .append(groupList, skuVO.groupList)
                .append(goodsOperateAllowableVO, skuVO.goodsOperateAllowableVO)
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
                .append(singleList)
                .append(groupList)
                .append(goodsOperateAllowableVO)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderSkuVO{" +
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
                ", singleList=" + singleList +
                ", groupList=" + groupList +
                ", goodsOperateAllowableVO=" + goodsOperateAllowableVO +
                ", promotionTags=" + promotionTags +
                '}';
    }
}
