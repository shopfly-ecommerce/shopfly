/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.redis.configure.builders;

import dev.shopflix.framework.redis.configure.IRedisBuilder;
import dev.shopflix.framework.redis.configure.RedisConfigType;
import dev.shopflix.framework.redis.configure.RedisConnectionConfig;
import dev.shopflix.framework.redis.configure.RedisType;
import dev.shopflix.framework.util.HttpUtils;
import dev.shopflix.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 独立模式构建器
 * v2.0: 新增手动配置方式,by kingapex 2017-12-26
 * @author fk,kingapex
 * @version v2.0
 * @since v6.4
 * 2017年10月27日 下午2:37:04
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
     * 构建redis cluster的配置
     * @return redis cluster的配置
     */
    public RedisStandaloneConfiguration buildStandaloneConfig(){


        //cache cloud rest api配置方式
        if(RedisConfigType.rest.name().equals(config.getConfigType() )){
            RedisStandaloneConfiguration configuration = createRestStandaloneConfig();
            return  configuration;
        }

        //手动配置方式
        if(RedisConfigType.manual.name().equals(config.getConfigType() )){
            RedisStandaloneConfiguration  configuration = createManualStandaloneConfig();
            return  configuration;
        }

        throw  new RuntimeException("redis 配置错误：错误的redis.config.type，只允许com.enation.eop.sdk.config.redis.configure.RedisConfigType中定义的值");


    }




    /**
     * 构建手动方式的 redis Standalone 配置
     * @return
     */
    private RedisStandaloneConfiguration createManualStandaloneConfig(){

        String host = config.getHost();
        if(StringUtil.isEmpty(host)){
            throw new RuntimeException("redis 配置错误：redis.host为空");
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
     * 构建rest方式的 Standalone 配置
     * @return
     */
    private RedisStandaloneConfiguration createRestStandaloneConfig(){

        String redisClusterSuffix= "/cache/client/redis/standalone/%s.json?clientVersion=";
        String redisClusterUrl = config.getRestUrl() + redisClusterSuffix+ config.getRestClientVersion();

        String url = String.format(redisClusterUrl, String.valueOf(config.getRestAppid()));
        String response = HttpUtils.doGet(url);

        String  appId = config.getRestAppid();
        /**
         * 心跳返回的请求为空；
         */

        if (response == null || response.isEmpty()) {
            logger.warn("cannot get response from server, appId={}. continue...", appId);
        }

        /**
         * 心跳返回的请求无效；
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
         * 从心跳中提取HostAndPort，构造JedisPool实例；
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
