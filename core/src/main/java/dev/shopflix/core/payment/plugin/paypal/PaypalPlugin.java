package dev.shopflix.core.payment.plugin.paypal;

import cn.hutool.json.JSONUtil;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import dev.shopflix.core.client.trade.OrderClient;
import dev.shopflix.core.payment.model.enums.ClientType;
import dev.shopflix.core.payment.model.enums.TradeType;
import dev.shopflix.core.payment.model.vo.ClientConfig;
import dev.shopflix.core.payment.model.vo.PayBill;
import dev.shopflix.core.payment.model.vo.RefundBill;
import dev.shopflix.core.payment.plugin.paypal.executor.PaypalPaymentExecutor;
import dev.shopflix.core.payment.service.PaymentPluginManager;
import dev.shopflix.core.trade.order.model.vo.OrderDetailVO;
import dev.shopflix.core.trade.order.service.OrderOperateManager;
import dev.shopflix.core.trade.sdk.model.OrderDetailDTO;
import dev.shopflix.framework.context.ThreadContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
        return null;
    }

    @Override
    public Map pay(PayBill bill) {
        return this.paypalPaymentExecutor.onPay(bill);
    }

    @Override
    public void onReturn(TradeType tradeType) {
        System.out.println("同步支付回调");
    }

    @Override
    public String onCallback(TradeType tradeType, ClientType clientType) {
        System.out.println("异步支付回调");

        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        System.out.println(JSONUtil.toJsonStr(request.getParameterMap()));

        return null;
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
