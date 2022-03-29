/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.model.vo;


import dev.shopflix.core.goods.model.dos.GoodsParamsDO;
import dev.shopflix.framework.database.annotation.Column;
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
