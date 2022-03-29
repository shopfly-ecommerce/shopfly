/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;


/**
 * 订单日志表实体
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
     * 主键ID
     */
    @Id(name = "log_id")
    @ApiModelProperty(hidden = true)
    private Integer logId;

    /**
     * 订单编号
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "订单编号", required = false)
    private String orderSn;

    /**
     * 操作者id
     */
    @Column(name = "op_id")
    @ApiModelProperty(name = "op_id", value = "操作者id", required = false)
    private Integer opId;

    /**
     * 操作者名称
     */
    @Column(name = "op_name")
    @ApiModelProperty(name = "op_name", value = "操作者名称", required = false)
    private String opName;

    /**
     * 日志信息
     */
    @Column(name = "message")
    @ApiModelProperty(name = "message", value = "日志信息", required = false)
    private String message;

    /**
     * 操作时间
     */
    @Column(name = "op_time")
    @ApiModelProperty(name = "op_time", value = "操作时间", required = false)
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
