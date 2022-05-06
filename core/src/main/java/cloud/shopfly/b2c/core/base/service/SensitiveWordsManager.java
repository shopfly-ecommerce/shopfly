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
 * 敏感词业务层
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-02 11:30:59
 */
public interface SensitiveWordsManager {

	/**
	 * 查询敏感词列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @param keyword
     * @return Page
	 */
	Page list(int page, int pageSize, String keyword);
	/**
	 * 添加敏感词
	 * @param sensitiveWords 敏感词
	 * @return SensitiveWords 敏感词
	 */
	SensitiveWords add(SensitiveWords sensitiveWords);

	/**
	* 修改敏感词
	* @param sensitiveWords 敏感词
	* @param id 敏感词主键
	* @return SensitiveWords 敏感词
	*/
	SensitiveWords edit(SensitiveWords sensitiveWords, Integer id);
	
	/**
	 * 删除敏感词
	 * @param id 敏感词主键
	 */
	void delete(Integer id);
	
	/**
	 * 获取敏感词
	 * @param id 敏感词主键
	 * @return SensitiveWords  敏感词
	 */
	SensitiveWords getModel(Integer id);

	/**
	 * 查询需要过滤的敏感词汇
	 * @return
	 */
	List<String> listWords();

}