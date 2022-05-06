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
 * 微信小程序登陆传参
 *
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-09-24
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WeChatMiniLoginDTO implements Serializable {


    private static final long serialVersionUID = 8061241514328873785L;

    @ApiModelProperty(name = "edata", value = "encryptedData", required = true)
    private String edata;

    @ApiModelProperty(name = "iv", value = "iv", required = true)
    private String iv;

    @ApiModelProperty(name = "code", value = "code", required = true)
    private String code;

    @ApiModelProperty(name = "uuid", value = "随机数", required = true)
    private String uuid;

    public String getEdata() {
        return edata;
    }

    public void setEdata(String edata) {
        this.edata = edata;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
