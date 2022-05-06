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
 * 焦点图业务层
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-21 15:23:23
 */
public interface FocusPictureManager {

	/**
	 * 查询焦点图列表
	 * @param clientType 客户端类型
	 * @return List
	 */
	List list(String clientType);
	/**
	 * 添加焦点图
	 * @param cmsFocusPicture 焦点图
	 * @return FocusPicture 焦点图
	 */
	FocusPicture add(FocusPicture cmsFocusPicture);

	/**
	* 修改焦点图
	* @param cmsFocusPicture 焦点图
	* @param id 焦点图主键
	* @return FocusPicture 焦点图
	*/
	FocusPicture edit(FocusPicture cmsFocusPicture, Integer id);
	
	/**
	 * 删除焦点图
	 * @param id 焦点图主键
	 */
	void delete(Integer id);
	
	/**
	 * 获取焦点图
	 * @param id 焦点图主键
	 * @return FocusPicture  焦点图
	 */
	FocusPicture getModel(Integer id);

}