/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.redis.configure.builders;


import com.enation.app.javashop.framework.redis.configure.IRedisBuilder;
import com.enation.app.javashop.framework.redis.configure.RedisConfigType;
import com.enation.app.javashop.framework.redis.configure.RedisConnectionConfig;
import com.enation.app.javashop.framework.redis.configure.RedisType;
import com.enation.app.javashop.framework.util.HttpUtils;
import com.enation.app.javashop.framework.util.StringUtil;
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
 * 集群构建器
 * v2.0: 新增手动配置方式,by kingapex 2017-12-26
 * @author fk,kingapex
 * @version v2.0
 * @since v6.4
 * 2017年10月27日 下午2:43:03
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
     * 构建redis cluster的配置
     * @return redis cluster的配置
     */
    public    RedisClusterConfiguration buildClusterConfig(){

        //cache cloud rest api配置方式
        if(RedisConfigType.rest.name().equals(config.getConfigType() )){
            RedisClusterConfiguration clusterConfiguration = this.createRestClusterConfig();
            return  clusterConfiguration;
        }

        //手动配置方式
        if(RedisConfigType.manual.name().equals(config.getConfigType() )){
            RedisClusterConfiguration  clusterConfiguration = this.createManualClusterConfig();

            return  clusterConfiguration;
        }

        throw  new RuntimeException("redis 配置错误：错误的redis.config.type，只允许com.enation.eop.sdk.config.redis.configure.RedisConfigType中定义的值");


    }





    /**
     * 构建手动方式的 redis cluster配置
     * @return
     */
    private RedisClusterConfiguration createManualClusterConfig(){
        String nodes = config.getClusterNodes();
        List<String> nodeList = RedisNodeBuilder.build(nodes);
        //如果指定了密码，设置密码
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(nodeList);
        String password =config.getPassword();
        if(StringUtil.notEmpty(password)) {
            clusterConfiguration.setPassword(RedisPassword.of(password));
        }
        return clusterConfiguration;
    }



    /**
     * 构建rest方式的cluster 配置
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


        /** 检查客户端版本 **/
        if (heartbeatInfo.getStatus() == ClientStatusEnum.ERROR.getStatus()) {
            throw new IllegalStateException(heartbeatInfo.getMessage());
        }
        else if (heartbeatInfo.getStatus() == ClientStatusEnum.WARN.getStatus()) {
            logger.warn(heartbeatInfo.getMessage());
        } else {
            logger.info(heartbeatInfo.getMessage());
        }

        Set<String> nodeList = new HashSet<String>();
        //形如 ip1:port1,ip2:port2,ip3:port3
        String nodeInfo = heartbeatInfo.getShardInfo();
        //为了兼容,如果允许直接nodeInfo.split(" ")
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
