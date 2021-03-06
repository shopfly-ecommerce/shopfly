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

import java.io.Serializable;


/**
 * Order outgoing status entity
 *
 * @author xlp
 * @version v2.0
 * @since v7.0.0
 * 2018-07-10 14:06:38
 */
@Table(name = "es_order_out_status")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderOutStatus implements Serializable {

    private static final long serialVersionUID = 582319689134779L;

    /**
     * A primary key
     */
    @Id(name = "out_id")
    @ApiModelProperty(hidden = true)
    private Integer outId;

    /**
     * Order no.
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "Order no.", required = false)
    private String orderSn;

    /**
     * Outbound type
     */
    @Column(name = "out_type")
    @ApiModelProperty(name = "out_type", value = "Outbound type", required = false)
    private String outType;

    /**
     * The delivery status
     */
    @Column(name = "out_status")
    @ApiModelProperty(name = "out_status", value = "The delivery status", required = false)
    private String outStatus;

    @PrimaryKeyField
    public Integer getOutId() {
        return outId;
    }

    public void setOutId(Integer outId) {
        this.outId = outId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOutType() {
        return outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
    }

    public String getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderOutStatus that = (OrderOutStatus) o;
        if (outId != null ? !outId.equals(that.outId) : that.outId != null) {
            return false;
        }
        if (orderSn != null ? !orderSn.equals(that.orderSn) : that.orderSn != null) {
            return false;
        }
        if (outType != null ? !outType.equals(that.outType) : that.outType != null) {
            return false;
        }
        return outStatus != null ? outStatus.equals(that.outStatus) : that.outStatus == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (outId != null ? outId.hashCode() : 0);
        result = 31 * result + (orderSn != null ? orderSn.hashCode() : 0);
        result = 31 * result + (outType != null ? outType.hashCode() : 0);
        result = 31 * result + (outStatus != null ? outStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderOutStatus{" +
                "outId=" + outId +
                ", orderSn='" + orderSn + '\'' +
                ", outType='" + outType + '\'' +
                ", outStatus='" + outStatus + '\'' +
                '}';
    }


}
