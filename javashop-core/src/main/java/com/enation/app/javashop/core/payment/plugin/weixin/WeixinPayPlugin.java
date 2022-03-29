/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.plugin.weixin;

import com.enation.app.javashop.core.payment.model.enums.ClientType;
import com.enation.app.javashop.core.payment.model.enums.TradeType;
import com.enation.app.javashop.core.payment.model.enums.WeixinConfigItem;
import com.enation.app.javashop.core.payment.model.vo.*;
import com.enation.app.javashop.core.payment.plugin.weixin.executor.*;
import com.enation.app.javashop.core.payment.service.PaymentPluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 微信支付插件
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

        //使用支付客户端判断调用哪个执行者
        if (bill.getClientType().equals(ClientType.PC)) {

            return weixinPaymentExecutor.onPay(bill);
        }
        //小程序
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
        return "微信";
    }

    @Override
    public void onReturn(TradeType tradeType) {
        //微信支付没有同步回调，不需要实现
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

        // 是否开启pc和wap
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
        config.setName("是否开启PC和WAP");

        resultList.add(config);
        //======================app原生配置==================================
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
        configApp.setName("是否开启APP原生");

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
        configAppReact.setName("是否开启APP-RN");

        resultList.add(configAppReact);

        //======================小程序配置==================================
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
        configMini.setName("是否开启小程序");

        resultList.add(configMini);

        return resultList;
    }
}
