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

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyCatDO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Group purchase classification business layer
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 16:08:03
 */
public interface GroupbuyCatManager {

	/**
	 * Read the group purchase category——paging
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page list(Integer pageNo, Integer pageSize);

	/**
	 * Query the group purchase category list
	 * @param parentId Classification of the fatherid
	 * @return Page
	 */
	List<GroupbuyCatDO>  getList(Integer parentId);

	/**
	 * Add group Purchase category
	 * @param groupbuyCat Group classification
	 * @return GroupbuyCat Group classification
	 */
	GroupbuyCatDO add(GroupbuyCatDO groupbuyCat);

	/**
	* Modify group purchase classification
	* @param groupbuyCat Group classification
	* @param id Group purchase classification key
	* @return GroupbuyCat Group classification
	*/
	GroupbuyCatDO edit(GroupbuyCatDO groupbuyCat, Integer id);

	/**
	 * Delete group purchase category
	 * @param id Group purchase classification key
	 */
	void delete(Integer id);

	/**
	 * Get group-buying categories
	 * @param id Group purchase classification key
	 * @return GroupbuyCat  Group classification
	 */
	GroupbuyCatDO getModel(Integer id);

}
