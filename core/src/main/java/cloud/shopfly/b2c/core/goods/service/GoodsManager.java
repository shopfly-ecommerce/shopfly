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
import cloud.shopfly.b2c.core.goods.model.dto.GoodsDTO;

/**
 * 商品业务层
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:23:10
 */
public interface GoodsManager {

    /**
     * 添加商品
     *
     * @param goodsVo
     * @return
     */
    GoodsDO add(GoodsDTO goodsVo);

    /**
     * 修改商品
     *
     * @param goodsDTO 商品
     * @param id       商品主键
     * @return Goods 商品
     */
    GoodsDO edit(GoodsDTO goodsDTO, Integer id);

    /**
     * 商品下架
     *
     * @param goodsIds
     * @param reason
     */
    void under(Integer[] goodsIds, String reason);

    /**
     * 商品放入回收站
     *
     * @param goodsIds
     */
    void inRecycle(Integer[] goodsIds);

    /**
     * 商品删除
     *
     * @param goodsIds
     */
    void delete(Integer[] goodsIds);

    /**
     * 回收站还原商品
     *
     * @param goodsIds
     */
    void revert(Integer[] goodsIds);

    /**
     * 上架商品
     *
     * @param goodsId
     */
    void up(Integer goodsId);

    /**
     * 获取商品是否使用检测的模版
     *
     * @param templateId
     * @return 商品
     */
    GoodsDO checkShipTemplate(Integer templateId);

    /**
     * 更新商品好平率
     */
    void updateGoodsGrade();

}