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
package cloud.shopfly.b2c.core.payment.plugin.weixin;

import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.enums.WeixinConfigItem;
import cloud.shopfly.b2c.core.payment.model.vo.ClientConfig;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.model.vo.PayConfigItem;
import cloud.shopfly.b2c.core.payment.model.vo.RefundBill;
import cloud.shopfly.b2c.core.payment.plugin.weixin.executor.*;
import cloud.shopfly.b2c.core.payment.plugin.weixin.executor.*;
import cloud.shopfly.b2c.core.payment.service.PaymentPluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: Wechat pay plug-in
 * @date 2018/4/12 10:25
 * @since v7.0.0
 */
@Service
public class WeixinPayPlugin implements PaymentPluginManager {

    @Autowired
    private WeixinPaymentAppExecutor weixinPaymentAppExecutor;

    @Autowired
    private WeixinPaymentJsapiExecutor weixinPaymentJsapiExecutor;

    @Autowired
    private WeixinPaymentMiniExecutor weixinPaymentMiniExecutor;

    @Autowired
    private WeixinPaymentExecutor weixinPaymentExecutor;

    @Autowired
    private WeixinPaymentWapExecutor weixinPaymentWapExecutor;

    @Autowired
    private WeixinRefundExecutor weixinRefundExecutor;

    @Override
    public Map pay(PayBill bill) {

        // Use the payment client to determine which performer to call
        if (bill.getClientType().equals(ClientType.PC)) {

            return weixinPaymentExecutor.onPay(bill);
        }
        // Small program
        if (bill.getClientType().equals(ClientType.MINI)) {

            return weixinPaymentMiniExecutor.onPay(bill);
        }

        if (bill.getClientType().equals(ClientType.WAP)) {

            if (WeixinUtil.isWeChat() == 1) {
                return weixinPaymentJsapiExecutor.onPay(bill);
            } else {
                return weixinPaymentWapExecutor.onPay(bill);
            }
        }

        if (bill.getClientType().equals(ClientType.NATIVE) || bill.getClientType().equals(ClientType.REACT)) {

            return weixinPaymentAppExecutor.onPay(bill);
        }
        return null;
    }

    @Override
    public String getPluginId() {
        return "weixinPayPlugin";
    }

    @Override
    public String getPluginName() {
        return "WeChat";
    }

    @Override
    public void onReturn(TradeType tradeType) {
        // Wechat Pay does not have a synchronous callback and does not need to be implemented
    }

    @Override
    public String onCallback(TradeType tradeType, ClientType clientType) {

        return weixinPaymentExecutor.onCallback(tradeType, clientType);
    }

    @Override
    public String onQuery(PayBill bill) {

        return weixinPaymentExecutor.onQuery(bill);
    }

    @Override
    public boolean onTradeRefund(RefundBill bill) {

        return weixinRefundExecutor.returnPay(bill);
    }

    @Override
    public String queryRefundStatus(RefundBill bill) {

        return weixinRefundExecutor.queryRefundStatus(bill);
    }

    @Override
    public Integer getIsRetrace() {

        return 1;
    }

    @Override
    public List<ClientConfig> definitionClientConfig() {
        List<ClientConfig> resultList = new ArrayList<>();

        // Whether to enable PC and WAP
        ClientConfig config = new ClientConfig();

        List<PayConfigItem> configList = new ArrayList<>();

        for (WeixinConfigItem configValue : WeixinConfigItem.values()) {
            PayConfigItem item = new PayConfigItem();
            item.setName(configValue.name());
            item.setText(configValue.getText());
            configList.add(item);
        }
        config.setKey(ClientType.PC.getDbColumn() + "&" + ClientType.WAP.getDbColumn());
        config.setConfigList(configList);
        config.setName("Whether openPCandWAP");

        resultList.add(config);
        // = = = = = = = = = = = = = = = = = = = = = = app native configuration = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
        ClientConfig configApp = new ClientConfig();

        List<PayConfigItem> configListApp = new ArrayList<>();

        for (WeixinConfigItem configValue : WeixinConfigItem.values()) {
            PayConfigItem item = new PayConfigItem();
            item.setName(configValue.name());
            item.setText(configValue.getText());
            configListApp.add(item);
        }
        configApp.setKey(ClientType.NATIVE.getDbColumn());
        configApp.setConfigList(configListApp);
        configApp.setName("Whether openAPPnative");

        resultList.add(configApp);
        //=========================app RN=================================
        ClientConfig configAppReact = new ClientConfig();

        List<PayConfigItem> configListAppReact = new ArrayList<>();

        for (WeixinConfigItem configValue : WeixinConfigItem.values()) {
            PayConfigItem item = new PayConfigItem();
            item.setName(configValue.name());
            item.setText(configValue.getText());
            configListAppReact.add(item);
        }
        configAppReact.setKey(ClientType.REACT.getDbColumn());
        configAppReact.setConfigList(configListAppReact);
        configAppReact.setName("Whether openAPP-RN");

        resultList.add(configAppReact);

        // = = = = = = = = = = = = = = = = = = = = = = small application configuration = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
        ClientConfig configMini = new ClientConfig();

        List<PayConfigItem> configListMini = new ArrayList<>();

        for (WeixinConfigItem configValue : WeixinConfigItem.values()) {
            PayConfigItem item = new PayConfigItem();
            item.setName(configValue.name());
            item.setText(configValue.getText());
            configListMini.add(item);
        }
        configMini.setKey(ClientType.MINI.getDbColumn());
        configMini.setConfigList(configListMini);
        configMini.setName("Whether to start a small program");

        resultList.add(configMini);

        return resultList;
    }
}
