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
 * 团购分类业务层
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 16:08:03
 */
public interface GroupbuyCatManager {

	/**
	 * 读取团购分类——分页
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page list(Integer pageNo, Integer pageSize);

	/**
	 * 查询团购分类列表
	 * @param parentId 分类父id
	 * @return Page
	 */
	List<GroupbuyCatDO>  getList(Integer parentId);

	/**
	 * 添加团购分类
	 * @param groupbuyCat 团购分类
	 * @return GroupbuyCat 团购分类
	 */
	GroupbuyCatDO add(GroupbuyCatDO groupbuyCat);

	/**
	* 修改团购分类
	* @param groupbuyCat 团购分类
	* @param id 团购分类主键
	* @return GroupbuyCat 团购分类
	*/
	GroupbuyCatDO edit(GroupbuyCatDO groupbuyCat, Integer id);

	/**
	 * 删除团购分类
	 * @param id 团购分类主键
	 */
	void delete(Integer id);

	/**
	 * 获取团购分类
	 * @param id 团购分类主键
	 * @return GroupbuyCat  团购分类
	 */
	GroupbuyCatDO getModel(Integer id);

}
