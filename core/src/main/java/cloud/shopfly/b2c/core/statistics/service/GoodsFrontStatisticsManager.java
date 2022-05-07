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

import java.util.List;

/**
 * Product analysismanagerinterface
 *
 * @author xin
 * @version v1.0, 2015-12-29
 * @since v1.0
 */
public interface GoodsFrontStatisticsManager {

    /**
     * Obtain product details
     *
     * @param pageNo    The current page number
     * @param pageSize  Data volume per page
     * @param catId     Categoryid
     * @param goodsName Name
     * @return Page Paging object
     */
    Page getGoodsDetail(Integer pageNo, Integer pageSize, Integer catId, String goodsName);

    /**
     * Get commodity price data, paging data
     *
     * @param sections       intervalList  format：0 100 200
     * @param searchCriteria Time and shopidRelated parameters
     * @return SimpleChartSimple chart data
     */
    SimpleChart getGoodsPriceSales(List<Integer> sections, SearchCriteria searchCriteria);

    /**
     * Get the order amount ranking of goods30, paging data
     *
     * @param searchCriteria Time dependent parameter
     * @param topNum         topThe number
     * @return Page Paging object
     */
    Page getGoodsOrderPriceTopPage(int topNum, SearchCriteria searchCriteria);

    /**
     * Gets the order quantity ranking30, paging data
     *
     * @param searchCriteria Time dependent parameter
     * @param topNum         The ranking defaults to30
     * @return Page Paging object
     */
    Page getGoodsNumTopPage(int topNum, SearchCriteria searchCriteria);

    /**
     * Get the order amount ranking of goods30, chart data
     *
     * @param topNum         topThe number
     * @param searchCriteria Time dependent parameter
     * @return SimpleChart Simple chart data
     */
    SimpleChart getGoodsOrderPriceTop(Integer topNum, SearchCriteria searchCriteria);

    /**
     * Get top order quantity ranking of goods30, chart data
     *
     * @param topNum         topThe number
     * @param searchCriteria Time dependent parameter
     * @return SimpleChart Simple chart data
     */
    SimpleChart getGoodsNumTop(Integer topNum, SearchCriteria searchCriteria);
}
