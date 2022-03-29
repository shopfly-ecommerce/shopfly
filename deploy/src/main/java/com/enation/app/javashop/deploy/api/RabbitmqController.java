/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.api;

import com.enation.app.javashop.deploy.model.Rabbitmq;
import com.enation.app.javashop.deploy.service.RabbitmqManager;
import org.hibernate.validator.constraints.Length;
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
 * rabbitmq控制器
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-05-07 17:33:11
 */
@RestController
@RequestMapping("/data/rabbitmq")
@Api(description = "rabbitmq相关API")
public class RabbitmqController	{
	
	@Autowired
	private RabbitmqManager rabbitmqManager;
				

	@ApiOperation(value	= "查询rabbitmq列表", response = Rabbitmq.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"页码",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"每页显示数量",	required = true, dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page list(@ApiIgnore Integer pageNo,@ApiIgnore Integer pageSize)	{
		
		return	this.rabbitmqManager.list(pageNo,pageSize);
	}
	
	
	@ApiOperation(value	= "添加rabbitmq", response = Rabbitmq.class)
	@PostMapping
	public Rabbitmq add(@Valid Rabbitmq rabbitmq)	{
		
		this.rabbitmqManager.add(rabbitmq);
		
		return	rabbitmq;
	}
				
	@PutMapping(value = "/{id}")
	@ApiOperation(value	= "修改rabbitmq", response = Rabbitmq.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	Rabbitmq edit(@Valid Rabbitmq rabbitmq, @PathVariable Integer id) {
		
		this.rabbitmqManager.edit(rabbitmq,id);
		
		return	rabbitmq;
	}
			
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "删除rabbitmq")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"要删除的rabbitmq主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable @Length  Integer id) {
		
		this.rabbitmqManager.delete(id);
		
		return "";
	}
				
	
	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "查询一个rabbitmq")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",	value = "要查询的rabbitmq主键",	required = true, dataType = "int",	paramType = "path")	
	})
	public	Rabbitmq get(@PathVariable	Integer	id)	{
		
		Rabbitmq rabbitmq = this.rabbitmqManager.getModel(id);
		
		return	rabbitmq;
	}

	@GetMapping(value =	"/connection")
	@ApiOperation(value	= "测试rabbitmq的连接")
	public  boolean testConnection(Rabbitmq rabbitmq){

		return this.rabbitmqManager.testConnection(rabbitmq);
	}

				
}