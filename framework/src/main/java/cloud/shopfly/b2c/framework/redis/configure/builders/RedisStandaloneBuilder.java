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
package cloud.shopfly.b2c.framework.redis.configure.builders;

import cloud.shopfly.b2c.framework.redis.configure.IRedisBuilder;
import cloud.shopfly.b2c.framework.redis.configure.RedisConfigType;
import cloud.shopfly.b2c.framework.redis.configure.RedisConnectionConfig;
import cloud.shopfly.b2c.framework.redis.configure.RedisType;
import cloud.shopfly.b2c.framework.util.HttpUtils;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Standalone schema builder
 * v2.0: Added the manual configuration mode,by kingapex 2017-12-26
 * @author fk,kingapex
 * @version v2.0
 * @since v6.4
 * 2017years10month27On the afternoon2:37:04
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
@Service
public class RedisStandaloneBuilder  implements IRedisBuilder {
	

	private static Logger logger = LoggerFactory.getLogger(RedisStandaloneBuilder.class);

    private RedisConnectionConfig config;


    @Override
    public LettuceConnectionFactory buildConnectionFactory( RedisConnectionConfig config) {
        this.config = config;
        RedisStandaloneConfiguration  configuration = buildStandaloneConfig();

        return new LettuceConnectionFactory(configuration);
    }




    /**
     * buildredis clusterThe configuration of the
     * @return redis clusterThe configuration of the
     */
    public RedisStandaloneConfiguration buildStandaloneConfig(){


        // Cache Cloud REST API configuration mode
        if(RedisConfigType.rest.name().equals(config.getConfigType() )){
            RedisStandaloneConfiguration configuration = createRestStandaloneConfig();
            return  configuration;
        }

        // Manual configuration
        if(RedisConfigType.manual.name().equals(config.getConfigType() )){
            RedisStandaloneConfiguration  configuration = createManualStandaloneConfig();
            return  configuration;
        }

        throw  new RuntimeException("redis Configuration error：The wrongredis.config.type, allowing onlycom.enation.eop.sdk.config.redis.configure.RedisConfigTypeValues defined in");


    }




    /**
     * Build manualredis Standalone configuration
     * @return
     */
    private RedisStandaloneConfiguration createManualStandaloneConfig(){

        String host = config.getHost();
        if(StringUtil.isEmpty(host)){
            throw new RuntimeException("redis Configuration error：redis.hostIs empty");
        }


        int port = config.getPort();
        String password  = config.getPassword();

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration( host,port);

        if(StringUtil.notEmpty(password)) {
            configuration.setPassword(RedisPassword.of(password));
        }
        return configuration;
    }





    /**
     * buildrestThe way ofStandalone configuration
     * @return
     */
    private RedisStandaloneConfiguration createRestStandaloneConfig(){

        String redisClusterSuffix= "/cache/client/redis/standalone/%s.json?clientVersion=";
        String redisClusterUrl = config.getRestUrl() + redisClusterSuffix+ config.getRestClientVersion();

        String url = String.format(redisClusterUrl, String.valueOf(config.getRestAppid()));
        String response = HttpUtils.doGet(url);

        String  appId = config.getRestAppid();
        /**
         * The heartbeat returned a null request；
         */

        if (response == null || response.isEmpty()) {
            logger.warn("cannot get response from server, appId={}. continue...", appId);
        }

        /**
         * Invalid request returned by heartbeat；
         */
        ObjectMapper mapper = new ObjectMapper();
        JsonNode responseJson = null;
        try {
            responseJson = mapper.readTree(response);
        } catch (Exception e) {
            logger.error("read json from response error, appId: {}.", appId, e);
        }

        if (responseJson == null) {
            logger.warn("invalid response, appId: {}. continue...", appId);
        }

        /**
         * From the heartbeatHostAndPort,JedisPoolThe instance；
         */
        String instance = responseJson.get("standalone").asText();
        String[] instanceArr = instance.split(":");
        if (instanceArr.length != 2) {
            logger.warn("instance info is invalid, instance: {}, appId: {}, continue...", instance, appId);
        }

        String host  = instanceArr[0];
        int port  = Integer.valueOf(instanceArr[1]);
        RedisStandaloneConfiguration clusterConfiguration = new RedisStandaloneConfiguration( host,port);

        return  clusterConfiguration;
    }



	@Override
	public RedisType getType() {
		
		return RedisType.standalone;
	}

}
