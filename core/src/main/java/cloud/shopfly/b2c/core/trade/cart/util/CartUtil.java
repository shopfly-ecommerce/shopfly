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
 * 购物车快捷操作工具
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-12-01 下午7:53
 */
public class CartUtil {


    /**
     * 根据属主id 从一个集合中查找cart
     *
     * @param ownerId  属主id
     * @param itemList 购物车列表
     * @return 购物车
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
     * 设置购物车优惠券的参数
     *
     * @return
     */
    public static CouponVO setCouponParam(MemberCoupon memberCoupon) {
        CouponVO coupon = new CouponVO();
        coupon.setAmount(memberCoupon.getCouponPrice());
        coupon.setMemberCouponId(memberCoupon.getMcId());
        coupon.setEndTime(memberCoupon.getEndTime());
        coupon.setCouponThresholdPrice(memberCoupon.getCouponThresholdPrice());
        coupon.setUseTerm("满" + new BigDecimal(memberCoupon.getCouponThresholdPrice() + "") + "可用");
        return coupon;
    }


    /**
     * 由一个产品列表中找到产品
     *
     * @param skuId       产品id
     * @param productList 产品列表
     * @return 找到的产品
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
     * 查找选中的优惠券
     *
     * @param cartCouponList
     * @return 如果为空则无选中的优惠劵
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
