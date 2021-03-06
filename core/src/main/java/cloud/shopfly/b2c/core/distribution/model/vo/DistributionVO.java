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

import cloud.shopfly.b2c.core.distribution.model.dos.DistributionDO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Distributor displayvo
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-23 In the morning9:57
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DistributionVO {

    @ApiModelProperty(value = "The current membersid", name = "id")
    private Integer id;
    @ApiModelProperty(value = "lv1id", name = "lv1_id")
    private Integer lv1Id;
    @ApiModelProperty(value = "lv2id", name = "lv2_id")
    private Integer lv2Id;
    @ApiModelProperty(value = "The name")
    private String name;
    @ApiModelProperty(value = "The name of the template", name = "current_tpl_name")
    private String currentTplName;
    @ApiModelProperty(value = "templateid", name = "current_tpl_id")
    private Integer currentTplId;
    @ApiModelProperty(value = "The number of referrals", name = "downline")
    private Integer downline;
    @ApiModelProperty(value = "The rebate amount")
    private Double rebateTotal;

    @Column(name = "order_num")
    @ApiModelProperty(name = "order_num", value = "Commission related order number", required = true)
    private Integer orderNum = 0;

    @Column(name = "turnover_price")
    @ApiModelProperty(name = "turnover_price", value = "Total turnover", required = true)
    private Double turnoverPrice = 0D;

    @ApiModelProperty(value = "offline", name = "item")
    private List<DistributionVO> item;

    @ApiModelProperty(value = "statements", name = "bill_member_vo")
    private BillMemberVO billMemberVO;

    @ApiModelProperty(name = "create_time", value = "Last update", required = false)
    private Long createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentTplName() {
        return currentTplName;
    }

    public void setCurrentTplName(String currentTplName) {
        this.currentTplName = currentTplName;
    }

    public Integer getDownline() {
        return downline;
    }

    public void setDownline(Integer downline) {
        this.downline = downline;
    }

    public Double getRebateTotal() {
        return rebateTotal;
    }

    public void setRebateTotal(Double rebateTotal) {
        this.rebateTotal = rebateTotal;
    }

    public List<DistributionVO> getItem() {
        return item;
    }

    public void setItem(List<DistributionVO> item) {
        this.item = item;
    }

    public Integer getLv1Id() {
        return lv1Id;
    }

    public void setLv1Id(Integer lv1Id) {
        this.lv1Id = lv1Id;
    }

    public Integer getLv2Id() {
        return lv2Id;
    }

    public void setLv2Id(Integer lv2Id) {
        this.lv2Id = lv2Id;
    }

    public Integer getId() {
        return id;
    }

    public DistributionVO(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Double getTurnoverPrice() {
        return turnoverPrice;
    }

    public void setTurnoverPrice(Double turnoverPrice) {
        this.turnoverPrice = turnoverPrice;
    }

    public Integer getCurrentTplId() {
        return currentTplId;
    }

    public void setCurrentTplId(Integer currentTplId) {
        this.currentTplId = currentTplId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public DistributionVO(DistributionDO ddo) {
        this.id = ddo.getMemberId();
        this.lv1Id = ddo.getMemberIdLv1();
        this.lv2Id = ddo.getMemberIdLv2();
        this.rebateTotal = ddo.getRebateTotal();
        this.downline = ddo.getDownline();
        this.turnoverPrice = ddo.getTurnoverPrice();
        this.name = ddo.getMemberName();
        this.currentTplName = ddo.getCurrentTplName();
        this.orderNum = ddo.getOrderNum();
        this.currentTplId = ddo.getCurrentTplId();
        this.createTime = ddo.getCreateTime();
    }

    public BillMemberVO getBillMemberVO() {
        return billMemberVO;
    }

    public void setBillMemberVO(BillMemberVO billMemberVO) {
        this.billMemberVO = billMemberVO;
    }

    @Override
    public String toString() {
        return "DistributionVO{" +
                "id=" + id +
                ", lv1Id=" + lv1Id +
                ", lv2Id=" + lv2Id +
                ", name='" + name + '\'' +
                ", currentTplName='" + currentTplName + '\'' +
                ", currentTplId=" + currentTplId +
                ", downline=" + downline +
                ", rebateTotal=" + rebateTotal +
                ", orderNum=" + orderNum +
                ", turnoverPrice=" + turnoverPrice +
                ", item=" + item +
                ", billMemberVO=" + billMemberVO +
                ", createTime=" + createTime +
                '}';
    }
}
