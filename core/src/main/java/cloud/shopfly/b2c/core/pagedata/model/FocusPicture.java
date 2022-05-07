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

import cloud.shopfly.b2c.core.pagedata.constraint.annotation.ClientAppType;
import cloud.shopfly.b2c.core.pagedata.constraint.annotation.OperationType;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * Focus map entity
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-21 15:23:23
 */
@Table(name = "es_focus_picture")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FocusPicture implements Serializable {

    private static final long serialVersionUID = 9393604588909990L;

    /**
     * A primary keyid
     */
    @Id(name = "id")
    @ApiModelProperty(name = "id", value = "A primary keyid", hidden = true)
    private Integer id;
    /**
     * Picture address
     */
    @Column(name = "pic_url")
    @ApiModelProperty(name = "pic_url", value = "Picture address", required = true)
    @NotEmpty(message = "The image address cannot be empty")
    private String picUrl;
    /**
     * Operation type
     */
    @Column(name = "operation_type")
    @ApiModelProperty(name = "operation_type", value = "Operation type", required = true)
    @OperationType
    private String operationType;
    /**
     * Operating parameters
     */
    @Column(name = "operation_param")
    @ApiModelProperty(name = "operation_param", value = "Operating parameters", required = true)
    private String operationParam;
    /**
     * The operating address
     */
    @Column(name = "operation_url")
    @ApiModelProperty(name = "operation_url", value = "The operating address", hidden = true)
    private String operationUrl;
    /**
     * Client typeAPP/WAP/PC
     */
    @Column(name = "client_type")
    @ApiModelProperty(name = "client_type", value = "Client typeAPP/WAP/PC", required = true)
    @ClientAppType
    private String clientType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getOperationParam() {
        return operationParam;
    }

    public void setOperationParam(String operationParam) {
        this.operationParam = operationParam;
    }

    public String getOperationUrl() {
        return operationUrl;
    }

    public void setOperationUrl(String operationUrl) {
        this.operationUrl = operationUrl;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FocusPicture that = (FocusPicture) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (picUrl != null ? !picUrl.equals(that.picUrl) : that.picUrl != null) {
            return false;
        }
        if (operationType != null ? !operationType.equals(that.operationType) : that.operationType != null) {
            return false;
        }
        if (operationParam != null ? !operationParam.equals(that.operationParam) : that.operationParam != null) {
            return false;
        }
        if (operationUrl != null ? !operationUrl.equals(that.operationUrl) : that.operationUrl != null) {
            return false;
        }
        return clientType != null ? clientType.equals(that.clientType) : that.clientType == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (picUrl != null ? picUrl.hashCode() : 0);
        result = 31 * result + (operationType != null ? operationType.hashCode() : 0);
        result = 31 * result + (operationParam != null ? operationParam.hashCode() : 0);
        result = 31 * result + (operationUrl != null ? operationUrl.hashCode() : 0);
        result = 31 * result + (clientType != null ? clientType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FocusPicture{" +
                "id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", operationType=" + operationType +
                ", operationParam='" + operationParam + '\'' +
                ", operationUrl='" + operationUrl + '\'' +
                ", clientType='" + clientType + '\'' +
                '}';
    }


}
