/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.promotion;

import com.enation.app.javashop.core.promotion.minus.model.dos.MinusDO;
import com.enation.app.javashop.core.promotion.minus.model.vo.MinusVO;
import com.enation.app.javashop.core.promotion.minus.service.MinusManager;
import com.enation.app.javashop.core.promotion.tool.support.PromotionValid;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.exception.NoPermissionException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 单品立减控制器
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 19:52:27
 */
@RestController
@RequestMapping("/seller/promotion/minus")
@Api(description = "单品立减相关API")
@Validated
public class MinusSellerController {

	@Autowired
	private MinusManager minusManager;


	@ApiOperation(value	= "查询单品立减列表", response = MinusDO.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"页码", dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"每页显示数量", dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "keywords",	value =	"关键字", dataType = "String",	paramType =	"query"),
	})
	@GetMapping
	public Page<MinusVO> list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String keywords)	{
		return	this.minusManager.list(pageNo,pageSize,keywords);
	}


	@ApiOperation(value	= "添加单品立减", response = MinusVO.class)
	@ApiImplicitParam(name = "minus", value = "单品立减信息", required = true, dataType = "MinusVO", paramType = "body")
	@PostMapping
	public MinusVO add(@ApiIgnore @Valid @RequestBody MinusVO minus) {

		PromotionValid.paramValid(minus.getStartTime(),minus.getEndTime(),minus.getRangeType(),minus.getGoodsList());
		this.minusManager.add(minus);
		return minus;

	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value	= "修改单品立减", response = MinusVO.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public MinusVO edit(@Valid @RequestBody MinusVO minus, @PathVariable Integer id) {

		this.minusManager.verifyAuth(id);
		PromotionValid.paramValid(minus.getStartTime(),minus.getEndTime(),minus.getRangeType(),minus.getGoodsList());
		minus.setMinusId(id);
		this.minusManager.edit(minus,id);

		return	minus;
	}


	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "删除单品立减")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"要删除的单品立减主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {

		this.minusManager.verifyAuth(id);
		this.minusManager.delete(id);

		return "";
	}


	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "查询一个单品立减")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",	value = "要查询的单品立减主键",	required = true, dataType = "int",	paramType = "path")
	})
	public MinusVO get(@PathVariable Integer id)	{
		MinusVO minusVO = this.minusManager.getFromDB(id);

		//验证越权操作
		if (minusVO == null){
			throw new NoPermissionException("无权操作");
		}

		return	minusVO;
	}


}
