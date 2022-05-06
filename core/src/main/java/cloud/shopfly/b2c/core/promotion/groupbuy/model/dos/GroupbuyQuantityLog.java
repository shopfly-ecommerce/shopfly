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
package cloud.shopfly.b2c.core.promotion.groupbuy.model.dos;

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

import java.io.Serializable;


/**
 * 团购商品库存日志表实体
 * @author Snow
 * @version v1.0
 * @since v7.0.0
 * 2018-07-09 15:32:29
 */
@Table(name="es_groupbuy_quantity_log")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupbuyQuantityLog implements Serializable {

    private static final long serialVersionUID = 2276297510896449L;

    /**日志id*/
    @Id(name = "log_id")
    @ApiModelProperty(hidden=true)
    private Integer logId;

    /**订单编号*/
    @Column(name = "order_sn")
    @ApiModelProperty(name="order_sn",value="订单编号",required=false)
    private String orderSn;

    /**商品ID*/
    @Column(name = "goods_id")
    @ApiModelProperty(name="goods_id",value="商品ID",required=false)
    private Integer goodsId;

    /**数量*/
    @Column(name = "quantity")
    @ApiModelProperty(name="quantity",value="数量",required=false)
    private Integer quantity;

    /**操作时间*/
    @Column(name = "op_time")
    @ApiModelProperty(name="op_time",value="操作时间",required=false)
    private Long opTime;

    /**日志类型*/
    @Column(name = "log_type")
    @ApiModelProperty(name="log_type",value="日志类型",required=false)
    private String logType;

    /**操作原因*/
    @Column(name = "reason")
    @ApiModelProperty(name="reason",value="操作原因",required=false)
    private String reason;

    @Column(name = "gb_id")
    @ApiModelProperty(name="gb_id",value="团购活动id",required=false)
    private Integer gbId;

    @PrimaryKeyField
    public Integer getLogId() {
        return logId;
    }
    public void setLogId(Integer logId) {
        this.logId = logId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getOpTime() {
        return opTime;
    }

    public void setOpTime(Long opTime) {
        this.opTime = opTime;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getGbId() {
        return gbId;
    }

    public void setGbId(Integer gbId) {
        this.gbId = gbId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        GroupbuyQuantityLog that = (GroupbuyQuantityLog) o;

        return new EqualsBuilder()
                .append(logId, that.logId)
                .append(orderSn, that.orderSn)
                .append(goodsId, that.goodsId)
                .append(quantity, that.quantity)
                .append(opTime, that.opTime)
                .append(logType, that.logType)
                .append(reason, that.reason)
                .append(gbId, that.gbId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(logId)
                .append(orderSn)
                .append(goodsId)
                .append(quantity)
                .append(opTime)
                .append(logType)
                .append(reason)
                .append(gbId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "GroupbuyQuantityLog{" +
                "logId=" + logId +
                ", orderSn='" + orderSn + '\'' +
                ", goodsId=" + goodsId +
                ", quantity=" + quantity +
                ", opTime=" + opTime +
                ", logType='" + logType + '\'' +
                ", reason='" + reason + '\'' +
                ", gbId=" + gbId +
                '}';
    }
}
