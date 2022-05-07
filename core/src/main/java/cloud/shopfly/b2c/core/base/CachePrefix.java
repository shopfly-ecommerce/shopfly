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
package cloud.shopfly.b2c.core.base;

/**
 * Prefix cache
 * Created by kingapex on 2018/3/19.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/19
 */
public enum CachePrefix {

    /**
     * token
     */
    TOKEN,

    /**
     * System Settings
     */
    SETTING,

    /**
     * Delivery platform
     */
    EXPRESS,

    /**
     * Image verification code
     */
    CAPTCHA,

    /**
     * Hot keywords
     */
    HOT_KEYWORD,

    /**
     * product
     */
    GOODS,

    /**
     * productsku
     */
    SKU,

    /**
     * skuInventory
     */
    SKU_STOCK,

    /**
     * inventory
     */
    GOODS_STOCK,

    /**
     * Category
     */
    GOODS_CAT,
    /**
     * viewed
     */
    VISIT_COUNT,
    /**
     * Storage solution
     */
    UPLOADER,
    /**
     * region
     */
    REGION,

    /**
     * SMS gateway
     */
    SPlATFORM,
    /**
     * SMS verification code prefix
     */
    _CODE_PREFIX,
    /**
     * smtp
     */
    SMTP,
    /**
     * System Settings
     */
    SETTINGS,
    /**
     * Electronic surface single
     */
    WAYBILL,
    /**
     * SMS verification code
     */
    SMS_CODE,
    /**
     * Message authentication
     */
    SMS_VERIFY,
    /**
     * Mapping table of administrator role permissions
     */
    ADMIN_URL_ROLE,

    /**
     * Mobile phone identification
     */
    MOBILE_VALIDATE,

    /**
     * List of store freight templates
     */
    SHIP_TEMPLATE,

    /**
     * A shipping template in the store
     */
    SHIP_TEMPLATE_ONE,

    // = = = = = = = = = = = = = = = = promotion = = = = = = = = = = = = = = = = =
    /**
     * Sales promotion activity
     */
    PROMOTION_KEY,

    /**
     * Item set
     */
    STORE_ID_MINUS_KEY,

    /**
     * The second one is half price
     */
    STORE_ID_HALF_PRICE_KEY,

    /**
     * With preferential
     */
    STORE_ID_FULL_DISCOUNT_KEY,

    /**
     * Flash sale activity cachekeyThe prefix
     */
    STORE_ID_SECKILL_KEY,

    /**
     * Group purchase activity cachekeyThe prefix
     */
    STORE_ID_GROUP_BUY_KEY,

    /**
     * Integral Goods CachekeyThe prefix
     */
    STORE_ID_EXCHANGE_KEY,


    // = = = = = = = = = = = = = = = = deal = = = = = = = = = = = = = = = = =

    /**
     * Shopping cart raw data
     */
    CART_ORIGIN_DATA_PREFIX,

    /**
     * Buy nowsku
     */
    CART_SKU_PREFIX,

    /**
     * Shopping cart view
     */
    CART_MEMBER_ID_PREFIX,

    /**
     * Shopping cart, user-selected promotional information
     */
    CART_PROMOTION_PREFIX,


    /**
     * trading_trading价格的前缀
     */
    PRICE_SESSION_ID_PREFIX,

    /**
     * trading_trading单
     */
    TRADE_SESSION_ID_PREFIX,


    /**
     * Settlement parameter
     */
    CHECKOUT_PARAM_ID_PREFIX,

    /**
     * Transaction order number prefix
     */
    TRADE_SN_CACHE_PREFIX,

    /**
     * Order Number prefix
     */
    ORDER_SN_CACHE_PREFIX,
    /**
     * Order number mark
     */
    ORDER_SN_SIGN_CACHE_PREFIX,
    /**
     * Order Number prefix
     */
    PAY_LOG_SN_CACHE_PREFIX,

    /**
     * trading
     */
    TRADE,

    /**
     * Product praise rate
     */
    GOODS_GRADE,
    /**
     * All regions
     */
    REGIONALL,
    /**
     * Hierarchical region caching
     */
    REGIONLIDEPTH,

    /**
     * Site navigation bar
     */
    SITE_NAVIGATION,
    /**
     * Trust the login
     */
    CONNECT_LOGIN,

    /**
     * Sensitive words
     */
    SENSITIVE_WORDS,
    /**
     * session_key
     */
    SESSION_KEY,
    /**
     * Pay parameters
     */
    PAYMENT_CONFIG,
    /**
     * Online personnel
     */
    DISTRIBUTION_UP,
    /**
     * Membership short link
     */
    MEMBER_SU;


    public String getPrefix() {
        return "{" + this.name() + "}_";
    }
}
