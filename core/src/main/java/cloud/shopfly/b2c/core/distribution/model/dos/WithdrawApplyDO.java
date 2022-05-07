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
 * Withdrawal application
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/21 In the afternoon2:37
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
     * Withdrawal amount
     **/
    @Column(name = "apply_money")
    @ApiModelProperty(value = "Withdrawal amount")
    private Double applyMoney;
    /**
     * Withdrawal state
     **/
    @Column()
    @ApiModelProperty(value = "Withdrawal state")
    private String status;
    /**
     * membersid
     **/
    @Column(name = "member_id")
    @ApiModelProperty(value = "membersid")
    private Integer memberId;
    /**
     * The member name
     **/
    @Column(name = "member_name")
    @ApiModelProperty(value = "Member name")
    private String memberName;
    /**
     * Application note
     **/
    @Column(name = "apply_remark")
    @ApiModelProperty(value = "Application note")
    private String applyRemark;
    /**
     * Review the note
     **/
    @Column(name = "inspect_remark")
    @ApiModelProperty(value = "Review the note")
    private String inspectRemark;
    /**
     * Transfer note
     **/
    @Column(name = "transfer_remark")
    @ApiModelProperty(value = "Transfer note")
    private String transferRemark;
    /**
     * To apply for time
     **/
    @Column(name = "apply_time")
    @ApiModelProperty(value = "To apply for time")
    private Long applyTime;
    /**
     * Audit time
     **/
    @Column(name = "inspect_time")
    @ApiModelProperty(value = "Audit time")
    private Long inspectTime;
    /**
     * Transfer time
     **/
    @Column(name = "transfer_time")
    @ApiModelProperty(value = "Transfer time")
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
	
