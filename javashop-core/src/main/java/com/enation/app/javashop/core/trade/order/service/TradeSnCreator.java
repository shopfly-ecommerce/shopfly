/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.service;

/**
 *  交易订单号创建
 * @author Snow create in 2018/4/9
 * @version v2.0
 * @since v7.0.0
 */
public interface TradeSnCreator {

    /**
     * 生成交易编号  格式如：20171022000011
     * @return 交易编号
     */
    String generateTradeSn();


    /**
     * 生成订单编号  格式如：20171022000011
     * @return 订单编号
     */
    String generateOrderSn();

    /**
     * 生成付款流水号  格式如：20171022000011
     * @return 订单编号
     */
    String generatePayLogSn();

    /**
     * 清除
     */
    void cleanCache();

}
