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
import cloud.shopfly.b2c.core.goods.model.dos.CategoryBrandDO;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.dos.CategorySpecDO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsParamsGroupVO;
import cloud.shopfly.b2c.core.goods.model.vo.ParameterGroupVO;
import cloud.shopfly.b2c.core.goods.model.vo.SelectVO;
import cloud.shopfly.b2c.core.goods.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * Merchandise classification controller
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-15 17:22:06
 */
@RestController
@RequestMapping("/seller/goods")
@Api(description = "Commodity classification correlationAPI")
public class CategorySellerController {

    @Autowired
    private CategoryManager categoryManager;
    @Autowired
    private GoodsParamsManager goodsParamsManager;
    @Autowired
    private BrandManager brandManager;
    @Autowired
    private SpecificationManager specificationManager;
    @Autowired
    private ParameterGroupManager parameterGroupManager;

    @ApiOperation(value = "Query the list of subcategories of a category")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parent_id", value = "The fatheridAnd the top of0", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "format", value = "Type if the value ispluginQuery the format data used by the plug-in", required = false, dataType = "string", paramType = "query"),})
    @GetMapping(value = "/categories/{parent_id}/children")
    public List list(@ApiIgnore @PathVariable("parent_id") Integer parentId, @ApiIgnore String format) {

        List list = this.categoryManager.list(parentId, format);

        return list;
    }

    @ApiOperation(value = "Product release, get the current login user to choose the business category of all the parents")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "CategoriesidAnd the top of0", required = true, dataType = "int", paramType = "path"),})
    @GetMapping(value = "/category/{category_id}/children")
    public List<CategoryDO> list(@ApiIgnore @PathVariable("category_id") Integer categoryId) {
        List<CategoryDO> list = this.categoryManager.getCategory(categoryId);
        return list;
    }


    @ApiOperation(value = "Commodity publishing to get parameter information associated with selected categories")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "Categoriesid", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "goods_id", value = "productid", required = true, dataType = "int", paramType = "path"),
    })
    @GetMapping(value = "/category/{category_id}/{goods_id}/params")
    public List<GoodsParamsGroupVO> queryParams(@PathVariable("category_id") Integer categoryId, @PathVariable("goods_id") Integer goodsId) {

        List<GoodsParamsGroupVO> list = this.goodsParamsManager.queryGoodsParams(categoryId, goodsId);

        return list;
    }

    @ApiOperation(value = "Publish goods to get parameter information associated with the selected category")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "Categoriesid", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping(value = "/category/{category_id}/params")
    public List<GoodsParamsGroupVO> queryParams(@PathVariable("category_id") Integer categoryId) {

        List<GoodsParamsGroupVO> list = this.goodsParamsManager.queryGoodsParams(categoryId);

        return list;
    }

    @ApiOperation(value = "Modify the product to get the brand information associated with the selected category")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "Categoriesid", required = true, dataType = "int", paramType = "path"),
    })
    @GetMapping(value = "/category/{category_id}/brands")
    public List<BrandDO> queryBrands(@PathVariable("category_id") Integer categoryId) {

        List<BrandDO> list = this.brandManager.getBrandsByCategory(categoryId);

        return list;
    }

    @ApiOperation(value = "Adding an item category", response = CategoryDO.class)
    @PostMapping("/categories")
    public CategoryDO add(@Valid CategoryDO category) {

        this.categoryManager.add(category);

        return category;
    }

    @PutMapping(value = "/categories/{id}")
    @ApiOperation(value = "Modification of commodity classification", response = CategoryDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")})
    public CategoryDO edit(@Valid CategoryDO category, @ApiIgnore @PathVariable Integer id) {

        this.categoryManager.edit(category, id);

        return category;
    }

    @DeleteMapping(value = "/categories/{id}")
    @ApiOperation(value = "Delete product Categories")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the category of goods to be deleted", required = true, dataType = "int", paramType = "path")})
    public String delete(@PathVariable Integer id) {

        this.categoryManager.delete(id);

        return "";
    }

    @GetMapping(value = "/categories/{id}")
    @ApiOperation(value = "Query an item category")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the category of goods to be queried", required = true, dataType = "int", paramType = "path")})
    public CategoryDO get(@PathVariable Integer id) {

        CategoryDO category = this.categoryManager.getModel(id);

        return category;
    }

    @ApiOperation(value = "Querying category Brands", notes = "Example Query the brand bound to a category,Includes unselected brands")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "Categoriesid", required = true, dataType = "int", paramType = "path"),})
    @GetMapping(value = "/categories/{category_id}/brands")
    public List<SelectVO> getCatBrands(@PathVariable("category_id") Integer categoryId) {

        List<SelectVO> brands = brandManager.getCatBrand(categoryId);

        return brands;
    }

    @ApiOperation(value = "The administrator binds a brand to a category", notes = "The administrator binds a brand to a category使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "Categoriesid", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "choose_brands", value = "brandidAn array of", required = true, paramType = "query", dataType = "int", allowMultiple = true)})
    @PutMapping(value = "/categories/{category_id}/brands")
    public List<CategoryBrandDO> saveBrand(@PathVariable("category_id") Integer categoryId, @ApiIgnore @RequestParam(value = "choose_brands",required = false) Integer[] chooseBrands) {
        return this.categoryManager.saveBrand(categoryId, chooseBrands);
    }

    @ApiOperation(value = "Querying Category Specifications", notes = "Query all specifications, including category binding specifications,selectedfortrue")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "Categoriesid", required = true, dataType = "int", paramType = "path"),})
    @GetMapping(value = "/categories/{category_id}/select-specs")
    public List<SelectVO> getCatSpecs(@PathVariable("category_id") Integer categoryId) {

        List<SelectVO> brands = specificationManager.getCatSpecification(categoryId);

        return brands;
    }

    @ApiOperation(value = "Bind specifications by category", notes = "Bind specifications by category使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "Categoriesid", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "choose_specs", value = "specificationsidAn array of", required = true, paramType = "query", dataType = "int", allowMultiple = true),})
    @PutMapping(value = "/categories/{category_id}/specs")
    public List<CategorySpecDO> saveSpec(@PathVariable("category_id") Integer categoryId, @ApiIgnore @RequestParam(value = "choose_specs",required = false) Integer[] chooseSpecs) {

        return this.categoryManager.saveSpec(categoryId, chooseSpecs);
    }

    @ApiOperation(value = "Querying Category Parameters", notes = "Example Query binding parameters of a class, including parameter groups and parameters")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "Categoriesid", required = true, dataType = "int", paramType = "path"),})
    @GetMapping(value = "/categories/{category_id}/param")
    public List<ParameterGroupVO> getCatParam(@PathVariable("category_id") Integer categoryId) {

        return parameterGroupManager.getParamsByCategory(categoryId);
    }





}
