/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.cart.service.rulebuilder;

import com.enation.app.javashop.core.trade.cart.model.vo.CartVO;
import com.enation.app.javashop.core.trade.cart.model.vo.CouponVO;
import com.enation.app.javashop.core.trade.cart.model.vo.PromotionRule;

/**
 * 优惠规则构建器<br/>
 * 负责购物车优惠券的渲染
 * 文档请参考：<br/>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html#促销规则的构建" >促销规则的构建</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/19
 */
public interface CartCouponRuleBuilder {

    /**
     * 构建优惠券促销规则
     *
     * @param cartVO
     * @param couponVO
     * @return
     */
    PromotionRule build(CartVO cartVO, CouponVO couponVO);

}
