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
package cloud.shopfly.b2c.core.trade.order.support;

import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kingapex on 2019-02-12.
 * Order special statustextTo deal with
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-12
 */
public abstract class AbstractOrderSpecialStatus {


    /**
     * Define a special process status display
     */
    private static Map<String, String> map = new HashMap<>(16);

    static {

        // The group is ready for delivery
        map.put(OrderTypeEnum.pintuan + "_" + PaymentTypeEnum.ONLINE + "_" + OrderStatusEnum.FORMED, "To send the goods");

        // General order online payment, payment has been shown as waiting for delivery
        map.put(OrderTypeEnum.normal + "_" + PaymentTypeEnum.ONLINE + "_" + OrderStatusEnum.PAID_OFF, "To send the goods");

        // Ordinary orders paid online have been confirmed and displayed as pending payment
        map.put(OrderTypeEnum.normal + "_" + PaymentTypeEnum.ONLINE + "_" + OrderStatusEnum.CONFIRM, "For the payment");

        // For general orders paid on delivery, confirmed goods are displayed as goods to be shipped
        map.put(OrderTypeEnum.normal + "_" + PaymentTypeEnum.COD + "_" + OrderStatusEnum.CONFIRM, "To send the goods");
    }


    /**
     * Get special statetext
     *
     * @param orderType   Order type
     * @param paymentType Payment type
     * @param orderStatus Status
     * @return
     */
    public static String getStatusText(String orderType, String paymentType, String orderStatus) {
        return map.get(orderType + "_" + paymentType + "_" + orderStatus);
    }

}
