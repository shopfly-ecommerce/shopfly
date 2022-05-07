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

import java.io.Serializable;


/**
 * Draft commodity parameter sheet entities
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-26 11:31:20
 */
@Table(name = "es_draft_goods_params")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DraftGoodsParamsDO implements Serializable {

    private static final long serialVersionUID = 1137617128769441L;

    /**
     * ID
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * The draftID
     */
    @Column(name = "draft_goods_id")
    @ApiModelProperty(name = "draft_goods_id", value = "The draftID", required = false)
    private Integer draftGoodsId;
    /**
     * parameterID
     */
    @Column(name = "param_id")
    @ApiModelProperty(name = "param_id", value = "parameterID", required = false)
    private Integer paramId;
    /**
     * Parameter names
     */
    @Column(name = "param_name")
    @ApiModelProperty(name = "param_name", value = "Parameter names", required = false)
    private String paramName;
    /**
     * The parameter value
     */
    @Column(name = "param_value")
    @ApiModelProperty(name = "param_value", value = "The parameter value", required = false)
    private String paramValue;

    public DraftGoodsParamsDO() {
    }

    public DraftGoodsParamsDO(GoodsParamsDO param) {
        this.paramId = param.getParamId();
        this.paramName = param.getParamName();
        this.paramValue = param.getParamValue();
    }

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDraftGoodsId() {
        return draftGoodsId;
    }

    public void setDraftGoodsId(Integer draftGoodsId) {
        this.draftGoodsId = draftGoodsId;
    }

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

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "DraftGoodsParamsDO [id=" + id + ", draftGoodsId=" + draftGoodsId + ", paramId=" + paramId
                + ", paramName=" + paramName + ", paramValue=" + paramValue + "]";
    }


}
