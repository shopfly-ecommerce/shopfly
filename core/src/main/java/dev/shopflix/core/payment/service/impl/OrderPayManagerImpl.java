/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.service.impl;

import dev.shopflix.core.payment.PaymentErrorCode;
import dev.shopflix.core.payment.model.dos.PaymentMethodDO;
import dev.shopflix.core.payment.model.dto.PayParam;
import dev.shopflix.core.payment.model.enums.ClientType;
import dev.shopflix.core.payment.model.enums.TradeType;
import dev.shopflix.core.payment.model.vo.PayBill;
import dev.shopflix.core.payment.service.OrderPayManager;
import dev.shopflix.core.payment.service.PaymentManager;
import dev.shopflix.core.payment.service.PaymentMethodManager;
import dev.shopflix.core.trade.TradeErrorCode;
import dev.shopflix.core.trade.order.model.dos.TradeDO;
import dev.shopflix.core.trade.order.model.enums.OrderStatusEnum;
import dev.shopflix.core.trade.order.model.vo.OrderDetailVO;
import dev.shopflix.core.trade.order.service.OrderQueryManager;
import dev.shopflix.core.trade.order.service.TradeQueryManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 订单支付
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
        //对订单状态进行检测，只有已确认的订单才可以进行支付
        boolean isLegitimate = true;
        if (param.getTradeType().equals(TradeType.order.name())) {
            isLegitimate = this.checkStatus(param.getSn(), TradeType.order, 0);
        } else {
            isLegitimate = this.checkStatus(param.getSn(), TradeType.trade, 0);
        }
        //判断订单状态是否已确认可以订单支付，否抛出异常
        if (!isLegitimate) {
            throw new ServiceException(PaymentErrorCode.E506.code(), "该交易状态不正确，无法支付");
        }
        String sql = "";
        String updateSql = "";
        // 交易
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

        //修改订单的支付方式
        this.daoSupport.execute(updateSql, paymentMethod.getPluginId(), paymentMethod.getMethodName(), param.getSn());

        return paymentManager.pay(bill);

    }


    /**
     * 检测交易（订单）是否可以被支付
     *
     * @param sn        订单（交易）号
     * @param tradeType 交易类型
     * @param times     次数
     * @return 是否可以被支付
     */
    private boolean checkStatus(String sn, TradeType tradeType, Integer times) {
        try {
            //如果超过三次则直接返回false，不能支付
            if (times >= 3) {
                return false;
            }
            //订单或者交易状态
            String status;
            if (tradeType.equals(TradeType.order)) {
                //获取订单详情，判断订单是否是已确认状态
                OrderDetailVO orderDetailVO = orderQueryManager.getModel(sn, null);
                if (orderDetailVO != null) {
                    status = orderDetailVO.getOrderStatus();
                } else {
                    throw new ServiceException(TradeErrorCode.E459.code(), "此订单不存在");
                }
            } else {
                //获取交易详情，判断交易是否是已确认状态
                TradeDO tradeDO = tradeQueryManager.getModel(sn);
                if (tradeDO != null) {
                    status = tradeDO.getTradeStatus();
                } else {
                    throw new ServiceException(TradeErrorCode.E458.code(), "此交易不存在");
                }
            }
            //检验交易或者订单状态是否是已确认可被支付
            if (!status.equals(OrderStatusEnum.CONFIRM.value())) {
                Thread.sleep(1000);
                return this.checkStatus(sn, tradeType, ++times);
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error("检测订单是否可被支付,订单不可被支付，重试检测" + times + ",次，消息" + e.getMessage());
            this.checkStatus(sn, tradeType, ++times);
        }
        return false;
    }
}
