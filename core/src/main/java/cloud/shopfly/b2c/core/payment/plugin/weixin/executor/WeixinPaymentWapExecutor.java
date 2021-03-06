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

import cloud.shopfly.b2c.core.payment.model.vo.Form;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.base.DomainHelper;
import cloud.shopfly.b2c.core.payment.plugin.weixin.WeixinPuginConfig;
import cloud.shopfly.b2c.core.payment.service.AbstractPaymentPlugin;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
public class WeixinPaymentWapExecutor extends WeixinPuginConfig {

    @Autowired
    private DomainHelper domainHelper;

    /**
     * pay
     *
     * @param bill
     * @return
     */
    public Map onPay(PayBill bill) {

        Map<String, String> params = new TreeMap<>();
        Map<String, String> result = new TreeMap<>();
        params.put("spbill_create_ip", getIpAddress());
        params.put("trade_type", "MWEB");

        try {

            Map<String, String> map = super.createUnifiedOrder(bill, params);
            Form form = new Form();
            // Returns the result
            String resultCode = map.get("result_code");
            if (AbstractPaymentPlugin.SUCCESS.equals(resultCode)) {
                String codeUrl = map.get("mweb_url");
                String redirect_url=  getPayWapSuccessUrl(bill.getTradeType().name(), bill.getBillSn(),bill.getPluginId());
                result.put("gateway_url", codeUrl + "&redirect_url=" +redirect_url);
                return result;
            }
        } catch (Exception e) {
            this.logger.error("Failed to generate parameters", e);

        }
        return null;

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

        return "http://" + serverName + portstr + contextPath + "/" + tradeType + "_" + outTradeNo + "_payment-wap-result.html";
    }

    /**
     * Obtain the page of successful payment retrieval
     *
     * @param tradeType
     * @return
     */
    private String getPayWapSuccessUrl(String tradeType, String subSn,String pluginId) {

        // Uniapp-h5 Payment result page
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();

        String redirectUri = "/order-module/cashier/cashier?";

        StringBuffer url = new StringBuffer(domainHelper.getMobileDomain());
        url.append(redirectUri);
        url.append("order_sn=" + subSn);

        url.append("&is_callback=yes&default_plugin_id="+pluginId);
        String result = "";
        try {
            result = URLEncoder.encode(url.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }



}
