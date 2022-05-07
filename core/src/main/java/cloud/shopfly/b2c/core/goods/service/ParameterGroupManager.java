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

import cloud.shopfly.b2c.core.goods.model.dos.ParameterGroupDO;
import cloud.shopfly.b2c.core.goods.model.vo.ParameterGroupVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Parameter group business layer
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 16:14:17
 */
public interface ParameterGroupManager {

	/**
	 * Example Query the parameter group list
	 * 
	 * @param page
	 *            The page number
	 * @param pageSize
	 *            Number each page
	 * @return Page
	 */
	Page list(int page, int pageSize);

	/**
	 * Add parameter group
	 * 
	 * @param parameterGroup
	 *            Parameter set
	 * @return ParameterGroup Parameter set
	 */
	ParameterGroupDO add(ParameterGroupDO parameterGroup);

	/**
	 * Modify parameter set
	 * 
	 * @param groupName
	 *            Parameter set
	 * @param id
	 *            Parameter group primary key
	 * @return ParameterGroup Parameter set
	 */
	ParameterGroupDO edit(String groupName, Integer id);

	/**
	 * Delete parameter set
	 * 
	 * @param id
	 *            Parameter group primary key
	 */
	void delete(Integer id);

	/**
	 * Get parameter set
	 * 
	 * @param id
	 *            Parameter group primary key
	 * @return ParameterGroup Parameter set
	 */
	ParameterGroupDO getModel(Integer id);

	/**
	 * Example Query the parameter groups associated with a category, including parameters
	 * 
	 * @param categoryId
	 * @return
	 */
	List<ParameterGroupVO> getParamsByCategory(Integer categoryId);

	/**
	 * The parameter group moves up or down
	 * 
	 * @param groupId
	 * @param sortType
	 *             upupMove down,down
	 */
	void groupSort(Integer groupId, String sortType);

}
