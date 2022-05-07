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
package cloud.shopfly.b2c.core.aftersale.model.dos;

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
 * Return of the goods（paragraph）Log entity
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-02 15:23:06
 */
@Table(name = "es_refund_log")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RefundLogDO implements Serializable {

    private static final long serialVersionUID = 5393882791682972L;

    /**
     * The logid
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * A refundsn
     */
    @Column(name = "refund_sn")
    @ApiModelProperty(name = "refund_sn", value = "A refundsn", required = false)
    private String refundSn;
    /**
     * Logging time
     */
    @Column(name = "logtime")
    @ApiModelProperty(name = "logtime", value = "Logging time", required = false)
    private Long logtime;
    /**
     * Log in detail
     */
    @Column(name = "logdetail")
    @ApiModelProperty(name = "logdetail", value = "Log in detail", required = false)
    private String logdetail;
    /**
     * The operator
     */
    @Column(name = "operator")
    @ApiModelProperty(name = "operator", value = "The operator", required = false)
    private String operator;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefundSn() {
        return refundSn;
    }

    public void setRefundSn(String refundSn) {
        this.refundSn = refundSn;
    }

    public Long getLogtime() {
        return logtime;
    }

    public void setLogtime(Long logtime) {
        this.logtime = logtime;
    }

    public String getLogdetail() {
        return logdetail;
    }

    public void setLogdetail(String logdetail) {
        this.logdetail = logdetail;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefundLogDO that = (RefundLogDO) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (refundSn != null ? !refundSn.equals(that.refundSn) : that.refundSn != null) {
            return false;
        }
        if (logtime != null ? !logtime.equals(that.logtime) : that.logtime != null) {
            return false;
        }
        if (logdetail != null ? !logdetail.equals(that.logdetail) : that.logdetail != null) {
            return false;
        }
        return operator != null ? operator.equals(that.operator) : that.operator == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (refundSn != null ? refundSn.hashCode() : 0);
        result = 31 * result + (logtime != null ? logtime.hashCode() : 0);
        result = 31 * result + (logdetail != null ? logdetail.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RefundLogDO{" +
                "id=" + id +
                ", refundSn='" + refundSn + '\'' +
                ", logtime=" + logtime +
                ", logdetail='" + logdetail + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }


}
