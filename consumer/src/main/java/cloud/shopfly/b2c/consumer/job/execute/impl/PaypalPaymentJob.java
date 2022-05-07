package cloud.shopfly.b2c.consumer.job.execute.impl;

import cloud.shopfly.b2c.consumer.job.execute.EveryTenMinutesExecute;
import cloud.shopfly.b2c.core.payment.model.dos.PaymentBillDO;
import cloud.shopfly.b2c.core.payment.service.PaymentBillManager;
import cloud.shopfly.b2c.core.payment.service.PaymentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PaypalPaymentJob
 *
 * @author snow
 * @version 1.0.0
 * 2022-04-28 21:53:00
 */
@Component
public class PaypalPaymentJob implements EveryTenMinutesExecute {

    @Autowired
    private PaymentBillManager paymentBillManager;

    @Autowired
    private PaymentManager paymentManager;

    @Override
    public void everyTenMinutes() {
        // Query all orders to be paid

        List<PaymentBillDO> list = this.paymentBillManager.getWaitPay();

        // Loop to query payment status
//        for (PaymentBillDO billDO: list) {
//            System.out.println("The order number= " + billDO.getSn());
//            PayParam param = new PayParam();
//            param.setSn(billDO.getSn());
//            param.setTradeType(billDO.getTradeType());
//            param.setClientType(ClientType.PC.name());
//            param.setPaymentPluginId(billDO.getPaymentPluginId());
//            try {
//                this.paymentManager.queryResult(param);
//            } catch (Exception e) {
////                e.printStackTrace();
//                continue;
//            }
//        }

    }
}
