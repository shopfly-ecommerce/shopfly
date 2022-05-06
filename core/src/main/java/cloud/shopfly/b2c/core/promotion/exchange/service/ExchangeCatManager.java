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
package cloud.shopfly.b2c.core.promotion.exchange.service;

import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeCat;

import java.util.List;

/**
 * 积分兑换分类业务层
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-29 16:56:22
 */
public interface ExchangeCatManager	{

	/**
	 * 查询积分兑换分类列表
	 * @param parentId 父ID
	 * @return Page
	 */
	List<ExchangeCat> list(Integer parentId);

	/**
	 * 添加积分兑换分类
	 * @param exchangeCat 积分兑换分类
	 * @return ExchangeCat 积分兑换分类
	 */
	ExchangeCat add(ExchangeCat exchangeCat);

	/**
	* 修改积分兑换分类
	* @param exchangeCat 积分兑换分类
	* @param id 积分兑换分类主键
	* @return ExchangeCat 积分兑换分类
	*/
	ExchangeCat edit(ExchangeCat exchangeCat, Integer id);

	/**
	 * 删除积分兑换分类
	 * @param id 积分兑换分类主键
	 */
	void delete(Integer id);

	/**
	 * 获取积分兑换分类
	 * @param id 积分兑换分类主键
	 * @return ExchangeCat  积分兑换分类
	 */
	ExchangeCat getModel(Integer id);

}
