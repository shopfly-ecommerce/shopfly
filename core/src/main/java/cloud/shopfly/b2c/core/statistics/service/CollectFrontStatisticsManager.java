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
package cloud.shopfly.b2c.core.statistics.service;

import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.framework.database.Page;

/**
* 商家中心，商品收藏统计
*
* @author mengyuanming
* @version 2.0
* @since 7.0 
* 2018年4月20日下午4:23:58
*/
public interface CollectFrontStatisticsManager {
	
	/**
	 * 商品收藏图表数据
	 *
	 * @return SimpleChart，简单图表数据
	 */
	SimpleChart getChart();
	
	/**
	 * 商品收藏列表数据
	 * @param pageNo，页码
	 * @param pageSize，页面数据量
	 * @return Page，分页数据
	 */
	Page getPage(Integer pageNo, Integer pageSize);

}
