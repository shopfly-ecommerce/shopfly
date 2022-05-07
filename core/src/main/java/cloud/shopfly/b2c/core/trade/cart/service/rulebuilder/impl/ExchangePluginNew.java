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

import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.enums.PromotionTarget;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PromotionRule;
import cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.SkuPromotionRuleBuilder;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import org.springframework.stereotype.Component;

/**
 * Point exchange plug-in
 *
 * @author Snow create in 2018/5/25
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class ExchangePluginNew implements SkuPromotionRuleBuilder {


    @Override
    public PromotionRule build(CartSkuVO skuVO, PromotionVO promotionVO) {

        PromotionRule rule = new PromotionRule(PromotionTarget.SKU);

        ExchangeDO exchangeDO = promotionVO.getExchange();

        // Gets the current purchase quantity of this item
        int num = skuVO.getNum();
        if (exchangeDO != null) {

            // The amount of goods to be exchanged (after a discount for a single item)
            Double exchangeMoney = exchangeDO.getExchangeMoney();
            // Points needed to redeem goods
            int exchangePoint = exchangeDO.getExchangePoint();

            // Points needed for this product (points needed for this product * quantity)
            int usePoint = CurrencyUtil.mul(exchangePoint, num).intValue();

            // Amount required for this commodity (Amount required for commodity * quantity)
            double useMoney = CurrencyUtil.mul(exchangeMoney, num);

            double oldTotal = skuVO.getSubtotal();

            // Calculate how much money you have saved relative to the original price
            Double reducedTotalPrice = CurrencyUtil.sub(oldTotal, useMoney);
            Double reducedPrice = CurrencyUtil.sub(skuVO.getOriginalPrice(), exchangeMoney);

            // Record to rule
            rule.setReducedTotalPrice(reducedTotalPrice);
            rule.setReducedPrice(reducedPrice);
            rule.setUsePoint(usePoint);
            rule.setTips("use[" + usePoint + "]An integral and[" + useMoney + "]Yuan convertibility");
            rule.setTag("Status");

        }

        return rule;

    }


    @Override
    public PromotionTypeEnum getPromotionType() {

        return PromotionTypeEnum.EXCHANGE;
    }

}
