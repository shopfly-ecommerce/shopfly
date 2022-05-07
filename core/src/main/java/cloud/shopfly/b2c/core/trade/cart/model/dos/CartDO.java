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
package cloud.shopfly.b2c.core.trade.cart.model.dos;

import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CouponVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PriceDetailVO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Shopping cart model
 *
 * @author Snow
 * @version 1.0
 * @since v7.0.0
 * 2018years03month20day14:21:39
 */

@ApiModel(description = "The shopping cart")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CartDO implements Serializable {


    private static final long serialVersionUID = 1466001652922300536L;

    @ApiModelProperty(value = "The sellerid")
    private Integer sellerId;

    @ApiModelProperty(value = "Selected delivery modeid")
    private Integer shippingTypeId;


    @ApiModelProperty(value = "Name of the selected delivery mode")
    private String shippingTypeName;


    @ApiModelProperty(value = "The seller name")
    private String sellerName;


    @ApiModelProperty(value = "Shopping cart weight")
    private Double weight;

    @ApiModelProperty(value = "Shopping cart price")
    private PriceDetailVO price;

    @ApiModelProperty(value = "A list of products in the shopping cart")
    private List<CartSkuVO> skuList;

    @ApiModelProperty(value = "List of coupons used")
    private List<CouponVO> couponList;

    @ApiModelProperty(value = "Gift list")
    private List<FullDiscountGiftDO> giftList;

    @ApiModelProperty(value = "Free coupon list")
    private List<CouponVO> giftCouponList;

    @ApiModelProperty(value = "Present integral")
    private Integer giftPoint;

    @ApiModelProperty(value = "Whether the failure：0:normal1:Has the failure")
    private Integer invalid;

    public CartDO() {
    }

    /**
     * Initializes the owner in the constructor、Product list、List of promotions and coupons
     */
    public CartDO(int sellerId, String sellerName) {

        this.sellerId = sellerId;
        this.sellerName = sellerName;
        price = new PriceDetailVO();
        skuList = new ArrayList<>();
        couponList = new ArrayList<>();
        giftCouponList = new ArrayList<>();
        giftList = new ArrayList<>();
        giftPoint = 0;
    }


    /**
     * Empty the offer information function, do not empty coupons
     */
    public void clearPromotion() {
        if (price != null) {
            price.clear();
        }
        this.couponList = new ArrayList<>();
        giftCouponList = new ArrayList<>();
        giftList = new ArrayList<>();
        giftPoint = 0;
    }

    @Override
    public String toString() {
        return "CartDO{" +
                "sellerId=" + sellerId +
                ", shippingTypeId=" + shippingTypeId +
                ", shippingTypeName='" + shippingTypeName + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", skuList=" + skuList +
                ", couponList=" + couponList +
                ", giftList=" + giftList +
                ", giftCouponList=" + giftCouponList +
                ", giftPoint=" + giftPoint +
                ", invalid=" + invalid +
                '}';
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getShippingTypeId() {
        return shippingTypeId;
    }

    public void setShippingTypeId(Integer shippingTypeId) {
        this.shippingTypeId = shippingTypeId;
    }

    public String getShippingTypeName() {
        return shippingTypeName;
    }

    public void setShippingTypeName(String shippingTypeName) {
        this.shippingTypeName = shippingTypeName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public PriceDetailVO getPrice() {
        return price;
    }

    public void setPrice(PriceDetailVO price) {
        this.price = price;
    }

    public List<CartSkuVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<CartSkuVO> skuList) {
        this.skuList = skuList;
    }

    public List<CouponVO> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponVO> couponList) {
        this.couponList = couponList;
    }

    public List<FullDiscountGiftDO> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<FullDiscountGiftDO> giftList) {
        this.giftList = giftList;
    }

    public List<CouponVO> getGiftCouponList() {
        return giftCouponList;
    }

    public void setGiftCouponList(List<CouponVO> giftCouponList) {
        this.giftCouponList = giftCouponList;
    }

    public Integer getGiftPoint() {
        return giftPoint;
    }

    public void setGiftPoint(Integer giftPoint) {
        this.giftPoint = giftPoint;
    }


    public Integer getInvalid() {
        return invalid;
    }


    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CartDO cartDO = (CartDO) o;

        return new EqualsBuilder()
                .append(sellerId, cartDO.sellerId)
                .append(shippingTypeId, cartDO.shippingTypeId)
                .append(shippingTypeName, cartDO.shippingTypeName)
                .append(sellerName, cartDO.sellerName)
                .append(weight, cartDO.weight)
                .append(price, cartDO.price)
                .append(skuList, cartDO.skuList)
                .append(couponList, cartDO.couponList)
                .append(giftList, cartDO.giftList)
                .append(giftCouponList, cartDO.giftCouponList)
                .append(giftPoint, cartDO.giftPoint)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(sellerId)
                .append(shippingTypeId)
                .append(shippingTypeName)
                .append(sellerName)
                .append(weight)
                .append(price)
                .append(skuList)
                .append(couponList)
                .append(giftList)
                .append(giftCouponList)
                .append(giftPoint)
                .toHashCode();
    }
}
