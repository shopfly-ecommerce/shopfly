/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.statistics.model.vo;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Revenue overview
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-04-17 In the morning12:15
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SalesTotal implements Serializable {

    @ApiModelProperty(value = "Income amount")
    @Column(name = "receive_money")
    private Double receiveMoney;
    @ApiModelProperty(value = "The refund amount")
    @Column(name = "refund_money")
    private Double refundMoney;
    @ApiModelProperty(value = "The final amount")
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
