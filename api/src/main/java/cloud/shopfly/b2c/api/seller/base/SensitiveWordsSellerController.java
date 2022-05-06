/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 敏感词控制器
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-02 11:30:59
 */
@RestController
@RequestMapping("/seller/sensitive-words")
@Api(description = "敏感词相关API")
public class SensitiveWordsSellerController {
	
	@Autowired
	private	SensitiveWordsManager sensitiveWordsManager;
				

	@ApiOperation(value	= "查询敏感词列表", response = SensitiveWords.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"页码",	required = true, dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"每页显示数量",	required = true, dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String keyword)	{
		
		return	this.sensitiveWordsManager.list(pageNo,pageSize,keyword);
	}
	
	
	@ApiOperation(value	= "添加敏感词", response = SensitiveWords.class)
	@PostMapping
	public SensitiveWords add(@Valid SensitiveWords sensitiveWords)	{
		
		this.sensitiveWordsManager.add(sensitiveWords);
		
		return	sensitiveWords;
	}
				
	@PutMapping(value = "/{id}")
	@ApiOperation(value	= "修改敏感词", response = SensitiveWords.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	SensitiveWords edit(@Valid SensitiveWords sensitiveWords, @PathVariable Integer id) {
		
		this.sensitiveWordsManager.edit(sensitiveWords,id);
		
		return	sensitiveWords;
	}
			
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "删除敏感词")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"要删除的敏感词主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {
		
		this.sensitiveWordsManager.delete(id);
		
		return "";
	}
				
	
	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "查询一个敏感词")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",	value = "要查询的敏感词主键",	required = true, dataType = "int",	paramType = "path")	
	})
	public	SensitiveWords get(@PathVariable	Integer	id)	{
		
		SensitiveWords sensitiveWords = this.sensitiveWordsManager.getModel(id);
		
		return	sensitiveWords;
	}
				
}
