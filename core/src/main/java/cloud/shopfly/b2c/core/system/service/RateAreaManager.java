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
package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.RateAreaDO;
import cloud.shopfly.b2c.core.system.model.vo.RateAreaVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 区域相关接口
 * @author cs
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-28 21:44:49
 */
public interface RateAreaManager {


	/**
	 * 区域列表
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page list(String name, Integer pageNo, Integer pageSize);

	/**
	 * 新增区域
	 * @param rateAreaVO
	 * @return
	 */
	RateAreaDO add(RateAreaVO rateAreaVO);


	/**
	 * 修改区域
	 * @param rateAreaVO
	 * @return
	 */
	RateAreaDO edit(RateAreaVO rateAreaVO);

	/**
	 * 删除区域
	 * @param rateAreaId
	 */
	void delete(Integer rateAreaId);

	/**
	 * 查询区域详情
	 * @param rateAreaId
	 * @return
	 */
	RateAreaVO getFromDB(Integer rateAreaId);
}