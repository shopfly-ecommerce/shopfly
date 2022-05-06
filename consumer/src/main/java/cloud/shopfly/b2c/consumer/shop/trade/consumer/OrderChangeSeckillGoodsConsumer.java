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
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillGoodsManager;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOutStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOutTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.service.OrderOutStatusManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单付款扣减限时抢购商品库存
 *
 * @author Snow create in 2018/7/12
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class OrderChangeSeckillGoodsConsumer implements OrderStatusChangeEvent {

    @Autowired
    private OrderOutStatusManager orderOutStatusManager;

    @Autowired
    private SeckillGoodsManager seckillApplyManager;


    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        OrderDO orderDO = orderMessage.getOrderDO();
        //如果订单已确认
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.CONFIRM.name())) {

            this.orderOutStatusManager.edit(orderDO.getSn(), OrderOutTypeEnum.SECKILL_GOODS, OrderOutStatusEnum.SUCCESS);
            return;
        }

        //出库失败
        if(orderMessage.getNewStatus().name().equals(OrderStatusEnum.INTODB_ERROR.name())){
            this.orderOutStatusManager.edit(orderDO.getSn(), OrderOutTypeEnum.SECKILL_GOODS, OrderOutStatusEnum.FAIL);
        }

    }

}
