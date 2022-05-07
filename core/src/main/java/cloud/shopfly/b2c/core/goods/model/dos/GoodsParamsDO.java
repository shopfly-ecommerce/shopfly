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
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;


/**
 * Item association parameter value entity
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-21 11:40:23
 */
@Table(name = "es_goods_params")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsParamsDO implements Serializable {

    private static final long serialVersionUID = 4134870721776090L;

    /**
     * A primary key
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * productid
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "productid", hidden = true)
    private Integer goodsId;
    /**
     * parameterid
     */
    @Column(name = "param_id")
    @ApiModelProperty(name = "param_id", value = "parameterid", required = true)
    private Integer paramId;
    /**
     * Parameter name
     */
    @Column(name = "param_name")
    @ApiModelProperty(name = "param_name", value = "Parameter name", required = true)
    private String paramName;
    /**
     * The parameter value
     */
    @Column(name = "param_value")
    @ApiModelProperty(name = "param_value", value = "The parameter value", required = true)
    @Length(max = 100, message = "Parameter value Cannot be greater than or equal to120")
    private String paramValue;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
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
        if (StringUtil.isEmpty(paramValue)) {
            return "";
        }
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "GoodsParamsDO [id=" + id + ", goodsId=" + goodsId + ", paramId=" + paramId + ", paramName=" + paramName
                + ", paramValue=" + paramValue + "]";
    }

}
