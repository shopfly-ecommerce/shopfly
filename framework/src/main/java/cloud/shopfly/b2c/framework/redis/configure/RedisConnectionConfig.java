/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.redis.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Redis连接配置 对象
 * @author kingapex
 * @version v1.0
 * @since v6.4.1 2017年12月26日
 */
@Configuration
@ConfigurationProperties(prefix="redis")
public class RedisConnectionConfig {

	/**
	 * Redis类型
	 * @see RedisType
	 */
	private  String type;


	/**
	 * 配置类型
	 * @see RedisConfigType
	 */
	@Value("${redis.config.type:''}")
	private String configType;


	/**
	 * 获取配置rest api的url
	 * 要包含端口号
	 */
	@Value("${redis.config.restUrl:''}")
	private  String restUrl;


	/**
	 * 获取配置rest api中的appid参数
	 */
	@Value("${redis.config.restAppid:''}")
	private String  restAppid;


	/**
	 * 客户端版本
	 */
	@Value("${redis.config.restClientVersion:''}")
	private String restClientVersion;


	/**
	 * redis host ,like:127.0.0.1
	 * 当configType 为manual（手工）时有效
	 */
	private String host;

	/**
	 * redis port ,like : 6379
	 * 当configType 为manual（手工）时有效
	 */
	private  Integer port;

	/**
	 * redis 密码
	 * 当configType 为manual（手工）时有效
	 */
	private String password;


	/**
	 * 集群节点配置,like : 127.0.0.1:6379, 127.0.0.1:6380
	 * 当configType 为manual（手工）时有效
	 */
	@Value("${redis.cluster.nodes:''}")
	private String clusterNodes;


	/**
	 * sentine Master 名称
	 * 当configType 为manual（手工）时有效
	 */
	@Value("${redis.sentinel.master:''}")
	private String sentinelMaster;

	/**
	 * sentine节点配置,like : 127.0.0.1:6379, 127.0.0.1:6380
	 * 当configType 为manual（手工）时有效
	 */
	@Value("${redis.sentinel.nodes:''}")
	private String sentinelNodes;


	private  Integer maxIdle;


	private  Integer maxTotal;

	private Long maxWaitMillis;


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
		return "ShopflixRedisConfig{" +
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
