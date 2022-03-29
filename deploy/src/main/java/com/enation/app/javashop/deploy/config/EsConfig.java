/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * elasticsearch 设置，为了配合devops的设置
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2021/4/16
 */
@Component
@ConfigurationProperties(prefix="es")
public class EsConfig {

    private String indexName;
    private String clusterName;
    private String clusterNodes;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
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

    @Override
    public String toString() {
        return "EsConfig{" +
                "indexName='" + indexName + '\'' +
                ", clusterName='" + clusterName + '\'' +
                ", clusterNodes='" + clusterNodes + '\'' +
                '}';
    }
}
