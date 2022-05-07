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

import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsDTO;

/**
 * Commodity business layer
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:23:10
 */
public interface GoodsManager {

    /**
     * Add the goods
     *
     * @param goodsVo
     * @return
     */
    GoodsDO add(GoodsDTO goodsVo);

    /**
     * Modify the goods
     *
     * @param goodsDTO product
     * @param id       Commodity primary key
     * @return Goods product
     */
    GoodsDO edit(GoodsDTO goodsDTO, Integer id);

    /**
     * Goods from the shelves
     *
     * @param goodsIds
     * @param reason
     */
    void under(Integer[] goodsIds, String reason);

    /**
     * Goods go into the recycling bin
     *
     * @param goodsIds
     */
    void inRecycle(Integer[] goodsIds);

    /**
     * Goods to delete
     *
     * @param goodsIds
     */
    void delete(Integer[] goodsIds);

    /**
     * Recycle bin restores goods
     *
     * @param goodsIds
     */
    void revert(Integer[] goodsIds);

    /**
     * Goods shelves
     *
     * @param goodsId
     */
    void up(Integer goodsId);

    /**
     * Gets whether an item uses a template for detection
     *
     * @param templateId
     * @return product
     */
    GoodsDO checkShipTemplate(Integer templateId);

    /**
     * Update the good leveling rate of goods
     */
    void updateGoodsGrade();

}
