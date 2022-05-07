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
package cloud.shopfly.b2c.core.trade.cart.model.vo;

import cloud.shopfly.b2c.core.goods.model.vo.SpecValueVO;
import cloud.shopfly.b2c.core.trade.order.model.enums.ServiceStatusEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.annotation.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Products in shopping cart
 *
 * @author Snow
 * @version v2.0
 * 2018years03month19day21:54:35
 * @since v7.0.0
 */
@ApiModel(value = "sku", description = "product")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@Order
public class CartSkuVO implements Serializable {


    private static final long serialVersionUID = -2761425455060777922L;

    /**
     * Initialize the promo list, spec list in the constructor
     */
    public CartSkuVO() {
        this.checked = 1;
        this.invalid = 0;
        this.purchaseNum = 0;
        specList = new ArrayList<>();
        promotionTags = new ArrayList<>();
    }

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

    @ApiModelProperty(value = "Preferential quantity")
    private Integer purchaseNum;

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
     * Whether selected, to settle0:uncheck1:Selected, default
     */
    @ApiModelProperty(value = "Whether selected, to settle")
    private Integer checked;


    @ApiModelProperty(value = "Free freight or not,1：Merchant bears freight（Free shipping） 0：Buyer pays freight")
    private Integer isFreeFreight;

    @ApiModelProperty(value = "List of single product activity tools that have been participated")
    private List<CartPromotionVo> singleList;

    @ApiModelProperty(value = "List of participating composite activity tools")
    private List<CartPromotionVo> groupList;

    @ApiModelProperty(value = "This product needs to be prompted to the customers discount label")
    private List<String> promotionTags;

    @ApiModelProperty(value = "Not participating in activities")
    private Integer notJoinPromotion;

    @ApiModelProperty(value = "The freight templateid")
    private Integer templateId;

    /**
     * List of specifications
     */
    @ApiModelProperty(value = "Specification list")
    private List<SpecValueVO> specList;

    /**
     * In the point exchange activity, the amount of points needed to purchase the product.
     */
    @ApiModelProperty(value = "Using the integral")
    private Integer point;

    @ApiModelProperty(value = "The snapshotID")
    private Integer snapshotId;

    @ApiModelProperty(value = "After state")
    private String serviceStatus;

    @ApiModelProperty(name = "last_modify", value = "Last Modified time")
    private Long lastModify;

    @ApiModelProperty(name = "enable_quantity", value = "Available")
    private Integer enableQuantity;

    private PromotionRule rule;

    @ApiModelProperty(value = "Whether the failure：0:normal1:Has the failure")
    private Integer invalid;

    @ApiModelProperty(value = "Shopping cart item error message")
    private String errorMessage;

    @ApiModelProperty(value = "Deliverable or not1Can be delivery（In stock）0  不Can be delivery（Is not available）")
    private Integer isShip;

    @ApiModelProperty(name = "goods_type", value = "TypeNORMALordinaryPOINTpoint")
    private String goodsType;


    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public Integer getIsShip() {
        if (isShip == null) {
            return 1;
        }
        return isShip;
    }

    public void setIsShip(Integer isShip) {
        this.isShip = isShip;
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


    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public Double getGoodsWeight() {
        if (this.goodsWeight == null) {
            return 0.0;
        }
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


    public Integer getIsFreeFreight() {
        return isFreeFreight;
    }

    public void setIsFreeFreight(Integer isFreeFreight) {
        this.isFreeFreight = isFreeFreight;
    }

    public List<CartPromotionVo> getSingleList() {
        return singleList;
    }

    public void setSingleList(List<CartPromotionVo> singleList) {
        this.singleList = singleList;
    }

    public List<CartPromotionVo> getGroupList() {
        if (groupList == null) {
            return new ArrayList<>();
        }
        return groupList;
    }

    public void setGroupList(List<CartPromotionVo> groupList) {
        this.groupList = groupList;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
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

    public Long getLastModify() {
        return lastModify;
    }

    public void setLastModify(Long lastModify) {
        this.lastModify = lastModify;
    }

    public String getServiceStatus() {
        if (serviceStatus == null) {
            serviceStatus = ServiceStatusEnum.NOT_APPLY.value();
        }
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;
    }

    public List<String> getPromotionTags() {
        return promotionTags;
    }

    public void setPromotionTags(List<String> promotionTags) {
        this.promotionTags = promotionTags;
    }

    public PromotionRule getRule() {
        return rule;
    }

    public void setRule(PromotionRule rule) {
        this.rule = rule;
    }

    public String getErrorMessage() {
        if (errorMessage == null) {
            return "";
        }
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getNotJoinPromotion() {

        if (notJoinPromotion == null) {
            return 0;
        }
        return notJoinPromotion;
    }

    public void setNotJoinPromotion(Integer notJoinPromotion) {
        this.notJoinPromotion = notJoinPromotion;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    @Override
    public String toString() {
        return "CartSkuVO{" +
                "sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", goodsId=" + goodsId +
                ", skuId=" + skuId +
                ", skuSn='" + skuSn + '\'' +
                ", catId=" + catId +
                ", num=" + num +
                ", purchaseNum=" + purchaseNum +
                ", goodsWeight=" + goodsWeight +
                ", originalPrice=" + originalPrice +
                ", purchasePrice=" + purchasePrice +
                ", subtotal=" + subtotal +
                ", name='" + name + '\'' +
                ", goodsImage='" + goodsImage + '\'' +
                ", checked=" + checked +
                ", isFreeFreight=" + isFreeFreight +
                ", singleList=" + singleList +
                ", groupList=" + groupList +
                ", promotionTags=" + promotionTags +
                ", notJoinPromotion=" + notJoinPromotion +
                ", templateId=" + templateId +
                ", specList=" + specList +
                ", point=" + point +
                ", snapshotId=" + snapshotId +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", lastModify=" + lastModify +
                ", enableQuantity=" + enableQuantity +
                ", rule=" + rule +
                ", invalid=" + invalid +
                ", errorMessage='" + errorMessage + '\'' +
                ", isShip=" + isShip +
                ", goodsType='" + goodsType + '\'' +
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

        CartSkuVO skuVO = (CartSkuVO) o;

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
                .append(checked, skuVO.checked)
                .append(isFreeFreight, skuVO.isFreeFreight)
                .append(singleList, skuVO.singleList)
                .append(groupList, skuVO.groupList)
                .append(templateId, skuVO.templateId)
                .append(specList, skuVO.specList)
                .append(point, skuVO.point)
                .append(snapshotId, skuVO.snapshotId)
                .append(serviceStatus, skuVO.serviceStatus)
                .append(lastModify, skuVO.lastModify)
                .append(enableQuantity, skuVO.enableQuantity)
                .append(goodsType, skuVO.goodsType)
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
                .append(checked)
                .append(isFreeFreight)
                .append(singleList)
                .append(groupList)
                .append(templateId)
                .append(specList)
                .append(point)
                .append(snapshotId)
                .append(serviceStatus)
                .append(lastModify)
                .append(enableQuantity)
                .append(goodsType)
                .toHashCode();
    }


}
