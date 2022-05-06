/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 单品立减插件
 *
 * @author mengyuanming
 * @version v1.0
 * @date 2017年8月18日下午9:15:00
 * @since v6.4.0
 */
@Component
public class MinusPluginNew implements SkuPromotionRuleBuilder {


    /**
     * 单品立减活动，计算插件
     */
    @Override
    public PromotionRule build(CartSkuVO skuVO, PromotionVO promotionVO) {

        PromotionRule rule = new PromotionRule(PromotionTarget.SKU);

        /**
         * 过期判定
         */
        //开始时间和结束时间
        MinusVO minusDO = promotionVO.getMinusVO();
        long startTime = minusDO.getStartTime();
        long endTime = minusDO.getEndTime();

        //是否过期了
        boolean expired = !DateUtil.inRangeOf(startTime, endTime);
        if (expired) {
            rule.setInvalid(true);
            rule.setInvalidReason("单品立减已过期,有效期为:[" + DateUtil.toString(startTime, "yyyy-MM-dd HH:mm:ss") + "至" + DateUtil.toString(endTime, "yyyy-MM-dd HH:mm:ss") + "]");
            return rule;
        }

        //商品优惠的总金额
        Double reducedTotalPrice = CurrencyUtil.mul(minusDO.getSingleReductionValue(), skuVO.getNum());

        //单品立减的金额
        Double reducedPrice = minusDO.getSingleReductionValue();
//如果活动促销金额，大雨 总金额，则减免金额 = 总金额
        if (reducedTotalPrice > CurrencyUtil.mul(skuVO.getNum(), skuVO.getPurchasePrice())) {
            reducedTotalPrice = CurrencyUtil.mul(skuVO.getNum(), skuVO.getPurchasePrice());
        }
        rule.setReducedPrice(reducedPrice);
        rule.setReducedTotalPrice(reducedTotalPrice);
        rule.setTips("单品立减[" + minusDO.getSingleReductionValue() + "]元");
        rule.setTag("单品立减");

        return rule;

    }


    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.MINUS;
    }
}
