/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.distribution.service.impl;

import cloud.shopfly.b2c.core.statistics.model.enums.QueryDateType;
import cloud.shopfly.b2c.core.statistics.model.vo.ChartSeries;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.util.DataDisplayUtil;
import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.core.distribution.service.DistributionStatisticManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * DistributionStatisticManagerImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-06-13 上午8:37
 */
@Service
public class DistributionStatisticManagerImpl implements DistributionStatisticManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public SimpleChart getOrderMoney(String circle, Integer memberId, Integer year, Integer month) {


        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setMonth(month);
        searchCriteria.setYear(year);
        searchCriteria.setCycleType(circle);

        searchCriteria = new SearchCriteria(searchCriteria);


        long[] timesTramp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
        String sql = "select (SUM(order_price)-SUM(return_money)) order_price,FROM_UNIXTIME(create_time, ?) date from es_distribution_order where create_time > ? and create_time < ? AND (member_id_lv1 = ?||member_id_lv2 = ?) group by FROM_UNIXTIME(create_time, ?)";

        Integer resultSize = DataDisplayUtil.getResultSize(searchCriteria);

        String circleWhere = "";
        if (Objects.equals(searchCriteria.getCycleType(), QueryDateType.YEAR.name())) {
            circleWhere = "%m";
        } else {
            circleWhere = "%d";
        }
        List<Map<String, Object>> list = this.daoSupport.queryForList(sql.toString(), circleWhere, timesTramp[0], timesTramp[1], memberId, memberId,circleWhere);

        String[] xAxis = new String[resultSize],
                data = new String[resultSize];

        for (int i = 0; i < resultSize; i++) {

            data[i] = 0 + "";
            for (Map<String, Object> map : list) {
                try {
                    if (Integer.parseInt(map.get("date").toString()) == (i + 1)) {
                        data[i] = map.get("order_price").toString();
                    }
                } catch (NullPointerException e) {
                }
            }
            xAxis[i] = i + 1 + "";
        }

        ChartSeries chartSeries = new ChartSeries("订单金额统计", data, new String[0]);

        SimpleChart simpleChart = new SimpleChart(chartSeries, xAxis, new String[0]);

        return simpleChart;
    }

    @Override
    public SimpleChart getPushMoney(String circle, Integer memberId, Integer year, Integer month) {


        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setMonth(month);
        searchCriteria.setYear(year);
        searchCriteria.setCycleType(circle);

        searchCriteria = new SearchCriteria(searchCriteria);


        long[] timesTramp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
        String sql = "select (SUM(grade1_rebate)-SUM(grade1_sellback_price)) grade_rebate,FROM_UNIXTIME(create_time, ?) date from es_distribution_order where create_time > ? and create_time < ? AND (member_id_lv1 = ?) group by grade1_rebate,create_time";
        String sql2 = "select (SUM(grade2_rebate)-SUM(grade2_sellback_price)) grade_rebate,FROM_UNIXTIME(create_time, ?) date from es_distribution_order where create_time > ? and create_time < ? AND (member_id_lv2 = ?) group by grade1_rebate,create_time";

        Integer resultSize = DataDisplayUtil.getResultSize(searchCriteria);

        String circleWhere = "";
        if (Objects.equals(searchCriteria.getCycleType(), QueryDateType.YEAR.name())) {
            circleWhere = "%m";
        } else {
            circleWhere = "%d";
        }
        List<Map<String, Object>> list = this.daoSupport.queryForList(sql.toString(), circleWhere, timesTramp[0], timesTramp[1], memberId);
        List<Map<String, Object>> list2 = this.daoSupport.queryForList(sql2.toString(), circleWhere, timesTramp[0], timesTramp[1], memberId);

        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = 0; i < resultSize; i++) {
            double finalRebate = 0;
            for (Map<String, Object> map : list) {
                try {
                    if (Integer.parseInt(map.get("date").toString()) == (i + 1)) {
                        finalRebate = CurrencyUtil.add(finalRebate, Double.parseDouble(map.get("grade_rebate").toString()));
                    }
                } catch (NullPointerException e) {
                }
            }
            for (Map<String, Object> map : list2) {
                try {
                    if (Integer.parseInt(map.get("date").toString()) == (i + 1)) {
                        finalRebate = CurrencyUtil.add(finalRebate, Double.parseDouble(map.get("grade_rebate").toString()));
                    }
                } catch (NullPointerException e) {
                }
            }
            Map<String, Object> map = new HashMap<>(16);
            map.put("date", i + 1);
            map.put("grade_rebate", finalRebate);
            result.add(map);
        }

        String[] xAxis = new String[resultSize],
                data = new String[resultSize];

        for (int i = 0; i < resultSize; i++) {

            data[i] = 0 + "";
            for (Map<String, Object> map : result) {
                if (Integer.parseInt(map.get("date").toString()) == (i + 1)) {
                    data[i] = map.get("grade_rebate").toString();
                }
            }
            xAxis[i] = i + 1 + "";
        }

        ChartSeries chartSeries = new ChartSeries("订单提成统计", data, new String[0]);

        SimpleChart simpleChart = new SimpleChart(chartSeries, xAxis, new String[0]);
        return simpleChart;
    }

    @Override
    public SimpleChart getOrderCount(String circle, Integer memberId, Integer year, Integer month) {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setMonth(month);
        searchCriteria.setYear(year);
        searchCriteria.setCycleType(circle);

        searchCriteria = new SearchCriteria(searchCriteria);


        long[] timesTramp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
        String sql = "select count(0) count,FROM_UNIXTIME(create_time, ?) date from es_distribution_order where create_time > ? and create_time < ? AND (member_id_lv1 = ?||member_id_lv2 = ?) group by FROM_UNIXTIME(create_time, ?) ";

        Integer resultSize = DataDisplayUtil.getResultSize(searchCriteria);

        String circleWhere = "";
        if (Objects.equals(searchCriteria.getCycleType(), QueryDateType.YEAR.name())) {
            circleWhere = "%m";
        } else {
            circleWhere = "%d";
        }
        List<Map<String, Object>> list = this.daoSupport.queryForList(sql.toString(), circleWhere, timesTramp[0], timesTramp[1], memberId, memberId,circleWhere);

        String[] xAxis = new String[resultSize],
                data = new String[resultSize];

        for (int i = 0; i < resultSize; i++) {

            data[i] = 0 + "";
            for (Map<String, Object> map : list) {
                try {
                    if (Integer.parseInt(map.get("date").toString()) == (i + 1)) {
                        data[i] = map.get("count").toString();
                    }
                } catch (NullPointerException e) {
                }
            }
            xAxis[i] = i + 1 + "";
        }

        ChartSeries chartSeries = new ChartSeries("订单数量统计", data, new String[0]);

        SimpleChart simpleChart = new SimpleChart(chartSeries, xAxis, new String[0]);

        return simpleChart;
    }

}
