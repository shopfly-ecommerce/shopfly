/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.api;

import com.enation.app.javashop.deploy.model.Elasticsearch;
import com.enation.app.javashop.deploy.service.ElasticsearchManager;
import com.enation.app.javashop.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * elasticsearch控制器
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2019-02-13 10:39:25
 */
@RestController
@RequestMapping("/data/elasticsearchs")
@Api(description = "elasticsearch相关API")
public class ElasticsearchController	{
	
	@Autowired
	private	ElasticsearchManager elasticsearchManager;
				

	@ApiOperation(value	= "查询elasticsearch列表", response = Elasticsearch.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"页码",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"每页显示数量",	required = true, dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page list(@ApiIgnore Integer pageNo,@ApiIgnore Integer pageSize)	{
		
		return	this.elasticsearchManager.list(pageNo,pageSize);
	}
	
	
	@ApiOperation(value	= "添加elasticsearch", response = Elasticsearch.class)
	@PostMapping
	public Elasticsearch add(@Valid Elasticsearch elasticsearch)	{
		
		this.elasticsearchManager.add(elasticsearch);
		
		return	elasticsearch;
	}
				
	@PutMapping(value = "/{id}")
	@ApiOperation(value	= "修改elasticsearch", response = Elasticsearch.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	Elasticsearch edit(@Valid Elasticsearch elasticsearch, @PathVariable Integer id) {
		
		this.elasticsearchManager.edit(elasticsearch,id);
		
		return	elasticsearch;
	}
			
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "删除elasticsearch")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"要删除的elasticsearch主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {
		
		this.elasticsearchManager.delete(id);
		
		return "";
	}
				
	
	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "查询一个elasticsearch")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",	value = "要查询的elasticsearch主键",	required = true, dataType = "int",	paramType = "path")	
	})
	public	Elasticsearch get(@PathVariable	Integer	id)	{
		
		Elasticsearch elasticsearch = this.elasticsearchManager.getModel(id);
		
		return	elasticsearch;
	}


	@GetMapping(value =	"/connection")
	@ApiOperation(value	= "测试 elasticsearch 的连接")
	public  boolean testConnection(Elasticsearch elasticsearch ){

		return elasticsearchManager.testConnection(elasticsearch);

	}

}