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

import cloud.shopfly.b2c.core.system.model.dos.AdminUser;
import cloud.shopfly.b2c.core.system.model.vo.AdminUserVO;
import cloud.shopfly.b2c.core.system.service.AdminUserManager;
import cloud.shopfly.b2c.framework.database.Page;
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
 * Platform Administrator Controller
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-20 20:38:26
 */
@RestController
@RequestMapping("/seller/systems/manager/admin-users")
@Api("Platform administrator management relatedAPI")
@Validated
public class AdminUserOperationManagerController {

    @Autowired
    private AdminUserManager adminUserManager;


    @ApiOperation(value = "Example Query the platform administrator list", response = AdminUser.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return this.adminUserManager.list(pageNo, pageSize);
    }


    @ApiOperation(value = "Adding a Platform Administrator", response = AdminUser.class)
    @PostMapping
    public AdminUser add(@Valid AdminUserVO adminUserVO) {
        return this.adminUserManager.add(adminUserVO);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modifying a Platform Administrator", response = AdminUser.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")
    })
    public AdminUser edit(@Valid AdminUserVO adminUserVO, @PathVariable Integer id) {
        return this.adminUserManager.edit(adminUserVO, id);
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Deleting a Platform Administrator")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The platform administrator primary key to delete", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {
        this.adminUserManager.delete(id);
        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Example Query a platform administrator")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the platform administrator to be queried", required = true, dataType = "int", paramType = "path")
    })
    public AdminUser get(@PathVariable Integer id) {

        AdminUser adminUser = this.adminUserManager.getModel(id);

        return adminUser;
    }
}
