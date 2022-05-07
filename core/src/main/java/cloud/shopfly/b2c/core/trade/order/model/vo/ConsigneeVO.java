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
package cloud.shopfly.b2c.core.trade.order.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * Consignee entity
 *
 * @author kingapex
 * @version 1.0
 * @created 2017years08month03day14:39:25
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConsigneeVO implements Serializable {

    private static final long serialVersionUID = 2499675140677613044L;
    @ApiModelProperty(value = "id")
    private Integer consigneeId;

    @ApiModelProperty(value = "Name of consignee")
    private String name;

    @ApiModelProperty(value = "countries")
    private String country;

    @ApiModelProperty(value = "state/Province name")
    private String stateName;

    @ApiModelProperty(value = "city")
    private String city;

    @ApiModelProperty(value = "Detailed address")
    private String address;

    @ApiModelProperty(value = "Mobile phone no.")
    private String mobile;

    @ApiModelProperty(value = "Country code")
    private String countryCode;

    @ApiModelProperty(value = "State code")
    private String stateCode;

    @ApiModelProperty(value = "Zip code")
    private String zipCode;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
        return Objects.equals(consigneeId, that.consigneeId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(country, that.country) &&
                Objects.equals(stateName, that.stateName) &&
                Objects.equals(city, that.city) &&
                Objects.equals(address, that.address) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(countryCode, that.countryCode) &&
                Objects.equals(stateCode, that.stateCode) &&
                Objects.equals(zipCode, that.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consigneeId, name, country, stateName, city, address, mobile, countryCode, stateCode, zipCode);
    }

    @Override
    public String toString() {
        return "ConsigneeVO{" +
                "consigneeId=" + consigneeId +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", stateName='" + stateName + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
