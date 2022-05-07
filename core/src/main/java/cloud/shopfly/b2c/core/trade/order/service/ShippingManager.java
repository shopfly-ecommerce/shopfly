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
package cloud.shopfly.b2c.core.trade.order.service;

import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;

import java.util.List;

/**
 * Freight calculation business layer interface
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
public interface ShippingManager {

    /**
     * Get shopping cart price
     *
     * @param cartVO The shopping cart
     * @return
     */
    Double getShipPrice(CartVO cartVO);

    /**
     * Set up the freight
     *
     * @param cartList Shopping cart collection
     */
    void setShippingPrice(List<CartVO> cartList);


    /**
     * Check area
     *
     * @param cartList The shopping cart
     * @param countryCode   countriescode
     * @param stateCode      chaucode
     * @return
     */
    List<CacheGoods> checkArea(List<CartVO> cartList, String countryCode,String stateCode);


}
