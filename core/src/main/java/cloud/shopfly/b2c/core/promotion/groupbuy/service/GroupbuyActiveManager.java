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

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 团购活动表业务层
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 11:52:14
 */
public interface GroupbuyActiveManager	{

	/**
	 * 查询团购活动表列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @return Page
	 */
	Page list(int page, int pageSize);

	/**
	 * 添加团购活动表
	 * @param groupbuyActive 团购活动表
	 * @return GroupbuyActive 团购活动表
	 */
	GroupbuyActiveDO add(GroupbuyActiveDO groupbuyActive);

	/**
	* 修改团购活动表
	* @param groupbuyActive 团购活动表
	* @param id 团购活动表主键
	* @return GroupbuyActive 团购活动表
	*/
	GroupbuyActiveDO edit(GroupbuyActiveDO groupbuyActive, Integer id);

	/**
	 * 删除团购活动表
	 * @param id 团购活动表主键
	 */
	void delete(Integer id);

	/**
	 * 获取团购活动表
	 * @param id 团购活动表主键
	 * @return GroupbuyActive  团购活动表
	 */
	GroupbuyActiveDO getModel(Integer id);


	/**
	 * 读取正在进行的活动列表
	 * @return
	 */
	List<GroupbuyActiveDO> getActiveList();


	/**
	 * 验证操作权限<br/>
	 * 如有问题直接抛出权限异常
	 * @param id
	 */
	void verifyAuth(Integer id);

}
