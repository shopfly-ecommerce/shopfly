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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


/**
 * Member parameter passing
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 14:27:48
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberDTO {
    /**
     * Member login user name
     */
    @Pattern(regexp = "^(?![0-9]+$)[\\u4e00-\\u9fa5_0-9A-Za-z]{2,20}$", message = "The user name cannot contain digits or special characters2-20A character")
    @ApiModelProperty(name = "username", value = "Member login user name")
    private String username;
    /**
     * Member mobile Phone Number
     */
    @NotEmpty(message = "The cell phone number cannot be empty")
    @ApiModelProperty(name = "mobile", value = "Member mobile Phone Number")
    private String mobile;
    /**
     * Password
     */
    @Pattern(regexp = "[a-fA-F0-9]{32}", message = "The password format is incorrect")
    @ApiModelProperty(name = "password", value = "Password")
    private String password;
    /**
     * SMS verification code
     */
    @NotEmpty(message = "The SMS verification code cannot be empty")
    @ApiModelProperty(name = "sms_code", value = "SMS verification code")
    private String smsCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "MemberDTO{" +
                "username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", smsCode='" + smsCode + '\'' +
                '}';
    }
}
