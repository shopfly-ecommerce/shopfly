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

import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.service.AbstractPaymentPlugin;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.dom4j.Document;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 微信相关config
 * @date 2018/4/1810:14
 * @since v7.0.0
 */
public class WeixinPuginConfig extends AbstractPaymentPlugin {

    public static final String OPENID_SESSION_KEY = "weixin_openid";
    public static final String UNIONID_SESSION_KEY = "weixin_unionid";
    public static final String QR_URL_PREFIX = "weixin://wxpay/bizpayurl[?]pr=";
    public static final String CACHE_KEY_PREFIX = "pay_";

    public final String siteName = "shopfly商城订单";


    @Override
    protected String getPluginId() {

        return "weixinPayPlugin";
    }


    /**
     * double转成分
     *
     * @param money
     * @return
     */
    protected String toFen(Double money) {
        String value = BigDecimal.valueOf(money).multiply(new BigDecimal(100)).toString();

        NumberFormat numberFormat = new DecimalFormat("##");

        return numberFormat.format(new BigDecimal(value));
    }


    /**
     * 组装参数生成预付订单
     *
     * @param bill
     * @param params
     * @return 微信返回信息和支付参数
     */
    protected Map<String, String> createUnifiedOrder(PayBill bill, Map<String, String> params) {

        Map<String, String> map = this.getConfig(bill.getClientType());

        WeixinPayConfig config = new WeixinPayConfig();
        config.setAppId(map.get("appid"));
        config.setMchId(map.get("mchid"));
        config.setKey(map.get("key"));

        params.put("appid", config.getAppId());
        params.put("mch_id", config.getMchId());
        params.put("nonce_str", StringUtil.getRandStr(10));
        params.put("body", siteName);
        params.put("out_trade_no", bill.getBillSn());
        // 应付转为分
        Double money = bill.getOrderPrice();
        if (money != null) {
            params.put("total_fee", toFen(money));
        }
        params.put("notify_url", this.getCallBackUrl(bill.getTradeType(), bill.getClientType()));
        if (logger.isDebugEnabled()) {
            logger.info("微信回调地址：" + this.getCallBackUrl(bill.getTradeType(), bill.getClientType()));
        }

        String sign = WeixinUtil.createSign(params, config.getKey());
        params.put("sign", sign);
        try {
            String xml = WeixinUtil.mapToXml(params);

            if (logger.isDebugEnabled()) {
                logger.info("微信支付请求参数如下：");
                logger.info(xml);
            }

            Document resultDoc = WeixinUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder", xml);

            Map resultMap = WeixinUtil.xmlToMap(resultDoc);

            xml = WeixinUtil.mapToXml(resultMap);
            logger.debug("微信返回值为：");
            logger.debug(xml);


            resultMap.putAll(map);

            return resultMap;
        } catch (Exception e) {
            this.logger.error("生成参数失败", e);
        }

        return null;
    }

    /**
     * 获取ip
     *
     * @return
     */
    protected String getIpAddress() {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtil.isEmpty(ip)) {
            return request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Cdn-Src-Ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
