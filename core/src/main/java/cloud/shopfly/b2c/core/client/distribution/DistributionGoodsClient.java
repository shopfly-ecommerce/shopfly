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

import cloud.shopfly.b2c.core.distribution.model.dos.DistributionGoods;

/**
 * 分销商品客户端
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/8/14 下午1:31
 */
public interface DistributionGoodsClient {


    /**
     * 修改分销商品提现设置
     *
     * @param distributionGoods
     * @return
     */
    DistributionGoods edit(DistributionGoods distributionGoods);

    /**
     * 获取分销设置
     *
     * @param goodsId
     * @return
     */
    DistributionGoods getModel(Integer goodsId);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Integer id);

}
