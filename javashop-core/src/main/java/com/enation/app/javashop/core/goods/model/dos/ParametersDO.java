/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goods.model.dos;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;
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
 * 参数实体
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
     * 主键
     */
    @Id(name = "param_id")
    @ApiModelProperty(hidden = true)
    private Integer paramId;
    /**
     * 参数名称
     */
    @Column(name = "param_name")
    @ApiModelProperty(name = "param_name", value = "参数名称", required = true)
    @NotEmpty(message = "参数名称必填")
    @Length(max = 50, message = "参数名称不能超过50字")
    private String paramName;
    /**
     * 参数类型1 输入项   2 选择项
     */
    @Column(name = "param_type")
    @ApiModelProperty(name = "param_type", value = "参数类型1 输入项   2 选择项", required = true)
    @NotNull(message = "参数类型必选")
    @Min(value = 1, message = "参数类型传值不正确")
    @Max(value = 2, message = "参数类型传值不正确")
    private Integer paramType;
    /**
     * 选择值，当参数类型是选择项2时，必填，逗号分隔
     */
    @Column(name = "options")
    @ApiModelProperty(value = "选择值，当参数类型是选择项2时，必填，逗号分隔", required = false)
    private String options;
    /**
     * 是否可索引，0 不显示 1 显示
     */
    @Column(name = "is_index")
    @ApiModelProperty(name = "is_index", value = "是否可索引，0 不显示 1 显示", required = true)
    @NotNull(message = "是否可索引必选")
    @Min(value = 0, message = "是否可索引传值不正确")
    @Max(value = 1, message = "是否可索引传值不正确")
    private Integer isIndex;
    /**
     * 是否必填是  1    否   0
     */
    @Column(name = "required")
    @ApiModelProperty(value = "是否必填是  1    否   0", required = true)
    @NotNull(message = "是否必填必选")
    @Min(value = 0, message = "是否必填传值不正确")
    @Max(value = 1, message = "是否必填传值不正确")
    private Integer required;
    /**
     * 参数分组id
     */
    @Column(name = "group_id")
    @ApiModelProperty(name = "group_id", value = "参数分组id", required = true)
    @NotNull(message = "所属参数组不能为空")
    private Integer groupId;
    /**
     * 分类id
     */
    @Column(name = "category_id")
    @ApiModelProperty(name = "category_id", value = "分类id", hidden = true)
    private Integer categoryId;
    /**
     * 排序
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