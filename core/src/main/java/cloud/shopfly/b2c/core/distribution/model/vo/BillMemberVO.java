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

import cloud.shopfly.b2c.core.distribution.model.dos.BillMemberDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * Member statement
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/21 In the morning10:51
 */

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BillMemberVO {


    @ApiModelProperty(value = "General statementid", name = "total_id")
    private Integer totalId;
    @ApiModelProperty(value = "statementsid", name = "id")
    private Integer id;

    @ApiModelProperty(value = "membersid", name = "member_id")
    private Integer memberId;

    @ApiModelProperty(value = "Member name", name = "member_name")
    private String memberName;

    @ApiModelProperty(value = "The start time", name = "start_time")
    private Long startTime;

    @ApiModelProperty(value = "The end of time", name = "end_time")
    private Long endTime;

    @ApiModelProperty(value = "Final settlement amount", name = "final_money")
    private Double finalMoney;

    @ApiModelProperty(value = "Commission amount", name = "push_money")
    private Double pushMoney;

    @ApiModelProperty(value = "The order number", name = "order_count")
    private Integer orderCount;

    @ApiModelProperty(value = "Amount", name = "order_money")
    private Double orderMoney;

    @ApiModelProperty(value = "Return order amount", name = "return_order_money")
    private Double returnOrderMoney;

    @ApiModelProperty(value = "Return order number", name = "return_order_count")
    private Integer returnOrderCount;

    @ApiModelProperty(value = "Return order amount", name = "return_push_money")
    private Double returnPushMoney;

    @ApiModelProperty(value = "Serial number", name = "sn")
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
