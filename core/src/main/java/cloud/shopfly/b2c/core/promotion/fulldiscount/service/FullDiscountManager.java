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

import cloud.shopfly.b2c.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Full discount activity business layer
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 10:42:06
 */
public interface FullDiscountManager	{

	/**
	 * Check the list of full offers
	 * @param page The page number
	 * @param pageSize Number each page
	 * @param keywords keyword
	 * @return Page
	 */
	Page list(int page, int pageSize, String keywords);

	/**
	 * Add full discount activities
	 * @param fullDiscount Full discount
	 * @return FullDiscount Full discount
	 */
	FullDiscountVO add(FullDiscountVO fullDiscount);

	/**
	* Modify full discount activities
	* @param fullDiscount Full discount
	* @param id Full discount activity main key
	* @return FullDiscount Full discount
	*/
	FullDiscountVO edit(FullDiscountVO fullDiscount, Integer id);

	/**
	 * Delete full discount activities
	 * @param id Full discount activity main key
	 */
	void delete(Integer id);

	/**
	 * Get promotional information from the database
	 * @param fdId Full discountID
	 * @return Full preferential activity entities
	 */
	FullDiscountVO getModel(Integer fdId);


	/**
	 * Verifying operation rights<br/>
	 * Throw a permission exception if there is a problem
	 * @param id
	 */
	void verifyAuth(Integer id);

}
