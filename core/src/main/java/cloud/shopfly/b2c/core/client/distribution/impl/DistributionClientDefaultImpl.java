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
package cloud.shopfly.b2c.core.client.distribution.impl;

import cloud.shopfly.b2c.core.client.distribution.DistributionClient;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionDO;
import cloud.shopfly.b2c.core.distribution.service.DistributionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * DistributionGoodsClientDefaultImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 In the afternoon1:32
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class DistributionClientDefaultImpl implements DistributionClient {

    @Autowired
    private DistributionManager distributionManager;

    /**
     * New distributor
     *
     * @param distributor
     * @return
     */
    @Override
    public DistributionDO add(DistributionDO distributor) {
        return distributionManager.add(distributor);
    }

    /**
     * updateDistributorinformation
     *
     * @param distributor
     * @return
     */
    @Override
    public DistributionDO edit(DistributionDO distributor) {
        return distributionManager.edit(distributor);
    }

    /**
     * Acquiring distributors
     *
     * @param memberId
     * @return
     */
    @Override
    public DistributionDO getDistributorByMemberId(Integer memberId) {
        return distributionManager.getDistributorByMemberId(memberId);
    }

    /**
     * According to the membershipidSet up its superior distributor（Two levels of）
     *
     * @param memberId membersid
     * @param parentId Superior membersid
     * @return Set the result, trun=successfulfalse=failure
     */
    @Override
    public boolean setParentDistributorId(Integer memberId, Integer parentId) {
        return distributionManager.setParentDistributorId(memberId, parentId);
    }
}
