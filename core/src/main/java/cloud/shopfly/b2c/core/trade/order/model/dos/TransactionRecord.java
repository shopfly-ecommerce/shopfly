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
package cloud.shopfly.b2c.core.trade.order.model.dos;

import java.io.Serializable;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Transaction record entity
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-25 15:37:56
 */
@Table(name = "es_transaction_record")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TransactionRecord implements Serializable {

    private static final long serialVersionUID = 6751804777335135L;

    /**
     * A primary keyID
     */
    @Id(name = "record_id")
    @ApiModelProperty(hidden = true)
    private Integer recordId;
    /**
     * Order no.
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "Order no.", required = false)
    private String orderSn;
    /**
     * productID
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "productID", required = false)
    private Integer goodsId;
    /**
     * The number
     */
    @Column(name = "goods_num")
    @ApiModelProperty(name = "goods_num", value = "The number", required = false)
    private Integer goodsNum;
    /**
     * Confirm delivery time
     */
    @Column(name = "rog_time")
    @ApiModelProperty(name = "rog_time", value = "Confirm delivery time", required = false)
    private Long rogTime;
    /**
     * Username
     */
    @Column(name = "uname")
    @ApiModelProperty(name = "uname", value = "Username", required = false)
    private String uname;
    /**
     * The transaction price
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "The transaction price", required = false)
    private Double price;
    /**
     * membersID
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "membersID", required = false)
    private Integer memberId;

    @PrimaryKeyField
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Long getRogTime() {
        return rogTime;
    }

    public void setRogTime(Long rogTime) {
        this.rogTime = rogTime;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionRecord that = (TransactionRecord) o;

        return new EqualsBuilder()
                .append(recordId, that.recordId)
                .append(orderSn, that.orderSn)
                .append(goodsId, that.goodsId)
                .append(goodsNum, that.goodsNum)
                .append(rogTime, that.rogTime)
                .append(uname, that.uname)
                .append(price, that.price)
                .append(memberId, that.memberId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(recordId)
                .append(orderSn)
                .append(goodsId)
                .append(goodsNum)
                .append(rogTime)
                .append(uname)
                .append(price)
                .append(memberId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "recordId=" + recordId +
                ", orderSn='" + orderSn + '\'' +
                ", goodsId=" + goodsId +
                ", goodsNum=" + goodsNum +
                ", rogTime=" + rogTime +
                ", uname='" + uname + '\'' +
                ", price=" + price +
                ", memberId=" + memberId +
                '}';
    }


}
