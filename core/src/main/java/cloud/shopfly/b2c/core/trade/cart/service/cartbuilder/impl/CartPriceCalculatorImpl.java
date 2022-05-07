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
package cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl;

import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import cloud.shopfly.b2c.core.trade.cart.model.enums.PromotionTarget;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PriceDetailVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PromotionRule;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartPriceCalculator;

import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kingapex on 2018/12/10.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
@Service(value = "cartPriceCalculator")
public class CartPriceCalculatorImpl implements CartPriceCalculator {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Override
    public PriceDetailVO countPrice(List<CartVO> cartList) {

        // Calculate the price according to the rules
        PriceDetailVO priceDetailVO = this.countPriceWithRule(cartList);


        return priceDetailVO;
    }


    private PriceDetailVO countPriceWithRule(List<CartVO> cartList) {

        PriceDetailVO price = new PriceDetailVO();

        for (CartVO cart : cartList) {


            boolean freeShipping = false;
            PriceDetailVO cartPrice = new PriceDetailVO();
            cartPrice.setFreightPrice(cart.getPrice().getFreightPrice());
            for (CartSkuVO cartSku : cart.getSkuList()) {

                // // If it is a settlement page, ignore the unchecked update by liuyulei 2019-05-07
                if (CartType.CHECKOUT.equals(cart.getCartType()) && cartSku.getChecked() == 0) {
                    continue;
                }
                PromotionRule skuRule = cartSku.getRule();

                if (skuRule != null) {

                    this.applyRule(cartSku, skuRule, cart);

                    // Free shipping for one item means free shipping for all
                    if (!freeShipping) {
                        freeShipping = skuRule.getFreeShipping();
                    }

                } else {
                    skuRule = new PromotionRule(PromotionTarget.SKU);
                }
                // Unselected items are not included in the total. Update by liuyulei 2019-05-07
                if (cartSku.getChecked() == 0) {
                    continue;
                }
                // The original price of all items in the shopping cart
                cartPrice.setOriginalPrice(CurrencyUtil.add(cartPrice.getOriginalPrice(), CurrencyUtil.mul(cartSku.getOriginalPrice(), cartSku.getNum())));

                // Shopping cart all subtotals
                cartPrice.setGoodsPrice(CurrencyUtil.add(cartPrice.getGoodsPrice(), cartSku.getSubtotal()));

                // Shopping cart cashback
                cartPrice.setCashBack(CurrencyUtil.add(cartPrice.getCashBack(), skuRule.getReducedTotalPrice()));

                // Shopping cart usage points
                cartPrice.setExchangePoint(cartPrice.getExchangePoint() + skuRule.getUsePoint());

                // Accumulated commodity weight
                double weight = CurrencyUtil.mul(cartSku.getGoodsWeight(), cartSku.getNum());
                double cartWeight = CurrencyUtil.add(cart.getWeight(), weight);
                cart.setWeight(cartWeight);


            }


            // Apply shopping cart level promotion rules
            List<PromotionRule> cartRuleList = cart.getRuleList();

            boolean cartFreeShipping = false;
            for (PromotionRule rule : cartRuleList) {
                // Apply shopping cart promotion rules
                this.applyCartRule(rule, cartPrice, cart);
                if (rule != null) {
                    cartFreeShipping = rule.getFreeShipping();
                }
            }


            // There is free freight in the single product rules or free freight in the full reduction
            if (freeShipping || cartFreeShipping) {
                cartPrice.setIsFreeFreight(1);
                cartPrice.setFreightPrice(0D);
            }


            // Calculate the total discount amount of goods in the store
            double totalDiscount = CurrencyUtil.add(cartPrice.getCashBack(), cartPrice.getCouponPrice());
            cartPrice.setDiscountPrice(totalDiscount);

            // Total price is commodity price plus freight
            double totalPrice = CurrencyUtil.add(cartPrice.getGoodsPrice(), cartPrice.getFreightPrice());
            cartPrice.setTotalPrice(totalPrice);
            cart.setPrice(cartPrice);

            price = price.plus(cartPrice);


        }


        if(logger.isDebugEnabled()){
            logger.debug("After calculating the discount, the shopping cart data is：");
            logger.debug(cartList.toString());
            logger.debug("The price for：");
            logger.debug(price.toString());
        }

        return price;
    }

    /**
     * Apply a shopping cart promotion rule to the shopping cart
     *
     * @param cartRule  Shopping cart rules
     * @param cartPrice Shopping cart price
     * @param cart      The shopping cart
     */
    private void applyCartRule(PromotionRule cartRule, PriceDetailVO cartPrice, CartVO cart) {

        if (cartRule == null) {
            return;
        }

        // Subtract the total amount of the discount
        if (cartRule.getReducedTotalPrice() != null) {

            double subtotal = cartPrice.getGoodsPrice();
            // Reduction of
            subtotal = CurrencyUtil.sub(subtotal, cartRule.getReducedTotalPrice());

            // It cant be negative
            if (subtotal < 0) {
                subtotal = 0;
            }
            // Set up the
            cartPrice.setFullMinus(cartRule.getReducedTotalPrice());
            cartPrice.setGoodsPrice(subtotal);
            // Shopping cart cashback
            cartPrice.setCashBack(CurrencyUtil.add(cartPrice.getCashBack(), cartRule.getReducedTotalPrice()));
        }

        // Coupons are not included, they will be counted when adding up
        if (cartRule.getUseCoupon() != null) {

            double subtotal = cartPrice.getGoodsPrice();
            // Reduction of
            subtotal = CurrencyUtil.sub(subtotal, cartRule.getUseCoupon().getAmount());

            // It cant be negative
            if (subtotal < 0) {
                subtotal = 0;
            }
            cartPrice.setGoodsPrice(subtotal);
            cartPrice.setCouponPrice(cartRule.getUseCoupon().getAmount());

        }
        // Complimentary coupons
        if (cartRule.getCouponGift() != null) {
            cart.getGiftCouponList().add(cartRule.getCouponGift());
        }

        // Complimentary gifts
        if (cartRule.getGoodsGift() != null) {
            cart.getGiftList().add(cartRule.getGoodsGift());
        }

        // Bonus points
        if (cartRule.getPointGift() != null) {
            cart.setGiftPoint(cartRule.getPointGift());
        }


    }


    /**
     * Apply an item promotion rule to the shopping cart
     *
     * @param cartSku Shopping cart itemsku
     * @param skuRule The promotion rules
     * @param cart    The shopping cart
     */
    private void applyRule(CartSkuVO cartSku, PromotionRule skuRule, CartVO cart) {

        if (!StringUtil.isEmpty(skuRule.getInvalidReason())) {
            cartSku.setChecked(0);
            cartSku.setErrorMessage(skuRule.getInvalidReason());
        }

        // If it has expired, mark it as unselected and the reason for the expiration, and do not participate in the calculation of the price
        if (skuRule.isInvalid()) {
            cartSku.setChecked(0);
            cartSku.setInvalid(1);
            return;
        }

        // Set promotional tag
        if (!StringUtil.isEmpty(skuRule.getTag())) {
            cartSku.getPromotionTags().add(skuRule.getTag());
        }

        // Subtract the total amount of the discount
        if (skuRule.getReducedTotalPrice() != null) {
            double subtotal = cartSku.getSubtotal();
            subtotal = CurrencyUtil.sub(subtotal, skuRule.getReducedTotalPrice());

            // It cant be negative
            if (subtotal < 0) {
                subtotal = 0;
            }

            cartSku.setSubtotal(subtotal);
        }


        // Price
        if (skuRule.getReducedPrice() != null) {
            double originalPrice = cartSku.getOriginalPrice();
            double purchasePrice = CurrencyUtil.sub(originalPrice, skuRule.getReducedPrice());

            // It cant be negative
            if (purchasePrice < 0) {
                purchasePrice = 0;
            }

            cartSku.setPurchasePrice(purchasePrice);
        }


        if (skuRule.getUsePoint() != null) {
            // The integral that Im going to use
            double point = CurrencyUtil.div(skuRule.getUsePoint(), cartSku.getNum());

            cartSku.setPoint(new Double(point).intValue());
        }


        // Complimentary coupons
        if (skuRule.getCouponGift() != null) {
            cart.getGiftCouponList().add(skuRule.getCouponGift());
        }

        // Complimentary gifts
        if (skuRule.getGoodsGift() != null) {
            cart.getGiftList().add(skuRule.getGoodsGift());
        }

        // Bonus points
        if (skuRule.getPointGift() != null) {
            cart.setGiftPoint(skuRule.getPointGift());
        }


    }


}
