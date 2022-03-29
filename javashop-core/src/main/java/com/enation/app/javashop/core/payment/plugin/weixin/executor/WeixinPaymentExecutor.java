/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.plugin.weixin.executor;

import com.enation.app.javashop.core.base.DomainHelper;
import com.enation.app.javashop.core.payment.model.enums.ClientType;
import com.enation.app.javashop.core.payment.model.enums.TradeType;
import com.enation.app.javashop.core.payment.model.vo.Form;
import com.enation.app.javashop.core.payment.model.vo.PayBill;
import com.enation.app.javashop.core.payment.plugin.weixin.WeixinPuginConfig;
import com.enation.app.javashop.core.payment.plugin.weixin.WeixinUtil;
import com.enation.app.javashop.framework.cache.Cache;
import com.enation.app.javashop.framework.context.ThreadContextHolder;
import com.enation.app.javashop.framework.logs.Debugger;
import com.enation.app.javashop.framework.util.CurrencyUtil;
import com.enation.app.javashop.framework.util.JsonUtil;
import com.enation.app.javashop.framework.util.StringUtil;
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
 * @Description: 微信pc端
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
     * 支付
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

            // 返回结果
            String resultCode = map.get("result_code");
            Form form = new Form();
            if (SUCCESS.equals(resultCode)) {
                debugger.log("创建预付订单成功");
                String codeUrl = map.get("code_url");
                String qr = codeUrl.replaceAll(QR_URL_PREFIX, "");
                String outTradeNo = bill.getBillSn();
                String gateWay = (domainHelper.getCallback() + "/order/pay/weixin/qrpage/" + outTradeNo + "/" + qr + "");
                debugger.log("生成gateway:");
                debugger.log(gateWay);
                result.put("bill_sn",outTradeNo);
                result.put("gateway_url",gateWay);

                return result;
            } else {
                debugger.log("创建预付订单失败");

            }
        } catch (Exception e) {
            this.logger.error("微信生成支付二维码错误", e);
        }
        return null;
    }

    /**
     * 异步回调
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
                logger.info("微信支付回调----->" + JsonUtil.objectToJson(params));
            }
            String returnCode = params.get("return_code");
            String resultCode = params.get("result_code");

            if (SUCCESS.equals(returnCode) && SUCCESS.equals(resultCode)) {

                String sign = WeixinUtil.createSign(params, key);
                if (sign.equals(params.get("sign"))) {

                    // 本商城交易号
                    String outTradeNo = params.get("out_trade_no");
                    // 微信支付订单号
                    String returnTradeNo = params.get("transaction_id");
                    // 支付金额
                    double payPrice = StringUtil.toDouble(params.get("total_fee"), 0d);
                    // 传回来的是分，转为元
                    payPrice = CurrencyUtil.mul(payPrice, 0.01);

                    if (logger.isDebugEnabled()) {
                        logger.info("支付成功:outTradeNo/returnTradeNo----->" + outTradeNo + "/" + returnTradeNo);
                    }
                    this.paySuccess(outTradeNo, returnTradeNo, tradeType, payPrice);

                    map.put("return_code", SUCCESS);
                    // 标记为成功
                    cache.put(CACHE_KEY_PREFIX + outTradeNo, "ok", 120);


                } else {
                    map.put("return_code", "FAIL");
                    map.put("return_msg", "签名失败");
                    this.logger.error("微信签名失败");
                }
            } else {
                map.put("return_code", "FAIL");
                this.logger.error("微信验签失败");
            }

        } catch (Exception e) {
            map.put("return_code", "FAIL");
            map.put("return_msg", "");
            this.logger.error("微信通知的结果为失败", e);
        }
        HttpServletResponse response = ThreadContextHolder.getHttpResponse();
        response.setHeader("Content-Type", "text/xml");
        try {
            return WeixinUtil.mapToXml(map);
        } catch (Exception e) {
            this.logger.error("微信通知的结果为失败", e);
            return "出现错误";
        }

    }

    /**
     * 查询账单状态
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
        // 应付转为分
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

            // 返回结果
            String returnCode = returnParams.get("return_code");
            String resultCode = returnParams.get("result_code");
            String tradeState = returnParams.get("trade_state");
            if (SUCCESS.equals(returnCode) || SUCCESS.equals(resultCode)) {
                if (SUCCESS.equals(tradeState)) {
                    //在这里不做更改订单状态的操作，而是在异步通知中来完成
                    //原因是：这个查询是用户体验相关的，在订单已经支付状态时（异步通知可能成功了），此处可能返回fail
                    return SUCCESS;
                } else {
                    return "FAIL";
                }

            } else {
                return "FAIL";
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("微信支付查询失败", e);
            return "FAIL";
        }


    }
}
