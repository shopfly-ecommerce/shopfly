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
package cloud.shopfly.b2c.core.goods.service.impl.util;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.goods.model.enums.QuantityType;

import java.util.ArrayList;
import java.util.List;

/**
 * Inventory in the cachekeyUtility class<br/>
 * The main purpose is to centrally manage these strings and prevent them from being scattered in the code<br/>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-26
 */

public class StockCacheKeyUtil {


    /**
     * skuAvailablekey
     *
     * @param skuId sku id
     * @return
     */
    public static String skuEnableKey(Integer skuId) {
        return "{stock}" + CachePrefix.SKU_STOCK.getPrefix() + QuantityType.enable + "_" + skuId;
    }

    /**
     * skuThe actual inventorykey
     *
     * @param skuId sku id
     * @return
     */
    public static String skuActualKey(Integer skuId) {
        return "{stock}" + CachePrefix.SKU_STOCK.getPrefix() + QuantityType.actual + "_" + skuId;
    }


    /**
     * Available inventory of goodskey
     *
     * @param goodsId goods id
     * @return
     */
    public static String goodsEnableKey(Integer goodsId) {
        return "{stock}" + CachePrefix.GOODS_STOCK.getPrefix() + QuantityType.enable + "_" + goodsId;
    }

    /**
     * Actual inventory of goodskey
     *
     * @param goodsId goods id
     * @return
     */
    public static String goodsActualKey(Integer goodsId) {
        return "{stock}" + CachePrefix.GOODS_STOCK.getPrefix() + QuantityType.actual + "_" + goodsId;
    }


    /**
     * Mass productionskuthekeys列表，包含可用the和实际the
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
