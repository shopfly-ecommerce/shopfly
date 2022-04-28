/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


/**
 * 会员收货地址表实体
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-26 08:52:27
 */
@Table(name = "es_member_address")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberAddress implements Serializable {

    private static final long serialVersionUID = 5386739629590247L;

    /**
     * primary key ID
     */
    @Id(name = "addr_id")
    @ApiModelProperty(hidden = true)
    private Integer addrId;
    /**
     * Member ID
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "Member ID", hidden = true)
    private Integer memberId;
    /**
     * Consignee name
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "Consignee name", required = true)
    @NotEmpty(message = "Consignee name is required")
    private String name;
    /**
     * Country name
     */
    @Column(name = "country")
    @ApiModelProperty(name = "country", value = "Country name", required = true)
    @NotEmpty(message = "Country name is required")
    private String country;
    /**
     * City name
     */
    @Column(name = "city")
    @ApiModelProperty(name = "city", value = "City name", required = true)
    @NotEmpty(message = "City name is required")
    private String city;
    /**
     * Full address
     */
    @Column(name = "addr")
    @ApiModelProperty(name = "addr", value = "Full address", required = true)
    @NotEmpty(message = "Full address is required")
    private String addr;
    /**
     * Contact number
     */
    @Column(name = "mobile")
    @ApiModelProperty(name = "mobile", value = "Contact number", required = true)
    @NotEmpty(message = "Contact number is required")
    private String mobile;
    /**
     * Is it the default address
     */
    @Column(name = "def_addr")
    @Max(value = 1, message = "Parameter error")
    @Min(value = 0, message = "Parameter error")
    @NotNull(message = "Default address is required")
    @ApiModelProperty(name = "def_addr", value = "Default address", required = true)
    private Integer defAddr;
    /**
     * Address alias
     */
    @Column(name = "ship_address_name")
    @ApiModelProperty(name = "ship_address_name", value = "Address alias")
    private String shipAddressName;
    /**
     * Country code
     */
    @Column(name = "country_code")
    @ApiModelProperty(name = "country_code", value = "Country code", required = true)
    @NotEmpty(message = "Country code is required")
    private String countryCode;
    /**
     * State name
     */
    @Column(name = "state_name")
    @ApiModelProperty(name = "state_name", value = "State name")
    private String stateName;
    /**
     * State code
     */
    @Column(name = "state_code")
    @ApiModelProperty(name = "state_code", value = "State code")
    private String stateCode;
    /**
     * Zip code
     */
    @Column(name = "zip_code")
    @ApiModelProperty(name = "zip_code", value = "Zip code", required = true)
    @NotEmpty(message = "Zip code is required")
    private String zipCode;

    public Integer getAddrId() {
        return addrId;
    }

    public void setAddrId(Integer addrId) {
        this.addrId = addrId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getDefAddr() {
        return defAddr;
    }

    public void setDefAddr(Integer defAddr) {
        this.defAddr = defAddr;
    }

    public String getShipAddressName() {
        return shipAddressName;
    }

    public void setShipAddressName(String shipAddressName) {
        this.shipAddressName = shipAddressName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
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
        MemberAddress that = (MemberAddress) o;
        return Objects.equals(addrId, that.addrId) &&
                Objects.equals(memberId, that.memberId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(addr, that.addr) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(defAddr, that.defAddr) &&
                Objects.equals(shipAddressName, that.shipAddressName) &&
                Objects.equals(countryCode, that.countryCode) &&
                Objects.equals(stateName, that.stateName) &&
                Objects.equals(stateCode, that.stateCode) &&
                Objects.equals(zipCode, that.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addrId, memberId, name, country, city, addr, mobile, defAddr, shipAddressName, countryCode, stateName, stateCode, zipCode);
    }

    @Override
    public String toString() {
        return "MemberAddress{" +
                "addrId=" + addrId +
                ", memberId=" + memberId +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", addr='" + addr + '\'' +
                ", mobile='" + mobile + '\'' +
                ", defAddr=" + defAddr +
                ", shipAddressName='" + shipAddressName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}