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
package cloud.shopfly.b2c.api.seller.base;

import cloud.shopfly.b2c.core.base.model.dos.SensitiveWords;
import cloud.shopfly.b2c.core.base.service.SensitiveWordsManager;
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
 * Sensitive word controller
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-02 11:30:59
 */
@RestController
@RequestMapping("/seller/sensitive-words")
@Api(description = "Sensitive word correlationAPI")
public class SensitiveWordsSellerController {
	
	@Autowired
	private	SensitiveWordsManager sensitiveWordsManager;
				

	@ApiOperation(value	= "Example Query the list of sensitive words", response = SensitiveWords.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"The page number",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"Display quantity per page",	required = true, dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String keyword)	{
		
		return	this.sensitiveWordsManager.list(pageNo,pageSize,keyword);
	}
	
	
	@ApiOperation(value	= "Add sensitive words", response = SensitiveWords.class)
	@PostMapping
	public SensitiveWords add(@Valid SensitiveWords sensitiveWords)	{
		
		this.sensitiveWordsManager.add(sensitiveWords);
		
		return	sensitiveWords;
	}
				
	@PutMapping(value = "/{id}")
	@ApiOperation(value	= "Modify sensitive words", response = SensitiveWords.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"A primary key",	required = true, dataType = "int",	paramType =	"path")
	})
	public	SensitiveWords edit(@Valid SensitiveWords sensitiveWords, @PathVariable Integer id) {
		
		this.sensitiveWordsManager.edit(sensitiveWords,id);
		
		return	sensitiveWords;
	}
			
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "Delete sensitive words")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"Primary key of sensitive words to delete",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {
		
		this.sensitiveWordsManager.delete(id);
		
		return "";
	}
				
	
	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "Query a sensitive word")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",	value = "Primary key of sensitive word to be queried",	required = true, dataType = "int",	paramType = "path")	
	})
	public	SensitiveWords get(@PathVariable	Integer	id)	{
		
		SensitiveWords sensitiveWords = this.sensitiveWordsManager.getModel(id);
		
		return	sensitiveWords;
	}
				
}
