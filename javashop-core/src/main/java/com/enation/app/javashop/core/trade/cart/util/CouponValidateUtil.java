/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.cart.util;

import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.enation.app.javashop.core.promotion.tool.model.vo.PromotionVO;
import com.enation.app.javashop.core.trade.cart.model.vo.SelectedPromotionVo;

import java.util.List;
import java.util.Map;

/**
 * @author liuyulei
 * @version 1.0
 * @date 2019/5/7 20:38
 * @since v7.0
 */
public class CouponValidateUtil {


    /**
     * 检测选择的促销活动是否为积分兑换  如果为积分兑换则不能使用优惠券
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
