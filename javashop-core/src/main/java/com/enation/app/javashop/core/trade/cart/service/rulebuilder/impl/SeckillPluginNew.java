/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.cart.service.rulebuilder.impl;

import com.enation.app.javashop.core.promotion.seckill.model.vo.SeckillGoodsVO;
import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.enation.app.javashop.core.promotion.tool.model.vo.PromotionVO;
import com.enation.app.javashop.core.trade.cart.model.enums.PromotionTarget;
import com.enation.app.javashop.core.trade.cart.model.vo.CartSkuVO;
import com.enation.app.javashop.core.trade.cart.model.vo.PromotionRule;
import com.enation.app.javashop.core.trade.cart.service.rulebuilder.SkuPromotionRuleBuilder;
import com.enation.app.javashop.framework.util.CurrencyUtil;
import com.enation.app.javashop.framework.util.DateUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 限时抢购插件
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
         * 过期判定
         */
        //开始时间和结束时间
        long startTime = seckillGoodsVO.getStartTime();
        long endTime = this.coutEndTime(startTime);

        //是否过期了
        boolean expired = !DateUtil.inRangeOf(startTime, endTime);
        if (expired) {
            rule.setInvalid(true);
            rule.setInvalidReason("秒杀已过期,有效期为:[" + DateUtil.toString(startTime, "yyyy-MM-dd HH:mm:ss") + "至" + DateUtil.toString(endTime, "yyyy-MM-dd HH:mm:ss") + "]");
            return rule;
        }


        //默认商品标签
        String tag = "限时抢购";

        //剩余可售数量 万一发生超卖，这里处理一下
        int num = seckillGoodsVO.getSoldQuantity() < 0 ? 0 : seckillGoodsVO.getSoldQuantity();


        //如果0件享受促销
        if (num == 0) {
            return rule;
        }


        //如果 剩余优惠数量不足
        if (skuVO.getNum() > num) {
            tag = "仅[" + num + "]件享限时抢购";
            skuVO.setPurchaseNum(num);
        } else {
            skuVO.setPurchaseNum(skuVO.getNum());
        }


        //活动部分价格
        double totalActivityPrice = CurrencyUtil.mul(seckillGoodsVO.getSeckillPrice(), skuVO.getPurchaseNum());

        //非活动部分价格
        double otherTotal = 0;
        if (!skuVO.getNum().equals(skuVO.getPurchaseNum())) {
            otherTotal = CurrencyUtil.mul((skuVO.getNum() - skuVO.getPurchaseNum()), skuVO.getOriginalPrice());
        }
        totalActivityPrice = CurrencyUtil.add(totalActivityPrice, otherTotal);
        double reducedTotalPrice = CurrencyUtil.sub(skuVO.getSubtotal(), totalActivityPrice);
        double reducedPrice = CurrencyUtil.sub(skuVO.getOriginalPrice(), seckillGoodsVO.getSeckillPrice());

        //如果商品金额 小于 优惠金额    则减免金额 = 需要支付的金额

        if (skuVO.getSubtotal() < reducedTotalPrice) {
            reducedTotalPrice = totalActivityPrice;
        }

        rule.setReducedTotalPrice(reducedTotalPrice);


        rule.setReducedPrice(reducedPrice);
        rule.setTips("秒杀价[" + seckillGoodsVO.getSeckillPrice() + "]元");
        rule.setTag(tag);
        return rule;

    }


    /**
     * 根据开始时间获取结束时间
     * 规则是开始时间当天晚的23：59：59
     *
     * @param startTime 开始时间
     * @return 结束时间戳
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
