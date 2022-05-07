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
package cloud.shopfly.b2c.core.distribution.service.impl;

import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.distribution.model.dos.CommissionTpl;
import cloud.shopfly.b2c.core.distribution.model.dos.UpgradeLogDO;
import cloud.shopfly.b2c.core.distribution.model.enums.UpgradeTypeEnum;
import cloud.shopfly.b2c.core.distribution.service.CommissionTplManager;
import cloud.shopfly.b2c.core.distribution.service.DistributionManager;
import cloud.shopfly.b2c.core.distribution.service.UpgradeLogManager;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Upgrade Log Implementation
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 In the afternoon12:58
 */

@Component
public class UpgradeLogManagerImpl implements UpgradeLogManager {

    @Autowired
    
    private DaoSupport daoSupport;
    @Autowired
    private DistributionManager distributionManager;
    @Autowired
    private CommissionTplManager commissionTplManager;

    @Autowired
    private MemberClient memberClient;

    @Override
    public Page page(int page, int pageSize, String memberName) {
        String sql = "SELECT * FROM es_upgrade_log";

        List<String> params = new ArrayList<>();
        // Only the search name is passed in
        if (!StringUtil.isEmpty(memberName)) {
            sql += " WHERE member_name LIKE ?";
            params.add("%" + memberName + "%");
        }
        sql += " ORDER BY create_time DESC";
        Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, params.toArray());
        return webpage;
    }

    @Override
    public UpgradeLogDO add(UpgradeLogDO upgradeLog) {

        // Is not empty
        if (upgradeLog != null) {
            this.daoSupport.insert("es_upgrade_log", upgradeLog);
        }
        return upgradeLog;
    }

    @Override
    public void addUpgradeLog(int memberId, int newTplId, UpgradeTypeEnum upgradeType) {
        UpgradeLogDO upgradelog = new UpgradeLogDO();
        Member member = this.memberClient.getModel(memberId);
        int oldTplId = this.distributionManager.getDistributorByMemberId(memberId).getCurrentTplId();
        CommissionTpl oldTpl = this.commissionTplManager.getModel(oldTplId);
        CommissionTpl newTpl = this.commissionTplManager.getModel(newTplId);

        // The set of data
        upgradelog.setMemberId(memberId);
        if (member != null) {
            upgradelog.setMemberName(member.getUname());
        } else {
            upgradelog.setMemberName("The unknown");
        }

        // If so, record it
        if (oldTpl != null) {
            upgradelog.setOldTplId(oldTplId);
            upgradelog.setOldTplName(oldTpl.getTplName());
        }

        upgradelog.setNewTplId(newTplId);
        upgradelog.setNewTplName(newTpl.getTplName());
        upgradelog.setType(upgradeType.getName());
        upgradelog.setCreateTime(DateUtil.getDateline());
        this.add(upgradelog);
    }
}
