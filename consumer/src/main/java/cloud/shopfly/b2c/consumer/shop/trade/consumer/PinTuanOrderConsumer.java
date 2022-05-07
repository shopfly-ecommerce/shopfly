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
package cloud.shopfly.b2c.consumer.shop.trade.consumer;

import cloud.shopfly.b2c.consumer.core.event.OrderStatusChangeEvent;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanOrderManager;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by kingapex on 2019-01-25.
 * Group order consumers<br/>
 * If it is a group order, check whether the corresponding group activity has been successful<br/>
 * If successful, the corresponding data is updated
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-25
 */
@Component
public class PinTuanOrderConsumer implements OrderStatusChangeEvent {

    @Autowired
    private PintuanOrderManager pintuanOrderManager;

    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        // For paid orders
        if (orderMessage.getNewStatus().equals(OrderStatusEnum.PAID_OFF)) {
            OrderDO orderDO = orderMessage.getOrderDO();
            if (orderDO.getOrderType().equals(OrderTypeEnum.pintuan.name())) {
                pintuanOrderManager.payOrder(orderDO);
            }

        }

    }
}
