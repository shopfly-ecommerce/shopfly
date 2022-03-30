/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.trade.consumer;

import dev.shopflix.consumer.core.event.OrderStatusChangeEvent;
import com.enation.app.javashop.core.base.message.OrderStatusChangeMsg;
import com.enation.app.javashop.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import com.enation.app.javashop.core.trade.order.model.dos.OrderDO;
import com.enation.app.javashop.core.trade.order.model.enums.OrderOutStatusEnum;
import com.enation.app.javashop.core.trade.order.model.enums.OrderOutTypeEnum;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import com.enation.app.javashop.core.trade.order.service.OrderOutStatusManager;
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
