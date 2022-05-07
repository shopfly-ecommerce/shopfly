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
package cloud.shopfly.b2c.framework.redis.builder;

import cloud.shopfly.b2c.framework.redis.configure.IRedisBuilder;
import cloud.shopfly.b2c.framework.redis.configure.JedisSetting;
import cloud.shopfly.b2c.framework.redis.configure.RedisConnectionConfig;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * redisTemplateBuilder To optimize
 *
 * @author jianghongyan
 * @version v1.0.0
 * @since v1.0.0 2017years4month10On the afternoon7:52:35
 */
@Component
public class RedisTemplateBuilder {
    private static Logger logger = LoggerFactory.getLogger(RedisTemplateBuilder.class);

    @Autowired
    private List<IRedisBuilder> redisBuilder;

    @Autowired
    private RedisConnectionConfig config;

    /**
     * Build the lock
     */
    private static final Lock LOCK = new ReentrantLock();

    public RedisTemplate<String, Object> build() {


        JedisSetting.loadPoolConfig(config);

        RedisTemplate<String, Object> redisTemplate = null;

        while (true) {
            try {
                LOCK.tryLock(10, TimeUnit.MILLISECONDS);
                if (redisTemplate == null) {

                    IRedisBuilder redisBuilder = this.getRedisBuilder();
                    RedisConnectionFactory lettuceConnectionFactory = redisBuilder.buildConnectionFactory(config);

                    // Initialize the connection pool
                    redisTemplate = new RedisTemplate<String, Object>();
                    redisTemplate.setConnectionFactory(lettuceConnectionFactory);
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
                    redisTemplate.setEnableTransactionSupport(false);
                    return redisTemplate;
                }
            } catch (Throwable e) {
                // Fault tolerance
                logger.error(e.getMessage(), e);

                break;
            } finally {
                LOCK.unlock();
            }
            try {
                // Live lock
                TimeUnit.MILLISECONDS.sleep(200 + new Random().nextInt(1000));
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return redisTemplate;
    }

    private IRedisBuilder getRedisBuilder() {
        for (IRedisBuilder builder : redisBuilder) {
            if (builder.getType().name().equals(config.getType())) {
                return builder;
            }
        }
        throw new ServiceException(SystemErrorCodeV1.INVALID_CONFIG_PARAMETER, "The wrongredis Configuration type, please check");
    }

}
