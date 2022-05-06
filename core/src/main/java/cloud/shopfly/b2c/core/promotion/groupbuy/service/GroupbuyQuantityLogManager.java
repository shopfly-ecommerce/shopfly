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

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyQuantityLog;

import java.util.List;

/**
 * 团购商品库存日志表业务层
 * @author xlp
 * @version v1.0
 * @since v7.0.0
 * 2018-07-09 15:32:29
 */
public interface GroupbuyQuantityLogManager {

	/**
	 * 还原团购库存
	 * @param orderSn 订单编号
	 * @return 团购取消订单日志
	 */
	List<GroupbuyQuantityLog> rollbackReduce(String orderSn);

	/**
	 * 添加团购商品库存日志表
	 * @param groupbuyQuantityLog 团购商品库存日志表
	 * @return 团购商品库存日志表
	 */
	GroupbuyQuantityLog add(GroupbuyQuantityLog groupbuyQuantityLog);



}
