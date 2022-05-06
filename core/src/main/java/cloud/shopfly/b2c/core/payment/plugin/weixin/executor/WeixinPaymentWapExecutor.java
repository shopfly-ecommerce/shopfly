/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * @Description: 微信wap端
 * @date 2018/4/1810:12
 * @since v7.0.0
 */
@Service
public class WeixinPaymentWapExecutor extends WeixinPuginConfig {

    @Autowired
    private DomainHelper domainHelper;

    /**
     * 支付
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
            // 返回结果
            String resultCode = map.get("result_code");
            if (AbstractPaymentPlugin.SUCCESS.equals(resultCode)) {
                String codeUrl = map.get("mweb_url");
                String redirect_url=  getPayWapSuccessUrl(bill.getTradeType().name(), bill.getBillSn(),bill.getPluginId());
                result.put("gateway_url", codeUrl + "&redirect_url=" +redirect_url);
                return result;
            }
        } catch (Exception e) {
            this.logger.error("生成参数失败", e);

        }
        return null;

    }


    /**
     * 获取支付成功调取页面
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
     * 获取支付成功调取页面
     *
     * @param tradeType
     * @return
     */
    private String getPayWapSuccessUrl(String tradeType, String subSn,String pluginId) {

        //uniapp-h5 支付成功结果页
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
