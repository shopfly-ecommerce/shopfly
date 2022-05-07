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
package cloud.shopfly.b2c.core.promotion.minus.service;

import cloud.shopfly.b2c.core.promotion.minus.model.vo.MinusVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Single product vertical reduction interface
 * @author mengyuanming
 * @version v1.0
 * @since v6.4.0
 * @date 2017years8month18On the afternoon9:20:46
 *
 */
public interface MinusManager {


	/**
	 * According to theidObtain a single item of instant reduction goods
	 * @param minusId Single product reduction activityId
	 * @return MinusVO
	 */
	MinusVO getFromDB(Integer minusId);

	/**
	 * Query the list of items
	 * @param page The page number
	 * @param pageSize Number each page
	 * @param keywords keyword
	 * @return Page
	 */
	Page list(int page, int pageSize, String keywords);

	/**
	 * Add a single item for immediate reduction
	 * @param minus Item set
	 * @return Minus Item set
	 */
	MinusVO add(MinusVO minus);

	/**
	 * Modify the immediate reduction of single products
	 * @param minus Item set
	 * @param id Single product minus primary key
	 * @return Minus Item set
	 */
	MinusVO edit(MinusVO minus, Integer id);

	/**
	 * According to theidDelete a single product immediately reduced goods
	 * @param minusId Single product immediately reduce activity objectid
	 * 1.According to the activityiddeleteesMinusThe data in the
	 * 2.callpromotionGoodsManagerDelete method, deleteesPromotionGoodsData in a table
	 * 3.deleteRedisThe active instance object in
	 *
	 */
	void delete(Integer minusId);


	/**
	 * Verifying operation rights<br/>
	 * Throw a permission exception if there is a problem
	 * @param minusId
	 */
	void verifyAuth(Integer minusId);

}
