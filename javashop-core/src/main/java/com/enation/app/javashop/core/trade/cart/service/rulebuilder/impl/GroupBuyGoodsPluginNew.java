/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.cart.service.rulebuilder.impl;

import com.enation.app.javashop.core.promotion.groupbuy.model.vo.GroupbuyGoodsVO;
import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.enation.app.javashop.core.promotion.tool.model.vo.PromotionVO;
import com.enation.app.javashop.core.trade.cart.model.enums.PromotionTarget;
import com.enation.app.javashop.core.trade.cart.model.vo.CartSkuVO;
import com.enation.app.javashop.core.trade.cart.model.vo.PromotionRule;
import com.enation.app.javashop.core.trade.cart.service.rulebuilder.SkuPromotionRuleBuilder;
import com.enation.app.javashop.framework.util.CurrencyUtil;
import com.enation.app.javashop.framework.util.DateUtil;
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

        //建立一个应用在商品的规则
        PromotionRule rule = new PromotionRule(PromotionTarget.SKU);
        GroupbuyGoodsVO groupbuyGoodsDO = promotionVO.getGroupbuyGoodsVO();
        if (groupbuyGoodsDO == null) {
            return rule;
        }


        //开始时间和结束时间
        long startTime = groupbuyGoodsDO.getStartTime();
        long endTime = groupbuyGoodsDO.getEndTime();

        //是否过期了
        boolean expired = !DateUtil.inRangeOf(startTime, endTime);
        if (expired) {
            rule.setInvalid(true);
            rule.setInvalidReason("团购已过期,有效期为:[" + DateUtil.toString(startTime, "yyyy-MM-dd HH:mm:ss") + "至" + DateUtil.toString(endTime, "yyyy-MM-dd HH:mm:ss") + "]");

            return rule;
        }


        //默认商品标签
        String tag = "团购活动";

        //售空数量
        int soldQuantity = groupbuyGoodsDO.getGoodsNum();

        //剩余可售数量 万一发生超卖，这里处理一下
        int num = soldQuantity < 0 ? 0 : soldQuantity;


        //如果0件享受促销
        if (num == 0) {
            return rule;
        }


        //处理限购数量
        if (groupbuyGoodsDO.getLimitNum() == 0 || groupbuyGoodsDO.getLimitNum() > num) {
            groupbuyGoodsDO.setLimitNum(num);
        }

        //如果购买数量大雨限购数量，并且限购数量不等于0
        if (skuVO.getNum() > groupbuyGoodsDO.getLimitNum()) {
            tag = "仅[" + groupbuyGoodsDO.getLimitNum() + "]件享团购活动";
            skuVO.setPurchaseNum(groupbuyGoodsDO.getLimitNum());
        } else {
            skuVO.setPurchaseNum(skuVO.getNum());
        }

        //原价小计
        double subtotal = skuVO.getSubtotal();

        //按团购价算 优惠小计
        double discountTotal = CurrencyUtil.mul(skuVO.getPurchaseNum(), groupbuyGoodsDO.getPrice());
        //非团购部分价格
        double otherTotal = 0;
        if (!skuVO.getNum().equals(skuVO.getPurchaseNum())) {
            otherTotal = CurrencyUtil.mul((skuVO.getNum() - skuVO.getPurchaseNum()), skuVO.getOriginalPrice());
        }
        discountTotal = CurrencyUtil.add(discountTotal, otherTotal);

        double reducedTotalPrice = CurrencyUtil.sub(subtotal, discountTotal);

        double reducedPrice = CurrencyUtil.sub(skuVO.getOriginalPrice(), groupbuyGoodsDO.getPrice());
        rule.setReducedTotalPrice(reducedTotalPrice);
        rule.setReducedPrice(reducedPrice);
        rule.setTips("团购价[" + groupbuyGoodsDO.getPrice() + "]");
        rule.setTag(tag);

        return rule;

    }


    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.GROUPBUY;
    }
}
