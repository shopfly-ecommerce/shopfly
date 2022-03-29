/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.cart.service.cartbuilder.impl;

import com.enation.app.javashop.core.client.member.MemberCouponClient;
import com.enation.app.javashop.core.member.model.dos.MemberCoupon;
import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.enation.app.javashop.core.promotion.tool.model.vo.PromotionVO;
import com.enation.app.javashop.core.trade.cart.model.vo.CartVO;
import com.enation.app.javashop.core.trade.cart.model.vo.CouponVO;
import com.enation.app.javashop.core.trade.cart.model.vo.SelectedPromotionVo;
import com.enation.app.javashop.core.trade.cart.service.CartPromotionManager;
import com.enation.app.javashop.core.trade.cart.service.cartbuilder.CartCouponRenderer;
import com.enation.app.javashop.framework.context.UserContext;
import com.enation.app.javashop.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车优惠券渲染实现
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/18
 */
@Service
public class CartCouponRendererImpl implements CartCouponRenderer {


    @Autowired
    private MemberCouponClient memberCouponClient;

    @Autowired
    private CartPromotionManager cartPromotionManager;

    @Override
    public void render(List<CartVO> cartList) {

        //查询出这些店铺的所有优惠券
        List<MemberCoupon> couponList = (List<MemberCoupon>) this.memberCouponClient.listByCheckout(UserContext.getBuyer().getUid());

        //填充购物车的优惠券列表
        cartList.forEach(cartVO -> {
            fillOneCartCoupon(cartVO, couponList);

        });

    }


    /**
     * 填充一个购物车的优惠劵
     *
     * @param cartVo
     * @param couponList
     */
    private void fillOneCartCoupon(CartVO cartVo, List<MemberCoupon> couponList) {


        //如果购物车中包含积分商品，则无需渲染积分商品不能使用优惠券  add by liuyulei 2019-05-14
        Boolean isEnable = this.checkEnableCoupon();

        //要形成的购物车优惠券列表
        List<CouponVO> cartCouponList = new ArrayList<>();

        //查找可能存在的优惠劵
        CouponVO selectedCoupon = cartPromotionManager.getSelectedPromotion().getCoupon();

        //当前时间，判断是否在有效期使用
        long nowTime = DateUtil.getDateline();

        for (MemberCoupon memberCoupon : couponList) {

            CouponVO couponVO = new CouponVO();
            couponVO.setCouponId(memberCoupon.getCouponId());
            couponVO.setAmount(memberCoupon.getCouponPrice());
            couponVO.setUseTerm("满" + new BigDecimal(memberCoupon.getCouponThresholdPrice() + "") + "可用");
            couponVO.setMemberCouponId(memberCoupon.getMcId());
            couponVO.setEndTime(memberCoupon.getEndTime());
            couponVO.setCouponThresholdPrice(memberCoupon.getCouponThresholdPrice());

            //判断优惠券使用条件    开始    add by liuyulei 2019-05-14
            //1.判读是否存在积分商品，如果存在则不能使用优惠券
            if (!isEnable) {
                couponVO.setEnable(0);
                couponVO.setErrorMsg("当前购物车内包含积分商品，不能使用优惠券！");
                couponVO.setSelected(0);
            } else {
                //不可用条件：
                // 2.购物车价格小于优惠券门槛价格
                // 3.在有效期范围内：当前时间大于等于生效时间 && 当前时间小于等于失效时间
                if (cartVo.getPrice().getOriginalPrice() < memberCoupon.getCouponThresholdPrice()) {

                    couponVO.setEnable(0);
                    couponVO.setErrorMsg("订单金额不满足此优惠券使用金额！");
                } else if (memberCoupon.getStartTime() > nowTime
                        || memberCoupon.getEndTime() < nowTime) {
                    couponVO.setEnable(0);
                    couponVO.setErrorMsg("当前时间不在此优惠券可用时间范围内！");
                } else {
                    couponVO.setEnable(1);
                    //如果购物车存在优惠劵  当优惠券可用时才设置是否选中
                    if (selectedCoupon != null && selectedCoupon.getMemberCouponId().intValue() == couponVO.getMemberCouponId().intValue()) {
                        couponVO.setSelected(1);
                    } else {
                        couponVO.setSelected(0);
                    }
                }
            }
            //判断优惠券使用条件    结束    add by liuyulei 2019-05-14


            cartCouponList.add(couponVO);

        }

        cartVo.setCouponList(cartCouponList);

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
