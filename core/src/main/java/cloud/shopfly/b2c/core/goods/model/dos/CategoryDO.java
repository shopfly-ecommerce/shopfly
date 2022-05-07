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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * Commodity classification entity
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-15 17:22:06
 */
@Table(name = "es_category")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CategoryDO implements Serializable {

    private static final long serialVersionUID = 1964321416223565L;

    /**
     * A primary key
     */
    @Id(name = "category_id")
    @ApiModelProperty(hidden = true)
    private Integer categoryId;

    /**
     * name
     */
    @Column()
    @ApiModelProperty(value = "name", required = true)
    @NotEmpty(message = "The category name cannot be empty")
    private String name;

    /**
     * Classification of the fatherid
     */
    @Column(name = "parent_id")
    @ApiModelProperty(name = "parent_id", value = "Classification of the fatheridAnd the top0", required = true)
    @NotNull(message = "The parent category cannot be empty")
    private Integer parentId;

    /**
     * Classified parent path
     */
    @Column(name = "category_path")
    @ApiModelProperty(hidden = true)
    private String categoryPath;

    /**
     * Quantity of goods under this category
     */
    @Column(name = "goods_count")
    @ApiModelProperty(hidden = true)
    private Integer goodsCount;

    /**
     * sort
     */
    @Column(name = "category_order")
    @ApiModelProperty(name = "category_order", value = "sort", required = false)
    private Integer categoryOrder;

    /**
     * Classification of icon
     */
    @Column()
    @ApiModelProperty(value = "Classification of icon", required = false)
    private String image;

    @PrimaryKeyField
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Integer getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(Integer categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "CategoryDO [categoryId=" + categoryId + ", name=" + name + ", parentId=" + parentId + ", categoryPath="
                + categoryPath + ", goodsCount=" + goodsCount + ", categoryOrder=" + categoryOrder + ", image=" + image
                + "]";
    }


}
