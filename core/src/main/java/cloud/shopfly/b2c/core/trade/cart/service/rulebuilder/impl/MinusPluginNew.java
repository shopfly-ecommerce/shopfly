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
package cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.impl;

import cloud.shopfly.b2c.core.promotion.minus.model.vo.MinusVO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.enums.PromotionTarget;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PromotionRule;
import cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.SkuPromotionRuleBuilder;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.stereotype.Component;

/**
 * Single product vertical reduction plug-in
 *
 * @author mengyuanming
 * @version v1.0
 * @date 2017years8month18On the afternoon9:15:00
 * @since v6.4.0
 */
@Component
public class MinusPluginNew implements SkuPromotionRuleBuilder {


    /**
     * Single product vertical reduction activities, computing plug-ins
     */
    @Override
    public PromotionRule build(CartSkuVO skuVO, PromotionVO promotionVO) {

        PromotionRule rule = new PromotionRule(PromotionTarget.SKU);

        /**
         * Overdue decision
         */
        // Start time and end time
        MinusVO minusDO = promotionVO.getMinusVO();
        long startTime = minusDO.getStartTime();
        long endTime = minusDO.getEndTime();

        // Is it expired?
        boolean expired = !DateUtil.inRangeOf(startTime, endTime);
        if (expired) {
            rule.setInvalid(true);
            rule.setInvalidReason("The item has expired,Is valid for:[" + DateUtil.toString(startTime, "yyyy-MM-dd HH:mm:ss") + "to" + DateUtil.toString(endTime, "yyyy-MM-dd HH:mm:ss") + "]");
            return rule;
        }

        // The total amount of the merchandise discount
        Double reducedTotalPrice = CurrencyUtil.mul(minusDO.getSingleReductionValue(), skuVO.getNum());

        // The amount immediately reduced for a single item
        Double reducedPrice = minusDO.getSingleReductionValue();
//If the amount of promotional activities, rain total amount, then reduced amount= The total amount
        if (reducedTotalPrice > CurrencyUtil.mul(skuVO.getNum(), skuVO.getPurchasePrice())) {
            reducedTotalPrice = CurrencyUtil.mul(skuVO.getNum(), skuVO.getPurchasePrice());
        }
        rule.setReducedPrice(reducedPrice);
        rule.setReducedTotalPrice(reducedTotalPrice);
        rule.setTips("Item set[" + minusDO.getSingleReductionValue() + "]USD");
        rule.setTag("Item set");

        return rule;

    }


    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.MINUS;
    }
}
