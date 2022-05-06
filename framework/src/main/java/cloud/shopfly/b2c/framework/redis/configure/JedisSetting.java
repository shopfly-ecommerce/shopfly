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
		poolConfig.setJmxNamePrefix("shopfly-redis-pool");
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
