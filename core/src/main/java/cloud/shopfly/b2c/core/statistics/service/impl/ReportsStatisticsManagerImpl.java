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

import cloud.shopfly.b2c.core.client.system.RegionsClient;
import cloud.shopfly.b2c.core.statistics.StatisticsErrorCode;
import cloud.shopfly.b2c.core.statistics.StatisticsException;
import cloud.shopfly.b2c.core.statistics.model.enums.QueryDateType;
import cloud.shopfly.b2c.core.statistics.model.enums.RegionsDataType;
import cloud.shopfly.b2c.core.statistics.model.vo.ChartSeries;
import cloud.shopfly.b2c.core.statistics.model.vo.MultipleChart;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.ReportsStatisticsManager;
import cloud.shopfly.b2c.core.statistics.util.DataDisplayUtil;
import cloud.shopfly.b2c.core.statistics.util.StatisticsUtil;
import cloud.shopfly.b2c.core.statistics.util.SyncopateUtil;
import cloud.shopfly.b2c.core.system.model.dos.Regions;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PayStatusEnum;
import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Business center, operational report implementation class
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/5/11 20:05
 */
@Service
public class ReportsStatisticsManagerImpl implements ReportsStatisticsManager {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired

    private DaoSupport daoSupport;

    @Autowired
    private RegionsClient regionsClient;

    /**
     * Sales statistics order amount
     *
     * @param searchCriteria Statistical parameters, time
     * @return MultipleChart Complex chart data
     */
    @Override
    public MultipleChart getSalesMoney(SearchCriteria searchCriteria) {

        String cycleType = searchCriteria.getCycleType();
        int year = searchCriteria.getYear();
        int month = null == searchCriteria.getMonth() ? 0 : searchCriteria.getMonth();
        SearchCriteria.checkDataParams(cycleType, year, month);
        try {
            // Parameter collection
            List<Object> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.value());
            paramList.add(PayStatusEnum.PAY_YES.value());

            /*
             * start: Joining togethersqlStatement and query the current period data
             */
            // Time range SQL
            String conditionSql = StatisticsUtil.getInstance().createSql(cycleType, year, month);
            String sql = "select SUM(order_price) as t_money, case " + conditionSql + " as time " +
                    " from es_sss_order_data o where order_status = ? AND pay_status = ? ";

            // Grouping by time
            sql += " group by time ";
            List<Map<String, Object>> currentList = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());
            /*
             * end:The period data query is complete
             */

            /*
             * start:Joining togethersqlQuery the data of the last period
             */
            if (QueryDateType.YEAR.value().equals(cycleType)) {
                year = year - 1;
            } else {
                month = month - 1;
            }
            conditionSql = StatisticsUtil.getInstance().createSql(cycleType, year, month);
            sql = "select SUM(order_price) as t_money, case " + conditionSql + " as time " +
                    " from es_sss_order_data o where  order_status = ? AND pay_status= ? ";
            sql += " group by time ";
            List<Map<String, Object>> lastList = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());
            /*
             * end:The data query of the last period is complete
             */

            // Determine whether to query by year or by month to obtain the scale length
            int time;
            if (QueryDateType.YEAR.value().equals(cycleType)) {
                time = 12;
            } else {
                // When comparing the current month with the previous month, the number of days is more. The month decreases by 1 when querying the data of the previous month, so you need to add another 1
                int currentMonth = DataDisplayUtil.getMonthDayNum(month + 1, year);
                int lastMonth = DataDisplayUtil.getMonthDayNum(month, year);
                if (currentMonth > lastMonth) {
                    time = currentMonth;
                } else {
                    time = lastMonth;
                }
            }

            // Fill the x scale
            String[] xAxis = new String[time];
            // The name of the data
            String[] localName = new String[time];
            for (int i = 0; i < time; i++) {
                xAxis[i] = i + 1 + "";
                localName[i] = i + 1 + "";
            }

            String[] data = new String[time];
            String[] lastData = new String[time];

            // Populate the array with data
            this.dataSet(currentList, data, time, "t_money");
            this.dataSet(lastList, lastData, time, "t_money");

            ChartSeries currentSeries = new ChartSeries((QueryDateType.YEAR.value().equals(cycleType) ? "This year," : "This month,"), data, localName);
            ChartSeries lastSeries = new ChartSeries((QueryDateType.YEAR.value().equals(cycleType) ? "Last year," : "Last month,"), lastData, localName);

            List<ChartSeries> series = new ArrayList<>();
            series.add(currentSeries);
            series.add(lastSeries);

            // Multi-data complex chart object
            return new MultipleChart(series, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }

    }


    /**
     * Sales statistics order quantity
     *
     * @param searchCriteria Statistical parameters, time
     * @return MultipleChart Complex chart data
     */
    @Override
    public MultipleChart getSalesNum(SearchCriteria searchCriteria) {

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        int month = null == searchCriteria.getMonth() ? 0 : searchCriteria.getMonth();
        SearchCriteria.checkDataParams(cycleType, year, month);
        SearchCriteria.checkDataParams(cycleType, year, month);

        try {

            // Parameter collection
            List<String> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.value());
            paramList.add(PayStatusEnum.PAY_YES.value());

            /*
             * start:Start stitchingsqlAnd query the current period data
             */
            String conditionSql = StatisticsUtil.getInstance().createSql(cycleType, year, month);
            String sql = "select count(0) as t_num, case " + conditionSql + " as time " +
                    " from es_sss_order_data o where order_status = ? AND pay_status = ?";
            sql += " group by time ";
            List<Map<String, Object>> currentList = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());
            /*
             * end:The data query of the current period is complete
             */

            /*
             * start:Start stitchingsqlQuery the data of the last period
             */
            if (QueryDateType.YEAR.value().equals(cycleType)) {
                year = year - 1;
            } else {
                month = month - 1;
            }

            conditionSql = StatisticsUtil.getInstance().createSql(cycleType, year, month);
            sql = "select count(0) as t_num, case " + conditionSql + " as time " +
                    " from es_sss_order_data o where order_status = ? AND pay_status = ? ";
            sql += " group by time ";
            List<Map<String, Object>> lastList = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());
            /*
             * end:The data query of the last period is complete
             */

            // X axis scale length
            int time;
            if (QueryDateType.YEAR.value().equals(cycleType)) {
                time = 12;
            } else {
                // When comparing the current month with the previous month, the number of days is more. The month decreases by 1 when querying the data of the previous month, so you need to add another 1
                int currentMonth = DataDisplayUtil.getMonthDayNum(month + 1, year);
                int lastMonth = DataDisplayUtil.getMonthDayNum(month, year);
                if (currentMonth > lastMonth) {
                    time = currentMonth;
                } else {
                    time = lastMonth;
                }
            }

            // The x axis calibration
            String[] xAxis = new String[time];
            // The name of the data
            String[] localName = new String[time];
            for (int i = 0; i < time; i++) {
                xAxis[i] = i + 1 + "";
                localName[i] = xAxis[i];
            }

            String[] data = new String[time];
            String[] lastData = new String[time];

            // Populate the Data and lastData arrays
            this.dataSet(currentList, data, time, "t_num");
            this.dataSet(lastList, lastData, time, "t_num");

            ChartSeries currentSeries = new ChartSeries((QueryDateType.YEAR.value().equals(cycleType) ? "This year," : "This month,"), data, localName);
            ChartSeries lastSeries = new ChartSeries((QueryDateType.YEAR.value().equals(cycleType) ? "Last year," : "Last month,"), lastData, localName);

            List<ChartSeries> series = new ArrayList<>();
            series.add(currentSeries);
            series.add(lastSeries);

            // Complex chart data
            return new MultipleChart(series, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }
    }


    /**
     * Sales statistics paging data
     *
     * @param searchCriteria Statistical parameters, time
     * @param pageNo         The query page
     * @param pageSize       Paging data length
     * @return Page Paging data
     */
    @Override
    public Page getSalesPage(SearchCriteria searchCriteria, int pageNo, int pageSize) {

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();
        SearchCriteria.checkDataParams(cycleType, year, month);

        SearchCriteria.checkDataParams(cycleType, year, month);

        try {

            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);
            long startTime = times[0];
            long endTime = times[1];

            // Add order status (completed), payment status (paid), time, store ID and other parameters
            List<Object> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.value());
            paramList.add(PayStatusEnum.PAY_YES.value());
            paramList.add(startTime);
            paramList.add(endTime);

            String sql = "select sn,buyer_name,create_time,order_price,order_status  from es_sss_order_data o where order_status = ? AND pay_status = ?" +
                    " and  o.create_time > ?  and  o.create_time < ?  order by create_time desc ";

            Page page = StatisticsUtil.getDataPage(this.daoSupport, searchCriteria.getYear(), sql, pageNo, pageSize, paramList.toArray());
            List<Map<String, Object>> list = page.getData();

            for (Map<String, Object> map : list) {
                // Gets the current timestamp, which is of type Integer in the database
                Integer timestamp = (Integer) map.get("create_time");
                map.replace("create_time", timestamp);

                // Gets the order status and changes the value to text, because only completed orders are queried, so it is directly completed
                String status = "Has been completed";
                map.replace("order_status", status);

            }

            return new Page(page.getPageNo(), page.getDataTotal(), page.getPageSize(), list);
        } catch (BadSqlGrammarException e) {
            // If the statistics table for a certain year does not exist, null data is returned
            if(e.getMessage().endsWith("doesn't exist")){
                return new Page(pageNo,0L,pageSize,new ArrayList());
            }
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }
    }

    /**
     * Sales analysis, data summary
     *
     * @param searchCriteria Statistical parameters, time
     * @return The sum of the order amount and the order quantity within the query time
     */
    @Override
    public Map getSalesSummary(SearchCriteria searchCriteria) {

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();
        SearchCriteria.checkDataParams(cycleType, year, month);

        SearchCriteria.checkDataParams(cycleType, year, month);

        try {

            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);
            long startTime = times[0];
            long endTime = times[1];

            // Add order status (completed), payment status (paid), time, store ID and other parameters
            List<Object> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.value());
            paramList.add(PayStatusEnum.PAY_YES.value());
            paramList.add(startTime);
            paramList.add(endTime);

            String sql = "SELECT COUNT(o.sn) AS order_num,SUM(o.order_price) AS order_price FROM es_sss_order_data o " +
                    "where order_status = ? AND pay_status = ? and  o.create_time > ?  and  o.create_time < ?";

            Map<String, Object> map = this.daoSupport.queryForMap(SyncopateUtil.handleSql(searchCriteria.getYear(), sql), paramList.toArray());
            if (map == null || map.size() <= 0){
                map = this.daoSupport.queryForMap(sql, paramList.toArray());
            }
            if (map.get("order_price") == null){
                map.put("order_price",0.00);
            }
            return map;
        }  catch (Exception e) {
            logger.error(e);
            // If the statistics table for a certain year does not exist, null data is returned
            return new HashMap();

        }
    }

    /**
     * Area analysis, map data
     *
     * @param searchCriteria Time dependent parameter
     * @param type           The type of data to get
     * @return MapChartData Map and chart data
     */
    @Override
    public List regionsMap(SearchCriteria searchCriteria, String type) {

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();
        SearchCriteria.checkDataParams(cycleType, year, month);

        try {

            // Get all provinces in the country
            List<Regions> provinceList = this.regionsClient.getRegionsChildren(0);

            // Parameter set, including order status, payment status, query time, store ID
            List<Object> paramList = new ArrayList<>();

            // The queried data does not contain any order status
            paramList.add(OrderStatusEnum.COMPLETE.value());

            // The queried data payment status should be paid
            paramList.add(PayStatusEnum.PAY_YES.value());

            // Gets the timestamp of the query time
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);
            long startTime = times[0];
            long endTime = times[1];
            paramList.add(startTime);
            paramList.add(endTime);

            // Distinguish the three types of data for area analysis, concatenate different SQL strings, and obtain different field names
            String[] needDataType = this.mapDataType(type);
            String sqlDifference = needDataType[0];
            String dataDifference = needDataType[1];


            // Get the number of members who placed orders within a certain period of time and the ID of their province
            String sql = "SELECT " + sqlDifference + ", ship_province_id FROM es_sss_order_data " +
                    "WHERE order_status = ? AND pay_status = ? AND create_time > ? " +
                    " AND create_time < ? GROUP BY ship_province_id";

            // Gets order statistics for all regions
            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());

            List<Map<String, Object>> result = new ArrayList<>();

            for (int i = 0; i < provinceList.size(); i++) {
                Regions regions = provinceList.get(i);
                Map<String, Object> map = new HashMap<>(16);
                map.put("name", regions.getLocalName());
                boolean flag = true;
                for (Map data : list) {
                    if (regions.getId().equals(data.get("ship_province_id"))) {
                        map.put("value", data.get(dataDifference).toString());
                        flag = false;
                    }
                }
                if (flag) {
                    map.put("value", 0);
                }
                result.add(map);
            }

            return result;
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }
    }

    /**
     * Purchase analysis, customer unit price distribution（Order quantity statistics within price range）
     *
     * @param searchCriteria Time dependent parameter
     * @param ranges         Price range. Only round numbers are accepted
     * @return SimpleChart Simple chart data
     */
    @Override
    public SimpleChart orderDistribution(SearchCriteria searchCriteria, Integer[] ranges) {

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();

        SearchCriteria.checkDataParams(cycleType, year, month);

        try {

            // Sort the price range in ascending order
            Arrays.sort(ranges);

            // Parameter collection
            List<Object> paramList = new ArrayList<>();

            // Price range SQL
            StringBuilder intervalSql = new StringBuilder();
            for (int i = 1; i < ranges.length; i++) {
                intervalSql.append(" WHEN o.order_price >= ? AND o.order_price <= ? THEN ? ");
                paramList.add(ranges[i - 1]);
                paramList.add(ranges[i]);
                paramList.add(i);
            }
            intervalSql.append(" WHEN o.order_price > ? THEN ? ");
            paramList.add(ranges[ranges.length-1]);
            paramList.add(ranges.length);
            intervalSql.append(" ELSE 0 END ");

            // Get timestamp
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);

            long startTime = times[0];
            long endTime = times[1];

            // Add time, order status (completed), payment status (paid), store ID and other parameters
            paramList.add(startTime);
            paramList.add(endTime);
            paramList.add(OrderStatusEnum.COMPLETE.value());
            paramList.add(PayStatusEnum.PAY_YES.value());
            String sql = "SELECT COUNT(o.sn) AS num, CASE " + intervalSql +
                    " AS distribution FROM es_sss_order_data o WHERE o.create_time > ? AND o.create_time < ? " +
                    "AND o.order_status = ? AND o.pay_status = ? GROUP BY distribution ";
            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());

            // X scale, data name, data
            String[] xAxis = new String[ranges.length];
            String[] localName = new String[ranges.length];
            String[] data = new String[ranges.length];

            /*
            xAxis array subscript scale name price range array subscript variable change
            0            0~100      0 1               i i+1
            1            100~200    1 2               i i+1
            2            200~300    2 3               i i+1
            3            300~400    3 4               i i+1
             */
            xAxis[xAxis.length-1] = ranges[ranges.length-1] + "+";
            localName[localName.length-1] = xAxis[xAxis.length-1];
            for (int i = 0; i < ranges.length - 1; i++) {
                xAxis[i] = ranges[i] + "~" + ranges[i + 1];
                localName[i] = xAxis[i];
                for (Map map : list) {
                    if ((i + 1) == (int) map.get("distribution")) {
                        data[i] = map.get("num").toString();
                        continue;
                    }
                    if (ranges.length == Integer.parseInt(map.get("distribution").toString())){
                        data[ranges.length-1] = map.get("num").toString();
                    }
                }
                if (null == (data[i])) {
                    data[i] = "0";
                }
            }
            if (StringUtil.isEmpty(data[ranges.length-1])){
                data[ranges.length-1] = "0";
            }

            ChartSeries chartSeries = new ChartSeries("Order quantity", data, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);

        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }

    }

    /**
     * Purchase analysis, purchase period distribution
     *
     * @param searchCriteria Time dependent parameter
     * @return SimpleChart Simple chart data
     */
    @Override
    public SimpleChart purchasePeriod(SearchCriteria searchCriteria) {

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();

        SearchCriteria.checkDataParams(cycleType, year, month);

        try {

            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);

            long startTime = times[0];
            long endTime = times[1];

            List<Object> paramList = new ArrayList();

            paramList.add(OrderStatusEnum.COMPLETE.value());
            paramList.add(PayStatusEnum.PAY_YES.value());
            paramList.add(startTime);
            paramList.add(endTime);

            // Change the time zone to CST
            String sqlTimezoneConvert = "CONVERT(DATE_FORMAT(CONVERT_TZ(FROM_UNIXTIME(o.create_time), @@session.time_zone,'+8:00'), '%k'), SIGNED)";

            String sql = "SELECT count(o.sn) AS num, "+sqlTimezoneConvert+" AS hour_num" +
                    " FROM es_sss_order_data o WHERE order_status = ? AND pay_status = ?" +
                    "AND o.create_time >= ? AND o.create_time <= ? GROUP BY hour_num ORDER BY hour_num";

            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());

            // Number of hours
            int hours = 24;

            String[] xAxis = new String[hours];
            String[] localName = new String[hours];
            String[] data = new String[hours];

            for (int i = 0; i < hours; i++) {
                xAxis[i] = i + "";
                localName[i] = xAxis[i];
                for (Map map : list) {
                    if ((i) == (int) map.get("hour_num")) {
                        data[i] = map.get("num").toString();
                    }
                }
                if (null == data[i]) {
                    data[i] = 0 + "";
                }
            }

            ChartSeries chartSeries = new ChartSeries("Order quantity", data, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }
    }

    /**
     * Sales statistics, filling data
     *
     * @param list  Database data
     * @param data  xAxis data
     * @param time  xAxis scale length
     * @param title Gets the type of data
     */
    private void dataSet(List<Map<String, Object>> list, String[] data, int time, String title) {

        if (null != list && list.size() != 0) {
            for (int i = 0; i < time; i++) {
                for (Map map : list) {
                    if ((i + 1) == (Integer) map.get("time")) {
                        data[i] = map.get(title).toString();
                    }
                }
                if (null == data[i] || "".equals(data[i])) {
                    data[i] = 0 + "";
                }
            }
        } else {
            for (int i = 0; i < time; i++) {
                data[i] = 0 + "";
            }
        }

    }

    /**
     * Area analysis, determination of required data types
     *
     * @param type The required type
     * @return String[] sqlStatement and actionMapthekey值the字段名
     */
    private String[] mapDataType(String type) {

        // Distinguish the three types of data for area analysis, concatenate different SQL strings, and obtain different field names
        String sqlDifference = "";
        String dataDifference = "";

        // 1. Number of ordering members 2. Amount of ordering 3
        if (RegionsDataType.ORDER_MEMBER_NUM.value().equals(type)) {
            sqlDifference = " COUNT(DISTINCT buyer_id) member ";
            dataDifference = "member";
        } else if (RegionsDataType.ORDER_PRICE.value().equals(type)) {
            sqlDifference = " SUM(order_price) price ";
            dataDifference = "price";
        } else if (RegionsDataType.ORDER_NUM.value().equals(type)) {
            sqlDifference = "COUNT(DISTINCT sn) num";
            dataDifference = "num";
        }

        String[] neededData = new String[2];
        neededData[0] = sqlDifference;
        neededData[1] = dataDifference;

        return neededData;
    }



}
