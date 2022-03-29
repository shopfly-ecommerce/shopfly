/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.statistics.service.impl;

import com.enation.app.javashop.core.base.SearchCriteria;
import com.enation.app.javashop.core.client.system.RegionsClient;
import com.enation.app.javashop.core.statistics.StatisticsErrorCode;
import com.enation.app.javashop.core.statistics.StatisticsException;
import com.enation.app.javashop.core.statistics.model.enums.QueryDateType;
import com.enation.app.javashop.core.statistics.model.vo.*;
import com.enation.app.javashop.core.statistics.service.OrderStatisticManager;
import com.enation.app.javashop.core.statistics.util.ChartSqlUtil;
import com.enation.app.javashop.core.statistics.util.DataDisplayUtil;
import com.enation.app.javashop.core.statistics.util.StatisticsUtil;
import com.enation.app.javashop.core.statistics.util.SyncopateUtil;
import com.enation.app.javashop.core.system.model.dos.Regions;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.ObjectNotFoundException;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.util.CurrencyUtil;
import com.enation.app.javashop.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * 订单统计实现类
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/4/28 下午5:11
 */
@Service
public class OrderStatisticManagerImpl implements OrderStatisticManager {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    @Qualifier("sssDaoSupport")
    private DaoSupport daoSupport;

    @Autowired
    private RegionsClient regionsClient;


    @Override
    public MultipleChart getOrderMoney(SearchCriteria searchCriteria, String orderStatus) {
        searchCriteria = new SearchCriteria(searchCriteria);

        try {
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
            long[] lastTimestamp = DataDisplayUtil.getLastStartTimeAndEndTime(searchCriteria);
            String circle = "";
            if (Objects.equals(searchCriteria.getCycleType(), QueryDateType.YEAR.name())) {
                circle = "%m";
            } else if (Objects.equals(searchCriteria.getCycleType(), QueryDateType.MONTH.name())) {
                circle = "%d";
            }
            StringBuffer sql = new StringBuffer("SELECT SUM(mo.order_price) AS order_money,mo.create_time AS create_time,e_create_time FROM (SELECT o.sn,o.order_price,o.create_time,FROM_UNIXTIME(o.create_time,?) AS e_create_time FROM es_sss_order_data o WHERE o.create_time >= ? AND o.create_time <= ?");

            StringBuffer lastSql = new StringBuffer("SELECT SUM(mo.order_price) AS order_money,mo.create_time AS create_time,e_create_time FROM (SELECT o.sn,o.order_price,o.create_time,FROM_UNIXTIME(o.create_time,?) AS e_create_time FROM es_sss_order_data o WHERE o.create_time >= ? AND o.create_time <= ?");

            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();
            params.add(circle);
            params.add(timestamp[0]);
            params.add(timestamp[1]);
            /*
             * 参数
             */
            List<Object> lastParams = new ArrayList<>();
            lastParams.add(circle);
            lastParams.add(lastTimestamp[0]);
            lastParams.add(lastTimestamp[1]);

            ChartSqlUtil.appendOrderStatusSql(orderStatus, sql, params);
            ChartSqlUtil.appendOrderStatusSql(orderStatus, lastSql, lastParams);


            sql.append(") mo GROUP BY mo.e_create_time,mo.create_time ");
            lastSql.append(") mo GROUP BY mo.e_create_time");

            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql.toString(), params.toArray());
            List<Map<String, Object>> lastList = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), lastSql.toString(), lastParams.toArray());


            String[] data = new String[DataDisplayUtil.getResultSize(searchCriteria)];
            String[] lastData = new String[DataDisplayUtil.getResultSize(searchCriteria)];

            String[] xAxis = new String[DataDisplayUtil.getResultSize(searchCriteria)];


            for (Integer i = 0; i < DataDisplayUtil.getResultSize(searchCriteria); i++) {
                xAxis[i] = (i + 1) + "";
                if (list == null || list.size() == 0) {
                    data[i] = "0";
                } else {
                    for (Map<String, Object> map : list) {
                        if ((i + 1) == StringUtil.toInt(map.get("e_create_time"), false)) {
                            data[i] = map.get("order_money").toString();
                        }
                    }
                    if (StringUtil.isEmpty(data[i])) {
                        data[i] = "0";
                    }
                }

                if (lastList == null || lastList.size() == 0) {
                    lastData[i] = "0";
                } else {
                    for (Map<String, Object> map : lastList) {
                        if ((i + 1) == StringUtil.toInt(map.get("e_create_time"), false)) {
                            lastData[i] = map.get("order_money").toString();
                        }
                    }
                    if (StringUtil.isEmpty(lastData[i])) {
                        lastData[i] = "0";
                    }
                }
            }

            ChartSeries chartSeries = new ChartSeries("本月", data, new String[0]);
            ChartSeries lastChartSeries = new ChartSeries("上月", lastData, new String[0]);

            if (searchCriteria.getCycleType().equals(QueryDateType.YEAR.name())) {
                chartSeries = new ChartSeries("今年", data, new String[0]);
                lastChartSeries = new ChartSeries("去年", lastData, new String[0]);
            }
            List<ChartSeries> chartSeriess = new ArrayList<>();
            chartSeriess.add(chartSeries);
            chartSeriess.add(lastChartSeries);
            return new MultipleChart(chartSeriess, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public Page getOrderPage(SearchCriteria searchCriteria, String orderStatus, Integer pageNo, Integer pageSize) {
        searchCriteria = new SearchCriteria(searchCriteria);

        long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
        List<Object> params = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select o.sn,o.create_time,o.order_price,o.order_status,o.buyer_name from es_sss_order_data o where o.create_time >= ? AND o.create_time <= ? ");
        params.add(timestamp[0]);
        params.add(timestamp[1]);
        ChartSqlUtil.appendOrderStatusSql(orderStatus, sql, params);
        return StatisticsUtil.getDataPage(this.daoSupport, searchCriteria.getYear(), sql.toString(), pageNo, pageSize, params.toArray());
    }


    @Override
    public MultipleChart getOrderNum(SearchCriteria searchCriteria, String orderStatus) {

        searchCriteria = new SearchCriteria(searchCriteria);

        try {
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
            long[] lastTimestamp = DataDisplayUtil.getLastStartTimeAndEndTime(searchCriteria);

            List<Object> params = new ArrayList<>();
            List<Object> lastParams = new ArrayList<>();

            StringBuffer sql = new StringBuffer("SELECT COUNT(mo.id) AS order_num,mo.create_time AS create_time,e_create_time FROM (SELECT o.id,o.create_time,FROM_UNIXTIME(o.create_time,?) AS e_create_time FROM es_sss_order_data o WHERE o.create_time >= ? AND o.create_time <= ? ");

            StringBuffer lastSql = new StringBuffer("SELECT COUNT(mo.id) AS order_num,mo.create_time AS create_time,e_create_time FROM (SELECT o.id,o.create_time,FROM_UNIXTIME(o.create_time,?) AS e_create_time FROM es_sss_order_data o WHERE o.create_time >= ? AND o.create_time <= ? ");

            String circle = "";
            if (Objects.equals(searchCriteria.getCycleType(), QueryDateType.YEAR.name())) {
                circle = "%m";
            } else if (Objects.equals(searchCriteria.getCycleType(), QueryDateType.MONTH.name())) {
                circle = "%d";
            }
            params.add(circle);
            lastParams.add(circle);
            params.add(timestamp[0]);
            lastParams.add(lastTimestamp[0]);
            params.add(timestamp[1]);
            lastParams.add(lastTimestamp[1]);

            ChartSqlUtil.appendOrderStatusSql(orderStatus, sql, params);
            ChartSqlUtil.appendOrderStatusSql(orderStatus, lastSql, lastParams);

            sql.append("  ) mo GROUP BY mo.e_create_time,mo.create_time ");
            lastSql.append("  ) mo GROUP BY mo.e_create_time,mo.create_time ");


            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql.toString(), params.toArray());
            List<Map<String, Object>> lastList = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), lastSql.toString(), lastParams.toArray());

            String[] data = new String[DataDisplayUtil.getResultSize(searchCriteria)];
            String[] lastData = new String[DataDisplayUtil.getResultSize(searchCriteria)];

            String[] xAxis = new String[DataDisplayUtil.getResultSize(searchCriteria)];


            for (Integer i = 0; i < DataDisplayUtil.getResultSize(searchCriteria); i++) {
                xAxis[i] = (i + 1) + "";
                if (list == null || list.size() == 0) {
                    data[i] = "0";
                } else {
                    for (Map<String, Object> map : list) {
                        if ((i + 1) == StringUtil.toInt(map.get("e_create_time"), false)) {
                            data[i] = map.get("order_num").toString();
                        }
                    }
                    if (StringUtil.isEmpty(data[i])) {
                        data[i] = "0";
                    }
                }

                if (lastList == null || lastList.size() == 0) {
                    lastData[i] = "0";
                } else {
                    for (Map<String, Object> map : lastList) {
                        if ((i + 1) == StringUtil.toInt(map.get("e_create_time"), false)) {
                            lastData[i] = map.get("order_num").toString();
                        }
                    }
                    if (StringUtil.isEmpty(lastData[i])) {
                        lastData[i] = "0";
                    }
                }
            }

            ChartSeries chartSeries = new ChartSeries("本月", data, new String[0]);
            ChartSeries lastChartSeries = new ChartSeries("上月", lastData, new String[0]);

            if (searchCriteria.getCycleType().equals(QueryDateType.YEAR.name())) {
                chartSeries = new ChartSeries("今年", data, new String[0]);
                lastChartSeries = new ChartSeries("去年", lastData, new String[0]);
            }
            List<ChartSeries> chartSeriess = new ArrayList<>();
            chartSeriess.add(chartSeries);
            chartSeriess.add(lastChartSeries);
            return new MultipleChart(chartSeriess, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }


    @Override
    public Page getSalesMoney(SearchCriteria searchCriteria, Integer pageNo, Integer pageSize) {

        searchCriteria = new SearchCriteria(searchCriteria);
        try {

            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();

            /*
             * 查询sql
             */
            StringBuffer sql = new StringBuffer("SELECT oi.`goods_name`,oi.goods_num,oi.`price`,oi.price*oi.goods_num as total FROM es_sss_order_goods_data oi LEFT JOIN es_sss_order_data o "
                    + " ON oi.`order_sn` = o.`sn` WHERE o.`create_time` >= ? AND o.`create_time` <= ?"
                    + " ORDER BY oi.`goods_id`");
            params.add(timestamp[0]);
            params.add(timestamp[1]);

            return StatisticsUtil.getDataPage(this.daoSupport, searchCriteria.getYear(), sql.toString(), pageNo, pageSize, params.toArray());
        } catch (BadSqlGrammarException e) {

            //某个年份的统计表不存在，则返回空数据
            if (e.getMessage().endsWith("doesn't exist")) {
                return new Page(pageNo, 0L, pageSize, new ArrayList());
            } else {
                throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }


    @Override
    public Page getAfterSalesMoney(SearchCriteria searchCriteria, Integer pageNo, Integer pageSize) {

        searchCriteria = new SearchCriteria(searchCriteria);
        long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
        /*
         * 参数
         */
        List<Object> params = new ArrayList<>();

        /*
         * 查询sql
         */
        StringBuffer sql = new StringBuffer("SELECT refund_sn,order_sn,refund_price,create_time from es_sss_refund_data WHERE `create_time` >= ? AND `create_time` <= ?");
        params.add(timestamp[0]);
        params.add(timestamp[1]);

        return StatisticsUtil.getDataPage(this.daoSupport, searchCriteria.getYear(), sql.toString(), pageNo, pageSize, params.toArray());
    }

    @Override
    public SalesTotal getSalesMoneyTotal(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);

        try {
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();

            /*
             * 查询sql
             */
            StringBuffer sql = new StringBuffer("SELECT SUM(o.`order_price`) AS receive_money,SUM(r.`refund_price`) AS refund_money "
                    + " FROM es_sss_order_data o LEFT JOIN es_sss_refund_data r ON o.`sn` = r.`order_sn` WHERE o.`create_time` >= ? AND o.`create_time` <= ? ");
            params.add(timestamp[0]);
            params.add(timestamp[1]);
            Map<String, Object> map;
            try {
                map = this.daoSupport.queryForMap(SyncopateUtil.handleSql(searchCriteria.getYear(), sql.toString()), params.toArray());
                if (map == null || map.size() <= 0 || map.get("receive_money") == null || map.get("refund_money") == null) {
                    map = this.daoSupport.queryForMap(sql.toString(), params.toArray());
                }
            } catch (ObjectNotFoundException e) {

                //某个年份的统计表不存在，则返回空数据
                map = new HashMap<>();
            }


            SalesTotal salesTotal = new SalesTotal();
            salesTotal.setReceiveMoney(StringUtil.toDouble(map.get("receive_money"), false));
            salesTotal.setRefundMoney(StringUtil.toDouble(map.get("refund_money"), false));
            salesTotal.setRealMoney(CurrencyUtil.sub(salesTotal.getReceiveMoney(), salesTotal.getRefundMoney()));
            return salesTotal;
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }


    @Override
    public MapChartData getOrderRegionMember(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);
        try {
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
            List<Regions> regionsList = this.regionsClient.getRegionsChildren(0);
            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();

            /*
             * 查询sql
             */
            StringBuffer sql = new StringBuffer("SELECT o.`ship_province_id` AS region_id,COUNT(DISTINCT o.`buyer_id`) AS value FROM es_sss_order_data o "
                    + " WHERE o.`create_time` >= ? AND o.`create_time` <= ? ");

            params.add(timestamp[0]);
            params.add(timestamp[1]);

            sql.append(" GROUP BY region_id ");

            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql.toString(), params.toArray());

            String[] name = new String[regionsList.size()];
            String[] data = new String[regionsList.size()];


            int index = 0;
            for (Regions region : regionsList) {
                name[index] = region.getLocalName();
                for (Map<String, Object> map : list) {
                    if (region.getId().equals(map.get("region_id"))) {
                        data[index] = map.get("value").toString();
                    }
                }
                if (StringUtil.isEmpty(data[index])) {
                    data[index] = "0";
                }
                index++;
            }
            return new MapChartData(name, data);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public MapChartData getOrderRegionNum(SearchCriteria searchCriteria) {

        searchCriteria = new SearchCriteria(searchCriteria);
        try {
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
            List<Regions> regionsList = this.regionsClient.getRegionsChildren(0);
            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();

            /*
             * 查询sql
             */
            StringBuffer sql = new StringBuffer("SELECT o.`ship_province_id` AS region_id,COUNT(o.`sn`) AS value FROM es_sss_order_data o "
                    + " WHERE o.`create_time` > ? AND o.`create_time` < ? ");
            params.add(timestamp[0]);
            params.add(timestamp[1]);
            sql.append(" GROUP BY region_id");

            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql.toString(), params.toArray());
            if (list == null) {
                list = new ArrayList<>();
            }

            String[] name = new String[regionsList.size()];
            String[] data = new String[regionsList.size()];


            int index = 0;
            for (Regions region : regionsList) {
                name[index] = region.getLocalName();
                for (Map<String, Object> map : list) {

                    if (region.getId().equals(map.get("region_id"))) {
                        data[index] = map.get("value").toString();
                    }
                }
                if (StringUtil.isEmpty(data[index])) {
                    data[index] = "0";
                }
                index++;
            }
            return new MapChartData(name, data);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public MapChartData getOrderRegionMoney(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);


        try {
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
            List<Regions> regionsList = this.regionsClient.getRegionsChildren(0);
            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();

            /*
             * 查询sql
             */
            StringBuffer sql = new StringBuffer();


            sql.append("SELECT o.`ship_province_id` AS region_id,SUM(o.`order_price`) AS value FROM es_sss_order_data o "
                    + " WHERE o.`create_time` >= ? AND o.`create_time` <= ?");


            params.add(timestamp[0]);
            params.add(timestamp[1]);

            sql.append(" GROUP BY region_id");

            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql.toString(), params.toArray());

            String[] name = new String[regionsList.size()];
            String[] data = new String[regionsList.size()];


            int index = 0;
            for (Regions region : regionsList) {
                name[index] = region.getLocalName();
                for (Map<String, Object> map : list) {

                    if (region.getId().equals(map.get("region_id"))) {
                        data[index] = map.get("value").toString();
                    }
                }
                if (StringUtil.isEmpty(data[index])) {
                    data[index] = "0";
                }
                index++;
            }
            return new MapChartData(name, data);
        } catch (Exception e) {

            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public Page getOrderRegionForm(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);
        try {

            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
            List<Regions> regionsList = this.regionsClient.getRegionsChildren(0);
            /**
             * 参数
             */
            List<Object> params = new ArrayList<>();

            /*
             * 查询sql
             */
            StringBuffer sql = new StringBuffer();

            sql.append("SELECT o.`ship_province_id` AS region_id,COUNT(DISTINCT o.`buyer_id`) AS member_num,SUM(o.`order_price`) AS order_price, "
                    + " COUNT(o.`sn`) AS sn_num  FROM es_sss_order_data o where o.create_time > ? and o.create_time < ? ");

            params.add(timestamp[0]);
            params.add(timestamp[1]);


            sql.append(" GROUP BY region_id");

            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql.toString(), params.toArray());

            List<Map<String, Object>> result = new ArrayList();
            for (Regions region : regionsList) {
                Map<String, Object> m = new HashMap(16);
                m.put("region_id", region.getId());
                m.put("region_name", region.getLocalName());
                result.add(m);
            }

            for (Map<String, Object> map : result) {
                boolean flag = true;
                for (Map<String, Object> map2 : list) {
                    if (map.get("region_id").equals(map2.get("region_id"))) {
                        map.put("member_num", map2.get("member_num"));
                        map.put("order_price", map2.get("order_price"));
                        map.put("sn_num", map2.get("sn_num"));
                        flag = false;
                    }
                }
                if (flag) {
                    map.put("member_num", 0);
                    map.put("order_price", 0);
                    map.put("sn_num", 0);
                }
            }

            return new Page(1, (long) regionsList.size(), regionsList.size(), result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public SimpleChart getUnitPrice(SearchCriteria searchCriteria, Integer[] prices) {
        searchCriteria = new SearchCriteria(searchCriteria);

        int notAvailable = 2;
        if (prices == null || prices.length < notAvailable) {
            prices = SearchCriteria.defaultPrice();
        }
        try {
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);

            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();

            /*
             * 查询sql
             */
            StringBuffer sql = new StringBuffer();

            sql.append("SELECT COUNT(oi.`order_sn`) AS num,CASE ");

            ChartSqlUtil.appendPriceSql(prices, sql, params);

            sql.append(" AS price_interval  FROM es_sss_order_goods_data oi LEFT JOIN es_sss_order_data o ON oi.`order_sn` = o.`sn` "
                    + " WHERE o.`create_time` >= ? AND o.`create_time` <= ? ");

            params.add(timestamp[0]);
            params.add(timestamp[1]);

            // 拼接价格区间

            sql.append(" GROUP BY CASE ");
            ChartSqlUtil.appendPriceSql(prices, sql, params);


            List<Map<String, Object>> dataList = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql.toString(), params.toArray());


            String[] xAxis = new String[prices.length - 1],
                    localName = new String[prices.length - 1],
                    data = new String[prices.length - 1];

            if (dataList == null) {
                dataList = new ArrayList<>();
            }
            for (int i = 0; i < prices.length - 1; i++) {
                xAxis[i] = prices[i] + "~" + prices[i + 1];
                localName[i] = xAxis[i];
                for (Map map : dataList) {
                    if ((i + 1) == (int) map.get("price_interval")) {
                        data[i] = map.get("num").toString();
                    }
                }
                if (StringUtil.isEmpty(data[i])) {
                    data[i] = "0";
                }

            }


            ChartSeries chartSeries = new ChartSeries("下单量", data, localName);
            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public Page getUnitNum() {
        try {
            String totalNum = "SELECT COUNT(member_id) AS member_totalnum FROM es_sss_member_register_data";

            List<Map<String, Object>> result = new ArrayList<>();

            for (int i = 1; i <= 10; i++) {
                Map<String, Object> m = new HashMap<>(16);
                m.put("order_num", i);
                m.put("member_num", 0);
                result.add(m);
            }

            Double total = this.daoSupport.queryForDouble(totalNum);

            String orderNum = "SELECT COUNT(o.`sn`) AS sn_num FROM es_sss_order_data o GROUP BY o.`buyer_id`";
            String moreOrderNum = "SELECT COUNT(0) AS member_num FROM (SELECT COUNT(o.`sn`) AS sn_num FROM es_sss_order_data o GROUP BY o.`buyer_id`) a WHERE a.sn_num > 10";

            List<Map<String, Integer>> firstList = this.daoSupport.queryForList(orderNum);
            Integer moreNum = this.daoSupport.queryForInt(moreOrderNum);

            for (Map<String, Integer> map : firstList) {
                for (Integer i = 1; i <= 10; i++) {
                    Object a = map.get("sn_num");
                    Integer b = new Integer(a.toString());
                    if (b.equals(i)) {
                        Map<String, Object> m = result.get(i - 1);
                        Object o = m.get("member_num");
                        Integer c = new Integer(o.toString());
                        Integer memberNum = c + 1;
                        m.put("member_num", memberNum);
                        result.set(i - 1, m);
                    }
                }
            }

            Map<String, Object> m = new HashMap<>(16);
            m.put("order_num", "10+");
            m.put("member_num", moreNum);
            result.add(m);


            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
            for (Map<String, Object> map : result) {
                double value = Double.parseDouble(map.get("member_num").toString());
                double num = (value / total) * 100;
                String percent = df.format(num) + "%";
                map.put("percent", percent);
            }

            return new Page(1, (long) result.size(), 10, result);
        } catch (NumberFormatException e) {

            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public SimpleChart getUnitTime(SearchCriteria searchCriteria) {

        searchCriteria = new SearchCriteria(searchCriteria);
        try {

            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();

            /*
             * 查询sql
             */
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT DATE_FORMAT(CONVERT_TZ(FROM_UNIXTIME(o.`create_time`), @@session.time_zone, '+8:00'), '%k') AS order_time,COUNT(o.`sn`) AS order_num FROM es_sss_order_data o "
                    + " WHERE o.`create_time` >= ? AND o.`create_time` <= ? ");

            params.add(timestamp[0]);
            params.add(timestamp[1]);

            sql.append(" GROUP BY order_time");

            List<Map<String, Object>> dataList = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql.toString(), params.toArray());


            String[] xAxis = new String[24],
                    data = new String[24];


            if (dataList == null) {
                dataList = new ArrayList<>();
            }

            // 小时数
            int hours = 24;
            int index = 0;
            for (int i = 0; i < hours; i++) {
                xAxis[index] = (i + 1) + "";
                for (Map map : dataList) {

                    if ((i + 1) == StringUtil.toInt(map.get("order_time"), false)) {
                        data[index] = map.get("order_num").toString();
                    }
                }
                if (StringUtil.isEmpty(data[index])) {
                    data[index] = "0";
                }
                index++;
            }

            ChartSeries chartSeries = new ChartSeries("下单量", data, new String[0]);
            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public SimpleChart getReturnMoney(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);
        try {
            String circle = "";

            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);

            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();

            /*
             * 查询sql
             */
            StringBuffer sql = new StringBuffer();

            if (Objects.equals(searchCriteria.getCycleType(), QueryDateType.YEAR.name())) {
                circle = "%m";
            } else if (Objects.equals(searchCriteria.getCycleType(), QueryDateType.MONTH.name())) {
                circle = "%d";
            }
            sql.append("SELECT sum(f.`refund_price`) as price ,FROM_UNIXTIME(f.`create_time`,?) AS refund_time FROM es_sss_refund_data f left join es_sss_order_data o on o.sn = f.order_sn"
                    + " WHERE f.`create_time` >= ? AND f.`create_time` <= ? group by f.`create_time`");

            params.add(circle);
            params.add(timestamp[0]);
            params.add(timestamp[1]);

            List<Map<String, Object>> dataList = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql.toString(), params.toArray());

            String[] xAxis = new String[DataDisplayUtil.getResultSize(searchCriteria)],
                    data = new String[DataDisplayUtil.getResultSize(searchCriteria)];


            if (dataList == null) {
                dataList = new ArrayList<>();
            }

            int index = 0;
            for (int i = 0; i < DataDisplayUtil.getResultSize(searchCriteria); i++) {
                xAxis[index] = (i + 1) + "";
                for (Map map : dataList) {
                    if ((i + 1) == StringUtil.toInt(map.get("refund_time"), false)) {
                        data[index] = map.get("price").toString();
                    }
                }
                if (StringUtil.isEmpty(data[index])) {
                    data[index] = 0 + "";
                }
                index++;
            }
            ChartSeries chartSeries = new ChartSeries("退款", data, new String[0]);
            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

}
