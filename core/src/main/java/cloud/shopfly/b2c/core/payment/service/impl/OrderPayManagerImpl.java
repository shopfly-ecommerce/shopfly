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
import cloud.shopfly.b2c.core.payment.model.dos.PaymentMethodDO;
import cloud.shopfly.b2c.core.payment.model.dto.PayParam;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.service.OrderPayManager;
import cloud.shopfly.b2c.core.payment.service.PaymentManager;
import cloud.shopfly.b2c.core.payment.service.PaymentMethodManager;
import cloud.shopfly.b2c.core.trade.order.model.dos.TradeDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.service.OrderQueryManager;
import cloud.shopfly.b2c.core.trade.order.service.TradeQueryManager;
import cloud.shopfly.b2c.core.trade.TradeErrorCode;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderDetailVO;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: Order payment
 * @date 2018/4/1617:10
 * @since v7.0.0
 */
@Service
public class OrderPayManagerImpl implements OrderPayManager {

    @Autowired
    
    private DaoSupport daoSupport;


    @Autowired
    private OrderQueryManager orderQueryManager;

    @Autowired
    private TradeQueryManager tradeQueryManager;

    @Autowired
    private PaymentManager paymentManager;

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());


    @Autowired
    private PaymentMethodManager paymentMethodManager;


    @Override
    public Map pay(PayParam param) {
        // Order status is detected and only confirmed orders can be paid
        boolean isLegitimate = true;
        if (param.getTradeType().equals(TradeType.order.name())) {
            isLegitimate = this.checkStatus(param.getSn(), TradeType.order, 0);
        } else {
            isLegitimate = this.checkStatus(param.getSn(), TradeType.trade, 0);
        }
        // Check whether the order status has been confirmed to order payment, or throw an exception
        if (!isLegitimate) {
            throw new ServiceException(PaymentErrorCode.E506.code(), "The transaction status is incorrect and cannot be paid");
        }
        String sql = "";
        String updateSql = "";
        // trading
        if (TradeType.trade.name().equals(param.getTradeType())) {
            sql = "select total_price as order_price from es_trade  where trade_sn = ?";
            updateSql = "update es_order set payment_plugin_id = ?,payment_method_name = ? where trade_sn = ?";
        } else {
            sql = "select order_price  from es_order  where sn = ?";
            updateSql = "update es_order  set payment_plugin_id = ?,payment_method_name = ? where sn = ?";
        }

        Double price = this.daoSupport.queryForDouble(sql, param.getSn());

        PayBill bill = new PayBill();
        bill.setPluginId(param.getPaymentPluginId());
        bill.setOrderPrice(price);
        bill.setPayMode(param.getPayMode());
        bill.setTradeType(TradeType.valueOf(param.getTradeType()));
        bill.setClientType(ClientType.valueOf(param.getClientType()));
        bill.setSn(param.getSn());
        PaymentMethodDO paymentMethod = this.paymentMethodManager.getByPluginId(param.getPaymentPluginId());

        // Modify the payment method of the order
        this.daoSupport.execute(updateSql, paymentMethod.getPluginId(), paymentMethod.getMethodName(), param.getSn());

        return paymentManager.pay(bill);

    }


    /**
     * Detection of trading（The order）Whether it can be paid
     *
     * @param sn        The order（trading）No.
     * @param tradeType Transaction type
     * @param times     The number of
     * @return Whether it can be paid
     */
    private boolean checkStatus(String sn, TradeType tradeType, Integer times) {
        try {
            // If more than three times it returns false and cannot be paid
            if (times >= 3) {
                return false;
            }
            // Order or transaction status
            String status;
            if (tradeType.equals(TradeType.order)) {
                // Get the details of the order to determine whether the order is confirmed
                OrderDetailVO orderDetailVO = orderQueryManager.getModel(sn, null);
                if (orderDetailVO != null) {
                    status = orderDetailVO.getOrderStatus();
                } else {
                    throw new ServiceException(TradeErrorCode.E459.code(), "This order does not exist");
                }
            } else {
                // Obtain transaction details to determine whether the transaction is confirmed
                TradeDO tradeDO = tradeQueryManager.getModel(sn);
                if (tradeDO != null) {
                    status = tradeDO.getTradeStatus();
                } else {
                    throw new ServiceException(TradeErrorCode.E458.code(), "This transaction does not exist");
                }
            }
            // Verify that the transaction or order status is confirmed to be payable
            if (!status.equals(OrderStatusEnum.CONFIRM.value())) {
                Thread.sleep(1000);
                return this.checkStatus(sn, tradeType, ++times);
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error("Check whether the order can be paid,Order could not be paid, retry detection" + times + ",Time, message" + e.getMessage());
            this.checkStatus(sn, tradeType, ++times);
        }
        return false;
    }
}
