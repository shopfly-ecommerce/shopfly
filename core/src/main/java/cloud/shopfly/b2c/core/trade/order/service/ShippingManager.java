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
 * 运费计算业务层接口
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
public interface ShippingManager {

    /**
     * 获取购物车价格
     *
     * @param cartVO 购物车
     * @return
     */
    Double getShipPrice(CartVO cartVO);

    /**
     * 设置运费
     *
     * @param cartList 购物车集合
     */
    void setShippingPrice(List<CartVO> cartList);


    /**
     * 校验地区
     *
     * @param cartList 购物车
     * @param countryCode   国家code
     * @param stateCode      洲code
     * @return
     */
    List<CacheGoods> checkArea(List<CartVO> cartList, String countryCode,String stateCode);


}
