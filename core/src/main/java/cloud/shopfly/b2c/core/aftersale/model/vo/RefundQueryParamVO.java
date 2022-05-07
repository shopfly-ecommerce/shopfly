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

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zjp
 * @version v7.0
 * @since v7.0 In the morning11:19 2018/5/2
 */
public class RefundQueryParamVO {
    @ApiModelProperty(value = "The page number", name = "page_no", hidden = true)
    private Integer pageNo;

    @ApiModelProperty(value = "Page size", name = "page_size", hidden = true)
    private Integer pageSize;

    @ApiModelProperty(allowableValues = "APPLY,PASS,REFUSE,STOCK_IN,WAIT_FOR_MANUAL,CANCEL,REFUNDING,REFUNDFAIL,COMPLETED", value = "Return of the goods(paragraph)Single state: APPLY/In the application,PASS/Apply for through,REFUSE/Audit refused to,STOCK_IN/Return of the goods入库,WAIT_FOR_MANUAL/To be handled manually,CANCEL/To apply for cancellation,REFUNDING/退paragraph中,REFUNDFAIL/退paragraph失败,COMPLETED/complete", name = "refund_status", required = false)
    private String refundStatus;

    @ApiModelProperty(value = "After type: CANCEL_ORDER Cancel the order,AFTER_SALE Apply for after sales", name = "refund_type", allowableValues = "CANCEL_ORDER,AFTER_SALE", required = false)
    private String refundType;

    @ApiModelProperty(value = "Return of the goods(paragraph)A single number", name = "sn", required = false)
    private String sn;

    @ApiModelProperty(hidden = true, value = "membersid")
    private Integer memberId;

    @ApiModelProperty(value = "Order no.", name = "order_sn", required = false)
    private String orderSn;

    @ApiModelProperty(name = "refuse_type", value = "type:A refundRETURN_MONEY,Return of the goodsRETURN_GOODS", allowableValues = "RETURN_MONEY,RETURN_GOODS", required = false)
    private String refuseType;

    @ApiModelProperty(value = "Starting time", name = "start_time", required = false)
    private String startTime;

    @ApiModelProperty(value = "The end of time", name = "end_time", required = false)
    private String endTime;

    @ApiModelProperty(value = "The refund wayOFFLINE Offline payment,ORIGINAL The way back", name = "refund_way", allowableValues = "OFFLINE,ORIGINAL")
    private String refundWay;


    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getRefuseType() {
        return refuseType;
    }

    public void setRefuseType(String refuseType) {
        this.refuseType = refuseType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getRefundWay() {
        return refundWay;
    }

    public void setRefundWay(String refundWay) {
        this.refundWay = refundWay;
    }

    @Override
    public String toString() {
        return "RefundQueryParamVO{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", refundStatus='" + refundStatus + '\'' +
                ", refundType='" + refundType + '\'' +
                ", sn='" + sn + '\'' +
                ", memberId=" + memberId +
                ", orderSn='" + orderSn + '\'' +
                ", refuseType='" + refuseType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
