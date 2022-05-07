/*
 * Yi family of hui（Beijing）All Rights Reserved.
 * You may not use this file without permission.
 * The official address：www.javamall.com.cn
 */
package cloud.shopfly.b2c.core.payment.plugin.weixin.executor;

import cloud.shopfly.b2c.core.member.model.vo.Auth2Token;
import cloud.shopfly.b2c.core.payment.PaymentErrorCode;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.FormItem;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.plugin.weixin.WeixinPuginConfig;
import cloud.shopfly.b2c.core.payment.plugin.weixin.WeixinUtil;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author fk
 * @version v2.0
 * @Description: WeChatwapend
 * @date 2018/4/1810:12
 * @since v7.0.0
 */
@Service
public class WeixinPaymentJsapiExecutor extends WeixinPuginConfig {

    @Autowired
    private Cache cache;

    /**
     * pay
     *
     * @param bill
     * @return
     */
    public Map onPay(PayBill bill) {
        Map<String, String> params = new TreeMap<>();
        Map<String, String> resultMap = new TreeMap<>();
        params.put("spbill_create_ip", "0:0:0:0:0:0:0:1");
        params.put("trade_type", "JSAPI");

        String openid=null;
        Buyer buyer = UserContext.getBuyer();
        Auth2Token token = (Auth2Token) cache.get(CachePrefix.CONNECT_LOGIN.getPrefix() + buyer.getUuid());
        // To obtain the openid

        if (token != null) {
            openid = token.getOpneId();
        }

        // If not, try to get it from Request
        if (StringUtil.isEmpty(openid)) {
            openid = ThreadContextHolder.getHttpRequest().getParameter("openid");
        }

        if (StringUtil.isEmpty(openid)) {
            throw new ServiceException(PaymentErrorCode.E509.code(),PaymentErrorCode.E509.getDescribe());
        }

        params.put("openid", openid);

        String result = "";

        try {

            Map<String,String> map = super.createUnifiedOrder(bill,params);

            // Returns the result
            String returnCode = map.get("return_code");
            logger.debug("Wechat Pay returns results："+returnCode);
            if (SUCCESS.equals(returnCode)) {
                // The business code
                String resultCode = map.get("result_code");

                if (SUCCESS.equals(resultCode)) {
                    // Pre-paid order ID
                    String prepayId = map.get("prepay_id");
                    logger.debug("WeChat payid："+prepayId);
                    Map<String, String> weixinparams = new TreeMap();
                    weixinparams.put("appId", map.get("appid"));
                    weixinparams.put("nonceStr", StringUtil.getRandStr(10));
                    weixinparams.put("timeStamp", String.valueOf(DateUtil.getDateline()));
                    weixinparams.put("package", "prepay_id=" + prepayId);
                    weixinparams.put("signType", "MD5");
                    String sign = WeixinUtil.createSign(weixinparams, map.get("key"));
                    weixinparams.put("paySign", sign);
                    resultMap = weixinparams;
                    String outTradeNo = bill.getBillSn();
                    logger.debug("WeChat returnmap："+weixinparams);
                    result = this.getPayScript(prepayId, map.get("appid"), map.get("key"), outTradeNo, bill.getTradeType());
                    logger.debug("Wechat returns the result："+result);
                } else {

                    String errCode = map.get("err_code");
                    String errCodeDes = map.get("err_code_des");
                    result = "<script>alert('Payment unexpected error, please contact technical personnel:"
                            + errCode + "【" + errCodeDes + "】')</script>";
                    logger.error(result);
                }
            } else {
                result = "<script>alert('Payment unexpected error, please contact technical personnel:" + returnCode + "')</script>";
                if ("FAIL".equals(returnCode)) {
                    // The error message
                    String returnMsg = map.get("return_msg");
                    this.logger.error("Error returned from wechat" + returnCode + "["
                            + returnMsg + "]");
                }
            }
        } catch (Exception e) {
            this.logger.error("Wechat generated payment QR code error", e);
            return null;
        }

        resultMap.put("gateway_url",result);
        System.out.println(resultMap);
        return resultMap;

    }

    private List<FormItem> getItems(Map<String, String> weixinparams) {

        List<FormItem> items = new ArrayList<>();
        for (String key : weixinparams.keySet()) {

            FormItem item = new FormItem();
            item.setItemName(key);
            item.setItemValue(weixinparams.get(key));

            items.add(item);
        }

        return items;
    }


    /**
     * Obtain the page of successful payment retrieval
     *
     * @param tradeType
     * @param outTradeNo
     * @return
     */
    private String getPayWapSuccessUrl(String tradeType, String outTradeNo) {

        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String portstr = "";
        if (port != 80) {
            portstr = ":" + port;
        }
        String contextPath = request.getContextPath();
        if (logger.isDebugEnabled()) {
            logger.info("The payment success page is displayed：" + "http://" + serverName + portstr + contextPath + "/" + tradeType + "_" + outTradeNo + "_payment-wap-result.html");
        }
        return "http://" + serverName + portstr + contextPath + "/" + tradeType + "_" + outTradeNo + "_payment-wap-result.html";
    }

    /**
     * Generate payment scripts
     *
     * @param prepayId
     * @param appid
     * @param weixinkey
     * @param outTradeNo
     * @param tradeType
     * @return
     */
    private String getPayScript(String prepayId, String appid, String weixinkey, String outTradeNo, TradeType tradeType) {

        Map<String, String> params = new TreeMap();
        params.put("appId", appid);
        params.put("nonceStr", StringUtil.getRandStr(10));
        params.put("timestamp", String.valueOf(DateUtil.getDateline()));
        params.put("package", "prepay_id=" + prepayId);
        params.put("signType", "MD5");
        String sign = WeixinUtil.createSign(params, weixinkey);
        params.put("paySign", sign);


        StringBuffer payStr = new StringBuffer();
        payStr.append("WeixinJSBridge.invoke('getBrandWCPayRequest',{");

        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);

            if (i != 0) {
                payStr.append(",");
            }

            payStr.append("'" + key + "':'" + value + "'");
            i++;

        }

        payStr.append("}");

        payStr.append(",function(res){  if( 'get_brand_wcpay_request:ok'==res.err_msg ) { "
                + "alert('Pay for success'); "
                + "location.href='" + getPayWapSuccessUrl(tradeType.name(), outTradeNo) + "?operation=success';"
                + "}else{ alert('Pay for failure'); "
                + "location.href='" + getPayWapSuccessUrl(tradeType.name(), outTradeNo) + "?operation=fail';"
                + "} "
                + "}");

        payStr.append(");");

        return payStr.toString();
    }
}
