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
package cloud.shopfly.b2c.consumer.shop.sss;

import cloud.shopfly.b2c.consumer.core.event.OrderStatusChangeEvent;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.client.statistics.OrderDataClient;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Order status changes consumption
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/8 In the afternoon6:44
 */
@Component
public class DataOrderConsumer implements OrderStatusChangeEvent {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());


    @Autowired
    private OrderDataClient orderDataClient;

    @Override
    public void orderChange(OrderStatusChangeMsg orderStatusChangeMsg) {
        try {
            if (orderStatusChangeMsg.getNewStatus().equals(OrderStatusEnum.PAID_OFF)) {
                this.orderDataClient.put(orderStatusChangeMsg.getOrderDO());
            } else if (!orderStatusChangeMsg.getNewStatus().equals(OrderStatusEnum.PAID_OFF)) {
                this.orderDataClient.change(orderStatusChangeMsg.getOrderDO());
            }
        } catch (Exception e) {
            logger.error("The order change message is abnormal:",e);
            e.printStackTrace();
        }
    }

}
