/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.api;

import com.enation.app.javashop.deploy.model.Database;
import com.enation.app.javashop.deploy.model.Redis;
import com.enation.app.javashop.deploy.service.RedisManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.enation.app.javashop.framework.database.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * redis控制器
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-05-04 20:04:36
 */
@RestController
@RequestMapping("/data/redis")
@Api(description = "redis相关API")
public class RedisController	{
	
	@Autowired
	private RedisManager redisManager;
				

	@ApiOperation(value	= "查询redis列表", response = Redis.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"页码",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"每页显示数量",	required = true, dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page list(@ApiIgnore Integer pageNo,@ApiIgnore Integer pageSize)	{
		
		return	this.redisManager.list(pageNo,pageSize);
	}
	
	
	@ApiOperation(value	= "添加redis", response = Redis.class)
	@PostMapping
	public Redis add(@Valid Redis redis)	{
		
		this.redisManager.add(redis);
		
		return	redis;
	}
				
	@PutMapping(value = "/{id}")
	@ApiOperation(value	= "修改redis", response = Redis.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	Redis edit(@Valid Redis redis, @PathVariable Integer id) {
		
		this.redisManager.edit(redis,id);
		
		return	redis;
	}
			
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "删除redis")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"要删除的redis主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {
		
		this.redisManager.delete(id);
		
		return "";
	}
				
	
	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "查询一个redis")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",	value = "要查询的redis主键",	required = true, dataType = "int",	paramType = "path")	
	})
	public	Redis get(@PathVariable	Integer	id)	{
		
		Redis redis = this.redisManager.getModel(id);
		
		return	redis;
	}


	@GetMapping(value =	"/connection")
	@ApiOperation(value	= "测试redis的连接")
	public  boolean testConnection(Redis redis){
		return this.redisManager.testConnection(redis);
	}
				
}