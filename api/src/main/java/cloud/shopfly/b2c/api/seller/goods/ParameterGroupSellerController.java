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
import cloud.shopfly.b2c.core.goods.model.dos.ParameterGroupDO;
import cloud.shopfly.b2c.core.goods.service.ParameterGroupManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Parameter group controller
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 16:14:17
 */
@RestController
@RequestMapping("/seller/goods/parameter-groups")
@Api(description = "Parameter group correlationAPI")
@Validated
public class ParameterGroupSellerController {

	@Autowired
	private ParameterGroupManager parameterGroupManager;

	@ApiOperation(value = "Add parameter group", response = ParameterGroupDO.class)
	@PostMapping
	public ParameterGroupDO add(@Valid ParameterGroupDO parameterGroup) {

		this.parameterGroupManager.add(parameterGroup);

		return parameterGroup;
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Modify parameter set", response = ParameterGroupDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path"),
			@ApiImplicitParam(name = "group_name", value = "Parameter group name", required = true, dataType = "string", paramType = "query")
	})
	public ParameterGroupDO edit(@NotEmpty(message = "The parameter group name cannot be empty") String groupName, @PathVariable Integer id) {

		ParameterGroupDO parameterGroup = this.parameterGroupManager.edit(groupName, id);

		return parameterGroup;
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Delete parameter set")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Primary key of the parameter group to be deleted", required = true, dataType = "int", paramType = "path") })
	public String delete(@PathVariable Integer id) {

		this.parameterGroupManager.delete(id);

		return "";
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Example Query a parameter group")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Primary key of the parameter group to be queried", required = true, dataType = "int", paramType = "path") })
	public ParameterGroupDO get(@PathVariable Integer id) {

		ParameterGroupDO parameterGroup = this.parameterGroupManager.getModel(id);

		return parameterGroup;
	}

	@ApiOperation(value = "The parameter group moves up or down", notes = "This parameter is used when you move parameter groups up or down when binding parameters by category")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "group_id", value = "Parameter setid", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "sort_type", value = "Sort type, move upupMove down,down", required = true, paramType = "query", dataType = "String"), })
	@PutMapping(value = "/{group_id}/sort")
	public String groupSort(@PathVariable("group_id") Integer groupId,
			@ApiIgnore @SortType String sortType) {

		this.parameterGroupManager.groupSort(groupId, sortType);

		return null;
	}

}
