/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 收银台参数VO
 *
 * @author Snow create in 2018/7/11
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CashierVO {


    @ApiModelProperty(name = "ship_name", value = "收货人姓名")
    private String shipName;

    @ApiModelProperty(name = "ship_addr", value = "收货地址")
    private String shipAddr;

    @ApiModelProperty(name = "ship_mobile", value = "收货人手机")
    private String shipMobile;

    @ApiModelProperty(name = "ship_tel", value = "收货人电话")
    private String shipTel;

    @ApiModelProperty(name = "ship_province", value = "配送地区-省份")
    private String shipProvince;

    @ApiModelProperty(name = "ship_city", value = "配送地区-城市")
    private String shipCity;

    @ApiModelProperty(name = "ship_county", value = "配送地区-区(县)")
    private String shipCounty;

    @ApiModelProperty(name = "ship_town", value = "配送街道")
    private String shipTown;

    @ApiModelProperty(name = "need_pay_price", value = "应付金额")
    private Double needPayPrice;

    @ApiModelProperty(name = "pay_type_text", value = "支付方式")
    private String payTypeText;
    @ApiModelProperty(name = "count_down", value = "订单失效时间")
    private Long countDown;

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
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

    public String getShipProvince() {
        return shipProvince;
    }

    public void setShipProvince(String shipProvince) {
        this.shipProvince = shipProvince;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipCounty() {
        return shipCounty;
    }

    public void setShipCounty(String shipCounty) {
        this.shipCounty = shipCounty;
    }

    public String getShipTown() {
        return shipTown;
    }

    public void setShipTown(String shipTown) {
        this.shipTown = shipTown;
    }

    public Double getNeedPayPrice() {
        return needPayPrice;
    }

    public void setNeedPayPrice(Double needPayPrice) {
        this.needPayPrice = needPayPrice;
    }

    public String getPayTypeText() {
        return payTypeText;
    }

    public void setPayTypeText(String payTypeText) {
        this.payTypeText = payTypeText;
    }

    public Long getCountDown() {
        return countDown;
    }

    public void setCountDown(Long countDown) {
        this.countDown = countDown;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CashierVO cashierVO = (CashierVO) o;

        return new EqualsBuilder()
                .append(shipName, cashierVO.shipName)
                .append(shipAddr, cashierVO.shipAddr)
                .append(shipMobile, cashierVO.shipMobile)
                .append(shipTel, cashierVO.shipTel)
                .append(shipProvince, cashierVO.shipProvince)
                .append(shipCity, cashierVO.shipCity)
                .append(shipCounty, cashierVO.shipCounty)
                .append(shipTown, cashierVO.shipTown)
                .append(needPayPrice, cashierVO.needPayPrice)
                .append(payTypeText, cashierVO.payTypeText)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(shipName)
                .append(shipAddr)
                .append(shipMobile)
                .append(shipTel)
                .append(shipProvince)
                .append(shipCity)
                .append(shipCounty)
                .append(shipTown)
                .append(needPayPrice)
                .append(payTypeText)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "CashierVO{" +
                "shipName='" + shipName + '\'' +
                ", shipAddr='" + shipAddr + '\'' +
                ", shipMobile='" + shipMobile + '\'' +
                ", shipTel='" + shipTel + '\'' +
                ", shipProvince='" + shipProvince + '\'' +
                ", shipCity='" + shipCity + '\'' +
                ", shipCounty='" + shipCounty + '\'' +
                ", shipTown='" + shipTown + '\'' +
                ", needPayPrice=" + needPayPrice +
                ", payTypeText='" + payTypeText + '\'' +
                '}';
    }
}
