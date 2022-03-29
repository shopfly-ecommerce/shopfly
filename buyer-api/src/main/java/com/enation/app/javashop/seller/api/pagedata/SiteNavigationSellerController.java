/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.pagedata;

import com.enation.app.javashop.core.goods.constraint.annotation.SortType;
import com.enation.app.javashop.core.pagedata.model.SiteNavigation;
import com.enation.app.javashop.core.pagedata.service.SiteNavigationManager;
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

/**
 * 导航栏控制器
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 17:07:22
 */
@RestController
@RequestMapping("/seller/pages/site-navigations")
@Api(description = "导航栏相关API")
@Validated
public class SiteNavigationSellerController {

    @Autowired
    private SiteNavigationManager siteNavigationManager;


    @ApiOperation(value = "查询导航栏列表", response = SiteNavigation.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "client_type", value = "客户端类型", required = true, dataType = "string", paramType = "query", allowableValues = "PC,MOBILE"),
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @ApiIgnore String clientType) {

        return this.siteNavigationManager.list(pageNo, pageSize, clientType);
    }


    @ApiOperation(value = "添加导航栏", response = SiteNavigation.class)
    @PostMapping
    public SiteNavigation add(@Valid SiteNavigation siteNavigation) {

        this.siteNavigationManager.add(siteNavigation);

        return siteNavigation;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改导航栏", response = SiteNavigation.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    })
    public SiteNavigation edit(@Valid SiteNavigation siteNavigation, @PathVariable Integer id) {

        this.siteNavigationManager.edit(siteNavigation, id);

        return siteNavigation;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除导航栏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的导航栏主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.siteNavigationManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个导航栏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的导航栏主键", required = true, dataType = "int", paramType = "path")
    })
    public SiteNavigation get(@PathVariable Integer id) {

        SiteNavigation siteNavigation = this.siteNavigationManager.getModel(id);

        return siteNavigation;
    }

    @PutMapping("/{id}/{sort}")
    @ApiOperation(value = "上下移动导航栏菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "导航主键", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "sort", value = "上移 up ，下移down", required = true, dataType = "string", paramType = "path")
    })
    public SiteNavigation updateSort(@PathVariable(name = "id") Integer id, @PathVariable @SortType String sort) {

        return this.siteNavigationManager.updateSort(id, sort);
    }

}
