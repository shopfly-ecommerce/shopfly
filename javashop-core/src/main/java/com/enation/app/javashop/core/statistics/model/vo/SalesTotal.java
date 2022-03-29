/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.statistics.model.vo;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 收入总览
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-04-17 上午12:15
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SalesTotal implements Serializable {

    @ApiModelProperty(value = "收入金额")
    @Column(name = "receive_money")
    private Double receiveMoney;
    @ApiModelProperty(value = "退款金额")
    @Column(name = "refund_money")
    private Double refundMoney;
    @ApiModelProperty(value = "最终金额")
    @Column(name = "real_money")
    private Double realMoney;

    public Double getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(Double receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public Double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Double getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Double realMoney) {
        this.realMoney = realMoney;
    }

    @Override
    public String toString() {
        return "SalesTotal{" +
                "receiveMoney='" + receiveMoney + '\'' +
                ", refundMoney='" + refundMoney + '\'' +
                ", realMoney='" + realMoney + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalesTotal that = (SalesTotal) o;

        if (receiveMoney != null ? !receiveMoney.equals(that.receiveMoney) : that.receiveMoney != null) {
            return false;
        }
        if (refundMoney != null ? !refundMoney.equals(that.refundMoney) : that.refundMoney != null) {
            return false;
        }
        return realMoney != null ? realMoney.equals(that.realMoney) : that.realMoney == null;
    }

    @Override
    public int hashCode() {
        int result = receiveMoney != null ? receiveMoney.hashCode() : 0;
        result = 31 * result + (refundMoney != null ? refundMoney.hashCode() : 0);
        result = 31 * result + (realMoney != null ? realMoney.hashCode() : 0);
        return result;
    }
}
