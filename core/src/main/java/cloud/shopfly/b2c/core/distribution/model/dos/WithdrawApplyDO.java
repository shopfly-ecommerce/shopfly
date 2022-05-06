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
 * 提现申请
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/21 下午2:37
 */
@Table(name = "es_withdraw_apply")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WithdrawApplyDO {
    /**
     * id
     **/
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 提现金额
     **/
    @Column(name = "apply_money")
    @ApiModelProperty(value = "提现金额")
    private Double applyMoney;
    /**
     * 提现状态
     **/
    @Column()
    @ApiModelProperty(value = "提现状态")
    private String status;
    /**
     * 会员id
     **/
    @Column(name = "member_id")
    @ApiModelProperty(value = "会员id")
    private Integer memberId;
    /**
     * 会员名字
     **/
    @Column(name = "member_name")
    @ApiModelProperty(value = "会员名")
    private String memberName;
    /**
     * 申请备注
     **/
    @Column(name = "apply_remark")
    @ApiModelProperty(value = "申请备注")
    private String applyRemark;
    /**
     * 审核备注
     **/
    @Column(name = "inspect_remark")
    @ApiModelProperty(value = "审核备注")
    private String inspectRemark;
    /**
     * 转账备注
     **/
    @Column(name = "transfer_remark")
    @ApiModelProperty(value = "转账备注")
    private String transferRemark;
    /**
     * 申请时间
     **/
    @Column(name = "apply_time")
    @ApiModelProperty(value = "申请时间")
    private Long applyTime;
    /**
     * 审核时间
     **/
    @Column(name = "inspect_time")
    @ApiModelProperty(value = "审核时间")
    private Long inspectTime;
    /**
     * 转账时间
     **/
    @Column(name = "transfer_time")
    @ApiModelProperty(value = "转账时间")
    private Long transferTime;


    public WithdrawApplyDO() {
        this.applyTime = 0L;
        this.inspectTime = 0L;
        this.transferTime = 0L;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(Double applyMoney) {
        this.applyMoney = applyMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }

    public String getInspectRemark() {
        return inspectRemark;
    }

    public void setInspectRemark(String inspectRemark) {
        this.inspectRemark = inspectRemark;
    }

    public String getTransferRemark() {
        return transferRemark;
    }

    public void setTransferRemark(String transferRemark) {
        this.transferRemark = transferRemark;
    }

    public Long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Long applyTime) {
        this.applyTime = applyTime;
    }

    public Long getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(Long inspectTime) {
        this.inspectTime = inspectTime;
    }

    public Long getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Long transferTime) {
        this.transferTime = transferTime;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public String toString() {
        return "WithdrawApplyDO{" +
                "id=" + id +
                ", applyMoney=" + applyMoney +
                ", status='" + status + '\'' +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", applyRemark='" + applyRemark + '\'' +
                ", inspectRemark='" + inspectRemark + '\'' +
                ", transferRemark='" + transferRemark + '\'' +
                ", applyTime=" + applyTime +
                ", inspectTime=" + inspectTime +
                ", transferTime=" + transferTime +
                '}';
    }
}
	
