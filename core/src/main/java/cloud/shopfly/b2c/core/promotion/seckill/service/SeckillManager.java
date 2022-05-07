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

import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillGoodsVO;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillVO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dto.SeckillDTO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Flash purchase warehousing business layer
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 10:32:36
 */
public interface SeckillManager	{

	/**
	 * Query the flash sale inventory list
	 * @param page The page number
	 * @param pageSize Number each page
	 * @param keywords keyword
	 * @return Page
	 */
	Page list(int page, int pageSize, String keywords);

	/**
	 * Add flash sale to store
	 * @param seckill Flash sale in stock
	 * @return Seckill Flash sale in stock
	 */
	SeckillDTO add(SeckillDTO seckill);

	/**
	* Modify flash sale to store
	* @param seckill Flash sale in stock
	* @param id Flash store main key
	* @return Seckill Flash sale in stock
	*/
	SeckillDTO edit(SeckillDTO seckill, Integer id);

	/**
	 * Delete flash sale to store
	 * @param id Flash store main key
	 */
	void delete(Integer id);

	/**
	 * Get flash sale for storage
	 * @param id Flash store main key
	 * @return Seckill  Flash sale in stock
	 */
	SeckillDTO getModelAndRange(Integer id);

	/**
	 * Get flash sale for storage
	 * @param id Flash store main key
	 * @return Seckill  Flash sale in stock
	 */
	SeckillVO getModelAndApplys(Integer id);


	/**
	 * Get flash sale for storage
	 * @param id Flash store main key
	 * @return Seckill  Flash sale in stock
	 */
	SeckillDO getModel(Integer id);

	/**
	 * According to the goodsID, read the activity information of the timer
	 * @param goodsId
	 * @return
	 */
	SeckillGoodsVO getSeckillGoods(Integer goodsId);


	/**
	 * Review the application
	 * @param applyId	To apply forID
	 */
	void reviewApply(Integer applyId);

	/**
	 * Close a flash sale
	 * @param id
	 */
    void close(Integer id);
}
