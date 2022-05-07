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
package cloud.shopfly.b2c.core.trade.cart.service.cartbuilder;

import cloud.shopfly.b2c.core.trade.cart.model.vo.CartView;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl.CartSkuFilter;

/**
 * Shopping cart builder<br/>
 * His goal was to produce one{@link CartView}
 * Please refer to the documentation.：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html" >Shopping cart architecture</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
public interface CartBuilder {

    /**
     * Render shopping cartskuinformation
     *
     * @return
     */
    CartBuilder renderSku();

    /**
     * Render with filterssku
     *
     * @param filter
     * @return
     */
    CartBuilder renderSku(CartSkuFilter filter);

    /**
     * Render promotional information
     *
     * @param includeCoupon Whether or not to include the calculation of coupons
     * @return builder
     */
    CartBuilder renderPromotionRule(boolean includeCoupon);


    /**
     * Calculate shopping cart prices
     *
     * @return
     */
    CartBuilder countPrice();


    /**
     * Calculate the freight
     *
     * @return builder
     */
    CartBuilder countShipPrice();

    /**
     * Calculate coupon fees
     *
     * @return builder
     */
    CartBuilder renderCoupon();

    /**
     * Test data correctness
     *
     * @return
     */
    CartBuilder checkData();

    /**
     * Building the shopping cart View
     *
     * @return
     */
    CartView build();


}
