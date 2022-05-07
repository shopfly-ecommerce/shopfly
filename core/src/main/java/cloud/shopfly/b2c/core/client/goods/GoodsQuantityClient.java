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
package cloud.shopfly.b2c.core.client.goods;

import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;

import java.util.List;

/**
 * Merchandise inventory operation client
 * @author zh
 * @version v1.0
 * @date 18/9/20 In the afternoon7:31
 * @since v7.0
 *
 * @version 2.0
 * Unified into one interface（Update the interface）<br/>
 * Internally implemented asredis +lua Guaranteed atomicity-- by kingapex 2019-01-17
 */
public interface GoodsQuantityClient {


    /**
     * Inventory update interface
     * @param goodsQuantityList Inventory to be deductedvo List
     * @return Returns true if the deduction succeeds, false otherwise
     */
    boolean updateSkuQuantity(List<GoodsQuantityVO> goodsQuantityList);
}


