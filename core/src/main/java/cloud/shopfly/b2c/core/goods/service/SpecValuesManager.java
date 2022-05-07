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

import cloud.shopfly.b2c.core.goods.model.dos.SpecValuesDO;
import cloud.shopfly.b2c.core.goods.model.enums.Permission;

import java.util.List;

/**
 * Specification value business layer
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 10:23:53
 */
public interface SpecValuesManager {

	/**
	 * Add value
	 * 
	 * @param specValues
	 *            values
	 * @return SpecValues values
	 */
	SpecValuesDO add(SpecValuesDO specValues);

	/**
	 * values
	 * 
	 * @param specValues
	 *            values
	 * @param id
	 *            Specification value primary key
	 * @return SpecValues values
	 */
	SpecValuesDO edit(SpecValuesDO specValues, Integer id);

	/**
	 * Get specification value
	 * 
	 * @param id
	 *            Specification value primary key
	 * @return SpecValues values
	 */
	SpecValuesDO getModel(Integer id);

	/**
	 * Gets the value of a specification
	 * 
	 * @param specId
	 * @param permission
	 * @return
	 */
	List<SpecValuesDO> listBySpecId(Integer specId, Permission permission);

	/**
	 * Add a specification value
	 * 
	 * @param specId
	 * @param valueList
	 * @return
	 */
	List<SpecValuesDO> saveSpecValue(Integer specId, String[] valueList);

}
