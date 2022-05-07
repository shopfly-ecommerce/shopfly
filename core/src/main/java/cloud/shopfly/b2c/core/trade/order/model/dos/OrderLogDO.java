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
 * Order log table entity
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-16 12:01:34
 */
@Table(name = "es_order_log")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderLogDO implements Serializable {

    private static final long serialVersionUID = 435282086399754L;

    /**
     * A primary keyID
     */
    @Id(name = "log_id")
    @ApiModelProperty(hidden = true)
    private Integer logId;

    /**
     * Order no.
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "Order no.", required = false)
    private String orderSn;

    /**
     * The operatorid
     */
    @Column(name = "op_id")
    @ApiModelProperty(name = "op_id", value = "The operatorid", required = false)
    private Integer opId;

    /**
     * Operator name
     */
    @Column(name = "op_name")
    @ApiModelProperty(name = "op_name", value = "Operator name", required = false)
    private String opName;

    /**
     * Log information
     */
    @Column(name = "message")
    @ApiModelProperty(name = "message", value = "Log information", required = false)
    private String message;

    /**
     * Operating time
     */
    @Column(name = "op_time")
    @ApiModelProperty(name = "op_time", value = "Operating time", required = false)
    private Long opTime;

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

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getOpTime() {
        return opTime;
    }

    public void setOpTime(Long opTime) {
        this.opTime = opTime;
    }


    @Override
    public String toString() {
        return "OrderLogDO{" +
                "logId=" + logId +
                ", orderSn='" + orderSn + '\'' +
                ", opId=" + opId +
                ", opName='" + opName + '\'' +
                ", message=" + message +
                ", opTime=" + opTime +
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

        OrderLogDO that = (OrderLogDO) o;

        return new EqualsBuilder()
                .append(logId, that.logId)
                .append(orderSn, that.orderSn)
                .append(opId, that.opId)
                .append(opName, that.opName)
                .append(message, that.message)
                .append(opTime, that.opTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(logId)
                .append(orderSn)
                .append(opId)
                .append(opName)
                .append(message)
                .append(opTime)
                .toHashCode();
    }
}
