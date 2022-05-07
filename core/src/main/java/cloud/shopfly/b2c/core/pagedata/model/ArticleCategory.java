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
package cloud.shopfly.b2c.core.pagedata.model;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * Article classification entity
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-11 15:01:32
 */
@Table(name = "es_article_category")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ArticleCategory implements Serializable {

    private static final long serialVersionUID = 5257682283507488L;

    /**
     * A primary key
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * name
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "name", required = true)
    @NotEmpty(message = "The category name cannot be empty")
    private String name;
    /**
     * The parent categoryid
     */
    @Column(name = "parent_id")
    @ApiModelProperty(name = "parent_id", value = "The parent categoryid", required = true)
    private Integer parentId;
    /**
     * Father and son path0|10|
     */
    @Column(name = "path")
    @ApiModelProperty(name = "path", value = "Father and son path0|10|", hidden = true)
    private String path;
    /**
     * Whether to delete1allow0不allow
     */
    @Column(name = "allow_delete")
    @ApiModelProperty(name = "allow_delete", value = "Whether to delete1allow0不allow", hidden = true)
    private Integer allowDelete;
    /**
     * Category type, enumeration value
     */
    @Column(name = "type")
    @ApiModelProperty(name = "type", value = "Category type, enumeration value", hidden = true)
    private String type;
    /**
     * Sort, positive order123
     */
    @Column(name = "sort")
    @ApiModelProperty(name = "sort", value = "Sort, positive order123", required = false)
    private Integer sort;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getAllowDelete() {
        return allowDelete;
    }

    public void setAllowDelete(Integer allowDelete) {
        this.allowDelete = allowDelete;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleCategory that = (ArticleCategory) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) {
            return false;
        }
        if (path != null ? !path.equals(that.path) : that.path != null) {
            return false;
        }
        if (allowDelete != null ? !allowDelete.equals(that.allowDelete) : that.allowDelete != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        return sort != null ? sort.equals(that.sort) : that.sort == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (allowDelete != null ? allowDelete.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ArticleCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", path='" + path + '\'' +
                ", allowDelete=" + allowDelete +
                ", type='" + type + '\'' +
                ", sort=" + sort +
                '}';
    }


}
