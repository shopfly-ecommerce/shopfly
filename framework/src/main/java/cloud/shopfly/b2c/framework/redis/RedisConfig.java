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
package cloud.shopfly.b2c.framework.redis;

import cloud.shopfly.b2c.framework.redis.builder.RedisTemplateBuilder;
import cloud.shopfly.b2c.framework.redis.builder.StringRedisTemplateBuilder;
import cloud.shopfly.b2c.framework.redis.configure.RedisConnectionConfig;
import cloud.shopfly.b2c.framework.redis.configure.RedisType;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;


import java.util.Set;

/**
 * Redisconfiguration
 * @author kingapex
 * 2017years8month2The morning of11:52:50
 *
 * Modifying file Location,increaseRedissonClinetThe configuration of the
 * @version 2.0
 * @since 6.4
 */
@Configuration
public class RedisConfig {

	@Autowired
	private RedisTemplateBuilder redisTemplateBuilder;

	@Autowired
	private StringRedisTemplateBuilder stringRedisTemplateBuilder;

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {

		RedisTemplate<String,Object> redisTemplate = null;

		redisTemplate = redisTemplateBuilder.build();

		return redisTemplate;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate() {

		StringRedisTemplate redisTemplate = null;

		redisTemplate = stringRedisTemplateBuilder.build();

		return redisTemplate;
	}

	@Bean
	LettuceConnectionFactory lettuceConnectionFactory() {
		return (LettuceConnectionFactory) redisTemplate().getConnectionFactory();
	}


	@Bean
	public RedissonClient redissonClient(LettuceConnectionFactory lettuceConnectionFactory, RedisConnectionConfig config) {
 		Config rconfig = null;
		String type  = config.getType();


		// Stand-alone mode
		if( RedisType.standalone.name().equals(type) ){
			rconfig = new Config();
			RedisStandaloneConfiguration standaloneConfiguration = lettuceConnectionFactory.getStandaloneConfiguration();
			String host  = standaloneConfiguration.getHostName();
			int port = standaloneConfiguration.getPort();
			SingleServerConfig singleServerConfig =  rconfig.useSingleServer().setAddress("redis://" + host+":" + port);
			if(standaloneConfiguration.getPassword().isPresent()){
				String password  = new String(standaloneConfiguration.getPassword().get() );
				singleServerConfig.setPassword(password);
			}

		}


		// The guard mode
		if( RedisType.sentinel.name().equals(type) ){
			rconfig = new Config();
			RedisSentinelConfiguration sentinelConfiguration =  lettuceConnectionFactory.getSentinelConfiguration();
			String masterName  =  sentinelConfiguration.getMaster().getName();
			Set<RedisNode> nodeSet =sentinelConfiguration.getSentinels();

			SentinelServersConfig sentinelServersConfig = rconfig.useSentinelServers().setMasterName(masterName);

			for (RedisNode node : nodeSet){
				sentinelServersConfig.addSentinelAddress("redis://"+node.asString());
			}
		}

		// Cluster pattern
		if( RedisType.cluster.name().equals(type) ){
			rconfig = new Config();
			RedisClusterConfiguration clusterConfiguration =  lettuceConnectionFactory.getClusterConfiguration();
			Set<RedisNode> nodeSet = clusterConfiguration.getClusterNodes();
			ClusterServersConfig clusterServersConfig =  rconfig.useClusterServers();
			for (RedisNode node : nodeSet){
				clusterServersConfig.addNodeAddress("redis://"+node.asString());
			}
		}

		if(  rconfig == null){
			throw  new RuntimeException("The wrongredis Type, please checkredis.typeparameter");
		}
		RedissonClient redisson = Redisson.create(rconfig);
		return  redisson;
	}


}
