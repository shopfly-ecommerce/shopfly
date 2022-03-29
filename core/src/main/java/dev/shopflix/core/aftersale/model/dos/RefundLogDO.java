/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.aftersale.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 退货（款）日志实体
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
     * 日志id
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 退款sn
     */
    @Column(name = "refund_sn")
    @ApiModelProperty(name = "refund_sn", value = "退款sn", required = false)
    private String refundSn;
    /**
     * 日志记录时间
     */
    @Column(name = "logtime")
    @ApiModelProperty(name = "logtime", value = "日志记录时间", required = false)
    private Long logtime;
    /**
     * 日志详细
     */
    @Column(name = "logdetail")
    @ApiModelProperty(name = "logdetail", value = "日志详细", required = false)
    private String logdetail;
    /**
     * 操作者
     */
    @Column(name = "operator")
    @ApiModelProperty(name = "operator", value = "操作者", required = false)
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