/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.trade.consumer;

import dev.shopflix.consumer.core.event.OrderStatusChangeEvent;
import com.enation.app.javashop.core.base.message.OrderStatusChangeMsg;
import com.enation.app.javashop.core.promotion.pintuan.service.PintuanOrderManager;
import com.enation.app.javashop.core.trade.order.model.dos.OrderDO;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import com.enation.app.javashop.core.trade.order.model.enums.OrderTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by kingapex on 2019-01-25.
 * 拼团订单消费者<br/>
 * 如果是拼团订单，检测相应的拼团活动是否已经参团成功<br/>
 * 如果成功，要更新相应数据
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

        //对已付款的订单
        if (orderMessage.getNewStatus().equals(OrderStatusEnum.PAID_OFF)) {
            OrderDO orderDO = orderMessage.getOrderDO();
            if (orderDO.getOrderType().equals(OrderTypeEnum.pintuan.name())) {
                pintuanOrderManager.payOrder(orderDO);
            }

        }

    }
}
