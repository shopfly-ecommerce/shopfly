/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.model;

import java.io.Serializable;

import com.enation.app.javashop.deploy.enums.ServiceType;
import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;


/**
 * 数据库实体
 *
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-04-24 13:51:26
 */
@Table(name = "es_database")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Database implements Serializable {

    private static final long serialVersionUID = 9906182984993581L;

    /**
     * 数据库id
     */
    @Id(name = "db_id")
    @ApiModelProperty(hidden = true)
    private Integer dbId;
    /**
     * 数据库类型
     */
    @Column(name = "db_type")
    @NotEmpty(message = "数据库类型不能为空")
    @ApiModelProperty(name = "db_type", value = "数据库类型", required = true)
    private String dbType;
    /**
     * 数据库ip
     */
    @Column(name = "db_ip")
    @NotEmpty(message = "数据库ip不能为空")
    @ApiModelProperty(name = "db_ip", value = "数据库ip", required = true)
    private String dbIp;
    /**
     * 数据库端口号
     */
    @Column(name = "db_port")
    @NotEmpty(message = "数据库端口号不能为空")
    @ApiModelProperty(name = "db_port", value = "数据库端口号", required = true)
    private String dbPort;
    /**
     * 数据库名称
     */
    @Column(name = "db_name")
    @NotEmpty(message = "数据库名称不能为空")
    @ApiModelProperty(name = "db_name", value = "数据库名称", required = true)
    private String dbName;
    /**
     * 数据库用户名
     */
    @Column(name = "db_username")
    @NotEmpty(message = "数据库用户名不能为空")
    @ApiModelProperty(name = "db_username", value = "数据库用户名", required = true)
    private String dbUsername;
    /**
     * 数据库密码
     */
    @Column(name = "db_password")
    @ApiModelProperty(name = "db_password", value = "数据库密码", required = true)
    private String dbPassword;
    /**
     * 业务类型
     */
    @Column(name = "service_type")
    private String serviceType;

    /**
     * 部署id
     */
    @Column(name = "deploy_id")
    private Integer deployId;

    /**
     * 业务类型名称，非数据库字段
     */
    private String serviceTypeName;

    @PrimaryKeyField
    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbIp() {
        return dbIp;
    }

    public void setDbIp(String dbIp) {
        this.dbIp = dbIp;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getDeployId() {
        return deployId;
    }

    public void setDeployId(Integer deployId) {
        this.deployId = deployId;
    }

    public String getServiceTypeName() {

        try {
            ServiceType type = ServiceType.valueOf(serviceType);

            if (type == null) {
                serviceTypeName = "未知";
            } else {
                serviceTypeName = type.getTypeName();
            }
            return serviceTypeName;
        } catch (NullPointerException e) {
            return "未知";
        }

    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Database that = (Database) o;
        if (dbId != null ? !dbId.equals(that.dbId) : that.dbId != null) {
            return false;
        }
        if (dbType != null ? !dbType.equals(that.dbType) : that.dbType != null) {
            return false;
        }
        if (dbIp != null ? !dbIp.equals(that.dbIp) : that.dbIp != null) {
            return false;
        }
        if (dbPort != null ? !dbPort.equals(that.dbPort) : that.dbPort != null) {
            return false;
        }
        if (dbName != null ? !dbName.equals(that.dbName) : that.dbName != null) {
            return false;
        }
        if (dbUsername != null ? !dbUsername.equals(that.dbUsername) : that.dbUsername != null) {
            return false;
        }
        if (dbPassword != null ? !dbPassword.equals(that.dbPassword) : that.dbPassword != null) {
            return false;
        }
        if (serviceType != null ? !serviceType.equals(that.serviceType) : that.serviceType != null) {
            return false;
        }
        if (deployId != null ? !deployId.equals(that.deployId) : that.deployId != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (dbId != null ? dbId.hashCode() : 0);
        result = 31 * result + (dbType != null ? dbType.hashCode() : 0);
        result = 31 * result + (dbIp != null ? dbIp.hashCode() : 0);
        result = 31 * result + (dbPort != null ? dbPort.hashCode() : 0);
        result = 31 * result + (dbName != null ? dbName.hashCode() : 0);
        result = 31 * result + (dbUsername != null ? dbUsername.hashCode() : 0);
        result = 31 * result + (dbPassword != null ? dbPassword.hashCode() : 0);
        result = 31 * result + (serviceType != null ? serviceType.hashCode() : 0);
        result = 31 * result + (deployId != null ? deployId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Database{" +
                "dbId=" + dbId +
                ", dbType='" + dbType + '\'' +
                ", dbIp='" + dbIp + '\'' +
                ", dbPort='" + dbPort + '\'' +
                ", dbName='" + dbName + '\'' +
                ", dbUsername='" + dbUsername + '\'' +
                ", dbPassword='" + dbPassword + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", deployId=" + deployId +
                '}';
    }


}