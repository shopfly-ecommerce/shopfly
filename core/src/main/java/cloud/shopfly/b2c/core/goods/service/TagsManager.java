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

import cloud.shopfly.b2c.core.goods.model.dos.TagsDO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSelectLine;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Commodity label business layer
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-28 14:49:36
 */
public interface TagsManager {


    /**
     * Query a fixed number of items under a label
     *
     * @param num
     * @param mark
     * @return
     */
    List<GoodsSelectLine> queryTagGoods(Integer num, String mark);

    /**
     * Example Query the commodity label list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Query items under a label
     *
     * @param tagId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page queryTagGoods(Integer tagId, Integer pageNo, Integer pageSize);

    /**
     * Save labelled goods
     *
     * @param tagId
     * @param goodsIds
     * @return
     */
    void saveTagGoods(Integer tagId, Integer[] goodsIds);


    /**
     * Querying a label
     * @param id
     * @return
     */
    TagsDO getModel(Integer id);
}
