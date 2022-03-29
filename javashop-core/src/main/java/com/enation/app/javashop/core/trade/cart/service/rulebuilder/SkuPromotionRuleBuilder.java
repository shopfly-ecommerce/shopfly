/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.cart.service.rulebuilder;

import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.enation.app.javashop.core.promotion.tool.model.vo.PromotionVO;
import com.enation.app.javashop.core.trade.cart.model.vo.CartSkuVO;
import com.enation.app.javashop.core.trade.cart.model.vo.PromotionRule;

/**
 * sku促销规则builder接口<br/>
 * 生产sku级别促销规则
 * 文档请参考：<br/>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html#促销规则的构建" >促销规则的构建</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/13
 */
public interface SkuPromotionRuleBuilder {

    /**
     * 构建sku促销规则
     *
     * @param skuVO       购物车的sku
     * @param promotionVO 促销vo
     * @return 构建的促销规则
     */
    PromotionRule build(CartSkuVO skuVO, PromotionVO promotionVO);


    /**
     * 定义促销类型
     *
     * @return
     */
    PromotionTypeEnum getPromotionType();


}
