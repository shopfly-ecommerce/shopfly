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
 * 商品标签业务层
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-28 14:49:36
 */
public interface TagsManager {


    /**
     * 查询某个标签下固定数量的商品
     *
     * @param num
     * @param mark
     * @return
     */
    List<GoodsSelectLine> queryTagGoods(Integer num, String mark);

    /**
     * 查询商品标签列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 查询某标签下的商品
     *
     * @param tagId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page queryTagGoods(Integer tagId, Integer pageNo, Integer pageSize);

    /**
     * 保存标签商品
     *
     * @param tagId
     * @param goodsIds
     * @return
     */
    void saveTagGoods(Integer tagId, Integer[] goodsIds);


    /**
     * 查询一个标签
     * @param id
     * @return
     */
    TagsDO getModel(Integer id);
}