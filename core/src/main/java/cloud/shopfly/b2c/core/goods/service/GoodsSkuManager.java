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
import cloud.shopfly.b2c.core.goods.model.dos.GoodsSkuDO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsQueryParam;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * productskuThe business layer
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:48:40
 */
public interface GoodsSkuManager {

    /**
     * The querySKUThe list of
     * @param param
     * @return
     */
    Page list(GoodsQueryParam param);

    /**
     * Inquire about a productsku
     *
     * @param goodsId
     * @return
     */
    List<GoodsSkuVO> listByGoodsId(Integer goodsId);

    /**
     * Add the goodssku
     *
     * @param skuList
     * @param goods
     */
    void add(List<GoodsSkuVO> skuList, GoodsDO goods);

    /**
     * Modify the goodssku
     *
     * @param skuList
     * @param goods
     */
    void edit(List<GoodsSkuVO> skuList, GoodsDO goods);

    /**
     * According to the goodsskuA primary keyidCollection to obtain commodity information
     * @param skuIds
     * @return
     */
    List<GoodsSkuVO> query(Integer[] skuIds);

    /**
     * Cached queryskuinformation
     *
     * @param skuId
     * @return
     */
    GoodsSkuVO getSkuFromCache(Integer skuId);

    /**
     * Query a singlesku
     *
     * @param id
     * @return
     */
    GoodsSkuDO getModel(Integer id);

}
