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
package cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl;

import cloud.shopfly.b2c.core.client.member.MemberCouponClient;
import cloud.shopfly.b2c.core.member.model.dos.MemberCoupon;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CouponVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.SelectedPromotionVo;
import cloud.shopfly.b2c.core.trade.cart.service.CartPromotionManager;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartCouponRenderer;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Shopping cart coupon rendering implementation
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

        // Look up all the coupons for those stores
        List<MemberCoupon> couponList = (List<MemberCoupon>) this.memberCouponClient.listByCheckout(UserContext.getBuyer().getUid());

        // Fill your shopping cart with coupons
        cartList.forEach(cartVO -> {
            fillOneCartCoupon(cartVO, couponList);

        });

    }


    /**
     * Fill a shopping cart with coupons
     *
     * @param cartVo
     * @param couponList
     */
    private void fillOneCartCoupon(CartVO cartVo, List<MemberCoupon> couponList) {


        // Coupons cannot be used without rendering bonus items if they are included in your shopping cart
        Boolean isEnable = this.checkEnableCoupon();

        // Shopping cart coupon list to form
        List<CouponVO> cartCouponList = new ArrayList<>();

        // Look for coupons that may exist
        CouponVO selectedCoupon = cartPromotionManager.getSelectedPromotion().getCoupon();

        // Current time: Determines whether the value is in the validity period
        long nowTime = DateUtil.getDateline();

        for (MemberCoupon memberCoupon : couponList) {

            CouponVO couponVO = new CouponVO();
            couponVO.setCouponId(memberCoupon.getCouponId());
            couponVO.setAmount(memberCoupon.getCouponPrice());
            couponVO.setUseTerm("full" + new BigDecimal(memberCoupon.getCouponThresholdPrice() + "") + "available");
            couponVO.setMemberCouponId(memberCoupon.getMcId());
            couponVO.setEndTime(memberCoupon.getEndTime());
            couponVO.setCouponThresholdPrice(memberCoupon.getCouponThresholdPrice());

            // Liuyulei 2019-05-14
            // 1. Check whether there is a product with points, if there is, you cannot use the coupon
            if (!isEnable) {
                couponVO.setEnable(0);
                couponVO.setErrorMsg("The current shopping cart contains points and cannot use coupons！");
                couponVO.setSelected(0);
            } else {
                // Unavailability conditions:
                // 2. Shopping cart price is less than the coupon threshold price
                // 3. Within the validity period: The current time is greater than or equal to the effective time && The current time is less than or equal to the expiration time
                if (cartVo.getPrice().getOriginalPrice() < memberCoupon.getCouponThresholdPrice()) {

                    couponVO.setEnable(0);
                    couponVO.setErrorMsg("The order amount does not meet the usage amount of this coupon！");
                } else if (memberCoupon.getStartTime() > nowTime
                        || memberCoupon.getEndTime() < nowTime) {
                    couponVO.setEnable(0);
                    couponVO.setErrorMsg("The current time is not within the available time range of this coupon！");
                } else {
                    couponVO.setEnable(1);
                    // If shopping cart has coupons, set check only when coupons are available
                    if (selectedCoupon != null && selectedCoupon.getMemberCouponId().intValue() == couponVO.getMemberCouponId().intValue()) {
                        couponVO.setSelected(1);
                    } else {
                        couponVO.setSelected(0);
                    }
                }
            }
            // Liuyulei 2019-05-14


            cartCouponList.add(couponVO);

        }

        cartVo.setCouponList(cartCouponList);

    }

    /**
     * You cant use coupons for points
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
