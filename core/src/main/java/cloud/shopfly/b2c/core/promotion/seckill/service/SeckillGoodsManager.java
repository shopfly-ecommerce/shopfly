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
package cloud.shopfly.b2c.core.promotion.seckill.service;

import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillApplyDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dto.SeckillQueryParam;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillGoodsVO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDTO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;
import java.util.Map;

/**
 * Flash sale application business layer
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 17:30:09
 */
public interface SeckillGoodsManager {

    /**
     * Query the flash sale application list
     *
     * @param queryParam Query parameters
     * @return Page
     */
    Page list(SeckillQueryParam queryParam);


    /**
     * Delete the flash sale application
     *
     * @param id Flash purchase application main key
     */
    void delete(Integer id);

    /**
     * Get a flash sale application
     *
     * @param id Flash purchase application main key
     * @return SeckillApply  Flash sale application
     */
    SeckillApplyDO getModel(Integer id);


    /**
     * Add flash sale application
     *
     * @param list
     */
    void addApply(List<SeckillApplyDO> list);

    /**
     * Increase the quantity of stock sold
     *
     * @param promotionDTOList
     * @return
     */
    boolean addSoldNum(List<PromotionDTO> promotionDTOList);

    /**
     * Read items from the flash sale of the day
     *
     * @return
     */
    Map<Integer, List<SeckillGoodsVO>> getSeckillGoodsList();

    /**
     * Read the flash sale list according to the time
     *
     * @param rangeTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    List getSeckillGoodsList(Integer rangeTime, Integer pageNo, Integer pageSize);

    /**
     * Roll back the inventory
     *
     * @param promotionDTOList
     */
    void rollbackStock(List<PromotionDTO> promotionDTOList);

    /**
     * Query for active items under a flash sale
     *
     * @param id
     * @return
     */
    List<SeckillApplyDO> getListBySeckill(Integer id);

    /**
     * Delete flash sale items
     *
     * @param goodsId
     */
    void deleteSeckillGoods(Integer goodsId);
}
