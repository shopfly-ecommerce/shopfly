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
package cloud.shopfly.b2c.core.statistics.util;

import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.framework.util.StringUtil;

import java.util.List;

/**
 * chart sqltool
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-04-27 In the afternoon3:14
 */
public class ChartSqlUtil {


    /**
     * The price parameterssql To obtain
     *
     * @param prices The price parameters
     */
    public static void appendPriceSql(Integer[] prices, StringBuffer stringBuffer, List<Object> params) {
        for (int i = 1; i < prices.length; i++) {
            stringBuffer.append(" when o.order_price >= ? AND o.order_price <=? then ?");
            params.add(prices[i - 1]);
            params.add(prices[i]);
            params.add(i);
        }
        stringBuffer.append(" else 0 end").toString();
    }

    /**
     * The price parameterssql groupTo obtain
     *
     * @param prices The price parameters
     * @return sql
     */
    public static String priceGroupSql(Integer[] prices) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" group by case ");
        for (int i = 1; i < prices.length; i++) {
            stringBuffer.append(" when o.order_price >= ? AND  o.order_price <=? then ?");
        }
        return stringBuffer.append(" else 0 end").toString();
    }


    /**
     * Order item classification parameterssqlTo obtain
     *
     * @return
     */
    public static String categoryOrderGoodsSql() {
        return " AND oi.category_path like (?)";
    }


    /**
     * Order item classification parameterssqlJoining together
     *
     * @param searchCriteria The search parameters
     * @param stringBuffer   sql
     * @param params         sqlparameter
     */
    public static void appendOrderGoodsCategorySql(SearchCriteria searchCriteria, StringBuffer stringBuffer, List<Object> params) {
        if (searchCriteria.getCategoryId() != 0) {
            stringBuffer.append(" AND oi.category_path like (?)");
            params.add(searchCriteria.getCategoryId());
        }
    }

    /**
     * Order status conditionsqlJoining together
     *
     * @param orderStatus  Status
     * @param stringBuffer sql
     * @param params       sqlparameter
     */
    public static void appendOrderStatusSql(String orderStatus, StringBuffer stringBuffer, List<Object> params) {
        if (!StringUtil.isEmpty(orderStatus)) {
            stringBuffer.append(" AND o.order_status = ? ");
            params.add(orderStatus);
        }
    }

    /**
     * like parameter
     *
     * @param like parameter
     * @return
     */
    public static String categoryLikeParams(Integer like) {
        return "%|" + like + "|%";
    }


}
