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
package cloud.shopfly.b2c.core.promotion.groupbuy.service;

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Group purchase activity sheet business layer
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 11:52:14
 */
public interface GroupbuyActiveManager	{

	/**
	 * Query group purchase activity list
	 * @param page The page number
	 * @param pageSize Number each page
	 * @return Page
	 */
	Page list(int page, int pageSize);

	/**
	 * Add group purchase activity table
	 * @param groupbuyActive Group buying activity sheet
	 * @return GroupbuyActive Group buying activity sheet
	 */
	GroupbuyActiveDO add(GroupbuyActiveDO groupbuyActive);

	/**
	* Modify the group purchase activity table
	* @param groupbuyActive Group buying activity sheet
	* @param id Group purchase activity table primary key
	* @return GroupbuyActive Group buying activity sheet
	*/
	GroupbuyActiveDO edit(GroupbuyActiveDO groupbuyActive, Integer id);

	/**
	 * Delete the group purchase activity table
	 * @param id Group purchase activity table primary key
	 */
	void delete(Integer id);

	/**
	 * Get group purchase activity sheet
	 * @param id Group purchase activity table primary key
	 * @return GroupbuyActive  Group buying activity sheet
	 */
	GroupbuyActiveDO getModel(Integer id);


	/**
	 * Read a list of ongoing activities
	 * @return
	 */
	List<GroupbuyActiveDO> getActiveList();


	/**
	 * Verifying operation rights<br/>
	 * Throw a permission exception if there is a problem
	 * @param id
	 */
	void verifyAuth(Integer id);

}
