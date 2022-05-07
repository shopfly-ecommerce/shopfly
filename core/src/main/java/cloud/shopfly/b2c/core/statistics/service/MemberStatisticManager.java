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
 * Membership statistics
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/4/16 In the afternoon1:54
 */

public interface MemberStatisticManager {

    /**
     * Get the number of new members
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getIncreaseMember(SearchCriteria searchCriteria);

    /**
     * Obtain the number of new members form
     *
     * @param searchCriteria
     * @return
     */
    Page getIncreaseMemberPage(SearchCriteria searchCriteria);

    /**
     * Get member orders
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getMemberOrderQuantity(SearchCriteria searchCriteria);

    /**
     * Obtain member order form
     *
     * @param searchCriteria
     * @return
     */
    Page getMemberOrderQuantityPage(SearchCriteria searchCriteria);

    /**
     * Obtain order quantity form
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getMemberGoodsNum(SearchCriteria searchCriteria);

    /**
     * Obtain order quantity form
     *
     * @param searchCriteria
     * @return
     */
    Page getMemberGoodsNumPage(SearchCriteria searchCriteria);

    /**
     * Obtain the total order amount
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getMemberMoney(SearchCriteria searchCriteria);

    /**
     * Obtain the order total amount form
     *
     * @param searchCriteria
     * @return
     */
    Page getMemberMoneyPage(SearchCriteria searchCriteria);


}
