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
package cloud.shopfly.b2c.core.distribution.service;

import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;


/**
 * Distributor statistics
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/13 In the morning8:36
 */
public interface DistributionStatisticManager {


    /**
     * turnover
     *
     * @param circle
     * @param memberId
     * @param year
     * @param month
     * @return
     */
    SimpleChart getOrderMoney(String circle, Integer memberId, Integer year, Integer month);

    /**
     * profits
     *
     * @param circle
     * @param memberId
     * @param year
     * @param month
     * @return
     */
    SimpleChart getPushMoney(String circle, Integer memberId, Integer year, Integer month);

    /**
     * orders
     *
     * @param circle
     * @param memberId
     * @param year
     * @param month
     * @return
     */
    SimpleChart getOrderCount(String circle, Integer memberId, Integer year, Integer month);


}
