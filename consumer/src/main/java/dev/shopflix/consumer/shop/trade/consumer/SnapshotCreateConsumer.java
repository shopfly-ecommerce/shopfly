/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.trade.consumer;

import dev.shopflix.consumer.core.event.OrderStatusChangeEvent;
import com.enation.app.javashop.core.base.message.OrderStatusChangeMsg;
import com.enation.app.javashop.core.trade.order.model.dos.OrderDO;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import com.enation.app.javashop.core.trade.snapshot.service.GoodsSnapshotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生成商品交易快照
 *
 * @author Snow create in 2018/5/22
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class SnapshotCreateConsumer implements OrderStatusChangeEvent {

    @Autowired
    private GoodsSnapshotManager goodsSnapshotManager;

    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        if (orderMessage.getNewStatus().equals(OrderStatusEnum.NEW)) {
            OrderDO orderDO = orderMessage.getOrderDO();

            this.goodsSnapshotManager.add(orderDO);
        }
    }


}
