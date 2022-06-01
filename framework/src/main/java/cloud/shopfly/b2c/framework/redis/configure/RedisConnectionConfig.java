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
package cloud.shopfly.b2c.framework.redis.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * RedisConnection configuration Object
 * @author kingapex
 * @version v1.0
 * @since v6.4.1 2017years12month26day
 */
@Configuration
@ConfigurationProperties(prefix="redis")
public class RedisConnectionConfig {

	/**
	 * Redistype
	 * @see RedisType
	 */
	private  String type;


	/**
	 * Configuration type
	 * @see RedisConfigType
	 */
	@Value("${redis.config.type:''}")
	private String configType;


	/**
	 * Access to the configurationrest apitheurl
	 * Include the port number
	 */
	@Value("${redis.config.restUrl:''}")
	private  String restUrl;


	/**
	 * Access to the configurationrest apiIn theappidparameter
	 */
	@Value("${redis.config.restAppid:''}")
	private String  restAppid;


	/**
	 * Client Version
	 */
	@Value("${redis.config.restClientVersion:''}")
	private String restClientVersion;


	/**
	 * redis host ,like:127.0.0.1
	 * whenconfigType formanual（manual）Effective when
	 */
	private String host;

	/**
	 * redis port ,like : 6379
	 * whenconfigType formanual（manual）Effective when
	 */
	private  Integer port;

	/**
	 * redis Password
	 * whenconfigType formanual（manual）Effective when
	 */
	private String password;


	/**
	 * Cluster Node Configuration,like : 127.0.0.1:6379, 127.0.0.1:6380
	 * whenconfigType formanual（manual）Effective when
	 */
	@Value("${redis.cluster.nodes:''}")
	private String clusterNodes;


	/**
	 * sentine Master The name of the
	 * whenconfigType formanual（manual）Effective when
	 */
	@Value("${redis.sentinel.master:''}")
	private String sentinelMaster;

	/**
	 * sentineNode configuration,like : 127.0.0.1:6379, 127.0.0.1:6380
	 * whenconfigType formanual（manual）Effective when
	 */
	@Value("${redis.sentinel.nodes:''}")
	private String sentinelNodes;

	@Value("${redis.database:0}")
	private int database;


	private  Integer maxIdle;


	private  Integer maxTotal;

	private Long maxWaitMillis;

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}
	public Integer getMaxTotal() {
		return maxTotal;
	}
	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}
	public Long getMaxWaitMillis() {
		return maxWaitMillis;
	}
	public void setMaxWaitMillis(Long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getRestUrl() {
		return restUrl;
	}

	public void setRestUrl(String restUrl) {
		this.restUrl = restUrl;
	}

	public String getRestAppid() {
		return restAppid;
	}

	public void setRestAppid(String restAppid) {
		this.restAppid = restAppid;
	}

	public String getRestClientVersion() {
		return restClientVersion;
	}

	public void setRestClientVersion(String restClientVersion) {
		this.restClientVersion = restClientVersion;
	}

	public String getType() {
		return type;
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

	@Override
	public String toString() {
		return "shopflyRedisConfig{" +
				"type='" + type + '\'' +
				", configType='" + configType + '\'' +
				", restUrl='" + restUrl + '\'' +
				", restAppid='" + restAppid + '\'' +
				", restClientVersion='" + restClientVersion + '\'' +
				", host='" + host + '\'' +
				", port=" + port +
				", password='" + password + '\'' +
				", clusterNodes='" + clusterNodes + '\'' +
				", sentinelMaster='" + sentinelMaster + '\'' +
				", sentinelNodes='" + sentinelNodes + '\'' +
				", maxIdle=" + maxIdle +
				", maxTotal=" + maxTotal +
				", maxWaitMillis=" + maxWaitMillis +
				'}';
	}
}
