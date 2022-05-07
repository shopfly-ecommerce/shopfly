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
package cloud.shopfly.b2c.core.statistics.service;

import cloud.shopfly.b2c.core.statistics.model.vo.MapChartData;
import cloud.shopfly.b2c.core.statistics.model.vo.MultipleChart;
import cloud.shopfly.b2c.core.statistics.model.vo.SalesTotal;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Order related statistics
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/4/16 In the afternoon1:53
 */

public interface OrderStatisticManager {

    /**
     * Get the order order amount
     *
     * @param searchCriteria
     * @param orderStatus
     * @return
     */
    MultipleChart getOrderMoney(SearchCriteria searchCriteria, String orderStatus);

    /**
     * Get the order order amount
     *
     * @param searchCriteria The search parameters
     * @param orderStatus    Status
     * @param pageNo         The page number
     * @param pageSize       Page size
     * @return
     */
    Page getOrderPage(SearchCriteria searchCriteria, String orderStatus, Integer pageNo, Integer pageSize);

    /**
     * Get the order quantity
     *
     * @param searchCriteria The search parameters
     * @param orderStatus    Status
     * @return
     */
    MultipleChart getOrderNum(SearchCriteria searchCriteria, String orderStatus);


    /**
     * Obtain sales revenue statistics
     *
     * @param searchCriteria
     * @param pageNo         The page number
     * @param pageSize       Page size
     * @return
     */
    Page getSalesMoney(SearchCriteria searchCriteria, Integer pageNo, Integer pageSize);

    /**
     * Obtain sales revenue refund statistics
     *
     * @param searchCriteria
     * @param pageNo         The page number
     * @param pageSize       Page size
     * @return
     */
    Page getAfterSalesMoney(SearchCriteria searchCriteria, Integer pageNo, Integer pageSize);

    /**
     * An overview of sales revenue
     *
     * @param searchCriteria
     * @return
     */
    SalesTotal getSalesMoneyTotal(SearchCriteria searchCriteria);

    /**
     * Analyze order member quantity by region
     *
     * @param searchCriteria
     * @return
     */
    MapChartData getOrderRegionMember(SearchCriteria searchCriteria);

    /**
     * Analyze the singular by region
     *
     * @param searchCriteria
     * @return
     */
    MapChartData getOrderRegionNum(SearchCriteria searchCriteria);

    /**
     * Analyze order amount by region
     *
     * @param searchCriteria
     * @return
     */
    MapChartData getOrderRegionMoney(SearchCriteria searchCriteria);

    /**
     * Get the region analysis table
     *
     * @param searchCriteria
     * @return
     */
    Page getOrderRegionForm(SearchCriteria searchCriteria);

    /**
     * Customer unit price distribution
     *
     * @param searchCriteria
     * @param prices
     * @return
     */
    SimpleChart getUnitPrice(SearchCriteria searchCriteria, Integer[] prices);

    /**
     * Purchase frequency analysis
     *
     * @return
     */
    Page getUnitNum();

    /**
     * Purchase period analysis
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getUnitTime(SearchCriteria searchCriteria);

    /**
     * Refund statistics
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getReturnMoney(SearchCriteria searchCriteria);


}
