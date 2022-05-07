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
package cloud.shopfly.b2c.core.statistics.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.statistics.model.dos.GoodsPageView;
import cloud.shopfly.b2c.core.statistics.service.DisplayTimesManager;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DisplayTimesManagerImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-07 In the morning8:22
 */
@Service
public class DisplayTimesManagerImpl implements DisplayTimesManager {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GoodsClient goodsClient;

    /**
     * Threshold, after the accumulated number of data, warehousing operation will be performed
     */
    private final int THRESHOLD = 100;

    /**
     * Access to goods
     */
    private final String GOODS = "{GOODS_VIEW}";


    /**
     * Access records
     */
    private final String HISTORY = "{VIEW_HISTORY}";


    @Autowired
    private Cache cache;

    public DisplayTimesManagerImpl() {
    }

    /**
     * Accessing an address
     *
     * @param url
     */
    @Override
    public void view(String url, String uuid) {

        // Record access
        List<String> history = new ArrayList<>(16);

        Object object = cache.get(HISTORY);
        // Not null, if empty, create
        if (object != null) {
            history = (List) object;
        }
        // If yes, statistics are not collected
        if (history.contains(url + uuid)) {
            return;
        }
        // Otherwise record access
        history.add(url + uuid);
        cache.put(HISTORY, history);

        // Determine whether the visit is a product or a store
        int type = regular(url);
        // Invalid access filtering
        if (type == 2) {
            return;
        }

        viewGoods(urlParams(url, type));


    }

    @Override
    public void countGoods(List<GoodsPageView> goodsPageViews) {
        if (goodsPageViews == null) {
            return;
        }
        List<GoodsPageView> buildGoods = reBuildGoods(goodsPageViews);
        String sql = "INSERT INTO `es_sss_goods_pv` (goods_name,goods_id,vs_year,vs_month,vs_num) VALUES (?,?,?,?,?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            /**
             * Set parameter values on the given PreparedStatement.
             *
             * @param ps the PreparedStatement to invoke setter methods on
             * @param i  index of the statement we're issuing in the batch, starting from 0
             * @throws SQLException if a SQLException is encountered
             *                      (i.e. there is no need to catch SQLException)
             */
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                GoodsPageView goodsPageView = buildGoods.get(i);
                ps.setString(1, goodsPageView.getGoodsName());
                ps.setInt(2, goodsPageView.getGoodsId());
                ps.setInt(3, goodsPageView.getYear());
                ps.setInt(4, goodsPageView.getMonth());
                ps.setInt(5, goodsPageView.getNum());
            }

            /**
             * Return the size of the batch.
             *
             * @return the number of statements in the batch
             */
            @Override
            public int getBatchSize() {
                return buildGoods.size();
            }

        });
    }

    @Override
    public void countNow() {
        List<GoodsPageView> goodsPageViews = (List<GoodsPageView>) cache.get(GOODS);
        this.cache.remove(GOODS);
        this.cache.remove(HISTORY);

        if (StringUtil.isNotEmpty(goodsPageViews)) {
            goodsPageViews = reBuildGoods(goodsPageViews);
            this.countGoods(goodsPageViews);
        }
    }

    /**
     * Match the currenturlAre you visiting goods or shops
     *
     * @param url
     * @return 1 product0The store2 invalid
     */
    private int regular(String url) {
        if (StringUtil.isEmpty(url)) {
            return 2;
        }
        if (url.indexOf("/goods/") > 0) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Access to goods
     *
     * @param goodsId
     */
    private void viewGoods(Integer goodsId) {
        List<GoodsPageView> goodsPageViews = (List<GoodsPageView>) cache.get(GOODS);
        if (goodsPageViews == null) {
            goodsPageViews = new ArrayList<>(16);
        }
        LocalDate localDate = LocalDate.now();
        GoodsPageView goodsPageView = new GoodsPageView();
        goodsPageView.setGoodsId(goodsId);
        goodsPageView.setNum(1);
        goodsPageView.setYear(localDate.getYear());
        goodsPageView.setMonth(localDate.getMonthValue());
        goodsPageView.setCreateTime(DateUtil.getDateline());
        goodsPageViews.add(goodsPageView);
        // If the threshold is reached, AMQP is sent for processing
        if (goodsPageViews.size() > THRESHOLD) {
            this.messageSender.send(new MqMessage(AmqpExchange.GOODS_VIEW_COUNT, AmqpExchange.GOODS_VIEW_COUNT + "_ROUTING",
                    goodsPageViews));
            this.cache.remove(GOODS);
            return;
        }
        this.cache.put(GOODS, goodsPageViews);

    }

    /**
     * Get the goods of the current storeid/Or the storeid
     *
     * @param url
     * @return 1 product0The store2 invalid
     */
    private int urlParams(String url, int type) {
        switch (type) {
            case 0:
                String pattern = "(/shop/)(\\d+)";
                // Create Pattern object
                Pattern r = Pattern.compile(pattern);
                // Now create the Matcher object
                Matcher m = r.matcher(url);
                if (m.find()) {
                    return new Integer(m.group(2));
                }
            case 1:
                pattern = "(/goods/)(\\d+)";
                // Create Pattern object
                r = Pattern.compile(pattern);
                // Now create the Matcher object
                m = r.matcher(url);
                if (m.find()) {
                    return new Integer(m.group(2));
                }
            default:
                return 0;

        }
    }


    /**
     * Restructure product browsing
     *
     * @param goodsPageViews
     */
    private List<GoodsPageView> reBuildGoods(List<GoodsPageView> goodsPageViews) {
        // Finish goods
        Map<Integer, GoodsPageView> countGoods = new HashMap<>(16);
        for (GoodsPageView goodsPageView : goodsPageViews) {
            if (countGoods.containsKey(goodsPageView.hashCode())) {
                GoodsPageView cGoodsPageView = countGoods.get(goodsPageView.hashCode());
                cGoodsPageView.setNum(cGoodsPageView.getNum() + 1);
                countGoods.put(goodsPageView.hashCode(), cGoodsPageView);
            } else {
                CacheGoods cacheGoods = goodsClient.getFromCache(goodsPageView.getGoodsId());
                goodsPageView.setGoodsName(cacheGoods.getGoodsName());
                countGoods.put(goodsPageView.hashCode(), goodsPageView);
            }
        }
        goodsPageViews = new ArrayList<>();
        for (Integer key : countGoods.keySet()) {
            goodsPageViews.add(countGoods.get(key));
        }
        return goodsPageViews;
    }

}
