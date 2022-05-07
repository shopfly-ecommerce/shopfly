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
 * Shopping cart raw data business class<br/>
 * Responsible for shopping cart raw data{@link CartSkuOriginVo}Read and write in the cache
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/11
 */
public interface CartOriginDataManager {


    /**
     * Read data from the cache
     *
     * @return The list of
     */
    List<CartSkuOriginVo> read();


    /**
     * Writes data to the cache
     *
     * @param skuId      Want to writeskuid
     * @param num        Quantity to be added to cart
     * @param activityId Activities to attend
     * @return
     */
    CartSkuOriginVo add(int skuId, int num, Integer activityId);

    /**
     * Buy now
     *
     * @param skuId
     * @param num
     * @param activityId
     */
    void buy(Integer skuId, Integer num, Integer activityId);

    /**
     * Update the number
     *
     * @param skuId To update thesku id
     * @param num   Number of updates to be made
     * @return
     */
    CartSkuOriginVo updateNum(int skuId, int num);


    /**
     * Update selected Status
     *
     * @param skuId
     * @param checked
     * @return
     */
    CartSkuOriginVo checked(int skuId, int checked);


    /**
     * Updates the selected status of all items in a store
     *
     * @param sellerId
     * @param checked
     */
    void checkedSeller(int sellerId, int checked);


    /**
     * Update all selected status
     *
     * @param checked
     */
    void checkedAll(int checked);


    /**
     * Batch delete
     *
     * @param skuIds
     */
    void delete(Integer[] skuIds);

    /**
     * Empty shopping cart
     */
    void clean();

    /**
     * Clear the cart of items already selected
     */
    void cleanChecked();


}
