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
package cloud.shopfly.b2c.core.goodssearch.service;

import cloud.shopfly.b2c.core.goodssearch.model.GoodsSearchDTO;
import cloud.shopfly.b2c.core.goodssearch.model.GoodsWords;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;
import java.util.Map;

/**
 * Commodity search
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017years9month14The morning of10:52:20
 */
public interface GoodsSearchManager {

    /**
     * search
     *
     * @param goodsSearch The search criteria
     * @return Goods paging
     */
    Page search(GoodsSearchDTO goodsSearch);

    /**
     * Get filter
     *
     * @param goodsSearch The search criteria
     * @return Map
     */
    Map<String, Object> getSelector(GoodsSearchDTO goodsSearch);

    /**
     * Get the item segmentation index by keyword
     *
     * @param keyword
     * @return
     */
    List<GoodsWords> getGoodsWords(String keyword);

    /**
     * To obtain'Recommend to you'Products
     * @param goodsSearch Query parameters
     * @return Paging data
     */
    Page recommendGoodsList(GoodsSearchDTO goodsSearch);
}
