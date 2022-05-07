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
 * Buyer modification membershipDTO
 *
 * @author zh
 * @version v7.0
 * @date 18/4/26 In the afternoon10:40
 * @since v7.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberEditDTO implements Serializable {

    private static final long serialVersionUID = -2199494753773301940L;

    /**
     * nickname
     */
    @Column(name = "nickname")
    @ApiModelProperty(name = "nickname", value = "nickname", required = true)
    @Length(min = 2, max = 20, message = "Member nickname must be2to20Between a")
    private String nickname;
    /**
     * Member of the gender
     */
    @Column(name = "sex")
    @Min(message = "The value must be a number and1For male,0For female", value = 0)
    @Max(message = "The value must be a number and1For male,0For female", value = 1)
    @NotNull(message = "Member gender cannot be blank")
    @ApiModelProperty(name = "sex", value = "Member of the gender,1For men,0For female", required = true)
    private Integer sex;
    /**
     * Members birthday
     */
    @Column(name = "birthday")
    @ApiModelProperty(name = "birthday", value = "Members birthday")
    private Long birthday;
    /**
     * Detailed address
     */
    @Column(name = "address")
    @ApiModelProperty(name = "address", value = "Detailed address")
    private String address;
    /**
     * email
     */
    @Column(name = "email")
    @Email(message = "The mailbox format is incorrect")
    @ApiModelProperty(name = "email", value = "email")
    private String email;
    /**
     * Machine number
     */
    @Column(name = "tel")
    @ApiModelProperty(name = "tel", value = "Machine number")
    private String tel;
    /**
     * Member of the head
     */
    @Column(name = "face")
    @ApiModelProperty(name = "face", value = "Member of the head")
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
     * Name of City
     */
    @Column(name = "city")
    @ApiModelProperty(name = "city", value = "Name of City", required = false)
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
