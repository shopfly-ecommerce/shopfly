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
package cloud.shopfly.b2c.core.goods.service.impl;

import cloud.shopfly.b2c.core.goods.model.enums.QuantityType;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;
import cloud.shopfly.b2c.core.goods.service.GoodsQuantityManager;
import cloud.shopfly.b2c.core.goods.service.impl.util.UpdatePool;
import cloud.shopfly.b2c.core.goods.service.impl.util.StockCacheKeyUtil;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Commodity inventory interface
 *
 * @author fk
 * @author kingapex
 * @version v1.0 2017years4month1On the afternoon12:03:08
 * @version v2.0 written by kingapex  2019years2month27day
 * usingluaScript executionredisInventory deduction in<br/>
 * Database updates are asynchronously synchronized<br/>
 * Instead, a buffer pool is set up to synchronize the database when a certain condition is reached<br/>
 * The conditions are：Buffer size, buffer times, buffer time<br/>
 * The preceding conditions can be configured in the configuration center${@link UpdatePool} The default value<br/>
 * In the configuration item description：<br/>
 * <li>Buffer size：shopfly.pool.stock.max-pool-size</li>
 * <li>Number of buffer：shopfly.pool.stock.max-update-time</li>
 * <li>Buffer time（Number of seconds）：shopfly.pool.stock.max-lazy-second</li>
 *
 * @see ShopflyConfig
 */
@Service
public class GoodsQuantityManagerImpl implements GoodsQuantityManager {


    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private DaoSupport daoSupport;

    @Autowired
    private ShopflyConfig shopflyConfig;


    /**
     * skuThe inventory update buffer pool
     */
    private static UpdatePool skuUpdatePool ;
    /**
     * goodsThe inventory update buffer pool
     */
    private static UpdatePool goodsUpdatePool ;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    private static RedisScript<Boolean> script = null;


    @Override
    public Map<String, Integer> fillCacheFromDB(int skuId) {
        Map<String,Integer>  map= daoSupport.queryForMap("select enable_quantity,quantity from es_goods_sku where sku_id=?", skuId);
        Integer enableNum = map.get("enable_quantity");
        Integer actualNum = map.get("quantity");

        stringRedisTemplate.opsForValue().set(StockCacheKeyUtil.skuActualKey(skuId), ""+actualNum);
        stringRedisTemplate.opsForValue().set(StockCacheKeyUtil.skuEnableKey(skuId), ""+enableNum);
        return  map;
    }

    @Override
    public Boolean updateSkuQuantity(List<GoodsQuantityVO> goodsQuantityList) {
        List<Integer> skuIdList = new ArrayList();
        List<Integer> goodsIdList = new ArrayList();

        List keys = new ArrayList<>();
        List values = new ArrayList<>();

        for (GoodsQuantityVO quantity : goodsQuantityList) {

            Assert.notNull(quantity.getGoodsId(), "goods id must not be null");
            Assert.notNull(quantity.getSkuId(), "sku id must not be null");
            Assert.notNull(quantity.getQuantity(), "quantity id must not be null");
            Assert.notNull(quantity.getQuantityType(), "Type must not be null");


            // Sku inventories
            if (QuantityType.enable.equals(quantity.getQuantityType())){
                keys.add(StockCacheKeyUtil.skuEnableKey(quantity.getSkuId()) );
            }else if (QuantityType.actual.equals(quantity.getQuantityType())){
                keys.add(StockCacheKeyUtil.skuActualKey(quantity.getSkuId()) );
            }
            values.add("" + quantity.getQuantity());

            // Goods inventory key
            if (QuantityType.enable.equals(quantity.getQuantityType())){
                keys.add(StockCacheKeyUtil.goodsEnableKey(quantity.getGoodsId()) );
            }else if (QuantityType.actual.equals(quantity.getQuantityType())){
                keys.add(StockCacheKeyUtil.goodsActualKey(quantity.getGoodsId()) );
            }
            values.add("" + quantity.getQuantity());


            skuIdList.add(quantity.getSkuId());
            goodsIdList.add(quantity.getGoodsId());
        }

        RedisScript<Boolean> redisScript = getRedisScript();
        Boolean result = stringRedisTemplate.execute(redisScript, keys, values.toArray());

        if (logger.isDebugEnabled()) {
            logger.debug("Update the inventory：");
            logger.debug(goodsQuantityList.toString());
            logger.debug("Update the results：" + result);
        }

        // If the Lua script executes successfully, the buffer is logged
        if (result) {

            // Check whether the commodity inventory buffer pool set in the configuration file is enabled
            if (shopflyConfig.isStock()) {

                // Whether to synchronize the database
                boolean needSync = getSkuPool().oneTime(skuIdList);
                getGoodsPool().oneTime(goodsIdList);

                if (logger.isDebugEnabled()) {
                    logger.debug("Whether to synchronize the database:" + needSync);
                    logger.debug(getSkuPool().toString());
                }

                // If the buffer pool is enabled and the buffer is saturated, synchronize the database
                if (needSync) {
                    syncDataBase();
                }
            } else {
                // If the buffer pool is not enabled, the inventory data in the goods database is synchronized in real time
                syncDataBase(skuIdList, goodsIdList);
            }

        }


        return result;
    }

    @Override
    public void syncDataBase() {
        // Get the synchronized SKUID and goodSID
        List<Integer>  skuIdList = getSkuPool().getTargetList();
        List<Integer>  goodsIdList = getGoodsPool().getTargetList();

        if (logger.isDebugEnabled()) {
            logger.debug("goodsIdList is:");
            logger.debug(goodsIdList.toString());
        }

        // Determines whether the goods and SKU sets to be synchronized have values
        if (skuIdList.size() != 0 && goodsIdList.size() != 0) {
            // Synchronizing databases
            syncDataBase(skuIdList, goodsIdList);
        }

        // Resetting the buffer pool
        getSkuPool().reset();
        getGoodsPool().reset();
    }

    /**
     * Synchronize inventory in the database
     *
     * @param skuIdList   To be synchronizedskuid
     * @param goodsIdList To be synchronizedgoodsid
     */
    private void syncDataBase(List<Integer> skuIdList, List<Integer> goodsIdList) {

        // To form refers to update SQL
        List<String> sqlList = new ArrayList<String>();


        // Get the INVENTORY of SKUs in bulk
        List skuKeys = StockCacheKeyUtil.skuKeys(skuIdList);
        List<String> skuQuantityList = stringRedisTemplate.opsForValue().multiGet(skuKeys);


        int i = 0;

        // Form a batch update LIST of SKUs
        for (Integer skuId : skuIdList) {
            String sql = "";
            if (skuQuantityList.get(i + 1) == null ) {
                sql = "update es_goods_sku set enable_quantity=" + skuQuantityList.get(i) + ", quantity=" + 0 + " where sku_id=" + skuId;
            } else {
                sql = "update es_goods_sku set enable_quantity=" + skuQuantityList.get(i) + ", quantity=" + skuQuantityList.get(i + 1) + " where sku_id=" + skuId;
            }
            sqlList.add(sql);
            i=i+2;
        }

        // Acquire inventory of goods in bulk
        List goodsKeys = createGoodsKeys(goodsIdList);
        List<String> goodsQuantityList =  stringRedisTemplate.opsForValue().multiGet(goodsKeys);

        i = 0;

        // Form a batch update goods list
        for (Integer goodsId : goodsIdList) {
            String sql = "update es_goods set enable_quantity=" + goodsQuantityList.get(i) + ", quantity=" + goodsQuantityList.get(i + 1) + " where goods_id=" + goodsId;
            sqlList.add(sql);
            i=i+2;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Batch updatedsqlfor：");
            logger.debug(sqlList.toString());

        }

        daoSupport.batchUpdate(sqlList.toArray(new String[]{}));

    }

    private static RedisScript<Boolean> getRedisScript() {

        if (script != null) {
            return script;
        }

        ScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("sku_quantity.lua"));
        String str = null;
        try {
            str = scriptSource.getScriptAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        script = RedisScript.of(str, Boolean.class);
        return script;
    }

    /**
     * Singleton accessgoods pool , set parameters during initialization
     * @return
     */
    private UpdatePool getGoodsPool() {
        if (goodsUpdatePool == null) {
            goodsUpdatePool = new UpdatePool(shopflyConfig.getMaxUpdateTime(),shopflyConfig.getMaxPoolSize(),shopflyConfig.getMaxLazySecond());


        }

        return goodsUpdatePool;
    }

    /**
     * Singleton accesssku pool , set parameters during initialization
     * @return
     */
    private UpdatePool getSkuPool() {
        if (skuUpdatePool == null) {
            skuUpdatePool = new UpdatePool(shopflyConfig.getMaxUpdateTime(),shopflyConfig.getMaxPoolSize(),shopflyConfig.getMaxLazySecond());

            if (logger.isDebugEnabled()) {
                logger.debug("Initialize thesku pool:");
                logger.debug(skuUpdatePool.toString());

            }
        }

        return skuUpdatePool;
    }

    /**
     * Generate batch fetchgoodsinventorykeys
     * @param goodsIdList
     * @return
     */
    private List createGoodsKeys(List<Integer> goodsIdList) {
        List keys = new ArrayList();
        for (Integer goodsId : goodsIdList) {
            keys.add( StockCacheKeyUtil.goodsEnableKey(goodsId) );
            keys.add(StockCacheKeyUtil.goodsActualKey(goodsId)  );
        }
        return keys;
    }

}
