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

import cloud.shopfly.b2c.core.statistics.model.vo.MultipleChart;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;
import java.util.Map;

/**
 * Business center, operation report management interface
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/5/11 10:10
 */
public interface ReportsStatisticsManager {

    /**
     * Sales statistics, order amount chart data
     *
     * @param searchCriteria Statistical parameters, time
     * @return MultipleChart Composite chart data
     */
    MultipleChart getSalesMoney(SearchCriteria searchCriteria);

    /**
     * Obtain sales statistics order volume chart data
     *
     * @param searchCriteria Statistical parameters, time
     * @return MultipleChart Composite chart data
     */
    MultipleChart getSalesNum(SearchCriteria searchCriteria);

    /**
     * Obtain paging data of sales analysis order amount
     *
     * @param searchCriteria Statistical parameters, time
     * @param pageNo         The query page
     * @param pageSize       Paging data length
     * @return Page Paging data
     */
    Page getSalesPage(SearchCriteria searchCriteria, int pageNo, int pageSize);

    /**
     * Obtain summary of sales analysis data
     *
     * @param searchCriteria Statistical parameters, time
     * @return Map The sum of the order amount and the order quantity within the query time
     */
    Map getSalesSummary(SearchCriteria searchCriteria);

    /**
     * Regional analysis
     *
     * @param searchCriteria Time dependent parameter
     * @param type           The type of data to get
     * @return List Map data
     */
    List regionsMap(SearchCriteria searchCriteria, String type);

    /**
     * Purchase analysis, customer unit price distribution
     *
     * @param searchCriteria Time dependent parameter
     * @param ranges         Price range. Only round numbers are accepted
     * @return SimpleChart Simple chart data
     */
    SimpleChart orderDistribution(SearchCriteria searchCriteria, Integer[] ranges);

    /**
     * Buy analysis, buy period analysis
     *
     * @param searchCriteria Time dependent parameter
     * @return SimpleChart Simple chart data
     */
    SimpleChart purchasePeriod(SearchCriteria searchCriteria);

}
