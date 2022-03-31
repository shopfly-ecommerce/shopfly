/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.service.impl;

import dev.shopflix.core.base.SearchCriteria;
import dev.shopflix.core.client.system.RegionsClient;
import dev.shopflix.core.statistics.StatisticsErrorCode;
import dev.shopflix.core.statistics.StatisticsException;
import dev.shopflix.core.statistics.model.enums.QueryDateType;
import dev.shopflix.core.statistics.model.enums.RegionsDataType;
import dev.shopflix.core.statistics.model.vo.ChartSeries;
import dev.shopflix.core.statistics.model.vo.MultipleChart;
import dev.shopflix.core.statistics.model.vo.SimpleChart;
import dev.shopflix.core.statistics.service.ReportsStatisticsManager;
import dev.shopflix.core.statistics.util.DataDisplayUtil;
import dev.shopflix.core.statistics.util.StatisticsUtil;
import dev.shopflix.core.statistics.util.SyncopateUtil;
import dev.shopflix.core.system.model.dos.Regions;
import dev.shopflix.core.trade.order.model.enums.OrderStatusEnum;
import dev.shopflix.core.trade.order.model.enums.PayStatusEnum;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 商家中心，运营报告实现类
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
     * 销售统计 下单金额
     *
     * @param searchCriteria 统计参数，时间
     * @return MultipleChart 复杂图表数据
     */
    @Override
    public MultipleChart getSalesMoney(SearchCriteria searchCriteria) {

        String cycleType = searchCriteria.getCycleType();
        int year = searchCriteria.getYear();
        int month = null == searchCriteria.getMonth() ? 0 : searchCriteria.getMonth();
        SearchCriteria.checkDataParams(cycleType, year, month);
        try {
            // 参数集合
            List<Object> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.value());
            paramList.add(PayStatusEnum.PAY_YES.value());

            /*
             * start: 拼接sql语句并查询当前周期数据
             */
            // 时间范围sql
            String conditionSql = StatisticsUtil.getInstance().createSql(cycleType, year, month);
            String sql = "select SUM(order_price) as t_money, case " + conditionSql + " as time " +
                    " from es_sss_order_data o where order_status = ? AND pay_status = ? ";

            // 根据时间分组
            sql += " group by time ";
            List<Map<String, Object>> currentList = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());
            /*
             * end:查询当前周期数据结束
             */

            /*
             * start:拼接sql并查询上一周期数据
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
             * end:查询上一周期数据结束
             */

            // 判断按年查询还是按月查询，获取刻度长度
            int time;
            if (QueryDateType.YEAR.value().equals(cycleType)) {
                time = 12;
            } else {
                // 本月与上月对比，按天数多的为准，month在查询上月数据时减了1，所以要再加1
                int currentMonth = DataDisplayUtil.getMonthDayNum(month + 1, year);
                int lastMonth = DataDisplayUtil.getMonthDayNum(month, year);
                if (currentMonth > lastMonth) {
                    time = currentMonth;
                } else {
                    time = lastMonth;
                }
            }

            // 填充x轴刻度
            String[] xAxis = new String[time];
            // 数据名称
            String[] localName = new String[time];
            for (int i = 0; i < time; i++) {
                xAxis[i] = i + 1 + "";
                localName[i] = i + 1 + "";
            }

            String[] data = new String[time];
            String[] lastData = new String[time];

            // 向数组填充数据
            this.dataSet(currentList, data, time, "t_money");
            this.dataSet(lastList, lastData, time, "t_money");

            ChartSeries currentSeries = new ChartSeries((QueryDateType.YEAR.value().equals(cycleType) ? "本年" : "本月"), data, localName);
            ChartSeries lastSeries = new ChartSeries((QueryDateType.YEAR.value().equals(cycleType) ? "去年" : "上月"), lastData, localName);

            List<ChartSeries> series = new ArrayList<>();
            series.add(currentSeries);
            series.add(lastSeries);

            // 多数据复杂图表对象
            return new MultipleChart(series, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        }

    }


    /**
     * 销售统计 下单量
     *
     * @param searchCriteria 统计参数，时间
     * @return MultipleChart 复杂图表数据
     */
    @Override
    public MultipleChart getSalesNum(SearchCriteria searchCriteria) {

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        int month = null == searchCriteria.getMonth() ? 0 : searchCriteria.getMonth();
        SearchCriteria.checkDataParams(cycleType, year, month);
        SearchCriteria.checkDataParams(cycleType, year, month);

        try {

            // 参数集合
            List<String> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.value());
            paramList.add(PayStatusEnum.PAY_YES.value());

            /*
             * start:开始拼接sql并查询当前周期数据
             */
            String conditionSql = StatisticsUtil.getInstance().createSql(cycleType, year, month);
            String sql = "select count(0) as t_num, case " + conditionSql + " as time " +
                    " from es_sss_order_data o where order_status = ? AND pay_status = ?";
            sql += " group by time ";
            List<Map<String, Object>> currentList = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());
            /*
             * end:当前周期数据查询结束
             */

            /*
             * start:开始拼接sql并查询上一周期数据
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
             * end:上一周期数据查询结束
             */

            // x轴刻度长度
            int time;
            if (QueryDateType.YEAR.value().equals(cycleType)) {
                time = 12;
            } else {
                // 本月与上月对比，按天数多的为准，month在查询上月数据时减了1，所以要再加1
                int currentMonth = DataDisplayUtil.getMonthDayNum(month + 1, year);
                int lastMonth = DataDisplayUtil.getMonthDayNum(month, year);
                if (currentMonth > lastMonth) {
                    time = currentMonth;
                } else {
                    time = lastMonth;
                }
            }

            // x轴刻度
            String[] xAxis = new String[time];
            // 数据名称
            String[] localName = new String[time];
            for (int i = 0; i < time; i++) {
                xAxis[i] = i + 1 + "";
                localName[i] = xAxis[i];
            }

            String[] data = new String[time];
            String[] lastData = new String[time];

            // 填充data和lastData数组
            this.dataSet(currentList, data, time, "t_num");
            this.dataSet(lastList, lastData, time, "t_num");

            ChartSeries currentSeries = new ChartSeries((QueryDateType.YEAR.value().equals(cycleType) ? "本年" : "本月"), data, localName);
            ChartSeries lastSeries = new ChartSeries((QueryDateType.YEAR.value().equals(cycleType) ? "去年" : "上月"), lastData, localName);

            List<ChartSeries> series = new ArrayList<>();
            series.add(currentSeries);
            series.add(lastSeries);

            // 复杂图表数据
            return new MultipleChart(series, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        }
    }


    /**
     * 销售统计 分页数据
     *
     * @param searchCriteria 统计参数，时间
     * @param pageNo         查询页码
     * @param pageSize       分页数据长度
     * @return Page 分页数据
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

            // 添加订单状态（已完成），支付状态（已支付），时间，店铺id等参数
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
                // 获取当前时间戳，数据库中时间戳是Integer型的
                Integer timestamp = (Integer) map.get("create_time");
                map.replace("create_time", timestamp);

                // 获取订单状态，并将值改为文字，因为只查询已完成订单，所以直接填入已完成
                String status = "已完成";
                map.replace("order_status", status);

            }

            return new Page(page.getPageNo(), page.getDataTotal(), page.getPageSize(), list);
        } catch (BadSqlGrammarException e) {
            //某个年份的统计表不存在，则返回空数据
            if(e.getMessage().endsWith("doesn't exist")){
                return new Page(pageNo,0L,pageSize,new ArrayList());
            }
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        }
    }

    /**
     * 销售分析，数据小结
     *
     * @param searchCriteria 统计参数，时间
     * @return 查询时间内下单金额之和与下单量之和
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

            // 添加订单状态（已完成），支付状态（已支付），时间，店铺id等参数
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
            //某个年份的统计表不存在，则返回空数据
            return new HashMap();

        }
    }

    /**
     * 区域分析，地图数据
     *
     * @param searchCriteria 时间相关参数
     * @param type           获取的数据类型
     * @return MapChartData 地图图表数据
     */
    @Override
    public List regionsMap(SearchCriteria searchCriteria, String type) {

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();
        SearchCriteria.checkDataParams(cycleType, year, month);

        try {

            // 获取国内各省份
            List<Regions> provinceList = this.regionsClient.getRegionsChildren(0);

            // 参数集合，包括订单状态，支付状态，查询时间，店铺id
            List<Object> paramList = new ArrayList<>();

            // 查询出的数据不包含哪些订单状态
            paramList.add(OrderStatusEnum.COMPLETE.value());

            // 查询出的数据支付状态应是已支付
            paramList.add(PayStatusEnum.PAY_YES.value());

            // 获取查询时间的时间戳
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);
            long startTime = times[0];
            long endTime = times[1];
            paramList.add(startTime);
            paramList.add(endTime);

            // 区分出区域分析的三种数据，拼接不同的sql字符串，获取不同的字段名
            String[] needDataType = this.mapDataType(type);
            String sqlDifference = needDataType[0];
            String dataDifference = needDataType[1];


            // 获取某一时间段内下单的会员数及其所在省份id
            String sql = "SELECT " + sqlDifference + ", ship_province_id FROM es_sss_order_data " +
                    "WHERE order_status = ? AND pay_status = ? AND create_time > ? " +
                    " AND create_time < ? GROUP BY ship_province_id";

            // 获取所有地区的下单量的统计值
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
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        }
    }

    /**
     * 购买分析，客单价分布（价格区间内下单量统计）
     *
     * @param searchCriteria 时间相关参数
     * @param ranges         价格区间，只接受整数
     * @return SimpleChart 简单图表数据
     */
    @Override
    public SimpleChart orderDistribution(SearchCriteria searchCriteria, Integer[] ranges) {

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();

        SearchCriteria.checkDataParams(cycleType, year, month);

        try {

            // 价格区间升序排序
            Arrays.sort(ranges);

            // 参数集合
            List<Object> paramList = new ArrayList<>();

            // 价格区间sql
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

            // 获取时间戳
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);

            long startTime = times[0];
            long endTime = times[1];

            // 添加时间，订单状态（已完成），支付状态（已支付），店铺id等参数
            paramList.add(startTime);
            paramList.add(endTime);
            paramList.add(OrderStatusEnum.COMPLETE.value());
            paramList.add(PayStatusEnum.PAY_YES.value());
            String sql = "SELECT COUNT(o.sn) AS num, CASE " + intervalSql +
                    " AS distribution FROM es_sss_order_data o WHERE o.create_time > ? AND o.create_time < ? " +
                    "AND o.order_status = ? AND o.pay_status = ? GROUP BY distribution ";
            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());

            // x轴刻度，数据名称，数据
            String[] xAxis = new String[ranges.length];
            String[] localName = new String[ranges.length];
            String[] data = new String[ranges.length];

            /*
            x轴数组下标   刻度名称   价格区间数组下标   变量变化
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

            ChartSeries chartSeries = new ChartSeries("下单量", data, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);

        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        }

    }

    /**
     * 购买分析，购买时段分布
     *
     * @param searchCriteria 时间相关参数
     * @return SimpleChart 简单图表数据
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

            //转换时区为CST
            String sqlTimezoneConvert = "CONVERT(DATE_FORMAT(CONVERT_TZ(FROM_UNIXTIME(o.create_time), @@session.time_zone,'+8:00'), '%k'), SIGNED)";

            String sql = "SELECT count(o.sn) AS num, "+sqlTimezoneConvert+" AS hour_num" +
                    " FROM es_sss_order_data o WHERE order_status = ? AND pay_status = ?" +
                    "AND o.create_time >= ? AND o.create_time <= ? GROUP BY hour_num ORDER BY hour_num";

            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());

            // 小时数
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

            ChartSeries chartSeries = new ChartSeries("下单量", data, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        }
    }

    /**
     * 销售统计，填充数据
     *
     * @param list  数据库数据
     * @param data  x轴数据
     * @param time  x轴刻度长度
     * @param title 获取数据的类型
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
     * 区域分析，所需数据类型判断
     *
     * @param type 所需类型
     * @return String[] sql语句和作为Map的key值的字段名
     */
    private String[] mapDataType(String type) {

        // 区分出区域分析的三种数据，拼接不同的sql字符串，获取不同的字段名
        String sqlDifference = "";
        String dataDifference = "";

        // 1.下单会员数 2.下单金额 3.下单量
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
