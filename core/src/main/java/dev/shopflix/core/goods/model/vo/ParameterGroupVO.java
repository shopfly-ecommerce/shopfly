/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.model.vo;

import dev.shopflix.core.goods.model.dos.ParametersDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 参数组vo
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月20日 下午4:33:21
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ParameterGroupVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 724427321881170297L;
    @ApiModelProperty("参数组关联的参数集合")
    private List<ParametersDO> params;
    @ApiModelProperty("参数组名称")
    private String groupName;
    @ApiModelProperty("参数组id")
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
