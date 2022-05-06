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
import cloud.shopfly.b2c.core.base.SearchCriteria;

/**
 * 商家中心与平台后台，流量分析
 * 
 * @author mengyuanming
 * @version 2.0
 * @since 7.0 
 * 2018年3月19日上午8:36:28
 */
public interface PageViewStatisticManager {

	/**
	 * 平台后台 查询商品访问量
	 * 
	 * @param searchCriteria，流量参数类
	 * @return 访问流量前30的商品名及流量数据
	 */
	SimpleChart countGoods(SearchCriteria searchCriteria);

}
