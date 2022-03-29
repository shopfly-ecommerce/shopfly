/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.service.cartbuilder.impl;

import dev.shopflix.core.promotion.tool.model.vo.PromotionVO;
import dev.shopflix.core.trade.cart.model.vo.*;
import dev.shopflix.core.trade.cart.service.CartPromotionManager;
import dev.shopflix.core.trade.cart.service.cartbuilder.CartPromotionRuleRenderer;
import dev.shopflix.core.trade.cart.service.rulebuilder.CartCouponRuleBuilder;
import dev.shopflix.core.trade.cart.service.rulebuilder.SkuPromotionRuleBuilder;
import dev.shopflix.framework.logs.Logger;
import dev.shopflix.framework.logs.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kingapex on 2018/12/10.
 * 购物促销信息渲染实现
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
@Service("pintuanCartPromotionRuleRendererImpl")
public class PintuanCartPromotionRuleRendererImpl implements CartPromotionRuleRenderer {


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private CartPromotionManager cartPromotionManager;


    @Autowired
    private List<SkuPromotionRuleBuilder> skuPromotionRuleBuilderList;

    @Autowired
    private CartCouponRuleBuilder cartCouponRuleBuilder;



    @Override
    public void render(List<CartVO> cartList, boolean includeCoupon) {

        //渲染规则
        this.renderRule(cartList,includeCoupon);

        logger.debug("购物车处理完促销规则结果为：");
        logger.debug(cartList.toString());

    }



    /**
     * 规则渲染
     *
     * @param cartList
     */
    private void renderRule(List<CartVO> cartList, boolean includeCoupon) {

        SelectedPromotionVo selectedPromotionVo = cartPromotionManager.getSelectedPromotion();





        for (CartVO cart : cartList) {

            //渲染优惠券
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
            //用户选择的单品活动
            List<PromotionVO> skuPromotionList = selectedPromotionVo.getSinglePromotionList();
            //空过滤
            if (skuPromotionList == null) {
                continue;
            }

            //循环处理购物车的促销规则
            for (CartSkuVO cartSku : cart.getSkuList()) {

                //跳过未选中的
                if (cartSku.getChecked() == 0) {
                    continue;
                }

                //计算促销规则，形成list，并压入统一的rule list 中
                PromotionRule skuRule = oneSku(cartSku, skuPromotionList);
                cartSku.setRule(skuRule);

            }


        }


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
            if (cartPromotionVo.getPromotionType().equals( promotionVo.getPromotionType())) {
                cartPromotionVo.setIsCheck(1);
            }
        }

    }





}
