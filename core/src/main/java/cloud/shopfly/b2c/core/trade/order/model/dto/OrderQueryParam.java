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
package cloud.shopfly.b2c.core.trade.order.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Order query parametersDTO
 *
 * @author Snow create in 2018/5/14
 * @version v2.0
 * @since v7.0.0
 */
@ApiIgnore
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderQueryParam {

    @ApiModelProperty(value = "What page")
    private Integer pageNo;

    @ApiModelProperty(value = "Number each page")
    private Integer pageSize;

    @ApiModelProperty(value = "Name")
    private String goodsName;

    @ApiModelProperty(value = "Order no.")
    private String orderSn;

    @ApiModelProperty(value = "Transaction number")
    private String tradeSn;

    /**
     * For example,：All orders, to be paid, to be shipped, etc
     *
     * @link OrderTagEnum
     */
    @ApiModelProperty(value = "Page TAB",
            example = "ALL:All orders,WAIT_PAY:For the payment,WAIT_SHIP:To send the goods,WAIT_ROG:For the goods," +
                    "CANCELLED:Has been cancelled,COMPLETE:Has been completed,WAIT_COMMENT:To comment on,REFUND:In the after-sale")
    private String tag;

    @ApiModelProperty(value = "merchantsID")
    private Integer sellerId;

    @ApiModelProperty(value = "membersID")
    private Integer memberId;

    @ApiModelProperty(value = "The consignee")
    private String shipName;

    @ApiModelProperty(value = "The start time")
    private Long startTime;

    @ApiModelProperty(value = "Start-stop time")
    private Long endTime;

    @ApiModelProperty(value = "Buyers nickname")
    private String buyerName;

    @ApiModelProperty(value = "Status")
    private String orderStatus;

    @ApiModelProperty(value = "Payment status")
    private String payStatus;

    /**
     * Can query the order number buyers product name
     */
    @ApiModelProperty(value = "keyword")
    private String keywords;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderQueryParam that = (OrderQueryParam) o;

        return new EqualsBuilder()
                .append(pageNo, that.pageNo)
                .append(pageSize, that.pageSize)
                .append(goodsName, that.goodsName)
                .append(orderSn, that.orderSn)
                .append(tradeSn, that.tradeSn)
                .append(tag, that.tag)
                .append(sellerId, that.sellerId)
                .append(memberId, that.memberId)
                .append(shipName, that.shipName)
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .append(buyerName, that.buyerName)
                .append(orderStatus, that.orderStatus)
                .append(payStatus, that.payStatus)
                .append(keywords, that.keywords)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(pageNo)
                .append(pageSize)
                .append(goodsName)
                .append(orderSn)
                .append(tradeSn)
                .append(tag)
                .append(sellerId)
                .append(memberId)
                .append(shipName)
                .append(startTime)
                .append(endTime)
                .append(buyerName)
                .append(orderStatus)
                .append(payStatus)
                .append(keywords)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderQueryParam{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", goodsName='" + goodsName + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", tradeSn='" + tradeSn + '\'' +
                ", tag='" + tag + '\'' +
                ", sellerId=" + sellerId +
                ", memberId=" + memberId +
                ", shipName='" + shipName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", buyerName='" + buyerName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
