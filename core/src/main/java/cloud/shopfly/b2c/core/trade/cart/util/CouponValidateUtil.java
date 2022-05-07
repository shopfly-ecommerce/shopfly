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

import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.SelectedPromotionVo;

import java.util.List;

/**
 * @author liuyulei
 * @version 1.0
 * @date 2019/5/7 20:38
 * @since v7.0
 */
public class CouponValidateUtil {


    /**
     * Check whether the selected promotion is redeemable for points and if it is redeemable for points, the coupon cannot be used
     *
     * @param selectedPromotionVo
     */
    public static Boolean validateCoupon(SelectedPromotionVo selectedPromotionVo) {
        List<PromotionVO> singlePromotionList = selectedPromotionVo.getSinglePromotionList();

        if (singlePromotionList != null && !singlePromotionList.isEmpty()) {
            for (PromotionVO promotion : singlePromotionList) {
                if (PromotionTypeEnum.EXCHANGE.name().equals(promotion.getPromotionType())) {
                    return false;
                }
            }
        }

        return true;
    }


}
