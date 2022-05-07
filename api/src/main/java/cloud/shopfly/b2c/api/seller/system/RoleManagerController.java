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
package cloud.shopfly.b2c.api.seller.system;

import cloud.shopfly.b2c.core.system.model.dos.RoleDO;
import cloud.shopfly.b2c.core.system.model.vo.RoleVO;
import cloud.shopfly.b2c.core.system.service.RoleSeller;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;
import java.util.List;


/**
 * Role list controller
 *
 * @author admin
 * @version v1.0.0
 * @since v7.0.0
 * 2018-04-17 16:48:27
 */
@RestController
@RequestMapping("/seller/systems/roles")
@Api(description = "Role table correlationAPI")
public class RoleManagerController {

    @Autowired
    private RoleSeller roleManager;


    @ApiOperation(value = "Querying the Role List", response = RoleDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore @NotEmpty(message = "The page number cannot be blank") Integer pageNo, @ApiIgnore @NotEmpty(message = "The number of pages cannot be empty") Integer pageSize) {
        return this.roleManager.list(pageNo, pageSize);
    }


    @ApiOperation(value = "Adding roles", response = RoleDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleVO", value = "role", required = true, dataType = "RoleVO", paramType = "body")
    })
    @PostMapping
    public RoleVO add(@RequestBody @ApiIgnore RoleVO roleVO) {
        return this.roleManager.add(roleVO);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modifying the role table", response = RoleDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "roleVO", value = "The menu", required = true, dataType = "RoleVO", paramType = "body")
    })
    public RoleVO edit(@RequestBody @ApiIgnore RoleVO roleVO, @PathVariable Integer id) {
        return this.roleManager.edit(roleVO, id);
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete the role")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the role table to be deleted", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {
        this.roleManager.delete(id);
        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query a role table")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the role table to be queried", required = true, dataType = "int", paramType = "path")
    })
    public RoleVO get(@PathVariable Integer id) {
        return this.roleManager.getRole(id);
    }

    @GetMapping(value = "/{id}/checked")
    @ApiOperation(value = "According to the characteridQuery menu permissions")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the role table to be queried", required = true, dataType = "int", paramType = "path")
    })
    public List<String> getCheckedMenu(@PathVariable Integer id) {
        return this.roleManager.getRoleMenu(id);
    }

}
