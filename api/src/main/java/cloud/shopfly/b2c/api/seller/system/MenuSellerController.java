/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.seller.system;

import cloud.shopfly.b2c.core.system.model.dos.Menu;
import cloud.shopfly.b2c.core.system.model.vo.MenuVO;
import cloud.shopfly.b2c.core.system.model.vo.MenusVO;
import cloud.shopfly.b2c.core.system.service.MenuManager;
import cloud.shopfly.b2c.framework.util.BeanUtil;
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
 * 菜单管理控制器
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-19 09:46:02
 */
@RestController
@RequestMapping("/seller/systems/menus")
@Api(description = "菜单管理相关API")
public class MenuSellerController {

    @Autowired
    private MenuManager menuManager;


    @ApiOperation(value = "根据父id查询所有菜单", response = Menu.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parent_id", value = "菜单父id，如果查询顶级菜单则传0", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/{parent_id}/children")
    public List<MenusVO> getMenuTree(@PathVariable("parent_id") @ApiIgnore Integer parentId) {
        return this.menuManager.getMenuTree(parentId);
    }


    @ApiOperation(value = "添加菜单", response = Menu.class)
    @PostMapping
    public Menu add(@Valid MenuVO menu) {
        return this.menuManager.add(menu);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改菜单", response = Menu.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    })
    public Menu edit(@Valid MenuVO menuVO, @PathVariable Integer id) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuVO, menu);
        return this.menuManager.edit(menu, id);
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的菜单管理主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {
        this.menuManager.delete(id);
        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的菜单管理主键", required = true, dataType = "int", paramType = "path")
    })
    public Menu get(@PathVariable Integer id) {
        Menu menu = this.menuManager.getModel(id);
        return menu;
    }

}
