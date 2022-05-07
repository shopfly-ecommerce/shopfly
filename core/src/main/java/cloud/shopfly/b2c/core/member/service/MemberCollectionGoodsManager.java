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
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.MemberCollectionGoods;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Member commodity collection table business layer
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 10:13:41
 */
public interface MemberCollectionGoodsManager {

    /**
     * Query the list of member merchandise collection list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Add member merchandise collection list
     *
     * @param memberCollectionGoods Collectible commodity object
     * @return
     */
    MemberCollectionGoods add(MemberCollectionGoods memberCollectionGoods);

    /**
     * Delete member merchandise collection
     *
     * @param goodsId productid
     */
    void delete(Integer goodsId);

    /**
     * Obtain member merchandise collection
     *
     * @param id Member commodity collection table primary key
     * @return MemberCollectionGoods  Member merchandise Collection list
     */
    MemberCollectionGoods getModel(Integer id);

    /**
     * Obtain the number of items collected by members
     *
     * @return Number of goods collected
     */
    Integer getMemberCollectCount();

    /**
     * Quantity of a collection
     *
     * @param goodsId
     * @return
     */
    Integer getGoodsCollectCount(Integer goodsId);

    /**
     * Whether to collect an item
     *
     * @param id productid
     * @return
     */
    boolean isCollection(Integer id);
}
