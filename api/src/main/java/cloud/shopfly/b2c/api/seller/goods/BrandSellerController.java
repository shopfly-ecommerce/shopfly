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

import cloud.shopfly.b2c.core.goods.model.dos.BrandDO;
import cloud.shopfly.b2c.core.goods.service.BrandManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Brand controller
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-16 16:32:46
 */
@RestController
@RequestMapping("/seller/goods/brands")
@Api(description = "Brand relatedAPI")
public class BrandSellerController {
    @Autowired
    private BrandManager brandManager;

    @ApiOperation(value = "Query brand list", response = BrandDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = " name", dataType = "string", paramType = "query")})
    @GetMapping
    public Page list(@ApiIgnore @NotEmpty(message = "The page number cannot be blank") Integer pageNo,
                     @ApiIgnore @NotEmpty(message = "The number of pages cannot be empty") Integer pageSize,
                     String name) {

        return this.brandManager.list(pageNo, pageSize,name);
    }

    @ApiOperation(value = "Add a brand", response = BrandDO.class)
    @PostMapping
    public BrandDO add(@Valid BrandDO brand) {

        this.brandManager.add(brand);

        return brand;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modify the brand", response = BrandDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")})
    public BrandDO edit(@Valid BrandDO brand, @PathVariable Integer id) {

        this.brandManager.edit(brand, id);

        return brand;
    }

    @DeleteMapping(value = "/{ids}")
    @ApiOperation(value = "Delete the brand")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "Collection of brand primary keys to delete", required = true, dataType = "int", paramType = "path", allowMultiple = true)})
    public String delete(@PathVariable Integer[] ids) {

        this.brandManager.delete(ids);

        return "";
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query a brand")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the brand to be queried", required = true, dataType = "int", paramType = "path")})
    public BrandDO get(@PathVariable Integer id) {

        BrandDO brand = this.brandManager.getModel(id);

        return brand;
    }

}
