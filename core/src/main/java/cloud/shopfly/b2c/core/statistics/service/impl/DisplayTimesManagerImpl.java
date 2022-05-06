/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 2018-08-07 上午8:22
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
     * 阙值，数据累计次数后，进行入库操作
     */
    private final int THRESHOLD = 100;

    /**
     * 商品访问
     */
    private final String GOODS = "{GOODS_VIEW}";


    /**
     * 访问记录
     */
    private final String HISTORY = "{VIEW_HISTORY}";


    @Autowired
    private Cache cache;

    public DisplayTimesManagerImpl() {
    }

    /**
     * 访问某地址
     *
     * @param url
     */
    @Override
    public void view(String url, String uuid) {

        //记录访问
        List<String> history = new ArrayList<>(16);

        Object object = cache.get(HISTORY);
        //非空判定，为空则创建
        if (object != null) {
            history = (List) object;
        }
        //如果已经访问过，则不进行统计
        if (history.contains(url + uuid)) {
            return;
        }
        //否则记录访问
        history.add(url + uuid);
        cache.put(HISTORY, history);

        //判定访问是商品还是店铺
        int type = regular(url);
        //无效访问过滤
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
     * 匹配当前url访问的是商品还是店铺
     *
     * @param url
     * @return 1 商品 0店铺 2 无效
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
     * 访问商品
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
        //如果达到阙值，则发送AMQP进行处理
        if (goodsPageViews.size() > THRESHOLD) {
            this.messageSender.send(new MqMessage(AmqpExchange.GOODS_VIEW_COUNT, AmqpExchange.GOODS_VIEW_COUNT + "_ROUTING",
                    goodsPageViews));
            this.cache.remove(GOODS);
            return;
        }
        this.cache.put(GOODS, goodsPageViews);

    }

    /**
     * 获取当前店铺的商品id/或者店铺id
     *
     * @param url
     * @return 1 商品 0店铺 2 无效
     */
    private int urlParams(String url, int type) {
        switch (type) {
            case 0:
                String pattern = "(/shop/)(\\d+)";
                // 创建 Pattern 对象
                Pattern r = Pattern.compile(pattern);
                // 现在创建 matcher 对象
                Matcher m = r.matcher(url);
                if (m.find()) {
                    return new Integer(m.group(2));
                }
            case 1:
                pattern = "(/goods/)(\\d+)";
                // 创建 Pattern 对象
                r = Pattern.compile(pattern);
                // 现在创建 matcher 对象
                m = r.matcher(url);
                if (m.find()) {
                    return new Integer(m.group(2));
                }
            default:
                return 0;

        }
    }


    /**
     * 重新构造商品浏览
     *
     * @param goodsPageViews
     */
    private List<GoodsPageView> reBuildGoods(List<GoodsPageView> goodsPageViews) {
        //整理商品
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
