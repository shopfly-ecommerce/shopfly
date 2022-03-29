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


/**
 * redis实体
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-05-07 20:11:08
 */
@Table(name="es_redis")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Redis implements Serializable {
			
    private static final long serialVersionUID = 5392729032235870L;
    
    /**redis_id*/
    @Id(name = "redis_id")
    @ApiModelProperty(hidden=true)
    private Integer redisId;
    /**类型*/
    @Column(name = "redis_type")
    @NotEmpty(message="类型不能为空")
    @ApiModelProperty(name="redis_type",value="类型",required=true)
    private String redisType;
    /**配置类型*/
    @Column(name = "config_type")
    @NotEmpty(message="配置类型不能为空")
    @ApiModelProperty(name="config_type",value="配置类型",required=false)
    private String configType;
    /**standalone_host*/
    @Column(name = "standalone_host")
    @NotEmpty(message="standalone_host不能为空")
    @ApiModelProperty(name="standalone_host",value="standalone_host",required=false)
    private String standaloneHost;
    /**standalone_port*/
    @Column(name = "standalone_port")
    @ApiModelProperty(name="standalone_port",value="standalone_port",required=false)
    private String standalonePort;
    /**standalone_password*/
    @Column(name = "standalone_password")
    @ApiModelProperty(name="standalone_password",value="standalone_password",required=false)
    private String standalonePassword;
    /**cluster_nodes*/
    @Column(name = "cluster_nodes")
    @ApiModelProperty(name="cluster_nodes",value="cluster_nodes",required=false)
    private String clusterNodes;
    /**sentinel_master*/
    @Column(name = "sentinel_master")
    @ApiModelProperty(name="sentinel_master",value="sentinel_master",required=false)
    private String sentinelMaster;
    /**sentinel_nodes*/
    @Column(name = "sentinel_nodes")
    @ApiModelProperty(name="sentinel_nodes",value="sentinel_nodes",required=false)
    private String sentinelNodes;
    /**部署id*/
    @Column(name = "deploy_id")
    @ApiModelProperty(name="deploy_id",value="部署id",required=false)
    private Integer deployId;
    /**rest_appid*/
    @Column(name = "rest_appid")
    @ApiModelProperty(name="rest_appid",value="rest_appid",required=false)
    private String restAppid;
    /**rest_url*/
    @Column(name = "rest_url")
    @ApiModelProperty(name="rest_url",value="rest_url",required=false)
    private String restUrl;

    @PrimaryKeyField
    public Integer getRedisId() {
        return redisId;
    }
    public void setRedisId(Integer redisId) {
        this.redisId = redisId;
    }

    public String getRedisType() {
        return redisType;
    }
    public void setRedisType(String redisType) {
        this.redisType = redisType;
    }

    public String getConfigType() {
        return configType;
    }
    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getStandaloneHost() {
        return standaloneHost;
    }
    public void setStandaloneHost(String standaloneHost) {
        this.standaloneHost = standaloneHost;
    }

    public String getStandalonePort() {
        return standalonePort;
    }
    public void setStandalonePort(String standalonePort) {
        this.standalonePort = standalonePort;
    }

    public String getStandalonePassword() {
        return standalonePassword;
    }
    public void setStandalonePassword(String standalonePassword) {
        this.standalonePassword = standalonePassword;
    }

    public String getClusterNodes() {
        return clusterNodes;
    }
    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public String getSentinelMaster() {
        return sentinelMaster;
    }
    public void setSentinelMaster(String sentinelMaster) {
        this.sentinelMaster = sentinelMaster;
    }

    public String getSentinelNodes() {
        return sentinelNodes;
    }
    public void setSentinelNodes(String sentinelNodes) {
        this.sentinelNodes = sentinelNodes;
    }

    public Integer getDeployId() {
        return deployId;
    }
    public void setDeployId(Integer deployId) {
        this.deployId = deployId;
    }

    public String getRestAppid() {
        return restAppid;
    }
    public void setRestAppid(String restAppid) {
        this.restAppid = restAppid;
    }

    public String getRestUrl() {
        return restUrl;
    }
    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }


	@Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Redis that = (Redis) o;
        if (redisId != null ? !redisId.equals(that.redisId) : that.redisId != null) {return false;}
        if (redisType != null ? !redisType.equals(that.redisType) : that.redisType != null) {return false;}
        if (configType != null ? !configType.equals(that.configType) : that.configType != null) {return false;}
        if (standaloneHost != null ? !standaloneHost.equals(that.standaloneHost) : that.standaloneHost != null) {return false;}
        if (standalonePort != null ? !standalonePort.equals(that.standalonePort) : that.standalonePort != null) {return false;}
        if (standalonePassword != null ? !standalonePassword.equals(that.standalonePassword) : that.standalonePassword != null) {return false;}
        if (clusterNodes != null ? !clusterNodes.equals(that.clusterNodes) : that.clusterNodes != null) {return false;}
        if (sentinelMaster != null ? !sentinelMaster.equals(that.sentinelMaster) : that.sentinelMaster != null) {return false;}
        if (sentinelNodes != null ? !sentinelNodes.equals(that.sentinelNodes) : that.sentinelNodes != null) {return false;}
        if (deployId != null ? !deployId.equals(that.deployId) : that.deployId != null) {return false;}
        if (restAppid != null ? !restAppid.equals(that.restAppid) : that.restAppid != null) {return false;}
        if (restUrl != null ? !restUrl.equals(that.restUrl) : that.restUrl != null) {return false;}
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (redisId != null ? redisId.hashCode() : 0);
        result = 31 * result + (redisType != null ? redisType.hashCode() : 0);
        result = 31 * result + (configType != null ? configType.hashCode() : 0);
        result = 31 * result + (standaloneHost != null ? standaloneHost.hashCode() : 0);
        result = 31 * result + (standalonePort != null ? standalonePort.hashCode() : 0);
        result = 31 * result + (standalonePassword != null ? standalonePassword.hashCode() : 0);
        result = 31 * result + (clusterNodes != null ? clusterNodes.hashCode() : 0);
        result = 31 * result + (sentinelMaster != null ? sentinelMaster.hashCode() : 0);
        result = 31 * result + (sentinelNodes != null ? sentinelNodes.hashCode() : 0);
        result = 31 * result + (deployId != null ? deployId.hashCode() : 0);
        result = 31 * result + (restAppid != null ? restAppid.hashCode() : 0);
        result = 31 * result + (restUrl != null ? restUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Redis{" +
                "redisId=" + redisId +
                ", redisType='" + redisType + '\'' +
                ", configType='" + configType + '\'' +
                ", standaloneHost='" + standaloneHost + '\'' +
                ", standalonePort='" + standalonePort + '\'' +
                ", standalonePassword='" + standalonePassword + '\'' +
                ", clusterNodes='" + clusterNodes + '\'' +
                ", sentinelMaster='" + sentinelMaster + '\'' +
                ", sentinelNodes='" + sentinelNodes + '\'' +
                ", deployId=" + deployId +
                ", restAppid='" + restAppid + '\'' +
                ", restUrl='" + restUrl + '\'' +
                '}';
    }

	
}