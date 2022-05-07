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


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * QQLogin user information entity
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

    @ApiModelProperty(name = "headimgurl", value = "Head portrait", required = false,hidden = true)
    private String headimgurl;

    @ApiModelProperty(name = "accessToken", value = "appEnd login incomingaccess_token", required = true)
    private String accesstoken;

    @ApiModelProperty(name = "nickName", value = "The user nickname", required = false)
    private String nickname;

    @ApiModelProperty(name = "gender", value = "gender", required = false)
    private String gender;

    @ApiModelProperty(name = "country", value = "Country name", required = false)
    private String country;

    @ApiModelProperty(name = "city", value = "city", required = false)
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
