/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * 注释
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderConsigneeVO implements Serializable {

    private static final long serialVersionUID = -6095473417556550895L;

    @ApiModelProperty(value = "订单号", name = "order_sn", hidden = true)
    private String orderSn;

    @ApiModelProperty(value = "收货人姓名", name = "ship_name")
    private String shipName;

    @ApiModelProperty(value = "订单备注", name = "remark")
    private String remark;

    @ApiModelProperty(value = "国家", name = "ship_country")
    private String shipCountry;

    @ApiModelProperty(value = "州/省名称", name = "ship_state")
    private String shipState;

    @ApiModelProperty(value = "城市", name = "ship_city")
    private String shipCity;

    @ApiModelProperty(value = "国家编码", name = "ship_country_code")
    private String shipCountryCode;

    @ApiModelProperty(value = "州/省编码", name = "ship_state_code")
    private String shipStateCode;

    @ApiModelProperty(value = "收货地址", name = "ship_addr")
    private String shipAddr;

    @ApiModelProperty(value = "联系电话", name = "ship_mobile")
    private String shipMobile;

    @ApiModelProperty(value = "邮编", name = "ship_zip")
    private String shipZip;

    @ApiModelProperty(value = "送货时间", name = "receive_time")
    private String receiveTime;

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

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipCountryCode() {
        return shipCountryCode;
    }

    public void setShipCountryCode(String shipCountryCode) {
        this.shipCountryCode = shipCountryCode;
    }

    public String getShipStateCode() {
        return shipStateCode;
    }

    public void setShipStateCode(String shipStateCode) {
        this.shipStateCode = shipStateCode;
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

    public String getShipZip() {
        return shipZip;
    }

    public void setShipZip(String shipZip) {
        this.shipZip = shipZip;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
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
        return Objects.equals(orderSn, that.orderSn) &&
                Objects.equals(shipName, that.shipName) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(shipCountry, that.shipCountry) &&
                Objects.equals(shipState, that.shipState) &&
                Objects.equals(shipCity, that.shipCity) &&
                Objects.equals(shipCountryCode, that.shipCountryCode) &&
                Objects.equals(shipStateCode, that.shipStateCode) &&
                Objects.equals(shipAddr, that.shipAddr) &&
                Objects.equals(shipMobile, that.shipMobile) &&
                Objects.equals(shipZip, that.shipZip) &&
                Objects.equals(receiveTime, that.receiveTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderSn, shipName, remark, shipCountry, shipState, shipCity, shipCountryCode, shipStateCode, shipAddr, shipMobile, shipZip, receiveTime);
    }

    @Override
    public String toString() {
        return "OrderConsigneeVO{" +
                "orderSn='" + orderSn + '\'' +
                ", shipName='" + shipName + '\'' +
                ", remark='" + remark + '\'' +
                ", shipCountry='" + shipCountry + '\'' +
                ", shipState='" + shipState + '\'' +
                ", shipCity='" + shipCity + '\'' +
                ", shipCountryCode='" + shipCountryCode + '\'' +
                ", shipStateCode='" + shipStateCode + '\'' +
                ", shipAddr='" + shipAddr + '\'' +
                ", shipMobile='" + shipMobile + '\'' +
                ", shipZip='" + shipZip + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                '}';
    }
}
