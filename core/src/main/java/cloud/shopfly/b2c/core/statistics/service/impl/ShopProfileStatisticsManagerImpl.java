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
import cloud.shopfly.b2c.core.statistics.model.vo.ShopProfileVO;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.ShopProfileStatisticsManager;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PayStatusEnum;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Store overview management implementation class
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018/3/28 In the morning9:50
 */
@Service
public class ShopProfileStatisticsManagerImpl implements ShopProfileStatisticsManager {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    
    private DaoSupport daoSupport;

    /**
     * Get store profile
     *
     * @return ShopProfileVO Store Overview DisplayVO
     */
    @Override
    public ShopProfileVO data() {
        try {

            // Start time within 30 days
            long startTime = DateUtil.startOfSomeDay(30);
            long endTime = DateUtil.endOfTodDay();

            List<Object> paramList = new ArrayList();

            paramList.add(startTime);
            paramList.add(endTime);
            paramList.add(OrderStatusEnum.COMPLETE.value());
            paramList.add(PayStatusEnum.PAY_YES.value());

            // Get order amount, order number of members, order quantity, order number of goods
            String sql = "SELECT SUM(o.order_price) AS order_money ,COUNT(DISTINCT o.buyer_id) AS order_member" +
                    ", COUNT(o.sn) AS order_num,SUM(o.goods_num) AS order_good FROM es_sss_order_data o" +
                    " WHERE o.create_time >= ? AND o.create_time <= ? AND o.order_status = ? AND o.pay_status= ?";

            Map<String, Object> map = this.daoSupport.queryForMap(sql, paramList.toArray());

            ShopProfileVO shopProfileVO = new ShopProfileVO();
            // Place the order amount
            String orderMoney = null == map.get("order_money") ? "0.0" : map.get("order_money").toString();
            shopProfileVO.setOrderMoney(orderMoney);
            // Number of order members
            String orderMember = null == map.get("order_member") ? "0" : map.get("order_member").toString();
            shopProfileVO.setOrderMember(orderMember);
            // Order quantity
            String orderNum = null == map.get("order_num") ? "0" : map.get("order_num").toString();
            shopProfileVO.setOrderNum(orderNum);
            // Number of goods ordered
            String orderGoods = null == map.get("order_good") ? "0" : map.get("order_good").toString();
            shopProfileVO.setOrderGoods(orderGoods);
            // Average unit price
            Double averageMemberMoney = 0.0;
            if (!"0".equals(orderMember)) {
                double orderMoneyNum = new Double(orderMoney);
                double orderMemberNum = new Double(orderMember);
                averageMemberMoney = CurrencyUtil.div(orderMoneyNum, orderMemberNum, 2);
            }
            DecimalFormat df = new DecimalFormat("0.##");
            shopProfileVO.setAverageMemberMoney(df.format(averageMemberMoney));
            // Average commodity price
            Double averageGoodsMoney = 0.0;
            if (!"0".equals(orderGoods)) {
                double orderMoneyNum = new Double(orderMoney);
                double orderGoodsNum = new Double(orderGoods);
                averageGoodsMoney = CurrencyUtil.div(orderMoneyNum, orderGoodsNum, 2);
            }
            shopProfileVO.setAverageGoodsMoney(df.format(averageGoodsMoney));

            sql = "SELECT COUNT(DISTINCT g.goods_id) AS total_goods, SUM(g.favorite_num) AS goods_collect " +
                    " FROM es_sss_goods_data g WHERE g.market_enable = 1";

            map = this.daoSupport.queryForMap(sql);

            // Total store merchandise
            String totalGoods = null == map.get("total_goods") ? "0" : map.get("total_goods").toString();
            shopProfileVO.setTotalGoods(totalGoods);
            // Total commodity collection
            String goodsCollect = null == map.get("goods_collect") ? "0" : map.get("goods_collect").toString();
            shopProfileVO.setGoodsCollect(goodsCollect);

            StringBuilder conditionSql = this.getConditionSql();

            ZonedDateTime nowDate = DateUtil.getNowZoneDateTime();

            long startDate = nowDate.plusDays(-29).toEpochSecond();
            long endDate = nowDate.plusHours(23).plusMinutes(59).plusSeconds(59).toEpochSecond();

            sql = "select count(1) FROM es_sss_order_data o WHERE o.order_status = ? AND o.pay_status = ?  AND o.create_time BETWEEN ? and ?  ";

            int count = this.daoSupport.queryForInt(sql, OrderStatusEnum.COMPLETE.value(), PayStatusEnum.PAY_YES.value(), startDate, endDate);

            if (count > 0) {
                sql = "SELECT s.num,s.time FROM (SELECT COUNT(o.sn) AS num,(CASE " + conditionSql.toString() + ") AS time " +
                        " FROM es_sss_order_data o WHERE o.order_status = ? AND o.pay_status = ? AND  o.create_time BETWEEN ? and ?  GROUP BY time) s order by s.num DESC LIMIT 1";

                map = this.daoSupport.queryForMap(sql, OrderStatusEnum.COMPLETE.value(), PayStatusEnum.PAY_YES.value(), startDate, endDate);

            }


            String orderFastigium = "no";
            String time = null == map.get("time") ? "0" : map.get("time").toString();

            if (null != time && !"0".equals(time)) {
                orderFastigium = time;
            }

            shopProfileVO.setOrderFastigium(orderFastigium);

            return shopProfileVO;

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }
    }

    /**
     * Store profile, get close30World order amount
     *
     * @return SimpleChart Simple chart data
     */
    @Override
    public SimpleChart chart() {
        // Querying a Map Collection
        try {

            List<Object> paramList = new ArrayList();
            paramList.add(OrderStatusEnum.COMPLETE.value());
            paramList.add(PayStatusEnum.PAY_YES.value());
            // Data name, same as x scale name
            String[] localName = new String[30];

            int limitDays = 30;
            for (int i = 0; i < limitDays; i++) {
                Map<String, Object> map = DateUtil.getYearMonthAndDay(i);
                String month = map.get("month").toString();
                String day = map.get("day").toString();
                localName[i] = month + "-" + day;
            }

            StringBuilder conditionSql = this.getConditionSql();

            String sql = "SELECT SUM(o.order_price) AS money, CASE " + conditionSql.toString() + " AS time " +
                    " FROM es_sss_order_data o WHERE o.order_status = ? AND o.pay_status = ? GROUP BY time";
            List<Map<String, Object>> list = this.daoSupport.queryForList(sql, paramList.toArray());

            String[] data = new String[30];

            // Loop through xAxis, and if it is the same as time, put money into the data array, with zero for the array elements that have no data
            for (int i = 0; i < limitDays; i++) {
                for (Map map : list) {
                    if (localName[i].equals(map.get("time").toString())) {
                        data[i] = map.get("money").toString();
                    }
                }
                if (null == data[i]) {
                    data[i] = 0 + "";
                }
            }

            ChartSeries chartSeries = new ChartSeries("Place the order amount", data, localName);

            return new SimpleChart(chartSeries, localName, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "Business exceptions");
        }
    }

    private StringBuilder getConditionSql() {

        // Time group, and get the data name
        StringBuilder conditionSql = new StringBuilder();
        // Data in the last 30 days were collected
        int limitDays = 30;
        for (int i = 0; i < limitDays; i++) {
            Map<String, Object> map = DateUtil.getYearMonthAndDay(i);
            String year = map.get("year").toString();
            String month = map.get("month").toString();
            String day = map.get("day").toString();
            String dayDate = year + "-" + month + "-" + day;
            long start = DateUtil.getDateline(dayDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            long end = DateUtil.getDateline(dayDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            conditionSql.append(" when create_time >= ").append(start).append(" and   create_time <=").append(end).append(" then '").append(month).append("-").append(day).append("'");
        }
        conditionSql.append(" else '0' end");

        return conditionSql;
    }

}
