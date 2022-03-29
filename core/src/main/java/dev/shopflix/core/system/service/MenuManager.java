/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service;

import dev.shopflix.core.system.model.dos.Menu;
import dev.shopflix.core.system.model.vo.MenuVO;
import dev.shopflix.core.system.model.vo.MenusVO;
import dev.shopflix.framework.database.Page;

import java.util.List;

/**
 * 菜单管理业务层
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-19 09:46:02
 */
public interface MenuManager {

    /**
     * 查询菜单管理列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加菜单管理
     *
     * @param menu 菜单管理
     * @return Menu 菜单管理
     */
    Menu add(MenuVO menu);

    /**
     * 修改菜单管理
     *
     * @param menu 菜单管理
     * @param id   菜单管理主键
     * @return Menu 菜单管理
     */
    Menu edit(Menu menu, Integer id);

    /**
     * 删除菜单管理
     *
     * @param id 菜单管理主键
     */
    void delete(Integer id);

    /**
     * 获取菜单管理
     *
     * @param id 菜单管理主键
     * @return MenuVO  菜单管理
     */
    Menu getModel(Integer id);

    /**
     * 根据id获取菜单集合
     *
     * @param id 菜单的id
     * @return
     */
    List<MenusVO> getMenuTree(Integer id);

    /**
     * 获取菜单管理
     *
     * @param identifier 菜单的唯一标识
     * @return MenuVO  菜单管理
     */
    Menu getMenuByIdentifier(String identifier);

}