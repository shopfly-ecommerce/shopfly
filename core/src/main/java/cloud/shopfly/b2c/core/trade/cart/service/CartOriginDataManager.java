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
package cloud.shopfly.b2c.core.trade.cart.service;

import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuOriginVo;

import java.util.List;

/**
 * 购物车原始数据业务类<br/>
 * 负责对购物车原始数据{@link CartSkuOriginVo}在缓存中的读写
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/11
 */
public interface CartOriginDataManager {


    /**
     * 由缓存中读取数据
     *
     * @return 列表
     */
    List<CartSkuOriginVo> read();


    /**
     * 向缓存中写入数据
     *
     * @param skuId      要写入的skuid
     * @param num        要加入购物车的数量
     * @param activityId 要参加的活动
     * @return
     */
    CartSkuOriginVo add(int skuId, int num, Integer activityId);

    /**
     * 立即购买
     *
     * @param skuId
     * @param num
     * @param activityId
     */
    void buy(Integer skuId, Integer num, Integer activityId);

    /**
     * 更新数量
     *
     * @param skuId 要更新的sku id
     * @param num   要更新的数量
     * @return
     */
    CartSkuOriginVo updateNum(int skuId, int num);


    /**
     * 更新选中状态
     *
     * @param skuId
     * @param checked
     * @return
     */
    CartSkuOriginVo checked(int skuId, int checked);


    /**
     * 更新某个店铺的所有商品的选中状态
     *
     * @param sellerId
     * @param checked
     */
    void checkedSeller(int sellerId, int checked);


    /**
     * 更新全部的选中状态
     *
     * @param checked
     */
    void checkedAll(int checked);


    /**
     * 批量删除
     *
     * @param skuIds
     */
    void delete(Integer[] skuIds);

    /**
     * 清空购物车
     */
    void clean();

    /**
     * 清除掉购物车中已经选中的商品
     */
    void cleanChecked();


}
