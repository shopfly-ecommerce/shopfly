/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.service.impl;

import com.enation.app.javashop.core.base.CachePrefix;
import com.enation.app.javashop.core.trade.order.service.TradeSnCreator;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * 交易，订单编号创建
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
    @Qualifier("tradeDaoSupport")
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
     * 通过Redis的自增来控制编号的自增
     *
     * @param key 区分类型的主key，日期会连在这个key后面
     * @return 生成的编码
     */
    private String generateSn(String key) {

        String timeStr = DateUtil.toString(new Date(), "yyyyMMdd");
        //组合出当天的Key
        String redisKey = key + "_" + timeStr;
        String redisSignKey = key + "_" + timeStr + "_SIGN";

        //用当天的时间进行自增
        Long snCount = getSnCount(redisKey, redisSignKey);


        String sn;

        //预计每天订单不超过1百万单
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

        //设置缓存是否被击穿缓存
        keys.add(redisSignKey);
        keys.add(redisKey);

        Long result = stringRedisTemplate.execute(redisScript, keys);
        //如果为-1，说明缓存被击穿了
        if (result == -1) {
            //从库中读取当天的订单数量
            snCount = countFromDB();
            snCount++;
            //重置计数器
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
