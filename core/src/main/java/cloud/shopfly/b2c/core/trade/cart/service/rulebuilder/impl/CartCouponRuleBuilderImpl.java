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

import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.dos.CartDO;
import cloud.shopfly.b2c.core.trade.cart.model.enums.PromotionTarget;
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.core.trade.cart.service.CartPromotionManager;
import cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.CartCouponRuleBuilder;
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
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
