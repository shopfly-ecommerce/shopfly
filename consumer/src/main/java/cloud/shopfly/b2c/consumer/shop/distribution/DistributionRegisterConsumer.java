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
package cloud.shopfly.b2c.consumer.shop.distribution;

import cloud.shopfly.b2c.consumer.core.event.MemberRegisterEvent;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.message.MemberRegisterMsg;
import cloud.shopfly.b2c.core.client.distribution.CommissionTplClient;
import cloud.shopfly.b2c.core.client.distribution.DistributionClient;
import cloud.shopfly.b2c.core.distribution.model.dos.CommissionTpl;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionDO;
import cloud.shopfly.b2c.framework.cache.Cache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Add resellers after registration
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/13 In the afternoon11:33
 */
@Component
public class DistributionRegisterConsumer implements MemberRegisterEvent {
    @Autowired
    private DistributionClient distributionClient;

    @Autowired
    private CommissionTplClient commissionTplClient;

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private Cache cache;

    @Transactional( rollbackFor = Exception.class)
    @Override
    public void memberRegister(MemberRegisterMsg memberRegisterMsg) {
        // Add membership information to the distributor table after registration
        DistributionDO distributor = new DistributionDO();
        try {
            distributor.setMemberId(memberRegisterMsg.getMember().getMemberId());
            // Default template Settings
            CommissionTpl commissionTpl = commissionTplClient.getDefaultCommission();
            distributor.setCurrentTplId(commissionTpl.getId());
            distributor.setCurrentTplName(commissionTpl.getTplName());
            distributor.setMemberName(memberRegisterMsg.getMember().getUname());
            distributor = this.distributionClient.add(distributor);
            distributor.setPath("0|" + distributor.getMemberId() + "|");
            this.distributionClient.edit(distributor);
        } catch (RuntimeException e) {
            logger.error(e);
        }

        // After registration, add the registered members parent reseller ID
        Object upMemberId = cache.get(CachePrefix.DISTRIBUTION_UP.getPrefix() + memberRegisterMsg.getUuid());

        try {
            // If the promoted member ID exists
            if (upMemberId != null) {
                int lv1MemberId = Integer.parseInt(upMemberId.toString());
                DistributionDO parentDistributor = this.distributionClient.getDistributorByMemberId(lv1MemberId);
                distributor.setPath(parentDistributor.getPath() + distributor.getMemberId() + "|");

                // To update the path
                this.distributionClient.edit(distributor);
                // To update the parentId
                this.distributionClient.setParentDistributorId(memberRegisterMsg.getMember().getMemberId(), lv1MemberId);

            } else {
                this.distributionClient.edit(distributor);
            }
            cache.remove(CachePrefix.DISTRIBUTION_UP.getPrefix() + memberRegisterMsg.getUuid());
        } catch (Exception e) {
            logger.error(e);
        }
    }


}
