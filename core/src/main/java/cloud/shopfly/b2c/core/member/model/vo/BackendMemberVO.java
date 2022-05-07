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
package cloud.shopfly.b2c.core.member.model.vo;


import cloud.shopfly.b2c.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * Background Home Page Membervo
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 14:27:48
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BackendMemberVO implements Serializable {


    /**
     * nickname
     */
    @Column(name = "nickname")
    @ApiModelProperty(name = "nickname", value = "nickname", required = false)
    private String nickname;
    /**
     * email
     */
    @Column(name = "email")
    @ApiModelProperty(name = "email", value = "email", required = false)
    private String email;
    /**
     * Membership registration Time
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "createTime", value = "Membership registration Time", required = false)
    private Long createTime;

    /**
     * Mobile phone number
     */
    @Column(name = "mobile")
    @ApiModelProperty(name = "mobile", value = "Mobile phone number", required = true)
    private String mobile;


    @Column(name = "uname")
    @ApiModelProperty(name = "uname", value = "Member user name", required = true)
    private String uname;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Override
    public String toString() {
        return "BackendMemberVO{" +
                "nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                ", mobile='" + mobile + '\'' +
                ", uname='" + uname + '\'' +
                '}';
    }
}
