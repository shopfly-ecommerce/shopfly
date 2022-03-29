/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.model;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * elasticsearch实体
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2019-02-13 10:39:25
 */
@Table(name="es_elasticsearch")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Elasticsearch   {
			

    /**id*/
    @Id(name = "id")
    @ApiModelProperty(hidden=true)
    private Integer id;

    /**index_name*/
    @Column(name = "index_name")
    @ApiModelProperty(name="index_name",value="index_name",required=false)
    private String indexName;

    /**cluster_name*/
    @Column(name = "cluster_name")
    @ApiModelProperty(name="cluster_name",value="cluster_name",required=false)
    private String clusterName;
    /**cluster_nodes*/
    @Column(name = "cluster_nodes")
    @ApiModelProperty(name="cluster_nodes",value="cluster_nodes",required=false)
    private String clusterNodes;
    /**deploy_id*/
    @Column(name = "deploy_id")
    @ApiModelProperty(name="deploy_id",value="deploy_id",required=false)
    private Integer deployId;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getClusterName() {
        return clusterName;
    }
    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterNodes() {
        return clusterNodes;
    }
    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public Integer getDeployId() {
        return deployId;
    }
    public void setDeployId(Integer deployId) {
        this.deployId = deployId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Elasticsearch that = (Elasticsearch) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(getIndexName(), that.getIndexName())
                .append(getClusterName(), that.getClusterName())
                .append(getClusterNodes(), that.getClusterNodes())
                .append(getDeployId(), that.getDeployId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getIndexName())
                .append(getClusterName())
                .append(getClusterNodes())
                .append(getDeployId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Elasticsearch{" +
                "id=" + id +
                ", indexName='" + indexName + '\'' +
                ", clusterName='" + clusterName + '\'' +
                ", clusterNodes='" + clusterNodes + '\'' +
                ", deployId=" + deployId +
                '}';
    }


}