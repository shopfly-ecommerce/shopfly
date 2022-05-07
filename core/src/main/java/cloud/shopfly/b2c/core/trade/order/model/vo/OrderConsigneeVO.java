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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * annotation
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderConsigneeVO implements Serializable {

    private static final long serialVersionUID = -6095473417556550895L;

    @ApiModelProperty(value = "The order number", name = "order_sn", hidden = true)
    private String orderSn;

    @ApiModelProperty(value = "Name of consignee", name = "ship_name")
    private String shipName;

    @ApiModelProperty(value = "The order note", name = "remark")
    private String remark;

    @ApiModelProperty(value = "countries", name = "ship_country")
    private String shipCountry;

    @ApiModelProperty(value = "state/The name of the province", name = "ship_state")
    private String shipState;

    @ApiModelProperty(value = "city", name = "ship_city")
    private String shipCity;

    @ApiModelProperty(value = "Country code", name = "ship_country_code")
    private String shipCountryCode;

    @ApiModelProperty(value = "state/Province code", name = "ship_state_code")
    private String shipStateCode;

    @ApiModelProperty(value = "Shipping address", name = "ship_addr")
    private String shipAddr;

    @ApiModelProperty(value = "Contact phone number", name = "ship_mobile")
    private String shipMobile;

    @ApiModelProperty(value = "Zip code", name = "ship_zip")
    private String shipZip;

    @ApiModelProperty(value = "Delivery time", name = "receive_time")
    private String receiveTime;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipCountryCode() {
        return shipCountryCode;
    }

    public void setShipCountryCode(String shipCountryCode) {
        this.shipCountryCode = shipCountryCode;
    }

    public String getShipStateCode() {
        return shipStateCode;
    }

    public void setShipStateCode(String shipStateCode) {
        this.shipStateCode = shipStateCode;
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

    public String getShipZip() {
        return shipZip;
    }

    public void setShipZip(String shipZip) {
        this.shipZip = shipZip;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderConsigneeVO that = (OrderConsigneeVO) o;
        return Objects.equals(orderSn, that.orderSn) &&
                Objects.equals(shipName, that.shipName) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(shipCountry, that.shipCountry) &&
                Objects.equals(shipState, that.shipState) &&
                Objects.equals(shipCity, that.shipCity) &&
                Objects.equals(shipCountryCode, that.shipCountryCode) &&
                Objects.equals(shipStateCode, that.shipStateCode) &&
                Objects.equals(shipAddr, that.shipAddr) &&
                Objects.equals(shipMobile, that.shipMobile) &&
                Objects.equals(shipZip, that.shipZip) &&
                Objects.equals(receiveTime, that.receiveTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderSn, shipName, remark, shipCountry, shipState, shipCity, shipCountryCode, shipStateCode, shipAddr, shipMobile, shipZip, receiveTime);
    }

    @Override
    public String toString() {
        return "OrderConsigneeVO{" +
                "orderSn='" + orderSn + '\'' +
                ", shipName='" + shipName + '\'' +
                ", remark='" + remark + '\'' +
                ", shipCountry='" + shipCountry + '\'' +
                ", shipState='" + shipState + '\'' +
                ", shipCity='" + shipCity + '\'' +
                ", shipCountryCode='" + shipCountryCode + '\'' +
                ", shipStateCode='" + shipStateCode + '\'' +
                ", shipAddr='" + shipAddr + '\'' +
                ", shipMobile='" + shipMobile + '\'' +
                ", shipZip='" + shipZip + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                '}';
    }
}
