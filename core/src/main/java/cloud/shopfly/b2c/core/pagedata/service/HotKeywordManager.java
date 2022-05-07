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

import cloud.shopfly.b2c.core.pagedata.model.HotKeyword;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Hot keyword business layer
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-04 10:43:23
 */
public interface HotKeywordManager {

	/**
	 * Example Query the list of popular keywords
	 * @param page The page number
	 * @param pageSize Number each page
	 * @return Page 
	 */
	Page list(int page, int pageSize);
	/**
	 * Add hot keywords
	 * @param hotKeyword Hot keywords
	 * @return HotKeyword Hot keywords
	 */
	HotKeyword add(HotKeyword hotKeyword);

	/**
	* Modify hot keywords
	* @param hotKeyword Hot keywords
	* @param id Hot key primary key
	* @return HotKeyword Hot keywords
	*/
	HotKeyword edit(HotKeyword hotKeyword, Integer id);
	
	/**
	 * Remove hot keywords
	 * @param id Hot key primary key
	 */
	void delete(Integer id);
	
	/**
	 * Get the hot keywords
	 * @param id Hot key primary key
	 * @return HotKeyword  Hot keywords
	 */
	HotKeyword getModel(Integer id);

	/**
	 * Query the popular keywords
	 * @param num
	 * @return
	 */
	List<HotKeyword> listByNum(Integer num);
}
