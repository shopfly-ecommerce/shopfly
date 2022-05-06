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
 * 分类品牌关联表实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-19 09:34:02
 */
@Table(name = "es_category_brand")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CategoryBrandDO implements Serializable {

    private static final long serialVersionUID = 3315719881926878L;

    /**
     * 分类id
     */
    @Column(name = "category_id")
    @ApiModelProperty(value = "分类id", required = false)
    private Integer categoryId;
    /**
     * 品牌id
     */
    @Column(name = "brand_id")
    @ApiModelProperty(value = "品牌id", required = false)
    private Integer brandId;

    public CategoryBrandDO() {

    }

    public CategoryBrandDO(Integer categoryId, Integer brandId) {
        super();
        this.categoryId = categoryId;
        this.brandId = brandId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    @Override
    public String toString() {
        return "CategoryBrandDO [categoryId=" + categoryId + ", brandId=" + brandId + "]";
    }

}