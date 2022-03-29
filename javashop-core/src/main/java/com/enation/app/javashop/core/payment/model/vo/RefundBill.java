/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.model.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 原路退回使用vo
 * @date 2018/4/2314:12
 * @since v7.0.0
 */
public class RefundBill implements Serializable {

    private static final long serialVersionUID = 6902367702212390171L;

    /**
     * 退款编号
     */
    private String refundSn;

    /**
     * 退款金额
     */
    private Double refundPrice;

    /**
     * 交易金额
     */
    private Double tradePrice;

    /**
     * 第三方订单号
     */
    private String returnTradeNo;

    /**
     * 支付时使用的参数
     */
    private Map<String, String> configMap;


    public String getRefundSn() {
        return refundSn;
    }

    public void setRefundSn(String refundSn) {
        this.refundSn = refundSn;
    }

    public Double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public Double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(Double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getReturnTradeNo() {
        return returnTradeNo;
    }

    public void setReturnTradeNo(String returnTradeNo) {
        this.returnTradeNo = returnTradeNo;
    }

    public Map<String, String> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, String> configMap) {
        this.configMap = configMap;
    }
}
