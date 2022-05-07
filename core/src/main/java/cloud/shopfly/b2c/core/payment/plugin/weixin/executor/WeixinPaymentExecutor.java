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
package cloud.shopfly.b2c.core.payment.plugin.weixin.executor;

import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.Form;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.plugin.weixin.WeixinPuginConfig;
import cloud.shopfly.b2c.core.payment.plugin.weixin.WeixinUtil;
import cloud.shopfly.b2c.core.base.DomainHelper;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author fk
 * @version v2.0
 * @Description: WeChatpcend
 * @date 2018/4/1810:12
 * @since v7.0.0
 */
@Service
public class WeixinPaymentExecutor extends WeixinPuginConfig {

    @Autowired
    private Cache cache;

    @Autowired
    private DomainHelper domainHelper;

    @Autowired
    private Debugger debugger;

    /**
     * pay
     *
     * @param bill
     * @return
     */
    public Map onPay(PayBill bill) {

        Map<String, String> params = new TreeMap<>();
        Map<String, String> result = new TreeMap<>();
        params.put("spbill_create_ip", "127.0.0.1");
        params.put("trade_type", "NATIVE");

        try {
            Map<String, String> map = super.createUnifiedOrder(bill, params);

            // Returns the result
            String resultCode = map.get("result_code");
            Form form = new Form();
            if (SUCCESS.equals(resultCode)) {
                debugger.log("The prepaid order was successfully created");
                String codeUrl = map.get("code_url");
                String qr = codeUrl.replaceAll(QR_URL_PREFIX, "");
                String outTradeNo = bill.getBillSn();
                String gateWay = (domainHelper.getCallback() + "/order/pay/weixin/qrpage/" + outTradeNo + "/" + qr + "");
                debugger.log("generategateway:");
                debugger.log(gateWay);
                result.put("bill_sn",outTradeNo);
                result.put("gateway_url",gateWay);

                return result;
            } else {
                debugger.log("Failed to create prepaid order");

            }
        } catch (Exception e) {
            this.logger.error("Wechat generated payment QR code error", e);
        }
        return null;
    }

    /**
     * An asynchronous callback
     *
     * @param tradeType
     * @param clientType
     * @return
     */
    public String onCallback(TradeType tradeType, ClientType clientType) {
        Map<String, String> cfgparams = this.getConfig(clientType);

        String key = cfgparams.get("key");

        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        Map map = new HashMap(16);

        try {
            SAXReader saxReadr = new SAXReader();
            saxReadr.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            Document document = saxReadr.read(request.getInputStream());

            Map<String, String> params = WeixinUtil.xmlToMap(document);
            if (logger.isDebugEnabled()) {
                logger.info("Wechat Pay callback----->" + JsonUtil.objectToJson(params));
            }
            String returnCode = params.get("return_code");
            String resultCode = params.get("result_code");

            if (SUCCESS.equals(returnCode) && SUCCESS.equals(resultCode)) {

                String sign = WeixinUtil.createSign(params, key);
                if (sign.equals(params.get("sign"))) {

                    // Transaction number of this mall
                    String outTradeNo = params.get("out_trade_no");
                    // Wechat Pay order No
                    String returnTradeNo = params.get("transaction_id");
                    // Pay the amount
                    double payPrice = StringUtil.toDouble(params.get("total_fee"), 0d);
                    // What comes back is a fraction, which goes to elements
                    payPrice = CurrencyUtil.mul(payPrice, 0.01);

                    if (logger.isDebugEnabled()) {
                        logger.info("Pay for success:outTradeNo/returnTradeNo----->" + outTradeNo + "/" + returnTradeNo);
                    }
                    this.paySuccess(outTradeNo, returnTradeNo, tradeType, payPrice);

                    map.put("return_code", SUCCESS);
                    // Mark as successful
                    cache.put(CACHE_KEY_PREFIX + outTradeNo, "ok", 120);


                } else {
                    map.put("return_code", "FAIL");
                    map.put("return_msg", "Signature failure");
                    this.logger.error("Wechat signature failed. Procedure");
                }
            } else {
                map.put("return_code", "FAIL");
                this.logger.error("Wechat visa verification failed");
            }

        } catch (Exception e) {
            map.put("return_code", "FAIL");
            map.put("return_msg", "");
            this.logger.error("The result of wechat notification is failure", e);
        }
        HttpServletResponse response = ThreadContextHolder.getHttpResponse();
        response.setHeader("Content-Type", "text/xml");
        try {
            return WeixinUtil.mapToXml(map);
        } catch (Exception e) {
            this.logger.error("The result of wechat notification is failure", e);
            return "There is an error";
        }

    }

    /**
     * Querying bill Status
     *
     * @param bill
     * @return
     */
    public String onQuery(PayBill bill) {

        Map<String, String> config = this.getConfig(bill.getClientType());
        String appId = config.get("appid");
        String mchId = config.get("mchid");
        String key = config.get("key");

        Map<String, String> params = new TreeMap();
        params.put("appid", appId);
        params.put("mch_id", mchId);
        params.put("nonce_str", StringUtil.getRandStr(10));
        params.put("out_trade_no", bill.getBillSn());
        // Conversion to points payable
        Double money = bill.getOrderPrice();
        if (money != null) {
            params.put("total_fee", toFen(money));
        }
        String sign = WeixinUtil.createSign(params, key);
        params.put("sign", sign);

        try {
            String xml = WeixinUtil.mapToXml(params);
            Document resultDoc = WeixinUtil.post("https://api.mch.weixin.qq.com/pay/orderquery", xml);
            Map<String, String> returnParams = WeixinUtil.xmlToMap(resultDoc);

            // Returns the result
            String returnCode = returnParams.get("return_code");
            String resultCode = returnParams.get("result_code");
            String tradeState = returnParams.get("trade_state");
            if (SUCCESS.equals(returnCode) || SUCCESS.equals(resultCode)) {
                if (SUCCESS.equals(tradeState)) {
                    // Instead of changing the order status, you do it in an asynchronous notification
                    // The reason for this is that the query is user experience specific and may return FAIL when the order has already been paid (the asynchronous notification may have succeeded)
                    return SUCCESS;
                } else {
                    return "FAIL";
                }

            } else {
                return "FAIL";
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to query wechat Payment", e);
            return "FAIL";
        }


    }
}
