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
package cloud.shopfly.b2c.core.distribution.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Member statement
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/21 In the morning10:51
 */

@Table(name = "es_bill_member")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BillMemberDO {
    /**
     * id
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * id
     */
    @Column(name = "total_id")
    @ApiModelProperty(value = "General statementid", required = true)
    private Integer totalId;
    /**
     * membersid
     */
    @Column(name = "member_id")
    @ApiModelProperty(value = "membersid", required = true)
    private Integer memberId;

    /**
     * The start time
     */
    @Column(name = "start_time")
    @ApiModelProperty(value = "The start time")
    private Long startTime;
    /**
     * The end of time
     */
    @Column(name = "end_time")
    @ApiModelProperty(value = "The end of time")
    private Long endTime;
    /**
     * Final settlement amount
     */
    @Column(name = "final_money")
    @ApiModelProperty(value = "Final settlement amount")
    private Double finalMoney;
    /**
     * Commission amount
     */
    @Column(name = "push_money")
    @ApiModelProperty(value = "Commission amount")
    private Double pushMoney;
    /**
     * The order number
     */
    @Column(name = "order_count")
    @ApiModelProperty(value = "orders")
    private Integer orderCount;
    /**
     * Amount
     */
    @Column(name = "order_money")
    @ApiModelProperty(value = "Amount")
    private Double orderMoney;
    /**
     * Return order amount
     */
    @Column(name = "return_order_money")
    @ApiModelProperty(value = "Return order amount")
    private Double returnOrderMoney;
    /**
     * Return order number
     */
    @Column(name = "return_order_count")
    @ApiModelProperty(value = "Return order number")
    private Integer returnOrderCount;
    /**
     * Return order amount
     */
    @Column(name = "return_push_money")
    @ApiModelProperty(value = "Return order amount")
    private Double returnPushMoney;
    /**
     * Member name
     */
    @Column(name = "member_name")
    @ApiModelProperty(value = "Member name")
    private String memberName;
    /**
     * Serial number
     */
    @Column()
    @ApiModelProperty(value = "Serial number")
    private String sn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalId() {
        return totalId;
    }

    public void setTotalId(Integer totalId) {
        this.totalId = totalId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "BillMemberDO{" +
                "totalId=" + totalId +
                ", memberId=" + memberId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", finalMoney=" + finalMoney +
                ", pushMoney=" + pushMoney +
                ", orderCount=" + orderCount +
                ", orderMoney=" + orderMoney +
                ", returnOrderMoney=" + returnOrderMoney +
                ", returnOrderCount=" + returnOrderCount +
                ", returnPushMoney=" + returnPushMoney +
                ", memberName='" + memberName + '\'' +
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

        BillMemberDO that = (BillMemberDO) o;

        if (totalId != null ? !totalId.equals(that.totalId) : that.totalId != null) {
            return false;
        }
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) {
            return false;
        }
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) {
            return false;
        }
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) {
            return false;
        }
        if (finalMoney != null ? !finalMoney.equals(that.finalMoney) : that.finalMoney != null) {
            return false;
        }
        if (pushMoney != null ? !pushMoney.equals(that.pushMoney) : that.pushMoney != null) {
            return false;
        }
        if (orderCount != null ? !orderCount.equals(that.orderCount) : that.orderCount != null) {
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
        if (memberName != null ? !memberName.equals(that.memberName) : that.memberName != null) {
            return false;
        }
        return sn != null ? sn.equals(that.sn) : that.sn == null;
    }

    @Override
    public int hashCode() {
        int result = totalId != null ? totalId.hashCode() : 0;
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (finalMoney != null ? finalMoney.hashCode() : 0);
        result = 31 * result + (pushMoney != null ? pushMoney.hashCode() : 0);
        result = 31 * result + (orderCount != null ? orderCount.hashCode() : 0);
        result = 31 * result + (orderMoney != null ? orderMoney.hashCode() : 0);
        result = 31 * result + (returnOrderMoney != null ? returnOrderMoney.hashCode() : 0);
        result = 31 * result + (returnOrderCount != null ? returnOrderCount.hashCode() : 0);
        result = 31 * result + (returnPushMoney != null ? returnPushMoney.hashCode() : 0);
        result = 31 * result + (memberName != null ? memberName.hashCode() : 0);
        result = 31 * result + (sn != null ? sn.hashCode() : 0);
        return result;
    }
}
