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
package cloud.shopfly.b2c.core.payment.service.impl;

import cloud.shopfly.b2c.core.payment.PaymentErrorCode;
import cloud.shopfly.b2c.core.payment.model.dos.PaymentBillDO;
import cloud.shopfly.b2c.core.payment.model.vo.RefundBill;
import cloud.shopfly.b2c.core.payment.service.AbstractPaymentPlugin;
import cloud.shopfly.b2c.core.payment.service.PaymentBillManager;
import cloud.shopfly.b2c.core.payment.service.PaymentPluginManager;
import cloud.shopfly.b2c.core.payment.service.RefundManager;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Realization of refund interface
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

        debugger.log("Initiate a refund");
        // Query the corresponding payment flow and find the corresponding payment parameters
        PaymentBillDO payBill = this.paymentBillManager.getBillByReturnTradeNo(returnTradeNo);

        if (payBill == null) {
            debugger.log("The third party[" + returnTradeNo + "]Payment bill cant be found");
            throw new ServiceException(PaymentErrorCode.E504.code(), "Paying bills does not exist");
        }
        PaymentPluginManager plugin = findPlugin(payBill.getPaymentPluginId());


        RefundBill refundBill = new RefundBill();
        // Pay parameters
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

        debugger.log("Tuned up[" + plugin + "]");
        boolean refundResult = plugin.onTradeRefund(refundBill);
        debugger.log("Results the refund：" + refundResult);

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
        // Query the corresponding payment flow and find the corresponding payment parameters
        PaymentBillDO payBill = this.paymentBillManager.getBillByReturnTradeNo(returnTradeNo);
        RefundBill refundBill = new RefundBill();
        // Pay parameters
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
     * Find payment plug-ins
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
