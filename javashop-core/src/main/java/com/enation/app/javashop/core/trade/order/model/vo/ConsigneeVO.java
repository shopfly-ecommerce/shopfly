/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 收货人实体
 *
 * @author kingapex
 * @version 1.0
 * @created 2017年08月03日14:39:25
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConsigneeVO implements Serializable {

    private static final long serialVersionUID = 2499675140677613044L;
    @ApiModelProperty(value = "id")
    private Integer consigneeId;

    @ApiModelProperty(value = "收货人姓名")
    private String name;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String county;

    @ApiModelProperty(value = "街道")
    private String town;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "省ID")
    private Integer provinceId;

    @ApiModelProperty(value = "市ID")
    private Integer countyId;

    @ApiModelProperty(value = "区ID")
    private Integer cityId;

    @ApiModelProperty(value = "街道ID")
    private Integer townId;


    @Override
    public String toString() {
        return "ConsigneeVO{" +
                "consigneeId=" + consigneeId +
                ", name='" + name + '\'' +
                ", county='" + county + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", town='" + town + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", telephone='" + telephone + '\'' +
                ", countyId=" + countyId +
                ", provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", townId=" + townId +
                '}';
    }

    public Integer getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(Integer consigneeId) {
        this.consigneeId = consigneeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsigneeVO that = (ConsigneeVO) o;

        return new EqualsBuilder()
                .append(consigneeId, that.consigneeId)
                .append(name, that.name)
                .append(county, that.county)
                .append(province, that.province)
                .append(city, that.city)
                .append(town, that.town)
                .append(address, that.address)
                .append(mobile, that.mobile)
                .append(telephone, that.telephone)
                .append(countyId, that.countyId)
                .append(provinceId, that.provinceId)
                .append(cityId, that.cityId)
                .append(townId, that.townId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(consigneeId)
                .append(name)
                .append(county)
                .append(province)
                .append(city)
                .append(town)
                .append(address)
                .append(mobile)
                .append(telephone)
                .append(countyId)
                .append(provinceId)
                .append(cityId)
                .append(townId)
                .toHashCode();
    }
}
