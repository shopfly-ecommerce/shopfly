/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.shop.trade.consumer;

import cloud.shopfly.b2c.consumer.core.event.RefundStatusChangeEvent;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundTypeEnum;
import cloud.shopfly.b2c.core.base.message.RefundChangeMsg;
import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import cloud.shopfly.b2c.core.trade.order.model.enums.ServiceStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.CancelVO;
import cloud.shopfly.b2c.core.trade.order.service.OrderOperateManager;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderSkuDTO;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 订单取消后审核消费者
 * @date 2018/9/415:18
 * @since v7.0.0
 */
@Component
public class OrderCancelAuthConsumer implements RefundStatusChangeEvent {

    @Autowired
    private OrderOperateManager orderOperateManager;

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private OrderClient orderClient;


    @Override
    public void refund(RefundChangeMsg refundChangeMsg) {
        //判断售后单是否是取消单
        RefundDO refund = refundChangeMsg.getRefund();
        if (RefundTypeEnum.CANCEL_ORDER.name().equals(refund.getRefundType())
                && RefundStatusEnum.PASS.equals(refundChangeMsg.getRefundStatusEnum())) {
            //更改订单的状态为已取消
            CancelVO cancelVO = new CancelVO();
            cancelVO.setOperator(refund.getMemberName());
            cancelVO.setOrderSn(refund.getOrderSn());
            cancelVO.setReason(refund.getRefundReason());
            orderOperateManager.cancel(cancelVO, OrderPermission.client);
        }

        if (RefundTypeEnum.CANCEL_ORDER.name().equals(refund.getRefundType())
                && RefundStatusEnum.REFUSE.equals(refundChangeMsg.getRefundStatusEnum())) {

            OrderDetailDTO order = orderClient.getModel(refund.getOrderSn());
            List<OrderSkuDTO> skuList = order.getOrderSkuList();
            for (OrderSkuDTO sku : skuList) {
                sku.setServiceStatus(ServiceStatusEnum.NOT_APPLY.value());
            }
            //更改订单的状态为已取消
            String sql = "update es_order set service_status  = ?,items_json = ? where sn =? ";
            this.daoSupport.execute(sql, ServiceStatusEnum.EXPIRED.name(), JsonUtil.objectToJson(skuList), refund.getOrderSn());
        }
    }
}
