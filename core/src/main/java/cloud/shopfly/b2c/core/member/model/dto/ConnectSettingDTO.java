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

import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingParametersVO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author zjp
 * @version v7.0
 * @Description 信任登录参数DTO
 * @ClassName ConnectSettingVO
 * @since v7.0 下午3:46 2018/6/5
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConnectSettingDTO implements Serializable {

    /**
     * Id
     */
    @ApiModelProperty(name = "id",value = "id")
    private Integer id;

    /**
     * 参数配置名称
     */
    @ApiModelProperty(name = "name", value = "参数配置名称")
    @NotEmpty(message="参数配置名称必填")
    private String name;
    /**
     * 信任登录类型
     */

    @ApiModelProperty(name = "type", value = "授权类型",allowableValues = "QQ,WEIBO,WECHAT,ALIPAY")
    private String type;

    private List<ConnectSettingParametersVO> clientList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ConnectSettingParametersVO> getClientList() {
        return clientList;
    }

    public void setClientList(List<ConnectSettingParametersVO> clientList) {
        this.clientList = clientList;
    }

    @Override
    public String toString() {
        return "ConnectSettingDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
