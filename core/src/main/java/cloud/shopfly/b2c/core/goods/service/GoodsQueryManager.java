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
import cloud.shopfly.b2c.core.goods.model.dto.GoodsQueryParam;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSelectLine;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;
import java.util.Map;

/**
 * Commodity business layer
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:23:10
 */
public interface GoodsQueryManager {

    /**
     * Access to goods
     *
     * @param id Commodity primary key
     * @return Goods product
     */
    GoodsDO getModel(Integer id);

    /**
     * Inquire the good rate of goods
     *
     * @param goodsId
     * @return
     */
    Double getGoodsGrade(Integer goodsId);

    /**
     * Query information about a product in the cache
     *
     * @param goodsId
     * @return
     */
    CacheGoods getFromCache(Integer goodsId);

    /**
     * Check if the goods are in the distribution area
     *
     * @param goodsId
     * @param areaId
     * @return
     */
    Integer checkArea(Integer goodsId, Integer areaId);

    /**
     * Gets the category path of an item
     *
     * @param id
     * @return
     */
    String queryCategoryPath(Integer id);

    /**
     * Querying commodity list
     *
     * @param goodsQueryParam
     * @return
     */
    Page list(GoodsQueryParam goodsQueryParam);

    /**
     * Query the goods of the warning goods
     *
     * @param pageNo
     * @param pageSize
     * @param keyword
     * @return
     */
    Page warningGoodsList(int pageNo, int pageSize, String keyword);

    /**
     * Merchant inquiry for goods,Edit view use
     *
     * @param goodsId
     * @return
     */
    GoodsVO queryGoods(Integer goodsId);

    /**
     * Example Query basic information about multiple commodities
     *
     * @param goodsIds
     * @return
     */
    List<GoodsSelectLine> query(Integer[] goodsIds);

    /**
     * Query the number of goods according to the condition
     *
     * @param status   Status
     * @param disabled Whether the item has been deleted
     * @return Number of goods
     */
    Integer getGoodsCountByParam(Integer status, Integer disabled);

    /**
     * Example Query the information and parameter information about many commodities
     *
     * @param goodsIds productidA collection of
     * @return
     */
    List<Map<String, Object>> getGoodsAndParams(Integer[] goodsIds);

    /**
     * According to the goodsidCollection queries commodity information
     *
     * @param goodsIds productids
     * @return  Product information
     */
    List<Map<String, Object>> getGoods(Integer[] goodsIds);

    /**
     * Get all goodsidA collection of
     * @return
     */
    List<Integer> getAllGoodsId();

}
