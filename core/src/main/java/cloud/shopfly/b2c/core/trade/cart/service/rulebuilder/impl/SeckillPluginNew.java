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

import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillGoodsVO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.enums.PromotionTarget;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PromotionRule;
import cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.SkuPromotionRuleBuilder;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Flash sale plugin
 *
 * @version v3.0 by kingapex
 * @since v7.0.0
 */
@Component
public class SeckillPluginNew implements SkuPromotionRuleBuilder {


    @Order(4)
    @Override
    public PromotionRule build(CartSkuVO skuVO, PromotionVO promotionVO) {

        PromotionRule rule = new PromotionRule(PromotionTarget.SKU);

        SeckillGoodsVO seckillGoodsVO = promotionVO.getSeckillGoodsVO();
        if (seckillGoodsVO == null) {
            return rule;
        }

        /**
         * Overdue decision
         */
        // Start time and end time
        long startTime = seckillGoodsVO.getStartTime();
        long endTime = this.coutEndTime(startTime);

        // Is it expired?
        boolean expired = !DateUtil.inRangeOf(startTime, endTime);
        if (expired) {
            rule.setInvalid(true);
            rule.setInvalidReason("The seconds kill has expired,Is valid for:[" + DateUtil.toString(startTime, "yyyy-MM-dd HH:mm:ss") + "to" + DateUtil.toString(endTime, "yyyy-MM-dd HH:mm:ss") + "]");
            return rule;
        }


        // Default product label
        String tag = "flash";

        // Surplus available quantity in case of oversold, here to deal with it
        int num = seckillGoodsVO.getSoldQuantity() < 0 ? 0 : seckillGoodsVO.getSoldQuantity();


        // If 0 pieces enjoy promotion
        if (num == 0) {
            return rule;
        }


        // If the number of remaining offers is insufficient
        if (skuVO.getNum() > num) {
            tag = "only[" + num + "]Pieces for a flash sale";
            skuVO.setPurchaseNum(num);
        } else {
            skuVO.setPurchaseNum(skuVO.getNum());
        }


        // Moving part price
        double totalActivityPrice = CurrencyUtil.mul(seckillGoodsVO.getSeckillPrice(), skuVO.getPurchaseNum());

        // Inactive part price
        double otherTotal = 0;
        if (!skuVO.getNum().equals(skuVO.getPurchaseNum())) {
            otherTotal = CurrencyUtil.mul((skuVO.getNum() - skuVO.getPurchaseNum()), skuVO.getOriginalPrice());
        }
        totalActivityPrice = CurrencyUtil.add(totalActivityPrice, otherTotal);
        double reducedTotalPrice = CurrencyUtil.sub(skuVO.getSubtotal(), totalActivityPrice);
        double reducedPrice = CurrencyUtil.sub(skuVO.getOriginalPrice(), seckillGoodsVO.getSeckillPrice());

        // If the amount of goods is less than the discount amount, the discount amount = the amount to be paid

        if (skuVO.getSubtotal() < reducedTotalPrice) {
            reducedTotalPrice = totalActivityPrice;
        }

        rule.setReducedTotalPrice(reducedTotalPrice);


        rule.setReducedPrice(reducedPrice);
        rule.setTips("Seconds to bargain[" + seckillGoodsVO.getSeckillPrice() + "]USD");
        rule.setTag(tag);
        return rule;

    }


    /**
     * Obtain the end time based on the start time
     * The rules are for the night of the start23：59：59
     *
     * @param startTime The start time
     * @return End time stamp
     */
    private long coutEndTime(long startTime) {
        String timeStr = DateUtil.toString(startTime, "yyyy-MM-dd");
        timeStr = timeStr + " 23:59:59";

        return DateUtil.getDateline(timeStr, "yyyy-MM-dd HH:mm:ss");
    }


    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.SECKILL;
    }
}
