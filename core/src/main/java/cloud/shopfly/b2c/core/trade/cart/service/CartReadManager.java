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
 * 购物车只读操作业务接口<br>
 * 包含对购物车读取操作
 *
 * @author Snow
 * @version v2.0
 * 2018年03月19日21:55:53
 * @since v7.0.0
 */
public interface CartReadManager {


    /**
     * 读取购物车数据，并计算优惠和价格
     *
     * @return
     */
    CartView getCartListAndCountPrice();


    /**
     * 由缓存中取出已勾选的购物列表<br>
     *
     * @return
     */
    CartView getCheckedItems();


}
