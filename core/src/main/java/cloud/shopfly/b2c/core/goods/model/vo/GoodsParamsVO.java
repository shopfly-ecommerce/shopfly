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


import cloud.shopfly.b2c.core.goods.model.dos.GoodsParamsDO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品关联参数的VO
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月26日 下午4:28:17
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsParamsVO extends GoodsParamsDO {

    private static final long serialVersionUID = -4904700751774005326L;

    @ApiModelProperty("1 输入项   2 选择项")
    @Column(name = "param_type")
    private Integer paramType;

    @ApiModelProperty(" 选择项的内容获取值，使用optionList")
    private String options;

    @ApiModelProperty("是否必填是  1    否   0")
    private Integer required;

    @ApiModelProperty("参数组id")
    @Column(name = "group_id")
    private Integer groupId;

    @ApiModelProperty("是否可索引  1 可以   0不可以")
    @Column(name = "is_index")
    private Integer isIndex;

    private String[] optionList;

    public void setOptionList(String[] optionList) {
        this.optionList = optionList;
    }

    public String[] getOptionList() {
        if (options != null) {
            return options.replaceAll("\r|\n", "").split(",");
        }
        return optionList;
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


    public Integer getIsIndex() {
        return isIndex;
    }


    public void setIsIndex(Integer isIndex) {
        this.isIndex = isIndex;
    }


    @Override
    public String toString() {
        return "GoodsParamsVO [paramType=" + paramType + ", options=" + options + ", required=" + required
                + ", groupId=" + groupId + ", isIndex=" + isIndex + "]";
    }

}
