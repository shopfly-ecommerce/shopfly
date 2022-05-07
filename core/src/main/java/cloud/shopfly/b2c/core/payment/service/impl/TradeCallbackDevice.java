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

import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.payment.PaymentErrorCode;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.service.PaymentCallbackDevice;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    private DaoSupport daoSupport;

    @Autowired
    private OrderClient orderClient;

    @Override
    public void paySuccess(String outTradeNo, String returnTradeNo, double payPrice) {
        // Payment transaction
        // Modify the order transaction number
        String sql = "update es_order set pay_order_no = ? where trade_sn = ? ";
        this.daoSupport.execute(sql,returnTradeNo, outTradeNo);

        // Update the payment status of the order
        List<OrderDetailDTO> orderList = orderClient.getOrderByTradeSn(outTradeNo);
        // Determine if the transaction amount is correct
        Double  totalPrice = 0d;
        for(OrderDetailDTO orderDetailDTO : orderList){
            totalPrice = CurrencyUtil.add(totalPrice,orderDetailDTO.getOrderPrice());
        }

        if(!totalPrice.equals(payPrice)){
            throw new ServiceException(PaymentErrorCode.E503.code(),"Inconsistent amounts");
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
