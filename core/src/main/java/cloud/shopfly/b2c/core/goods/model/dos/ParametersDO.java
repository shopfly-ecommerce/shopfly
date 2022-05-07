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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * Parameter entities
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-20 16:14:31
 */
@Table(name = "es_parameters")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ParametersDO implements Serializable {

    private static final long serialVersionUID = 320610453789555L;

    /**
     * A primary key
     */
    @Id(name = "param_id")
    @ApiModelProperty(hidden = true)
    private Integer paramId;
    /**
     * name
     */
    @Column(name = "param_name")
    @ApiModelProperty(name = "param_name", value = "name", required = true)
    @NotEmpty(message = "Parameter Name This parameter is mandatory")
    @Length(max = 50, message = "The parameter name cannot exceed50word")
    private String paramName;
    /**
     * type1 Input item2 Select item
     */
    @Column(name = "param_type")
    @ApiModelProperty(name = "param_type", value = "type1 Input item2 Select item", required = true)
    @NotNull(message = "Parameter Type This parameter is mandatory")
    @Min(value = 1, message = "The value of the parameter type is incorrect")
    @Max(value = 2, message = "The value of the parameter type is incorrect")
    private Integer paramType;
    /**
     * Select a value when the parameter type is an option2", separated by commas (,)
     */
    @Column(name = "options")
    @ApiModelProperty(value = "Select a value when the parameter type is an option2  separated by commas (,)", required = false)
    private String options;
    /**
     * Whether indexable,0 Dont show1 According to
     */
    @Column(name = "is_index")
    @ApiModelProperty(name = "is_index", value = "Whether indexable,0 Dont show1 According to", required = true)
    @NotNull(message = "Indexable This parameter is mandatory")
    @Min(value = 0, message = "Indexable or not The value is incorrect")
    @Max(value = 1, message = "Indexable or not The value is incorrect")
    private Integer isIndex;
    /**
     * Mandatory Yes1    no0
     */
    @Column(name = "required")
    @ApiModelProperty(value = "Mandatory Yes1    no0", required = true)
    @NotNull(message = "Mandatory Mandatory")
    @Min(value = 0, message = "Mandatory Whether the input value is incorrect")
    @Max(value = 1, message = "Mandatory Whether the input value is incorrect")
    private Integer required;
    /**
     * Parameters of the groupid
     */
    @Column(name = "group_id")
    @ApiModelProperty(name = "group_id", value = "Parameters of the groupid", required = true)
    @NotNull(message = "The owning parameter group cannot be empty")
    private Integer groupId;
    /**
     * Categoriesid
     */
    @Column(name = "category_id")
    @ApiModelProperty(name = "category_id", value = "Categoriesid", hidden = true)
    private Integer categoryId;
    /**
     * sort
     */
    @Column(name = "sort")
    @ApiModelProperty(hidden = true)
    private Integer sort;

    @PrimaryKeyField
    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(Integer isIndex) {
        this.isIndex = isIndex;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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
        return "ParametersDO [paramId=" + paramId + ", paramName=" + paramName + ", paramType=" + paramType
                + ", options=" + options + ", isIndex=" + isIndex + ", required=" + required + ", groupId=" + groupId
                + ", categoryId=" + categoryId + ", sort=" + sort + "]";
    }

}
