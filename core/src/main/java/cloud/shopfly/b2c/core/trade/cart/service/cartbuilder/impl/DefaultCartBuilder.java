/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl;

import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.*;
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

/**
 * 促销信息构建器
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
public class DefaultCartBuilder implements CartBuilder {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    /**
     * 购物车促销规则渲染器
     */
    private CartPromotionRuleRenderer cartPromotionRuleRenderer;

    /**
     * 购物车价格计算器
     */
    private CartPriceCalculator cartPriceCalculator;

    /**
     * 数据校验
     */
    private CheckDataRebderer checkDataRebderer;

    /**
     * 购物车sku渲染器
     */
    private CartSkuRenderer cartSkuRenderer;

    /**
     * 购物车优惠券渲染
     */
    private CartCouponRenderer cartCouponRenderer;


    /**
     * 运费价格计算器
     */
    private CartShipPriceCalculator cartShipPriceCalculator;


    private List<CartVO> cartList;
    private PriceDetailVO price;
    private CartType cartType;


    public DefaultCartBuilder(CartType cartType, CartSkuRenderer cartSkuRenderer, CartPromotionRuleRenderer cartPromotionRuleRenderer, CartPriceCalculator cartPriceCalculator, CheckDataRebderer checkDataRebderer) {
        this.cartType = cartType;
        this.cartSkuRenderer = cartSkuRenderer;
        this.cartPromotionRuleRenderer = cartPromotionRuleRenderer;
        this.cartPriceCalculator = cartPriceCalculator;
        this.checkDataRebderer = checkDataRebderer;
        cartList = new ArrayList<>();
    }

    public DefaultCartBuilder(CartType cartType, CartSkuRenderer cartSkuRenderer, CartPromotionRuleRenderer cartPromotionRuleRenderer, CartPriceCalculator cartPriceCalculator, CartCouponRenderer cartCouponRenderer, CartShipPriceCalculator cartShipPriceCalculator, CheckDataRebderer checkDataRebderer) {
        this.cartType = cartType;
        this.cartSkuRenderer = cartSkuRenderer;
        this.cartPromotionRuleRenderer = cartPromotionRuleRenderer;
        this.cartPriceCalculator = cartPriceCalculator;
        this.cartCouponRenderer = cartCouponRenderer;
        this.cartShipPriceCalculator = cartShipPriceCalculator;
        this.checkDataRebderer = checkDataRebderer;
        cartList = new ArrayList<>();
    }

    /**
     * 渲染sku<br/>
     * 此步通过{@link CartSkuOriginVo}生产出一个全新的cartList
     *
     * @return
     */
    @Override
    public CartBuilder renderSku() {
        cartSkuRenderer.renderSku(this.cartList, cartType);
        return this;
    }

    /**
     * 带过滤器式的渲染sku<br/>
     * 可以过滤为指定条件{@link  CartSkuFilter}的商品<br/>
     *
     * @return
     * @see CartSkuFilter
     */
    @Override
    public CartBuilder renderSku(CartSkuFilter filter) {
        cartSkuRenderer.renderSku(this.cartList, filter, cartType);
        return this;
    }


    /**
     * 此步通过
     * {@link SelectedPromotionVo}
     * 生产出
     * {@link PromotionRule}
     *
     * @param includeCoupon
     * @return
     */
    @Override
    public CartBuilder renderPromotionRule(boolean includeCoupon) {
        cartPromotionRuleRenderer.render(cartList, includeCoupon);
        return this;
    }

    /**
     * 此步通过上一步的产出物
     * {@link PromotionRule}
     * 来计算出价格:
     * {@link PriceDetailVO}
     *
     * @return
     */
    @Override
    public CartBuilder countPrice() {
        this.price = cartPriceCalculator.countPrice(cartList);
        return this;
    }


    /**
     * 调用运费模板来算出运费，只接应用到购物车的价格中
     *
     * @return
     */
    @Override
    public CartBuilder countShipPrice() {
        cartShipPriceCalculator.countShipPrice(cartList);
        return this;
    }


    /**
     * 此步读取出会员的可用优惠券，加入到购物车的couponList中
     *
     * @return
     */
    @Override
    public CartBuilder renderCoupon() {
        cartCouponRenderer.render(cartList);
        return this;
    }

    @Override
    public CartView build() {
        return new CartView(cartList, price);
    }

    @Override
    public CartBuilder checkData() {
        checkDataRebderer.checkData(cartList);
        return this;
    }
}
