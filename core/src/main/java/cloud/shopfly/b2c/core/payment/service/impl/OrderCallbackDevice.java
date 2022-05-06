/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.payment.service.impl;

import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.service.PaymentCallbackDevice;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单支付回调器
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-16
 */

@Service
public class OrderCallbackDevice implements PaymentCallbackDevice {


    @Autowired
    private OrderClient orderClient;

    /**
     * 调用订单client完成对订单支付状态的修改
     *
     * @param outTradeNo
     * @param returnTradeNo
     * @param payPrice
     */
    @Override
    public void paySuccess(String outTradeNo, String returnTradeNo, double payPrice) {
        orderClient.payOrder(outTradeNo, payPrice, returnTradeNo, OrderPermission.client.name());
    }

    /**
     * 定义交易类型
     *
     * @return
     */
    @Override
    public TradeType tradeType() {

        return TradeType.order;
    }

}
