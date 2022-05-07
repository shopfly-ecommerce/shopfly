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

import cloud.shopfly.b2c.core.promotion.halfprice.model.vo.HalfPriceVO;
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
 * Second half-piece rule builder
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/12
 */
@Component
public class HalfPricePluginNew implements SkuPromotionRuleBuilder {

    @Override
    public PromotionRule build(CartSkuVO skuVO, PromotionVO promotionVO) {

        PromotionRule rule = new PromotionRule(PromotionTarget.SKU);

        /**
         * Overdue decision
         */
        // Start time and end time
        HalfPriceVO halfPriceVO = promotionVO.getHalfPriceVO();
        long startTime = halfPriceVO.getStartTime();
        long endTime = halfPriceVO.getEndTime();

        // Is it expired?
        boolean expired = !DateUtil.inRangeOf(startTime, endTime);
        if (expired) {
            rule.setInvalid(true);
            rule.setInvalidReason("The second and half pieces are overdue,Is valid for:[" + DateUtil.toString(startTime, "yyyy-MM-dd HH:mm:ss") + "to" + DateUtil.toString(endTime, "yyyy-MM-dd HH:mm:ss") + "]");
            return rule;
        }


        // Transaction price of individual goods
        Double purchasePrice = skuVO.getOriginalPrice();

        // The number
        Integer num = skuVO.getNum();
        if (num.intValue() > 1) {
            // Create a rule to apply to the SKU
            // Get a good price for the event
            Double reducedPrice = CurrencyUtil.div(purchasePrice, 2);
            // We subtract both of them, so we subtract 2 from 4 items
            rule.setReducedTotalPrice(CurrencyUtil.mul(reducedPrice, num / 2));
            rule.setTips("The second piece is half price, reduce[" + CurrencyUtil.mul(reducedPrice, num / 2) + "]USD");
            rule.setTag("The second one is half price");
        }

        return rule;
    }


    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.HALF_PRICE;
    }

}
