/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.service.impl;

import dev.shopflix.core.payment.PaymentErrorCode;
import dev.shopflix.core.payment.model.dos.PaymentBillDO;
import dev.shopflix.core.payment.model.dos.PaymentMethodDO;
import dev.shopflix.core.payment.model.dto.PayParam;
import dev.shopflix.core.payment.model.enums.ClientType;
import dev.shopflix.core.payment.model.enums.TradeType;
import dev.shopflix.core.payment.model.vo.PayBill;
import dev.shopflix.core.payment.service.PaymentBillManager;
import dev.shopflix.core.payment.service.PaymentManager;
import dev.shopflix.core.payment.service.PaymentMethodManager;
import dev.shopflix.core.payment.service.PaymentPluginManager;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.logs.Debugger;
import dev.shopflix.framework.util.StringUtil;
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