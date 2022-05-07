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

import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Order price information
 *
 * @author kingapex
 * @version v1.0
 * @created 2017years08month17day
 * @since v6.2
 */
@ApiModel(value = "PriceDetailVO", description = "The price detail")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PriceDetailVO implements Serializable {


    private static final long serialVersionUID = -960537582096338500L;

    @ApiModelProperty(value = "The total price")
    private Double totalPrice;


    /**
     * Its used to determine full subtraction、The base of a discount such as a coupon
     */
    @ApiModelProperty(value = "Original price, no discount")
    private Double originalPrice;

    @ApiModelProperty(value = "Commodity price, after preferential")
    private Double goodsPrice;


    @ApiModelProperty(value = "The shipping fee,")
    private Double freightPrice;

    /**
     * The amount of= Discount amount of various discount tools+ The amount of a discount coupon
     */
    @ApiModelProperty(value = "Discount amount")
    private Double discountPrice;

    @ApiModelProperty(value = "Cash back amount, not including coupons")
    private Double cashBack;

    @ApiModelProperty(value = "Coupon deduction amount")
    private Double couponPrice;

    @ApiModelProperty(value = "Full amount reduction")
    private Double fullMinus;

    /**
     * 1For free shipping
     */
    @ApiModelProperty(value = "Free freight or not,1For free shipping")
    private Integer isFreeFreight;

    @ApiModelProperty(value = "Integral used")
    private Integer exchangePoint;

    /**
     * Constructor to initialize the default values
     */
    public PriceDetailVO() {
        this.goodsPrice = 0.0;
        this.freightPrice = 0.0;
        this.totalPrice = 0.0;
        this.discountPrice = 0.0;
        this.exchangePoint = 0;
        this.isFreeFreight = 0;
        this.couponPrice = 0D;
        this.cashBack = 0D;
        this.originalPrice = 0D;
        fullMinus = 0d;
    }


    /**
     * Emptying function
     */
    public void clear() {

        this.goodsPrice = 0.0;
        this.freightPrice = 0.0;
        this.totalPrice = 0.0;
        this.discountPrice = 0.0;
        this.exchangePoint = 0;
        this.isFreeFreight = 0;
        this.cashBack = 0D;
        this.originalPrice = 0D;
        this.couponPrice = 0D;
        fullMinus = 0d;
    }

    /**
     * Price summation
     *
     * @param price
     * @return
     */
    public PriceDetailVO plus(PriceDetailVO price) {

        double total = CurrencyUtil.add(totalPrice, price.getTotalPrice());
        double original = CurrencyUtil.add(originalPrice, price.getOriginalPrice());
        double goods = CurrencyUtil.add(goodsPrice, price.getGoodsPrice());
        double freight = CurrencyUtil.add(this.freightPrice, price.getFreightPrice());
        double discount = CurrencyUtil.add(this.discountPrice, price.getDiscountPrice());
        double couponPrice = CurrencyUtil.add(this.couponPrice, price.getCouponPrice());
        double cashBack = CurrencyUtil.add(this.cashBack, price.getCashBack());
        double fullMinus = CurrencyUtil.add(this.cashBack, price.getFullMinus());
        int point = this.exchangePoint + price.getExchangePoint();

        PriceDetailVO newPrice = new PriceDetailVO();
        newPrice.setTotalPrice(total);
        newPrice.setGoodsPrice(goods);
        newPrice.setFreightPrice(freight);
        newPrice.setDiscountPrice(discount);
        newPrice.setExchangePoint(point);
        newPrice.setCouponPrice(couponPrice);
        newPrice.setCashBack(cashBack);
        newPrice.setIsFreeFreight(price.getIsFreeFreight());
        newPrice.setOriginalPrice(original);
        newPrice.setFullMinus(fullMinus);
        return newPrice;
    }


    /**
     * Calculate the total price of the current store
     */
    public void countPrice() {
        // Total original price of goods of current merchants in the shopping cart
        Double goodsPrice = this.getGoodsPrice();

        // Total delivery amount of current merchants in the shopping cart
        Double freightPrice = this.getFreightPrice();

        // Total amount payable by current merchants in the shopping cart
        // Calculation process = total original price of goods - cash rebate amount + distribution cost
        Double totalPrice = CurrencyUtil.add(CurrencyUtil.sub(goodsPrice, this.getCashBack()), freightPrice);

        // Calculation process = total original price of goods - coupon amount
        totalPrice = CurrencyUtil.sub(totalPrice, this.getCouponPrice());

        // Prevent negative amounts
        if (totalPrice.doubleValue() <= 0) {
            totalPrice = 0d;
        }
        this.setTotalPrice(totalPrice);

    }

    /**
     * Recalculate the discount amount
     */
    public void reCountDiscountPrice() {
        discountPrice = CurrencyUtil.add(cashBack, couponPrice);
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Double getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(Double freightPrice) {
        this.freightPrice = freightPrice;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getIsFreeFreight() {
        return isFreeFreight;
    }

    public void setIsFreeFreight(Integer isFreeFreight) {
        this.isFreeFreight = isFreeFreight;
    }

    public Integer getExchangePoint() {
        return exchangePoint;
    }

    public void setExchangePoint(Integer exchangePoint) {
        this.exchangePoint = exchangePoint;
    }


    public Double getFullMinus() {
        return fullMinus;
    }

    public void setFullMinus(Double fullMinus) {
        this.fullMinus = fullMinus;
    }

    public Double getCashBack() {

        return cashBack;
    }

    public void setCashBack(Double cashBack) {
        this.cashBack = cashBack;
    }

    public Double getCouponPrice() {
        if (couponPrice == null) {
            return 0D;
        }
        return couponPrice;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setCouponPrice(Double couponPrice) {
        this.couponPrice = couponPrice;
    }

    @Override
    public String toString() {
        return "PriceDetailVO{" +
                "totalPrice=" + totalPrice +
                ", goodsPrice=" + goodsPrice +
                ", freightPrice=" + freightPrice +
                ", discountPrice=" + discountPrice +
                ", cashBack=" + cashBack +
                ", couponPrice=" + couponPrice +
                ", isFreeFreight=" + isFreeFreight +
                ", exchangePoint=" + exchangePoint +
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

        PriceDetailVO that = (PriceDetailVO) o;

        return new EqualsBuilder()
                .append(totalPrice, that.totalPrice)
                .append(goodsPrice, that.goodsPrice)
                .append(freightPrice, that.freightPrice)
                .append(discountPrice, that.discountPrice)
                .append(isFreeFreight, that.isFreeFreight)
                .append(exchangePoint, that.exchangePoint)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(totalPrice)
                .append(goodsPrice)
                .append(freightPrice)
                .append(discountPrice)
                .append(isFreeFreight)
                .append(exchangePoint)
                .toHashCode();
    }
}
