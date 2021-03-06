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
package cloud.shopfly.b2c.core.aftersale.model.vo;

import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundWayEnum;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import io.swagger.annotations.ApiModelProperty;

/**
 * @version v7.0
 * @Description: Refund Receipt ExportexcelVO
 * @author: zjp
 * @Date: 2018/7/23 14:14
 */
public class ExportRefundExcelVO {

    /**
     * refund(cargo)A single paragraphid
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * Return of the goods(paragraph)A single number
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "Return of the goods(paragraph)A single number", required = false)
    private String sn;
    /**
     * Member name
     */
    @Column(name = "member_name")
    @ApiModelProperty(value = "Member name")
    private String memberName;

    /**
     * Order no.
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "Order no.", required = false)
    private String orderSn;
    /**
     * refund(cargo)Kind of state
     */
    @Column(name = "refund_status")
    @ApiModelProperty(name = "refund_status", value = "refund(cargo)Kind of state", required = false)
    private String refundStatus;
    /**
     * Last update
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "Last update", required = false)
    private Long createTime;
    /**
     * The refund amount
     */
    @Column(name = "refund_price")
    @ApiModelProperty(name = "refund_price", value = "The refund amount", required = false)
    private Double refundPrice;

    /**
     * The refund way(The original way back, online payment, offline payment)
     */
    @Column(name = "refund_way")
    @ApiModelProperty(name = "refund_way", value = "The refund way(The original way back, offline payment)", required = false)
    private String refundWay;
    @Column(name = "refund_time")
    @ApiModelProperty(name = "refund_time", value = "A refund of time", hidden = true)
    private Long refundTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getRefundStatus() {
        return RefundStatusEnum.valueOf(refundStatus).description();
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public String getRefundWay() {
        return RefundWayEnum.valueOf(refundWay).description();
    }

    public void setRefundWay(String refundWay) {
        this.refundWay = refundWay;
    }

    public Long getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Long refundTime) {
        this.refundTime = refundTime;
    }

    @Override
    public String toString() {
        return "ExportRefundExcelVO{" +
                "id=" + id +
                ", sn='" + sn + '\'' +
                ", memberName='" + memberName + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", refundStatus='" + refundStatus + '\'' +
                ", createTime=" + createTime +
                ", refundPrice=" + refundPrice +
                ", refundWay='" + refundWay + '\'' +
                ", refundTime=" + refundTime +
                '}';
    }
}
