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
package cloud.shopfly.b2c.api.seller.goods;

import cloud.shopfly.b2c.core.goods.model.dos.TagsDO;
import cloud.shopfly.b2c.core.goods.service.TagsManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Commodity label controller
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-28 14:49:36
 */
@RestController
@RequestMapping("/seller/goods/tags")
@Api(description = "Product label correlationAPI")
public class TagsSellerController {
	
	@Autowired
	private TagsManager tagsManager;
				

	@ApiOperation(value	= "Example Query the commodity label list", response = TagsDO.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"The page number",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"Display quantity per page",	required = true, dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize)	{
		
		return	this.tagsManager.list(pageNo,pageSize);
	}
	
	@ApiOperation(value	= "Query items under a label")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"The page number",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"Display quantity per page",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "tag_id",	value =	"The labelid",	required = true, dataType = "int",	paramType =	"path")
	})
	@GetMapping("/{tag_id}/goods")
	public Page listGoods(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @PathVariable("tag_id") Integer tagId){
		
		return this.tagsManager.queryTagGoods(tagId,pageNo,pageSize);
	}
	
	@ApiOperation(value	= "Save an item under a label")
	@ApiImplicitParams({
		@ApiImplicitParam(name	= "tag_id",	value =	"The labelid",	required = true, dataType = "int",	paramType =	"path"),
		@ApiImplicitParam(name	= "goods_ids",	value =	"Goods to be savedid",	required = true, dataType = "int",	paramType =	"path"),
	})
	@PutMapping("/{tag_id}/goods/{goods_ids}")
	public String saveGoods( @PathVariable("tag_id") Integer tagId,@PathVariable("goods_ids")Integer[] goodsIds){
		
		this.tagsManager.saveTagGoods(tagId,goodsIds);
		
		return null;
	}
	
	
	
	
}
