/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.model.vo;

import dev.shopflix.core.distribution.model.dos.BillMemberDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * 会员结算单
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/21 上午10:51
 */

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BillMemberVO {


    @ApiModelProperty(value = "总结算单id", name = "total_id")
    private Integer totalId;
    @ApiModelProperty(value = "结算单id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "会员id", name = "member_id")
    private Integer memberId;

    @ApiModelProperty(value = "会员名称", name = "member_name")
    private String memberName;

    @ApiModelProperty(value = "开始时间", name = "start_time")
    private Long startTime;

    @ApiModelProperty(value = "结束时间", name = "end_time")
    private Long endTime;

    @ApiModelProperty(value = "最终结算金额", name = "final_money")
    private Double finalMoney;

    @ApiModelProperty(value = "提成金额", name = "push_money")
    private Double pushMoney;

    @ApiModelProperty(value = "订单数量", name = "order_count")
    private Integer orderCount;

    @ApiModelProperty(value = "订单金额", name = "order_money")
    private Double orderMoney;

    @ApiModelProperty(value = "返还订单金额", name = "return_order_money")
    private Double returnOrderMoney;

    @ApiModelProperty(value = "返还订单数", name = "return_order_count")
    private Integer returnOrderCount;

    @ApiModelProperty(value = "返还订单金额", name = "return_push_money")
    private Double returnPushMoney;

    @ApiModelProperty(value = "编号", name = "sn")
    private String sn;

    private List<BillMemberVO> item;

    public BillMemberVO() {
        this.finalMoney = 0D;
        this.pushMoney = 0D;
        this.returnPushMoney = 0D;
        this.orderCount = 0;
        this.orderMoney = 0D;
        this.returnOrderMoney = 0D;
        this.returnOrderCount = 0;
    }

    public BillMemberVO(BillMemberDO billMemberDO) {
        this.id = billMemberDO.getId();
        this.totalId = billMemberDO.getTotalId();
        this.memberId = billMemberDO.getMemberId();
        this.memberName = billMemberDO.getMemberName();
        this.startTime = billMemberDO.getStartTime();
        this.endTime = billMemberDO.getEndTime();
        this.finalMoney = billMemberDO.getFinalMoney();
        this.pushMoney = billMemberDO.getPushMoney();
        this.returnPushMoney = billMemberDO.getReturnPushMoney();
        this.orderCount = billMemberDO.getOrderCount();
        this.orderMoney = billMemberDO.getOrderMoney();
        this.returnOrderMoney = billMemberDO.getReturnOrderMoney();
        this.returnOrderCount = billMemberDO.getReturnOrderCount();
        this.sn = billMemberDO.getSn();
    }

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

    public Integer getTotalId() {
        return totalId;
    }

    public void setTotalId(Integer totalId) {
        this.totalId = totalId;
    }

    @Override
    public String toString() {
        return "BillMemberVO{" +
                "totalId=" + totalId +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", finalMoney=" + finalMoney +
                ", pushMoney=" + pushMoney +
                ", orderCount=" + orderCount +
                ", orderMoney=" + orderMoney +
                ", returnOrderMoney=" + returnOrderMoney +
                ", returnOrderCount=" + returnOrderCount +
                ", returnPushMoney=" + returnPushMoney +
                ", sn='" + sn + '\'' +
                '}';
    }

    public List<BillMemberVO> getItem() {
        return item;
    }

    public void setItem(List<BillMemberVO> item) {
        this.item = item;
    }
}