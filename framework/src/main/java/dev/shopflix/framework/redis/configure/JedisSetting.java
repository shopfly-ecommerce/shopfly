/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.redis.configure;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 初始化redi使用
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018年3月23日 上午10:26:41
 */
public class JedisSetting {
	
	private static JedisPoolConfig poolConfig;
	
	private static  RedisConnectionConfig connectionConfig;

	public static void loadPoolConfig(RedisConnectionConfig config) {
		
		poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(config.getMaxTotal()==null?(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 3):config.getMaxTotal());
		poolConfig.setMaxIdle(config.getMaxIdle()==null?(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 2):config.getMaxIdle());
		poolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE);
		poolConfig.setMaxWaitMillis(config.getMaxWaitMillis()==null?1000L:config.getMaxWaitMillis());
		poolConfig.setJmxNamePrefix("javashop-redis-pool");
		poolConfig.setJmxEnabled(true);
		poolConfig.setTestOnBorrow(true);
		
		connectionConfig = config;
		
	}
	
	public static JedisPoolConfig getPoolConfig(){
		
		return poolConfig;
	}

	public static RedisConnectionConfig getConnectionConfig() {
		
		return connectionConfig;
	}
	
}
