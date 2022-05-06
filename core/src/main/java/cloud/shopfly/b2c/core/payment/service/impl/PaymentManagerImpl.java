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
import cloud.shopfly.b2c.core.payment.model.dos.PaymentMethodDO;
import cloud.shopfly.b2c.core.payment.model.dto.PayParam;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.service.PaymentBillManager;
import cloud.shopfly.b2c.core.payment.service.PaymentManager;
import cloud.shopfly.b2c.core.payment.service.PaymentMethodManager;
import cloud.shopfly.b2c.core.payment.service.PaymentPluginManager;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 支付账单管理实现
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-10
 */
@Service
public class PaymentManagerImpl implements PaymentManager {

    @Autowired
    private PaymentMethodManager paymentMethodManager;

    @Autowired
    private PaymentBillManager paymentBillManager;

    @Autowired
    private List<PaymentPluginManager> paymentPluginList;

    @Autowired
    private Debugger debugger;

    @Override
    public Map pay(PayBill bill) {

        debugger.log("准备对以下bill进行支付");
        debugger.log(bill.toString());

        // 查询支付方式
        PaymentMethodDO paymentMethod = this.paymentMethodManager.getByPluginId(bill.getPluginId());
        if (paymentMethod == null) {
            debugger.log("未找到相应的支付方式[" + bill.getPluginId() + "]");
            throw new ServiceException(PaymentErrorCode.E501.code(), "未找到相应的支付方式[" + bill.getPluginId() + "]");
        }

        //使用系统时间加4位随机数生成流程号
        String billSn = System.currentTimeMillis() + "" + StringUtil.getRandStr(4);

        debugger.log("为账单生成流程号：" + billSn);

        // 生成支付流水
        PaymentBillDO paymentBill = new PaymentBillDO(bill.getSn(), billSn, null, 0, bill.getTradeType().name(), paymentMethod.getMethodName(), bill.getOrderPrice(), paymentMethod.getPluginId());
        //保存支付参数

        switch (bill.getClientType()) {
            case PC:
                paymentBill.setPayConfig(paymentMethod.getPcConfig());
                break;
            case WAP:
                paymentBill.setPayConfig(paymentMethod.getWapConfig());
                break;
            case NATIVE:
                paymentBill.setPayConfig(paymentMethod.getAppNativeConfig());
                break;
            case REACT:
                paymentBill.setPayConfig(paymentMethod.getAppReactConfig());
                break;
            case MINI:
                paymentBill.setPayConfig(paymentMethod.getMiniConfig());
                break;
            default:
                break;
        }

        //将支付单入库
        this.paymentBillManager.add(paymentBill);

        debugger.log("账单入库成功");

        bill.setBillSn(billSn);

        //调起相应的支付插件
        PaymentPluginManager paymentPlugin = this.findPlugin(bill.getPluginId());

        debugger.log("开始调起支付插件：" + bill.getPluginId());
        return paymentPlugin.pay(bill);
    }


    @Override
    public void payReturn(TradeType tradeType, String paymentPluginId) {
        PaymentPluginManager plugin = this.findPlugin(paymentPluginId);
        if (plugin != null) {
            plugin.onReturn(tradeType);
        }
    }

    @Override
    public String payCallback(TradeType tradeType, String paymentPluginId, ClientType clientType) {
        PaymentPluginManager plugin = this.findPlugin(paymentPluginId);
        if (plugin != null) {
            return plugin.onCallback(tradeType, clientType);
        }
        return "fail";
    }

    @Override
    public String queryResult(PayParam param) {

        PaymentPluginManager plugin = this.findPlugin(param.getPaymentPluginId());

        PaymentBillDO paymentBill = this.paymentBillManager.getBillBySnAndTradeType(param.getSn(), param.getTradeType());
        //已经支付回调，则不需要查询
        if (paymentBill.getIsPay() == 1) {
            return "success";
        }

        PayBill bill = new PayBill();
        bill.setBillSn(paymentBill.getOutTradeNo());
        bill.setClientType(ClientType.valueOf(param.getClientType()));
        bill.setTradeType(TradeType.valueOf(param.getTradeType()));
        bill.setSn(param.getSn());
        bill.setOrderPrice(paymentBill.getTradePrice());

        return plugin.onQuery(bill);
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
