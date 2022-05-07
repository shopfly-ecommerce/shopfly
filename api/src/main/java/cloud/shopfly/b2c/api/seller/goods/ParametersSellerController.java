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

import cloud.shopfly.b2c.core.goods.constraint.annotation.SortType;
import cloud.shopfly.b2c.core.goods.model.dos.ParametersDO;
import cloud.shopfly.b2c.core.goods.service.ParametersManager;
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
 * Parameter controller
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 16:14:31
 */
@RestController
@RequestMapping("/seller/goods/parameters")
@Api(description = "Parameters related toAPI")
@Validated
public class ParametersSellerController {

	@Autowired
	private ParametersManager parametersManager;

	@ApiOperation(value = "Add parameters", response = ParametersDO.class)
	@PostMapping
	public ParametersDO add(@Valid ParametersDO parameters) {

		this.parametersManager.add(parameters);

		return parameters;
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Modify the parameters", response = ParametersDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path") })
	public ParametersDO edit(@Valid ParametersDO parameters, @PathVariable Integer id) {

		this.parametersManager.edit(parameters, id);

		return parameters;
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Delete the parameter")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Primary key of the parameter to be deleted", required = true, dataType = "int", paramType = "path") })
	public String delete(@PathVariable Integer id) {

		this.parametersManager.delete(id);

		return "";
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Query a parameter")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Primary key of the parameter to be queried", required = true, dataType = "int", paramType = "path") })
	public ParametersDO get(@PathVariable Integer id) {

		ParametersDO parameters = this.parametersManager.getModel(id);

		return parameters;
	}

	@ApiOperation(value = "Parameters move up or down", notes = "This parameter is used when you move parameter groups up or down when binding parameters by category")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sort_type", value = "Sort type, move upupMove down,down", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "param_id", value = "Parameter the primary key", required = true, paramType = "path", dataType = "String"), })
	@PutMapping(value = "/{param_id}/sort")
	public String paramSort(@PathVariable("param_id") Integer paramId, @ApiIgnore @SortType String sortType) {

		this.parametersManager.paramSort(paramId, sortType);

		return null;
	}

}
