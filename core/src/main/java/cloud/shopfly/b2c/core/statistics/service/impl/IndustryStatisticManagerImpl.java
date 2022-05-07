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
import cloud.shopfly.b2c.core.statistics.service.IndustryStatisticManager;
import cloud.shopfly.b2c.core.statistics.util.DataDisplayUtil;
import cloud.shopfly.b2c.core.statistics.util.StatisticsUtil;
import cloud.shopfly.b2c.core.statistics.util.SyncopateUtil;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PayStatusEnum;
import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.core.goods.model.vo.CategoryVO;
import cloud.shopfly.b2c.core.goods.service.CategoryManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Industry analysis implementation class
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/4/28 In the afternoon5:11
 */
@Service
public class IndustryStatisticManagerImpl implements IndustryStatisticManager {

    @Autowired
    private CategoryManager categoryManager;

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    
    private DaoSupport daoSupport;


    @Override
    public SimpleChart getOrderQuantity(SearchCriteria searchCriteria) {

        searchCriteria = new SearchCriteria(searchCriteria);


        try {
            List<CategoryVO> categoryList = this.categoryManager.listAllChildren(0);

            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
            /*
             * parameter
             */
            List<Object> params = new ArrayList<>();

            /*
             * The querysql
             */
            StringBuffer sql = new StringBuffer();

            sql.append("SELECT COUNT(oi.`order_sn`) AS order_num,oi.`industry_id` FROM es_sss_order_goods_data oi "
                    + " LEFT JOIN es_sss_order_data o ON oi.`order_sn` = o.`sn` "
                    + " WHERE oi.create_time >= ? AND oi.create_time <= ? ");

            params.add(timestamp[0]);
            params.add(timestamp[1]);
            sql.append(" group by industry_id ");

            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(),sql.toString(),params.toArray());

            String[] data = new String[categoryList.size()];
            String[] name = new String[categoryList.size()];
            int index = 0;

            for (CategoryVO category : categoryList) {
                name[index] = category.getName();
                for (Map<String, Object> map : list) {
                    if (category.getCategoryId().toString().equals(map.get("industry_id").toString())) {
                        data[index] = map.get("order_num").toString();
                    }
                }

                if (StringUtil.isEmpty(data[index])) {
                    data[index] = "0";
                }
                index++;
            }

            ChartSeries chartSeries = new ChartSeries("Industry order statistics", data, name);
            return new SimpleChart(chartSeries, new String[0], name);

        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public SimpleChart getGoodsNum(SearchCriteria searchCriteria) {

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

            List<CategoryVO> categoryList = this.categoryManager.listAllChildren(0);

            sql.append("SELECT SUM(oi.goods_num) AS goods_num,oi.`industry_id` FROM es_sss_order_goods_data oi "
                    + " LEFT JOIN es_sss_order_data o ON oi.`order_sn` = o.`sn` "
                    + " WHERE oi.create_time >= ? AND oi.create_time <= ?");

            params.add(timestamp[0]);
            params.add(timestamp[1]);

            sql.append(" GROUP BY oi.`industry_id`");

            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(),sql.toString(),params.toArray());

            String[] data = new String[categoryList.size()];
            String[] name = new String[categoryList.size()];
            int index = 0;
            for (CategoryVO category : categoryList) {
                name[index] = category.getName();
                for (Map<String, Object> map : list) {
                    if (map.get("industry_id").toString().equals(category.getCategoryId().toString())) {
                        data[index] = map.get("goods_num").toString();
                    }
                }
                if (StringUtil.isEmpty(data[index])) {
                    data[index] = "0";
                }
                index++;
            }

            ChartSeries chartSeries = new ChartSeries("Number of goods ordered by industry", data, name);
            return new SimpleChart(chartSeries, new String[0], name);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public SimpleChart getOrderMoney(SearchCriteria searchCriteria) {

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
            List<CategoryVO> categoryList = this.categoryManager.listAllChildren(0);

            sql.append("SELECT SUM(oi.`price`) AS order_money,oi.`industry_id` FROM es_sss_order_goods_data oi "
                    + " LEFT JOIN es_sss_order_data o ON oi.`order_sn` = o.`sn` "
                    + " WHERE oi.create_time >= ? AND oi.create_time <= ? ");

            params.add(timestamp[0]);
            params.add(timestamp[1]);

            sql.append(" GROUP BY oi.`industry_id`");

            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(),sql.toString(),params.toArray());

            String[] data = new String[categoryList.size()];
            String[] name = new String[categoryList.size()];
            int index = 0;
            for (CategoryVO category : categoryList) {
                name[index] = category.getName();
                for (Map<String, Object> map : list) {
                    if (map.get("industry_id").toString().equals(category.getCategoryId().toString())) {
                        data[index] = map.get("order_money").toString();
                    }
                }
                if (StringUtil.isEmpty(data[index])) {
                    data[index] = "0";
                }
                index++;
            }
            ChartSeries chartSeries = new ChartSeries("Industry order amount", data, name);
            return new SimpleChart(chartSeries, new String[0], name);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public Page getGeneralOverview(SearchCriteria searchCriteria) {

        searchCriteria = new SearchCriteria(searchCriteria);

        try {
            List<CategoryVO> categoryList = this.categoryManager.listAllChildren(searchCriteria.getCategoryId());
            List<Map<String, Object>> result = new ArrayList<>();

            for (CategoryVO category : categoryList) {
                Map<String, Object> m = new HashMap<>(16);
                m.put("category_name", category.getName());
                m.put("industry_id", category.getCategoryId());
                // The average price
                List<Object> avgParams = new ArrayList<>();
                StringBuffer avgSql = new StringBuffer("select AVG(gd.price) as avg from es_sss_goods_data gd where category_path like ? ");
                avgParams.add("%|" + category.getCategoryId() + "|%");

                m.put("avg_price", StringUtil.toDouble(this.daoSupport.queryForMap(avgSql.toString(), avgParams.toArray()).get("avg"), false));


                // The number of goods sold
                List<Object> salesGoodsParams = new ArrayList<>();
                StringBuffer salesGoodsSql = new StringBuffer("select count(0) from (select oi.goods_id from es_sss_goods_data g inner join " +
                        " es_sss_order_goods_data oi on g.goods_id = oi.goods_id " +
                        " left join es_sss_order_data od on oi.order_sn = od.sn where oi.category_path like ? ");
                salesGoodsParams.add("%|" + category.getCategoryId() + "|%");
                salesGoodsSql.append(" AND ((od.order_status = '" + OrderStatusEnum.COMPLETE.name() + "')	OR ( od.pay_status = '" + PayStatusEnum.PAY_YES.name()
                         + "'))" + " GROUP BY oi.goods_id) tt");

                Integer soldGoodsNum = this.daoSupport.queryForInt(SyncopateUtil.handleSql(searchCriteria.getYear(),salesGoodsSql.toString()), salesGoodsParams.toArray());

                m.put("sold_goods_num", soldGoodsNum);


                // The overview
                List<Object> totalParams = new ArrayList<>();
                StringBuffer totalSql = new StringBuffer("select count(0) from es_sss_goods_data gd where gd.category_path  like ? ");
                totalParams.add("%|" + category.getCategoryId() + "|%");
                Integer totalGoodsNum = this.daoSupport.queryForInt(totalSql.toString(), totalParams.toArray());
                m.put("goods_total_num", totalGoodsNum);
                m.put("nosales_goods_num", totalGoodsNum - soldGoodsNum);
                // No sales
                List<Object> soldParams = new ArrayList<>();
                StringBuffer soldSql = new StringBuffer("select count(oi.goods_num)as num,sum(oi.goods_num*oi.price) as price from es_sss_order_goods_data oi "
                        + " inner join es_sss_order_data od on oi.order_sn = od.sn "
                        + " left join es_sss_goods_data gd on oi.goods_id = gd.goods_id where gd.category_path like ? ");
                soldParams.add("%|" + category.getCategoryId() + "|%");
                Map map = this.daoSupport.queryForMap(SyncopateUtil.handleSql(searchCriteria.getYear(),soldSql.toString()), soldParams.toArray());
                m.put("sold_num", map.get("num"));
                try {
                    if (!StringUtil.isEmpty(map.get("price").toString())) {
                        m.put("sales_money", map.get("price"));
                    }
                } catch (Exception e) {
                    m.put("sales_money", 0);
                }
                result.add(m);
            }

            return new Page(1, (long) result.size(), 10, result);
        } catch(BadSqlGrammarException e) {
            // If the statistics table for a certain year does not exist, null data is returned
            if (e.getMessage().endsWith("doesn't exist")) {
                return new Page(1, 0L, 10, new ArrayList());
            }
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

}
