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
 * Group purchase commodity inventory log table business layer
 * @author xlp
 * @version v1.0
 * @since v7.0.0
 * 2018-07-09 15:32:29
 */
public interface GroupbuyQuantityLogManager {

	/**
	 * Restore group purchase inventory
	 * @param orderSn Order no.
	 * @return Group purchase cancellation order log
	 */
	List<GroupbuyQuantityLog> rollbackReduce(String orderSn);

	/**
	 * Add group purchase inventory log table
	 * @param groupbuyQuantityLog Group purchase inventory log sheet
	 * @return Group purchase inventory log sheet
	 */
	GroupbuyQuantityLog add(GroupbuyQuantityLog groupbuyQuantityLog);



}
