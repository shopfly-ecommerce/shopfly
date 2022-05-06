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

import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;

/**
 * 购物车sku过滤器<br/>
 * 指定一定的条件，进行过滤购物车的sku。<br/>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/19
 */
public interface CartSkuFilter {

    /**
     * 指定要过滤的条件
     *
     * @param cartSkuVO 当前的sku，做要比对的对象
     * @return 返回true/false来决定是否过滤
     */
    boolean accept(CartSkuVO cartSkuVO);
}
