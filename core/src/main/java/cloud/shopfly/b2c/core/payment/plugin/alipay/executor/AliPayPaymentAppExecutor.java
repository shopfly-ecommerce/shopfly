/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.payment.plugin.alipay.executor;

import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.plugin.alipay.AlipayPluginConfig;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
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
