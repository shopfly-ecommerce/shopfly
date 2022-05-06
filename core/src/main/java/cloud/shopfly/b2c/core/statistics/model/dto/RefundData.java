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
package cloud.shopfly.b2c.core.statistics.model.dto;

import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * 退货数据
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018/3/25 下午10:49
 */

@Table(name = "es_sss_refund_data")
public class RefundData {
    @ApiModelProperty(value = "主键id")
    @Id
    private Integer id;

    @ApiModelProperty(value = "售后订单sn")
    @Column(name = "refund_sn")
    private String refundSn;

    @ApiModelProperty(value = "订单sn")
    @Column(name = "order_sn")
    private String orderSn;

    @ApiModelProperty(value = "退款金额")
    @Column(name = "refund_price")
    private Double refundPrice;

    @ApiModelProperty(value = "创建日期")
    @Column(name = "create_time")
    private Long createTime;


    public RefundData() {

    }

    public RefundData(RefundDO refund) {
        this.setCreateTime(refund.getCreateTime());
        this.setOrderSn(refund.getOrderSn());
        this.setRefundPrice(refund.getRefundPrice());
        this.setRefundSn(refund.getSn());
    }

    public RefundData(Map refund) {
        this.setCreateTime(Long.parseLong(refund.get("create_time").toString()));
        this.setOrderSn(refund.get("order_sn").toString());
        this.setRefundPrice(Double.parseDouble(refund.get("refund_price").toString()));
        this.setRefundSn(refund.get("refund_sn").toString());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getRefundSn() {
        return refundSn;
    }

    public void setRefundSn(String refundSn) {
        this.refundSn = refundSn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RefundData{" +
                ", refundSn='" + refundSn + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", refundPrice=" + refundPrice +
                ", createTime=" + createTime +
                '}';
    }
}
