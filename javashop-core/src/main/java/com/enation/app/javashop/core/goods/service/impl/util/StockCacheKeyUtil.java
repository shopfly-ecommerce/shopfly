/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goods.service.impl.util;

import com.enation.app.javashop.core.base.CachePrefix;
import com.enation.app.javashop.core.goods.model.enums.QuantityType;

import java.util.ArrayList;
import java.util.List;

/**
 * 库存中缓存中的key工具类<br/>
 * 主要作用是集中管理这些字串，防止散在代码中<br/>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-26
 */

public class StockCacheKeyUtil {


    /**
     * sku可用库存key
     *
     * @param skuId sku id
     * @return
     */
    public static String skuEnableKey(Integer skuId) {
        return "{stock}" + CachePrefix.SKU_STOCK.getPrefix() + QuantityType.enable + "_" + skuId;
    }

    /**
     * sku实际库存key
     *
     * @param skuId sku id
     * @return
     */
    public static String skuActualKey(Integer skuId) {
        return "{stock}" + CachePrefix.SKU_STOCK.getPrefix() + QuantityType.actual + "_" + skuId;
    }


    /**
     * 商品可用库存key
     *
     * @param goodsId goods id
     * @return
     */
    public static String goodsEnableKey(Integer goodsId) {
        return "{stock}" + CachePrefix.GOODS_STOCK.getPrefix() + QuantityType.enable + "_" + goodsId;
    }

    /**
     * 商品实际库存key
     *
     * @param goodsId goods id
     * @return
     */
    public static String goodsActualKey(Integer goodsId) {
        return "{stock}" + CachePrefix.GOODS_STOCK.getPrefix() + QuantityType.actual + "_" + goodsId;
    }


    /**
     * 批量生成sku的keys列表，包含可用的和实际的
     *
     * @param skuIdList
     * @return
     */
    public static List<String> skuKeys(List<Integer> skuIdList) {
        List keys = new ArrayList();
        for (Integer skuId : skuIdList) {
            keys.add(StockCacheKeyUtil.skuEnableKey(skuId));
            keys.add(StockCacheKeyUtil.skuActualKey(skuId));
        }
        return keys;
    }


}
