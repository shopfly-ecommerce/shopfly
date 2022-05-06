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

import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;

import java.util.List;

/**
 * 商品相册业务层
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:39:54
 */
public interface GoodsGalleryManager {

    /**
     * 查询某商品的相册
     *
     * @param goodsId
     * @return
     */
    List<GoodsGalleryDO> list(Integer goodsId);

    /**
     * 使用原始图片得到商品的其他规格的图片格式
     *
     * @param origin
     * @return
     */
    GoodsGalleryDO getGoodsGallery(String origin);

    /**
     * 添加商品相册
     *
     * @param goodsGallery 商品相册
     * @return GoodsGallery 商品相册
     */
    GoodsGalleryDO add(GoodsGalleryDO goodsGallery);

    /**
     * 添加商品的相册
     *
     * @param goodsGalleryList
     * @param goodsId
     */
    void add(List<GoodsGalleryDO> goodsGalleryList, Integer goodsId);

    /**
     * 修改某商品的相册
     * @param goodsGalleryList
     * @param goodsId
     */
    void edit(List<GoodsGalleryDO> goodsGalleryList, Integer goodsId);

    /**
     * 获取商品相册
     *
     * @param id
     *            商品相册主键
     * @return GoodsGallery 商品相册
     */
    GoodsGalleryDO getModel(Integer id);


}