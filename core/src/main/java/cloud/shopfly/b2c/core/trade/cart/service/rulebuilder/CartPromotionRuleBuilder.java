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
package cloud.shopfly.b2c.core.trade.cart.service.rulebuilder;

import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PromotionRule;

/**
 * Shopping cart Discount Specificationsbuilderinterface<br/>
 * Responsible for producing shopping cart level rules<br/>
 * Please refer to the documentation.：<br/>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html#The construction of promotion rules" >The construction of promotion rules</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/13
 */
public interface CartPromotionRuleBuilder {

    /**
     * Build promotional rules for shopping carts
     *
     * @param cartVO
     * @param promotionVO
     * @return
     */
    PromotionRule build(CartVO cartVO, PromotionVO promotionVO);


    /**
     * Define promotion types
     *
     * @return
     */
    PromotionTypeEnum getPromotionType();

}
