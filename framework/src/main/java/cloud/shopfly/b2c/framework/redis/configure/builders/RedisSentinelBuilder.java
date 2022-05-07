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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Sentinel builder
 * v2.0: Added the manual configuration mode,by kingapex 2017-12-26
 * @author fk,kingapex
 * @version 2.0
 * @since v6.4
 * 2017years10month27On the afternoon2:25:52
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
@Service
public class RedisSentinelBuilder  implements IRedisBuilder {


	private static Logger logger = LoggerFactory.getLogger(RedisSentinelBuilder.class);


    private RedisConnectionConfig config;

	
	@Override
	public LettuceConnectionFactory buildConnectionFactory( RedisConnectionConfig config) {
        this.config= config;
        RedisSentinelConfiguration sentinelConfiguration = this.buildSentinelConfig();

		return new LettuceConnectionFactory(sentinelConfiguration);
	}


    /**
     * buildredis clusterThe configuration of the
     * @return redis clusterThe configuration of the
     */
    public RedisSentinelConfiguration buildSentinelConfig(){


        // Cache Cloud REST API configuration mode
        if(RedisConfigType.rest.name().equals(config.getConfigType() )){
            RedisSentinelConfiguration configuration = createRestSentinelConfig();
            return  configuration;
        }

        // Manual configuration
        if(RedisConfigType.manual.name().equals(config.getConfigType() )){
            RedisSentinelConfiguration  configuration = createManualSentinelConfig();
            return  configuration;
        }

        throw  new RuntimeException("redis Configuration error：The wrongredis.config.type, allowing onlycom.enation.eop.sdk.config.redis.configure.RedisConfigTypeValues defined in");


    }




    /**
     * Build manualredis The sentry configuration
     * @return
     */
    private RedisSentinelConfiguration createManualSentinelConfig(){

        String masterName  = config.getSentinelMaster();

        if(StringUtil.isEmpty(masterName)){
            throw  new RuntimeException("redis Configuration error： sentinel.masterCant be empty");
        }


        String nodes = config.getSentinelNodes();

        if(StringUtil.isEmpty(nodes)){
            throw  new RuntimeException("redis Configuration error： sentinel.nodesCant be empty");
        }


        Set<String> sentinelSet = new HashSet<String>();
        for (String sentinelStr : nodes.split(",")) {
            String[] sentinelArr = sentinelStr.split(":");
            if (sentinelArr.length == 2) {
                sentinelSet.add(sentinelStr);
            }
        }

        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration( masterName,sentinelSet);

        // If a password is specified, set the password
        String password =config.getPassword();

        if(StringUtil.notEmpty(password)) {
            configuration.setPassword(RedisPassword.of(password));
        }

        return configuration;
    }





    /**
     * buildrestMode sentry configuration
     * @return
     */
    private RedisSentinelConfiguration createRestSentinelConfig(){

        String redisClusterSuffix= "/cache/client/redis/sentinel/%s.json?clientVersion=";
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
         * httpThe result returned by the request is invalid；
         */
        ObjectMapper mapper = new ObjectMapper();
        JsonNode heartbeatInfo = null;
        try {
            heartbeatInfo = mapper.readTree(response);
        } catch (Exception e) {
//            logger.error("heartbeat error, appId: {}. continue...", appId, e);
        }
        if (heartbeatInfo == null) {
            logger.error("get sentinel info for appId: {} error. continue...", appId);
        }

        /** Check the client version**/
        if (heartbeatInfo.get("status").intValue() == ClientStatusEnum.ERROR.getStatus()) {
            throw new IllegalStateException(heartbeatInfo.get("message").textValue());
        } else if (heartbeatInfo.get("status").intValue() == ClientStatusEnum.WARN.getStatus()) {
            logger.warn(heartbeatInfo.get("message").textValue());
        } else {
            logger.info(heartbeatInfo.get("message").textValue());
        }

        /**
         * Valid request：Take out themasterNameandsentinels, and create theJedisSentinelPoolAn instance of the；
         */
        String masterName = heartbeatInfo.get("masterName").asText();
        String sentinels = heartbeatInfo.get("sentinels").asText();
        Set<String> sentinelSet = new HashSet<String>();
        for (String sentinelStr : sentinels.split(" ")) {
            String[] sentinelArr = sentinelStr.split(":");
            if (sentinelArr.length == 2) {
                sentinelSet.add(sentinelStr);
            }
        }
        RedisSentinelConfiguration configuration  = new RedisSentinelConfiguration(masterName, sentinelSet);

        return  configuration;
    }




    @Override
	public RedisType getType() {
		
		return RedisType.sentinel;
	}

}
