/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.api;

import com.enation.app.javashop.deploy.service.DeployExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.enation.app.javashop.framework.database.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

import com.enation.app.javashop.deploy.model.Database;
import com.enation.app.javashop.deploy.service.DatabaseManager;

/**
 * 数据库控制器
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-04-24 13:34:30
 */
@RestController
@RequestMapping("/data/databases")
@Api(description = "数据库相关API")
public class DatabaseDataController	{
	
	@Autowired
	private	DatabaseManager databaseManager;


	@ApiOperation(value	= "查询数据库列表", response = Database.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"页码",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"每页显示数量",	required = true, dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page list(@ApiIgnore Integer pageNo,@ApiIgnore Integer pageSize)	{
		
		return	this.databaseManager.list(pageNo,pageSize);
	}
	
	
	@ApiOperation(value	= "添加数据库", response = Database.class)
	@PostMapping
	public Database add(@Valid Database database)	{
		
		this.databaseManager.add(database);
		
		return	database;
	}
				
	@PutMapping(value = "/{id}")
	@ApiOperation(value	= "修改数据库", response = Database.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	Database edit(@Valid Database database, @PathVariable Integer id) {
		
		this.databaseManager.edit(database,id);
		
		return	database;
	}
			
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "删除数据库")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"要删除的数据库主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {
		
		this.databaseManager.delete(id);
		
		return "";
	}
				
	
	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "查询一个数据库")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",	value = "要查询的数据库主键",	required = true, dataType = "int",	paramType = "path")
	})
	public	Database get(@PathVariable	Integer	id)	{
		
		Database database = this.databaseManager.getModel(id);
		
		return	database;
	}

	@GetMapping(value =	"/connection")
	@ApiOperation(value	= "测试数据库的连接")
	public  boolean testConnection(Database database){
		return  this.databaseManager.testConnection(database);
	}




}