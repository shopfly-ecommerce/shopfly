/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
import dev.shopflix.core.trade.cart.service.cartbuilder.*;
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
 * 拼团购物车业务类实现
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
     * 购物车促销渲染器
     */
    @Autowired
    @Qualifier(value = "pintuanCartPromotionRuleRendererImpl")
    private CartPromotionRuleRenderer cartPromotionRuleRenderer;

    /**
     * 购物车价格计算器
     */
    @Autowired
    @Qualifier(value = "pintuanCartPriceCalculatorImpl")
    private CartPriceCalculator cartPriceCalculator;

    /**
     * 拼团购物车sku数据渲染器
     */
    @Autowired
    @Qualifier(value = "pintuanCartSkuRenderer")
    private CartSkuRenderer pintuanCartSkuRenderer;

    /**
     * 数据校验
     */
    @Autowired
    private CheckDataRebderer checkDataRebderer;

    /**
     * 购物车优惠券渲染器
     */
    @Autowired
    private CartCouponRenderer cartCouponRenderer;

    /**
     * 购物车运费价格计算器
     */
    @Autowired
    private CartShipPriceCalculator cartShipPriceCalculator;


    @Autowired
    private GoodsClient goodsClient;


    @Autowired
    private Cache cache;


    @Override
    public CartView getCart() {
        //调用CartView生产流程线进行生产
        CartBuilder cartBuilder = new DefaultCartBuilder(CartType.PINTUAN, pintuanCartSkuRenderer, cartPromotionRuleRenderer, cartPriceCalculator, cartCouponRenderer, cartShipPriceCalculator, checkDataRebderer);

        //生产流程为：渲染sku->渲染促销规则(计算优惠券）->计算运费->计算价格 -> 渲染优惠券 ->生成成品
        CartView cartView = cartBuilder.renderSku().countShipPrice().countPrice().checkData().build();
        return cartView;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public CartSkuOriginVo addSku(Integer skuId, Integer num) {
        CartSkuOriginVo skuVo = new CartSkuOriginVo();
        GoodsSkuVO sku = this.goodsClient.getSkuFromCache(skuId);
        if (sku == null) {
            throw new ServiceException(TradeErrorCode.E451.code(), "商品已失效，请刷新购物车");
        }
        if (num <= 0) {
            throw new ServiceException(TradeErrorCode.E451.code(), "商品数量不能小于等于0。");
        }

        //读取sku的可用库存
        Integer enableQuantity = sku.getEnableQuantity();
        if (enableQuantity <= 0) {
            throw new ServiceException(TradeErrorCode.E451.code(), "商品库存已不足，不能购买。");
        }

        BeanUtils.copyProperties(sku, skuVo);
        skuVo.setNum(num);
        skuVo.setChecked(1);

        String originKey = this.getOriginKey();

        cache.put(originKey, skuVo);


        if (logger.isDebugEnabled()) {
            logger.debug("将拼团商品加入缓存：" + skuVo);
        }

        return skuVo;
    }

    /**
     * 读取当前会员购物车原始数据key
     *
     * @return
     */
    @SuppressWarnings("Duplicates")
    protected String getOriginKey() {

        String cacheKey = "";
        //如果会员登陆了，则要以会员id为key
        Buyer buyer = UserContext.getBuyer();
        if (buyer != null) {
            cacheKey = CachePrefix.CART_SKU_PREFIX.getPrefix() + buyer.getUid();
        }

        return cacheKey;
    }
}
