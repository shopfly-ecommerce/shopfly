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

import cloud.shopfly.b2c.core.promotion.model.PromotionAbnormalGoods;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PinTuanGoodsVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PintuanGoodsDO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Group commodity business layer
 *
 * @author admin
 * @version vv1.0.0
 * @since vv7.1.0
 * 2019-01-22 11:20:56
 */
public interface PintuanGoodsManager {

    /**
     * Query the list of group items
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);


    /**
     * Add group goods
     *
     * @param pintuanGoods Spell mass goods
     * @return PintuanGoods Spell mass goods
     */
    PintuanGoodsDO add(PintuanGoodsDO pintuanGoods);


    /**
     * Batch save group commodity data
     *
     * @param pintuanId        Spell groupid
     * @param pintuanGoodsList Group goods to be added in bulk
     */
    void save(Integer pintuanId, List<PintuanGoodsDO> pintuanGoodsList);

    /**
     * Modify group goods
     *
     * @param pintuanGoods Spell mass goods
     * @param id           Group commodities primary key
     * @return PintuanGoods Spell mass goods
     */
    PintuanGoodsDO edit(PintuanGoodsDO pintuanGoods, Integer id);

    /**
     * Delete group items
     *
     * @param id Group commodities primary key
     */
    void delete(Integer id);

    /**
     * Get group goods
     *
     * @param id Group commodities primary key
     * @return PintuanGoods  Spell mass goods
     */
    PintuanGoodsDO getModel(Integer id);

    /**
     * Get group goods
     *
     * @param pintuanId
     * @param skuId
     * @return
     */
    PintuanGoodsDO getModel(Integer pintuanId, Integer skuId);

    /**
     * Get detailed information about the group products, including the group itself
     *
     * @param skuId skuid
     * @return Goods in detailvo
     */
    PinTuanGoodsVO getDetail(Integer skuId);


    /**
     * Obtain a commodity to participate in the groupsku
     *
     * @param goodsId
     * @param pintuanId
     * @return
     */
    List<GoodsSkuVO> skus(Integer goodsId, Integer pintuanId);

    /**
     * Update the number of groups
     *
     * @param id  Spell mass goodsid
     * @param num Quantity
     */
    void addQuantity(Integer id, Integer num);

    /**
     * Get all goods for an event
     *
     * @param promotionId
     * @return
     */
    List<PinTuanGoodsVO> all(Integer promotionId);


    /**
     * Close the promotional items index for an event
     *
     * @param promotionId
     */
    void delIndex(Integer promotionId);

    /**
     * Open an index of promotional items for an event
     *
     * @param promotionId
     * @return
     */
    boolean addIndex(Integer promotionId);


    /**
     * Goods query
     *
     * @param page        The page number
     * @param pageSize    Page size
     * @param promotionId Sales promotionid
     * @param name        Name
     * @return
     */
    Page page(Integer page, Integer pageSize, Integer promotionId, String name);

    /**
     * Queries whether you have participated in other activities within the specified time range
     *
     * @param skuIds      productidA collection of
     * @param startTime   The start time
     * @param endTime     The end of time
     * @param promotionID Sales promotionid
     * @return
     */
    List<PromotionAbnormalGoods> checkPromotion(Integer[] skuIds, Long startTime, Long endTime, Integer promotionID);

}
