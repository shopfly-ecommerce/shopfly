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


import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Background Industry analysis
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018/4/16 In the afternoon1:53
 */
public interface IndustryStatisticManager {


    /**
     * Order quantity is counted by classification
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getOrderQuantity(SearchCriteria searchCriteria);

    /**
     * Count the quantity of goods ordered by category
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getGoodsNum(SearchCriteria searchCriteria);

    /**
     * According to the classification statistics order amount
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getOrderMoney(SearchCriteria searchCriteria);

    /**
     * Summarize the overview
     *
     * @param searchCriteria
     * @return
     */
    Page getGeneralOverview(SearchCriteria searchCriteria);

}
