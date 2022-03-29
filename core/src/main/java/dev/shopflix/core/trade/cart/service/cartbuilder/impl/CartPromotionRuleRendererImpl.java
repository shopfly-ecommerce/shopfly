/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.service.cartbuilder.impl;

import dev.shopflix.core.promotion.coupon.model.dos.CouponDO;
import dev.shopflix.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import dev.shopflix.core.promotion.tool.model.enums.PromotionTypeEnum;
import dev.shopflix.core.promotion.tool.model.vo.PromotionVO;
import dev.shopflix.core.trade.cart.model.enums.CartType;
import dev.shopflix.core.trade.cart.model.vo.*;
import dev.shopflix.core.trade.cart.service.CartPromotionManager;
import dev.shopflix.core.trade.cart.service.cartbuilder.CartPromotionRuleRenderer;
import dev.shopflix.core.trade.cart.service.rulebuilder.CartCouponRuleBuilder;
import dev.shopflix.core.trade.cart.service.rulebuilder.CartPromotionRuleBuilder;
import dev.shopflix.core.trade.cart.service.rulebuilder.SkuPromotionRuleBuilder;
import dev.shopflix.framework.logs.Logger;
import dev.shopflix.framework.logs.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by kingapex on 2018/12/10.
 * 购物促销信息渲染实现
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
@Service("cartPromotionRuleRenderer")
public class CartPromotionRuleRendererImpl implements CartPromotionRuleRenderer {


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private CartPromotionManager cartPromotionManager;


    @Autowired
    private List<SkuPromotionRuleBuilder> skuPromotionRuleBuilderList;

    /**
     * 目前只有一种满减是购物车级别促销活动
     */
    @Autowired
    private CartPromotionRuleBuilder cartPromotionRuleBuilder;

    @Autowired
    private CartCouponRuleBuilder cartCouponRuleBuilder;


    @Override
    public void render(List<CartVO> cartList, boolean includeCoupon) {

        //渲染规则
        this.renderRule(cartList, includeCoupon);

        logger.debug("购物车处理完促销规则结果为：");
        logger.debug(cartList.toString());

    }


    /**
     * 规则渲染
     *
     * @param cartList
     */
    private void renderRule(List<CartVO> cartList, boolean includeCoupon) {

        //获取正在进行的满优惠活动列表
        List<FullDiscountVO> fullDiscountVoList = cartPromotionManager.getFullDiscounPromotion(cartList);

        SelectedPromotionVo selectedPromotionVo = cartPromotionManager.getSelectedPromotion();

        //用户选择的单品活动
        List<PromotionVO> skuPromotionList = selectedPromotionVo.getSinglePromotionList();

        for (CartVO cart : cartList) {

            if (includeCoupon) {
                //用户选择使用的优惠券
                CouponVO coupon = selectedPromotionVo.getCoupon();
                //如果有使用的优惠券，则build rule
                if (coupon != null) {
                    PromotionRule couponRule = cartCouponRuleBuilder.build(cart, coupon);
                    cart.getRuleList().add(couponRule);
                }

            }


            //渲染单品活动
            //空过滤
            if (skuPromotionList != null) {
                //循环处理购物车的促销规则
                for (CartSkuVO cartSku : cart.getSkuList()) {

                    //如果是结算页，则跳过未选中的  update by liuyulei 2019-05-07
                    if (CartType.CHECKOUT.equals(cart.getCartType()) && cartSku.getChecked() == 0) {
                        //如果未选中，则将选择的促销活动删除
                        cartPromotionManager.delete(new Integer[]{cartSku.getSkuId()});
                        continue;
                    }

                    //计算促销规则，形成list，并压入统一的rule list 中
                    PromotionRule skuRule = oneSku(cartSku, skuPromotionList);
                    cartSku.setRule(skuRule);

                }
            }


            //渲染满减的优惠
            PromotionRule cartRule = this.onCart(cart, fullDiscountVoList);
            cart.getRuleList().add(cartRule);

        }


    }


    /**
     * 构建满减的购物车级别的规则
     *
     * @param cart
     * @param fullDiscountVoList
     * @return 如果没有合适的规则返回null
     */
    private PromotionRule onCart(CartVO cart, List<FullDiscountVO> fullDiscountVoList) {


        for (FullDiscountVO fullDiscountVO : fullDiscountVoList) {
            //生成优惠规则
            PromotionVO promotionVO = new PromotionVO();
            promotionVO.setPromotionType(PromotionTypeEnum.FULL_DISCOUNT.name());
            promotionVO.setFullDiscountVO(fullDiscountVO);
            PromotionRule rule = cartPromotionRuleBuilder.build(cart, promotionVO);

            //如果达到了满减门槛或者是在购物车页面，则显示活动提示
            //同时意谓着：如果在结算页没有达到门槛，则不显示活动提示
            if (!rule.isInvalid() || cart.getCartType().equals(CartType.CART)) {
                //满减提示
                String notice = this.createNotice(fullDiscountVO);
                cart.setPromotionNotice(notice);
            }

            return rule;
        }


        return null;
    }


    /**
     * 根据促销类型找到相应的builder
     *
     * @param promotionType 促销类型
     * @return
     */
    private SkuPromotionRuleBuilder getSkuRuleBuilder(String promotionType) {

        if (skuPromotionRuleBuilderList == null) {
            return null;
        }
        for (SkuPromotionRuleBuilder builder : skuPromotionRuleBuilderList) {
            if (builder.getPromotionType().name().equals(promotionType)) {
                return builder;
            }
        }

        return null;
    }


    private PromotionRule oneSku(CartSkuVO cartSku, List<PromotionVO> skuPromotionList) {

        for (PromotionVO promotionVo : skuPromotionList) {

            if (promotionVo.getSkuId().intValue() == cartSku.getSkuId().intValue()) {
                //sku优惠规则构建器
                SkuPromotionRuleBuilder skuRuleBuilder = this.getSkuRuleBuilder(promotionVo.getPromotionType());

                if (skuRuleBuilder == null) {
                    logger.debug(cartSku.getSkuId() + "的活动类型[" + promotionVo.getPromotionType() + "]没有找到builder");
                    continue;
                }

                //设置单品活动的选择中情况
                selectedPromotion(cartSku, promotionVo);

                //构建促销规则
                return skuRuleBuilder.build(cartSku, promotionVo);
            }

        }

        return null;
    }

    private void selectedPromotion(CartSkuVO cartSku, PromotionVO promotionVo) {

        List<CartPromotionVo> singleList = cartSku.getSingleList();
        for (CartPromotionVo cartPromotionVo : singleList) {
            if (cartPromotionVo.getPromotionType().equals(promotionVo.getPromotionType())) {
                cartPromotionVo.setIsCheck(1);
            }
        }

    }


    /**
     * 根据满减活动，生成促销提示
     *
     * @param fullDiscountVO 满减活动vo
     * @return
     */
    private String createNotice(FullDiscountVO fullDiscountVO) {

        DecimalFormat df = new DecimalFormat("#.00");
        //促销文字提示
        StringBuilder promotionNotice = new StringBuilder();

        //优惠门槛
        Double fullMoney = fullDiscountVO.getFullMoney();
        promotionNotice.append("满").append(df.format(fullMoney));

        //是否减现金
        Integer isFullMinus = fullDiscountVO.getIsFullMinus();
        //减现金的金额
        Double minusValue = fullDiscountVO.getMinusValue();
        if (isFullMinus == 1) {
            promotionNotice.append("减").append(minusValue).append("元");
        }


        //是否打折
        Integer isDiscount = fullDiscountVO.getIsDiscount();
        //折扣
        Double discountValue = fullDiscountVO.getDiscountValue();

        if (isDiscount == 1) {
            promotionNotice.append("打").append(discountValue).append("折");
        }


        //是否赠送积分
        Integer isSendPoint = fullDiscountVO.getIsSendPoint();

        //赠品积分值
        Integer pointValue = fullDiscountVO.getPointValue();

        if (isSendPoint == 1) {
            promotionNotice.append("赠").append(pointValue).append("积分");
        }

        //是否免邮
        Integer isFreeShip = fullDiscountVO.getIsFreeShip();
        if (isFreeShip == 1) {
            promotionNotice.append("免邮费");
        }


        //是否有赠品
        Integer isSendGift = fullDiscountVO.getIsSendGift();

        if (isSendGift == 1) {
            promotionNotice.append("送赠品");
        }


        //是否赠优惠券
        Integer isSendBonus = fullDiscountVO.getIsSendBonus();
        if (isSendBonus == 1) {
            CouponDO couponDO = fullDiscountVO.getCouponDO();
            if (couponDO != null) {
                promotionNotice.append("送优惠券");
            }


        }


        return promotionNotice.toString();
    }


}
