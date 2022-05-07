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
package cloud.shopfly.b2c.core.goods.service;

import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;

import java.util.List;
import java.util.Map;


/**
 * Commodity inventory interface
 *
 * @author fk
 * @version 3.0
 * Unified into one interface（Update the interface）<br/>
 * Internally implemented asredis +lua Guaranteed atomicity-- by kingapex 2019-01-17
 * @since v7.0.0
 * 2018years3month23The morning of11:47:29
 */
public interface GoodsQuantityManager {


    /**
     * For asku Fill in the inventorycache<br/>
     * The inventory quantity is obtained from the database<br/>
     * Generally used in the case of cache breakdown
     *
     * @param skuId
     * @return Available stock and actual stock
     */
    Map<String, Integer> fillCacheFromDB(int skuId);

    /**
     * Inventory update interface
     * @param goodsQuantityList Inventory to be updatedvo List
     * @return Return true if the update was successful, false otherwise
     */
    Boolean updateSkuQuantity(List<GoodsQuantityVO> goodsQuantityList );

    /**
     * Synchronizing database data
     */
    void syncDataBase();


}
