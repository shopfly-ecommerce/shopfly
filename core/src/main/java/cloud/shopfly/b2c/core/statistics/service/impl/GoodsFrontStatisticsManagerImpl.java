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

import cloud.shopfly.b2c.core.statistics.StatisticsErrorCode;
import cloud.shopfly.b2c.core.statistics.StatisticsException;
import cloud.shopfly.b2c.core.statistics.model.vo.ChartSeries;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.GoodsFrontStatisticsManager;
import cloud.shopfly.b2c.core.statistics.util.StatisticsUtil;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Merchant center, product analysis statistical interface implementation body
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0 2018/3/28 In the morning9:49
 */
@Service
public class GoodsFrontStatisticsManagerImpl implements GoodsFrontStatisticsManager {

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    
    private DaoSupport daoSupport;

    /**
     * Obtain product details
     *
     * @param pageNo    The current page number
     * @param pageSize  Data volume per page
     * @param catId     Categoryid
     * @param goodsName Name
     * @return Page Paging data
     */
    @Override
    public Page getGoodsDetail(Integer pageNo, Integer pageSize, Integer catId, String goodsName) {

        try {
            // The list of parameters
            ArrayList<Object> paramList = new ArrayList<>(16);
            // Get todays 23:59:59 time stamp
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DATE);
            long endTime = DateUtil.toDate(year + "-" + month + "-" + day + " 23:59:59", "yyyy-MM-dd HH:mm:ss")
                    .getTime() / 1000;

            // Gets the timestamp of 23:59:59 30 days ago
            long startTime = endTime - 2592000;

            // The timestamp is converted to a string
            String startDate = String.valueOf(startTime);
            String endDate = String.valueOf(endTime);
            // Join the parameters
            paramList.add(startDate);
            paramList.add(endDate);

            // If the item name is not empty, fuzzy query is performed for the item name
            String nameWhere = "";
            if (goodsName != null && !"".equals(goodsName)) {
                nameWhere = " AND oi.goods_name LIKE ? ";
                paramList.add("%" + goodsName + "%");
            }

            // Commodity classification path
            String catPath;

            // If the category ID is 0, all categories are queried. If the category id is not 0, the path is queried in the database. If there is no category or duplicate data, empty data is returned
            if (catId == 0) {
                catPath = "0";
            } else {
                List<Map<String,Object>> categoryPath = this.daoSupport.queryForList(
                        "SELECT DISTINCT gd.category_path FROM es_sss_goods_data gd WHERE gd.category_id = " + catId);
                if (null == categoryPath || 0 == categoryPath.size() || 1 < categoryPath.size()) {
                    return new Page(pageNo, 0L, pageSize, new ArrayList());
                }
                Map<String, Object> map = categoryPath.get(0);
                catPath = map.get("category_path").toString();
            }

            // Splice Sql store ID, completed order, time range, product name, classification path, grouped by product ID
            String sql = "SELECT goods_id,goods_name , SUM(goods_num) AS numbers,ROUND(price,2) AS price,ROUND(price * SUM(goods_num),2) AS total_price" +
                    " FROM es_sss_order_goods_data oi WHERE oi.order_sn IN (SELECT sn FROM es_sss_order_data o WHERE o.create_time >= ? AND o.create_time <= ? ) " + nameWhere + " AND oi.category_path like '%" + catPath + "%' "
                    + "  GROUP BY goods_id ,goods_name,price ";

            return StatisticsUtil.getDataPage(this.daoSupport, year, sql, pageNo, pageSize, paramList.toArray());
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }
    }


    /**
     * A price range
     *
     * @param sections       intervalList format：0 100 200
     * @param searchCriteria Time, classificationidShops,id
     * @return SimpleChart Simple chart data
     */
    @Override
    public SimpleChart getGoodsPriceSales(List<Integer> sections, SearchCriteria searchCriteria) {

        // Validate parameter
        SearchCriteria.checkDataParams(searchCriteria, true, false);

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();
        Integer catId = searchCriteria.getCategoryId();

        try {
            // Get the start time and end time
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);

            // Parameter collection
            ArrayList<Object> paramList = new ArrayList<>();

            // Time conditional string
            String dateWhere = "";

            dateWhere += " o.create_time >= ? ";
            paramList.add(times[0]);
            dateWhere += " AND o.create_time <= ? ";
            paramList.add(times[1]);

            // Classification of goods
            String catPath;
            if (null == catId || catId == 0) {
                catPath = "0";
            } else {
                catPath = this.daoSupport.queryForString(
                        "SELECT DISTINCT(gd.category_path) FROM es_sss_goods_data gd WHERE gd.category_id = " + catId);
            }

            // Concatenated CASE statement
            String caseStatement = getGoodsPriceSqlCaseStatement(sections);
            String sql = "SELECT SUM(oi.goods_num) AS goods_num, " + caseStatement
                    + "AS elt_data FROM es_sss_order_goods_data oi left join es_sss_order_data o on oi.order_sn=o.sn WHERE "
                    + dateWhere + " AND oi.category_path like '%" + catPath + "%'" + " GROUP BY oi.price";

            String mainSql = "SELECT SUM(t1.goods_num) goods_num, t1.elt_data FROM(" + sql
                    + ") t1 GROUP BY t1.elt_data ORDER BY t1.elt_data";

            List<Map<String, Object>> data = this.daoSupport.queryForList(mainSql, paramList.toArray());

            // Reorder the data
            data = StatisticsUtil.getInstance().fitOrderPriceData(data, sections);

            // The chart data
            String[] chartData = new String[data.size()];

            // The data name is the interval
            String[] localName = new String[data.size()];

            int i = 0;
            for (Map map : data) {
                chartData[i] = null == map.get("goods_num") ? "0" : map.get("goods_num").toString();
                localName[i] = map.get("elt_data").toString();
                i++;
            }

            ChartSeries chartSeries = new ChartSeries("Price and volume analysis", chartData, localName);

            return new SimpleChart(chartSeries, localName, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }
    }

    /**
     * Before obtaining the order amount30
     *
     * @param topNum         topThe number
     * @param searchCriteria Time dependent parameter
     * @return Page Paging object
     */
    @Override
    public Page getGoodsOrderPriceTopPage(int topNum, SearchCriteria searchCriteria) {

        SearchCriteria.checkDataParams(searchCriteria, true, false);
        try {

            // Parameter collection
            ArrayList<Object> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.name());

            // If the ranking has no value, the default is 30
            if (topNum == 0) {
                topNum = 30;
            }
            // Get timestamp
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(searchCriteria.getCycleType(),
                    searchCriteria.getYear(), searchCriteria.getMonth());

            // The time condition
            String dateWhere = "";
            dateWhere += "AND o.create_time >= ? ";
            paramList.add(times[0]);
            dateWhere += " AND o.create_time <= ? ";
            paramList.add(times[1]);
            // Splice Sql store ID, completed order, time range, grouped by commodity ID, sorted by total price
            String sql = "FROM es_sss_order_goods_data WHERE order_sn IN (SELECT sn FROM es_sss_order_data o WHERE "
                    + " order_status = ? " + dateWhere + ") GROUP BY goods_id  ";

            String selectPage = "SELECT goods_name,goods_id,price,SUM(goods_num) * price AS sum "
                    + sql;

            String selectCount = "SELECT count(*) from (" + selectPage + ") t0";

            selectPage += " ORDER BY SUM(goods_num) * price DESC";

            return StatisticsUtil.getDataPage(this.daoSupport, searchCriteria.getYear(), selectPage, selectCount, 1, topNum, paramList.toArray());
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }
    }

    /**
     * Order quantity before30, paging data
     *
     * @param topNum         The ranking defaults to30
     * @param searchCriteria Time dependent parameter
     * @return Page Paging object
     */
    @Override
    public Page getGoodsNumTopPage(int topNum, SearchCriteria searchCriteria) {

        SearchCriteria.checkDataParams(searchCriteria, true, false);

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();

        try {

            // Parameter collection
            ArrayList<Object> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.name());

            // If the ranking has no value
            if (topNum == 0) {
                topNum = 30;
            }

            // The time condition
            String dateWhere = "";
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);

            // If you include the start time condition
            dateWhere += "and o.create_time >= ? ";
            paramList.add(times[0]);
            dateWhere += " AND o.create_time <= ? ";
            paramList.add(times[1]);

            // Splice Sql store ID, completed order, time range, grouped by commodity ID and commodity name, sorted by commodity quantity in descending order
            String sql = "FROM es_sss_order_goods_data WHERE order_sn IN (SELECT sn FROM es_sss_order_data o WHERE "
                    + " order_status = ? " + dateWhere + ") GROUP BY goods_id,goods_name";
            String selectPage = "SELECT goods_name,goods_id,SUM(goods_num) as all_num " + sql;

            String selectCount = "select count(*) from (" + selectPage + ") t0";
            selectPage += " ORDER BY SUM(goods_num) DESC";

            return StatisticsUtil.getDataPage(this.daoSupport, searchCriteria.getYear(), selectPage, selectCount, 1, topNum, paramList.toArray());
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }
    }

    /**
     * Before obtaining the order amount of goods30, chart data
     *
     * @param topNum         topThe number
     * @param searchCriteria Time dependent parameter
     * @return SimpleChart Simple chart data
     */
    @Override
    public SimpleChart getGoodsOrderPriceTop(Integer topNum, SearchCriteria searchCriteria) {

        SearchCriteria.checkDataParams(searchCriteria, true, false);
        try {
            // Get the id of the member store currently logged in
            ArrayList<Object> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.name());

            // If the ranking has no value
            if (topNum == 0) {
                topNum = 30;
            }
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(searchCriteria.getCycleType(),
                    searchCriteria.getYear(), searchCriteria.getMonth());

            // The time condition
            String dateWhere = "";
            dateWhere += "AND o.create_time >= ? ";
            paramList.add(times[0]);
            dateWhere += " AND o.create_time <= ? ";
            paramList.add(times[1]);
            // Splice Sql store ID, completed order, time range, grouped by commodity ID, sorted by total amount
            String sql = "FROM es_sss_order_goods_data WHERE order_sn IN (SELECT sn FROM es_sss_order_data o WHERE "
                    + " order_status = ? " + dateWhere + "" + ") GROUP BY goods_id  ";

            String selectPage = "SELECT goods_name,goods_id,price,SUM(goods_num) * price AS sum "
                    + sql;

            selectPage += " ORDER BY SUM(goods_num) * price DESC LIMIT " + topNum;

            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), selectPage, paramList.toArray());

            // Chart data, order amount
            String[] data = new String[list.size()];

            // The data name is the commodity name
            String[] localName = new String[list.size()];

            // The x axis calibration
            String[] xAxis = new String[list.size()];

            // If the data is greater than 0, it is traversed
            int dataNum = list.size();
            if (list.size() > 0) {
                for (int i = 0; i < dataNum; i++) {
                    Map map = list.get(i);
                    data[i] = map.get("sum").toString();
                    localName[i] = map.get("goods_name").toString();
                    xAxis[i] = i + 1 + "";
                }
            }

            ChartSeries chartSeries = new ChartSeries("The total amount", data, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }

    }

    /**
     * Before obtaining the purchase quantity of goods30
     *
     * @param topNum         topThe number
     * @param searchCriteria Time dependent parameter
     * @return SimpleChart Simple chart data
     */
    @Override
    public SimpleChart getGoodsNumTop(Integer topNum, SearchCriteria searchCriteria) {

        SearchCriteria.checkDataParams(searchCriteria, true, false);

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();

        try {
            // Get the id of the member store currently logged in
            ArrayList<Object> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.name());

            // If the ranking has no value
            if (topNum == 0) {
                topNum = 30;
            }

            // The time condition
            String dateWhere = "";
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);

            // If you include the start time condition
            dateWhere += "AND o.create_time >= ? ";
            paramList.add(times[0]);
            dateWhere += " AND o.create_time <= ? ";
            paramList.add(times[1]);

            // Stitching Sql
            String sql = "FROM es_sss_order_goods_data WHERE order_sn IN (SELECT sn FROM es_sss_order_data o WHERE "
                    + " order_status = ? " + dateWhere + ") GROUP BY goods_id,goods_name";
            String selectCharts = "SELECT goods_name,goods_id,SUM(goods_num) as all_num " + sql;

            selectCharts += " ORDER BY SUM(goods_num) DESC LIMIT " + topNum;

            // To get the data
            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), selectCharts, paramList.toArray());

            // Chart data, i.e. the quantity of goods purchased
            String[] chartData = new String[list.size()];

            // The data name is the commodity name
            String[] localName = new String[list.size()];

            // The x axis calibration
            String[] xAxis = new String[list.size()];

            // If the list has data, it is traversed
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = list.get(i);
                    chartData[i] = map.get("all_num").toString();
                    localName[i] = map.get("goods_name").toString();
                    xAxis[i] = i + 1 + "";
                }
            }

            ChartSeries chartSeries = new ChartSeries("Order quantity", chartData, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }
    }

    /**
     * Generating price and sales statisticsSQL CASEstatements
     *
     * @param ranges The integer set
     * @return SQL CASE Statement
     */
    private static String getGoodsPriceSqlCaseStatement(List<Integer> ranges) {
        if (null == ranges || ranges.size() == 0) {
            return "0";
        }
        // Sort from largest to smallest
        StatisticsUtil.sortRanges(ranges);

        StringBuilder sb = new StringBuilder("(case ");
        sb.append("when oi.price > ").append(ranges.get(0)).append(" then '").append(ranges.get(0)).append("+' ");
        for (int i = 0; i < ranges.size(); ) {
            Integer num = ranges.get(i);
            Integer nextNum;
            if (i < ranges.size() - 1) {
                nextNum = ranges.get(i + 1);
                sb.append("when oi.price >= ").append(nextNum).append(" and oi.price <= ").append(num).append(" then '")
                        .append(nextNum).append("~").append(num).append("' ");
                i+=2;
            }
        }
        sb.append("else '0' end ) ");
        return sb.toString();
    }

}
