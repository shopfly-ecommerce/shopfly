/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.service.impl;

import dev.shopflix.core.payment.PaymentErrorCode;
import dev.shopflix.core.payment.model.dos.PaymentBillDO;
import dev.shopflix.core.payment.model.vo.RefundBill;
import dev.shopflix.core.payment.service.AbstractPaymentPlugin;
import dev.shopflix.core.payment.service.PaymentBillManager;
import dev.shopflix.core.payment.service.PaymentPluginManager;
import dev.shopflix.core.payment.service.RefundManager;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.logs.Debugger;
import dev.shopflix.framework.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 退款接口实现
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-15
 */
@Service
public class RefundManagerImpl implements RefundManager {

    @Autowired
    private List<PaymentPluginManager> paymentPluginList;


    @Autowired
    private PaymentBillManager paymentBillManager;


    @Autowired
    private Cache cache;

    @Autowired
    private Debugger debugger;


    @Override
    public Map originRefund(String returnTradeNo, String refundSn, Double refundPrice) {

        debugger.log("发起退款");
        //查询对应的支付流水，找到对应的支付参数
        PaymentBillDO payBill = this.paymentBillManager.getBillByReturnTradeNo(returnTradeNo);

        if (payBill == null) {
            debugger.log("第三方[" + returnTradeNo + "]支付账单找不到");
            throw new ServiceException(PaymentErrorCode.E504.code(), "支付账单不存在");
        }
        PaymentPluginManager plugin = findPlugin(payBill.getPaymentPluginId());


        RefundBill refundBill = new RefundBill();
        //支付参数
        Map map = JsonUtil.jsonToObject(payBill.getPayConfig(), Map.class);

        List<Map> list = (List<Map>) map.get("config_list");
        Map<String, String> result = new HashMap<>(list.size());
        if (list != null) {
            for (Map item : list) {
                result.put(item.get("name").toString(), item.get("value").toString());
            }
        }
        refundBill.setConfigMap(result);
        refundBill.setRefundPrice(refundPrice);
        refundBill.setRefundSn(refundSn);
        refundBill.setReturnTradeNo(returnTradeNo);
        refundBill.setTradePrice(payBill.getTradePrice());

        debugger.log("调起[" + plugin + "]");
        boolean refundResult = plugin.onTradeRefund(refundBill);
        debugger.log("退款结果：" + refundResult);

        Map hashMap = new HashMap(2);
        if (refundResult) {
            hashMap.put("result", "true");
        } else {
            hashMap.put("result", "false");
            String failKey = AbstractPaymentPlugin.REFUND_ERROR_MESSAGE + "_" + refundBill.getRefundSn();
            hashMap.put("fail_reason", this.cache.get(failKey));
            this.cache.remove(failKey);
        }
        return hashMap;
    }

    @Override
    public String queryRefundStatus(String returnTradeNo, String refundSn) {
        //查询对应的支付流水，找到对应的支付参数
        PaymentBillDO payBill = this.paymentBillManager.getBillByReturnTradeNo(returnTradeNo);
        RefundBill refundBill = new RefundBill();
        //支付参数
        Map map = JsonUtil.jsonToObject(payBill.getPayConfig(), Map.class);
        List<Map> list = (List<Map>) map.get("config_list");
        Map<String, String> result = new HashMap<>(list.size());
        if (list != null) {
            for (Map item : list) {
                result.put(item.get("name").toString(), item.get("value").toString());
            }
        }
        refundBill.setConfigMap(result);
        refundBill.setRefundSn(refundSn);
        refundBill.setReturnTradeNo(returnTradeNo);

        PaymentPluginManager plugin = findPlugin(payBill.getPaymentPluginId());

        return plugin.queryRefundStatus(refundBill);
    }

    /**
     * 查找支付插件
     *
     * @param pluginId
     * @return
     */
    private PaymentPluginManager findPlugin(String pluginId) {
        for (PaymentPluginManager plugin : paymentPluginList) {
            if (plugin.getPluginId().equals(pluginId)) {
                return plugin;
            }
        }
        return null;
    }

}
