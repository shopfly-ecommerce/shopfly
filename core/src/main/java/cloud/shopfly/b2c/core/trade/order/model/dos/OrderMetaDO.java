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
 * 订单扩展信息表实体
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-10 14:22:57
 */
@Table(name = "es_order_meta")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderMetaDO implements Serializable {

    private static final long serialVersionUID = 3108120423695585L;

    /**
     * 主键ID
     */
    @Id(name = "meta_id")
    @ApiModelProperty(hidden = true)
    private Integer metaId;

    /**
     * 订单编号
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "订单编号", required = false)
    private String orderSn;

    /**
     * 扩展-键
     */
    @Column(name = "meta_key")
    @ApiModelProperty(name = "meta_key", value = "扩展-键", required = false)
    private String metaKey;

    /**
     * 扩展-值
     */
    @Column(name = "meta_value")
    @ApiModelProperty(name = "meta_value", value = "扩展-值", required = false)
    private String metaValue;

    /**
     * 售后状态
     */
    @Column(name = "status")
    @ApiModelProperty(name = "status", value = "售后状态", required = false)
    private String status;

    @PrimaryKeyField
    public Integer getMetaId() {
        return metaId;
    }

    public void setMetaId(Integer metaId) {
        this.metaId = metaId;
    }


    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderMetaDO that = (OrderMetaDO) o;

        return new EqualsBuilder()
                .append(metaId, that.metaId)
                .append(orderSn, that.orderSn)
                .append(metaKey, that.metaKey)
                .append(metaValue, that.metaValue)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(metaId)
                .append(orderSn)
                .append(metaKey)
                .append(metaValue)
                .append(status)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderMetaDO{" +
                "metaId=" + metaId +
                ", orderSn=" + orderSn +
                ", metaKey='" + metaKey + '\'' +
                ", metaValue=" + metaValue +
                ", status=" + status +
                '}';
    }
}
