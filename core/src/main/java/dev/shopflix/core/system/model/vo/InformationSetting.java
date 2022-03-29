/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

/**
 * 平台联系方式
 *
 * @author zh
 * @version v7.0
 * @date 18/5/30 下午3:08
 * @since v7.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InformationSetting {
    /**
     * 平台地址
     */
    @ApiModelProperty(name = "address", value = "平台地址")
    private String address;

    /**
     * 省id
     */
    @ApiModelProperty(name = "province_id", value = "省id", required = false)
    private Integer provinceId;
    /**
     * 市id
     */
    @ApiModelProperty(name = "city_id", value = "市id", required = false)
    private Integer cityId;
    /**
     * 县id
     */
    @ApiModelProperty(name = "county_id", value = "县id", required = false)
    private Integer countyId;
    /**
     * 镇id
     */
    @ApiModelProperty(name = "town_id", value = "镇id", required = false)
    private Integer townId;
    /**
     * 省
     */
    @ApiModelProperty(name = "province", value = "省", required = false)
    private String province;
    /**
     * 市
     */
    @ApiModelProperty(name = "city", value = "市", required = false)
    private String city;
    /**
     * 县
     */
    @ApiModelProperty(name = "county", value = "县", required = false)
    private String county;
    /**
     * 镇
     */
    @ApiModelProperty(name = "town", value = "镇", required = false)
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
