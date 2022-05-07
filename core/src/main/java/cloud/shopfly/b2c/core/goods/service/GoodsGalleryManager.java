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
 * Commodity album business layer
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:39:54
 */
public interface GoodsGalleryManager {

    /**
     * Query an album for an item
     *
     * @param goodsId
     * @return
     */
    List<GoodsGalleryDO> list(Integer goodsId);

    /**
     * Use the original image to get the image format of the other specifications of the product
     *
     * @param origin
     * @return
     */
    GoodsGalleryDO getGoodsGallery(String origin);

    /**
     * Add a commodity album
     *
     * @param goodsGallery Photo album
     * @return GoodsGallery Photo album
     */
    GoodsGalleryDO add(GoodsGalleryDO goodsGallery);

    /**
     * Add an album of goods
     *
     * @param goodsGalleryList
     * @param goodsId
     */
    void add(List<GoodsGalleryDO> goodsGalleryList, Integer goodsId);

    /**
     * Modify an album for an item
     * @param goodsGalleryList
     * @param goodsId
     */
    void edit(List<GoodsGalleryDO> goodsGalleryList, Integer goodsId);

    /**
     * Get commodity album
     *
     * @param id
     *            Commodity album main key
     * @return GoodsGallery Photo album
     */
    GoodsGalleryDO getModel(Integer id);


}
