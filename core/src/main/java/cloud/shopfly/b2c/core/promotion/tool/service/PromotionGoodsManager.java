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
 * 活动商品对照接口
 *
 * @author Snow create in 2018/3/21
 * @version v2.0
 * @since v7.0.0
 */
public interface PromotionGoodsManager {

    /**
     * 添加活动商品对照表
     *
     * @param list      商品列表
     * @param detailDTO 活动详情
     */
    void add(List<PromotionGoodsDTO> list, PromotionDetailDTO detailDTO);

    /**
     * 添加单个商品对照
     * @param goodsId
     * @param detailDTO
     * @return
     */
    PromotionGoodsDO addModel(Integer goodsId, PromotionDetailDTO detailDTO);


    /**
     * 修改活动商品对照表
     *
     * @param list
     * @param detailDTO 活动详情
     */
    void edit(List<PromotionGoodsDTO> list, PromotionDetailDTO detailDTO);

    /**
     * 根据商品id删除活动(正在进行中或者未开始的促销活动)
     * @param goodsId
     */
    void delete(Integer goodsId);

    /**
     * 根据活动id和活动工具类型删除活动商品对照表
     *
     * @param activityId
     * @param promotionType
     */
    void delete(Integer activityId, String promotionType);

    /**
     * 根据活动id,活动工具类型和商品id删除活动商品对照表
     *
     * @param goodsId       商品id
     * @param activityId    活动id
     * @param promotionType 活动类型
     */
    void delete(Integer goodsId, Integer activityId, String promotionType);


    /**
     * 根据活动ID和活动类型查出参与此活动的所有商品
     *
     * @param activityId
     * @param promotionType
     * @return
     */
    List<PromotionGoodsDO> getPromotionGoods(Integer activityId, String promotionType);

    /**
     * 根据商品id读取商品参与的所有活动（有效的活动）
     *
     * @param goodsId
     * @return 返回活动的集合
     */
    List<PromotionVO> getPromotion(Integer goodsId);

    /**
     * 清空key
     *
     * @param goodsId
     */
    void cleanCache(Integer goodsId);

    /**
     * 重新写入缓存
     *
     * @param goodsId
     */
    void reputCache(Integer goodsId);
}
