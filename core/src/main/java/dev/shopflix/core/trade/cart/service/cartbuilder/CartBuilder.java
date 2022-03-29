/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.service.cartbuilder;

import dev.shopflix.core.trade.cart.model.vo.CartView;
import dev.shopflix.core.trade.cart.service.cartbuilder.impl.CartSkuFilter;

/**
 * 购物车构建器<br/>
 * 他的目标是要生产出一个{@link CartView}
 * 文档请参考：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html" >购物车架构</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
public interface CartBuilder {

    /**
     * 渲染购物车的sku信息
     *
     * @return
     */
    CartBuilder renderSku();

    /**
     * 带过滤器的渲染sku
     *
     * @param filter
     * @return
     */
    CartBuilder renderSku(CartSkuFilter filter);

    /**
     * 渲染促销信息
     *
     * @param includeCoupon 是否包含优惠券的计算
     * @return builder
     */
    CartBuilder renderPromotionRule(boolean includeCoupon);


    /**
     * 计算购物车价格
     *
     * @return
     */
    CartBuilder countPrice();


    /**
     * 计算运费
     *
     * @return builder
     */
    CartBuilder countShipPrice();

    /**
     * 计算优惠券费用
     *
     * @return builder
     */
    CartBuilder renderCoupon();

    /**
     * 检测数据正确性
     *
     * @return
     */
    CartBuilder checkData();

    /**
     * 构建购物车视图
     *
     * @return
     */
    CartView build();


}
