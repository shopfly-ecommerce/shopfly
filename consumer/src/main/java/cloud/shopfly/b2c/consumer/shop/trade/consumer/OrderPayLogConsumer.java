/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.shop.trade.consumer;

import cloud.shopfly.b2c.consumer.core.event.OrderStatusChangeEvent;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.payment.model.dos.PaymentMethodDO;
import cloud.shopfly.b2c.core.payment.service.PaymentMethodManager;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.PayLog;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PayStatusEnum;
import cloud.shopfly.b2c.core.trade.order.service.PayLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单支付后，修改付款单
 *
 * @author Snow create in 2018/7/23
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class OrderPayLogConsumer implements OrderStatusChangeEvent {

    @Autowired
    private PayLogManager payLogManager;

    @Autowired
    private PaymentMethodManager paymentMethodManager;

    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        //订单已付款
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.PAID_OFF.name())) {

            OrderDO orderDO = orderMessage.getOrderDO();
            PayLog payLog = this.payLogManager.getModel(orderDO.getSn());

            // 查询支付方式
            PaymentMethodDO paymentMethod = this.paymentMethodManager.getByPluginId(orderDO.getPaymentPluginId());
            if (paymentMethod == null) {
                paymentMethod = new PaymentMethodDO();
                paymentMethod.setMethodName("管理员确认收款");
            }

            payLog.setPayType(paymentMethod.getMethodName());
            payLog.setPayTime(orderDO.getPaymentTime());
            payLog.setPayMoney(orderDO.getPayMoney());
            payLog.setPayStatus(PayStatusEnum.PAY_YES.name());
            payLog.setPayOrderNo(orderDO.getPayOrderNo());

            this.payLogManager.edit(payLog, payLog.getPayLogId());
        }

    }
}
