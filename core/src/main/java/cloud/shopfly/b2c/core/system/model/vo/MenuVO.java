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
package cloud.shopfly.b2c.core.system.model.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * The menuVO
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-06-19 11:13:31
 */
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 9663234630434717L;

    /**
     * The fatherid
     */
    @NotNull(message = "The parent menuidCant be empty")
    @Min(message = "The parent menu must be numeric and cannot be negative", value = 0)
    @ApiModelProperty(name = "parent_id", value = "The parent menuid, if for the top menu pass0Can be")
    private Integer parentId;
    /**
     * The menu title
     */
    @NotEmpty(message = "Menu titles cannot be empty")
    @ApiModelProperty(name = "title", value = "The menu title")
    private String title;
    /**
     * The menuurl
     */
    @ApiModelProperty(name = "url", value = "Menu links")
    private String url;
    /**
     * Menu unique identifier
     */
    @NotEmpty(message = "The unique identifier of the menu cannot be empty")
    @ApiModelProperty(name = "identifier", value = "Menu unique identifier")
    private String identifier;
    /**
     * Permission expression
     */
    @NotEmpty(message = "Permission expressions cannot be empty")
    @ApiModelProperty(name = "auth_regular", value = "Permission expression")
    private String authRegular;


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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuVO that = (MenuVO) o;

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
        return authRegular != null ? authRegular.equals(that.authRegular) : that.authRegular == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + (authRegular != null ? authRegular.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MenuVO{" +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", identifier='" + identifier + '\'' +
                ", authRegular='" + authRegular + '\'' +
                '}';
    }


}
