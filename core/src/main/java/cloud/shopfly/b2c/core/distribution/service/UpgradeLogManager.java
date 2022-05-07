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

import cloud.shopfly.b2c.core.distribution.model.dos.UpgradeLogDO;
import cloud.shopfly.b2c.core.distribution.model.enums.UpgradeTypeEnum;
import cloud.shopfly.b2c.framework.database.Page;


/**
 * Upgrade the log management class
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 In the afternoon12:56
 */

public interface UpgradeLogManager {

    /**
     * search
     *
     * @param page       paging
     * @param pageSize   Number of pages per page
     * @param memberName Member name
     * @return Page
     */
    Page<UpgradeLogDO> page(int page, int pageSize, String memberName);

    /**
     * A template upgrade log is added
     *
     * @param upgradeLog
     * @return do
     */
    UpgradeLogDO add(UpgradeLogDO upgradeLog);

    /**
     * The new log,Be sure to revise again before【Because of the old templateidIs based on the useridNow check the】
     *
     * @param memberId        membersid
     * @param newTplId        A new templateid
     * @param upgradeTypeEnum Template operation type
     */
    void addUpgradeLog(int memberId, int newTplId, UpgradeTypeEnum upgradeTypeEnum);
}
