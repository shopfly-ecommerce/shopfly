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

import cloud.shopfly.b2c.core.goods.model.dos.SpecificationDO;
import cloud.shopfly.b2c.core.goods.model.vo.SpecificationVO;
import cloud.shopfly.b2c.core.goods.service.SpecificationManager;
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
import java.util.List;

/**
 * Specification controller
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 09:31:27
 */
@RestController
@RequestMapping("/seller/goods")
@Api(description = "Specification correlationAPI")
@Validated
public class SpecificationSellerController {

    @Autowired
    private SpecificationManager specificationManager;

    @ApiOperation(value = "According to the classificationidQuerying specifications includes specification values", notes = "According to the classificationidThe query specification")
    @ApiImplicitParam(name = "category_id", value = "Categoriesid", required = true, paramType = "path", dataType = "int")
    @GetMapping("/categories/{category_id}/specs")
    public List<SpecificationVO> sellerQuerySpec(@PathVariable("category_id") Integer categoryId) {

        return this.specificationManager.querySpec(categoryId);
    }

    @ApiOperation(value = "Example Query the specification list", response = SpecificationDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query") })
    @GetMapping("/specs")
    public Page list(@ApiIgnore  Integer pageNo, @ApiIgnore Integer pageSize) {

        return this.specificationManager.list(pageNo, pageSize);
    }

    @ApiOperation(value = "Add specification item", response = SpecificationDO.class)
    @PostMapping("/specs")
    public SpecificationDO add(@Valid SpecificationDO specification) {

        this.specificationManager.add(specification);

        return specification;
    }

    @PutMapping(value = "/specs/{id}")
    @ApiOperation(value = "Modifying Specifications", response = SpecificationDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path") })
    public SpecificationDO edit(@Valid SpecificationDO specification, @PathVariable Integer id) {

        this.specificationManager.edit(specification, id);

        return specification;
    }

    @DeleteMapping(value = "/specs/{ids}")
    @ApiOperation(value = "Deleting specifications")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "Primary key of the specification to be deleted", required = true, dataType = "int", paramType = "path",allowMultiple=true) })
    public String delete(@PathVariable Integer[] ids) {

        this.specificationManager.delete(ids);

        return "";
    }

    @GetMapping(value = "/specs/{id}")
    @ApiOperation(value = "Example Query a specification")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the specification to be queried", required = true, dataType = "int", paramType = "path") })
    public SpecificationDO get(@PathVariable Integer id) {

        SpecificationDO specification = this.specificationManager.getModel(id);

        return specification;
    }
}
