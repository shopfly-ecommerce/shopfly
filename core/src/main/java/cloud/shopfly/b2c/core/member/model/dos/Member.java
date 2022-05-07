
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
package cloud.shopfly.b2c.core.member.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import cloud.shopfly.b2c.framework.validation.annotation.Mobile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;


/**
 * Membership form entity
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 14:27:48
 */
@Table(name = "es_member")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Member implements Serializable {

    private static final long serialVersionUID = 3227466962489949L;
    /**
     * membersID
     */
    @Id(name = "member_id")
    @ApiModelProperty(hidden = true)
    private Integer memberId;
    /**
     * Member login user name
     */
    @Column(name = "uname")
    @NotEmpty(message = "Member login user name cannot be empty")
    @ApiModelProperty(name = "uname", value = "Member login user name", required = true)
    private String uname;
    /**
     * email
     */
    @Column(name = "email")
    @Email(message = "Malformed")
    @ApiModelProperty(name = "email", value = "email", required = false)
    private String email;
    /**
     * Member login password
     */
    @Column(name = "password")
    @ApiModelProperty(name = "password", value = "Member login password", required = false)
    private String password;
    /**
     * Membership registration Time
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "createTime", value = "Membership registration Time", required = false)
    private Long createTime;
    /**
     * Member of the gender
     */
    @Column(name = "sex")
    @Min(message = "Must be a number,1", value = 0)
    @ApiModelProperty(name = "sex", value = "Member of the gender,1For men,0For female", required = false)
    private Integer sex;
    /**
     * Members birthday
     */
    @Column(name = "birthday")
    @ApiModelProperty(name = "birthday", value = "Members birthday", required = false)
    private Long birthday;
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
    /**
     * Detailed address
     */
    @Column(name = "address")
    @ApiModelProperty(name = "address", value = "Detailed address", required = false)
    private String address;
    /**
     * Mobile phone number
     */
    @Column(name = "mobile")
    @NotEmpty(message = "The mobile phone number cannot be empty")
    @Mobile
    @ApiModelProperty(name = "mobile", value = "Mobile phone number", required = true)
    private String mobile;
    /**
     * Machine number
     */
    @Column(name = "tel")
    @ApiModelProperty(name = "tel", value = "Machine number", required = false)
    private String tel;
    /**
     * Level score
     */
    @Column(name = "grade_point")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "grade_point", value = "Level score", required = false)
    private Integer gradePoint;

    /**
     * consumption score
     */
    @Column(name = "consum_point")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "consum_point", value = "consumption score", required = false)
    private Integer consumPoint;
    /**
     * membersMSN
     */
    @Column(name = "msn")
    @ApiModelProperty(name = "msn", value = "membersMSN", required = false)
    private String msn;
    /**
     * Member of the note
     */
    @Column(name = "remark")
    @ApiModelProperty(name = "remark", value = "Member of the note", required = false)
    private String remark;
    /**
     * Last landing time
     */
    @Column(name = "last_login")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "last_login", value = "Last landing time", required = false)
    private Long lastLogin;
    /**
     * Log in number
     */
    @Column(name = "login_count")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "login_count", value = "Log in number", required = false)
    private Integer loginCount;
    /**
     * Whether the message is validated
     */
    @Column(name = "is_cheked")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "is_cheked", value = "Whether the message is validated", required = false)
    private Integer isCheked;
    /**
     * RegisterIPaddress
     */
    @Column(name = "register_ip")
    @ApiModelProperty(name = "register_ip", value = "RegisterIPaddress", required = false)
    private String registerIp;
    /**
     * Whether the recommended points have been completed
     */
    @Column(name = "recommend_point_state")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "recommend_point_state", value = "Whether the recommended points have been completed", required = false)
    private Integer recommendPointState;
    /**
     * Whether the member information is perfect
     */
    @Column(name = "info_full")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "info_full", value = "Whether the member information is perfect,0Is not perfect", required = false)
    private Integer infoFull;
    /**
     * find_code
     */
    @Column(name = "find_code")
    @ApiModelProperty(name = "find_code", value = "find_code", required = false)
    private String findCode;
    /**
     * Member of the head
     */
    @Column(name = "face")
    @ApiModelProperty(name = "face", value = "Member of the head", required = false)
    private String face;
    /**
     * Id number
     */
    @Column(name = "midentity")
    @ApiModelProperty(name = "midentity", value = "Id number", required = false)
    private Integer midentity;
    /**
     * The member state
     */
    @Column(name = "disabled")
    @ApiModelProperty(name = "disabled", value = "The member state,0For the normal-1Is in the recycle bin", required = false)
    private Integer disabled;
    /**
     * nickname
     */
    @Column(name = "nickname")
    @ApiModelProperty(name = "nickname", value = "nickname", required = false)
    private String nickname;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(Integer gradePoint) {
        this.gradePoint = gradePoint;
    }

    public Integer getConsumPoint() {
        return consumPoint;
    }

    public void setConsumPoint(Integer consumPoint) {
        this.consumPoint = consumPoint;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getIsCheked() {
        return isCheked;
    }

    public void setIsCheked(Integer isCheked) {
        this.isCheked = isCheked;
    }

    @JsonIgnore
    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public Integer getRecommendPointState() {
        return recommendPointState;
    }

    public void setRecommendPointState(Integer recommendPointState) {
        this.recommendPointState = recommendPointState;
    }

    public Integer getInfoFull() {
        return infoFull;
    }

    public void setInfoFull(Integer infoFull) {
        this.infoFull = infoFull;
    }

    public String getFindCode() {
        return findCode;
    }

    public void setFindCode(String findCode) {
        this.findCode = findCode;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getMidentity() {
        return midentity;
    }

    public void setMidentity(Integer midentity) {
        this.midentity = midentity;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(memberId, member.memberId) &&
                Objects.equals(uname, member.uname) &&
                Objects.equals(email, member.email) &&
                Objects.equals(password, member.password) &&
                Objects.equals(createTime, member.createTime) &&
                Objects.equals(sex, member.sex) &&
                Objects.equals(birthday, member.birthday) &&
                Objects.equals(country, member.country) &&
                Objects.equals(countryCode, member.countryCode) &&
                Objects.equals(stateName, member.stateName) &&
                Objects.equals(stateCode, member.stateCode) &&
                Objects.equals(city, member.city) &&
                Objects.equals(address, member.address) &&
                Objects.equals(mobile, member.mobile) &&
                Objects.equals(tel, member.tel) &&
                Objects.equals(gradePoint, member.gradePoint) &&
                Objects.equals(consumPoint, member.consumPoint) &&
                Objects.equals(msn, member.msn) &&
                Objects.equals(remark, member.remark) &&
                Objects.equals(lastLogin, member.lastLogin) &&
                Objects.equals(loginCount, member.loginCount) &&
                Objects.equals(isCheked, member.isCheked) &&
                Objects.equals(registerIp, member.registerIp) &&
                Objects.equals(recommendPointState, member.recommendPointState) &&
                Objects.equals(infoFull, member.infoFull) &&
                Objects.equals(findCode, member.findCode) &&
                Objects.equals(face, member.face) &&
                Objects.equals(midentity, member.midentity) &&
                Objects.equals(disabled, member.disabled) &&
                Objects.equals(nickname, member.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, uname, email, password, createTime, sex, birthday, country, countryCode, stateName, stateCode, city, address, mobile, tel, gradePoint, consumPoint, msn, remark, lastLogin, loginCount, isCheked, registerIp, recommendPointState, infoFull, findCode, face, midentity, disabled, nickname);
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", uname='" + uname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", tel='" + tel + '\'' +
                ", gradePoint=" + gradePoint +
                ", consumPoint=" + consumPoint +
                ", msn='" + msn + '\'' +
                ", remark='" + remark + '\'' +
                ", lastLogin=" + lastLogin +
                ", loginCount=" + loginCount +
                ", isCheked=" + isCheked +
                ", registerIp='" + registerIp + '\'' +
                ", recommendPointState=" + recommendPointState +
                ", infoFull=" + infoFull +
                ", findCode='" + findCode + '\'' +
                ", face='" + face + '\'' +
                ", midentity=" + midentity +
                ", disabled=" + disabled +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
