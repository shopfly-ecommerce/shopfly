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
package cloud.shopfly.b2c.core.trade.snapshot.service;

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.snapshot.model.GoodsSnapshot;
import cloud.shopfly.b2c.core.trade.snapshot.model.SnapshotVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 交易快照业务层
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-01 14:55:26
 */
public interface GoodsSnapshotManager	{

	/**
	 * 查询交易快照列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @return Page 
	 */
	Page list(int page, int pageSize);
	/**
	 * 添加交易快照
	 * @param goodsSnapshot 交易快照
	 * @return GoodsSnapshot 交易快照
	 */
	GoodsSnapshot add(GoodsSnapshot goodsSnapshot);

	/**
	* 修改交易快照
	* @param goodsSnapshot 交易快照
	* @param id 交易快照主键
	* @return GoodsSnapshot 交易快照
	*/
	GoodsSnapshot edit(GoodsSnapshot goodsSnapshot, Integer id);
	
	/**
	 * 删除交易快照
	 * @param id 交易快照主键
	 */
	void delete(Integer id);
	
	/**
	 * 获取交易快照
	 * @param id 交易快照主键
	 * @return GoodsSnapshot  交易快照
	 */
	GoodsSnapshot getModel(Integer id);

	/**
	 * 添加交易快照
	 * @param orderDO
	 */
	void add(OrderDO orderDO);

	/**
	 * 查询快照VO
	 * @param id
	 * @return
	 */
    SnapshotVO get(Integer id);
}