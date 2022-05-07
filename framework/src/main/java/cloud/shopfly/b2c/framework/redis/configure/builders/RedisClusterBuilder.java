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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Cluster builder
 * v2.0: Added the manual configuration mode,by kingapex 2017-12-26
 * @author fk,kingapex
 * @version v2.0
 * @since v6.4
 * 2017years10month27On the afternoon2:43:03
 */
@Service
public class RedisClusterBuilder implements IRedisBuilder {
	

	private static Logger logger = LoggerFactory.getLogger(RedisClusterBuilder.class);



    private RedisConnectionConfig config;


    @Override
	public LettuceConnectionFactory buildConnectionFactory(RedisConnectionConfig config) {
        this.config = config;
        RedisClusterConfiguration  clusterConfiguration = buildClusterConfig();

        return new LettuceConnectionFactory(clusterConfiguration);
	}


    /**
     * buildredis clusterThe configuration of the
     * @return redis clusterThe configuration of the
     */
    public    RedisClusterConfiguration buildClusterConfig(){

        // Cache Cloud REST API configuration mode
        if(RedisConfigType.rest.name().equals(config.getConfigType() )){
            RedisClusterConfiguration clusterConfiguration = this.createRestClusterConfig();
            return  clusterConfiguration;
        }

        // Manual configuration
        if(RedisConfigType.manual.name().equals(config.getConfigType() )){
            RedisClusterConfiguration  clusterConfiguration = this.createManualClusterConfig();

            return  clusterConfiguration;
        }

        throw  new RuntimeException("redis Configuration error：The wrongredis.config.type, allowing onlycom.enation.eop.sdk.config.redis.configure.RedisConfigTypeValues defined in");


    }





    /**
     * Build manualredis clusterconfiguration
     * @return
     */
    private RedisClusterConfiguration createManualClusterConfig(){
        String nodes = config.getClusterNodes();
        List<String> nodeList = RedisNodeBuilder.build(nodes);
        // If a password is specified, set the password
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(nodeList);
        String password =config.getPassword();
        if(StringUtil.notEmpty(password)) {
            clusterConfiguration.setPassword(RedisPassword.of(password));
        }
        return clusterConfiguration;
    }



    /**
     * buildrestThe way ofcluster configuration
     * @return
     */
    private RedisClusterConfiguration createRestClusterConfig(){

        String redisClusterSuffix= "/cache/client/redis/cluster/%s.json?clientVersion=";
        String redisClusterUrl = config.getRestUrl() + redisClusterSuffix+ config.getRestClientVersion();

        String url = String.format(redisClusterUrl, String.valueOf(config.getRestAppid()));
        String response = HttpUtils.doGet(url);
        ObjectMapper objectMapper = new ObjectMapper();
        HeartbeatInfo heartbeatInfo = null;
        try {
            heartbeatInfo = objectMapper.readValue(response, HeartbeatInfo.class);
        } catch (IOException e) {
            logger.error("remote build error, appId: {}", config.getRestAppid(), e);
        }
        if (heartbeatInfo == null) {
        }


        /** Check the client version**/
        if (heartbeatInfo.getStatus() == ClientStatusEnum.ERROR.getStatus()) {
            throw new IllegalStateException(heartbeatInfo.getMessage());
        }
        else if (heartbeatInfo.getStatus() == ClientStatusEnum.WARN.getStatus()) {
            logger.warn(heartbeatInfo.getMessage());
        } else {
            logger.info(heartbeatInfo.getMessage());
        }

        Set<String> nodeList = new HashSet<String>();
        // Like ip1: port1, ip2: port2, ip3: port3
        String nodeInfo = heartbeatInfo.getShardInfo();
        // For compatibility, if direct nodeinfo.split (" ") is allowed
        nodeInfo = nodeInfo.replace(" ", ",");
        String[] nodeArray = nodeInfo.split(",");
        for (String node : nodeArray) {
            String[] ipAndPort = node.split(":");
            if (ipAndPort.length < 2) {
                continue;
            }

            nodeList.add(node);
        }

        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(nodeList);

        return  clusterConfiguration;
    }


	@Override
	public RedisType getType() {
		return RedisType.cluster;
	}

}
