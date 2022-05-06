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
 * 2018-08-14 下午1:54
 */
public interface DistributionClient {

    /**
     * 新增分销商
     *
     * @param distributor
     * @return
     */
    DistributionDO add(DistributionDO distributor);

    /**
     * 更新Distributor信息
     *
     * @param distributor
     * @return
     */
    DistributionDO edit(DistributionDO distributor);

    /**
     * 获取分销商
     *
     * @param memberId
     * @return
     */
    DistributionDO getDistributorByMemberId(Integer memberId);

    /**
     * 根据会员id设置其上级分销商（两级）
     *
     * @param memberId 会员id
     * @param parentId 上级会员的id
     * @return 设置结果， trun=成功 false=失败
     */
    boolean setParentDistributorId(Integer memberId, Integer parentId);
}
