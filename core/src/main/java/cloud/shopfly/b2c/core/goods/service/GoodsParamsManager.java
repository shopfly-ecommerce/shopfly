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

import cloud.shopfly.b2c.core.goods.model.dos.GoodsParamsDO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsParamsGroupVO;

import java.util.List;

/**
 * Item parameter association interface
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018years3month21On the afternoon5:29:09
 */
public interface GoodsParamsManager {

    /**
     * Example Modify the parameters of a commodity query category and its association
     *
     * @param categoryId
     * @param goodsId
     * @return
     */
    List<GoodsParamsGroupVO> queryGoodsParams(Integer categoryId, Integer goodsId);

    /**
     * Add the parameters of commodity query categories and commodity association
     *
     * @param categoryId
     * @return
     */
    List<GoodsParamsGroupVO> queryGoodsParams(Integer categoryId);

    /**
     * Add parameters for commodity association
     *
     * @param goodsId   productid
     * @param paramList Parameter collection
     */
    void addParams(List<GoodsParamsDO> paramList, Integer goodsId);


}
