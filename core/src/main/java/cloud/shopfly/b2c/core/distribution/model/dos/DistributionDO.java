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
 * distributors
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/21 In the morning11:25
 */

@Table(name = "es_distribution")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DistributionDO {

    /**
     * A primary keyid
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * Associated memberid
     */
    @Column(name = "member_id")
    @ApiModelProperty(value = "membersid", required = true)
    private Integer memberId;


    /**
     * Member name
     */
    @Column(name = "member_name")
    @ApiModelProperty(value = "Member name", required = true)
    private String memberName;


    /**
     * Relationship between the path
     */
    @Column()
    @ApiModelProperty(value = "Relationship between the path", required = true)
    private String path;

    /**
     * 2Level distributorsid（On the superior）
     */
    @Column(name = "member_id_lv2")
    @ApiModelProperty(name = "member_id_lv2", value = "2Level distributorsid（On the superior）", required = true)
    private Integer memberIdLv2;

    /**
     * 1Level distributorsid（The superior）
     */
    @Column(name = "member_id_lv1")
    @ApiModelProperty(name = "member_id_lv1", value = "1Level distributorsid（The superior）", required = true)
    private Integer memberIdLv1;

    /**
     * The number of referrals
     */
    @Column(name = "downline")
    @ApiModelProperty(value = "The number of referrals", required = true)
    private Integer downline = 0;

    /**
     * Commission related order number
     */
    @Column(name = "order_num")
    @ApiModelProperty(name = "order_num", value = "Commission related order number", required = true)
    private Integer orderNum = 0;

    /**
     * The total amount of the rebate
     */
    @Column(name = "rebate_total")
    @ApiModelProperty(name = "rebate_total", value = "The total amount of the rebate", required = true)
    private Double rebateTotal = 0D;

    /**
     * Total turnover
     */
    @Column(name = "turnover_price")
    @ApiModelProperty(name = "turnover_price", value = "Total turnover", required = true)
    private Double turnoverPrice = 0D;

    /**
     * Withdrawal amount
     */
    @Column(name = "can_rebate")
    @ApiModelProperty(name = "can_rebate", value = "Withdrawal amount", required = true)
    private Double canRebate = 0D;

    /**
     * The rebate amount is frozen
     */
    @Column(name = "commission_frozen")
    @ApiModelProperty(name = "commission_frozen", value = "Amount frozen", required = true)
    private Double commissionFrozen = 0D;


    /**
     * Withdrawal to freeze
     */
    @Column(name = "withdraw_frozen_price")
    @ApiModelProperty(name = "withdraw_frozen_price", value = "Amount frozen", required = true)
    private Double withdrawFrozenPrice = 0D;

    /**
     * Use the templateid
     */
    @Column(name = "current_tpl_id")
    @ApiModelProperty(name = "current_tpl_id", value = "Use the templateid", required = true)
    private Integer currentTplId;


    /**
     * Use the templateid
     */
    @Column(name = "current_tpl_name")
    @ApiModelProperty(name = "current_tpl_name", value = "Using template names", required = true)
    private String currentTplName;

    /**
     * Last update
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "Last update", required = false)
    private Long createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getMemberIdLv2() {
        return memberIdLv2;
    }

    public void setMemberIdLv2(Integer memberIdLv2) {
        this.memberIdLv2 = memberIdLv2;
    }

    public Integer getMemberIdLv1() {
        return memberIdLv1;
    }

    public void setMemberIdLv1(Integer memberIdLv1) {
        this.memberIdLv1 = memberIdLv1;
    }

    public Integer getDownline() {
        return downline;
    }

    public void setDownline(Integer downline) {
        this.downline = downline;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Double getRebateTotal() {
        return rebateTotal;
    }

    public void setRebateTotal(Double rebateTotal) {
        this.rebateTotal = rebateTotal;
    }

    public Double getTurnoverPrice() {
        return turnoverPrice;
    }

    public void setTurnoverPrice(Double turnoverPrice) {
        this.turnoverPrice = turnoverPrice;
    }

    public Double getCanRebate() {
        return canRebate;
    }

    public void setCanRebate(Double canRebate) {
        this.canRebate = canRebate;
    }

    public Double getCommissionFrozen() {
        return commissionFrozen;
    }

    public void setCommissionFrozen(Double commissionFrozen) {
        this.commissionFrozen = commissionFrozen;
    }

    public Integer getCurrentTplId() {
        return currentTplId;
    }

    public void setCurrentTplId(Integer currentTplId) {
        this.currentTplId = currentTplId;
    }

    public String getCurrentTplName() {
        return currentTplName;
    }

    public void setCurrentTplName(String currentTplName) {
        this.currentTplName = currentTplName;
    }

    public Double getWithdrawFrozenPrice() {
        return withdrawFrozenPrice;
    }

    public void setWithdrawFrozenPrice(Double withdrawFrozenPrice) {
        this.withdrawFrozenPrice = withdrawFrozenPrice;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DistributionDO{" +
                " memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", path='" + path + '\'' +
                ", memberIdLv2=" + memberIdLv2 +
                ", memberIdLv1=" + memberIdLv1 +
                ", downline=" + downline +
                ", orderNum=" + orderNum +
                ", rebateTotal=" + rebateTotal +
                ", turnoverPrice=" + turnoverPrice +
                ", canRebate=" + canRebate +
                ", commissionFrozen=" + commissionFrozen +
                ", withdrawFrozenPrice=" + withdrawFrozenPrice +
                ", currentTplId=" + currentTplId +
                ", currentTplName='" + currentTplName + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DistributionDO that = (DistributionDO) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) {
            return false;
        }
        if (memberName != null ? !memberName.equals(that.memberName) : that.memberName != null) {
            return false;
        }
        if (path != null ? !path.equals(that.path) : that.path != null) {
            return false;
        }
        if (memberIdLv2 != null ? !memberIdLv2.equals(that.memberIdLv2) : that.memberIdLv2 != null) {
            return false;
        }
        if (memberIdLv1 != null ? !memberIdLv1.equals(that.memberIdLv1) : that.memberIdLv1 != null) {
            return false;
        }
        if (downline != null ? !downline.equals(that.downline) : that.downline != null) {
            return false;
        }
        if (orderNum != null ? !orderNum.equals(that.orderNum) : that.orderNum != null) {
            return false;
        }
        if (currentTplId != null ? !currentTplId.equals(that.currentTplId) : that.currentTplId != null) {
            return false;
        }
        if (currentTplName != null ? !currentTplName.equals(that.currentTplName) : that.currentTplName != null) {
            return false;
        }
        if (rebateTotal != null ? !rebateTotal.equals(that.rebateTotal) : that.rebateTotal != null) {
            return false;
        }
        if (turnoverPrice != null ? !turnoverPrice.equals(that.turnoverPrice) : that.turnoverPrice != null) {
            return false;
        }
        if (canRebate != null ? !canRebate.equals(that.canRebate) : that.canRebate != null) {
            return false;
        }
        if (commissionFrozen != null ? !commissionFrozen.equals(that.commissionFrozen) : that.commissionFrozen != null) {
            return false;
        }
        if (withdrawFrozenPrice != null ? !withdrawFrozenPrice.equals(that.withdrawFrozenPrice) : that.withdrawFrozenPrice != null) {
            return false;
        }
        return createTime != null ? createTime.equals(that.createTime) : that.createTime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (memberName != null ? memberName.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (memberIdLv2 != null ? memberIdLv2.hashCode() : 0);
        result = 31 * result + (memberIdLv1 != null ? memberIdLv1.hashCode() : 0);
        result = 31 * result + (downline != null ? downline.hashCode() : 0);
        result = 31 * result + (orderNum != null ? orderNum.hashCode() : 0);
        result = 31 * result + (rebateTotal != null ? rebateTotal.hashCode() : 0);
        result = 31 * result + (turnoverPrice != null ? turnoverPrice.hashCode() : 0);
        result = 31 * result + (canRebate != null ? canRebate.hashCode() : 0);
        result = 31 * result + (commissionFrozen != null ? commissionFrozen.hashCode() : 0);
        result = 31 * result + (withdrawFrozenPrice != null ? withdrawFrozenPrice.hashCode() : 0);
        result = 31 * result + (currentTplId != null ? currentTplId.hashCode() : 0);
        result = 31 * result + (currentTplName != null ? currentTplName.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

}
