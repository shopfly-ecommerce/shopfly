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
package cloud.shopfly.b2c.core.base.plugin.waybill.vo;

import java.util.List;

/**
 * Express bird electronic panel encapsulates parameter entities
 *
 * @author zh
 * @version v1.0
 * @since v7.0
 * 2018years6month10The morning of10:51:10
 */
public class WayBillJson {

    /**
     * Order no.
     */
    private String orderCode;
    /**
     * Express Company Code
     */
    private String shipperCode;
    /**
     * Payment method of postage:1-Now pay,2-To pay,3-Monthly statement.4-Third-party payment
     */
    private Integer payType;
    /**
     * Express type：1-Standard express
     */
    private String expType;
    /**
     * Whether to inform the Courier to collect pieces：0-notice；1-不notice；If this parameter is not specified, the default value is1
     */
    private Integer isNotice;
    /**
     * E-face single customer account（Apply with express outlets or through express bird official website or through the application of electronic list customer number application）
     */
    private String customerName;
    /**
     * Electronic surface single cipher
     */
    private String customerPwd;
    /**
     * Send a fee（freight）
     */
    private Double cost;
    /**
     * Other fees
     */
    private Double otherCost;
    /**
     * Senders information
     */
    private Information sender;
    /**
     * Recipient information
     */
    private Information receiver;
    /**
     * Sending commodity information
     */
    private List<Commodity> commodity;
    /**
     * Gross weightkg
     */
    private Double weight;
    /**
     * number/The parcel number
     */
    private Integer quantity;
    /**
     * Total volume of articlesm3
     */
    private Double volume;
    /**
     * note
     */
    private String remark;
    /**
     * Returns the electronic surface single template：0-Dont need；1-Need to be
     */
    private String isReturnPrintTemplate;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public Integer getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(Integer isNotice) {
        this.isNotice = isNotice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPwd() {
        return customerPwd;
    }

    public void setCustomerPwd(String customerPwd) {
        this.customerPwd = customerPwd;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
    }

    public Information getSender() {
        return sender;
    }

    public void setSender(Information sender) {
        this.sender = sender;
    }

    public Information getReceiver() {
        return receiver;
    }

    public void setReceiver(Information receiver) {
        this.receiver = receiver;
    }

    public List<Commodity> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<Commodity> commodity) {
        this.commodity = commodity;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsReturnPrintTemplate() {
        return isReturnPrintTemplate;
    }

    public void setIsReturnPrintTemplate(String isReturnPrintTemplate) {
        this.isReturnPrintTemplate = isReturnPrintTemplate;
    }

    @Override
    public String toString() {
        return "WayBillJson{" +
                "orderCode='" + orderCode + '\'' +
                ", shipperCode='" + shipperCode + '\'' +
                ", payType=" + payType +
                ", expType='" + expType + '\'' +
                ", isNotice=" + isNotice +
                ", customerName='" + customerName + '\'' +
                ", customerPwd='" + customerPwd + '\'' +
                ", cost=" + cost +
                ", otherCost=" + otherCost +
                ", add=" + sender +
                ", receiver=" + receiver +
                ", commodity=" + commodity +
                ", weight=" + weight +
                ", quantity=" + quantity +
                ", volume=" + volume +
                ", remark='" + remark + '\'' +
                ", isReturnPrintTemplate='" + isReturnPrintTemplate + '\'' +
                '}';
    }
}
