/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * QQ登陆用户信息实体
 *
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-09-28
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QQUserDTO implements Serializable {

    private static final long serialVersionUID = -1232483319436590972L;

    @ApiModelProperty(name = "openid", value = "openid", required = true)
    private String openid;

    @ApiModelProperty(name = "unionid", value = "unionid", required = true)
    private String unionid;

    @ApiModelProperty(name = "headimgurl", value = "头像", required = false,hidden = true)
    private String headimgurl;

    @ApiModelProperty(name = "accessToken", value = "app端登陆传入access_token", required = true)
    private String accesstoken;

    @ApiModelProperty(name = "nickName", value = "用户昵称", required = false)
    private String nickname;

    @ApiModelProperty(name = "gender", value = "性别", required = false)
    private String gender;

    @ApiModelProperty(name = "country", value = "Country name", required = false)
    private String country;

    @ApiModelProperty(name = "city", value = "城市", required = false)
    private String city;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
}
