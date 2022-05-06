
/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 会员表实体
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
     * 会员ID
     */
    @Id(name = "member_id")
    @ApiModelProperty(hidden = true)
    private Integer memberId;
    /**
     * 会员登陆用户名
     */
    @Column(name = "uname")
    @NotEmpty(message = "会员登陆用户名不能为空")
    @ApiModelProperty(name = "uname", value = "会员登陆用户名", required = true)
    private String uname;
    /**
     * 邮箱
     */
    @Column(name = "email")
    @Email(message = "格式不正确")
    @ApiModelProperty(name = "email", value = "邮箱", required = false)
    private String email;
    /**
     * 会员登陆密码
     */
    @Column(name = "password")
    @ApiModelProperty(name = "password", value = "会员登陆密码", required = false)
    private String password;
    /**
     * 会员注册时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "createTime", value = "会员注册时间", required = false)
    private Long createTime;
    /**
     * 会员性别
     */
    @Column(name = "sex")
    @Min(message = "必须为数字,1", value = 0)
    @ApiModelProperty(name = "sex", value = "会员性别,1为男，0为女", required = false)
    private Integer sex;
    /**
     * 会员生日
     */
    @Column(name = "birthday")
    @ApiModelProperty(name = "birthday", value = "会员生日", required = false)
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
     * 所属城市名称
     */
    @Column(name = "city")
    @ApiModelProperty(name = "city", value = "所属城市名称", required = false)
    private String city;
    /**
     * 详细地址
     */
    @Column(name = "address")
    @ApiModelProperty(name = "address", value = "详细地址", required = false)
    private String address;
    /**
     * 手机号码
     */
    @Column(name = "mobile")
    @NotEmpty(message = "手机号码不能为空")
    @Mobile
    @ApiModelProperty(name = "mobile", value = "手机号码", required = true)
    private String mobile;
    /**
     * 座机号码
     */
    @Column(name = "tel")
    @ApiModelProperty(name = "tel", value = "座机号码", required = false)
    private String tel;
    /**
     * 等级积分
     */
    @Column(name = "grade_point")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "grade_point", value = "等级积分", required = false)
    private Integer gradePoint;

    /**
     * 消费积分
     */
    @Column(name = "consum_point")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "consum_point", value = "消费积分", required = false)
    private Integer consumPoint;
    /**
     * 会员MSN
     */
    @Column(name = "msn")
    @ApiModelProperty(name = "msn", value = "会员MSN", required = false)
    private String msn;
    /**
     * 会员备注
     */
    @Column(name = "remark")
    @ApiModelProperty(name = "remark", value = "会员备注", required = false)
    private String remark;
    /**
     * 上次登陆时间
     */
    @Column(name = "last_login")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "last_login", value = "上次登陆时间", required = false)
    private Long lastLogin;
    /**
     * 登陆次数
     */
    @Column(name = "login_count")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "login_count", value = "登陆次数", required = false)
    private Integer loginCount;
    /**
     * 邮件是否已验证
     */
    @Column(name = "is_cheked")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "is_cheked", value = "邮件是否已验证", required = false)
    private Integer isCheked;
    /**
     * 注册IP地址
     */
    @Column(name = "register_ip")
    @ApiModelProperty(name = "register_ip", value = "注册IP地址", required = false)
    private String registerIp;
    /**
     * 是否已经完成了推荐积分
     */
    @Column(name = "recommend_point_state")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "recommend_point_state", value = "是否已经完成了推荐积分", required = false)
    private Integer recommendPointState;
    /**
     * 会员信息是否完善
     */
    @Column(name = "info_full")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "info_full", value = "会员信息是否完善，0为未完善", required = false)
    private Integer infoFull;
    /**
     * find_code
     */
    @Column(name = "find_code")
    @ApiModelProperty(name = "find_code", value = "find_code", required = false)
    private String findCode;
    /**
     * 会员头像
     */
    @Column(name = "face")
    @ApiModelProperty(name = "face", value = "会员头像", required = false)
    private String face;
    /**
     * 身份证号
     */
    @Column(name = "midentity")
    @ApiModelProperty(name = "midentity", value = "身份证号", required = false)
    private Integer midentity;
    /**
     * 会员状态
     */
    @Column(name = "disabled")
    @ApiModelProperty(name = "disabled", value = "会员状态,0为正常 -1为在回收站中", required = false)
    private Integer disabled;
    /**
     * 昵称
     */
    @Column(name = "nickname")
    @ApiModelProperty(name = "nickname", value = "昵称", required = false)
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