/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.service.impl;

import com.enation.app.javashop.core.client.trade.OrderClient;
import com.enation.app.javashop.core.payment.PaymentErrorCode;
import com.enation.app.javashop.core.payment.model.enums.TradeType;
import com.enation.app.javashop.core.payment.service.PaymentCallbackDevice;
import com.enation.app.javashop.core.trade.cart.model.dos.OrderPermission;
import com.enation.app.javashop.core.trade.sdk.model.OrderDetailDTO;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.exception.ServiceException;
import com.enation.app.javashop.framework.util.CurrencyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-16
 */

@Service
public class TradeCallbackDevice implements PaymentCallbackDevice {


    @Autowired
    @Qualifier("tradeDaoSupport")
    private DaoSupport daoSupport;

    @Autowired
    private OrderClient orderClient;

    @Override
    public void paySuccess(String outTradeNo, String returnTradeNo, double payPrice) {
        //交易支付
        //修改订单交易号
        String sql = "update es_order set pay_order_no = ? where trade_sn = ? ";
        this.daoSupport.execute(sql,returnTradeNo, outTradeNo);

        //更新订单的支付状态
        List<OrderDetailDTO> orderList = orderClient.getOrderByTradeSn(outTradeNo);
        //判断交易的金额是否正确
        Double  totalPrice = 0d;
        for(OrderDetailDTO orderDetailDTO : orderList){
            totalPrice = CurrencyUtil.add(totalPrice,orderDetailDTO.getOrderPrice());
        }

        if(!totalPrice.equals(payPrice)){
            throw new ServiceException(PaymentErrorCode.E503.code(),"金额不一致");
        }

        for(OrderDetailDTO orderDetailDTO : orderList){
            orderClient.payOrder(orderDetailDTO.getSn(),orderDetailDTO.getNeedPayMoney(), returnTradeNo,OrderPermission.client.name());
        }
    }

    @Override
    public TradeType tradeType() {

        return TradeType.trade;
    }
}
