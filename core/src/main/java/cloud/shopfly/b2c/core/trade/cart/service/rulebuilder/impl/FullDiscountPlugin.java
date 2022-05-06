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

import cloud.shopfly.b2c.core.goods.model.enums.GoodsType;
import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.coupon.service.CouponManager;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.service.FullDiscountGiftManager;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.dos.CartDO;
import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import cloud.shopfly.b2c.core.trade.cart.model.enums.PromotionTarget;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CouponVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PromotionRule;
import cloud.shopfly.b2c.core.trade.cart.service.rulebuilder.CartPromotionRuleBuilder;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;


/**
 * Created by kingapex on 2018/12/12.
 * 满优惠插件
 *
 * @author kingapex
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class FullDiscountPlugin implements CartPromotionRuleBuilder {

    @Autowired
    private FullDiscountGiftManager fullDiscountGiftManager;

    @Autowired
    private CouponManager couponManager;

    @Override
    public PromotionRule build(CartVO cart, PromotionVO promotionVO) {


        //建立一个应用在购物车的规则
        PromotionRule rule = new PromotionRule(PromotionTarget.CART);


        FullDiscountVO fullDiscountVO = promotionVO.getFullDiscountVO();

        //计算购物车的总价
        double totalPrice = this.countTotalPrice(cart, fullDiscountVO);

        //达到满减的门槛
        if (fullDiscountVO.getFullMoney() != null && fullDiscountVO.getFullMoney() <= totalPrice) {

            //满减
            this.manJian(fullDiscountVO, rule);

            //满折
            this.manZhe(totalPrice, fullDiscountVO, rule);

            //免运费
            this.freeShip(fullDiscountVO, rule);

            //送赠品
            this.giveGift(fullDiscountVO, rule);

            //送优惠券
            this.giveCoupon(fullDiscountVO, rule);

            //送积分
            this.givePoint(fullDiscountVO, rule);

            setTag(fullDiscountVO.getFdId(), cart, fullDiscountVO.getFullMoney(), true);


        } else {

            //没有达到门槛购物车也是要设置的，以便提示用户此商品是参与活动的
            if (cart.getCartType().equals(CartType.CART)) {
                setTag(fullDiscountVO.getFdId(), cart, fullDiscountVO.getFullMoney(), false);
            }

            //如果没有达到满减则设置为已失效
            rule.setInvalid(true);
        }

        return rule;


    }

    private void setTag(int activeId, CartVO cartVO, double fullMoney, boolean enable) {

        DecimalFormat df = new DecimalFormat("#.00");
        cartVO.getSkuList().forEach(cartSkuVO -> {
            //有满减活动，虽然没达到门槛也要显示tag
            //如果是积分商品，则不能显示
            if (cartSkuVO.getGroupList().size() != 0 && !GoodsType.POINT.name().equals(cartSkuVO.getGoodsType())) {

                cartSkuVO.getGroupList().forEach(cartPromotionVo -> {
                    if (cartPromotionVo.getActivityId().equals(activeId)) {

                        //达到门槛了，则选中
                        if (enable) {
                            cartPromotionVo.setIsCheck(1);
                        }
                        //cart不管有没有达到门槛，都要显示标签,checkout则要达到门槛才显示
                        if (cartVO.getCartType().equals(CartType.CART) || enable) {
                            cartSkuVO.getPromotionTags().add("满" + df.format(fullMoney) + "优惠");
                        }

                    }
                });


            }
        });

    }


    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.FULL_DISCOUNT;
    }


    /**
     * 满减
     *
     * @param fullDiscountVO
     * @param rule
     */
    private void manJian(FullDiscountVO fullDiscountVO, PromotionRule rule) {
        //满减优惠计算
        if (fullDiscountVO.getIsFullMinus() != null && fullDiscountVO.getIsFullMinus() == 1 && fullDiscountVO.getMinusValue() != null) {
            //设置规则要减掉的金额
            rule.setReducedTotalPrice(fullDiscountVO.getMinusValue());
            rule.setTips("减" + fullDiscountVO.getMinusValue() + "元");
        }
    }


    /**
     * 满折
     *
     * @param totalPrice
     * @param fullDiscountVO
     * @param rule
     */
    private void manZhe(double totalPrice, FullDiscountVO fullDiscountVO, PromotionRule rule) {

        //满折优惠计算
        if (fullDiscountVO.getIsDiscount() != null && fullDiscountVO.getIsDiscount() == 1 && fullDiscountVO.getDiscountValue() != null) {

            //处理折扣比：不能大于10，再用10减就为要减掉的折扣比
            double discountValue = fullDiscountVO.getDiscountValue();
            discountValue = discountValue > 10 ? 10 : discountValue;
            discountValue = 10 - discountValue;
            //计算折扣比
            double discount = CurrencyUtil.mul(discountValue, 0.1);

            //算出要减多少钱
            double reducedPrice = CurrencyUtil.mul(totalPrice, discount);

            //设置规则要减掉的金额
            rule.setReducedTotalPrice(reducedPrice);
            rule.setTips("折后减" + reducedPrice + "元");


        }

    }


    public static void main(String[] args) {


        System.out.println(DateUtil.toString(1546073780L, "yyyy-MM-dd"));
    }


    /**
     * 免运费
     *
     * @param fullDiscountVO
     * @param rule
     */
    private void freeShip(FullDiscountVO fullDiscountVO, PromotionRule rule) {
        ///免运费优惠计算
        if (fullDiscountVO.getIsFreeShip() != null && fullDiscountVO.getIsFreeShip() == 1) {
            rule.setFreeShipping(true);
            rule.setTips("免运费");

        }
    }


    /**
     * 送赠品
     *
     * @param fullDiscountVO
     * @param rule
     */
    private void giveGift(FullDiscountVO fullDiscountVO, PromotionRule rule) {
        //判断是否赠送赠品
        if (fullDiscountVO.getIsSendGift() != null && fullDiscountVO.getIsSendGift() == 1 && fullDiscountVO.getGiftId() != null) {
            FullDiscountGiftDO giftDO = fullDiscountGiftManager.getModel(fullDiscountVO.getGiftId());

            //可用库存大于0才显示
            if (giftDO.getEnableStore() > 0) {

                rule.setGoodsGift(giftDO);
                rule.setTips("送赠品[" + giftDO.getGiftName() + "]");
            } else {
                giftDO.setGiftName(giftDO.getGiftName() + "(赠品已送完)");
                rule.setGoodsGift(giftDO);
                rule.setTips("赠品已送完");
            }

        }
    }


    /**
     * 送积分
     *
     * @param fullDiscountVO
     * @param rule
     */
    private void givePoint(FullDiscountVO fullDiscountVO, PromotionRule rule) {

        //判断是否赠送积分
        if (fullDiscountVO.getIsSendPoint() != null && fullDiscountVO.getIsSendPoint() == 1 && fullDiscountVO.getPointValue() != null) {
            rule.setPointGift(fullDiscountVO.getPointValue());
            rule.setTips("送积分[" + fullDiscountVO.getPointValue() + "]");

        }
    }


    /**
     * 送优惠券
     *
     * @param fullDiscountVO
     * @param rule
     */
    private void giveCoupon(FullDiscountVO fullDiscountVO, PromotionRule rule) {
        //判断是否赠品优惠券
        if (fullDiscountVO.getIsSendBonus() != null && fullDiscountVO.getIsSendBonus() == 1 && fullDiscountVO.getBonusId() != null) {

            CouponDO couponDO = this.couponManager.getModel(fullDiscountVO.getBonusId());

            CouponVO couponVO = new CouponVO();
            couponVO.setCouponId(couponDO.getCouponId());
            couponVO.setUseTerm(couponDO.getTitle());
            couponVO.setAmount(couponDO.getCouponPrice());
            couponVO.setCouponThresholdPrice(couponDO.getCouponThresholdPrice());
            //剩余可领取数量大于0,显示赠送优惠券,否则不显示
            if (CurrencyUtil.sub(couponDO.getCreateNum(), couponDO.getReceivedNum()) <= 0) {
                couponVO.setErrorMsg("(优惠券已送完)");
                rule.setCouponGift(couponVO);
                rule.setTips("优惠券[" + couponVO.getUseTerm() + "，减" + couponVO.getAmount() + "]已送完");
            } else {
                fullDiscountVO.setCouponDO(couponDO);
                rule.setCouponGift(couponVO);
                rule.setTips("送优惠券[" + couponVO.getUseTerm() + "，减" + couponVO.getAmount() + "]");

            }
        }
    }


    /**
     * 合计购物车的总价
     *
     * @param cart 某个购物车
     * @return 购物车的总价
     */
    private double countTotalPrice(CartDO cart, FullDiscountVO fullDiscountVO) {

        double totalPrice = 0;
        List<CartSkuVO> skuList = cart.getSkuList();

        for (CartSkuVO skuVO : skuList) {

            if (skuVO.getChecked() == 0) {
                continue;
            }

            //积分商品不参加其它活动
            //参考需求：http://doc.javamall.com.cn/current/demand/xu-qiu-shuo-ming/cu-xiao.html#重叠关系说明
            if (GoodsType.POINT.name().equals(skuVO.getGoodsType())) {
                continue;
            }

            if (!fullDiscountVO.getGoodsIdList().contains(-1) && !fullDiscountVO.getGoodsIdList().contains(skuVO.getGoodsId())) {
                continue;
            }

            //合计小计，就是总价
            //废弃：最原始的总价，不能用成交价来计算门槛，因为可能被别的活动改变了
            //新：需求是先计算第二件半价，单品立减后计算满折
            double subTotal = skuVO.getSubtotal();
            PromotionRule rule = skuVO.getRule();
            double reducedTotalPrice = 0.0;
            if (rule != null) {
                reducedTotalPrice = rule.getReducedTotalPrice();
            }

            totalPrice = CurrencyUtil.add(CurrencyUtil.sub(subTotal, reducedTotalPrice), totalPrice);

        }

        return totalPrice;
    }


}
