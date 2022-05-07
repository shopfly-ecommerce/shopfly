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
package cloud.shopfly.b2c.core.promotion.tool.service;


import cloud.shopfly.b2c.core.promotion.tool.model.dos.PromotionGoodsDO;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDetailDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;

import java.util.List;

/**
 * Active commodity control interface
 *
 * @author Snow create in 2018/3/21
 * @version v2.0
 * @since v7.0.0
 */
public interface PromotionGoodsManager {

    /**
     * Add the active commodity comparison table
     *
     * @param list      Products
     * @param detailDTO Event details
     */
    void add(List<PromotionGoodsDTO> list, PromotionDetailDTO detailDTO);

    /**
     * Add a single commodity comparison
     * @param goodsId
     * @param detailDTO
     * @return
     */
    PromotionGoodsDO addModel(Integer goodsId, PromotionDetailDTO detailDTO);


    /**
     * Modify the activity commodity comparison table
     *
     * @param list
     * @param detailDTO Event details
     */
    void edit(List<PromotionGoodsDTO> list, PromotionDetailDTO detailDTO);

    /**
     * According to the goodsidDelete activity(Ongoing or uninitiated sales promotion)
     * @param goodsId
     */
    void delete(Integer goodsId);

    /**
     * According to the activityidDelete the active goods table from the active tool type
     *
     * @param activityId
     * @param promotionType
     */
    void delete(Integer activityId, String promotionType);

    /**
     * According to the activityid,Activity tool types and goodsidDelete the active commodity comparison table
     *
     * @param goodsId       productid
     * @param activityId    activityid
     * @param promotionType The activity type
     */
    void delete(Integer goodsId, Integer activityId, String promotionType);


    /**
     * According to the activityIDAnd activity type to find out all the merchandise involved in the activity
     *
     * @param activityId
     * @param promotionType
     * @return
     */
    List<PromotionGoodsDO> getPromotionGoods(Integer activityId, String promotionType);

    /**
     * According to the goodsidRead all activities that the commodity participates in（Effective activities）
     *
     * @param goodsId
     * @return Returns a collection of activities
     */
    List<PromotionVO> getPromotion(Integer goodsId);

    /**
     * emptykey
     *
     * @param goodsId
     */
    void cleanCache(Integer goodsId);

    /**
     * Rewrite cache
     *
     * @param goodsId
     */
    void reputCache(Integer goodsId);
}
