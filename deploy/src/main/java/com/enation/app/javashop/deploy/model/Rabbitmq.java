/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.model;

import java.io.Serializable;

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


/**
 * rabbitmq实体
 *
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-05-07 17:33:11
 */
@Table(name = "es_rabbitmq")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Rabbitmq implements Serializable {

    private static final long serialVersionUID = 6359508582527375L;

    /**
     * id
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * host
     */
    @Column(name = "host")
    @NotEmpty(message = "host不能为空")
    @ApiModelProperty(name = "host", value = "host", required = true)
    private String host;
    /**
     * port
     */
    @Column(name = "port")
    @NotEmpty(message = "port不能为空")
    @ApiModelProperty(name = "port", value = "port", required = true)
    private String port;
    /**
     * password
     */
    @Column(name = "password")
    @NotEmpty(message = "password不能为空")
    @ApiModelProperty(name = "password", value = "password", required = true)
    private String password;
    /**
     * deploy_id
     */
    @Column(name = "deploy_id")
    @ApiModelProperty(name = "deploy_id", value = "deploy_id", required = true)
    private Integer deployId;
    /**
     * username
     */
    @Column(name = "username")
    @ApiModelProperty(name = "username", value = "username", required = false)
    private String username;
    /**
     * username
     */
    @Column(name = "virtual_host")
    @ApiModelProperty(name = "virtual_host", value = "virtual_host", required = false)
    private String virtualHost;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDeployId() {
        return deployId;
    }

    public void setDeployId(Integer deployId) {
        this.deployId = deployId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rabbitmq that = (Rabbitmq) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (host != null ? !host.equals(that.host) : that.host != null) {
            return false;
        }
        if (port != null ? !port.equals(that.port) : that.port != null) {
            return false;
        }
        if (password != null ? !password.equals(that.password) : that.password != null) {
            return false;
        }
        if (deployId != null ? !deployId.equals(that.deployId) : that.deployId != null) {
            return false;
        }
        if (username != null ? !username.equals(that.username) : that.username != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (deployId != null ? deployId.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Rabbitmq{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", password='" + password + '\'' +
                ", deployId=" + deployId +
                ", username='" + username + '\'' +
                '}';
    }


}