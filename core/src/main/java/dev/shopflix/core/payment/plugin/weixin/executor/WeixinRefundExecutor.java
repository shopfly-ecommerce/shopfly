/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.plugin.weixin.executor;

import dev.shopflix.core.aftersale.model.enums.RefundStatusEnum;
import dev.shopflix.core.payment.model.vo.RefundBill;
import dev.shopflix.core.payment.plugin.weixin.WeixinPayConfig;
import dev.shopflix.core.payment.plugin.weixin.WeixinPuginConfig;
import dev.shopflix.core.payment.plugin.weixin.WeixinUtil;
import dev.shopflix.core.payment.service.AbstractPaymentPlugin;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.util.StringUtil;
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
 * @Description: 微信退款
 * @date 2018/4/17 14:55
 * @since v7.0.0
 */
@Service
public class WeixinRefundExecutor extends WeixinPuginConfig {

    @Autowired
    private Cache cache;

    /**
     * 原路退回
     *
     * @param bill
     * @return
     */
    public boolean returnPay(RefundBill bill) {

        // 支付参数
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
                this.cache.put(AbstractPaymentPlugin.REFUND_ERROR_MESSAGE + "_" + bill.getRefundSn(), "找不到证书路径" + config.getP12Path() + "，请联系管理员正确配置");
                return false;
            }

            Document resultDoc = WeixinUtil.verifyCertPost("https://api.mch.weixin.qq.com/secapi/pay/refund", xml, config.getMchId(), config.getP12Path());
            Element rootEl = resultDoc.getRootElement();
            // 返回结果
            String returnCode = rootEl.element("return_code").getText();
            if (AbstractPaymentPlugin.SUCCESS.equals(returnCode)) {

                //此时也不能表示退款成功
                String resultCode = rootEl.element("result_code").getText();
                if (AbstractPaymentPlugin.SUCCESS.equals(resultCode)) {
                    return true;
                } else {
                    //错误码
                    String errCode = rootEl.element("err_code").getText();
                    //错误代码描述
                    String errCodeDes = rootEl.element("err_code_des").getText();

                    String failReason = "请联系管理员并提供退款错误信息：" + errCode + "," + errCodeDes;

                    this.cache.put(AbstractPaymentPlugin.REFUND_ERROR_MESSAGE + "_" + bill.getRefundSn(), failReason);

                    return false;
                }

            } else {
                String failReason = "原路退回失败，请联系管理员检查参数配置";
                Element returnMsg = rootEl.element("return_msg");
                if (returnMsg != null) {
                    failReason = "请联系管理员并提供退款错误信息：" + returnMsg.getText();
                }
                this.cache.put(AbstractPaymentPlugin.REFUND_ERROR_MESSAGE + "_" + bill.getRefundSn(), failReason);
                return false;
            }

        } catch (Exception e) {
            this.logger.error("微信退款失败", e);
            this.cache.put(AbstractPaymentPlugin.REFUND_ERROR_MESSAGE + "_" + bill.getRefundSn(), "异常");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询退款状态
     *
     * @param bill
     * @return
     */
    public String queryRefundStatus(RefundBill bill) {

        // 支付参数
        WeixinPayConfig config = this.getConfig(bill.getConfigMap());

        Map params = new TreeMap();
        params.put("appid", config.getAppId());
        params.put("mch_id", config.getMchId());
        params.put("nonce_str", StringUtil.getRandStr(10));
        // 商户系统内部订单号
        params.put("out_refund_no", bill.getRefundSn());
        // 签名
        String sign = WeixinUtil.createSign(params, config.getKey());
        params.put("sign", sign);
        try {
            String xml = WeixinUtil.mapToXml(params);
            Document resultDoc = WeixinUtil.post("https://api.mch.weixin.qq.com/pay/refundquery", xml);

            Map<String, String> resultMap = WeixinUtil.xmlToMap(resultDoc);

            if (AbstractPaymentPlugin.SUCCESS.equals(resultMap.get("return_code"))) {
                // 实际退款状态
                String status = resultMap.get("refund_status_0");
//				退款状态： SUCCESS—退款成功  REFUNDCLOSE—退款关闭。  PROCESSING—退款处理中 CHANGE—退款异常
                if (AbstractPaymentPlugin.SUCCESS.equals(status)) {
                    //退款成功
                    return RefundStatusEnum.COMPLETED.value();
                } else if ("PROCESSING".equals(status)) {
                    //退款中
                    return RefundStatusEnum.REFUNDING.value();
                } else {
                    //退款失败
                    return RefundStatusEnum.REFUNDFAIL.value();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return RefundStatusEnum.REFUNDING.value();
    }

    /**
     * 设置支付宝参数
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
