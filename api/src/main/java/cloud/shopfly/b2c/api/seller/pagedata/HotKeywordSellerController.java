/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 热门关键字控制器
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-04 10:43:23
 */
@RestController
@RequestMapping("/seller/pages/hot-keywords")
@Api(description = "热门关键字相关API")
public class HotKeywordSellerController {
	
	@Autowired
	private HotKeywordManager hotKeywordManager;
				

	@ApiOperation(value	= "查询热门关键字列表", response = HotKeyword.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"页码",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"每页显示数量",	required = true, dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize)	{
		
		return	this.hotKeywordManager.list(pageNo,pageSize);
	}
	
	
	@ApiOperation(value	= "添加热门关键字", response = HotKeyword.class)
	@PostMapping
	public HotKeyword add(@Valid HotKeyword hotKeyword)	{
		
		this.hotKeywordManager.add(hotKeyword);
		
		return	hotKeyword;
	}
				
	@PutMapping(value = "/{id}")
	@ApiOperation(value	= "修改热门关键字", response = HotKeyword.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public HotKeyword edit(@Valid HotKeyword hotKeyword, @PathVariable Integer id) {
		
		this.hotKeywordManager.edit(hotKeyword,id);
		
		return	hotKeyword;
	}
			
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "删除热门关键字")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"要删除的热门关键字主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {
		
		this.hotKeywordManager.delete(id);
		
		return "";
	}
				
	
	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "查询一个热门关键字")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",	value = "要查询的热门关键字主键",	required = true, dataType = "int",	paramType = "path")	
	})
	public HotKeyword get(@PathVariable	Integer	id)	{
		
		HotKeyword hotKeyword = this.hotKeywordManager.getModel(id);
		
		return	hotKeyword;
	}
				
}
