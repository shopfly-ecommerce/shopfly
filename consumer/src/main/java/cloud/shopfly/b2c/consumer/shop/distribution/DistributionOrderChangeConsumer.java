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
package cloud.shopfly.b2c.consumer.shop.distribution;

import cloud.shopfly.b2c.consumer.core.event.OrderStatusChangeEvent;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.client.distribution.DistributionOrderClient;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Distributor Order processing
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/14 In the morning7:13
 */

@Component
public class DistributionOrderChangeConsumer implements OrderStatusChangeEvent {

    @Autowired
    private DistributionOrderClient distributionOrderClient;

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Override
    public void orderChange(OrderStatusChangeMsg orderStatusChangeMsg) {
        OrderDO order = orderStatusChangeMsg.getOrderDO();
        try {
            if (orderStatusChangeMsg.getNewStatus().equals(OrderStatusEnum.ROG)) {
                distributionOrderClient.confirm(order);
            }
        } catch (Exception e) {
            logger.error("Order collection distribution calculation rebate is abnormal：", e);
        }
    }

}
