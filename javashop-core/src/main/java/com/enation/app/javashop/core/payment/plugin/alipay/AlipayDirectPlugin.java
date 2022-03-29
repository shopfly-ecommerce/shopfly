/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.plugin.alipay;

import com.enation.app.javashop.core.payment.model.enums.AlipayConfigItem;
import com.enation.app.javashop.core.payment.model.enums.ClientType;
import com.enation.app.javashop.core.payment.model.enums.TradeType;
import com.enation.app.javashop.core.payment.model.vo.ClientConfig;
import com.enation.app.javashop.core.payment.model.vo.PayBill;
import com.enation.app.javashop.core.payment.model.vo.PayConfigItem;
import com.enation.app.javashop.core.payment.model.vo.RefundBill;
import com.enation.app.javashop.core.payment.plugin.alipay.executor.AliPayPaymentAppExecutor;
import com.enation.app.javashop.core.payment.plugin.alipay.executor.AliPayPaymentExecutor;
import com.enation.app.javashop.core.payment.plugin.alipay.executor.AliPayPaymentWapExecutor;
import com.enation.app.javashop.core.payment.plugin.alipay.executor.AlipayRefundExcutor;
import com.enation.app.javashop.core.payment.service.PaymentPluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 支付宝支付插件
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

        //使用支付客户端判断调用哪个执行者
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
        return "支付宝";
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
        config.setName("是否开启");

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
