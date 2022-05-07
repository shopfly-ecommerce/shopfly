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

import cloud.shopfly.b2c.core.payment.model.vo.RefundBill;
import cloud.shopfly.b2c.core.payment.plugin.weixin.WeixinPayConfig;
import cloud.shopfly.b2c.core.payment.plugin.weixin.WeixinPuginConfig;
import cloud.shopfly.b2c.core.payment.plugin.weixin.WeixinUtil;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.core.payment.service.AbstractPaymentPlugin;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author fk
 * @version v1.0
 * @Description: WeChat refund
 * @date 2018/4/17 14:55
 * @since v7.0.0
 */
@Service
public class WeixinRefundExecutor extends WeixinPuginConfig {

    @Autowired
    private Cache cache;

    /**
     * The way back
     *
     * @param bill
     * @return
     */
    public boolean returnPay(RefundBill bill) {

        // Pay parameters
        WeixinPayConfig config = this.getConfig(bill.getConfigMap());

        Map params = new TreeMap();
        params.put("appid", config.getAppId());
        params.put("mch_id", config.getMchId());
        params.put("nonce_str", StringUtil.getRandStr(10));
        params.put("transaction_id", bill.getReturnTradeNo());
        params.put("refund_fee", toFen(bill.getRefundPrice()));
        params.put("total_fee", toFen(bill.getTradePrice()));
        params.put("out_refund_no", bill.getRefundSn());
        String sign = WeixinUtil.createSign(params, config.getKey());
        params.put("sign", sign);

        try {
            String xml = WeixinUtil.mapToXml(params);
            File file = new File(config.getP12Path());
            if (!file.exists()) {
                this.cache.put(AbstractPaymentPlugin.REFUND_ERROR_MESSAGE + "_" + bill.getRefundSn(), "The certificate path could not be found" + config.getP12Path() + "Contact the administrator to correctly configure the configuration");
                return false;
            }

            Document resultDoc = WeixinUtil.verifyCertPost("https://api.mch.weixin.qq.com/secapi/pay/refund", xml, config.getMchId(), config.getP12Path());
            Element rootEl = resultDoc.getRootElement();
            // Returns the result
            String returnCode = rootEl.element("return_code").getText();
            if (AbstractPaymentPlugin.SUCCESS.equals(returnCode)) {

                // At this time, it cannot be said that the refund is successful
                String resultCode = rootEl.element("result_code").getText();
                if (AbstractPaymentPlugin.SUCCESS.equals(resultCode)) {
                    return true;
                } else {
                    // Error code
                    String errCode = rootEl.element("err_code").getText();
                    // Error code description
                    String errCodeDes = rootEl.element("err_code_des").getText();

                    String failReason = "Please contact the administrator with refund error information：" + errCode + "," + errCodeDes;

                    this.cache.put(AbstractPaymentPlugin.REFUND_ERROR_MESSAGE + "_" + bill.getRefundSn(), failReason);

                    return false;
                }

            } else {
                String failReason = "The original route fails to be returned. Contact the administrator to check parameter Settings";
                Element returnMsg = rootEl.element("return_msg");
                if (returnMsg != null) {
                    failReason = "Please contact the administrator with refund error information：" + returnMsg.getText();
                }
                this.cache.put(AbstractPaymentPlugin.REFUND_ERROR_MESSAGE + "_" + bill.getRefundSn(), failReason);
                return false;
            }

        } catch (Exception e) {
            this.logger.error("Wechat refund failed", e);
            this.cache.put(AbstractPaymentPlugin.REFUND_ERROR_MESSAGE + "_" + bill.getRefundSn(), "abnormal");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Querying refund Status
     *
     * @param bill
     * @return
     */
    public String queryRefundStatus(RefundBill bill) {

        // Pay parameters
        WeixinPayConfig config = this.getConfig(bill.getConfigMap());

        Map params = new TreeMap();
        params.put("appid", config.getAppId());
        params.put("mch_id", config.getMchId());
        params.put("nonce_str", StringUtil.getRandStr(10));
        // Internal order number of merchant system
        params.put("out_refund_no", bill.getRefundSn());
        // The signature
        String sign = WeixinUtil.createSign(params, config.getKey());
        params.put("sign", sign);
        try {
            String xml = WeixinUtil.mapToXml(params);
            Document resultDoc = WeixinUtil.post("https://api.mch.weixin.qq.com/pay/refundquery", xml);

            Map<String, String> resultMap = WeixinUtil.xmlToMap(resultDoc);

            if (AbstractPaymentPlugin.SUCCESS.equals(resultMap.get("return_code"))) {
                // Actual refund status
                String status = resultMap.get("refund_status_0");
//				The refund status： SUCCESS—Refund successREFUNDCLOSE—Refund closed.  PROCESSING—Refund processingCHANGE—Refund abnormal
                if (AbstractPaymentPlugin.SUCCESS.equals(status)) {
                    // Refund success
                    return RefundStatusEnum.COMPLETED.value();
                } else if ("PROCESSING".equals(status)) {
                    // A refund of
                    return RefundStatusEnum.REFUNDING.value();
                } else {
                    // Refund failure
                    return RefundStatusEnum.REFUNDFAIL.value();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return RefundStatusEnum.REFUNDING.value();
    }

    /**
     * Set alipay parameters
     *
     * @param config
     */
    private WeixinPayConfig getConfig(Map<String, String> config) {

        WeixinPayConfig weixinPayConfig = new WeixinPayConfig();
        weixinPayConfig.setAppId(config.get("appid"));
        weixinPayConfig.setMchId(config.get("mchid"));
        weixinPayConfig.setKey(config.get("key"));
        weixinPayConfig.setP12Path(config.get("p12_path"));

        return weixinPayConfig;
    }


}
