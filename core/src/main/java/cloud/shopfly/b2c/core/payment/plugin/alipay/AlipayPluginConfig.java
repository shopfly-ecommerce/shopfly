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
package cloud.shopfly.b2c.core.payment.plugin.alipay;

import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import com.alipay.api.AlipayClient;
import cloud.shopfly.b2c.core.payment.plugin.alipay.executor.SfAlipayPayClient;
import cloud.shopfly.b2c.core.payment.service.AbstractPaymentPlugin;
import cloud.shopfly.b2c.framework.logs.Debugger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;


/**
 * @author fk
 * @version v2.0
 * @Description: 支付宝配置相关
 * @date 2018/4/12 10:25
 * @since v7.0.0
 */
public class AlipayPluginConfig extends AbstractPaymentPlugin {

    protected final String siteName = "shopfly商城";

    @Autowired
    private Debugger debugger;

    @Override
    protected String getPluginId() {

        return "alipayDirectPlugin";
    }


    /**
     * 构建alipay client
     * @param clientType
     * @return
     */
    protected AlipayClient buildClient(ClientType clientType) {

        Map<String, String> config = this.getConfig(clientType );

        return buildClient(config);
    }


    protected AlipayClient buildClient(Map<String, String> config ){

        String appId = config.get("app_id");
        String merchantPrivateKey = config.get("merchant_private_key");
        String alipayPublicKey = config.get("alipay_public_key");

        debugger.log("使用如下参数构建client:");
        debugger.log(config.toString());

        AlipayClient alipayClient = new SfAlipayPayClient(AlipayConfig.gatewayUrl, appId, merchantPrivateKey, "json", AlipayConfig.charset, alipayPublicKey, AlipayConfig.signType);

        return alipayClient;
    }

    /**
     * @param bill
     * @return
     */
    protected Map<String, String> createParam(PayBill bill) {

        // 商户网站订单
        String outTradeNo = bill.getBillSn();
        Double payMoney = bill.getOrderPrice();

        // 订单名称
        String subject = siteName + "订单";

        Map<String, String> sParaTemp = new HashMap<String, String>(16);
        sParaTemp.put("out_trade_no", outTradeNo);
        sParaTemp.put("product_code", "FAST_INSTANT_TRADE_PAY");
        sParaTemp.put("total_amount", payMoney + "");
        sParaTemp.put("subject", subject);
        sParaTemp.put("body", "");

        return sParaTemp;
    }






}
