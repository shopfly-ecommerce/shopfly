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
package cloud.shopfly.b2c.core.trade.cart.util;

import cloud.shopfly.b2c.core.member.model.dos.MemberCoupon;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CouponVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Shopping cart quick operation tool
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-12-01 In the afternoon7:53
 */
public class CartUtil {


    /**
     * According to the ownerid Look it up from a collectioncart
     *
     * @param ownerId  ownerid
     * @param itemList Shopping cart list
     * @return The shopping cart
     */
    public static CartVO findCart(int ownerId, List<CartVO> itemList) {
        if (itemList == null) {
            return null;
        }
        for (CartVO item : itemList) {
            if (item.getSellerId() == ownerId) {
                return item;
            }
        }
        return null;
    }


    /**
     * Set the shopping cart coupon parameters
     *
     * @return
     */
    public static CouponVO setCouponParam(MemberCoupon memberCoupon) {
        CouponVO coupon = new CouponVO();
        coupon.setAmount(memberCoupon.getCouponPrice());
        coupon.setMemberCouponId(memberCoupon.getMcId());
        coupon.setEndTime(memberCoupon.getEndTime());
        coupon.setCouponThresholdPrice(memberCoupon.getCouponThresholdPrice());
        coupon.setUseTerm("full" + new BigDecimal(memberCoupon.getCouponThresholdPrice() + "") + "available");
        return coupon;
    }


    /**
     * Find products from a list of products
     *
     * @param skuId       productid
     * @param productList Product list
     * @return Products found
     */
    public static CartSkuVO findProduct(Integer skuId, List<CartSkuVO> productList) {
        for (CartSkuVO skuVO : productList) {
            if (skuVO.getSkuId().equals(skuId)) {
                return skuVO;
            }
        }
        return null;
    }


    /**
     * Find the selected coupon
     *
     * @param cartCouponList
     * @return If blank, no coupon is selected
     */
    public static CouponVO findUsedCounpon(List<CouponVO> cartCouponList) {
        if (cartCouponList == null) {
            return null;
        }
        for (CouponVO couponVO : cartCouponList) {
            if (couponVO.getSelected() == 1) {
                return couponVO;
            }
        }

        return null;

    }
}
