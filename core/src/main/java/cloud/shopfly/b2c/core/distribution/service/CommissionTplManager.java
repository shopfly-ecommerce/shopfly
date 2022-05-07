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

import cloud.shopfly.b2c.core.distribution.model.dos.CommissionTpl;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Royalty templatemanagerinterface
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 In the morning10:52
 */

public interface CommissionTplManager {


    /**
     * Get a template used by a member
     *
     * @param memberId membersid
     * @return CommissionTplDO
     */
    CommissionTpl getCommissionTplByMember(int memberId);

    /**
     * page
     *
     * @param page     The page number
     * @param pageSize Page size
     * @return page
     */
    Page page(int page, int pageSize);

    /**
     * throughidTo obtainCommissionTpl
     *
     * @param id
     * @return CommissionTpl
     */
    CommissionTpl getModel(int id);


    /**
     * Add acommissionTpl
     *
     * @param commissionTpl template
     * @return CommissionTplDO
     */
    CommissionTpl add(CommissionTpl commissionTpl);


    /**
     * To modify aCommissionTpl
     *
     * @param commissionTpl
     * @return CommissionTplDO
     */
    CommissionTpl edit(CommissionTpl commissionTpl);

    /**
     * To delete aCommissionTpl
     *
     * @param tplId
     */
    void delete(Integer tplId);

    /**
     * Get a default template
     *
     * @return DO
     */
    CommissionTpl getDefaultCommission();
}
