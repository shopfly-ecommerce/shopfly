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

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 规格项实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-20 09:31:27
 */
@Table(name = "es_specification")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SpecificationDO implements Serializable {

    private static final long serialVersionUID = 5111769180376075L;

    /**
     * 主键
     */
    @Id(name = "spec_id")
    @ApiModelProperty(hidden = true)
    private Integer specId;
    /**
     * 规格项名称
     */
    @Column(name = "spec_name")
    @NotEmpty(message = "规格项名称不能为空")
    @ApiModelProperty(name = "spec_name", value = "规格项名称", required = true)
    private String specName;
    /**
     * 是否被删除0 删除   1  没有删除
     */
    @Column(name = "disabled")
    @ApiModelProperty(hidden = true)
    private Integer disabled;
    /**
     * 规格描述
     */
    @Column(name = "spec_memo")
    @ApiModelProperty(name = "spec_memo", value = "规格描述", required = false)
    private String specMemo;

    public SpecificationDO() {
    }


    public SpecificationDO(String specName, Integer disabled, String specMemo) {
        super();
        this.specName = specName;
        this.disabled = disabled;
        this.specMemo = specMemo;
    }

    @PrimaryKeyField
    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getSpecMemo() {
        return specMemo;
    }

    public void setSpecMemo(String specMemo) {
        this.specMemo = specMemo;
    }

    @Override
    public String toString() {
        return "SpecificationDO [specId=" + specId + ", specName=" + specName + ", disabled=" + disabled + ", specMemo="
                + specMemo +  "]";
    }

}