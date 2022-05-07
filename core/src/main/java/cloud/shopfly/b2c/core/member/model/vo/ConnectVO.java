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

import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zjp
 * @version v7.0
 * @Description Trust the loginVO
 * @ClassName ConnectDO
 * @since v7.0 In the afternoon2:43 2018/6/20
 */
@Table(name = "es_connect")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConnectVO {

    /**
     * Trusted Login type
     */
    @ApiModelProperty(name = "union_type", value = "Trusted Login type")
    private String unionType;
    /**
     * Whether the binding
     */
    @ApiModelProperty(name = "is_bind", value = "Whether the binding：true Is binding,false unbounded")
    private Boolean isBind;

    public String getUnionType() {
        return unionType;
    }

    public void setUnionType(String unionType) {
        this.unionType = unionType;
    }

    public Boolean getIsBind() {
        return isBind;
    }

    public void setIsBind(Boolean bind) {
        isBind = bind;
    }

    @Override
    public String toString() {
        return "ConnectVO{" +
                ", unionType='" + unionType + '\'' +
                ", isBind=" + isBind +
                '}';
    }
}
