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
package cloud.shopfly.b2c.core.promotion.pintuan.service;

import cloud.shopfly.b2c.core.promotion.pintuan.model.PinTuanGoodsVO;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PtGoodsDoc;

import java.util.List;

/**
 * Created by kingapex on 2019-01-21.
 * Group search service interface
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-21
 */
public interface PinTuanSearchManager {

    /**
     * Search for group products
     *
     * @param categoryId
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<PtGoodsDoc> search(Integer categoryId, Integer pageNo, Integer pageSize);


    /**
     * toesWrite the index
     *
     * @param goodsDoc
     */
    void addIndex(PtGoodsDoc goodsDoc);

    /**
     * toesWrite the index
     *
     * @param pintuanGoods
     * @return Check whether the file is generated successfully.
     */
    boolean addIndex(PinTuanGoodsVO pintuanGoods);

    /**
     * To delete askuThe index of the
     *
     * @param skuId
     */
    void delIndex(Integer skuId);


    /**
     * Deletes all indexes for an item
     *
     * @param goodsId
     */
    void deleteByGoodsId(Integer goodsId);


    /**
     * Deletes all indexes of a group
     *
     * @param pinTuanId Spell groupid
     */
    void deleteByPintuanId(Integer pinTuanId);

    /**
     * According to spellidsynchronousesIn the group of goods<br/>
     * This method is called when a group activity item changes
     *
     * @param pinTuanId
     */
    void syncIndexByPinTuanId(Integer pinTuanId);

    /**
     * According to the goodsidsynchronousesIn the group of goods<br>
     *
     * @param goodsId productid
     */
    void syncIndexByGoodsId(Integer goodsId);
}
