/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.trade.consumer;

import dev.shopflix.consumer.core.event.OrderStatusChangeEvent;
import com.enation.app.javashop.core.base.message.OrderStatusChangeMsg;
import com.enation.app.javashop.core.goods.model.enums.QuantityType;
import com.enation.app.javashop.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import com.enation.app.javashop.core.promotion.fulldiscount.service.FullDiscountGiftManager;
import com.enation.app.javashop.core.trade.order.model.enums.OrderMetaKeyEnum;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import com.enation.app.javashop.core.trade.order.service.OrderMetaManager;
import com.enation.app.javashop.framework.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单取消时增加订单赠品的可用库存
 *
 * @author Snow create in 2018/5/21
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class OrderGiftStoreConsumer implements OrderStatusChangeEvent {

    @Autowired
    private FullDiscountGiftManager fullDiscountGiftManager;

    @Autowired
    private OrderMetaManager orderMetaManager;

    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        //已确认状态减少赠品可用库存
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.CONFIRM.name())) {

            List<FullDiscountGiftDO> giftList = this.getList(orderMessage);
            this.fullDiscountGiftManager.reduceGiftQuantity(giftList, QuantityType.enable);
        }

        // 发货减少赠品真实库存
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.SHIPPED.name())) {

            List<FullDiscountGiftDO> giftList = this.getList(orderMessage);
            this.fullDiscountGiftManager.reduceGiftQuantity(giftList, QuantityType.actual);
        }

        //取消状态增加赠品可用库存
        if(orderMessage.getNewStatus().name().equals(OrderStatusEnum.CANCELLED.name())){

            List<FullDiscountGiftDO> giftList = this.getList(orderMessage);
            this.fullDiscountGiftManager.addGiftEnableQuantity(giftList);
        }

    }

    /**
     * 查询赠品信息
     *
     * @param orderMessage
     * @return
     */
    private List<FullDiscountGiftDO> getList(OrderStatusChangeMsg orderMessage) {

        String fullDiscountGiftJson = this.orderMetaManager.getMetaValue(orderMessage.getOrderDO().getSn(), OrderMetaKeyEnum.GIFT);
        List<FullDiscountGiftDO> giftList = JsonUtil.jsonToList(fullDiscountGiftJson, FullDiscountGiftDO.class);

        return giftList;
    }
}
