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
package cloud.shopfly.b2c.core.promotion.fulldiscount.service;

import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.goods.model.enums.QuantityType;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Full preferential gift business layer
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 17:34:46
 */
public interface FullDiscountGiftManager	{

	/**
	 * Check the full discount gift list
	 * @param page The page number
	 * @param pageSize Number each page
	 * @param keyword keyword
	 * @return Page
	 */
	Page list(int page, int pageSize, String keyword);

	/**
	 * Add full discount gift
	 * @param fullDiscountGift Full discount free gift
	 * @return FullDiscountGift Full discount free gift
	 */
	FullDiscountGiftDO add(FullDiscountGiftDO fullDiscountGift);

	/**
	 * Modify full discount gifts
	 * @param fullDiscountGift Full discount free gift
	 * @param id Full discount gift master key
	 * @return FullDiscountGift Full discount free gift
	 */
	FullDiscountGiftDO edit(FullDiscountGiftDO fullDiscountGift, Integer id);

	/**
	 * Delete full discount gifts
	 * @param id Full discount gift master key
	 */
	void delete(Integer id);

	/**
	 * Get full discount gift
	 * @param id Full discount gift master key
	 * @return FullDiscountGift  Full discount free gift
	 */
	FullDiscountGiftDO getModel(Integer id);


	/**
	 * Verifying operation rights<br/>
	 * Throw a permission exception if there is a problem
	 * @param id
	 */
	void verifyAuth(Integer id);

	/**
	 * Increase your gift inventory
	 * @param giftList
	 * @return
	 */
	boolean addGiftQuantity(List<FullDiscountGiftDO> giftList);

	/**
	 * Increase the inventory available for freebies
	 * @param giftList
	 * @return
	 */
	boolean addGiftEnableQuantity(List<FullDiscountGiftDO> giftList);


	/**
	 * Reduce your gift inventory
	 * @param giftList
	 * @param type
	 * @return
	 */
	boolean reduceGiftQuantity(List<FullDiscountGiftDO> giftList, QuantityType type);

	/**
	 * Get a collection of all of the merchants gift data
	 * @return
	 */
	List<FullDiscountGiftDO> listAll();
}
