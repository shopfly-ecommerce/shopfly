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
package cloud.shopfly.b2c.core.system.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

/**
 * Platform contact Information
 *
 * @author zh
 * @version v7.0
 * @date 18/5/30 In the afternoon3:08
 * @since v7.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InformationSetting {
    /**
     * Platform to address
     */
    @ApiModelProperty(name = "address", value = "Platform to address")
    private String address;

    /**
     * provinceid
     */
    @ApiModelProperty(name = "province_id", value = "provinceid", required = false)
    private Integer provinceId;
    /**
     * The cityid
     */
    @ApiModelProperty(name = "city_id", value = "The cityid", required = false)
    private Integer cityId;
    /**
     * countyid
     */
    @ApiModelProperty(name = "county_id", value = "countyid", required = false)
    private Integer countyId;
    /**
     * The town ofid
     */
    @ApiModelProperty(name = "town_id", value = "The town ofid", required = false)
    private Integer townId;
    /**
     * province
     */
    @ApiModelProperty(name = "province", value = "province", required = false)
    private String province;
    /**
     * The city
     */
    @ApiModelProperty(name = "city", value = "The city", required = false)
    private String city;
    /**
     * county
     */
    @ApiModelProperty(name = "county", value = "county", required = false)
    private String county;
    /**
     * The town of
     */
    @ApiModelProperty(name = "town", value = "The town of", required = false)
    private String town;
    /**
     * qq
     */
    @ApiModelProperty(name = "qq", value = "qq", required = false)
    private String qq;
    /**
     * email
     */
    @ApiModelProperty(name = "email", value = "email", required = false)
    private String email;

    /**
     * phone
     */
    @ApiModelProperty(name = "phone", value = "phone", required = false)
    private String phone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "InformationSetting{" +
                "address='" + address + '\'' +
                ", provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", countyId=" + countyId +
                ", townId=" + townId +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", town='" + town + '\'' +
                ", qq='" + qq + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
