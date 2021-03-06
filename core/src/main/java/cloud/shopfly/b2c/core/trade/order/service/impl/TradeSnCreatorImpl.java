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
package cloud.shopfly.b2c.core.trade.order.service.impl;

import cloud.shopfly.b2c.core.trade.order.service.TradeSnCreator;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Transaction, order number created
 *
 * @author Snow create in 2018/4/9
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class TradeSnCreatorImpl implements TradeSnCreator {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired

    private DaoSupport daoSupport;

    @Override
    public String generateTradeSn() {
        String key = CachePrefix.TRADE_SN_CACHE_PREFIX.getPrefix();
        return generateSn(key);
    }

    @Override
    public String generateOrderSn() {
        String key = CachePrefix.ORDER_SN_CACHE_PREFIX.getPrefix();
        return generateSn(key);
    }

    @Override
    public String generatePayLogSn() {
        String key = CachePrefix.PAY_LOG_SN_CACHE_PREFIX.getPrefix();
        return generateSn(key);
    }

    @Override
    public void cleanCache() {
        Date yesterday = getYesterday();
        String timeStr = DateUtil.toString(yesterday, "yyyyMMdd");
        stringRedisTemplate.delete(CachePrefix.TRADE_SN_CACHE_PREFIX.getPrefix() + "_" + timeStr);
        stringRedisTemplate.delete(CachePrefix.ORDER_SN_CACHE_PREFIX.getPrefix() + "_" + timeStr);
    }


    private Date getYesterday() {
        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }


    /**
     * throughRedisTo control the increment of the number
     *
     * @param key Distinguish between types of masterkeyThe date will be linked to thiskeybehind
     * @return Generated code
     */
    private String generateSn(String key) {

        String timeStr = DateUtil.toString(new Date(), "yyyyMMdd");
        // Make up the Key for the day
        String redisKey = key + "_" + timeStr;
        String redisSignKey = key + "_" + timeStr + "_SIGN";

        // Increment with the time of the day
        Long snCount = getSnCount(redisKey, redisSignKey);


        String sn;

        // No more than 1 million orders are expected per day
        int num = 1000000;
        if (snCount < num) {
            sn = "000000" + snCount;
            sn = sn.substring(sn.length() - 6, sn.length());
        } else {
            sn = String.valueOf(snCount);
        }
        sn = timeStr + sn;
        return sn;
    }


    private static RedisScript<Long> script = null;


    private Long getSnCount(String redisKey, String redisSignKey) {

        Long snCount = 0L;
        RedisScript<Long> redisScript = getRedisScript();
        List keys = new ArrayList<>();

        // Set whether the cache is breached
        keys.add(redisSignKey);
        keys.add(redisKey);

        Long result = stringRedisTemplate.execute(redisScript, keys);
        // If it is -1, the cache has been penetrated
        if (result == -1) {
            // Read the number of orders for the day from the library
            snCount = countFromDB();
            snCount++;
            // Reset counter
            stringRedisTemplate.opsForValue().set(redisKey, snCount.toString());
        } else {
            snCount = result;
        }

        return snCount;
    }


    private static RedisScript<Long> getRedisScript() {

        if (script != null) {
            return script;
        }

        ScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("trade_sn.lua"));
        String str = null;
        try {
            str = scriptSource.getScriptAsString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        script = RedisScript.of(str, Long.class);
        return script;
    }

    private Long countFromDB() {
        return daoSupport.queryForLong("select count(1) from es_order where create_time >= ? and create_time <= ? ", DateUtil.startOfTodDay(), DateUtil.endOfTodDay());
    }

}
