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
package cloud.shopfly.b2c.core.promotion.groupbuy.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * Group purchase classification entity
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 16:08:03
 */
@Table(name = "es_groupbuy_cat")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupbuyCatDO implements Serializable {

    private static final long serialVersionUID = 9105318699704423L;

    /**
     * Categoriesid
     */
    @Id(name = "cat_id")
    @ApiModelProperty(hidden = true)
    private Integer catId;
    /**
     * The parent classid
     */
    @Column(name = "parent_id")
    @ApiModelProperty(name = "parent_id", value = "The parent classid", required = false)
    private Integer parentId;

    /**
     * name
     */
    @Column(name = "cat_name")
    @NotEmpty(message = "Please fill in the category name")
    @ApiModelProperty(name = "cat_name", value = "name", required = false)
    private String catName;

    /**
     * Classification structure directory
     */
    @Column(name = "cat_path")
    @ApiModelProperty(name = "cat_path", value = "Classification structure directory", required = false)
    private String catPath;

    /**
     * sort
     */
    @Column(name = "cat_order")
    @ApiModelProperty(name = "cat_order", value = "sort", required = false)
    private Integer catOrder;

    @PrimaryKeyField
    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatPath() {
        return catPath;
    }

    public void setCatPath(String catPath) {
        this.catPath = catPath;
    }

    public Integer getCatOrder() {
        return catOrder;
    }

    public void setCatOrder(Integer catOrder) {
        this.catOrder = catOrder;
    }

    @Override
    public String toString() {
        return "GroupbuyCatDO{" +
                "catId=" + catId +
                ", parentId=" + parentId +
                ", catName='" + catName + '\'' +
                ", catPath='" + catPath + '\'' +
                ", catOrder=" + catOrder +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GroupbuyCatDO catDO = (GroupbuyCatDO) o;

        return new EqualsBuilder()
                .append(catId, catDO.catId)
                .append(parentId, catDO.parentId)
                .append(catName, catDO.catName)
                .append(catPath, catDO.catPath)
                .append(catOrder, catDO.catOrder)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(catId)
                .append(parentId)
                .append(catName)
                .append(catPath)
                .append(catOrder)
                .toHashCode();
    }
}
