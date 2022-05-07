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
package cloud.shopfly.b2c.core.distribution.model.vo;

import cloud.shopfly.b2c.core.distribution.model.dos.WithdrawApplyDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WithdrawApplyVO {
    /**
     * id
     **/
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * Withdrawal amount
     **/
    @ApiModelProperty(value = "Withdrawal amount",name = "apply_money")
    private Double applyMoney;
    /**
     * Withdrawal state
     **/
    @ApiModelProperty(value = "Withdrawal state",name = "status")
    private String status;
    /**
     * membersid
     **/
    @ApiModelProperty(value = "membersid",name = "member_id")
    private Integer memberId;
    /**
     * Member name
     **/
    @ApiModelProperty(value = "The member name",name = "member_name")
    private String memberName;
    /**
     * Application note
     **/
    @ApiModelProperty(value = "Application note",name = "apply_remark")
    private String applyRemark;
    /**
     * Review the note
     **/
    @ApiModelProperty(value = "Review the note",name = "inspect_remark")
    private String inspectRemark;
    /**
     * Transfer note
     **/
    @ApiModelProperty(value = "Transfer note",name = "transfer_remark")
    private String transferRemark;
    /**
     * To apply for time
     **/
    @ApiModelProperty(value = "To apply for time",name = "apply_time")
    private Long applyTime;

    /**
     * Audit time
     **/
    @ApiModelProperty(value = "Audit time",name = "inspect_time")
    private Long inspectTime;

    /**
     * Transfer time
     **/
    @ApiModelProperty(value = "Transfer time",name = "transfer_time")
    private Long transferTime;

    /**
     * Withdrawal parameters
     **/
    @ApiModelProperty(value = "Withdrawal parameters",name = "bank_params")
    private BankParamsVO bankParamsVO;


    public WithdrawApplyVO(WithdrawApplyDO withdrawApplyDO) {
        this.id = withdrawApplyDO.getId();
        this.memberId = withdrawApplyDO.getMemberId();
        this.memberName = withdrawApplyDO.getMemberName();
        this.status = withdrawApplyDO.getStatus();
        this.inspectTime = withdrawApplyDO.getInspectTime();
        this.inspectRemark = withdrawApplyDO.getInspectRemark();
        this.transferTime = withdrawApplyDO.getTransferTime();
        this.transferRemark = withdrawApplyDO.getTransferRemark();
        this.applyMoney = withdrawApplyDO.getApplyMoney();
        this.applyRemark = withdrawApplyDO.getApplyRemark();
        this.applyTime = withdrawApplyDO.getApplyTime();
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

    public Long getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(Long inspectTime) {
        this.inspectTime = inspectTime;
    }

    public BankParamsVO getBankParamsVO() {
        return bankParamsVO;
    }

    public void setBankParamsVO(BankParamsVO bankParamsVO) {
        this.bankParamsVO = bankParamsVO;
    }

    @Override
    public String toString() {
        return "WithdrawApplyVO{" +
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

