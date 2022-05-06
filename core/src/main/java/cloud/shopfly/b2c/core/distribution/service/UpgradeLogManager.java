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
 * 升级日志管理类
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 下午12:56
 */

public interface UpgradeLogManager {

    /**
     * 搜索
     *
     * @param page       分页
     * @param pageSize   分页每页数量
     * @param memberName 会员名
     * @return Page
     */
    Page<UpgradeLogDO> page(int page, int pageSize, String memberName);

    /**
     * 新增一个模板升级日志
     *
     * @param upgradeLog
     * @return do
     */
    UpgradeLogDO add(UpgradeLogDO upgradeLog);

    /**
     * 新增日志,一定要再修改之前【因为旧的模板id是根据用户id现查的】
     *
     * @param memberId        会员id
     * @param newTplId        新的模板id
     * @param upgradeTypeEnum 模版操作类型
     */
    void addUpgradeLog(int memberId, int newTplId, UpgradeTypeEnum upgradeTypeEnum);
}
