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

import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.core.trade.cart.service.CartPromotionManager;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartPromotionRuleRenderer;
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.CartCouponRuleBuilder;
import cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.CartPromotionRuleBuilder;
import cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.SkuPromotionRuleBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by kingapex on 2018/12/10.
 * Shopping promotion information rendering implementation
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
@Service("cartPromotionRuleRenderer")
public class CartPromotionRuleRendererImpl implements CartPromotionRuleRenderer {


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private CartPromotionManager cartPromotionManager;


    @Autowired
    private List<SkuPromotionRuleBuilder> skuPromotionRuleBuilderList;

    /**
     * At present, only one kind of discount is shopping cart level promotion
     */
    @Autowired
    private CartPromotionRuleBuilder cartPromotionRuleBuilder;

    @Autowired
    private CartCouponRuleBuilder cartCouponRuleBuilder;


    @Override
    public void render(List<CartVO> cartList, boolean includeCoupon) {

        // Rendering rules
        this.renderRule(cartList, includeCoupon);

        if(logger.isDebugEnabled()){
            logger.debug("The result of shopping cart processing promotion rule is：");
            logger.debug(cartList.toString());
        }

    }


    /**
     * The rules apply colours to a drawing
     *
     * @param cartList
     */
    private void renderRule(List<CartVO> cartList, boolean includeCoupon) {

        // Get a list of ongoing full offers
        List<FullDiscountVO> fullDiscountVoList = cartPromotionManager.getFullDiscounPromotion(cartList);

        SelectedPromotionVo selectedPromotionVo = cartPromotionManager.getSelectedPromotion();

        // User selected single product activity
        List<PromotionVO> skuPromotionList = selectedPromotionVo.getSinglePromotionList();

        for (CartVO cart : cartList) {

            if (includeCoupon) {
                // The coupons the user chooses to use
                CouponVO coupon = selectedPromotionVo.getCoupon();
                // Build rule if there are coupons to use
                if (coupon != null) {
                    PromotionRule couponRule = cartCouponRuleBuilder.build(cart, coupon);
                    cart.getRuleList().add(couponRule);
                }

            }


            // Render single product activity
            // Empty filter
            if (skuPromotionList != null) {
                // Loop through shopping cart promotion rules
                for (CartSkuVO cartSku : cart.getSkuList()) {

                    // If it is a settlement page, skip the unchecked update by LIUyulei 2019-05-07
                    if (CartType.CHECKOUT.equals(cart.getCartType()) && cartSku.getChecked() == 0) {
                        // If not selected, the selected promotion is deleted
                        cartPromotionManager.delete(new Integer[]{cartSku.getSkuId()});
                        continue;
                    }

                    // Calculate promotion rules, form a list, and press it into a unified rule list
                    PromotionRule skuRule = oneSku(cartSku, skuPromotionList);
                    cartSku.setRule(skuRule);

                }
            }


            // Render full minus offers
            PromotionRule cartRule = this.onCart(cart, fullDiscountVoList);
            cart.getRuleList().add(cartRule);

        }


    }


    /**
     * Build a fully subtracted shopping cart level rule
     *
     * @param cart
     * @param fullDiscountVoList
     * @return Returns if there is no suitable rulenull
     */
    private PromotionRule onCart(CartVO cart, List<FullDiscountVO> fullDiscountVoList) {


        for (FullDiscountVO fullDiscountVO : fullDiscountVoList) {
            // Generate preference rules
            PromotionVO promotionVO = new PromotionVO();
            promotionVO.setPromotionType(PromotionTypeEnum.FULL_DISCOUNT.name());
            promotionVO.setFullDiscountVO(fullDiscountVO);
            PromotionRule rule = cartPromotionRuleBuilder.build(cart, promotionVO);

            // If the full reduction threshold is reached or is on the shopping cart page, an activity prompt is displayed
            // It also means that if the threshold is not reached on the settlement page, no active prompt will be displayed
            if (!rule.isInvalid() || cart.getCartType().equals(CartType.CART)) {
                // With prompt reduction
                String notice = this.createNotice(fullDiscountVO);
                cart.setPromotionNotice(notice);
            }

            return rule;
        }


        return null;
    }


    /**
     * Find the corresponding promotion according to the type of promotionbuilder
     *
     * @param promotionType Promotion type
     * @return
     */
    private SkuPromotionRuleBuilder getSkuRuleBuilder(String promotionType) {

        if (skuPromotionRuleBuilderList == null) {
            return null;
        }
        for (SkuPromotionRuleBuilder builder : skuPromotionRuleBuilderList) {
            if (builder.getPromotionType().name().equals(promotionType)) {
                return builder;
            }
        }

        return null;
    }


    private PromotionRule oneSku(CartSkuVO cartSku, List<PromotionVO> skuPromotionList) {

        for (PromotionVO promotionVo : skuPromotionList) {

            if (promotionVo.getSkuId().intValue() == cartSku.getSkuId().intValue()) {
                // Sku preference rule builder
                SkuPromotionRuleBuilder skuRuleBuilder = this.getSkuRuleBuilder(promotionVo.getPromotionType());

                if (skuRuleBuilder == null) {
                   if (logger.isDebugEnabled()){
                       logger.debug(cartSku.getSkuId() + "Types of activities[" + promotionVo.getPromotionType() + "]Could not findbuilder");
                   }
                    continue;
                }

                // Set the situation in the selection of single product activities
                selectedPromotion(cartSku, promotionVo);

                // Build promotion rules
                return skuRuleBuilder.build(cartSku, promotionVo);
            }

        }

        return null;
    }

    private void selectedPromotion(CartSkuVO cartSku, PromotionVO promotionVo) {

        List<CartPromotionVo> singleList = cartSku.getSingleList();
        for (CartPromotionVo cartPromotionVo : singleList) {
            if (cartPromotionVo.getPromotionType().equals(promotionVo.getPromotionType())) {
                cartPromotionVo.setIsCheck(1);
            }
        }

    }


    /**
     * Generate promotional prompts based on full reduction activities
     *
     * @param fullDiscountVO With reduced activityvo
     * @return
     */
    private String createNotice(FullDiscountVO fullDiscountVO) {

        DecimalFormat df = new DecimalFormat("#.00");
        // Promotional text prompt
        StringBuilder promotionNotice = new StringBuilder();

        // Preferential threshold
        Double fullMoney = fullDiscountVO.getFullMoney();
        promotionNotice.append("full").append(df.format(fullMoney));

        // Whether to reduce cash or not
        Integer isFullMinus = fullDiscountVO.getIsFullMinus();
        // Reduce the amount of cash
        Double minusValue = fullDiscountVO.getMinusValue();
        if (isFullMinus == 1) {
            promotionNotice.append("Reduction of").append(minusValue).append("USD");
        }


        // Whether the discount
        Integer isDiscount = fullDiscountVO.getIsDiscount();
        // discount
        Double discountValue = fullDiscountVO.getDiscountValue();

        if (isDiscount == 1) {
            promotionNotice.append("play").append(discountValue).append("fold");
        }


        // Whether to give bonus points
        Integer isSendPoint = fullDiscountVO.getIsSendPoint();

        // Bonus points
        Integer pointValue = fullDiscountVO.getPointValue();

        if (isSendPoint == 1) {
            promotionNotice.append("A gift").append(pointValue).append("point");
        }

        // Whether free mail
        Integer isFreeShip = fullDiscountVO.getIsFreeShip();
        if (isFreeShip == 1) {
            promotionNotice.append("Exempt postage");
        }


        // Are there any freebies
        Integer isSendGift = fullDiscountVO.getIsSendGift();

        if (isSendGift == 1) {
            promotionNotice.append("To send gift");
        }


        // Do you give coupons?
        Integer isSendBonus = fullDiscountVO.getIsSendBonus();
        if (isSendBonus == 1) {
            CouponDO couponDO = fullDiscountVO.getCouponDO();
            if (couponDO != null) {
                promotionNotice.append("To send a coupon");
            }


        }


        return promotionNotice.toString();
    }


}
