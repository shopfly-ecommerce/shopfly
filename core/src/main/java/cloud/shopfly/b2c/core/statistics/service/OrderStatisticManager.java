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
 * 订单相关统计
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/4/16 下午1:53
 */

public interface OrderStatisticManager {

    /**
     * 获取订单下单金额
     *
     * @param searchCriteria
     * @param orderStatus
     * @return
     */
    MultipleChart getOrderMoney(SearchCriteria searchCriteria, String orderStatus);

    /**
     * 获取订单下单金额
     *
     * @param searchCriteria 搜索参数
     * @param orderStatus    订单状态
     * @param pageNo         页码
     * @param pageSize       分页大小
     * @return
     */
    Page getOrderPage(SearchCriteria searchCriteria, String orderStatus, Integer pageNo, Integer pageSize);

    /**
     * 获取订单下单量
     *
     * @param searchCriteria 搜索参数
     * @param orderStatus    订单状态
     * @return
     */
    MultipleChart getOrderNum(SearchCriteria searchCriteria, String orderStatus);


    /**
     * 获取销售收入统计
     *
     * @param searchCriteria
     * @param pageNo         页码
     * @param pageSize       分页大小
     * @return
     */
    Page getSalesMoney(SearchCriteria searchCriteria, Integer pageNo, Integer pageSize);

    /**
     * 获取销售收入退款 统计
     *
     * @param searchCriteria
     * @param pageNo         页码
     * @param pageSize       分页大小
     * @return
     */
    Page getAfterSalesMoney(SearchCriteria searchCriteria, Integer pageNo, Integer pageSize);

    /**
     * 销售收入总览
     *
     * @param searchCriteria
     * @return
     */
    SalesTotal getSalesMoneyTotal(SearchCriteria searchCriteria);

    /**
     * 按区域分析下单会员量
     *
     * @param searchCriteria
     * @return
     */
    MapChartData getOrderRegionMember(SearchCriteria searchCriteria);

    /**
     * 按区域分析下单数
     *
     * @param searchCriteria
     * @return
     */
    MapChartData getOrderRegionNum(SearchCriteria searchCriteria);

    /**
     * 按区域分析下单金额
     *
     * @param searchCriteria
     * @return
     */
    MapChartData getOrderRegionMoney(SearchCriteria searchCriteria);

    /**
     * 获取区域分析表格
     *
     * @param searchCriteria
     * @return
     */
    Page getOrderRegionForm(SearchCriteria searchCriteria);

    /**
     * 客单价分布
     *
     * @param searchCriteria
     * @param prices
     * @return
     */
    SimpleChart getUnitPrice(SearchCriteria searchCriteria, Integer[] prices);

    /**
     * 购买频次分析
     *
     * @return
     */
    Page getUnitNum();

    /**
     * 购买时段分析
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getUnitTime(SearchCriteria searchCriteria);

    /**
     * 退款统计
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getReturnMoney(SearchCriteria searchCriteria);


}
