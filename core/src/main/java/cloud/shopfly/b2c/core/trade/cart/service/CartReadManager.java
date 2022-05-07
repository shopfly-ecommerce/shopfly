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
package cloud.shopfly.b2c.core.trade.cart.service;


import cloud.shopfly.b2c.core.trade.cart.model.vo.CartView;

/**
 * Shopping cart read-only operation business interface<br>
 * Contains the shopping cart read operation
 *
 * @author Snow
 * @version v2.0
 * 2018years03month19day21:55:53
 * @since v7.0.0
 */
public interface CartReadManager {


    /**
     * Read shopping cart data and calculate offers and prices
     *
     * @return
     */
    CartView getCartListAndCountPrice();


    /**
     * Retrieves the checked shopping list from the cache<br>
     *
     * @return
     */
    CartView getCheckedItems();


}
