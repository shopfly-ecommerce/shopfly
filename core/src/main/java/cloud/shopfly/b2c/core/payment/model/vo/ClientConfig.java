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
package cloud.shopfly.b2c.core.payment.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: Client Configuration
 * @date 2018/4/1117:05
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ClientConfig implements Serializable {

    @ApiModelProperty(name = "fieldname")
    @NotEmpty(message = "The clientkeyCant be empty")
    private String key;

    @ApiModelProperty(name = "Field text prompt")
    private String name;

    @ApiModelProperty(name = "Field text prompt", value = "config_list")
    private List<PayConfigItem> configList;

    @ApiModelProperty(name = "Whether open1open0close", value = "is_open")
    @NotNull(message = "Whether to enable a client Cannot be left blank")
    @Min(value = 0, message = "Whether to enable a client The value is incorrect")
    @Max(value = 1, message = "Whether to enable a client The value is incorrect")
    private Integer isOpen;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PayConfigItem> getConfigList() {
        return configList;
    }

    public void setConfigList(List<PayConfigItem> configList) {
        this.configList = configList;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
}
