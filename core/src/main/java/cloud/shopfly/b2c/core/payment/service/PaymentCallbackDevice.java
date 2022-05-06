/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.payment.service;

import cloud.shopfly.b2c.core.payment.model.enums.TradeType;

/**
 * 支付回调器
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-16
 */

public interface PaymentCallbackDevice {


    /**
     * 第三方平台支付成功后回调的方法
     *
     * @param outTradeNo
     * @param returnTradeNo
     * @param payPrice
     */
    void paySuccess(String outTradeNo, String returnTradeNo, double payPrice);


    /**
     * 定义此回调器支持的交易类型
     *
     * @return
     */
    TradeType tradeType();
}
