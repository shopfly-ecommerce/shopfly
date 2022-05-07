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
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.TransactionRecord;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import cloud.shopfly.b2c.core.trade.order.service.TransactionRecordManager;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Order confirmation receipt increases transaction records for consumers
 *
 * @author Snow create in 2018/5/22
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class OrderRogTransactionConsumer implements OrderStatusChangeEvent {

    @Autowired
    private TransactionRecordManager transactionRecordManager;

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        if (orderMessage.getNewStatus().equals(OrderStatusEnum.ROG)) {

            OrderDO orderDO = orderMessage.getOrderDO();

            TransactionRecord record = new TransactionRecord();
            record.setOrderSn(orderDO.getSn());
            if (orderDO.getMemberId() == null) {
                record.setUname("tourists");
                record.setMemberId(0);
            } else {
                record.setMemberId(orderDO.getMemberId());
                record.setUname(orderDO.getMemberName());
            }
            record.setRogTime(DateUtil.getDateline());
            String itemJson = orderDO.getItemsJson();

            List<OrderSkuVO> orderSkuVOList = JsonUtil.jsonToList(itemJson, OrderSkuVO.class);

            for (OrderSkuVO orderSkuVO : orderSkuVOList) {
                record.setPrice(orderSkuVO.getPurchasePrice());
                record.setGoodsNum(orderSkuVO.getNum());
                record.setGoodsId(orderSkuVO.getGoodsId());
                transactionRecordManager.add(record);
            }
        }

    }

}
