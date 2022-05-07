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

import cloud.shopfly.b2c.core.pagedata.model.PageData;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Floor Service floor
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-21 16:39:22
 */
public interface PageDataManager {

	/**
	 * Querying a Floor List
	 * @param page The page number
	 * @param pageSize Number each page
	 * @return Page
	 */
	Page list(int page, int pageSize);
	/**
	 * Add the floor
	 * @param page floor
	 * @return PageData floor
	 */
	PageData add(PageData page);

	/**
	* Modify the floor
	* @param page floor
	* @param id Floor is a primary key
	* @return PageData floor
	*/
	PageData edit(PageData page, Integer id);
	
	/**
	 * Delete the floor
	 * @param id Floor is a primary key
	 */
	void delete(Integer id);
	
	/**
	 * Access to the floor
	 * @param id Floor is a primary key
	 * @return PageData  floor
	 */
	PageData getModel(Integer id);

	/**
	 * Query data
	 * @return
	 * @param clientType
	 * @param pageType
	 */
    PageData queryPageData(String clientType, String pageType);

	/**
	 * Modify the floor according to the type
	 * @param pageData
	 * @return
	 */
	PageData editByType(PageData pageData);

	/**
	 * Query data by type
	 * @param clientType
	 * @param pageType
	 * @return
	 */
	PageData getByType(String clientType, String pageType);
}
