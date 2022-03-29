/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.service.impl;

import com.enation.app.javashop.deploy.model.Redis;
import com.enation.app.javashop.deploy.service.RedisManager;
import com.enation.app.javashop.framework.exception.ServiceException;
import com.enation.app.javashop.framework.exception.SystemErrorCodeV1;
import com.enation.app.javashop.framework.redis.configure.IRedisBuilder;
import com.enation.app.javashop.framework.redis.configure.RedisConfigType;
import com.enation.app.javashop.framework.redis.configure.RedisConnectionConfig;
import com.enation.app.javashop.framework.redis.configure.RedisType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;

import java.util.List;

/**
 * redis业务类
 *
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-05-04 20:04:36
 */
@Service
public class RedisManagerImpl implements RedisManager {

    @Autowired
    private DaoSupport daoSupport;

    @Autowired
    private List<IRedisBuilder> redisBuilder;


    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_redis  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, Redis.class);

        return webPage;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Redis add(Redis redis) {
        this.daoSupport.insert(redis);

        return redis;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Redis edit(Redis redis, Integer id) {
        this.daoSupport.update(redis, id);
        return redis;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(Redis.class, id);
    }

    @Override
    public Redis getModel(Integer id) {
        return this.daoSupport.queryForObject(Redis.class, id);
    }

    @Override
    public Redis getByDeployId(Integer deployId) {
        String sql ="select * from es_redis where deploy_id=?";
        Redis redis = daoSupport.queryForObject(sql,Redis.class, deployId);
        return redis;
    }

    @Override
    public void initRedis(Integer deployId) {
        Redis redis = new Redis();
        redis.setRedisType(RedisType.standalone.name());
        redis.setConfigType(RedisConfigType.manual.name());
        redis.setStandaloneHost("127.0.0.1");
        redis.setStandalonePort("6379");
        redis.setDeployId(deployId);
        this.add(redis);
    }

    @Override
    public boolean testConnection(Redis redis) {
        try {

            RedisConnection connection =  this.getConnection(redis);

            byte[] byti = "test".getBytes();
            connection.set(byti, byti);
            byte[] result = connection.get(byti);

            return  new String(result).equals( new String( byti));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public RedisConnection getConnection(Redis redis) {
        RedisConnectionFactory connectionFactory = this.buildFactory(redis);
        RedisConnection connection = connectionFactory.getConnection();
        return connection;
    }

    private RedisConnectionFactory buildFactory(Redis redis){
        IRedisBuilder builder = this.getRedisBuilder(redis.getRedisType());

        RedisConnectionConfig config = new RedisConnectionConfig();
        config.setConfigType(redis.getConfigType());
        config.setClusterNodes(redis.getClusterNodes());

        config.setHost(redis.getStandaloneHost());
        config.setPassword(redis.getStandalonePassword());
        config.setPort(Integer.valueOf(redis.getStandalonePort()));
        config.setRestAppid(redis.getRestAppid());
        config.setRestUrl(redis.getRestUrl());
        config.setSentinelMaster(redis.getSentinelMaster());
        config.setSentinelNodes(redis.getSentinelNodes());

        RedisConnectionFactory connectionFactory = builder.buildConnectionFactory(config);
        return connectionFactory;
    }

    private IRedisBuilder getRedisBuilder(String redisType) {

        for (IRedisBuilder builder : redisBuilder) {
            if (builder.getType().name().equals(redisType)) {
                return builder;
            }
        }
        throw new ServiceException(SystemErrorCodeV1.INVALID_CONFIG_PARAMETER, "错误的redis 配置类型，请检查");
    }

}
