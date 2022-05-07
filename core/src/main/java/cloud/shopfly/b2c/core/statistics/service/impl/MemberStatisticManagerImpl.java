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
import cloud.shopfly.b2c.core.statistics.model.enums.QueryDateType;
import cloud.shopfly.b2c.core.statistics.model.vo.ChartSeries;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.MemberStatisticManager;
import cloud.shopfly.b2c.core.statistics.util.ChartUtil;
import cloud.shopfly.b2c.core.statistics.util.DataDisplayUtil;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PayStatusEnum;
import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Member statistics implementation class
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/4/28 In the afternoon5:11
 */
@Service
public class MemberStatisticManagerImpl implements MemberStatisticManager {

    protected final Log logger = org.apache.commons.logging.LogFactory.getLog(this.getClass());
    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public SimpleChart getIncreaseMember(SearchCriteria searchCriteria) {
        // Parameter calibration
        searchCriteria = new SearchCriteria(searchCriteria);
        try {
            // Number of results obtained
            Integer resultSize = DataDisplayUtil.getResultSize(searchCriteria);

            Page page = this.getIncreaseMemberPage(searchCriteria);
            List<Map<String, Object>> result = page.getData();

            String[] nowData = new String[resultSize];

            // Generate icon data
            int i = 0;
            for (Map<String, Object> mrd : result) {
                nowData[i] = mrd.get("num").toString();
                i++;
            }
            ChartSeries chartSeries = new ChartSeries("Number of new members", nowData);
            return new SimpleChart(chartSeries, ChartUtil.structureXAxis(searchCriteria.getCycleType(), searchCriteria.getYear(), searchCriteria.getMonth()), new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }


    @Override
    public Page getIncreaseMemberPage(SearchCriteria searchCriteria) {
        // Parameter calibration
        searchCriteria = new SearchCriteria(searchCriteria);
        try {

            // Number of results obtained
            Integer resultSize = DataDisplayUtil.getResultSize(searchCriteria);
            // Number of results obtained
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
            long[] lasttimestamp = DataDisplayUtil.getLastStartTimeAndEndTime(searchCriteria);

            /*
             * parameter
             */
            List<Object> params = new ArrayList<>();
            /*
             * parameter
             */
            List<Object> lastParams = new ArrayList<>();

            // Determine the SQL date grouping criteria
            String circle;
            if (Objects.equals(searchCriteria.getCycleType(), QueryDateType.YEAR.name())) {
                circle = "%m";
            } else {
                circle = "%d";
            }

            StringBuffer sql = new StringBuffer("SELECT COUNT(em.member_id) AS num , em.e_regtime AS time  FROM ( SELECT m.member_id,m.create_time, FROM_UNIXTIME(m.create_time, ?)  AS e_regtime FROM es_sss_member_register_data m WHERE  m.create_time >= ? AND  m.create_time <= ? ) em GROUP BY em.e_regtime");
            params.add(circle);
            params.add(timestamp[0]);
            params.add(timestamp[1]);
            lastParams.add(circle);
            lastParams.add(lasttimestamp[0]);
            lastParams.add(lasttimestamp[1]);

            List<Map<String, Object>> list = this.daoSupport.queryForList(sql.toString(), params.toArray());
            List<Map<String, Object>> lastList = this.daoSupport.queryForList(sql.toString(), lastParams.toArray());

            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
            // Put the data of the previous period into the map with time as the key
            Map lastMap = new HashMap();
            for (Map<String, Object> mrd2 : lastList) {

                lastMap.put(Integer.parseInt(mrd2.get("time").toString()),mrd2.get("num"));
            }

            // Generate comparative data
            Map<Integer,Map<String, Object>> map = new HashMap();
            for (Map<String, Object> mrd : list) {
                Integer key = Integer.parseInt(mrd.get("time").toString());
                if (lastMap.get(key)!=null) {
                    double value = Double.parseDouble(mrd.get("num").toString());
                    double lastValue = Double.parseDouble(lastMap.get(key).toString());
                    double num = ((value - lastValue) / lastValue) * 100;
                    String result = df.format(num) + "%";
                    mrd.put("last_num", lastMap.get(key)==null?0:lastMap.get(key));
                    mrd.put("growth", result);
                }else{
                    mrd.put("last_num", lastMap.get(key)==null?0:lastMap.get(key));
                    mrd.put("growth", 0);
                }
                mrd.put("time",Integer.parseInt(mrd.get("time").toString()));
                map.put(key,mrd);
            }
            // End result data population
            List<Map<String, Object>> result = new ArrayList<>();

            for (int i = 1; i <= resultSize; i++) {
                Map<String, Object> mrd = null;

                if(map.get(i)==null){
                    mrd = new HashMap<>();
                    mrd.put("time",i);
                    mrd.put("num",0);
                    mrd.put("last_num", lastMap.get(i)==null ? 0:lastMap.get(i));
                    if(lastMap.get(i)==null){

                        mrd.put("growth", 0);
                    }else{
                        double num = ((0 - Double.parseDouble(lastMap.get(i).toString())) / Double.parseDouble(lastMap.get(i).toString())) * 100;
                        mrd.put("growth", df.format(num) + "%");
                    }
                }else{
                    mrd = map.get(i);
                }
                result.add(mrd);
            }

            return new Page(1, (long) resultSize, resultSize, result);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public SimpleChart getMemberOrderQuantity(SearchCriteria searchCriteria) {

        searchCriteria = new SearchCriteria(searchCriteria);

        try {
            Page page = this.getMemberOrderQuantityPage(searchCriteria);
            List<Map<String, Object>> list = page.getData();
            int dataLength = 10;
            String[] xAxis = new String[dataLength], localName = new String[dataLength], data = new String[dataLength];

            for (int i = 0; i < dataLength; i++) {
                if (null != list && i < list.size()) {
                    Map map = list.get(i);
                    data[i] = map.get("order_num").toString();
                    localName[i] = map.get("member_name").toString();
                } else {
                    data[i] = "0";
                    localName[i] = "There is no";
                }
                xAxis[i] = i + 1 + "";
            }

            ChartSeries chartSeries = new ChartSeries("Member orders", data, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }


    @Override
    public Page getMemberOrderQuantityPage(SearchCriteria searchCriteria) {

        searchCriteria = new SearchCriteria(searchCriteria);

        try {
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);

            /*
             * parameter
             */
            List<Object> params = new ArrayList<>();

            /*
             * The querysql
             */
            StringBuffer sql = new StringBuffer();

            sql.append("SELECT count(0) AS order_num,m.`member_name` AS member_name "
                    + " FROM es_sss_order_data o INNER JOIN es_sss_member_register_data m ON o.`buyer_id` = m.`member_id` "
                    + " WHERE o.`create_time` >= ? AND o.`create_time` <= ? AND (o.order_status = '"
                    + OrderStatusEnum.COMPLETE.name() + "'	OR o.pay_status = '" + PayStatusEnum.PAY_YES.name() + "') ");
            params.add(timestamp[0]);
            params.add(timestamp[1]);

            sql.append(" GROUP BY member_name ORDER BY order_num DESC,member_name asc ");
            List<Map> list = this.daoSupport.queryForListPage(sql.toString(), 1, 10, params.toArray());
            return new Page(1, (long) list.size(), 10, list);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public SimpleChart getMemberGoodsNum(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);
        try {

            Page page = this.getMemberGoodsNumPage(searchCriteria);
            List<Map<String, Object>> list = page.getData();

            int dataLength = 10;
            String[] xAxis = new String[dataLength], localName = new String[dataLength], data = new String[dataLength];

            for (int i = 0; i < dataLength; i++) {
                if (null != list && i < list.size()) {
                    Map map = list.get(i);
                    data[i] = map.get("goods_num").toString();
                    localName[i] = map.get("member_name").toString();
                } else {
                    data[i] = "0";
                    localName[i] = "There is no";
                }
                xAxis[i] = i + 1 + "";
            }

            ChartSeries chartSeries = new ChartSeries("Number of items placed by members", data, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }


    @Override
    public Page getMemberGoodsNumPage(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);
        try {
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);

            /*
             * parameter
             */
            List<Object> params = new ArrayList<>();

            /*
             * The querysql
             */
            StringBuffer sql = new StringBuffer();

            sql.append("SELECT sum(o.`goods_num`) AS goods_num,m.`member_name` AS member_name "
                    + " FROM es_sss_order_data o INNER JOIN es_sss_member_register_data m ON o.`buyer_id` = m.`member_id` "
                    + " WHERE o.`create_time` >= ? AND o.`create_time` <= ?  AND (o.order_status = '"
                    + OrderStatusEnum.COMPLETE.name() + "'	OR o.pay_status = '" + PayStatusEnum.PAY_YES.name() + "') ");
            params.add(timestamp[0]);
            params.add(timestamp[1]);

            sql.append(" GROUP BY member_name ORDER BY goods_num DESC ");

            List<Map> list = this.daoSupport.queryForListPage(sql.toString(), 1, 10, params.toArray());

            return new Page(1, (long) list.size(), 10, list);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public SimpleChart getMemberMoney(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);
        try {
            Page page = this.getMemberMoneyPage(searchCriteria);
            List<Map<String, Object>> list = page.getData();
            int dataLength = 10;
            String[] xAxis = new String[dataLength], localName = new String[dataLength], data = new String[dataLength];

            for (int i = 0; i < dataLength; i++) {
                if (null != list && i < list.size()) {
                    Map map = list.get(i);
                    data[i] = map.get("total_money").toString();
                    localName[i] = map.get("member_name").toString();
                } else {
                    data[i] = "0";
                    localName[i] = "There is no";
                }
                xAxis[i] = i + 1 + "";
            }

            ChartSeries chartSeries = new ChartSeries("Member order amount", data, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (StatisticsException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public Page getMemberMoneyPage(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);
        try {

            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);

            /*
             * parameter
             */
            List<Object> params = new ArrayList<>();

            /*
             * The querysql
             */
            StringBuffer sql = new StringBuffer();

            sql.append("SELECT SUM(o.`order_price`) AS total_money,m.`member_name` AS member_name "
                    + " FROM es_sss_order_data o INNER JOIN es_sss_member_register_data m ON o.`buyer_id` = m.`member_id` "
                    + " WHERE o.`create_time` >= ? AND o.`create_time` <= ?  AND (o.order_status = '"
                    + OrderStatusEnum.COMPLETE.name() + "'	OR o.pay_status = '" + PayStatusEnum.PAY_YES.name() + "') ");
            params.add(timestamp[0]);
            params.add(timestamp[1]);
            sql.append(" GROUP BY member_name ORDER BY total_money DESC ");

            List<Map> list = this.daoSupport.queryForListPage(sql.toString(), 1, 10, params.toArray());

            return new Page(1, (long) list.size(), 10, list);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }

    }
}
