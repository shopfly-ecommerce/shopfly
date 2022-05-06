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
package cloud.shopfly.b2c.core.trade.order.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * 收银台参数VO
 *
 * @author Snow create in 2018/7/11
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CashierVO {


    @ApiModelProperty(name = "ship_name", value = "收货人姓名")
    private String shipName;

    @ApiModelProperty(name = "ship_addr", value = "收货地址")
    private String shipAddr;

    @ApiModelProperty(name = "ship_mobile", value = "收货人手机")
    private String shipMobile;

    @ApiModelProperty(name = "ship_country", value = "配送地区-国家")
    private String shipCountry;

    @ApiModelProperty(name = "ship_state", value = "配送地区-州/省")
    private String shipState;

    @ApiModelProperty(name = "ship_city", value = "配送地区-城市")
    private String shipCity;

    @ApiModelProperty(name = "need_pay_price", value = "应付金额")
    private Double needPayPrice;

    @ApiModelProperty(name = "pay_type_text", value = "支付方式")
    private String payTypeText;
    @ApiModelProperty(name = "count_down", value = "订单失效时间")
    private Long countDown;

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipAddr() {
        return shipAddr;
    }

    public void setShipAddr(String shipAddr) {
        this.shipAddr = shipAddr;
    }

    public String getShipMobile() {
        return shipMobile;
    }

    public void setShipMobile(String shipMobile) {
        this.shipMobile = shipMobile;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public Double getNeedPayPrice() {
        return needPayPrice;
    }

    public void setNeedPayPrice(Double needPayPrice) {
        this.needPayPrice = needPayPrice;
    }

    public String getPayTypeText() {
        return payTypeText;
    }

    public void setPayTypeText(String payTypeText) {
        this.payTypeText = payTypeText;
    }

    public Long getCountDown() {
        return countDown;
    }

    public void setCountDown(Long countDown) {
        this.countDown = countDown;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CashierVO cashierVO = (CashierVO) o;
        return Objects.equals(shipName, cashierVO.shipName) &&
                Objects.equals(shipAddr, cashierVO.shipAddr) &&
                Objects.equals(shipMobile, cashierVO.shipMobile) &&
                Objects.equals(shipCountry, cashierVO.shipCountry) &&
                Objects.equals(shipState, cashierVO.shipState) &&
                Objects.equals(shipCity, cashierVO.shipCity) &&
                Objects.equals(needPayPrice, cashierVO.needPayPrice) &&
                Objects.equals(payTypeText, cashierVO.payTypeText) &&
                Objects.equals(countDown, cashierVO.countDown);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipName, shipAddr, shipMobile, shipCountry, shipState, shipCity, needPayPrice, payTypeText, countDown);
    }

    @Override
    public String toString() {
        return "CashierVO{" +
                "shipName='" + shipName + '\'' +
                ", shipAddr='" + shipAddr + '\'' +
                ", shipMobile='" + shipMobile + '\'' +
                ", shipCountry='" + shipCountry + '\'' +
                ", shipState='" + shipState + '\'' +
                ", shipCity='" + shipCity + '\'' +
                ", needPayPrice=" + needPayPrice +
                ", payTypeText='" + payTypeText + '\'' +
                ", countDown=" + countDown +
                '}';
    }
}
