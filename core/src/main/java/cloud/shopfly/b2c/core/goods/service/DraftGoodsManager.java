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
 * 草稿商品业务层
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-26 10:40:34
 */
public interface DraftGoodsManager {

    /**
     * 查询草稿商品列表
     *
     * @param page        页码
     * @param pageSize    每页数量
     * @param keyword
     * @param shopCatPath
     * @return Page
     */
    Page list(int page, int pageSize, String keyword, String shopCatPath);

    /**
     * 添加草稿商品
     *
     * @param goodsVO 草稿商品
     * @return DraftGoods 草稿商品
     */
    DraftGoodsDO add(GoodsDTO goodsVO);

    /**
     * 修改草稿商品
     *
     * @param goodsVo 草稿商品
     * @param id      草稿商品主键
     * @return DraftGoods 草稿商品
     */
    DraftGoodsDO edit(GoodsDTO goodsVo, Integer id);

    /**
     * 删除草稿商品
     *
     * @param draftGoodsIds 草稿商品主键
     */
    void delete(Integer[] draftGoodsIds);

    /**
     * 获取草稿商品
     *
     * @param id 草稿商品主键
     * @return DraftGoods  草稿商品
     */
    DraftGoodsDO getModel(Integer id);

    /**
     * 获取草稿商品
     *
     * @param id 草稿商品主键
     * @return DraftGoods  草稿商品
     */
    DraftGoodsVO getVO(Integer id);

    /**
     * 草稿商品上架
     *
     * @param goodsVO
     * @param draftGoodsId
     * @return
     */
    GoodsDO addMarket(GoodsDTO goodsVO, Integer draftGoodsId);

}