/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.goods;

import dev.shopflix.core.goods.model.dos.TagsDO;
import dev.shopflix.core.goods.service.TagsManager;
import dev.shopflix.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 商品标签控制器
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-28 14:49:36
 */
@RestController
@RequestMapping("/seller/goods/tags")
@Api(description = "商品标签相关API")
public class TagsSellerController {
	
	@Autowired
	private TagsManager tagsManager;
				

	@ApiOperation(value	= "查询商品标签列表", response = TagsDO.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"页码",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"每页显示数量",	required = true, dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize)	{
		
		return	this.tagsManager.list(pageNo,pageSize);
	}
	
	@ApiOperation(value	= "查询某标签下的商品")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"页码",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"每页显示数量",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "tag_id",	value =	"标签id",	required = true, dataType = "int",	paramType =	"path")
	})
	@GetMapping("/{tag_id}/goods")
	public Page listGoods(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @PathVariable("tag_id") Integer tagId){
		
		return this.tagsManager.queryTagGoods(tagId,pageNo,pageSize);
	}
	
	@ApiOperation(value	= "保存某标签下的商品")
	@ApiImplicitParams({
		@ApiImplicitParam(name	= "tag_id",	value =	"标签id",	required = true, dataType = "int",	paramType =	"path"),
		@ApiImplicitParam(name	= "goods_ids",	value =	"要保存的商品id",	required = true, dataType = "int",	paramType =	"path"),
	})
	@PutMapping("/{tag_id}/goods/{goods_ids}")
	public String saveGoods( @PathVariable("tag_id") Integer tagId,@PathVariable("goods_ids")Integer[] goodsIds){
		
		this.tagsManager.saveTagGoods(tagId,goodsIds);
		
		return null;
	}
	
	
	
	
}
