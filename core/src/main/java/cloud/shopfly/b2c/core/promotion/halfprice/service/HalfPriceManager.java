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
package cloud.shopfly.b2c.core.promotion.halfprice.service;

import cloud.shopfly.b2c.core.promotion.halfprice.model.dos.HalfPriceDO;
import cloud.shopfly.b2c.core.promotion.halfprice.model.vo.HalfPriceVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * The second half price business layer
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 19:53:42
 */
public interface HalfPriceManager	{

	/**
	 * Check the second half price list
	 * @param page The page number
	 * @param pageSize Number each page
	 * @param keywords keyword
	 * @return Page
	 */
	Page list(int page, int pageSize, String keywords);
	/**
	 * Add a second piece for half price
	 * @param halfPrice The second one is half price
	 * @return HalfPrice The second one is half price
	 */
	HalfPriceDO add(HalfPriceVO halfPrice);

	/**
	* Modify the second half price
	* @param halfPrice The second one is half price
	* @param id The second half-price primary key
	* @return HalfPrice The second one is half price
	*/
	HalfPriceDO edit(HalfPriceVO halfPrice, Integer id);

	/**
	 * Delete the second piece at half price
	 * @param id The second half-price primary key
	 */
	void delete(Integer id);

	/**
	 * Get the second half price
	 * @param id The second half-price primary key
	 * @return HalfPrice  The second one is half price
	 */
	HalfPriceVO getFromDB(Integer id);

	/**
	 * Verifying operation rights<br/>
	 * Throw a permission exception if there is a problem
	 * @param id
	 */
	void verifyAuth(Integer id);

}
