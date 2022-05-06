/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

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

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "州名/省名")
    private String stateName;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "手机号")
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
