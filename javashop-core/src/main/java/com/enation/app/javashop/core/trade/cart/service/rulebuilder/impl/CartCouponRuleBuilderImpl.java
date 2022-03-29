/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.cart.service.rulebuilder.impl;

import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.enation.app.javashop.core.promotion.tool.model.vo.PromotionVO;
import com.enation.app.javashop.core.trade.cart.model.dos.CartDO;
import com.enation.app.javashop.core.trade.cart.model.enums.PromotionTarget;
import com.enation.app.javashop.core.trade.cart.model.vo.*;
import com.enation.app.javashop.core.trade.cart.service.CartPromotionManager;
import com.enation.app.javashop.core.trade.cart.service.rulebuilder.CartCouponRuleBuilder;
import com.enation.app.javashop.framework.util.CurrencyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券促销规则构建器实现
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/19
 */
@Service
public class CartCouponRuleBuilderImpl implements CartCouponRuleBuilder {


    @Autowired
    private CartPromotionManager cartPromotionManager;


    @Override
    public PromotionRule build(CartVO cartVO, CouponVO couponVO) {
        //建立一个应用在购物车的规则
        PromotionRule rule = new PromotionRule(PromotionTarget.CART);

        double totalPrice = this.countTotalPrice(cartVO);

        //没有达到门槛，移除掉正在使用的优惠券
        if (totalPrice < couponVO.getCouponThresholdPrice()) {
            cartPromotionManager.cleanCoupon();
            return rule;
        }
        //设置减价为优惠券金额
        rule.setUseCoupon(couponVO);

        return rule;
    }


    /**
     * 合计购物车的总价
     *
     * @param cart 某个购物车
     * @return 购物车的总价
     */
    private double countTotalPrice(CartDO cart) {

        double totalPrice = 0;
        List<CartSkuVO> skuList = cart.getSkuList();

        for (CartSkuVO skuVO : skuList) {

            if (skuVO.getChecked() == 0) {
                continue;
            }
            //选中，但是是积分商品，不累计
            if (!this.checkEnableCoupon()) {

                continue;
            }

            //合计小计，就是总价
            //最原始的总价，不能用成交价来计算门槛，因为可能被别的活动改变了
            double subTotal = skuVO.getSubtotal();

            totalPrice = CurrencyUtil.add(subTotal, totalPrice);

        }

        return totalPrice;
    }

    /**
     * 积分商品不能使用优惠券
     *
     * @return add by liuyulei 2019-05-14
     */
    private boolean checkEnableCoupon() {
        SelectedPromotionVo selectedPromotionVo = cartPromotionManager.getSelectedPromotion();

        List<PromotionVO> singlePromotionList = selectedPromotionVo.getSinglePromotionList();

        if (singlePromotionList != null && !singlePromotionList.isEmpty()) {
            for (PromotionVO promotionVO : singlePromotionList) {
                if (PromotionTypeEnum.EXCHANGE.name().equals(promotionVO.getPromotionType())) {
                    return false;
                }
            }
        }


        return true;
    }


}
