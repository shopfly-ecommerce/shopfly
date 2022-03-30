/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.trade.consumer;

import dev.shopflix.consumer.core.event.OrderStatusChangeEvent;
import com.enation.app.javashop.core.base.message.OrderStatusChangeMsg;
import com.enation.app.javashop.core.promotion.seckill.service.SeckillGoodsManager;
import com.enation.app.javashop.core.trade.order.model.dos.OrderDO;
import com.enation.app.javashop.core.trade.order.model.enums.OrderOutStatusEnum;
import com.enation.app.javashop.core.trade.order.model.enums.OrderOutTypeEnum;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import com.enation.app.javashop.core.trade.order.service.OrderOutStatusManager;
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
