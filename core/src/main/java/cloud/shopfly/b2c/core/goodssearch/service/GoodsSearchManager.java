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
 * 商品搜索
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年9月14日 上午10:52:20
 */
public interface GoodsSearchManager {

    /**
     * 搜索
     *
     * @param goodsSearch 搜索条件
     * @return 商品分页
     */
    Page search(GoodsSearchDTO goodsSearch);

    /**
     * 获取筛选器
     *
     * @param goodsSearch 搜索条件
     * @return Map
     */
    Map<String, Object> getSelector(GoodsSearchDTO goodsSearch);

    /**
     * 通过关键字获取商品分词索引
     *
     * @param keyword
     * @return
     */
    List<GoodsWords> getGoodsWords(String keyword);

    /**
     * 获取'为你推荐'商品列表
     * @param goodsSearch 查询参数
     * @return 分页数据
     */
    Page recommendGoodsList(GoodsSearchDTO goodsSearch);
}
