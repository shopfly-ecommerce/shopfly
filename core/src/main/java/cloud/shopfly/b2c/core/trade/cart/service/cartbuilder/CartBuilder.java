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
package cloud.shopfly.b2c.core.trade.cart.service.cartbuilder;

import cloud.shopfly.b2c.core.trade.cart.model.vo.CartView;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl.CartSkuFilter;

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
