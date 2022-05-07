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

import cloud.shopfly.b2c.core.pagedata.constraint.annotation.ClientMobileType;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * Navigation bar entity
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 17:07:22
 */
@Table(name = "es_site_navigation")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SiteNavigation implements Serializable {

    private static final long serialVersionUID = 964913498755358L;

    /**
     * A primary key
     */
    @Id(name = "navigation_id")
    @ApiModelProperty(hidden = true)
    private Integer navigationId;
    /**
     * The name of the navigation
     */
    @Column(name = "navigation_name")
    @ApiModelProperty(name = "navigation_name", value = "The name of the navigation", required = true)
    @Size(max=15,message = "Navigation name character length verification, cannot exceed15A character")
    @NotEmpty(message = "The navigation name cannot be empty")
    private String navigationName;
    /**
     * Navigation address
     */
    @Column(name = "url")
    @ApiModelProperty(name = "url", value = "Navigation address", required = true)
    @Size(max=255,message = "Navigation address length verification, cannot exceed255A character")
    @NotEmpty(message = "The navigation address cannot be empty")
    private String url;
    /**
     * Client type
     */
    @Column(name = "client_type")
    @ApiModelProperty(name = "client_type", value = "Client typePC/MOBILE", required = true)
    @ClientMobileType
    private String clientType;
    /**
     * Image
     */
    @Column(name = "image")
    @ApiModelProperty(name = "image", value = "Image", required = false)
    private String image;
    /**
     * sort
     */
    @Column(name = "sort")
    @ApiModelProperty(name = "sort", value = "sort",hidden = true)
    private Integer sort;

    @PrimaryKeyField
    public Integer getNavigationId() {
        return navigationId;
    }

    public void setNavigationId(Integer navigationId) {
        this.navigationId = navigationId;
    }

    public String getNavigationName() {
        return navigationName;
    }

    public void setNavigationName(String navigationName) {
        this.navigationName = navigationName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        SiteNavigation that = (SiteNavigation) o;
        if (navigationId != null ? !navigationId.equals(that.navigationId) : that.navigationId != null) {
            return false;
        }
        if (navigationName != null ? !navigationName.equals(that.navigationName) : that.navigationName != null) {
            return false;
        }
        if (url != null ? !url.equals(that.url) : that.url != null) {
            return false;
        }
        if (clientType != null ? !clientType.equals(that.clientType) : that.clientType != null) {
            return false;
        }
        if (image != null ? !image.equals(that.image) : that.image != null) {
            return false;
        }
        return sort != null ? sort.equals(that.sort) : that.sort == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (navigationId != null ? navigationId.hashCode() : 0);
        result = 31 * result + (navigationName != null ? navigationName.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (clientType != null ? clientType.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SiteNavigation{" +
                "navigationId=" + navigationId +
                ", navigationName='" + navigationName + '\'' +
                ", url='" + url + '\'' +
                ", clientType='" + clientType + '\'' +
                ", image='" + image + '\'' +
                ", sort=" + sort +
                '}';
    }


}
