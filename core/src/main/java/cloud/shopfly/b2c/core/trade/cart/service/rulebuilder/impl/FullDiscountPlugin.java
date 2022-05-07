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
package cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.impl;

import cloud.shopfly.b2c.core.goods.model.enums.GoodsType;
import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.coupon.service.CouponManager;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.service.FullDiscountGiftManager;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.dos.CartDO;
import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import cloud.shopfly.b2c.core.trade.cart.model.enums.PromotionTarget;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CouponVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PromotionRule;
import cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.CartPromotionRuleBuilder;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;


/**
 * Created by kingapex on 2018/12/12.
 * Full discount plug-in
 *
 * @author kingapex
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class FullDiscountPlugin implements CartPromotionRuleBuilder {

    @Autowired
    private FullDiscountGiftManager fullDiscountGiftManager;

    @Autowired
    private CouponManager couponManager;

    @Override
    public PromotionRule build(CartVO cart, PromotionVO promotionVO) {


        // Create a rule that applies to the shopping cart
        PromotionRule rule = new PromotionRule(PromotionTarget.CART);


        FullDiscountVO fullDiscountVO = promotionVO.getFullDiscountVO();

        // Calculate the total price of the shopping cart
        double totalPrice = this.countTotalPrice(cart, fullDiscountVO);

        // Reach the threshold of full reduction
        if (fullDiscountVO.getFullMoney() != null && fullDiscountVO.getFullMoney() <= totalPrice) {

            // Full reduction
            this.manJian(fullDiscountVO, rule);

            // Full fold
            this.manZhe(totalPrice, fullDiscountVO, rule);

            // Free shipping
            this.freeShip(fullDiscountVO, rule);

            // To send gift
            this.giveGift(fullDiscountVO, rule);

            // To send a coupon
            this.giveCoupon(fullDiscountVO, rule);

            // Award points
            this.givePoint(fullDiscountVO, rule);

            setTag(fullDiscountVO.getFdId(), cart, fullDiscountVO.getFullMoney(), true);


        } else {

            // The shopping cart that does not meet the threshold is also set up to prompt the user that the item is participating
            if (cart.getCartType().equals(CartType.CART)) {
                setTag(fullDiscountVO.getFdId(), cart, fullDiscountVO.getFullMoney(), false);
            }

            // If the full decrement is not reached, set to expired
            rule.setInvalid(true);
        }

        return rule;


    }

    private void setTag(int activeId, CartVO cartVO, double fullMoney, boolean enable) {

        DecimalFormat df = new DecimalFormat("#.00");
        cartVO.getSkuList().forEach(cartSkuVO -> {
            // There is a full subtraction activity, even if the threshold is not reached to display the tag
            // If it is an integral item, it cannot be displayed
            if (cartSkuVO.getGroupList().size() != 0 && !GoodsType.POINT.name().equals(cartSkuVO.getGoodsType())) {

                cartSkuVO.getGroupList().forEach(cartPromotionVo -> {
                    if (cartPromotionVo.getActivityId().equals(activeId)) {

                        // If the threshold is reached, it is selected
                        if (enable) {
                            cartPromotionVo.setIsCheck(1);
                        }
                        // Cart displays the tag whether or not it reaches the threshold, while Checkout does not display it until it reaches the threshold
                        if (cartVO.getCartType().equals(CartType.CART) || enable) {
                            cartSkuVO.getPromotionTags().add("full" + df.format(fullMoney) + "preferential");
                        }

                    }
                });


            }
        });

    }


    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.FULL_DISCOUNT;
    }


    /**
     * Full reduction
     *
     * @param fullDiscountVO
     * @param rule
     */
    private void manJian(FullDiscountVO fullDiscountVO, PromotionRule rule) {
        // Discount for full reduction
        if (fullDiscountVO.getIsFullMinus() != null && fullDiscountVO.getIsFullMinus() == 1 && fullDiscountVO.getMinusValue() != null) {
            // Set the amount of the rule to subtract
            rule.setReducedTotalPrice(fullDiscountVO.getMinusValue());
            rule.setTips("Reduction of" + fullDiscountVO.getMinusValue() + "USD");
        }
    }


    /**
     * Full fold
     *
     * @param totalPrice
     * @param fullDiscountVO
     * @param rule
     */
    private void manZhe(double totalPrice, FullDiscountVO fullDiscountVO, PromotionRule rule) {

        // Full discount discount calculation
        if (fullDiscountVO.getIsDiscount() != null && fullDiscountVO.getIsDiscount() == 1 && fullDiscountVO.getDiscountValue() != null) {

            // Discount ratio: no more than 10, then use 10 to subtract the discount ratio to be deducted
            double discountValue = fullDiscountVO.getDiscountValue();
            discountValue = discountValue > 10 ? 10 : discountValue;
            discountValue = 10 - discountValue;
            // Calculate discount ratio
            double discount = CurrencyUtil.mul(discountValue, 0.1);

            // Figure out how much youre going to take off
            double reducedPrice = CurrencyUtil.mul(totalPrice, discount);

            // Set the amount of the rule to subtract
            rule.setReducedTotalPrice(reducedPrice);
            rule.setTips("After the fold reduction" + reducedPrice + "USD");


        }

    }


    public static void main(String[] args) {


        System.out.println(DateUtil.toString(1546073780L, "yyyy-MM-dd"));
    }


    /**
     * Free shipping
     *
     * @param fullDiscountVO
     * @param rule
     */
    private void freeShip(FullDiscountVO fullDiscountVO, PromotionRule rule) {
        // / Free shipping discount
        if (fullDiscountVO.getIsFreeShip() != null && fullDiscountVO.getIsFreeShip() == 1) {
            rule.setFreeShipping(true);
            rule.setTips("Free shipping");

        }
    }


    /**
     * To send gift
     *
     * @param fullDiscountVO
     * @param rule
     */
    private void giveGift(FullDiscountVO fullDiscountVO, PromotionRule rule) {
        // Judge whether to give gifts
        if (fullDiscountVO.getIsSendGift() != null && fullDiscountVO.getIsSendGift() == 1 && fullDiscountVO.getGiftId() != null) {
            FullDiscountGiftDO giftDO = fullDiscountGiftManager.getModel(fullDiscountVO.getGiftId());

            // Available inventory is displayed only when greater than 0
            if (giftDO.getEnableStore() > 0) {

                rule.setGoodsGift(giftDO);
                rule.setTips("To send gift[" + giftDO.getGiftName() + "]");
            } else {
                giftDO.setGiftName(giftDO.getGiftName() + "(The freebies have been given away)");
                rule.setGoodsGift(giftDO);
                rule.setTips("The freebies have been given away");
            }

        }
    }


    /**
     * Award points
     *
     * @param fullDiscountVO
     * @param rule
     */
    private void givePoint(FullDiscountVO fullDiscountVO, PromotionRule rule) {

        // Judge whether to give points
        if (fullDiscountVO.getIsSendPoint() != null && fullDiscountVO.getIsSendPoint() == 1 && fullDiscountVO.getPointValue() != null) {
            rule.setPointGift(fullDiscountVO.getPointValue());
            rule.setTips("Award points[" + fullDiscountVO.getPointValue() + "]");

        }
    }


    /**
     * To send a coupon
     *
     * @param fullDiscountVO
     * @param rule
     */
    private void giveCoupon(FullDiscountVO fullDiscountVO, PromotionRule rule) {
        // Determine whether gift coupons are available
        if (fullDiscountVO.getIsSendBonus() != null && fullDiscountVO.getIsSendBonus() == 1 && fullDiscountVO.getBonusId() != null) {

            CouponDO couponDO = this.couponManager.getModel(fullDiscountVO.getBonusId());

            CouponVO couponVO = new CouponVO();
            couponVO.setCouponId(couponDO.getCouponId());
            couponVO.setUseTerm(couponDO.getTitle());
            couponVO.setAmount(couponDO.getCouponPrice());
            couponVO.setCouponThresholdPrice(couponDO.getCouponThresholdPrice());
            // If the remaining available quantity is greater than 0, the free coupon will be displayed. Otherwise, it will not be displayed
            if (CurrencyUtil.sub(couponDO.getCreateNum(), couponDO.getReceivedNum()) <= 0) {
                couponVO.setErrorMsg("(Coupons have been delivered)");
                rule.setCouponGift(couponVO);
                rule.setTips("coupons[" + couponVO.getUseTerm() + "," + couponVO.getAmount() + "]Has been sent out");
            } else {
                fullDiscountVO.setCouponDO(couponDO);
                rule.setCouponGift(couponVO);
                rule.setTips("To send a coupon[" + couponVO.getUseTerm() + "," + couponVO.getAmount() + "]");

            }
        }
    }


    /**
     * Add up the total price of the shopping cart
     *
     * @param cart A shopping cart
     * @return Total price of shopping cart
     */
    private double countTotalPrice(CartDO cart, FullDiscountVO fullDiscountVO) {

        double totalPrice = 0;
        List<CartSkuVO> skuList = cart.getSkuList();

        for (CartSkuVO skuVO : skuList) {

            if (skuVO.getChecked() == 0) {
                continue;
            }

            // Bonus items do not participate in other activities
            // Reference: http://doc.javamall.com.cn/current/demand/xu-qiu-shuo-ming/cu-xiao.html# overlapping relationship
            if (GoodsType.POINT.name().equals(skuVO.getGoodsType())) {
                continue;
            }

            if (!fullDiscountVO.getGoodsIdList().contains(-1) && !fullDiscountVO.getGoodsIdList().contains(skuVO.getGoodsId())) {
                continue;
            }

            // The total is the total price
            // Abandoned: the most original total price, cannot use clinch a deal price to calculate threshold, because may be changed by other activities
            // New: the demand is to calculate the second half price first, and calculate the full fold after the immediate reduction of the single item
            double subTotal = skuVO.getSubtotal();
            PromotionRule rule = skuVO.getRule();
            double reducedTotalPrice = 0.0;
            if (rule != null) {
                reducedTotalPrice = rule.getReducedTotalPrice();
            }

            totalPrice = CurrencyUtil.add(CurrencyUtil.sub(subTotal, reducedTotalPrice), totalPrice);

        }

        return totalPrice;
    }


}
