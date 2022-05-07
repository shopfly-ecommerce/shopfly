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
package cloud.shopfly.b2c.core.goods.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * Parameter group entity
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-20 16:14:17
 */
@Table(name = "es_parameter_group")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ParameterGroupDO implements Serializable {

    private static final long serialVersionUID = 2394621849767871L;

    /**
     * A primary key
     */
    @Id(name = "group_id")
    @ApiModelProperty(hidden = true)
    private Integer groupId;
    /**
     * Parameter group name
     */
    @Column(name = "group_name")
    @ApiModelProperty(name = "group_name", value = "Parameter group name", required = true)
    @NotEmpty(message = "The parameter group name cannot be empty")
    @Length(max = 50, message = "Parameter group names cannot exceed50word")
    private String groupName;
    /**
     * Associative classificationid
     */
    @Column(name = "category_id")
    @ApiModelProperty(name = "category_id", value = "Associative classificationid", required = true)
    @NotNull(message = "The classification of an association cannot be empty")
    private Integer categoryId;
    /**
     * sort
     */
    @Column(name = "sort")
    @ApiModelProperty(hidden = true)
    private Integer sort;

    @PrimaryKeyField
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "ParameterGroupDO [groupId=" + groupId + ", groupName=" + groupName + ", categoryId=" + categoryId
                + ", sort=" + sort + "]";
    }

}
