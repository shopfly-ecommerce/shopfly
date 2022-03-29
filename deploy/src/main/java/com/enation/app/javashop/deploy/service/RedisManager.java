/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.service;

import com.enation.app.javashop.deploy.model.Database;
import com.enation.app.javashop.deploy.model.Redis;
import com.enation.app.javashop.framework.database.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * redis业务层
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-05-04 20:04:36
 */
public interface RedisManager	{

	/**
	 * 查询redis列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @return Page 
	 */
	Page list(int page, int pageSize);
	/**
	 * 添加redis
	 * @param redis redis
	 * @return Redis redis
	 */
	Redis add(Redis redis);

	/**
	* 修改redis
	* @param redis redis
	* @param id redis主键
	* @return Redis redis
	*/
	Redis edit(Redis redis, Integer id);
	
	/**
	 * 删除redis
	 * @param id redis主键
	 */
	void delete(Integer id);
	
	/**
	 * 获取redis
	 * @param id redis主键
	 * @return Redis  redis
	 */
	Redis getModel(Integer id);


	/**
	 * 根据depolyid获取 Redis配置信息
	 * @param deployId 部署id
	 * @return redis配置信息
	 */
	Redis getByDeployId(Integer deployId);

	/**
	 * 初始化Redis
	 * @param deployId 部署id
	 */
	void initRedis(Integer deployId);


	/**
	 * 测试redis连接
	 * @param redis  reids信息
	 * @return 是否连接成功
	 */
	boolean testConnection(Redis redis);


	/**
	 * 构建redis  Connection
	 * @param redis
	 * @return
	 */
	RedisConnection getConnection(Redis redis);



}