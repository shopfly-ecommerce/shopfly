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

import cloud.shopfly.b2c.core.goods.model.dos.ParametersDO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Parameter business layer
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 16:14:31
 */
public interface ParametersManager {

	/**
	 * Querying the Parameter List
	 * 
	 * @param page
	 *            The page number
	 * @param pageSize
	 *            Number each page
	 * @return Page
	 */
	Page list(int page, int pageSize);

	/**
	 * Add parameters
	 * 
	 * @param parameters
	 *            parameter
	 * @return Parameters parameter
	 */
	ParametersDO add(ParametersDO parameters);

	/**
	 * Modify the parameters
	 * 
	 * @param parameters
	 *            parameter
	 * @param id
	 *            Parameter the primary key
	 * @return Parameters parameter
	 */
	ParametersDO edit(ParametersDO parameters, Integer id);

	/**
	 * Delete the parameter
	 * 
	 * @param id
	 *            Parameter the primary key
	 */
	void delete(Integer id);

	/**
	 * To obtain parameters
	 * 
	 * @param id
	 *            Parameter the primary key
	 * @return Parameters parameter
	 */
	ParametersDO getModel(Integer id);

	/**
	 * Parameters of the sort
	 * 
	 * @param paramId
	 * @param sortType
	 */
	void paramSort(Integer paramId, String sortType);

	/**
	 * Delete parameters and use parameter groups
	 * 
	 * @param groupId
	 */
	void deleteByGroup(Integer groupId);

}
