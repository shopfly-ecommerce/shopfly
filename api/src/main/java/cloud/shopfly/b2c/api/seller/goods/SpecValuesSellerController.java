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

import cloud.shopfly.b2c.core.goods.model.dos.SpecValuesDO;
import cloud.shopfly.b2c.core.goods.model.enums.Permission;
import cloud.shopfly.b2c.core.goods.service.SpecValuesManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Specification value controller
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 10:23:53
 */
@RestController
@RequestMapping("/seller/goods/specs/{spec_id}/values")
@Api(description = "Specification value correlationAPI")
@Validated
public class SpecValuesSellerController {

	@Autowired
	private SpecValuesManager specValuesManager;

	@ApiOperation(value = "Example Query the specification list", response = SpecValuesDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "spec_id", value = "specificationsid", required = true, dataType = "int", paramType = "path"), })
	@GetMapping
	public List<SpecValuesDO> list(@PathVariable("spec_id") Integer specId) {

		return this.specValuesManager.listBySpecId(specId, Permission.ADMIN);
	}

	@ApiOperation(value = "Add a specification value")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "spec_id", value = "specificationsid", required = true, dataType = "int", paramType = "path"),
			@ApiImplicitParam(name = "value_list", value = "Set of specification values", required = false, dataType = "string", paramType = "query", allowMultiple = true), })
	@PostMapping
	public List<SpecValuesDO> saveSpecValue(@PathVariable("spec_id") Integer specId, @NotNull(message = "Add at least one specification value") @ApiIgnore @RequestParam(value = "value_list",required = false) String[] valueList) {

		return this.specValuesManager.saveSpecValue(specId, valueList);
	}

}
