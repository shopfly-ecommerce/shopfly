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
 * 订单特殊状态text处理
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-12
 */
public abstract class AbstractOrderSpecialStatus {


    /**
     * 定义特殊的流程状态显示
     */
    private static Map<String, String> map = new HashMap<>(16);

    static {

        //拼团已经成团的为待发货
        map.put(OrderTypeEnum.pintuan + "_" + PaymentTypeEnum.ONLINE + "_" + OrderStatusEnum.FORMED, "待发货");

        //普通订单在线付款的，已经付款显示为待发货
        map.put(OrderTypeEnum.normal + "_" + PaymentTypeEnum.ONLINE + "_" + OrderStatusEnum.PAID_OFF, "待发货");

        //普通订单在线付款的，已确认显示为待付款
        map.put(OrderTypeEnum.normal + "_" + PaymentTypeEnum.ONLINE + "_" + OrderStatusEnum.CONFIRM, "待付款");

        //普通订单货到付款的，已确认的显示为待发货
        map.put(OrderTypeEnum.normal + "_" + PaymentTypeEnum.COD + "_" + OrderStatusEnum.CONFIRM, "待发货");
    }


    /**
     * 获取特殊状态text
     *
     * @param orderType   订单类型
     * @param paymentType 支付类型
     * @param orderStatus 订单状态
     * @return
     */
    public static String getStatusText(String orderType, String paymentType, String orderStatus) {
        return map.get(orderType + "_" + paymentType + "_" + orderStatus);
    }

}
