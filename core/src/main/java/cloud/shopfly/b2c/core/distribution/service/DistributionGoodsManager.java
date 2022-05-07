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

import cloud.shopfly.b2c.core.distribution.model.dos.DistributionGoods;

import java.util.List;

/**
 * Commodity Distribution Interface
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/14 In the morning12:37
 */
public interface DistributionGoodsManager {

    /**
     * Set up withdrawal Settings for distributed goods
     *
     * @param distributionGoods
     * @return
     */
    DistributionGoods edit(DistributionGoods distributionGoods);

    /**
     * delete
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * To obtainmodel
     *
     * @param goodsId
     * @return
     */
    DistributionGoods getModel(Integer goodsId);


    /**
     * Get distribution rebate merchandise
     * @param goodsIds
     * @return
     */
    List<DistributionGoods> getGoodsIds(List<Integer> goodsIds);
}
