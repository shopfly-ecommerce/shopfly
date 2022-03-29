/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.dos;

import dev.shopflix.core.base.context.Region;
import dev.shopflix.core.base.context.RegionFormat;
import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.Table;
import dev.shopflix.framework.validation.annotation.Mobile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


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
     * 主键ID
     */
    @Id(name = "addr_id")
    @ApiModelProperty(hidden = true)
    private Integer addrId;
    /**
     * 会员ID
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "会员ID", required = false, hidden = true)
    private Integer memberId;
    /**
     * 收货人姓名
     */
    @Column(name = "name")
    @NotEmpty(message = "收货人姓名不能为空")
    @ApiModelProperty(name = "name", value = "收货人姓名", required = false)
    private String name;
    /**
     * 收货人国籍
     */
    @Column(name = "country")
    @ApiModelProperty(name = "country", value = "收货人国籍", required = false, hidden = true)
    private String country;
    /**
     * 所属省份ID
     */
    @Column(name = "province_id")
    @ApiModelProperty(name = "province_id", value = "所属省份ID", required = false, hidden = true)
    private Integer provinceId;
    /**
     * 所属城市ID
     */
    @Column(name = "city_id")
    @ApiModelProperty(name = "city_id", value = "所属城市ID", required = false, hidden = true)
    private Integer cityId;
    /**
     * 所属县(区)ID
     */
    @Column(name = "county_id")
    @ApiModelProperty(name = "county_id", value = "所属县(区)ID", required = false, hidden = true)
    private Integer countyId;
    /**
     * 所属城镇ID
     */
    @Column(name = "town_id")
    @ApiModelProperty(name = "town_id", value = "所属城镇ID", required = false, hidden = true)
    private Integer townId;
    /**
     * 所属县(区)名称
     */
    @Column(name = "county")
    @ApiModelProperty(name = "county", value = "所属县(区)名称", required = false, hidden = true)
    private String county;
    /**
     * 所属城市名称
     */
    @Column(name = "city")
    @ApiModelProperty(name = "city", value = "所属城市名称", required = false, hidden = true)
    private String city;
    /**
     * 所属省份名称
     */
    @Column(name = "province")
    @ApiModelProperty(name = "province", value = "所属省份名称", required = false, hidden = true)
    private String province;
    /**
     * 所属城镇名称
     */
    @Column(name = "town")
    @ApiModelProperty(name = "town", value = "所属城镇名称", required = false, hidden = true)
    private String town;
    /**
     * 详细地址
     */
    @Column(name = "addr")
    @NotEmpty(message = "详细地址不能为空")
    @ApiModelProperty(name = "addr", value = "详细地址", required = false)
    private String addr;

    /**
     * 联系电话(一般指座机)
     */
    @Column(name = "tel")
    @ApiModelProperty(name = "tel", value = "联系电话(一般指座机)", required = false)
    private String tel;
    /**
     * 手机号码
     */
    @Column(name = "mobile")
    @Mobile
    @ApiModelProperty(name = "mobile", value = "手机号码", required = false)
    private String mobile;
    /**
     * 是否为默认收货地址
     */
    @Column(name = "def_addr")
    @Max(value = 1, message = "是否为默认地址参数错误")
    @Min(value = 0, message = "是否为默认地址参数错误")
    @NotNull(message = "是否为默认地址不能为空")
    @ApiModelProperty(name = "def_addr", value = "是否为默认收货地址,1为默认", required = false)
    private Integer defAddr;
    /**
     * 地址别名
     */
    @Column(name = "ship_address_name")
    @ApiModelProperty(name = "ship_address_name", value = "地址别名", required = false)
    private String shipAddressName;

    @RegionFormat
    @ApiModelProperty(name = "region", value = "地区")
    private Region region;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    @JsonIgnore
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "MemberAddress{" +
                "addrId=" + addrId +
                ", memberId=" + memberId +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", countyId=" + countyId +
                ", townId=" + townId +
                ", county='" + county + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", town='" + town + '\'' +
                ", addr='" + addr + '\'' +
                ", tel='" + tel + '\'' +
                ", mobile='" + mobile + '\'' +
                ", defAddr=" + defAddr +
                ", shipAddressName='" + shipAddressName + '\'' +
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

        MemberAddress that = (MemberAddress) o;

        if (addrId != null ? !addrId.equals(that.addrId) : that.addrId != null) {
            return false;
        }
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (country != null ? !country.equals(that.country) : that.country != null) {
            return false;
        }
        if (provinceId != null ? !provinceId.equals(that.provinceId) : that.provinceId != null) {
            return false;
        }
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) {
            return false;
        }
        if (countyId != null ? !countyId.equals(that.countyId) : that.countyId != null) {
            return false;
        }
        if (townId != null ? !townId.equals(that.townId) : that.townId != null) {
            return false;
        }
        if (county != null ? !county.equals(that.county) : that.county != null) {
            return false;
        }
        if (city != null ? !city.equals(that.city) : that.city != null) {
            return false;
        }
        if (province != null ? !province.equals(that.province) : that.province != null) {
            return false;
        }
        if (town != null ? !town.equals(that.town) : that.town != null) {
            return false;
        }
        if (addr != null ? !addr.equals(that.addr) : that.addr != null) {
            return false;
        }
        if (tel != null ? !tel.equals(that.tel) : that.tel != null) {
            return false;
        }
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) {
            return false;
        }
        if (defAddr != null ? !defAddr.equals(that.defAddr) : that.defAddr != null) {
            return false;
        }
        if (shipAddressName != null ? !shipAddressName.equals(that.shipAddressName) : that.shipAddressName != null) {
            return false;
        }
        return region != null ? region.equals(that.region) : that.region == null;
    }

    @Override
    public int hashCode() {
        int result = addrId != null ? addrId.hashCode() : 0;
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (provinceId != null ? provinceId.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (countyId != null ? countyId.hashCode() : 0);
        result = 31 * result + (townId != null ? townId.hashCode() : 0);
        result = 31 * result + (county != null ? county.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (town != null ? town.hashCode() : 0);
        result = 31 * result + (addr != null ? addr.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (defAddr != null ? defAddr.hashCode() : 0);
        result = 31 * result + (shipAddressName != null ? shipAddressName.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        return result;
    }
    /**
     * 获取最低级地区
     * @return
     */
    public Integer actualAddress() {
//        if(this.townId!=null&&townId!=0) {
//            return townId;
//        }
        if(this.countyId!=null&&countyId!=0) {
            return countyId;
        }if(this.cityId!=null&&cityId!=0) {
            return cityId;
        }
        return provinceId;
    }
}