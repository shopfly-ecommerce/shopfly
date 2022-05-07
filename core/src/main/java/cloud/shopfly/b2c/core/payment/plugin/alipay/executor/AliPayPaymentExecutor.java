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

import cloud.shopfly.b2c.core.payment.PaymentErrorCode;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.PayMode;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.plugin.alipay.AlipayPluginConfig;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import cloud.shopfly.b2c.core.payment.plugin.alipay.ShopflyAlipayUtil;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: Alipaypcend
 * @date 2018/4/1714:55
 * @since v7.0.0
 */
@Service
public class AliPayPaymentExecutor extends AlipayPluginConfig {

    @Autowired
    private Debugger debugger;

    /**
     * pay
     * @param bill
     * @return
     */
    public Map onPay(PayBill bill) {

        try {

            AlipayClient alipayClient =  super.buildClient(bill.getClientType());

            // Setting request Parameters
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(this.getReturnUrl(bill));
            alipayRequest.setNotifyUrl(this.getCallBackUrl(bill.getTradeType(), bill.getClientType()));

            debugger.log("callback url is ");
            debugger.log(alipayRequest.getNotifyUrl());

            Map<String, String> sParaTemp =  createParam(bill);

            // Scanning TWO-DIMENSIONAL code mode
            if (PayMode.qr.name().equals(bill.getPayMode())) {
                sParaTemp.put("qr_pay_mode", "4");
                sParaTemp.put("qrcode_width", "200");
            }

            ObjectMapper json = new ObjectMapper();

            String bizContent =  json.writeValueAsString( sParaTemp);

            // Populate business parameters
            alipayRequest.setBizContent(bizContent);
            AlipayResponse response = alipayClient.pageExecute(alipayRequest);
            return JsonUtil.toMap(response.getBody());

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * Synchronous payment callback
     *
     * @param tradeType
     */
    public String onReturn(TradeType tradeType) {

        // Bill No.
        String billSn = "";
        // Transaction no.
        try {

            HttpServletRequest request = ThreadContextHolder.getHttpRequest();
            billSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // TRADE_CLOSED unpaid trade timeout closed, or full refund after payment is completed TRADE_SUCCESS trade paid successfully TRADE_FINISHED trade closed, non-refundable
            String returnTradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // The payment amount
            String totalAmount = request.getParameter("total_amount");

            Map<String, String> cfgparams = this.getConfig(ClientType.PC);
            String alipayPublicKey = cfgparams.get("alipay_public_key");

            // Verify success
            if (ShopflyAlipayUtil.verify(alipayPublicKey)) {
                // New version synchronization has no transaction status
                double payPrice = StringUtil.toDouble(totalAmount, 0d);
                //this.paySuccess(billSn, returnTradeNo, tradeType, payPrice);
            } else {

                throw new ServiceException(PaymentErrorCode.E503.code(), "Validation fails");
            }

        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Verify an exception", e);
            }
        }
        return billSn;
    }

    /**
     * Asynchronous payment callback
     *
     * @param tradeType
     */
    public String onCallback(TradeType tradeType, ClientType clientType) {
        debugger.log("Enter alipay callback");
        try {

            HttpServletRequest request = ThreadContextHolder.getHttpRequest();

            // Merchant Order Number
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            // Alipay transaction number
            String returnTradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            // Transaction status
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            // The payment amount
            String totalAmount = request.getParameter("total_amount");

            Map<String, String> cfgparams = this.getConfig(clientType);
            String alipayPublicKey = cfgparams.get("alipay_public_key");

            debugger.log("outTradeNo:["+outTradeNo+"],returnTradeNo:["+returnTradeNo+"],tradeStatus:["+tradeStatus+"],totalAmount:["+totalAmount+"]");
            debugger.log("alipayPublicKey:");
            debugger.log(alipayPublicKey);

            // Authentication is successful
            if (ShopflyAlipayUtil.verify(alipayPublicKey)) {
                debugger.log("Authentication is successful");

                if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                    double payPrice = StringUtil.toDouble(totalAmount, 0d);
                    this.paySuccess(outTradeNo, returnTradeNo, tradeType, payPrice);
                }
                // Please do not modify or delete
                return "success";
            } else {// Validation fails
                debugger.log("Validation fails");
                return "fail";
            }
        } catch (Exception e) {
            this.logger.error("Verify an exception", e);
            return "fail";
        }


    }

    /**
     * Query payment result
     *
     * @param bill
     * @return
     */
    public String onQuery(PayBill bill) {

        AlipayClient alipayClient =  super.buildClient(bill.getClientType());

        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

        String outTradeNo = bill.getBillSn();

        Map<String, String> sParaTemp = new HashMap<String, String>(16);
        sParaTemp.put("out_trade_no", bill.getBillSn());

        ObjectMapper json = new ObjectMapper();

        try {
            alipayRequest.setBizContent(json.writeValueAsString(sParaTemp));

            AlipayTradeQueryResponse response = alipayClient.execute(alipayRequest);
            if (response.isSuccess()) {

                String tradeStatus = response.getTradeStatus();
                // Trade status: WAIT_BUYER_PAY (transaction created, waiting for buyer to pay), TRADE_CLOSED (unpaid transaction closed over time, or full refund after payment completed), TRADE_SUCCESS (transaction paid successfully), TRADE_FINISHED (transaction completed, non-refundable)
                if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {

                    String totalAmount = response.getTotalAmount();
                    double payPrice = StringUtil.toDouble(totalAmount, 0d);

                    String returnTradeNo = response.getTradeNo();

                    this.paySuccess(outTradeNo, returnTradeNo, bill.getTradeType(), payPrice);

                }
            } else {

                logger.error("Payment query failed");
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return "";
    }


}
