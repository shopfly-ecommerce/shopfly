/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.goods;

import com.enation.app.javashop.core.goods.model.dos.BrandDO;
import com.enation.app.javashop.core.goods.service.BrandManager;
import com.enation.app.javashop.framework.database.Page;
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
 * 品牌控制器
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-16 16:32:46
 */
@RestController
@RequestMapping("/seller/goods/brands")
@Api(description = "品牌相关API")
public class BrandSellerController {
    @Autowired
    private BrandManager brandManager;

    @ApiOperation(value = "查询品牌列表", response = BrandDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "品牌名称", dataType = "string", paramType = "query")})
    @GetMapping
    public Page list(@ApiIgnore @NotEmpty(message = "页码不能为空") Integer pageNo,
                     @ApiIgnore @NotEmpty(message = "每页数量不能为空") Integer pageSize,
                     String name) {

        return this.brandManager.list(pageNo, pageSize,name);
    }

    @ApiOperation(value = "添加品牌", response = BrandDO.class)
    @PostMapping
    public BrandDO add(@Valid BrandDO brand) {

        this.brandManager.add(brand);

        return brand;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改品牌", response = BrandDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")})
    public BrandDO edit(@Valid BrandDO brand, @PathVariable Integer id) {

        this.brandManager.edit(brand, id);

        return brand;
    }

    @DeleteMapping(value = "/{ids}")
    @ApiOperation(value = "删除品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "要删除的品牌主键集合", required = true, dataType = "int", paramType = "path", allowMultiple = true)})
    public String delete(@PathVariable Integer[] ids) {

        this.brandManager.delete(ids);

        return "";
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的品牌主键", required = true, dataType = "int", paramType = "path")})
    public BrandDO get(@PathVariable Integer id) {

        BrandDO brand = this.brandManager.getModel(id);

        return brand;
    }

}
