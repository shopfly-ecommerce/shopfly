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
 * 商家中心，运营报告管理接口
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/5/11 10:10
 */
public interface ReportsStatisticsManager {

    /**
     * 销量统计，下单金额图表数据
     *
     * @param searchCriteria 统计参数，时间
     * @return MultipleChart 复合图表数据
     */
    MultipleChart getSalesMoney(SearchCriteria searchCriteria);

    /**
     * 获取销售统计  下单量图表数据
     *
     * @param searchCriteria 统计参数，时间
     * @return MultipleChart 复合图表数据
     */
    MultipleChart getSalesNum(SearchCriteria searchCriteria);

    /**
     * 获取销售分析  下单金额 分页数据
     *
     * @param searchCriteria 统计参数，时间
     * @param pageNo         查询页码
     * @param pageSize       分页数据长度
     * @return Page 分页数据
     */
    Page getSalesPage(SearchCriteria searchCriteria, int pageNo, int pageSize);

    /**
     * 获取销售分析  数据小结
     *
     * @param searchCriteria 统计参数，时间
     * @return Map 查询时间内下单金额之和与下单量之和
     */
    Map getSalesSummary(SearchCriteria searchCriteria);

    /**
     * 区域分析
     *
     * @param searchCriteria 时间相关参数
     * @param type           获取的数据类型
     * @return List 地图数据
     */
    List regionsMap(SearchCriteria searchCriteria, String type);

    /**
     * 购买分析，客单价分布
     *
     * @param searchCriteria 时间相关参数
     * @param ranges         价格区间，只接受整数
     * @return SimpleChart 简单图表数据
     */
    SimpleChart orderDistribution(SearchCriteria searchCriteria, Integer[] ranges);

    /**
     * 购买分析，购买时段分析
     *
     * @param searchCriteria 时间相关参数
     * @return SimpleChart 简单图表数据
     */
    SimpleChart purchasePeriod(SearchCriteria searchCriteria);

}
