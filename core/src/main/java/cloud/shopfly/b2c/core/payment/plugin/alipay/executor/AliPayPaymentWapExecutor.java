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
import com.alipay.api.request.AlipayTradeWapPayRequest;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author fk
 * @version v1.0
 * @Description: 支付宝wap端
 * @date 2018/4/1714:55
 * @since v7.0.0
 */
@Service
public class AliPayPaymentWapExecutor extends AlipayPluginConfig {


    /**
     * 支付
     *
     * @param bill
     * @return
     */
    public Map onPay(PayBill bill) {

        try {
            AlipayClient alipayClient =  super.buildClient(bill.getClientType());

            //设置请求参数
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
            alipayRequest.setNotifyUrl(this.getCallBackUrl(bill.getTradeType(), bill.getClientType()));
            alipayRequest.setReturnUrl(this.getReturnUrl(bill));
            Map<String, String> sParaTemp =  createParam(bill);
            ObjectMapper json = new ObjectMapper();
            String bizContent =  json.writeValueAsString( sParaTemp );

            //填充业务参数
            alipayRequest.setBizContent(bizContent );
            return JsonUtil.toMap(alipayClient.pageExecute(alipayRequest).getBody());
        } catch ( Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

}
