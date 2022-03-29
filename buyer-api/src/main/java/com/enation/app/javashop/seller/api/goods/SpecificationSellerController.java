/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.goods;

import com.enation.app.javashop.core.goods.model.dos.SpecificationDO;
import com.enation.app.javashop.core.goods.model.vo.SpecificationVO;
import com.enation.app.javashop.core.goods.service.SpecificationManager;
import com.enation.app.javashop.framework.database.Page;
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
 * 规格项控制器
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 09:31:27
 */
@RestController
@RequestMapping("/seller/goods")
@Api(description = "规格项相关API")
@Validated
public class SpecificationSellerController {

    @Autowired
    private SpecificationManager specificationManager;

    @ApiOperation(value = "根据分类id查询规格包括规格值", notes = "根据分类id查询规格")
    @ApiImplicitParam(name = "category_id", value = "分类id", required = true, paramType = "path", dataType = "int")
    @GetMapping("/categories/{category_id}/specs")
    public List<SpecificationVO> sellerQuerySpec(@PathVariable("category_id") Integer categoryId) {

        return this.specificationManager.querySpec(categoryId);
    }

    @ApiOperation(value = "查询规格项列表", response = SpecificationDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query") })
    @GetMapping("/specs")
    public Page list(@ApiIgnore  Integer pageNo, @ApiIgnore Integer pageSize) {

        return this.specificationManager.list(pageNo, pageSize);
    }

    @ApiOperation(value = "添加规格项", response = SpecificationDO.class)
    @PostMapping("/specs")
    public SpecificationDO add(@Valid SpecificationDO specification) {

        this.specificationManager.add(specification);

        return specification;
    }

    @PutMapping(value = "/specs/{id}")
    @ApiOperation(value = "修改规格项", response = SpecificationDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path") })
    public SpecificationDO edit(@Valid SpecificationDO specification, @PathVariable Integer id) {

        this.specificationManager.edit(specification, id);

        return specification;
    }

    @DeleteMapping(value = "/specs/{ids}")
    @ApiOperation(value = "删除规格项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "要删除的规格项主键", required = true, dataType = "int", paramType = "path",allowMultiple=true) })
    public String delete(@PathVariable Integer[] ids) {

        this.specificationManager.delete(ids);

        return "";
    }

    @GetMapping(value = "/specs/{id}")
    @ApiOperation(value = "查询一个规格项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的规格项主键", required = true, dataType = "int", paramType = "path") })
    public SpecificationDO get(@PathVariable Integer id) {

        SpecificationDO specification = this.specificationManager.getModel(id);

        return specification;
    }
}
