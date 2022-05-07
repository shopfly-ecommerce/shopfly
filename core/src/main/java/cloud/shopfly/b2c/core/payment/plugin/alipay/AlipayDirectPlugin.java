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

import cloud.shopfly.b2c.core.payment.model.enums.AlipayConfigItem;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.ClientConfig;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.model.vo.PayConfigItem;
import cloud.shopfly.b2c.core.payment.model.vo.RefundBill;
import cloud.shopfly.b2c.core.payment.plugin.alipay.executor.AliPayPaymentAppExecutor;
import cloud.shopfly.b2c.core.payment.plugin.alipay.executor.AliPayPaymentExecutor;
import cloud.shopfly.b2c.core.payment.plugin.alipay.executor.AliPayPaymentWapExecutor;
import cloud.shopfly.b2c.core.payment.plugin.alipay.executor.AlipayRefundExcutor;
import cloud.shopfly.b2c.core.payment.service.PaymentPluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: Alipay payment plug-in
 * @date 2018/4/12 10:25
 * @since v7.0.0
 */
@Service
@ComponentScan("com.alipay.api")
public class AlipayDirectPlugin implements PaymentPluginManager {

    @Autowired
    private AliPayPaymentAppExecutor aliPayPaymentAppExecutor;

    @Autowired
    private AliPayPaymentExecutor aliPayPaymentExecutor;

    @Autowired
    private AliPayPaymentWapExecutor aliPayPaymentWapExecutor;

    @Autowired
    private AlipayRefundExcutor alipayRefundExcutor;



    @Override
    public Map pay(PayBill bill) {

        // Use the payment client to determine which performer to call
        if (bill.getClientType().equals(ClientType.PC)) {

            return aliPayPaymentExecutor.onPay(bill);
        }

        if (bill.getClientType().equals(ClientType.WAP)) {

            return aliPayPaymentWapExecutor.onPay(bill);
        }

        if (bill.getClientType().equals(ClientType.NATIVE) || bill.getClientType().equals(ClientType.REACT)) {

            return aliPayPaymentAppExecutor.onPay(bill);
        }
        return null;
    }

    @Override
    public String getPluginId() {
        return "alipayDirectPlugin";
    }

    @Override
    public String getPluginName() {
        return "Alipay";
    }

    @Override
    public List<ClientConfig> definitionClientConfig() {

        List<ClientConfig> resultList = new ArrayList<>();

        ClientConfig config = new ClientConfig();

        List<PayConfigItem> configList = new ArrayList<>();
        for (AlipayConfigItem value : AlipayConfigItem.values()) {
            PayConfigItem item = new PayConfigItem();
            item.setName(value.name());
            item.setText(value.getText());
            configList.add(item);
        }

        config.setKey(ClientType.PC.getDbColumn() + "&" + ClientType.WAP.getDbColumn() + "&" + ClientType.NATIVE.getDbColumn() + "&" + ClientType.REACT.getDbColumn());
        config.setConfigList(configList);
        config.setName("Whether open");

        resultList.add(config);

        return resultList;
    }


    @Override
    public void onReturn(TradeType tradeType) {

        aliPayPaymentExecutor.onReturn(tradeType);
    }

    @Override
    public String onCallback(TradeType tradeType, ClientType clientType) {

        return aliPayPaymentExecutor.onCallback(tradeType, clientType);
    }

    @Override
    public String onQuery(PayBill bill) {

        return aliPayPaymentExecutor.onQuery(bill);
    }

    @Override
    public boolean onTradeRefund(RefundBill bill) {

        return alipayRefundExcutor.refundPay(bill);
    }

    @Override
    public String queryRefundStatus(RefundBill bill) {

        return alipayRefundExcutor.queryRefundStatus(bill);
    }

    @Override
    public Integer getIsRetrace() {

        return 1;
    }
}
