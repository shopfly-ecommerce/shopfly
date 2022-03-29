/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.model.enums;

/**
 * 交易类型
 *
 * @author kingapex
 * @version 1.0
 * @since pangu1.0
 * 2017年4月5日下午5:12:55
 */
public enum TradeType {

    /**
     * 订单类型
     */
    order("订单"),

    /**
     * 交易类型
     */
    trade("交易"),

    /**
     * 调试器类型（程序调试用，不会人为用到）
     */
    debugger("调试器");

    private String description;

    TradeType(String description) {
        this.description = description;
    }
}
