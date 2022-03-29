/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.distribution.model.dos;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 总结算单
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/21 上午10:40
 */

@Table(name = "es_bill_total")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BillTotalDO {
    /**
     * id
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    @ApiModelProperty(value = "开始时间")
    private Long startTime;
    /**
     * 结束时间
     */
    @Column(name = "end_time")
    @ApiModelProperty(value = "结束时间")
    private Long endTime;
    /**
     * 订单数
     */
    @Column(name = "order_count")
    @ApiModelProperty(value = "订单数")
    private Integer orderCount;
    /**
     * 最终结算金额
     */
    @Column(name = "final_money")
    @ApiModelProperty(value = "最终结算金额")
    private Double finalMoney;
    /**
     * 提成金额
     */
    @Column(name = "push_money")
    @ApiModelProperty(value = "提成金额")
    private Double pushMoney;
    /**
     * 订单金额
     */
    @Column(name = "order_money")
    @ApiModelProperty(value = "订单金额")
    private Double orderMoney;
    /**
     * 退还订单金额
     */
    @Column(name = "return_order_money")
    @ApiModelProperty(value = "退还订单金额")
    private Double returnOrderMoney;
    /**
     * 退还订单数量
     */
    @Column(name = "return_order_count")
    @ApiModelProperty(value = "退还订单数量")
    private Integer returnOrderCount;
    /**
     * 退还提成金额
     */
    @Column(name = "return_push_money")
    @ApiModelProperty(value = "退还提成金额")
    private Double returnPushMoney;
    /**
     * sn
     */
    @Column(name = "sn")
    @ApiModelProperty(value = "编号")
    private String sn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Double getFinalMoney() {
        return finalMoney;
    }

    public void setFinalMoney(Double finalMoney) {
        this.finalMoney = finalMoney;
    }

    public Double getPushMoney() {
        return pushMoney;
    }

    public void setPushMoney(Double pushMoney) {
        this.pushMoney = pushMoney;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Double getReturnOrderMoney() {
        return returnOrderMoney;
    }

    public void setReturnOrderMoney(Double returnOrderMoney) {
        this.returnOrderMoney = returnOrderMoney;
    }

    public Integer getReturnOrderCount() {
        return returnOrderCount;
    }

    public void setReturnOrderCount(Integer returnOrderCount) {
        this.returnOrderCount = returnOrderCount;
    }

    public Double getReturnPushMoney() {
        return returnPushMoney;
    }

    public void setReturnPushMoney(Double returnPushMoney) {
        this.returnPushMoney = returnPushMoney;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "BillTotalDO{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", orderCount=" + orderCount +
                ", finalMoney=" + finalMoney +
                ", pushMoney=" + pushMoney +
                ", orderMoney=" + orderMoney +
                ", returnOrderMoney=" + returnOrderMoney +
                ", returnOrderCount=" + returnOrderCount +
                ", returnPushMoney=" + returnPushMoney +
                ", sn='" + sn + '\'' +
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

        BillTotalDO that = (BillTotalDO) o;

        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) {
            return false;
        }
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) {
            return false;
        }
        if (orderCount != null ? !orderCount.equals(that.orderCount) : that.orderCount != null) {
            return false;
        }
        if (finalMoney != null ? !finalMoney.equals(that.finalMoney) : that.finalMoney != null) {
            return false;
        }
        if (pushMoney != null ? !pushMoney.equals(that.pushMoney) : that.pushMoney != null) {
            return false;
        }
        if (orderMoney != null ? !orderMoney.equals(that.orderMoney) : that.orderMoney != null) {
            return false;
        }
        if (returnOrderMoney != null ? !returnOrderMoney.equals(that.returnOrderMoney) : that.returnOrderMoney != null) {
            return false;
        }
        if (returnOrderCount != null ? !returnOrderCount.equals(that.returnOrderCount) : that.returnOrderCount != null) {
            return false;
        }
        if (returnPushMoney != null ? !returnPushMoney.equals(that.returnPushMoney) : that.returnPushMoney != null) {
            return false;
        }
        return sn != null ? sn.equals(that.sn) : that.sn == null;
    }

    @Override
    public int hashCode() {
        int result = startTime != null ? startTime.hashCode() : 0;
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (orderCount != null ? orderCount.hashCode() : 0);
        result = 31 * result + (finalMoney != null ? finalMoney.hashCode() : 0);
        result = 31 * result + (pushMoney != null ? pushMoney.hashCode() : 0);
        result = 31 * result + (orderMoney != null ? orderMoney.hashCode() : 0);
        result = 31 * result + (returnOrderMoney != null ? returnOrderMoney.hashCode() : 0);
        result = 31 * result + (returnOrderCount != null ? returnOrderCount.hashCode() : 0);
        result = 31 * result + (returnPushMoney != null ? returnPushMoney.hashCode() : 0);
        result = 31 * result + (sn != null ? sn.hashCode() : 0);
        return result;
    }
}