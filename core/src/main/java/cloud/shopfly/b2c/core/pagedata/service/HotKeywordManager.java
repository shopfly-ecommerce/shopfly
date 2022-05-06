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
 * 热门关键字业务层
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-04 10:43:23
 */
public interface HotKeywordManager {

	/**
	 * 查询热门关键字列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @return Page 
	 */
	Page list(int page, int pageSize);
	/**
	 * 添加热门关键字
	 * @param hotKeyword 热门关键字
	 * @return HotKeyword 热门关键字
	 */
	HotKeyword add(HotKeyword hotKeyword);

	/**
	* 修改热门关键字
	* @param hotKeyword 热门关键字
	* @param id 热门关键字主键
	* @return HotKeyword 热门关键字
	*/
	HotKeyword edit(HotKeyword hotKeyword, Integer id);
	
	/**
	 * 删除热门关键字
	 * @param id 热门关键字主键
	 */
	void delete(Integer id);
	
	/**
	 * 获取热门关键字
	 * @param id 热门关键字主键
	 * @return HotKeyword  热门关键字
	 */
	HotKeyword getModel(Integer id);

	/**
	 * 查询热门关键字
	 * @param num
	 * @return
	 */
	List<HotKeyword> listByNum(Integer num);
}