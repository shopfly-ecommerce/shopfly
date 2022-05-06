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
 * 参数业务层
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 16:14:31
 */
public interface ParametersManager {

	/**
	 * 查询参数列表
	 * 
	 * @param page
	 *            页码
	 * @param pageSize
	 *            每页数量
	 * @return Page
	 */
	Page list(int page, int pageSize);

	/**
	 * 添加参数
	 * 
	 * @param parameters
	 *            参数
	 * @return Parameters 参数
	 */
	ParametersDO add(ParametersDO parameters);

	/**
	 * 修改参数
	 * 
	 * @param parameters
	 *            参数
	 * @param id
	 *            参数主键
	 * @return Parameters 参数
	 */
	ParametersDO edit(ParametersDO parameters, Integer id);

	/**
	 * 删除参数
	 * 
	 * @param id
	 *            参数主键
	 */
	void delete(Integer id);

	/**
	 * 获取参数
	 * 
	 * @param id
	 *            参数主键
	 * @return Parameters 参数
	 */
	ParametersDO getModel(Integer id);

	/**
	 * 参数排序
	 * 
	 * @param paramId
	 * @param sortType
	 */
	void paramSort(Integer paramId, String sortType);

	/**
	 * 删除参数，使用参数组
	 * 
	 * @param groupId
	 */
	void deleteByGroup(Integer groupId);

}