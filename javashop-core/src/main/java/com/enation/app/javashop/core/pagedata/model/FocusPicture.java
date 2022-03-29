/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.pagedata.model;

import com.enation.app.javashop.core.pagedata.constraint.annotation.ClientAppType;
import com.enation.app.javashop.core.pagedata.constraint.annotation.OperationType;
import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 焦点图实体
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
     * 主键id
     */
    @Id(name = "id")
    @ApiModelProperty(name = "id", value = "主键id", hidden = true)
    private Integer id;
    /**
     * 图片地址
     */
    @Column(name = "pic_url")
    @ApiModelProperty(name = "pic_url", value = "图片地址", required = true)
    @NotEmpty(message = "图片地址不能为空")
    private String picUrl;
    /**
     * 操作类型
     */
    @Column(name = "operation_type")
    @ApiModelProperty(name = "operation_type", value = "操作类型", required = true)
    @OperationType
    private String operationType;
    /**
     * 操作参数
     */
    @Column(name = "operation_param")
    @ApiModelProperty(name = "operation_param", value = "操作参数", required = true)
    private String operationParam;
    /**
     * 操作地址
     */
    @Column(name = "operation_url")
    @ApiModelProperty(name = "operation_url", value = "操作地址", hidden = true)
    private String operationUrl;
    /**
     * 客户端类型 APP/WAP/PC
     */
    @Column(name = "client_type")
    @ApiModelProperty(name = "client_type", value = "客户端类型 APP/WAP/PC", required = true)
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