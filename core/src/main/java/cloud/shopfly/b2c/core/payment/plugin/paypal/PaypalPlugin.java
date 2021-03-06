package cloud.shopfly.b2c.core.payment.plugin.paypal;


import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.PaypalConfigItem;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.ClientConfig;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.model.vo.PayConfigItem;
import cloud.shopfly.b2c.core.payment.model.vo.RefundBill;
import cloud.shopfly.b2c.core.payment.plugin.paypal.executor.PaypalPaymentExecutor;
import cloud.shopfly.b2c.core.payment.service.PaymentPluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PaypalPlugin
 *
 * @author snow
 * @version 1.0.0
 * 2022-04-22 18:51:00
 */
@Service
public class PaypalPlugin extends PayPalClient implements PaymentPluginManager  {

    private static 	Map<String, String> map = new HashMap<String, String>();

    @Autowired
    private PaypalPaymentExecutor paypalPaymentExecutor;

    @Override
    public String getPluginId() {
        return "paypalPlugin";
    }

    @Override
    public String getPluginName() {
        return "Paypal";
    }

    @Override
    public List<ClientConfig> definitionClientConfig() {

        List<ClientConfig> resultList = new ArrayList<>();

        ClientConfig config = new ClientConfig();

        List<PayConfigItem> configList = new ArrayList<>();
        for (PaypalConfigItem value : PaypalConfigItem.values()) {
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
    public Map pay(PayBill bill) {
        return this.paypalPaymentExecutor.onPay(bill);
    }

    @Override
    public void onReturn(TradeType tradeType) {
        System.out.println("Synchronous payment callback");
    }

    @Override
    public String onCallback(TradeType tradeType, ClientType clientType) {
        return this.paypalPaymentExecutor.onCallback(tradeType, clientType);
    }

    @Override
    public String onQuery(PayBill bill) {
        return this.paypalPaymentExecutor.onQuery(bill);
    }

    @Override
    public boolean onTradeRefund(RefundBill bill) {
        return false;
    }

    @Override
    public String queryRefundStatus(RefundBill bill) {
        return null;
    }

    @Override
    public Integer getIsRetrace() {
        return null;
    }





}
