/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.system;

import com.enation.app.javashop.core.system.model.dos.AdminUser;
import com.enation.app.javashop.core.system.model.vo.AdminUserVO;
import com.enation.app.javashop.core.system.service.AdminUserManager;
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
 * 平台管理员控制器
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-20 20:38:26
 */
@RestController
@RequestMapping("/seller/systems/manager/admin-users")
@Api("平台管理员管理相关API")
@Validated
public class AdminUserOperationManagerController {

    @Autowired
    private AdminUserManager adminUserManager;


    @ApiOperation(value = "查询平台管理员列表", response = AdminUser.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return this.adminUserManager.list(pageNo, pageSize);
    }


    @ApiOperation(value = "添加平台管理员", response = AdminUser.class)
    @PostMapping
    public AdminUser add(@Valid AdminUserVO adminUserVO) {
        return this.adminUserManager.add(adminUserVO);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改平台管理员", response = AdminUser.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    })
    public AdminUser edit(@Valid AdminUserVO adminUserVO, @PathVariable Integer id) {
        return this.adminUserManager.edit(adminUserVO, id);
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除平台管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的平台管理员主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {
        this.adminUserManager.delete(id);
        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个平台管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的平台管理员主键", required = true, dataType = "int", paramType = "path")
    })
    public AdminUser get(@PathVariable Integer id) {

        AdminUser adminUser = this.adminUserManager.getModel(id);

        return adminUser;
    }
}