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
package cloud.shopfly.b2c.api.seller.pagedata;

import cloud.shopfly.b2c.core.goods.constraint.annotation.SortType;
import cloud.shopfly.b2c.core.pagedata.model.SiteNavigation;
import cloud.shopfly.b2c.core.pagedata.service.SiteNavigationManager;
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
 * Navigation bar controller
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 17:07:22
 */
@RestController
@RequestMapping("/seller/pages/site-navigations")
@Api(description = "Navigation bar correlationAPI")
@Validated
public class SiteNavigationSellerController {

    @Autowired
    private SiteNavigationManager siteNavigationManager;


    @ApiOperation(value = "Query the navigation bar list", response = SiteNavigation.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "client_type", value = "Client type", required = true, dataType = "string", paramType = "query", allowableValues = "PC,MOBILE"),
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @ApiIgnore String clientType) {

        return this.siteNavigationManager.list(pageNo, pageSize, clientType);
    }


    @ApiOperation(value = "Add navigation", response = SiteNavigation.class)
    @PostMapping
    public SiteNavigation add(@Valid SiteNavigation siteNavigation) {

        this.siteNavigationManager.add(siteNavigation);

        return siteNavigation;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modify navigation bar", response = SiteNavigation.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")
    })
    public SiteNavigation edit(@Valid SiteNavigation siteNavigation, @PathVariable Integer id) {

        this.siteNavigationManager.edit(siteNavigation, id);

        return siteNavigation;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete navigation bar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The primary key of the navigation bar to delete", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.siteNavigationManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query a navigation bar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the navigation bar to be queried", required = true, dataType = "int", paramType = "path")
    })
    public SiteNavigation get(@PathVariable Integer id) {

        SiteNavigation siteNavigation = this.siteNavigationManager.getModel(id);

        return siteNavigation;
    }

    @PutMapping("/{id}/{sort}")
    @ApiOperation(value = "Move the navigation bar menu up and down")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Navigation keys", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "sort", value = " upup Move down,down", required = true, dataType = "string", paramType = "path")
    })
    public SiteNavigation updateSort(@PathVariable(name = "id") Integer id, @PathVariable @SortType String sort) {

        return this.siteNavigationManager.updateSort(id, sort);
    }

}
