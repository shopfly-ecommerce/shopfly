/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.goods;

import dev.shopflix.consumer.core.event.OrderStatusChangeEvent;
import dev.shopflix.core.base.message.OrderStatusChangeMsg;
import dev.shopflix.core.client.goods.GoodsClient;
import dev.shopflix.core.trade.order.model.dos.OrderDO;
import dev.shopflix.core.trade.order.model.enums.OrderStatusEnum;
import dev.shopflix.core.trade.order.model.vo.OrderSkuVO;
import dev.shopflix.framework.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fk
 * @version v1.0
 * @Description: 商品购买数量变化
 * @date 2018/6/2510:13
 * @since v7.0.0
 */
@Service
public class GoodsBuyCountConsumer implements OrderStatusChangeEvent {

    @Autowired
    private GoodsClient goodsClient;


    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        // 收货后更新商品的购买数量
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.ROG.name())) {
            OrderDO order = orderMessage.getOrderDO();
            String itemsJson = order.getItemsJson();

            List<OrderSkuVO> list = JsonUtil.jsonToList(itemsJson, OrderSkuVO.class);
            this.goodsClient.updateBuyCount(list);
        }
    }
}
