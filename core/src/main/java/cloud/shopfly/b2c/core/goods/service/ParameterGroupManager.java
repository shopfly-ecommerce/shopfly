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
 * 参数组业务层
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 16:14:17
 */
public interface ParameterGroupManager {

	/**
	 * 查询参数组列表
	 * 
	 * @param page
	 *            页码
	 * @param pageSize
	 *            每页数量
	 * @return Page
	 */
	Page list(int page, int pageSize);

	/**
	 * 添加参数组
	 * 
	 * @param parameterGroup
	 *            参数组
	 * @return ParameterGroup 参数组
	 */
	ParameterGroupDO add(ParameterGroupDO parameterGroup);

	/**
	 * 修改参数组
	 * 
	 * @param groupName
	 *            参数组
	 * @param id
	 *            参数组主键
	 * @return ParameterGroup 参数组
	 */
	ParameterGroupDO edit(String groupName, Integer id);

	/**
	 * 删除参数组
	 * 
	 * @param id
	 *            参数组主键
	 */
	void delete(Integer id);

	/**
	 * 获取参数组
	 * 
	 * @param id
	 *            参数组主键
	 * @return ParameterGroup 参数组
	 */
	ParameterGroupDO getModel(Integer id);

	/**
	 * 查询分类关联的参数组，包括参数
	 * 
	 * @param categoryId
	 * @return
	 */
	List<ParameterGroupVO> getParamsByCategory(Integer categoryId);

	/**
	 * 参数组上移或者下移
	 * 
	 * @param groupId
	 * @param sortType
	 *            上移 up，下移down
	 */
	void groupSort(Integer groupId, String sortType);

}