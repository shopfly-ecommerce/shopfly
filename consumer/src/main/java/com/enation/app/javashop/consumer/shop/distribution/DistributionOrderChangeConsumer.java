/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.shop.distribution;

import com.enation.app.javashop.consumer.core.event.OrderStatusChangeEvent;
import com.enation.app.javashop.core.base.message.OrderStatusChangeMsg;
import com.enation.app.javashop.core.client.distribution.DistributionOrderClient;
import com.enation.app.javashop.core.trade.order.model.dos.OrderDO;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 分销商订单处理
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/14 上午7:13
 */

@Component
public class DistributionOrderChangeConsumer implements OrderStatusChangeEvent {

    @Autowired
    private DistributionOrderClient distributionOrderClient;

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Override
    public void orderChange(OrderStatusChangeMsg orderStatusChangeMsg) {
        OrderDO order = orderStatusChangeMsg.getOrderDO();
        try {
            if (orderStatusChangeMsg.getNewStatus().equals(OrderStatusEnum.ROG)) {
                distributionOrderClient.confirm(order);
            }
        } catch (Exception e) {
            logger.error("订单收款分销计算返利异常：", e);
        }
    }

}
