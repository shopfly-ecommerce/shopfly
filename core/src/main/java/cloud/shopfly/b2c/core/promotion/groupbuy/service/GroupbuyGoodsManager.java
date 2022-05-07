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
package cloud.shopfly.b2c.core.promotion.groupbuy.service;

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyGoodsDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyGoodsVO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyQueryParam;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDTO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Group purchase commodity business layer
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 16:57:26
 */
public interface GroupbuyGoodsManager {


    /**
     * Merchants check the list of products for group purchase
     *
     * @param param Query parameters
     * @return Page
     */
    Page listPage(GroupbuyQueryParam param);


    /**
     * The seller queries the list of group deals
     *
     * @param param Query parameters
     * @return Page
     */
    Page listPageByBuyer(GroupbuyQueryParam param);


    /**
     * Add group purchase items
     *
     * @param groupbuyGoods A bulk goods
     * @return GroupbuyGoods A bulk goods
     */
    GroupbuyGoodsDO add(GroupbuyGoodsDO groupbuyGoods);

    /**
     * Modify group purchase goods
     *
     * @param groupbuyGoods A bulk goods
     * @param id            Group purchase commodity primary key
     * @return GroupbuyGoods A bulk goods
     */
    GroupbuyGoodsDO edit(GroupbuyGoodsDO groupbuyGoods, Integer id);

    /**
     * Delete group purchase products
     *
     * @param id Group purchase commodity primary key
     */
    void delete(Integer id);

    /**
     * Get group-buy items
     *
     * @param gbId Group purchase commodity primary key
     * @return GroupbuyGoods  A bulk goods
     */
    GroupbuyGoodsVO getModel(Integer gbId);


    /**
     * Get group-buy items
     *
     * @param actId   Group-buying activitiesID
     * @param goodsId productID
     * @return GroupbuyGoods  A bulk goods
     */
    GroupbuyGoodsDO getModel(Integer actId, Integer goodsId);

    /**
     * Verifying operation rights<br/>
     * Throw a permission exception if there is a problem
     *
     * @param id
     */
    void verifyAuth(Integer id);

    /**
     * Modifying audit Status
     *
     * @param gbId
     * @param status
     */
    void updateStatus(Integer gbId, Integer status);


    /**
     * Deduct the inventory of group purchase goods
     *
     * @param orderSn
     * @param promotionDTOList
     * @return
     */
    boolean cutQuantity(String orderSn, List<PromotionDTO> promotionDTOList);

    /**
     * Restore group purchase goods inventory
     *
     * @param orderSn
     */
    void addQuantity(String orderSn);


    /**
     * Query group purchase information and commodity inventory information
     *
     * @param id
     * @return
     */
    GroupbuyGoodsVO getModelAndQuantity(Integer id);


    /**
     * According to the goodsid, modify group purchase commodity information
     *
     * @param goodsIds
     */
    void updateGoodsInfo(Integer[] goodsIds);

    /**
     * Roll back the inventory
     *
     * @param promotionDTOList
     * @param orderSn
     */
    void rollbackStock(List<PromotionDTO> promotionDTOList, String orderSn);

}
