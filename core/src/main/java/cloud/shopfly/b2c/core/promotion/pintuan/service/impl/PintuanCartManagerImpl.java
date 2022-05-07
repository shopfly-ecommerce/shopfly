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
package cloud.shopfly.b2c.core.promotion.pintuan.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanCartManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.trade.TradeErrorCode;
import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuOriginVo;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartView;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.*;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.*;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl.DefaultCartBuilder;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by kingapex on 2019-01-23.
 * Group shopping cart business class implementation
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-23
 */
@Service
public class PintuanCartManagerImpl implements PintuanCartManager {

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * Shopping cart promotion renderer
     */
    @Autowired
    @Qualifier(value = "pintuanCartPromotionRuleRendererImpl")
    private CartPromotionRuleRenderer cartPromotionRuleRenderer;

    /**
     * Shopping cart price calculator
     */
    @Autowired
    @Qualifier(value = "pintuanCartPriceCalculatorImpl")
    private CartPriceCalculator cartPriceCalculator;

    /**
     * Group shopping cartskuData renderer
     */
    @Autowired
    @Qualifier(value = "pintuanCartSkuRenderer")
    private CartSkuRenderer pintuanCartSkuRenderer;

    /**
     * Data validation
     */
    @Autowired
    private CheckDataRebderer checkDataRebderer;

    /**
     * Shopping cart coupon renderer
     */
    @Autowired
    private CartCouponRenderer cartCouponRenderer;

    /**
     * Shopping cart freight price calculator
     */
    @Autowired
    private CartShipPriceCalculator cartShipPriceCalculator;


    @Autowired
    private GoodsClient goodsClient;


    @Autowired
    private Cache cache;


    @Override
    public CartView getCart() {
        // Call CartView production flow line for production
        CartBuilder cartBuilder = new DefaultCartBuilder(CartType.PINTUAN, pintuanCartSkuRenderer, cartPromotionRuleRenderer, cartPriceCalculator, cartCouponRenderer, cartShipPriceCalculator, checkDataRebderer);

        // Production process: render SKU -> render promotional rules (calculate coupons) -> calculate freight -> calculate prices -> render coupons -> generate finished products
        CartView cartView = cartBuilder.renderSku().countShipPrice().countPrice().checkData().build();
        return cartView;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public CartSkuOriginVo addSku(Integer skuId, Integer num) {
        CartSkuOriginVo skuVo = new CartSkuOriginVo();
        GoodsSkuVO sku = this.goodsClient.getSkuFromCache(skuId);
        if (sku == null) {
            throw new ServiceException(TradeErrorCode.E451.code(), "Item no longer valid, please refresh cart");
        }
        if (num <= 0) {
            throw new ServiceException(TradeErrorCode.E451.code(), "The quantity of goods cannot be less than or equal to0。");
        }

        // Read the available inventory of the SKU
        Integer enableQuantity = sku.getEnableQuantity();
        if (enableQuantity <= 0) {
            throw new ServiceException(TradeErrorCode.E451.code(), "The goods are out of stock and cannot be purchased.");
        }

        BeanUtils.copyProperties(sku, skuVo);
        skuVo.setNum(num);
        skuVo.setChecked(1);

        String originKey = this.getOriginKey();

        cache.put(originKey, skuVo);


        if (logger.isDebugEnabled()) {
            logger.debug("Add grouped items to the cache：" + skuVo);
        }

        return skuVo;
    }

    /**
     * Read the current member shopping cart raw datakey
     *
     * @return
     */
    @SuppressWarnings("Duplicates")
    protected String getOriginKey() {

        String cacheKey = "";
        // If the member logs in, the member ID is the key
        Buyer buyer = UserContext.getBuyer();
        if (buyer != null) {
            cacheKey = CachePrefix.CART_SKU_PREFIX.getPrefix() + buyer.getUid();
        }

        return cacheKey;
    }
}
