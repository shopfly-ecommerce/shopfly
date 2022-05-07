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
package cloud.shopfly.b2c.core.base.service;

import cloud.shopfly.b2c.core.base.model.dos.SensitiveWords;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Sensitive word business layer
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-02 11:30:59
 */
public interface SensitiveWordsManager {

	/**
	 * Example Query the list of sensitive words
	 * @param page The page number
	 * @param pageSize Number each page
	 * @param keyword
     * @return Page
	 */
	Page list(int page, int pageSize, String keyword);
	/**
	 * Add sensitive words
	 * @param sensitiveWords Sensitive words
	 * @return SensitiveWords Sensitive words
	 */
	SensitiveWords add(SensitiveWords sensitiveWords);

	/**
	* Modify sensitive words
	* @param sensitiveWords Sensitive words
	* @param id Sensitive word primary key
	* @return SensitiveWords Sensitive words
	*/
	SensitiveWords edit(SensitiveWords sensitiveWords, Integer id);
	
	/**
	 * Delete sensitive words
	 * @param id Sensitive word primary key
	 */
	void delete(Integer id);
	
	/**
	 * Get sensitive words
	 * @param id Sensitive word primary key
	 * @return SensitiveWords  Sensitive words
	 */
	SensitiveWords getModel(Integer id);

	/**
	 * Query sensitive terms to be filtered
	 * @return
	 */
	List<String> listWords();

}
