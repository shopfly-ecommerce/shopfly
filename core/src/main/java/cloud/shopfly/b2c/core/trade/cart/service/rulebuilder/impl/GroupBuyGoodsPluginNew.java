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

import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyGoodsVO;
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
 * Created by kingapex on 2018/12/12.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/12
 */

@Component
public class GroupBuyGoodsPluginNew implements SkuPromotionRuleBuilder {


    @Override
    public PromotionRule build(CartSkuVO skuVO, PromotionVO promotionVO) {

        // Establish a rule that applies to merchandise
        PromotionRule rule = new PromotionRule(PromotionTarget.SKU);
        GroupbuyGoodsVO groupbuyGoodsDO = promotionVO.getGroupbuyGoodsVO();
        if (groupbuyGoodsDO == null) {
            return rule;
        }


        // Start time and end time
        long startTime = groupbuyGoodsDO.getStartTime();
        long endTime = groupbuyGoodsDO.getEndTime();

        // Is it expired?
        boolean expired = !DateUtil.inRangeOf(startTime, endTime);
        if (expired) {
            rule.setInvalid(true);
            rule.setInvalidReason("Group purchase has expired,Is valid for:[" + DateUtil.toString(startTime, "yyyy-MM-dd HH:mm:ss") + "to" + DateUtil.toString(endTime, "yyyy-MM-dd HH:mm:ss") + "]");

            return rule;
        }


        // Default product label
        String tag = "Group-buying activities";

        // The number sold out
        int soldQuantity = groupbuyGoodsDO.getGoodsNum();

        // Surplus available quantity in case of oversold, here to deal with it
        int num = soldQuantity < 0 ? 0 : soldQuantity;


        // If 0 pieces enjoy promotion
        if (num == 0) {
            return rule;
        }


        // Deal with limit quantity
        if (groupbuyGoodsDO.getLimitNum() == 0 || groupbuyGoodsDO.getLimitNum() > num) {
            groupbuyGoodsDO.setLimitNum(num);
        }

        // If the purchase quantity is heavy limit quantity, and the limit quantity is not equal to 0
        if (skuVO.getNum() > groupbuyGoodsDO.getLimitNum()) {
            tag = "only[" + groupbuyGoodsDO.getLimitNum() + "]Enjoy the group purchase activities";
            skuVO.setPurchaseNum(groupbuyGoodsDO.getLimitNum());
        } else {
            skuVO.setPurchaseNum(skuVO.getNum());
        }

        // The original subtotal
        double subtotal = skuVO.getSubtotal();

        // Discount subtotal according to group purchase price
        double discountTotal = CurrencyUtil.mul(skuVO.getPurchaseNum(), groupbuyGoodsDO.getPrice());
        // Non-group purchase partial price
        double otherTotal = 0;
        if (!skuVO.getNum().equals(skuVO.getPurchaseNum())) {
            otherTotal = CurrencyUtil.mul((skuVO.getNum() - skuVO.getPurchaseNum()), skuVO.getOriginalPrice());
        }
        discountTotal = CurrencyUtil.add(discountTotal, otherTotal);

        double reducedTotalPrice = CurrencyUtil.sub(subtotal, discountTotal);

        double reducedPrice = CurrencyUtil.sub(skuVO.getOriginalPrice(), groupbuyGoodsDO.getPrice());
        rule.setReducedTotalPrice(reducedTotalPrice);
        rule.setReducedPrice(reducedPrice);
        rule.setTips("group[" + groupbuyGoodsDO.getPrice() + "]");
        rule.setTag(tag);

        return rule;

    }


    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.GROUPBUY;
    }
}
