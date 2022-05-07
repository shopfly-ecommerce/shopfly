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
 * Transaction snapshot business layer
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-01 14:55:26
 */
public interface GoodsSnapshotManager	{

	/**
	 * Example Query the trade snapshot list
	 * @param page The page number
	 * @param pageSize Number each page
	 * @return Page 
	 */
	Page list(int page, int pageSize);
	/**
	 * Adding a Transaction Snapshot
	 * @param goodsSnapshot Trading snapshot
	 * @return GoodsSnapshot Trading snapshot
	 */
	GoodsSnapshot add(GoodsSnapshot goodsSnapshot);

	/**
	* Modifying a trade Snapshot
	* @param goodsSnapshot Trading snapshot
	* @param id Transaction flash key
	* @return GoodsSnapshot Trading snapshot
	*/
	GoodsSnapshot edit(GoodsSnapshot goodsSnapshot, Integer id);
	
	/**
	 * Deleting a Transaction Snapshot
	 * @param id Transaction flash key
	 */
	void delete(Integer id);
	
	/**
	 * Get a trade snapshot
	 * @param id Transaction flash key
	 * @return GoodsSnapshot  Trading snapshot
	 */
	GoodsSnapshot getModel(Integer id);

	/**
	 * Adding a Transaction Snapshot
	 * @param orderDO
	 */
	void add(OrderDO orderDO);

	/**
	 * Query the snapshotVO
	 * @param id
	 * @return
	 */
    SnapshotVO get(Integer id);
}
