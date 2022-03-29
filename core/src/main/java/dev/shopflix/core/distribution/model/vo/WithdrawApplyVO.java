/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.model.vo;

import dev.shopflix.core.distribution.model.dos.WithdrawApplyDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WithdrawApplyVO {
    /**
     * id
     **/
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 提现金额
     **/
    @ApiModelProperty(value = "提现金额",name = "apply_money")
    private Double applyMoney;
    /**
     * 提现状态
     **/
    @ApiModelProperty(value = "提现状态",name = "status")
    private String status;
    /**
     * 会员id
     **/
    @ApiModelProperty(value = "会员id",name = "member_id")
    private Integer memberId;
    /**
     * 会员名称
     **/
    @ApiModelProperty(value = "会员名字",name = "member_name")
    private String memberName;
    /**
     * 申请备注
     **/
    @ApiModelProperty(value = "申请备注",name = "apply_remark")
    private String applyRemark;
    /**
     * 审核备注
     **/
    @ApiModelProperty(value = "审核备注",name = "inspect_remark")
    private String inspectRemark;
    /**
     * 转账备注
     **/
    @ApiModelProperty(value = "转账备注",name = "transfer_remark")
    private String transferRemark;
    /**
     * 申请时间
     **/
    @ApiModelProperty(value = "申请时间",name = "apply_time")
    private Long applyTime;

    /**
     * 审核时间
     **/
    @ApiModelProperty(value = "审核时间",name = "inspect_time")
    private Long inspectTime;

    /**
     * 转账时间
     **/
    @ApiModelProperty(value = "转账时间",name = "transfer_time")
    private Long transferTime;

    /**
     * 提现参数
     **/
    @ApiModelProperty(value = "提现参数",name = "bank_params")
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

