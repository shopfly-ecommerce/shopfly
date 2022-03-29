/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.service;

import dev.shopflix.core.payment.model.enums.ClientType;
import dev.shopflix.core.payment.model.enums.TradeType;
import dev.shopflix.core.payment.model.vo.ClientConfig;
import dev.shopflix.core.payment.model.vo.PayBill;
import dev.shopflix.core.payment.model.vo.RefundBill;

import java.util.List;
import java.util.Map;

/**
 * 支付插件
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-11 16:06:57
 */
public interface PaymentPluginManager {


    /**
     * 获取支付插件id
     *
     * @return
     */
    String getPluginId();

    /**
     * 支付名称
     *
     * @return
     */
    String getPluginName();

    /**
     * 自定义客户端配置文件
     *
     * @return
     */
    List<ClientConfig> definitionClientConfig();

    /**
     * 支付
     *
     * @param bill
     * @return
     */
    Map pay(PayBill bill);

    /**
     * 同步回调
     *
     * @param tradeType
     */
    void onReturn(TradeType tradeType);

    /**
     * 异步回调
     *
     * @param tradeType
     * @param clientType
     * @return
     */
    String onCallback(TradeType tradeType, ClientType clientType);

    /**
     * 主动查询支付结果
     *
     * @param bill
     * @return
     */
    String onQuery(PayBill bill);


    /**
     * 退款，原路退回
     *
     * @param bill
     * @return
     */
    boolean onTradeRefund(RefundBill bill);

    /**
     * 查询退款状态
     *
     * @param bill
     * @return
     */
    String queryRefundStatus(RefundBill bill);

    /**
     * 是否支持原路退回   0 不支持  1支持
     *
     * @return
     */
    Integer getIsRetrace();

}
