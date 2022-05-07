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
package cloud.shopfly.b2c.core.goods.model.vo;

import cloud.shopfly.b2c.core.goods.model.dos.ParametersDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Parameter setvo
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month20On the afternoon4:33:21
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ParameterGroupVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 724427321881170297L;
    @ApiModelProperty("Parameter group Indicates the associated parameter set")
    private List<ParametersDO> params;
    @ApiModelProperty("Parameter group name")
    private String groupName;
    @ApiModelProperty("Parameter setid")
    private Integer groupId;

    public List<ParametersDO> getParams() {
        return params;
    }

    public void setParams(List<ParametersDO> params) {
        this.params = params;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "ParameterGroupVO [params=" + params + ", groupName=" + groupName + ", groupId=" + groupId + "]";
    }

}
