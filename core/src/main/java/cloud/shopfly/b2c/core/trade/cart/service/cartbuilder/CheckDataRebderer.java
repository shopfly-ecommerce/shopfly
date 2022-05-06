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

import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;

import java.util.List;

/**
 * 数据正确性校验器
 *
 * @author zh
 * @version v7.0
 * @date 18/12/27 上午10:05
 * @since v7.0
 */
public interface CheckDataRebderer {
    /**
     * 数据正确性校验
     *
     * @param cartList 购物车数据
     */
    void checkData(List<CartVO> cartList);
}
