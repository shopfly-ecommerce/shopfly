/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.shop.trade.consumer;

import com.enation.app.javashop.consumer.core.event.OrderStatusChangeEvent;
import com.enation.app.javashop.core.base.message.OrderStatusChangeMsg;
import com.enation.app.javashop.core.trade.order.model.dos.OrderDO;
import com.enation.app.javashop.core.trade.order.model.dos.TransactionRecord;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import com.enation.app.javashop.core.trade.order.model.vo.OrderSkuVO;
import com.enation.app.javashop.core.trade.order.service.TransactionRecordManager;
import com.enation.app.javashop.framework.util.DateUtil;
import com.enation.app.javashop.framework.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单确认收货增加交易记录消费者
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
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        if (orderMessage.getNewStatus().equals(OrderStatusEnum.ROG)) {

            OrderDO orderDO = orderMessage.getOrderDO();

            TransactionRecord record = new TransactionRecord();
            record.setOrderSn(orderDO.getSn());
            if (orderDO.getMemberId() == null) {
                record.setUname("游客");
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
