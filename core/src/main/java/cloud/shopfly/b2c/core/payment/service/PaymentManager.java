/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.payment.service;

import cloud.shopfly.b2c.core.payment.model.dto.PayParam;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;

import java.util.Map;

/**
 * 全局统一支付接口
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-10
 */
public interface PaymentManager {

    /**
     * 创建支付账单并支付
     *
     * @param payBill
     * @return
     */
    Map pay(PayBill payBill);


    /**
     * 同步回调方法
     *
     * @param tradeType
     * @param paymentPluginId
     */
    void payReturn(TradeType tradeType, String paymentPluginId);


    /**
     * 支付异步回调
     *
     * @param tradeType
     * @param paymentPluginId
     * @param clientType
     * @return
     */
    String payCallback(TradeType tradeType, String paymentPluginId, ClientType clientType);

    /**
     * 查询支付结果并更新订单状态
     *
     * @param param
     * @return
     */
    String queryResult(PayParam param);


}
