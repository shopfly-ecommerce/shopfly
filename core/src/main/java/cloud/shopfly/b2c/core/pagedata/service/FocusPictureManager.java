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
package cloud.shopfly.b2c.core.pagedata.service;

import cloud.shopfly.b2c.core.pagedata.model.FocusPicture;

import java.util.List;

/**
 * Focus map business layer
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-21 15:23:23
 */
public interface FocusPictureManager {

	/**
	 * Query the list of focus graphs
	 * @param clientType Client type
	 * @return List
	 */
	List list(String clientType);
	/**
	 * Add focus diagram
	 * @param cmsFocusPicture Focus figure
	 * @return FocusPicture Focus figure
	 */
	FocusPicture add(FocusPicture cmsFocusPicture);

	/**
	* Modify focus diagram
	* @param cmsFocusPicture Focus figure
	* @param id Focus graph primary key
	* @return FocusPicture Focus figure
	*/
	FocusPicture edit(FocusPicture cmsFocusPicture, Integer id);
	
	/**
	 * Delete focus diagram
	 * @param id Focus graph primary key
	 */
	void delete(Integer id);
	
	/**
	 * Get focus map
	 * @param id Focus graph primary key
	 * @return FocusPicture  Focus figure
	 */
	FocusPicture getModel(Integer id);

}
