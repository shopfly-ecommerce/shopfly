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

import cloud.shopfly.b2c.core.goods.model.dos.DraftGoodsDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsDTO;
import cloud.shopfly.b2c.core.goods.model.vo.DraftGoodsVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Draft commodity business layer
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-26 10:40:34
 */
public interface DraftGoodsManager {

    /**
     * Query the draft commodity list
     *
     * @param page        The page number
     * @param pageSize    Number each page
     * @param keyword
     * @param shopCatPath
     * @return Page
     */
    Page list(int page, int pageSize, String keyword, String shopCatPath);

    /**
     * Add draft goods
     *
     * @param goodsVO The draft of goods
     * @return DraftGoods The draft of goods
     */
    DraftGoodsDO add(GoodsDTO goodsVO);

    /**
     * Revised draft goods
     *
     * @param goodsVo The draft of goods
     * @param id      Draft commodity primary key
     * @return DraftGoods The draft of goods
     */
    DraftGoodsDO edit(GoodsDTO goodsVo, Integer id);

    /**
     * Delete draft goods
     *
     * @param draftGoodsIds Draft commodity primary key
     */
    void delete(Integer[] draftGoodsIds);

    /**
     * Get draft goods
     *
     * @param id Draft commodity primary key
     * @return DraftGoods  The draft of goods
     */
    DraftGoodsDO getModel(Integer id);

    /**
     * Get draft goods
     *
     * @param id Draft commodity primary key
     * @return DraftGoods  The draft of goods
     */
    DraftGoodsVO getVO(Integer id);

    /**
     * Draft goods on shelves
     *
     * @param goodsVO
     * @param draftGoodsId
     * @return
     */
    GoodsDO addMarket(GoodsDTO goodsVO, Integer draftGoodsId);

}
