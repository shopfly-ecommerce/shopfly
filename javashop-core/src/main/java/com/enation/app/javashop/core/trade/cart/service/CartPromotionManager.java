/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.cart.service;

import com.enation.app.javashop.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.enation.app.javashop.core.trade.cart.model.vo.CartVO;
import com.enation.app.javashop.core.trade.cart.model.vo.SelectedPromotionVo;

import java.util.List;

/**
 * 购物车优惠信息处理接口<br/>
 * 负责促销的使用、取消、读取。
 * 文档请参考：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html" >购物车架构</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/1
 */
public interface CartPromotionManager {


    /**
     * 获取选中的促销活动
     * @return
     */
    SelectedPromotionVo getSelectedPromotion();

    /**
     * 获取所有有效的满优惠活动
     * @param cartList
     * @return
     */
    List<FullDiscountVO> getFullDiscounPromotion(List<CartVO> cartList);

    /**
     * 使用一个促销活动
     *
     * @param skuId
     * @param activityId
     * @param promotionType
     */
    void usePromotion(Integer skuId, Integer activityId, PromotionTypeEnum promotionType);


    /**
     * 使用一个优惠券
     * @param mcId
     * @param totalPrice
     */
    void useCoupon(Integer mcId, double totalPrice);

    /**
     * 清除所有的优惠券
     */
    void cleanCoupon();

    /**
     * 批量删除sku对应的优惠活动
     *
     * @param skuIds
     */
    void delete(Integer[] skuIds);

    /**
     * 清空当前用户的所有优惠活动
     */
    void clean();
}
