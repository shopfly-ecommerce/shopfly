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
package cloud.shopfly.b2c.core.promotion.seckill.service;

import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillRangeDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.TimeLineVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Flash sale moment business layer
 * @author Snow
 * @version v2.0.0
 * @since v7.0.0
 * 2018-04-02 18:24:47
 */
public interface SeckillRangeManager	{

	/**
	 * Query the flash sale time list
	 * @param page The page number
	 * @param pageSize Number each page
	 * @return Page
	 */
	Page list(int page, int pageSize);


	/**
	* Modify flash sale moments
	* @param seckillRange Flash sale time
	* @param id Flash sale time primary key
	* @return SeckillRange Flash sale time
	*/
	SeckillRangeDO edit(SeckillRangeDO seckillRange, Integer id);

	/**
	 * Delete flash sale moments
	 * @param id Flash sale time primary key
	 */
	void delete(Integer id);

	/**
	 * Get a flash sale moment
	 * @param id Flash sale time primary key
	 * @return SeckillRange  Flash sale time
	 */
	SeckillRangeDO getModel(Integer id);

	/**
	 * According to the set of time, storage
	 * @param list
	 * @param seckillId
	 */
	void addList(List<Integer> list, Integer seckillId);

	/**
	 * According to a flash saleID, reads the moment collection
	 * @param seckillId
	 * @return
	 */
	List<SeckillRangeDO> getList(Integer seckillId);

	/**
	 * Read the list of current seconds
	 * @return
	 */
	List<TimeLineVO> readTimeList();

}
