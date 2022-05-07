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
package cloud.shopfly.b2c.core.promotion.exchange.service;

import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import cloud.shopfly.b2c.core.promotion.exchange.model.dto.ExchangeQueryParam;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Points commodity business layer
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 11:47:18
 */
public interface ExchangeGoodsManager {


	/**
	 * Query the list of credits
	 * @param param
	 * @return
	 */
	Page list(ExchangeQueryParam param);

	/**
	 * Add point redemption
	 * @param exchangeSetting Status
	 * @param goodsDTO productDTO
	 * @return ExchangeSetting Status*/
	ExchangeDO add(ExchangeDO exchangeSetting, PromotionGoodsDTO goodsDTO);

	/**
	* Modified point redemption
	* @param exchangeSetting Status
	* @param goodsDTO productDTO
	* @return ExchangeSetting Status
	*/
	ExchangeDO edit(ExchangeDO exchangeSetting, PromotionGoodsDTO goodsDTO);

	/**
	 * Delete Points redemption
	 * @param id Points are converted to primary keys
	 */
	void delete(Integer id);

	/**
	 * Get points for redemption
	 * @param id Points are converted to primary keys
	 * @return ExchangeSetting  Status
	 */
	ExchangeDO getModel(Integer id);

	/**
	 * Query information about a product
	 * @param goodsId
	 * @return
	 */
    ExchangeDO getModelByGoods(Integer goodsId);

	/**
	 * Delete credit information for an item
	 * @param goodsId
	 */
	void deleteByGoods(Integer goodsId);

	/**
	 * Query the point redemption information for a point category
	 * @param categoryId
	 * @return
	 */
	List<ExchangeDO> getModelByCategoryId(Integer categoryId);
}
