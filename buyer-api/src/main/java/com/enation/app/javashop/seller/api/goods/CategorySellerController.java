/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.goods;

import com.enation.app.javashop.core.goods.model.dos.BrandDO;
import com.enation.app.javashop.core.goods.model.dos.CategoryBrandDO;
import com.enation.app.javashop.core.goods.model.dos.CategoryDO;
import com.enation.app.javashop.core.goods.model.dos.CategorySpecDO;
import com.enation.app.javashop.core.goods.model.vo.GoodsParamsGroupVO;
import com.enation.app.javashop.core.goods.model.vo.ParameterGroupVO;
import com.enation.app.javashop.core.goods.model.vo.SelectVO;
import com.enation.app.javashop.core.goods.service.*;
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
 * 商品分类控制器
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-15 17:22:06
 */
@RestController
@RequestMapping("/seller/goods")
@Api(description = "商品分类相关API")
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

    @ApiOperation(value = "查询某分类下的子分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parent_id", value = "父id，顶级为0", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "format", value = "类型，如果值是plugin则查询插件使用的格式数据", required = false, dataType = "string", paramType = "query"),})
    @GetMapping(value = "/categories/{parent_id}/children")
    public List list(@ApiIgnore @PathVariable("parent_id") Integer parentId, @ApiIgnore String format) {

        List list = this.categoryManager.list(parentId, format);

        return list;
    }

    @ApiOperation(value = "商品发布，获取当前登录用户选择经营类目的所有父")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "分类id，顶级为0", required = true, dataType = "int", paramType = "path"),})
    @GetMapping(value = "/category/{category_id}/children")
    public List<CategoryDO> list(@ApiIgnore @PathVariable("category_id") Integer categoryId) {
        List<CategoryDO> list = this.categoryManager.getCategory(categoryId);
        return list;
    }


    @ApiOperation(value = "商品发布，获取所选分类关联的参数信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "分类id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "goods_id", value = "商品id", required = true, dataType = "int", paramType = "path"),
    })
    @GetMapping(value = "/category/{category_id}/{goods_id}/params")
    public List<GoodsParamsGroupVO> queryParams(@PathVariable("category_id") Integer categoryId, @PathVariable("goods_id") Integer goodsId) {

        List<GoodsParamsGroupVO> list = this.goodsParamsManager.queryGoodsParams(categoryId, goodsId);

        return list;
    }

    @ApiOperation(value = "发布商品，获取所选分类关联的参数信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "分类id", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping(value = "/category/{category_id}/params")
    public List<GoodsParamsGroupVO> queryParams(@PathVariable("category_id") Integer categoryId) {

        List<GoodsParamsGroupVO> list = this.goodsParamsManager.queryGoodsParams(categoryId);

        return list;
    }

    @ApiOperation(value = "修改商品，获取所选分类关联的品牌信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "分类id", required = true, dataType = "int", paramType = "path"),
    })
    @GetMapping(value = "/category/{category_id}/brands")
    public List<BrandDO> queryBrands(@PathVariable("category_id") Integer categoryId) {

        List<BrandDO> list = this.brandManager.getBrandsByCategory(categoryId);

        return list;
    }

    @ApiOperation(value = "添加商品分类", response = CategoryDO.class)
    @PostMapping("/categories")
    public CategoryDO add(@Valid CategoryDO category) {

        this.categoryManager.add(category);

        return category;
    }

    @PutMapping(value = "/categories/{id}")
    @ApiOperation(value = "修改商品分类", response = CategoryDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")})
    public CategoryDO edit(@Valid CategoryDO category, @ApiIgnore @PathVariable Integer id) {

        this.categoryManager.edit(category, id);

        return category;
    }

    @DeleteMapping(value = "/categories/{id}")
    @ApiOperation(value = "删除商品分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的商品分类主键", required = true, dataType = "int", paramType = "path")})
    public String delete(@PathVariable Integer id) {

        this.categoryManager.delete(id);

        return "";
    }

    @GetMapping(value = "/categories/{id}")
    @ApiOperation(value = "查询一个商品分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的商品分类主键", required = true, dataType = "int", paramType = "path")})
    public CategoryDO get(@PathVariable Integer id) {

        CategoryDO category = this.categoryManager.getModel(id);

        return category;
    }

    @ApiOperation(value = "查询分类品牌", notes = "查询某个分类绑定的品牌,包括未选中的品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "分类id", required = true, dataType = "int", paramType = "path"),})
    @GetMapping(value = "/categories/{category_id}/brands")
    public List<SelectVO> getCatBrands(@PathVariable("category_id") Integer categoryId) {

        List<SelectVO> brands = brandManager.getCatBrand(categoryId);

        return brands;
    }

    @ApiOperation(value = "管理员操作分类绑定品牌", notes = "管理员操作分类绑定品牌使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "分类id", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "choose_brands", value = "品牌id数组", required = true, paramType = "query", dataType = "int", allowMultiple = true)})
    @PutMapping(value = "/categories/{category_id}/brands")
    public List<CategoryBrandDO> saveBrand(@PathVariable("category_id") Integer categoryId, @ApiIgnore @RequestParam(value = "choose_brands",required = false) Integer[] chooseBrands) {
        return this.categoryManager.saveBrand(categoryId, chooseBrands);
    }

    @ApiOperation(value = "查询分类规格", notes = "查询所有规格，包括分类绑定的规格，selected为true")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "分类id", required = true, dataType = "int", paramType = "path"),})
    @GetMapping(value = "/categories/{category_id}/select-specs")
    public List<SelectVO> getCatSpecs(@PathVariable("category_id") Integer categoryId) {

        List<SelectVO> brands = specificationManager.getCatSpecification(categoryId);

        return brands;
    }

    @ApiOperation(value = "管理员操作分类绑定规格", notes = "管理员操作分类绑定规格使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "分类id", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "choose_specs", value = "规格id数组", required = true, paramType = "query", dataType = "int", allowMultiple = true),})
    @PutMapping(value = "/categories/{category_id}/specs")
    public List<CategorySpecDO> saveSpec(@PathVariable("category_id") Integer categoryId, @ApiIgnore @RequestParam(value = "choose_specs",required = false) Integer[] chooseSpecs) {

        return this.categoryManager.saveSpec(categoryId, chooseSpecs);
    }

    @ApiOperation(value = "查询分类参数", notes = "查询分类绑定的参数，包括参数组和参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "分类id", required = true, dataType = "int", paramType = "path"),})
    @GetMapping(value = "/categories/{category_id}/param")
    public List<ParameterGroupVO> getCatParam(@PathVariable("category_id") Integer categoryId) {

        return parameterGroupManager.getParamsByCategory(categoryId);
    }





}
