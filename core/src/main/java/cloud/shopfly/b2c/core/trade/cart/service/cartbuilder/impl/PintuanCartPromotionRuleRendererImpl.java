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

import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.core.trade.cart.service.CartPromotionManager;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartPromotionRuleRenderer;
import cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.CartCouponRuleBuilder;
import cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.SkuPromotionRuleBuilder;
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kingapex on 2018/12/10.
 * Shopping promotion information rendering implementation
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
@Service("pintuanCartPromotionRuleRendererImpl")
public class PintuanCartPromotionRuleRendererImpl implements CartPromotionRuleRenderer {


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private CartPromotionManager cartPromotionManager;


    @Autowired
    private List<SkuPromotionRuleBuilder> skuPromotionRuleBuilderList;

    @Autowired
    private CartCouponRuleBuilder cartCouponRuleBuilder;



    @Override
    public void render(List<CartVO> cartList, boolean includeCoupon) {

        // Rendering rules
        this.renderRule(cartList,includeCoupon);

        if (logger.isDebugEnabled()){
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

        SelectedPromotionVo selectedPromotionVo = cartPromotionManager.getSelectedPromotion();





        for (CartVO cart : cartList) {

            // Render coupon
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
            // User selected single product activity
            List<PromotionVO> skuPromotionList = selectedPromotionVo.getSinglePromotionList();
            // Empty filter
            if (skuPromotionList == null) {
                continue;
            }

            // Loop through shopping cart promotion rules
            for (CartSkuVO cartSku : cart.getSkuList()) {

                // Skip the unselected ones
                if (cartSku.getChecked() == 0) {
                    continue;
                }

                // Calculate promotion rules, form a list, and press it into a unified rule list
                PromotionRule skuRule = oneSku(cartSku, skuPromotionList);
                cartSku.setRule(skuRule);

            }


        }


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
                    if(logger.isDebugEnabled()){
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
            if (cartPromotionVo.getPromotionType().equals( promotionVo.getPromotionType())) {
                cartPromotionVo.setIsCheck(1);
            }
        }

    }





}
