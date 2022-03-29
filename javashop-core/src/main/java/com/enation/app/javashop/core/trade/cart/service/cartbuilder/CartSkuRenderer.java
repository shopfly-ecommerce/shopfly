/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.cart.service.cartbuilder;

import com.enation.app.javashop.core.trade.cart.model.enums.CartType;
import com.enation.app.javashop.core.trade.cart.model.vo.CartVO;
import com.enation.app.javashop.core.trade.cart.service.cartbuilder.impl.CartSkuFilter;

import java.util.List;

/**
 * 购物车sku渲染器，负责生产一个全新的cartList<br/>
 * 此步生产的物料是{@link com.enation.app.javashop.core.trade.cart.model.vo.CartSkuOriginVo}
 * 文档请参考：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html" >购物车架构</a>
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/11
 */
public interface CartSkuRenderer {

    /**
     * 渲染sku数据
     * @param cartList
     * @param cartType
     */
    void renderSku(List<CartVO> cartList, CartType cartType);

    /**
     * 渲染sku数据 带过滤器式的
     * @param cartList
     * @param cartFilter
     * @param cartType
     */
    void renderSku(List<CartVO> cartList, CartSkuFilter cartFilter, CartType cartType);
}
