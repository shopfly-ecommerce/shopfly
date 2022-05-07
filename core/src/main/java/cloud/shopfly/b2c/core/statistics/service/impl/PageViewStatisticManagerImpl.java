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
 * Platform background traffic analysis
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0 2018years3month19The morning of9:35:06
 */
@Service
public class PageViewStatisticManagerImpl implements PageViewStatisticManager {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    
    private DaoSupport daoSupport;


    /**
     * Statistics of Product visits
     *
     * @param searchCriteria The time parameter
     * @return SimpleChart Simple chart data
     */
    @Override
    public SimpleChart countGoods(SearchCriteria searchCriteria) {

        searchCriteria = new SearchCriteria(searchCriteria);
        // Gets parameters for easy use
        String type = searchCriteria.getCycleType();

        String sql = "select sum(vs_num) as num, goods_name from es_sss_goods_pv ";

        // Query a set of conditions, and a set of parameters
        List<String> sqlList = new ArrayList<>();
        List<Integer> paramList = new ArrayList<>();

        if (type.equals(QueryDateType.YEAR.value())) {
            sqlList.add(" vs_year = ? ");
            paramList.add(searchCriteria.getYear());
        } else if (type.equals(QueryDateType.MONTH.value())) {
            sqlList.add(" vs_month = ? ");
            paramList.add(searchCriteria.getMonth());
        }
        // Group by commodity name, sort by traffic, take the top 30
        sql += SqlUtil.sqlSplicing(sqlList) + " group by goods_id,goods_name order by num desc limit 30  ";

        List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), sql, paramList.toArray());

        int dataLength = 30;

        // Get X-axis data, including product names and visits
        String[] data = new String[dataLength];
        String[] goodsName = new String[dataLength];
        // Get the X-axis scale
        String[] xAxis = new String[dataLength];
        // The assignment
        for (int i = 0; i < dataLength; i++) {
            if (null != list && i < list.size()) {
                Map map = list.get(i);
                data[i] = map.get("num").toString();
                goodsName[i] = map.get("goods_name").toString();
            } else {
                data[i] = "There is no";
                goodsName[i] = "There is no";
            }
            xAxis[i] = i + 1 + "";
        }

        ChartSeries series = new ChartSeries("traffic", data, goodsName);

        return new SimpleChart(series, xAxis, new String[0]);

    }
}
