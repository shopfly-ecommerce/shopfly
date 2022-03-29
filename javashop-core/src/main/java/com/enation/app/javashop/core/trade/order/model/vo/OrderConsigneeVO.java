/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.model.vo;

import com.enation.app.javashop.core.base.context.Region;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 注释
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
public class OrderConsigneeVO {

    @ApiModelProperty(value = "订单号")
    private String orderSn;

    @ApiModelProperty(value = "收货人姓名")
    private String shipName;

    @ApiModelProperty(value = "订单备注")
    private String remark;

    @ApiModelProperty(value = "收货地址")
    private String shipAddr;

    @ApiModelProperty(value = "收货人手机号")
    private String shipMobile;

    @ApiModelProperty(value = "收货人电话")
    private String shipTel;

    @ApiModelProperty(value = "送货时间")
    private String receiveTime;

    @ApiModelProperty(name = "region", value = "地区id")
    private Region region;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShipAddr() {
        return shipAddr;
    }

    public void setShipAddr(String shipAddr) {
        this.shipAddr = shipAddr;
    }

    public String getShipMobile() {
        return shipMobile;
    }

    public void setShipMobile(String shipMobile) {
        this.shipMobile = shipMobile;
    }

    public String getShipTel() {
        return shipTel;
    }

    public void setShipTel(String shipTel) {
        this.shipTel = shipTel;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "OrderConsigneeVO{" +
                "orderSn='" + orderSn + '\'' +
                ", shipName='" + shipName + '\'' +
                ", remark='" + remark + '\'' +
                ", shipAddr='" + shipAddr + '\'' +
                ", shipMobile='" + shipMobile + '\'' +
                ", shipTel='" + shipTel + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", region=" + region +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderConsigneeVO that = (OrderConsigneeVO) o;

        return new EqualsBuilder()
                .append(orderSn, that.orderSn)
                .append(shipName, that.shipName)
                .append(remark, that.remark)
                .append(shipAddr, that.shipAddr)
                .append(shipMobile, that.shipMobile)
                .append(shipTel, that.shipTel)
                .append(receiveTime, that.receiveTime)
                .append(region, that.region)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(orderSn)
                .append(shipName)
                .append(remark)
                .append(shipAddr)
                .append(shipMobile)
                .append(shipTel)
                .append(receiveTime)
                .append(region)
                .toHashCode();
    }
}
