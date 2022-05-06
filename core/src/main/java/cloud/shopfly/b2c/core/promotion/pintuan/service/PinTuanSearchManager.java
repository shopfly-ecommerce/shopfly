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
package cloud.shopfly.b2c.core.promotion.pintuan.service;

import cloud.shopfly.b2c.core.promotion.pintuan.model.PinTuanGoodsVO;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PtGoodsDoc;

import java.util.List;

/**
 * Created by kingapex on 2019-01-21.
 * 拼团搜索业务接口
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-21
 */
public interface PinTuanSearchManager {

    /**
     * 搜索拼团商品
     *
     * @param categoryId
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<PtGoodsDoc> search(Integer categoryId, Integer pageNo, Integer pageSize);


    /**
     * 向es写入索引
     *
     * @param goodsDoc
     */
    void addIndex(PtGoodsDoc goodsDoc);

    /**
     * 向es写入索引
     *
     * @param pintuanGoods
     * @return 是否生成成功
     */
    boolean addIndex(PinTuanGoodsVO pintuanGoods);

    /**
     * 删除一个sku的索引
     *
     * @param skuId
     */
    void delIndex(Integer skuId);


    /**
     * 删除某个商品的所有的索引
     *
     * @param goodsId
     */
    void deleteByGoodsId(Integer goodsId);


    /**
     * 删除某个拼团的所有索引
     *
     * @param pinTuanId 拼团id
     */
    void deleteByPintuanId(Integer pinTuanId);

    /**
     * 根据拼团id同步es中的拼团商品<br/>
     * 当拼团活动商品发生变化时调用此方法
     *
     * @param pinTuanId
     */
    void syncIndexByPinTuanId(Integer pinTuanId);

    /**
     * 根据商品id同步es中的拼团商品<br>
     *
     * @param goodsId 商品id
     */
    void syncIndexByGoodsId(Integer goodsId);
}
