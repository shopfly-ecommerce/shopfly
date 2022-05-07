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
package cloud.shopfly.b2c.core.client.statistics.impl;

import cloud.shopfly.b2c.core.client.statistics.OrderDataClient;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.statistics.service.OrderDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * OrderDataClientDefaultImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 In the afternoon2:41
 */

@Service
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
public class OrderDataClientDefaultImpl implements OrderDataClient {

    @Autowired
    private OrderDataManager orderDataManager;
    /**
     * Orders for new
     *
     * @param order
     */
    @Override
    public void put(OrderDO order) {
        orderDataManager.put(order);
    }

    /**
     * Order to modify
     *
     * @param order
     */
    @Override
    public void change(OrderDO order) {
        orderDataManager.change(order);
    }
}
