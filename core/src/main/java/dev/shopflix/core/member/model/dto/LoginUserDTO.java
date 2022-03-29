/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.dto;



import dev.shopflix.core.member.model.enums.ConnectTypeEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 请求登陆model
 *
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-09-24
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginUserDTO implements Serializable {

    private static final long serialVersionUID = -1232483319436590972L;

    @ApiModelProperty(name = "uuid", value = "此次登陆随机数", required = false)
    private String uuid;

    @ApiModelProperty(name = "tokenOutTime", value = "token过期时间", required = false)
    private Integer tokenOutTime;

    @ApiModelProperty(name = "refreshTokenOutTime", value = "refreshToken过期时间", required = false)
    private Integer refreshTokenOutTime;

    @ApiModelProperty(name = "openid", value = "openid", required = true)
    private String openid;

    @ApiModelProperty(name = "openType", value = "openid类型", required = false)
    private ConnectTypeEnum openType;

    @ApiModelProperty(name = "unionid", value = "unionid", required = true)
    private String unionid;

    @ApiModelProperty(name = "unionType", value = "unionid类型", required = false)
    private ConnectTypeEnum unionType;


    @ApiModelProperty(name = "headimgurl", value = "头像", required = false,hidden = true)
    private String headimgurl;

    @ApiModelProperty(name = "nickName", value = "用户昵称", required = false)
    private String nickName;

    @ApiModelProperty(name = "sex", value = "性别：1:男;0:女", required = false)
    private Integer sex;

    @ApiModelProperty(name = "province", value = "省份", required = false)
    private String province;

    @ApiModelProperty(name = "city", value = "城市", required = false)
    private String city;

    @ApiModelProperty(name = "oldUuid", value = "重定向之前的uuid（分销使用）", required = false)
    private String oldUuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getTokenOutTime() {
        return tokenOutTime;
    }

    public void setTokenOutTime(Integer tokenOutTime) {
        this.tokenOutTime = tokenOutTime;
    }

    public Integer getRefreshTokenOutTime() {
        return refreshTokenOutTime;
    }

    public void setRefreshTokenOutTime(Integer refreshTokenOutTime) {
        this.refreshTokenOutTime = refreshTokenOutTime;
    }


    public ConnectTypeEnum getOpenType() {
        return openType;
    }

    public void setOpenType(ConnectTypeEnum openType) {
        this.openType = openType;
    }

    public ConnectTypeEnum getUnionType() {
        return unionType;
    }

    public void setUnionType(ConnectTypeEnum unionType) {
        this.unionType = unionType;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
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

    public String getOldUuid() {
        return oldUuid;
    }

    public void setOldUuid(String oldUuid) {
        this.oldUuid = oldUuid;
    }
}
