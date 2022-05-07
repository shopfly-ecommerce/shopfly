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
package cloud.shopfly.b2c.core.trade.cart.service.impl;

import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartView;
import cloud.shopfly.b2c.core.trade.cart.service.CartReadManager;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.*;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl.DefaultCartBuilder;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Shopping cart read-only operation business class
 *
 * @author Snow create in 2018/3/21
 * @version v2.0 by kingapex
 * This is done through the builder pattern. Please refer to the architecture documentation：
 * http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html
 * @since v7.0.0
 */
@Service
public class CartReadManagerImpl implements CartReadManager {


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * Shopping cart promotion renderer
     */
    @Autowired
    private CartPromotionRuleRenderer cartPromotionRuleRenderer;

    /**
     * Shopping cart price calculator
     */
    @Autowired
    private CartPriceCalculator cartPriceCalculator;

    /**
     * The shopping cartskuData renderer
     */
    @Autowired
    @Qualifier(value = "cartSkuRendererImpl")
    private CartSkuRenderer cartSkuRenderer;

    /**
     * Data validation
     */
    @Autowired
    private CheckDataRebderer checkDataRebderer;

    /**
     * Shopping cart coupon renderer
     */
    @Autowired
    private CartCouponRenderer cartCouponRenderer;

    /**
     * Shopping cart freight price calculator
     */
    @Autowired
    private CartShipPriceCalculator cartShipPriceCalculator;


    @Override
    public CartView getCartListAndCountPrice() {

        // Call CartView production flow line for production
        CartBuilder cartBuilder = new DefaultCartBuilder(CartType.CART, cartSkuRenderer, cartPromotionRuleRenderer, cartPriceCalculator, checkDataRebderer);

        /**
         * The production process：Apply colours to a drawingsku->checkskuThe validity of->Apply colours to a drawing促销规则->Calculate the price->Produce the finished product
         * Production process Description： checkskuValidity must precede rendering of the promotion rules. If the item participating in the maxed out campaign is invalid, then the maxed out campaign does not need to be rendered
         * update by liuyulei 2019-05-17
         */
        CartView cartView = cartBuilder.renderSku().checkData().renderPromotionRule(false).countPrice().build();
        List<CartVO> itemList = cartView.getCartList();
        processChecked(itemList);
        return cartView;
    }


    /**
     * Handles shopping cart checks
     * According to the selection of each commodity to set up the shop is all selected
     *
     * @param itemList
     */
    private void processChecked(List<CartVO> itemList) {
        for (CartVO cartVO : itemList) {
            // Set the default to select all items in the store
            cartVO.setChecked(1);
            cartVO.setInvalid(0);

            // If the cart has a valid item
            Boolean notInvalid = false;

            for (CartSkuVO skuVO : cartVO.getSkuList()) {
                // If the item is not selected and it is not a valid item
                if (skuVO.getChecked() == 0 && skuVO.getInvalid() == 0) {
                    cartVO.setChecked(0);
                }
                if (skuVO.getInvalid() == 0) {
                    notInvalid = true;
                }
            }

            // If all items are invalid && cart status is selected
            if (!notInvalid && cartVO.getChecked() == 1) {
                cartVO.setInvalid(1);
            }
            if (logger.isDebugEnabled()){
                this.logger.info("Shopping cart selected processing results===》：");
                this.logger.info(cartVO.toString());
            }
        }
    }


    @Override
    public CartView getCheckedItems() {

        // Call CartView production flow line for production
        CartBuilder cartBuilder = new DefaultCartBuilder(CartType.CHECKOUT, cartSkuRenderer, cartPromotionRuleRenderer, cartPriceCalculator, cartCouponRenderer, cartShipPriceCalculator, checkDataRebderer);

        /**
         * The production process：Apply colours to a drawingsku->checkskuThe validity of->Apply colours to a drawing促销规则(Calculate coupons）->Calculate the freight->Calculate the price-> Apply colours to a drawing优惠券->Produce the finished product
         * Production process Description： checkskuValidity must precede rendering of the promotion rules. If the item participating in the maxed out campaign is invalid, then the maxed out campaign does not need to be rendered
         * update by liuyulei 2019-05-17
         */
        CartView cartView = cartBuilder.renderSku().checkData().renderPromotionRule(true).countShipPrice().countPrice().renderCoupon().build();
        List<CartVO> cartList = cartView.getCartList();


        // Invalid shopping cart clear
        List<CartVO> invalidCart = new ArrayList<>();

        for (CartVO cart : cartList) {
            List<CartSkuVO> skuList = cart.getSkuList();
            List<CartSkuVO> newSkuList = new ArrayList<CartSkuVO>();

            for (CartSkuVO skuVO : skuList) {
                // Only the selected ones are pressed in
                if (skuVO.getChecked() == 1) {
                    newSkuList.add(skuVO);
                }
            }
            cart.setSkuList(newSkuList);

            if (newSkuList.size() == 0) {
                invalidCart.add(cart);
            }
        }
        // Get rid of shopping carts that dont have items to buy
        for (CartVO cart : invalidCart) {
            cartList.remove(cart);
        }

        return cartView;

    }


}
