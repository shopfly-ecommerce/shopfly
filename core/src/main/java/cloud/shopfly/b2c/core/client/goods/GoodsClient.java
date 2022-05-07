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

import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSelectLine;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSnapshotVO;

import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v1.0
 * @Description: The external interface of a commodity
 * @date 2018/7/26 10:33
 * @since v7.0.0
 */
public interface GoodsClient {
    /**
     * Query information about a product in the cache
     *
     * @param goodsId
     * @return
     */
    CacheGoods getFromCache(Integer goodsId);

    /**
     * Query the total number of items by condition
     *
     * @param status Status
     * @return The total number of goods
     */
    Integer queryGoodsCountByParam(Integer status);

    /**
     * Example Query basic information about multiple commodities
     *
     * @param goodsIds
     * @return
     */
    List<GoodsSelectLine> query(Integer[] goodsIds);

    /**
     * Example Query the information and parameter information about many commodities
     *
     * @param goodsIds productidA collection of
     * @return
     */
    List<Map<String, Object>> getGoodsAndParams(Integer[] goodsIds);

    /**
     * Cached queryskuinformation
     *
     * @param skuId
     * @return
     */
    GoodsSkuVO getSkuFromCache(Integer skuId);

    /**
     * Update the number of reviews for items
     *
     * @param goodsId
     */
    void updateCommentCount(Integer goodsId);

    /**
     * Querying Commodity Information
     *
     * @param goodsIds productidA collection of
     * @return
     */
    List<Map<String, Object>> getGoods(Integer[] goodsIds);

    /**
     * Update the purchase quantity of goods
     *
     * @param list
     */
    void updateBuyCount(List<OrderSkuVO> list);

    /**
     * Querying the total number of goods
     *
     * @return The total number of goods
     */
    Integer queryGoodsCount();

    /**
     * Query information about a range of commodities
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Map> queryGoodsByRange(Integer pageNo, Integer pageSize);

    /**
     * Get product categories
     *
     * @param id Merchandise category primary key
     * @return Category Category
     */
    CategoryDO getCategory(Integer id);

    /**
     * Verify whether the product template is used
     *
     * @param templateId
     * @return product
     */
    GoodsDO checkShipTemplate(Integer templateId);

    /**
     * Interface for adding a commodity snapshot
     *
     * @param goodsId
     * @return
     */
    GoodsSnapshotVO queryGoodsSnapShotInfo(Integer goodsId);

    /**
     * Update the good leveling rate of goods
     */
    void updateGoodsGrade();

}
