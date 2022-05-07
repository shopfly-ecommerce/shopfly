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
package cloud.shopfly.b2c.api.seller.promotion;

import cloud.shopfly.b2c.core.promotion.minus.model.dos.MinusDO;
import cloud.shopfly.b2c.core.promotion.minus.model.vo.MinusVO;
import cloud.shopfly.b2c.core.promotion.minus.service.MinusManager;
import cloud.shopfly.b2c.core.promotion.tool.support.PromotionValid;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
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
 * Single-product vertical reduction controller
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 19:52:27
 */
@RestController
@RequestMapping("/seller/promotion/minus")
@Api(description = "Individual products immediately reduce correlationAPI")
@Validated
public class MinusSellerController {

	@Autowired
	private MinusManager minusManager;


	@ApiOperation(value	= "Query the list of items", response = MinusDO.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"The page number", dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"Display quantity per page", dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "keywords",	value =	"keyword", dataType = "String",	paramType =	"query"),
	})
	@GetMapping
	public Page<MinusVO> list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String keywords)	{
		return	this.minusManager.list(pageNo,pageSize,keywords);
	}


	@ApiOperation(value	= "Add a single item for immediate reduction", response = MinusVO.class)
	@ApiImplicitParam(name = "minus", value = "Single product instant reduction information", required = true, dataType = "MinusVO", paramType = "body")
	@PostMapping
	public MinusVO add(@ApiIgnore @Valid @RequestBody MinusVO minus) {

		PromotionValid.paramValid(minus.getStartTime(),minus.getEndTime(),minus.getRangeType(),minus.getGoodsList());
		this.minusManager.add(minus);
		return minus;

	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value	= "Modify the immediate reduction of single products", response = MinusVO.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"A primary key",	required = true, dataType = "int",	paramType =	"path")
	})
	public MinusVO edit(@Valid @RequestBody MinusVO minus, @PathVariable Integer id) {

		this.minusManager.verifyAuth(id);
		PromotionValid.paramValid(minus.getStartTime(),minus.getEndTime(),minus.getRangeType(),minus.getGoodsList());
		minus.setMinusId(id);
		this.minusManager.edit(minus,id);

		return	minus;
	}


	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "Delete single item subtraction")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"The item to be deleted immediately decreases the primary key",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {

		this.minusManager.verifyAuth(id);
		this.minusManager.delete(id);

		return "";
	}


	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "Query a single item for immediate reduction")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",	value = "To query the single product minus primary key",	required = true, dataType = "int",	paramType = "path")
	})
	public MinusVO get(@PathVariable Integer id)	{
		MinusVO minusVO = this.minusManager.getFromDB(id);

		// Verify unauthorized operations
		if (minusVO == null){
			throw new NoPermissionException("Have the right to operate");
		}

		return	minusVO;
	}


}
