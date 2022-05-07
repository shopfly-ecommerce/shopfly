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
package cloud.shopfly.b2c.core.system.model.dos;

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
 * Menu table entities
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-06-20 09:29:19
 */
@Table(name = "es_menu")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Menu implements Serializable {
    private static final long serialVersionUID = 3033586331756935L;

    /**
     * The menuid
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * The fatherid
     */
    @Column(name = "parent_id")
    @ApiModelProperty(name = "parent_id", value = "The fatherid", required = false)
    private Integer parentId;
    /**
     * The menu title
     */
    @Column(name = "title")
    @ApiModelProperty(name = "title", value = "The menu title", required = false)
    private String title;
    /**
     * The menuurl
     */
    @Column(name = "url")
    @ApiModelProperty(name = "url", value = "The menuurl", required = false)
    private String url;
    /**
     * Menu unique identifier
     */
    @Column(name = "identifier")
    @ApiModelProperty(name = "identifier", value = "Menu unique identifier", required = false)
    private String identifier;
    /**
     * Permission expression
     */
    @Column(name = "auth_regular")
    @ApiModelProperty(name = "auth_regular", value = "Permission expression", required = false)
    private String authRegular;
    /**
     * Remove the tag
     */
    @Column(name = "delete_flag")
    @ApiModelProperty(name = "delete_flag", value = "Remove the tag", required = false)
    private Integer deleteFlag;
    /**
     * Menu level identification
     */
    @Column(name = "path")
    @ApiModelProperty(name = "path", value = "Menu level identification", required = false)
    private String path;
    /**
     * Menu level
     */
    @Column(name = "grade")
    @ApiModelProperty(name = "grade", value = "Menu level", required = false)
    private Integer grade;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAuthRegular() {
        return authRegular;
    }

    public void setAuthRegular(String authRegular) {
        this.authRegular = authRegular;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu that = (Menu) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) {
            return false;
        }
        if (title != null ? !title.equals(that.title) : that.title != null) {
            return false;
        }
        if (url != null ? !url.equals(that.url) : that.url != null) {
            return false;
        }
        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) {
            return false;
        }
        if (authRegular != null ? !authRegular.equals(that.authRegular) : that.authRegular != null) {
            return false;
        }
        if (deleteFlag != null ? !deleteFlag.equals(that.deleteFlag) : that.deleteFlag != null) {
            return false;
        }
        if (path != null ? !path.equals(that.path) : that.path != null) {
            return false;
        }
        return grade != null ? grade.equals(that.grade) : that.grade == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + (authRegular != null ? authRegular.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", identifier='" + identifier + '\'' +
                ", authRegular='" + authRegular + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", path='" + path + '\'' +
                ", grade=" + grade +
                '}';
    }

}
