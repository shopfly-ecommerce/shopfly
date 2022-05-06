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
 * 菜单VO
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-06-19 11:13:31
 */
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 9663234630434717L;

    /**
     * 父id
     */
    @NotNull(message = "父菜单id不能为空")
    @Min(message = "父菜单必须为数字且不能为负数", value = 0)
    @ApiModelProperty(name = "parent_id", value = "父菜单id，如果为顶级菜单传0即可")
    private Integer parentId;
    /**
     * 菜单标题
     */
    @NotEmpty(message = "菜单标题不能为空")
    @ApiModelProperty(name = "title", value = "菜单标题")
    private String title;
    /**
     * 菜单url
     */
    @ApiModelProperty(name = "url", value = "菜单链接")
    private String url;
    /**
     * 菜单唯一标识
     */
    @NotEmpty(message = "菜单唯一标识不能为空")
    @ApiModelProperty(name = "identifier", value = "菜单唯一标识")
    private String identifier;
    /**
     * 权限表达式
     */
    @NotEmpty(message = "权限表达式不能为空")
    @ApiModelProperty(name = "auth_regular", value = "权限表达式")
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