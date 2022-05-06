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
import cloud.shopfly.b2c.core.trade.cart.model.vo.PriceDetailVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PromotionRule;

import java.util.List;

/**
 * 购物车价格计算器<br/>
 * 对购物车中的{@link PromotionRule}进行计算
 * 形成{@link PriceDetailVO}
 * 文档请参考：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html" >购物车架构</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
public interface CartPriceCalculator {


    /**
     * 计算购物车价格
     *
     * @param cartList
     * @return
     */
    PriceDetailVO countPrice(List<CartVO> cartList);


}
