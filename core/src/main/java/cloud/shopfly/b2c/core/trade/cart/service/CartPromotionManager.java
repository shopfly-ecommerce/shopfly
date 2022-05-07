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
package cloud.shopfly.b2c.core.trade.cart.service;

import cloud.shopfly.b2c.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.SelectedPromotionVo;

import java.util.List;

/**
 * Shopping cart discount information processing interface<br/>
 * Responsible for the use of promotions、cancel、Read.
 * Please refer to the documentation.：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html" >Shopping cart architecture</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/1
 */
public interface CartPromotionManager {


    /**
     * Get the selected promotion
     * @return
     */
    SelectedPromotionVo getSelectedPromotion();

    /**
     * Get all valid full offers
     * @param cartList
     * @return
     */
    List<FullDiscountVO> getFullDiscounPromotion(List<CartVO> cartList);

    /**
     * Use a promotion
     *
     * @param skuId
     * @param activityId
     * @param promotionType
     */
    void usePromotion(Integer skuId, Integer activityId, PromotionTypeEnum promotionType);


    /**
     * Use a coupon
     * @param mcId
     * @param totalPrice
     */
    void useCoupon(Integer mcId, double totalPrice);

    /**
     * Clear all coupons
     */
    void cleanCoupon();

    /**
     * Batch deleteskuCorresponding preferential activities
     *
     * @param skuIds
     */
    void delete(Integer[] skuIds);

    /**
     * Clear all offers for the current user
     */
    void clean();
}
