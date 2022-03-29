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
 * 请求微信https://api.weixin.qq.com/sns/userinfo返回的实体
 *
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-09-24
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WeChatUserDTO implements Serializable {

    private static final long serialVersionUID = -1232483319436590972L;

    @ApiModelProperty(name = "openid", value = "openid", required = true)
    private String openid;

    @ApiModelProperty(name = "unionid", value = "unionid", required = true)
    private String unionid;

    @ApiModelProperty(name = "headimgurl", value = "头像", required = false,hidden = true)
    private String headimgurl;

    @ApiModelProperty(name = "accessToken", value = "app端登陆传入access_token", required = true)
    private String accessToken;

    @ApiModelProperty(name = "nickName", value = "用户昵称", required = false)
    private String nickName;

    @ApiModelProperty(name = "sex", value = "性别：1:男;0:女", required = false)
    private Integer sex;

    @ApiModelProperty(name = "refreshToken", value = "app授权登陆时需要传入refreshToken", required = false)
    private String refreshToken;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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
