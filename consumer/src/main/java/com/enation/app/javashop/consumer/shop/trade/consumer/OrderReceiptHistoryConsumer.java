/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.shop.trade.consumer;

import com.enation.app.javashop.consumer.core.event.TradeIntoDbEvent;
import com.enation.app.javashop.core.client.member.MemberHistoryReceiptClient;
import com.enation.app.javashop.core.member.model.dos.ReceiptHistory;
import com.enation.app.javashop.core.trade.order.model.dto.OrderDTO;
import com.enation.app.javashop.core.trade.order.model.vo.ReceiptVO;
import com.enation.app.javashop.core.trade.order.model.vo.TradeVO;
import com.enation.app.javashop.framework.util.CurrencyUtil;
import com.enation.app.javashop.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单发票历史消费者
 *
 * @author zh
 * @version v7.0
 * @date 18/7/21 下午5:50
 * @since v7.0
 */
@Component
public class OrderReceiptHistoryConsumer implements TradeIntoDbEvent {

    @Autowired
    private MemberHistoryReceiptClient memberHistoryReceiptClient;

    @Override
    public void onTradeIntoDb(TradeVO tradeVO) {

        //从交易中获取订单列表
        List<OrderDTO> orderDTOS = tradeVO.getOrderList();
        //循环订单取出发票信息
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
                //发票金额为待支付金额减去运费  update by liuyulei 2019-05-13
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


