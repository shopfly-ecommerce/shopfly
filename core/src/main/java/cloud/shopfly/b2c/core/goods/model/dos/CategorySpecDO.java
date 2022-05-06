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
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 分类规格关联表实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-20 10:04:26
 */
@Table(name = "es_category_spec")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CategorySpecDO implements Serializable {

    private static final long serialVersionUID = 3127259064985991L;

    /**
     * 分类id
     */
    @Column(name = "category_id")
    @ApiModelProperty(value = "分类id", required = false)
    private Integer categoryId;
    /**
     * 规格id
     */
    @Column(name = "spec_id")
    @ApiModelProperty(value = "规格id", required = false)
    private Integer specId;

    public CategorySpecDO() {

    }

    public CategorySpecDO(Integer categoryId, Integer specId) {
        super();
        this.categoryId = categoryId;
        this.specId = specId;
    }


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    @Override
    public String toString() {
        return "CategorySpecDO [categoryId=" + categoryId + ", specId=" + specId + "]";
    }


}