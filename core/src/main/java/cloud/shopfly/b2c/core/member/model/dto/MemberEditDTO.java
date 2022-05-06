/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.model.dto;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 买家修改会员DTO
 *
 * @author zh
 * @version v7.0
 * @date 18/4/26 下午10:40
 * @since v7.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberEditDTO implements Serializable {

    private static final long serialVersionUID = -2199494753773301940L;

    /**
     * 昵称
     */
    @Column(name = "nickname")
    @ApiModelProperty(name = "nickname", value = "昵称", required = true)
    @Length(min = 2, max = 20, message = "会员昵称必须为2到20位之间")
    private String nickname;
    /**
     * 会员性别
     */
    @Column(name = "sex")
    @Min(message = "必须为数字且1为男,0为女", value = 0)
    @Max(message = "必须为数字且1为男,0为女", value = 1)
    @NotNull(message = "会员性别不能为空")
    @ApiModelProperty(name = "sex", value = "会员性别,1为男，0为女", required = true)
    private Integer sex;
    /**
     * 会员生日
     */
    @Column(name = "birthday")
    @ApiModelProperty(name = "birthday", value = "会员生日")
    private Long birthday;
    /**
     * 详细地址
     */
    @Column(name = "address")
    @ApiModelProperty(name = "address", value = "详细地址")
    private String address;
    /**
     * 邮箱
     */
    @Column(name = "email")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(name = "email", value = "邮箱")
    private String email;
    /**
     * 座机号码
     */
    @Column(name = "tel")
    @ApiModelProperty(name = "tel", value = "座机号码")
    private String tel;
    /**
     * 会员头像
     */
    @Column(name = "face")
    @ApiModelProperty(name = "face", value = "会员头像")
    private String face;
    /**
     * Country name
     */
    @Column(name = "country")
    @ApiModelProperty(name = "country", value = "Country name", required = false)
    private String country;
    /**
     * Country name
     */
    @Column(name = "country_code")
    @ApiModelProperty(name = "country_code", value = "Country code", required = false)
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
     * 所属城市名称
     */
    @Column(name = "city")
    @ApiModelProperty(name = "city", value = "所属城市名称", required = false)
    private String city;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "MemberEditDTO{" +
                "nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", face='" + face + '\'' +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
