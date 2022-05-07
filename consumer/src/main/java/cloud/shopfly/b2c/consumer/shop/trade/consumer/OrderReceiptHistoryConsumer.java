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

import cloud.shopfly.b2c.consumer.core.event.TradeIntoDbEvent;
import cloud.shopfly.b2c.core.client.member.MemberHistoryReceiptClient;
import cloud.shopfly.b2c.core.member.model.dos.ReceiptHistory;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderDTO;
import cloud.shopfly.b2c.core.trade.order.model.vo.ReceiptVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Order invoice history consumer
 *
 * @author zh
 * @version v7.0
 * @date 18/7/21 In the afternoon5:50
 * @since v7.0
 */
@Component
public class OrderReceiptHistoryConsumer implements TradeIntoDbEvent {

    @Autowired
    private MemberHistoryReceiptClient memberHistoryReceiptClient;

    @Override
    public void onTradeIntoDb(TradeVO tradeVO) {

        // Get the order list from the transaction
        List<OrderDTO> orderDTOS = tradeVO.getOrderList();
        // Loop order to retrieve invoice information
        for (OrderDTO orderDTO : orderDTOS) {
            ReceiptVO receiptVO = orderDTO.getReceiptVO();
            if (receiptVO != null && receiptVO.getReceiptTitle() != null && receiptVO.getReceiptType() != null) {
                ReceiptHistory receiptHistory = new ReceiptHistory();
                receiptHistory.setAddTime(DateUtil.getDateline());
                receiptHistory.setMemberId(orderDTO.getMemberId());
                receiptHistory.setMemberName(orderDTO.getMemberName());
                receiptHistory.setOrderSn(orderDTO.getSn());
                receiptHistory.setReceiptTitle(receiptVO.getReceiptTitle());
                receiptHistory.setReceiptContent(receiptVO.getReceiptContent());
                // Invoice amount is the amount to be paid minus freight
                Double receiptAmount = tradeVO.getPriceDetail().getIsFreeFreight() != 1 ? CurrencyUtil.sub(orderDTO.getNeedPayMoney(), orderDTO.getPrice().getFreightPrice()) : 0;
                if (receiptAmount < 0) {
                    receiptAmount = 0D;
                }
                receiptHistory.setReceiptAmount(receiptAmount);
                receiptHistory.setReceiptType(receiptVO.getReceiptType());
                receiptHistory.setTaxNo(receiptVO.getTaxNo());
                memberHistoryReceiptClient.add(receiptHistory);
            }
        }
    }
}


