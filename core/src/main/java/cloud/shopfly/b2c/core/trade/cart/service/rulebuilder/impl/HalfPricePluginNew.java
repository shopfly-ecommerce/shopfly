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
 * 第二件半件规则构建器
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
         * 过期判定
         */
        //开始时间和结束时间
        HalfPriceVO halfPriceVO = promotionVO.getHalfPriceVO();
        long startTime = halfPriceVO.getStartTime();
        long endTime = halfPriceVO.getEndTime();

        //是否过期了
        boolean expired = !DateUtil.inRangeOf(startTime, endTime);
        if (expired) {
            rule.setInvalid(true);
            rule.setInvalidReason("第二件半件已过期,有效期为:[" + DateUtil.toString(startTime, "yyyy-MM-dd HH:mm:ss") + "至" + DateUtil.toString(endTime, "yyyy-MM-dd HH:mm:ss") + "]");
            return rule;
        }


        // 单个商品成交价
        Double purchasePrice = skuVO.getOriginalPrice();

        //商品数量
        Integer num = skuVO.getNum();
        if (num.intValue() > 1) {
            //建立一个应用在sku的规则
            //参加活动要优惠的价格
            Double reducedPrice = CurrencyUtil.div(purchasePrice, 2);
            //双数都要减，即4件商品减2件的钱
            rule.setReducedTotalPrice(CurrencyUtil.mul(reducedPrice, num / 2));
            rule.setTips("第二件半价，减[" + CurrencyUtil.mul(reducedPrice, num / 2) + "]元");
            rule.setTag("第二件半价");
        }

        return rule;
    }


    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.HALF_PRICE;
    }

}
