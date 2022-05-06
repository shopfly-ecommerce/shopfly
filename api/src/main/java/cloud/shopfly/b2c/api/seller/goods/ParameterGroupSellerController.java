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
 * 参数组控制器
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 16:14:17
 */
@RestController
@RequestMapping("/seller/goods/parameter-groups")
@Api(description = "参数组相关API")
@Validated
public class ParameterGroupSellerController {

	@Autowired
	private ParameterGroupManager parameterGroupManager;

	@ApiOperation(value = "添加参数组", response = ParameterGroupDO.class)
	@PostMapping
	public ParameterGroupDO add(@Valid ParameterGroupDO parameterGroup) {

		this.parameterGroupManager.add(parameterGroup);

		return parameterGroup;
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value = "修改参数组", response = ParameterGroupDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path"),
			@ApiImplicitParam(name = "group_name", value = "参数组名称", required = true, dataType = "string", paramType = "query")
	})
	public ParameterGroupDO edit(@NotEmpty(message = "参数组名称不能为空") String groupName, @PathVariable Integer id) {

		ParameterGroupDO parameterGroup = this.parameterGroupManager.edit(groupName, id);

		return parameterGroup;
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "删除参数组")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "要删除的参数组主键", required = true, dataType = "int", paramType = "path") })
	public String delete(@PathVariable Integer id) {

		this.parameterGroupManager.delete(id);

		return "";
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "查询一个参数组")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "要查询的参数组主键", required = true, dataType = "int", paramType = "path") })
	public ParameterGroupDO get(@PathVariable Integer id) {

		ParameterGroupDO parameterGroup = this.parameterGroupManager.getModel(id);

		return parameterGroup;
	}

	@ApiOperation(value = "参数组上移或者下移", notes = "分类绑定参数时，上移下移参数组时使用")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "group_id", value = "参数组id", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "sort_type", value = "排序类型，上移 up，下移down", required = true, paramType = "query", dataType = "String"), })
	@PutMapping(value = "/{group_id}/sort")
	public String groupSort(@PathVariable("group_id") Integer groupId,
			@ApiIgnore @SortType String sortType) {

		this.parameterGroupManager.groupSort(groupId, sortType);

		return null;
	}

}
