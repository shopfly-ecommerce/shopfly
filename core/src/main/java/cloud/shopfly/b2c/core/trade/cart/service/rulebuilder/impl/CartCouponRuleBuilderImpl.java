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

import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.dos.CartDO;
import cloud.shopfly.b2c.core.trade.cart.model.enums.PromotionTarget;
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.core.trade.cart.service.CartPromotionManager;
import cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.CartCouponRuleBuilder;
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Coupon promotion rule builder implementation
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/19
 */
@Service
public class CartCouponRuleBuilderImpl implements CartCouponRuleBuilder {


    @Autowired
    private CartPromotionManager cartPromotionManager;


    @Override
    public PromotionRule build(CartVO cartVO, CouponVO couponVO) {
        // Create a rule that applies to the shopping cart
        PromotionRule rule = new PromotionRule(PromotionTarget.CART);

        double totalPrice = this.countTotalPrice(cartVO);

        // If the threshold is not reached, remove coupons that are in use
        if (totalPrice < couponVO.getCouponThresholdPrice()) {
            cartPromotionManager.cleanCoupon();
            return rule;
        }
        // Set the discount to the coupon amount
        rule.setUseCoupon(couponVO);

        return rule;
    }


    /**
     * Add up the total price of the shopping cart
     *
     * @param cart A shopping cart
     * @return Total price of shopping cart
     */
    private double countTotalPrice(CartDO cart) {

        double totalPrice = 0;
        List<CartSkuVO> skuList = cart.getSkuList();

        for (CartSkuVO skuVO : skuList) {

            if (skuVO.getChecked() == 0) {
                continue;
            }
            // Selected, but is integral goods, not cumulative
            if (!this.checkEnableCoupon()) {

                continue;
            }

            // The total is the total price
            // The most primitive total price, cannot clinch a deal with valence will calculate threshold, because the likelihood was changed by other activity
            double subTotal = skuVO.getSubtotal();

            totalPrice = CurrencyUtil.add(subTotal, totalPrice);

        }

        return totalPrice;
    }

    /**
     * You cant use coupons for points
     *
     * @return add by liuyulei 2019-05-14
     */
    private boolean checkEnableCoupon() {
        SelectedPromotionVo selectedPromotionVo = cartPromotionManager.getSelectedPromotion();

        List<PromotionVO> singlePromotionList = selectedPromotionVo.getSinglePromotionList();

        if (singlePromotionList != null && !singlePromotionList.isEmpty()) {
            for (PromotionVO promotionVO : singlePromotionList) {
                if (PromotionTypeEnum.EXCHANGE.name().equals(promotionVO.getPromotionType())) {
                    return false;
                }
            }
        }


        return true;
    }


}
