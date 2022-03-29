/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.system;

import com.enation.app.javashop.core.system.model.dos.RoleDO;
import com.enation.app.javashop.core.system.model.vo.RoleVO;
import com.enation.app.javashop.core.system.service.RoleSeller;
import com.enation.app.javashop.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;
import java.util.List;


/**
 * 角色表控制器
 *
 * @author admin
 * @version v1.0.0
 * @since v7.0.0
 * 2018-04-17 16:48:27
 */
@RestController
@RequestMapping("/seller/systems/roles")
@Api(description = "角色表相关API")
public class RoleManagerController {

    @Autowired
    private RoleSeller roleManager;


    @ApiOperation(value = "查询角色列表", response = RoleDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore @NotEmpty(message = "页码不能为空") Integer pageNo, @ApiIgnore @NotEmpty(message = "每页数量不能为空") Integer pageSize) {
        return this.roleManager.list(pageNo, pageSize);
    }


    @ApiOperation(value = "添加角色", response = RoleDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleVO", value = "角色", required = true, dataType = "RoleVO", paramType = "body")
    })
    @PostMapping
    public RoleVO add(@RequestBody @ApiIgnore RoleVO roleVO) {
        return this.roleManager.add(roleVO);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改角色表", response = RoleDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "roleVO", value = "菜单", required = true, dataType = "RoleVO", paramType = "body")
    })
    public RoleVO edit(@RequestBody @ApiIgnore RoleVO roleVO, @PathVariable Integer id) {
        return this.roleManager.edit(roleVO, id);
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的角色表主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {
        this.roleManager.delete(id);
        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个角色表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的角色表主键", required = true, dataType = "int", paramType = "path")
    })
    public RoleVO get(@PathVariable Integer id) {
        return this.roleManager.getRole(id);
    }

    @GetMapping(value = "/{id}/checked")
    @ApiOperation(value = "根据角色id查询所拥有的菜单权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的角色表主键", required = true, dataType = "int", paramType = "path")
    })
    public List<String> getCheckedMenu(@PathVariable Integer id) {
        return this.roleManager.getRoleMenu(id);
    }

}
