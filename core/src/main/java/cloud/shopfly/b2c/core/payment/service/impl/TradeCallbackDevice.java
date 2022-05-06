/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
