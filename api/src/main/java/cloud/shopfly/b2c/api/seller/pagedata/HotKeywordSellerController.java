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
package cloud.shopfly.b2c.api.seller.pagedata;

import cloud.shopfly.b2c.core.pagedata.model.HotKeyword;
import cloud.shopfly.b2c.core.pagedata.service.HotKeywordManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Hot keyword controller
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-04 10:43:23
 */
@RestController
@RequestMapping("/seller/pages/hot-keywords")
@Api(description = "Popular keywords are relevantAPI")
public class HotKeywordSellerController {
	
	@Autowired
	private HotKeywordManager hotKeywordManager;
				

	@ApiOperation(value	= "Example Query the list of popular keywords", response = HotKeyword.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"The page number",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"Display quantity per page",	required = true, dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize)	{
		
		return	this.hotKeywordManager.list(pageNo,pageSize);
	}
	
	
	@ApiOperation(value	= "Add hot keywords", response = HotKeyword.class)
	@PostMapping
	public HotKeyword add(@Valid HotKeyword hotKeyword)	{
		
		this.hotKeywordManager.add(hotKeyword);
		
		return	hotKeyword;
	}
				
	@PutMapping(value = "/{id}")
	@ApiOperation(value	= "Modify hot keywords", response = HotKeyword.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"A primary key",	required = true, dataType = "int",	paramType =	"path")
	})
	public HotKeyword edit(@Valid HotKeyword hotKeyword, @PathVariable Integer id) {
		
		this.hotKeywordManager.edit(hotKeyword,id);
		
		return	hotKeyword;
	}
			
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "Remove hot keywords")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"The primary key of the hot keyword to delete",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {
		
		this.hotKeywordManager.delete(id);
		
		return "";
	}
				
	
	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "Query a popular keyword")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",	value = "The primary key of the hot keyword to query",	required = true, dataType = "int",	paramType = "path")	
	})
	public HotKeyword get(@PathVariable	Integer	id)	{
		
		HotKeyword hotKeyword = this.hotKeywordManager.getModel(id);
		
		return	hotKeyword;
	}
				
}
