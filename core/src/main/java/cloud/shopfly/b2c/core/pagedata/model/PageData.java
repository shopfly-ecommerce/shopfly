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
 * Floor entity
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-21 16:39:22
 */
@Table(name = "es_page")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PageData implements Serializable {

    private static final long serialVersionUID = 3806389481183972L;

    /**
     * A primary keyid
     */
    @Id(name = "page_id")
    @ApiModelProperty(hidden = true)
    private Integer pageId;
    /**
     * Name of floor
     */
    @Column(name = "page_name")
    @ApiModelProperty(name = "page_name", value = "Name of floor", required = true)
    @NotEmpty(message = "The name cannot be empty")
    private String pageName;
    /**
     * Floor data
     */
    @Column(name = "page_data")
    @ApiModelProperty(name = "page_data", value = "Floor data", required = true)
    @NotEmpty(message = "Page data cannot be empty")
    private String pageData;

    @Column(name = "page_type")
    @ApiModelProperty(name = "page_type", value = "Page type", hidden = true)
    private String pageType;

    @Column(name = "client_type")
    @ApiModelProperty(name = "client_type", value = "Client type", hidden = true)
    private String clientType;

    @PrimaryKeyField
    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageData() {
        return pageData;
    }

    public void setPageData(String pageData) {
        this.pageData = pageData;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageData that = (PageData) o;
        if (pageId != null ? !pageId.equals(that.pageId) : that.pageId != null) {
            return false;
        }
        if (pageName != null ? !pageName.equals(that.pageName) : that.pageName != null) {
            return false;
        }
        if (pageData != null ? !pageData.equals(that.pageData) : that.pageData != null) {
            return false;
        }
        if (pageType != null ? !pageType.equals(that.pageType) : that.pageType != null) {
            return false;
        }
        return clientType != null ? clientType.equals(that.clientType) : that.clientType == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (pageId != null ? pageId.hashCode() : 0);
        result = 31 * result + (pageName != null ? pageName.hashCode() : 0);
        result = 31 * result + (pageData != null ? pageData.hashCode() : 0);
        result = 31 * result + (pageType != null ? pageType.hashCode() : 0);
        result = 31 * result + (clientType != null ? clientType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "pageId=" + pageId +
                ", pageName='" + pageName + '\'' +
                ", pageData='" + pageData + '\'' +
                ", pageType='" + pageType + '\'' +
                ", clientType='" + clientType + '\'' +
                '}';
    }


}
