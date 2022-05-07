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
package cloud.shopfly.b2c.core.client.distribution;

import cloud.shopfly.b2c.core.distribution.model.dos.DistributionDO;

/**
 * DistributionClient
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 In the afternoon1:54
 */
public interface DistributionClient {

    /**
     * New distributor
     *
     * @param distributor
     * @return
     */
    DistributionDO add(DistributionDO distributor);

    /**
     * updateDistributorinformation
     *
     * @param distributor
     * @return
     */
    DistributionDO edit(DistributionDO distributor);

    /**
     * Acquiring distributors
     *
     * @param memberId
     * @return
     */
    DistributionDO getDistributorByMemberId(Integer memberId);

    /**
     * According to the membershipidSet up its superior distributor（Two levels of）
     *
     * @param memberId membersid
     * @param parentId Superior membersid
     * @return Set the result, trun=successfulfalse=failure
     */
    boolean setParentDistributorId(Integer memberId, Integer parentId);
}
