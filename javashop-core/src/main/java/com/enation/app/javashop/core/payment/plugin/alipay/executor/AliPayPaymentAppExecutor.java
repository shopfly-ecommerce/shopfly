/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.plugin.alipay.executor;

import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.enation.app.javashop.core.payment.model.vo.PayBill;
import com.enation.app.javashop.core.payment.plugin.alipay.AlipayPluginConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 支付宝app端
 * @date 2018/4/1714:55
 * @since v7.0.0
 */
@Service
public class AliPayPaymentAppExecutor extends AlipayPluginConfig {


    /**
     * 支付
     *
     * @param bill
     * @return
     */
    public Map onPay(PayBill bill) {

        Map result = new HashMap();

        try {
            AlipayClient alipayClient =  super.buildClient(bill.getClientType());

             //设置请求参数
            AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
            alipayRequest.setNotifyUrl(this.getCallBackUrl(bill.getTradeType(), bill.getClientType()));

            // 商户网站订单
            String outTradeNo = bill.getBillSn();
            double payMoney = bill.getOrderPrice();

            // 订单名称
            String subject = siteName + "订单";

            String body = "";

            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody(body);
            model.setSubject(subject);
            model.setOutTradeNo(outTradeNo);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(payMoney + "");
            model.setProductCode("QUICK_MSECURITY_PAY");
            alipayRequest.setBizModel(model);

            result.put("gateway_url",alipayClient.sdkExecute(alipayRequest).getBody());

            return result;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

}
