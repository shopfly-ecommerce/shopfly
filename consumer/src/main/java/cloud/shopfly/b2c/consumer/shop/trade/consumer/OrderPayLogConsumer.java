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
import cloud.shopfly.b2c.core.payment.model.dos.PaymentMethodDO;
import cloud.shopfly.b2c.core.payment.service.PaymentMethodManager;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.PayLog;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PayStatusEnum;
import cloud.shopfly.b2c.core.trade.order.service.PayLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Revise the payment order after the order is paid
 *
 * @author Snow create in 2018/7/23
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class OrderPayLogConsumer implements OrderStatusChangeEvent {

    @Autowired
    private PayLogManager payLogManager;

    @Autowired
    private PaymentMethodManager paymentMethodManager;

    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        // Order paid
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.PAID_OFF.name())) {

            OrderDO orderDO = orderMessage.getOrderDO();
            PayLog payLog = this.payLogManager.getModel(orderDO.getSn());

            // Check payment method
            PaymentMethodDO paymentMethod = this.paymentMethodManager.getByPluginId(orderDO.getPaymentPluginId());
            if (paymentMethod == null) {
                paymentMethod = new PaymentMethodDO();
                paymentMethod.setMethodName("Administrator confirmation of collection");
            }

            payLog.setPayType(paymentMethod.getMethodName());
            payLog.setPayTime(orderDO.getPaymentTime());
            payLog.setPayMoney(orderDO.getPayMoney());
            payLog.setPayStatus(PayStatusEnum.PAY_YES.name());
            payLog.setPayOrderNo(orderDO.getPayOrderNo());

            this.payLogManager.edit(payLog, payLog.getPayLogId());
        }

    }
}
