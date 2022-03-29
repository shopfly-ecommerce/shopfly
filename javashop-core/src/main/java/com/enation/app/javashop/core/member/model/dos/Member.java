
/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.model.dos;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.enation.app.javashop.framework.validation.annotation.Mobile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


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
     * 所属省份ID
     */
    @Column(name = "province_id")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "province_id", value = "所属省份ID", required = false)
    private Integer provinceId;
    /**
     * 所属城市ID
     */
    @Column(name = "city_id")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "city_id", value = "所属城市ID", required = false)
    private Integer cityId;
    /**
     * 所属县(区)ID
     */
    @Column(name = "county_id")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "county_id", value = "所属县(区)ID", required = false)
    private Integer countyId;
    /**
     * 所属城镇ID
     */
    @Column(name = "town_id")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "town_id", value = "所属城镇ID", required = false)
    private Integer townId;
    /**
     * 所属省份名称
     */
    @Column(name = "province")
    @ApiModelProperty(name = "province", value = "所属省份名称", required = false)
    private String province;
    /**
     * 所属城市名称
     */
    @Column(name = "city")
    @ApiModelProperty(name = "city", value = "所属城市名称", required = false)
    private String city;
    /**
     * 所属县(区)名称
     */
    @Column(name = "county")
    @ApiModelProperty(name = "county", value = "所属县(区)名称", required = false)
    private String county;
    /**
     * 所属城镇名称
     */
    @Column(name = "town")
    @ApiModelProperty(name = "town", value = "所属城镇名称", required = false)
    private String town;
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

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
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

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
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
                ", provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", countyId=" + countyId +
                ", townId=" + townId +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", town='" + town + '\'' +
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Member member = (Member) o;

        if (memberId != null ? !memberId.equals(member.memberId) : member.memberId != null) {
            return false;
        }
        if (uname != null ? !uname.equals(member.uname) : member.uname != null) {
            return false;
        }
        if (email != null ? !email.equals(member.email) : member.email != null) {
            return false;
        }
        if (password != null ? !password.equals(member.password) : member.password != null) {
            return false;
        }
        if (createTime != null ? !createTime.equals(member.createTime) : member.createTime != null) {
            return false;
        }
        if (sex != null ? !sex.equals(member.sex) : member.sex != null) {
            return false;
        }
        if (birthday != null ? !birthday.equals(member.birthday) : member.birthday != null) {
            return false;
        }
        if (provinceId != null ? !provinceId.equals(member.provinceId) : member.provinceId != null) {
            return false;
        }
        if (cityId != null ? !cityId.equals(member.cityId) : member.cityId != null) {
            return false;
        }
        if (countyId != null ? !countyId.equals(member.countyId) : member.countyId != null) {
            return false;
        }
        if (townId != null ? !townId.equals(member.townId) : member.townId != null) {
            return false;
        }
        if (province != null ? !province.equals(member.province) : member.province != null) {
            return false;
        }
        if (city != null ? !city.equals(member.city) : member.city != null) {
            return false;
        }
        if (county != null ? !county.equals(member.county) : member.county != null) {
            return false;
        }
        if (town != null ? !town.equals(member.town) : member.town != null) {
            return false;
        }
        if (address != null ? !address.equals(member.address) : member.address != null) {
            return false;
        }
        if (mobile != null ? !mobile.equals(member.mobile) : member.mobile != null) {
            return false;
        }
        if (tel != null ? !tel.equals(member.tel) : member.tel != null) {
            return false;
        }
        if (gradePoint != null ? !gradePoint.equals(member.gradePoint) : member.gradePoint != null) {
            return false;
        }
        if (consumPoint != null ? !consumPoint.equals(member.consumPoint) : member.consumPoint != null) {
            return false;
        }
        if (msn != null ? !msn.equals(member.msn) : member.msn != null) {
            return false;
        }
        if (remark != null ? !remark.equals(member.remark) : member.remark != null) {
            return false;
        }
        if (lastLogin != null ? !lastLogin.equals(member.lastLogin) : member.lastLogin != null) {
            return false;
        }
        if (loginCount != null ? !loginCount.equals(member.loginCount) : member.loginCount != null) {
            return false;
        }
        if (isCheked != null ? !isCheked.equals(member.isCheked) : member.isCheked != null) {
            return false;
        }
        if (registerIp != null ? !registerIp.equals(member.registerIp) : member.registerIp != null) {
            return false;
        }
        if (recommendPointState != null ? !recommendPointState.equals(member.recommendPointState) : member.recommendPointState != null) {
            return false;
        }
        if (infoFull != null ? !infoFull.equals(member.infoFull) : member.infoFull != null) {
            return false;
        }
        if (findCode != null ? !findCode.equals(member.findCode) : member.findCode != null) {
            return false;
        }
        if (face != null ? !face.equals(member.face) : member.face != null) {
            return false;
        }
        if (midentity != null ? !midentity.equals(member.midentity) : member.midentity != null) {
            return false;
        }
        if (disabled != null ? !disabled.equals(member.disabled) : member.disabled != null) {
            return false;
        }
        return nickname != null ? nickname.equals(member.nickname) : member.nickname == null;
    }

    @Override
    public int hashCode() {
        int result = memberId != null ? memberId.hashCode() : 0;
        result = 31 * result + (uname != null ? uname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (provinceId != null ? provinceId.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (countyId != null ? countyId.hashCode() : 0);
        result = 31 * result + (townId != null ? townId.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (county != null ? county.hashCode() : 0);
        result = 31 * result + (town != null ? town.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (gradePoint != null ? gradePoint.hashCode() : 0);
        result = 31 * result + (consumPoint != null ? consumPoint.hashCode() : 0);
        result = 31 * result + (msn != null ? msn.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (lastLogin != null ? lastLogin.hashCode() : 0);
        result = 31 * result + (loginCount != null ? loginCount.hashCode() : 0);
        result = 31 * result + (isCheked != null ? isCheked.hashCode() : 0);
        result = 31 * result + (registerIp != null ? registerIp.hashCode() : 0);
        result = 31 * result + (recommendPointState != null ? recommendPointState.hashCode() : 0);
        result = 31 * result + (infoFull != null ? infoFull.hashCode() : 0);
        result = 31 * result + (findCode != null ? findCode.hashCode() : 0);
        result = 31 * result + (face != null ? face.hashCode() : 0);
        result = 31 * result + (midentity != null ? midentity.hashCode() : 0);
        result = 31 * result + (disabled != null ? disabled.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        return result;
    }
}