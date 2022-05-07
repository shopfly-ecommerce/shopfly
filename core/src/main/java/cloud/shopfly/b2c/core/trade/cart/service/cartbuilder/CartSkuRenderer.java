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

import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuOriginVo;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl.CartSkuFilter;

import java.util.List;

/**
 * The shopping cartskuRenderer, responsible for producing a brand new onecartList<br/>
 * The material produced in this step is{@link CartSkuOriginVo}
 * Please refer to the documentation.：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html" >Shopping cart architecture</a>
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/11
 */
public interface CartSkuRenderer {

    /**
     * Apply colours to a drawingskudata
     * @param cartList
     * @param cartType
     */
    void renderSku(List<CartVO> cartList, CartType cartType);

    /**
     * Apply colours to a drawingskuData with filters
     * @param cartList
     * @param cartFilter
     * @param cartType
     */
    void renderSku(List<CartVO> cartList, CartSkuFilter cartFilter, CartType cartType);
}
