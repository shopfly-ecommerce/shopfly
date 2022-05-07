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
package cloud.shopfly.b2c.core.statistics.service;

import cloud.shopfly.b2c.core.statistics.model.dto.GoodsData;

/**
 * Goods collectionmanager
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/8 In the afternoon4:11
 */

public interface GoodsDataManager {
    /**
     * The new goods
     *
     * @param goodsIds productid
     */
    void addGoods(Integer[] goodsIds);

    /**
     * Modify the goods
     *
     * @param goodsIds productid
     */
    void updateGoods(Integer[] goodsIds);

    /**
     * Delete the goods
     *
     * @param goodsIds productid
     */
    void deleteGoods(Integer[] goodsIds);

    /**
     * Modify the collection quantity of goods
     * @param goodsData
     */
    void updateCollection(GoodsData goodsData);


    /**
     * Access to goods
     * @param goodsId
     * @return
     */
    GoodsData get(Integer goodsId);

    /**
     * Take all merchandise off the shelves
     */
    void underAllGoods();

}
