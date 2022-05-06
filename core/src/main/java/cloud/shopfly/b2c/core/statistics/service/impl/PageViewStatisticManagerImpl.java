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

import cloud.shopfly.b2c.core.statistics.model.enums.QueryDateType;
import cloud.shopfly.b2c.core.statistics.model.vo.ChartSeries;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.PageViewStatisticManager;
import cloud.shopfly.b2c.core.statistics.util.StatisticsUtil;
import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 平台后台 流量分析
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0 2018年3月19日上午9:35:06
 */
@Service
public class PageViewStatisticManagerImpl implements PageViewStatisticManager {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    
    private DaoSupport daoSupport;


    /**
     * 统计商品访问量
     *
     * @param searchCriteria 时间参数
     * @return SimpleChart 简单图表数据
     */
    @Override
    public SimpleChart countGoods(SearchCriteria searchCriteria) {

        searchCriteria = new SearchCriteria(searchCriteria);
        // 获取参数，便于使用
        String type = searchCriteria.getCycleType();

        String sql = "select sum(vs_num) as num, goods_name from es_sss_goods_pv ";

        // 查询条件集合，和参数集合
        List<String> sqlList = new ArrayList<>();
        List<Integer> paramList = new ArrayList<>();

        if (type.equals(QueryDateType.YEAR.value())) {
            sqlList.add(" vs_year = ? ");
            paramList.add(searchCriteria.getYear());
        } else if (type.equals(QueryDateType.MONTH.value())) {
            sqlList.add(" vs_month = ? ");
            paramList.add(searchCriteria.getMonth());
        }
        // 按商品名分组，按流量排序，取前30
        sql += SqlUtil.sqlSplicing(sqlList) + " group by goods_id,goods_name order by num desc limit 30  ";

        List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());

        int dataLength = 30;

        // 获取x轴数据，包括商品名和访问量
        String[] data = new String[dataLength];
        String[] goodsName = new String[dataLength];
        // 获取x轴刻度
        String[] xAxis = new String[dataLength];
        // 赋值
        for (int i = 0; i < dataLength; i++) {
            if (null != list && i < list.size()) {
                Map map = list.get(i);
                data[i] = map.get("num").toString();
                goodsName[i] = map.get("goods_name").toString();
            } else {
                data[i] = "无";
                goodsName[i] = "无";
            }
            xAxis[i] = i + 1 + "";
        }

        ChartSeries series = new ChartSeries("访问量", data, goodsName);

        return new SimpleChart(series, xAxis, new String[0]);

    }
}
