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
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOutStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOutTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.service.OrderOutStatusManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 团购商品库存变更
 *
 * @author Snow create in 2018/5/22
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class OrderChangeGroupBuyConsumer implements OrderStatusChangeEvent {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private GroupbuyGoodsManager groupbuyGoodsManager;

    @Autowired
    private OrderOutStatusManager orderOutStatusManager;

    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        OrderDO orderDO = orderMessage.getOrderDO();
        //订单确认，优惠库存扣减
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.CONFIRM.name())) {

            this.orderOutStatusManager.edit(orderDO.getSn(), OrderOutTypeEnum.GROUPBUY_GOODS, OrderOutStatusEnum.SUCCESS);

        } else if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.CANCELLED.name())) {

            groupbuyGoodsManager.addQuantity(orderMessage.getOrderDO().getSn());
        } else if(orderMessage.getNewStatus().name().equals(OrderStatusEnum.INTODB_ERROR.name())){

            logger.error("团购库存扣减失败");
            this.orderOutStatusManager.edit(orderDO.getSn(), OrderOutTypeEnum.GROUPBUY_GOODS, OrderOutStatusEnum.FAIL);
        }
    }

}
