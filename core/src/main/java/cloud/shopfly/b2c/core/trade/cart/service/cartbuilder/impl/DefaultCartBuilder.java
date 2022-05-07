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
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.*;
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

/**
 * Promotion message builder
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
public class DefaultCartBuilder implements CartBuilder {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    /**
     * Shopping cart promotion rule renderer
     */
    private CartPromotionRuleRenderer cartPromotionRuleRenderer;

    /**
     * Shopping cart price calculator
     */
    private CartPriceCalculator cartPriceCalculator;

    /**
     * Data validation
     */
    private CheckDataRebderer checkDataRebderer;

    /**
     * The shopping cartskuThe renderer
     */
    private CartSkuRenderer cartSkuRenderer;

    /**
     * Shopping cart coupon Rendering
     */
    private CartCouponRenderer cartCouponRenderer;


    /**
     * Freight price calculator
     */
    private CartShipPriceCalculator cartShipPriceCalculator;


    private List<CartVO> cartList;
    private PriceDetailVO price;
    private CartType cartType;


    public DefaultCartBuilder(CartType cartType, CartSkuRenderer cartSkuRenderer, CartPromotionRuleRenderer cartPromotionRuleRenderer, CartPriceCalculator cartPriceCalculator, CheckDataRebderer checkDataRebderer) {
        this.cartType = cartType;
        this.cartSkuRenderer = cartSkuRenderer;
        this.cartPromotionRuleRenderer = cartPromotionRuleRenderer;
        this.cartPriceCalculator = cartPriceCalculator;
        this.checkDataRebderer = checkDataRebderer;
        cartList = new ArrayList<>();
    }

    public DefaultCartBuilder(CartType cartType, CartSkuRenderer cartSkuRenderer, CartPromotionRuleRenderer cartPromotionRuleRenderer, CartPriceCalculator cartPriceCalculator, CartCouponRenderer cartCouponRenderer, CartShipPriceCalculator cartShipPriceCalculator, CheckDataRebderer checkDataRebderer) {
        this.cartType = cartType;
        this.cartSkuRenderer = cartSkuRenderer;
        this.cartPromotionRuleRenderer = cartPromotionRuleRenderer;
        this.cartPriceCalculator = cartPriceCalculator;
        this.cartCouponRenderer = cartCouponRenderer;
        this.cartShipPriceCalculator = cartShipPriceCalculator;
        this.checkDataRebderer = checkDataRebderer;
        cartList = new ArrayList<>();
    }

    /**
     * Apply colours to a drawingsku<br/>
     * This step by{@link CartSkuOriginVo}Make a brand new onecartList
     *
     * @return
     */
    @Override
    public CartBuilder renderSku() {
        cartSkuRenderer.renderSku(this.cartList, cartType);
        return this;
    }

    /**
     * Filter renderingsku<br/>
     * Can be filtered for specified conditions{@link  CartSkuFilter}The goods<br/>
     *
     * @return
     * @see CartSkuFilter
     */
    @Override
    public CartBuilder renderSku(CartSkuFilter filter) {
        cartSkuRenderer.renderSku(this.cartList, filter, cartType);
        return this;
    }


    /**
     * This step by
     * {@link SelectedPromotionVo}
     * To produce
     * {@link PromotionRule}
     *
     * @param includeCoupon
     * @return
     */
    @Override
    public CartBuilder renderPromotionRule(boolean includeCoupon) {
        cartPromotionRuleRenderer.render(cartList, includeCoupon);
        return this;
    }

    /**
     * This step passes through the output of the previous step
     * {@link PromotionRule}
     * To figure out the price:
     * {@link PriceDetailVO}
     *
     * @return
     */
    @Override
    public CartBuilder countPrice() {
        this.price = cartPriceCalculator.countPrice(cartList);
        return this;
    }


    /**
     * Call the shipping template to figure out the shipping cost, which applies only to the cart price
     *
     * @return
     */
    @Override
    public CartBuilder countShipPrice() {
        cartShipPriceCalculator.countShipPrice(cartList);
        return this;
    }


    /**
     * This step takes out the memberships available coupon and adds it to the shopping cartcouponListIn the
     *
     * @return
     */
    @Override
    public CartBuilder renderCoupon() {
        cartCouponRenderer.render(cartList);
        return this;
    }

    @Override
    public CartView build() {
        return new CartView(cartList, price);
    }

    @Override
    public CartBuilder checkData() {
        checkDataRebderer.checkData(cartList);
        return this;
    }
}
