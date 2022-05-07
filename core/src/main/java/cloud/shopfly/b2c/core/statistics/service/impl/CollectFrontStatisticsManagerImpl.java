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

import cloud.shopfly.b2c.core.statistics.model.vo.ChartSeries;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.CollectFrontStatisticsManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Business center, commodity collection statistics implementation class
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018years4month20On the afternoon4:35:26
 */
@Service
public class CollectFrontStatisticsManagerImpl implements CollectFrontStatisticsManager {

    @Autowired
    
    private DaoSupport daoSupport;

    /**
     * Commodity collection quantity statistics
     *
     * @return simpleChart Simple chart data
     */
    @Override
    public SimpleChart getChart() {

        // Select * from es_SSS_GOOds_data
        String sql = " SELECT goods_id,favorite_num,goods_name FROM es_sss_goods_data ORDER BY favorite_num DESC LIMIT 50 ";

        List<Map<String, Object>> list = this.daoSupport.queryForList(sql);

        // Collection quantity array, corresponding to chart data
        String[] data = new String[list.size()];

        // An array of commodity names that correspond to chart data names
        String[] localName = new String[list.size()];

        // The X-axis scale, starting at 1, is based on the amount of data, and 0 if there is no data
        String[] xAxis = new String[list.size()];

        // If there is data, add it to the array
        if (!list.isEmpty()) {
            int i = 0;
            for (Map<String, Object> map : list) {
                data[i] = map.get("favorite_num").toString();
                localName[i] = map.get("goods_name").toString();
                xAxis[i] = i + 1 + "";
                i++;
            }
        }

        ChartSeries series = new ChartSeries("Collect the number", data, localName);

        // Data, x scale, y scale
        return new SimpleChart(series, xAxis, new String[0]);
    }

    /**
     * Commodity collection statistics table
     *
     * @param pageNoThe page number,
     * @param pageSize, page data volume
     * @return Page Paging data
     */
    @Override
    public Page getPage(Integer pageNo, Integer pageSize) {

        // Gets the item name, quantity of collection, and price of the item being sold, sorted by quantity of collection
        String sql = "select goods_id,goods_name,favorite_num,price from es_sss_goods_data where  market_enable = 1 order by favorite_num desc";

        return this.daoSupport.queryForPage(sql, pageNo, pageSize);
    }

}
